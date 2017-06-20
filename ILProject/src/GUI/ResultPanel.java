package GUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Objects.block;
import Objects.file;

public class ResultPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private ArrayList<file> files;				// 	Array that represent the files in the file system.
	private ArrayList<block> blocks;			//  Array that represent the blocks in the file system.
	private ArrayList<String> output;			//  Save the relevant information about the file system (to the export file).
	private JTable fileTable;					//	Table for the files to deletion.
	private JTable blockTable;					//	Table for the blocks to deletion.
	private ArrayList<Integer> deleteFile;		//  Array that save the files for deletion after the analysis.
	private ArrayList<Integer> deleteBlock;		//  Array that save the blocks for deletion after the analysis.

	/**
	 * Create the panel.
	 */
	public ResultPanel(HomePanel homePanel) {
		panel = new JPanel();
		panel.setLayout(null);
		
		JLabel lblResults = new JLabel("Results");
		lblResults.setBounds(206, 11, 35, 14);
		panel.add(lblResults);
		
		JLabel lblTextFile2 = new JLabel("Text file:");
		lblTextFile2.setBounds(10, 40, 46, 14);
		panel.add(lblTextFile2);
		
		JLabel lblObjective2 = new JLabel("Objective:");
		lblObjective2.setBounds(10, 65, 50, 14);
		panel.add(lblObjective2);
		
		JLabel lblSubject2 = new JLabel("Subject:");
		lblSubject2.setBounds(206, 65, 40, 14);
		panel.add(lblSubject2);
		
		JLabel lblTime2 = new JLabel("Time (sec):");
		lblTime2.setBounds(99, 120, 55, 14);
		panel.add(lblTime2);
		
		JLabel lblTotalFreeSpace2 = new JLabel("Total free space (bytes):");
		lblTotalFreeSpace2.setBounds(34, 145, 120, 14);
		panel.add(lblTotalFreeSpace2);
		
		JLabel lblNumberOfFiles2 = new JLabel("Number of  files for deletion:");
		lblNumberOfFiles2.setBounds(17, 170, 137, 14);
		panel.add(lblNumberOfFiles2);
		
		JLabel lblNumberOfBlocks2 = new JLabel("Number of blocks for deletion:");
		lblNumberOfBlocks2.setBounds(10, 195, 144, 14);
		panel.add(lblNumberOfBlocks2);
		
		JButton btnExport = new JButton("Export");
		btnExport.setBounds(65, 267, 89, 23);
		panel.add(btnExport);
		
		JButton btnHelp = new JButton("Help");
		btnHelp.setBounds(387, 7, 53, 23);
		panel.add(btnHelp);
		
		//	Help window
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(panel,
						"                                                                   Using our System - Results\n\n"
					    + "In this window you can see the all information after the file analysis has done.\n\n"
					    + "The running time and the achieved total free space are shown, and also\n"
					    + "the numbers of files and blocks that are candidates to be deleted.\n\n"
					    + "Click on the files button in order to get the list of these files that are recommend to be deleted.\n"
					    + "In a similar way, click on the blocks button in order to get the list of these blocks that are recommend to be deleted.\n\n"
					    + "You can get an extra information by clicking the 'Export' button.\n"
					    + "A new text file contains this information will be create and store in the same path of the input file.",
					    "Help Information",
					    JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		//	Export to file
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				File exportFile = new File(homePanel.getFileName().substring(0, homePanel.getFileName().length()-4)+"_Result.txt");
				
				try {
					
					if(exportFile.createNewFile()) {
						BufferedWriter writer = new BufferedWriter(new FileWriter(exportFile));
						for(int i=0; i<output.size(); i++) {
							writer.write(output.get(i));
							writer.newLine();
						}
						writer.write("#K: " + homePanel.getNumK());
						writer.newLine();
						writer.write("#Processing time: " + homePanel.getTimeInput());
						writer.newLine();
						writer.write("#Size of input: " + homePanel.getInputSize());
						writer.newLine();
						writer.write("#Execution time: " + homePanel.getTime());
						writer.newLine();
						writer.write("#Number of files marked for deletion by solver: " + homePanel.getNumOfFiles());
						writer.newLine();
						writer.write("#Number of blocks marked for deletion by solver: " + homePanel.getNumOfBlocks());
						writer.newLine();
						writer.write("#Number of deleted blocks: " + (homePanel.getNumOfBlocks()+homePanel.getDeletedB()));
						writer.newLine();
						writer.write("#Size in GBs of physical files marked for deletion by solver: " + homePanel.getTotalFreeSpace()/1000000000.0);
						writer.newLine();
						writer.write("#Size in GBs of physical files deleted: " + (homePanel.getTotalFreeSpace()+homePanel.getSizeB())/1000000000.0);
						writer.newLine();
						
			            writer.close();
			            JOptionPane.showMessageDialog(null, "File created !");
				      }
					else
						JOptionPane.showMessageDialog(null, "File already exists. \nPlease change the name of current file.");	
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JLabel lblListOf = new JLabel("List of:");
		lblListOf.setBounds(298, 120, 35, 14);
		panel.add(lblListOf);
		
		JRadioButton rdbtnFiles = new JRadioButton("Files");
		rdbtnFiles.setBounds(339, 116, 46, 23);
		panel.add(rdbtnFiles);
		
		JRadioButton rdbtnBlocks = new JRadioButton("Blocks");
		rdbtnBlocks.setBounds(387, 116, 55, 23);
		panel.add(rdbtnBlocks);
				
		JLabel lblFileName = new JLabel("");
		lblFileName.setBounds(66, 40, 372, 14);
		panel.add(lblFileName);
		
		JLabel lblObjective = new JLabel("");
		lblObjective.setBounds(70, 65, 126, 14);
		panel.add(lblObjective);
		
		JLabel lblSubject = new JLabel("");
		lblSubject.setBounds(256, 65, 182, 14);
		panel.add(lblSubject);
		
		JLabel lblTime = new JLabel("");
		lblTime.setBounds(164, 120, 82, 14);
		panel.add(lblTime);
		
		JLabel lblTotalFreeSpace = new JLabel("");
		lblTotalFreeSpace.setBounds(164, 145, 82, 14);
		panel.add(lblTotalFreeSpace);
		
		JLabel lblNumFiles = new JLabel("");
		lblNumFiles.setBounds(164, 170, 82, 14);
		panel.add(lblNumFiles);
		
		JLabel lblNumblocks = new JLabel("");
		lblNumblocks.setBounds(164, 195, 82, 14);
		panel.add(lblNumblocks);
		
		
		lblFileName.setText(homePanel.getFileName());
		lblObjective.setText(homePanel.getObjective());
		lblSubject.setText(homePanel.getNumK() + "  " + homePanel.getSubject());
		lblTime.setText(String.valueOf((homePanel.getTime()+homePanel.getTimeInput())));
		lblTotalFreeSpace.setText(String.valueOf(homePanel.getTotalFreeSpace()));
		lblNumFiles.setText(String.valueOf(homePanel.getNumOfFiles()));
		lblNumblocks.setText(String.valueOf(homePanel.getNumOfBlocks()));
		
		files = new ArrayList<file>(homePanel.getFiles());
		blocks = new ArrayList<block>(homePanel.getBlocks());
		output = new ArrayList<String>(homePanel.getOutput());
		deleteFile = new ArrayList<Integer>(homePanel.getDeleteFile());
		deleteBlock = new ArrayList<Integer>(homePanel.getDeleteBlock());
		
		//	Data for the files&blocks tables
		String[] col = {"SN", "ID", "Size"};
		String[][] fileData = new String[deleteFile.size()][3];
		for(int i=0; i<deleteFile.size(); i++) {
			fileData[i][0] = String.valueOf(files.get(deleteFile.get(i)).getSn());
			fileData[i][1] = files.get(deleteFile.get(i)).getId();
			fileData[i][2] = String.valueOf(files.get(deleteFile.get(i)).getSize());
		}
		String[][] blockData = new String[deleteBlock.size()][3];
		for(int i=0; i<deleteBlock.size(); i++) {
			blockData[i][0] = String.valueOf(blocks.get(deleteBlock.get(i)).getSn());
			blockData[i][1] = blocks.get(deleteBlock.get(i)).getId();
			blockData[i][2] = String.valueOf(blocks.get(deleteBlock.get(i)).getSize());
		}
		
		fileTable = new JTable(fileData, col);
		fileTable.setEnabled(false);
		fileTable.setRowSelectionAllowed(false);
		fileTable.setFillsViewportHeight(true);
		fileTable.setBounds(339, 145, 99, 145);
		
		blockTable = new JTable(blockData, col);
		blockTable.setRowSelectionAllowed(false);
		blockTable.setEnabled(false);
		blockTable.setFillsViewportHeight(true);
		blockTable.setBounds(339, 145, 99, 145);
		
		JScrollPane jspFile = new JScrollPane(fileTable);
		jspFile.setEnabled(false);
		jspFile.setSize(140, 145);
		jspFile.setLocation(298, 145);
		panel.add(jspFile);
		jspFile.setVisible(false);
		
		JScrollPane jspBlock = new JScrollPane(blockTable);
		jspBlock.setEnabled(false);
		jspBlock.setSize(140, 145);
		jspBlock.setLocation(298, 145);
		panel.add(jspBlock);
		jspBlock.setVisible(false);
		
		//	Show files table
		rdbtnFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnBlocks.setSelected(false);
				jspBlock.setVisible(false);
				jspFile.setVisible(true);
				
			}
		});
		
		//	Show blocks table
		rdbtnBlocks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnFiles.setSelected(false);
				jspFile.setVisible(false);
				jspBlock.setVisible(true);
			}
		});
	}

	public JPanel getPanel() {
		return panel;
	}
}
