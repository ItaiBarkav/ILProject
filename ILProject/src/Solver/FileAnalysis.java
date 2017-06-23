package Solver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import Objects.block;
import Objects.file;

public class FileAnalysis {
	private ArrayList<file> files = new ArrayList<file>();			// Array that represent the files in the file system.
	private ArrayList<block> blocks = new ArrayList<block>();		// Array that represent the blocks in the file system.
	private ArrayList<String> output = new ArrayList<String>();		// Save the relevant information about the file system (to the export file).
	private long totalSize = 0;										// Save the size of data in the file system.
	private int inputSize = 0;										// Save the size of the files&blocks arrays.

	public FileAnalysis(String fileName) {
		File file = new File(fileName);
        BufferedReader br = null;
        
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
        String st;
        ArrayList<Integer> tempSize = new ArrayList<Integer>();
        
        try {
			while((st=br.readLine()) != null) {
			    
			    if(st.charAt(0)=='F') {	// If it's file
			    	
			    	String[] fTemp = st.split(",");		//	Split the line by the text file form.
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
					inputSize += 20 + 4*f.getNumOfBlocks();
			    }// End if(F)
			    
			    if(st.charAt(0)=='B') {	// If it's block
			    	
			    	String[] bTemp = st.split(",");		//	Split the line by the text file form.
			    	int bj = 0;
			    	int[] FilesID = new int[Integer.parseInt(bTemp[3])];

			    	for(int i=4; i<bTemp.length; i++,bj++) 
			    		FilesID[bj] = Integer.parseInt(bTemp[i]);
			    				    				    	
			    	block b = new block(Integer.parseInt(bTemp[1]), bTemp[2], tempSize.get((tempSize.indexOf(Integer.parseInt(bTemp[1]))+1)), Integer.parseInt(bTemp[3]), FilesID);
			    	totalSize += b.getSize();
					blocks.add(b);		
					inputSize += 20 + 4*b.getNumOfFiles();
			    }// End if(B)
			    
			    if(st.charAt(0)=='D') {	//	Finish read file
			    	break;	
			    }// End if(D)
			    
			    if(st.charAt(0)=='#') {	//	Info to export file
			    	output.add(st);
			    }// End if(#)
			}// End while
		} catch (IOException e) {
			e.printStackTrace();
		}// End try
        
        Collections.sort(files, new Comparator<file>() {	// Sort file array by Sn
            public int compare(file f1, file f2) {
            	
         	    int sn1 = f1.getSn();
         	    int sn2 = f2.getSn();

         	    return sn1-sn2;
            }
        });
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

	public long getTotalSize() {
		return totalSize;
	}

	public int getInputSize() {
		return inputSize;
	}
	
}
