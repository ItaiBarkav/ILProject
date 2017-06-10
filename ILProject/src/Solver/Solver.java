package Solver;
import java.util.ArrayList;

import Objects.block;
import Objects.file;
import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloNumVarType;
import ilog.cplex.IloCplex;

public class Solver {
	
	private String fileName;												// Keep the path to the file to analysis.
	private ArrayList<file> files = new ArrayList<file>();					// Array that represent the files in the file system.
	private ArrayList<block> blocks = new ArrayList<block>();				// Array that represent the blocks in the file system.
	private ArrayList<String> output = new ArrayList<String>();				// Save the relevant information about the file system (to the export file).
	private double time;													// Saving the execution time.
	private double timeInput;												// Saving the time of creating the input (files&blocks arrays).
	private IloCplex cplex;													// The CPLEX model.
	private int totalFreeSpace = 0;											// Keep the size of the total free space after the analysis.
	private int numOfFiles = 0;												// Keep the number of the files for deletion after the analysis.
	private int numOfBlocks = 0;											// Keep the number of the blocks for deletion after the analysis.
	private ArrayList<Integer> deleteFile = new ArrayList<Integer>();		// Array that save the files for deletion after the analysis.
	private ArrayList<Integer> deleteBlock = new ArrayList<Integer>();		// Array that save the blocks for deletion after the analysis.
	private int totalSize = 0;												// Save the size of data in the file system.
	private int inputSize = 0;												// Save the size of the files&blocks arrays.
	private int deletedB = 0;												// Keep the number of the blocks for deletion after the analysis that the solver doesn't delete them.
	private int sizeB = 0;													// Keep the size of the total free space after the analysis that the solver doesn't delete them.
	
	public void solve(String fileName, String K, boolean obj) {
		
		long startTime = System.nanoTime();
		
		this.fileName = fileName;
		
		FileAnalysis FA = new FileAnalysis(fileName);
		
		files = FA.getFiles();
		blocks = FA.getBlocks();
		output = FA.getOutput();
		totalSize = FA.getTotalSize();
		inputSize = FA.getInputSize();
        
        // Calculate input time
        long duration = System.nanoTime() - startTime;
        timeInput = duration/1000000000.0;
        

        // CPLEX - Solver
    	try {
    		// Define new model
			cplex = new IloCplex();
			
			// Define files&blocks arrays
			IloNumVar[]f = new IloNumVar[files.size()];
			IloNumVar[]b = new IloNumVar[blocks.size()];
			
			// Reset the arrays
			for(int i=0; i<f.length; i++)
				f[i] = cplex.numVar(0, 1, IloNumVarType.Int);
			for(int i=0; i<b.length; i++)
				b[i] = cplex.numVar(0, 1, IloNumVarType.Int);
					
			int kTemp;
			   	
        	if(obj == true) {	// Minimize objective
        		// expressions
    			IloLinearNumExpr blockSize = cplex.linearNumExpr();
            	for(int i=0; i<b.length; i++)
            		blockSize.addTerm(blocks.get(i).getSize(), b[i]);

    			IloLinearNumExpr objective = cplex.linearNumExpr();
            		for (int i=0; i<f.length; i++) {
            			objective.addTerm(1, f[i]);
            		}

            	// define objective - Sum of all fj
            	cplex.addMinimize(objective);
            	 
            	// Calculate the K(if it's in % or not)
            	if(K.charAt(K.length()-1) == '%') {
            		kTemp = totalSize/100;
            		K = K.substring(0, K.length()-1);
            		kTemp *= Integer.parseInt(K);      		
            	}
            	else
            		kTemp = Integer.parseInt(K);
            	
            	// constraints
            	cplex.addGe(blockSize, kTemp);
            	
            	//bi <= fj
            	for (int i=0; i<b.length; i++) {
            		int[] arr = blocks.get(i).getFilesID();
            		for(int j=0; j<blocks.get(i).getNumOfFiles(); j++)
            			cplex.addLe(b[i], f[arr[j]]);
            	}
        	}
        	else {	//// Maximize objective
        		// expressions
    			IloLinearNumExpr fileNum = cplex.linearNumExpr();
            	for(int i=0; i<f.length; i++)
            		fileNum.addTerm(1, f[i]);

    			IloLinearNumExpr objective = cplex.linearNumExpr();
            		for (int i=0; i<b.length; i++) {
            			objective.addTerm(blocks.get(i).getSize(), b[i]);
            		}

            	// define objective - Sum of all bi*bi.size
            	cplex.addMaximize(objective);
            	      	
            	// Calculate the K(if it's in % or not)
            	if(K.charAt(K.length()-1) == '%') {
            		kTemp = files.size()/100;
            		K = K.substring(0, K.length()-1);
            		kTemp *= Integer.parseInt(K);      		
            	}
            	else
            		kTemp = Integer.parseInt(K);
            	
            	// constraints
            	cplex.addLe(fileNum, kTemp);
            	
            	//bi <= fj
            	for (int i=0; i<b.length; i++) {
            		int[] arr = blocks.get(i).getFilesID();
            		for(int j=0; j<blocks.get(i).getNumOfFiles(); j++)
            			cplex.addLe(b[i], f[arr[j]]);
            	}
        	}
			      	
        	// solve
        	if (cplex.solve()) {
        		for (int i=0; i<f.length; i++) {
        			if(cplex.getValue(f[i]) == 1) {
        				files.get(i).setDelete(1);
        				numOfFiles++;
        				deleteFile.add(i);
        			}
        		}

        		for (int i=0; i<b.length; i++) {
        			if(cplex.getValue(b[i]) == 1) {
        				blocks.get(i).setDelete(1);
        				totalFreeSpace += blocks.get(i).getSize();
        				numOfBlocks++;
        				deleteBlock.add(i);
        			}
        		}

        		time = cplex.getCplexTime();
        		
        		int check = 0;
        		
        		for(int i=0; i<blocks.size();i++)
        			if(blocks.get(i).getDelete() == 0) {
        				check = 0;
        				for(int j=0;j<blocks.get(i).getNumOfFiles();j++)
        					if(files.get(blocks.get(i).getFilesID()[j]).getDelete() == 1)
        						check++;      						
        					else
        						break;
        				if(check == blocks.get(i).getNumOfFiles()) {
        					deletedB++;
        					sizeB += blocks.get(i).getSize();
        				}
        			}
        		
        	}
        	else {
        		System.out.println("problem not solved");
        	}
        	
        	cplex.end();
			
		} catch (IloException e) {
			e.printStackTrace();
		}
        
	}

	public ArrayList<file> getFiles() {
		return files;
	}

	public ArrayList<block> getBlocks() {
		return blocks;
	}

	public ArrayList<String> getOutput() {
		return output;
	}

	public String getFileName() {
		return fileName;
	}

	public double getTime() {
		return time;
	}

	public double getTimeInput() {
		return timeInput;
	}

	public int getTotalFreeSpace() {
		return totalFreeSpace;
	}

	public int getNumOfFiles() {
		return numOfFiles;
	}

	public int getNumOfBlocks() {
		return numOfBlocks;
	}

	public ArrayList<Integer> getDeleteFile() {
		return deleteFile;
	}

	public ArrayList<Integer> getDeleteBlock() {
		return deleteBlock;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public int getInputSize() {
		return inputSize;
	}

	public int getDeletedB() {
		return deletedB;
	}

	public int getSizeB() {
		return sizeB;
	}
	
}
