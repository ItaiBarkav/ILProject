import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloNumVarType;
import ilog.cplex.IloCplex;

public class Solver {
	
	private String fileName;
	private ArrayList<file> files = new ArrayList<file>();
	private ArrayList<block> blocks = new ArrayList<block>();
	private ArrayList<String> output = new ArrayList<String>();
	private double time;
	private IloCplex cplex;
	private int totalFreeSpace = 0;
	private int numOfFiles = 0;
	private int numOfBlocks = 0;
	private ArrayList<Integer> deleteFile = new ArrayList<Integer>();
	private ArrayList<Integer> deleteBlock = new ArrayList<Integer>();
	private int totalSize = 0;
	
	public void solve(String fileName, String K) {
		
		this.fileName = fileName;

		File file = new File(this.fileName);
        BufferedReader br = null;
        
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
        String st;
        ArrayList<Integer> tempSize = new ArrayList<Integer>();
        
        try {
			while((st=br.readLine()) != null){
			    
			    if(st.charAt(0)=='F') {
			    	
			    	String[] fTemp = st.split(",");
			    	int fSize = 0, fj = 0;
			    	int[] BlocksID = new int[Integer.parseInt(fTemp[4])];

			    	for(int i=5; i<fTemp.length; i++) {
			    		tempSize.add(Integer.parseInt(fTemp[i]));
			    		if(i%2 == 0)
			    			fSize += Integer.parseInt(fTemp[i]);
			    		else {
			    			BlocksID[fj] = Integer.parseInt(fTemp[i]);
			    			fj++;
			    		}
			    	}			    	
			    	file f = new file(Integer.parseInt(fTemp[1]), fTemp[2], fSize, Integer.parseInt(fTemp[4]), BlocksID);
					files.add(f);
			    }// End if(F)
			    
			    if(st.charAt(0)=='B') {
			    	
			    	String[] bTemp = st.split(",");
			    	int bj = 0;
			    	int[] FilesID = new int[Integer.parseInt(bTemp[3])];

			    	for(int i=4; i<bTemp.length; i++,bj++) 
			    		FilesID[bj] = Integer.parseInt(bTemp[i]);
			    				    				    	
			    	block b = new block(Integer.parseInt(bTemp[1]), bTemp[2], tempSize.get((tempSize.indexOf(Integer.parseInt(bTemp[1]))+1)), Integer.parseInt(bTemp[3]), FilesID);
			    	totalSize += b.size;
					blocks.add(b);			    	
			    }// End if(B)
			    
			    if(st.charAt(0)=='D') {
			    	break;
			    }// End if(D)
			    
			    if(st.charAt(0)=='#') {
			    	output.add(st);
			    }// End if(D)
			}// End while
		} catch (IOException e) {
			e.printStackTrace();
		}// End try
                
        //CPLEX
    	try {
    		// define new model
			cplex = new IloCplex();
			
			IloNumVar[]f = new IloNumVar[files.size()];
			IloNumVar[]b = new IloNumVar[blocks.size()];
			
			for(int i=0; i<f.length; i++)
				f[i] = cplex.numVar(0, 1, IloNumVarType.Int);
			for(int i=0; i<b.length; i++)
				b[i] = cplex.numVar(0, 1, IloNumVarType.Int);
			
			// expressions
			IloLinearNumExpr blockSize = cplex.linearNumExpr();
        	for(int i=0; i<b.length; i++)
        		blockSize.addTerm(blocks.get(i).size, b[i]);

			IloLinearNumExpr objective = cplex.linearNumExpr();
        		for (int i=0; i<f.length; i++) {
        			objective.addTerm(1, f[i]);
        		}

        	// define objective
        	cplex.addMinimize(objective);
        	
        	// constraints
        	cplex.addGe(blockSize, Integer.parseInt(K));
        	
        	for (int i=0; i<b.length; i++) {
        		int[] arr = blocks.get(i).filesID;
        		for(int j=0; j<blocks.get(i).NumOfFiles; j++)
        			cplex.addLe(b[i], f[arr[j]]);
        	}
        	
        	// solve
        	if (cplex.solve()) {
        		System.out.println("Number of deleted file(obj) = "+cplex.getObjValue());
        		for (int i=0; i<f.length; i++) {
        			System.out.println("F"+ (i) +" = "+cplex.getValue(f[i]));
        			if(cplex.getValue(f[i]) == 1) {
        				files.get(i).Delete = 1;
        				numOfFiles++;
        				deleteFile.add(i);
        			}
        		}
        		System.out.println("-----------");
        		for (int i=0; i<b.length; i++) {
        			System.out.println("B"+ (i) +" = "+cplex.getValue(b[i]));
        			if(cplex.getValue(b[i]) == 1) {
        				blocks.get(i).Delete = 1;
        				totalFreeSpace += blocks.get(i).size;
        				numOfBlocks++;
        				deleteBlock.add(i);
        			}
        		}
        		System.out.println("obj = "+cplex.getCplexTime());
        		time = cplex.getCplexTime();
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
	
}
