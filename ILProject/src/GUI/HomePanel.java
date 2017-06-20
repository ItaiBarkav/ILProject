package GUI;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import Objects.block;
import Objects.file;
import Solver.Solver;

public class HomePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JTextField filePath;				//	Show the path of the file to analysis.
	private String fileName = null;				//  Keep the path to the file to analysis.
	private JTextField numK;					//	The field of number that the user enter as constraint. 
	private JButton btnStart;					//	Start button.
	private static Solver solve;				//	Solver object to solve the ILP problem by CPLEX.
	private ArrayList<file> files;				// 	Array that represent the files in the file system.
	private ArrayList<block> blocks;			//  Array that represent the blocks in the file system.
	private ArrayList<String> output;			//  Save the relevant information about the file system (to the export file).
	private String objective;					//	The objective that the user select.
	private String subject;						//	The subject that the user select by the objective.
	private double time;						// 	Saving the execution time.
	private double timeInput;					//  Saving the time of creating the input (files&blocks arrays).
	private int totalFreeSpace;					//  Keep the size of the total free space after the analysis.
	private int numOfFiles = 0;					//  Keep the number of the files for deletion after the analysis.
	private int numOfBlocks = 0;				//  Keep the number of the blocks for deletion after the analysis.
	private ArrayList<Integer> deleteFile;		//  Array that save the files for deletion after the analysis.
	private ArrayList<Integer> deleteBlock;		//  Array that save the blocks for deletion after the analysis.
	private int inputSize;						//  Save the size of the files&blocks arrays.
	private int deletedB = 0;					//  Keep the number of the blocks for deletion after the analysis that the solver doesn't delete them.
	private int sizeB = 0;						//  Keep the size of the total free space after the analysis that the solver doesn't delete them.
	private boolean obj;						//	Indicate which objective selected by the user Minimize (true) Maximize (false).
	
	/**
	 * Create the panel.
	 */
	public HomePanel() {
		panel = new JPanel();
		panel.setLayout(null);
		JLabel lblAutomatedFileDeletion = new JLabel("Automated File Deletion in Storage Systems ");
		lblAutomatedFileDeletion.setBounds(119, 11, 212, 14);
		panel.add(lblAutomatedFileDeletion);
		
		JLabel lblWithDeduplicationUsing = new JLabel("with Deduplication using ILP solver");
		lblWithDeduplicationUsing.setBounds(142, 30, 165, 14);
		panel.add(lblWithDeduplicationUsing);
		
		filePath = new JTextField();
		filePath.setBounds(119, 67, 212, 20);
		panel.add(filePath);
		filePath.setColumns(10);
				
		Button btnChooseFile = new Button("Browse");
		btnChooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT file", "txt");
				fc.setFileFilter(filter);
				if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					filePath.setText(fc.getSelectedFile().toString());
					fileName = fc.getSelectedFile().toString();
				}
			}
		});
		btnChooseFile.setBounds(337, 68, 53, 19);
		panel.add(btnChooseFile);
		
		JLabel lblSelectObjective = new JLabel("Select Objective:");
		lblSelectObjective.setBounds(31, 114, 82, 14);
		panel.add(lblSelectObjective);
		
		JRadioButton rdbtnMinimize = new JRadioButton("Minimize   \u03A3 file");
		rdbtnMinimize.setBounds(119, 110, 97, 23);
		panel.add(rdbtnMinimize);
		
		JRadioButton rdbtnMaximize = new JRadioButton("Maximize  \u03A3 block.size");
		rdbtnMaximize.setBounds(119, 136, 129, 23);
		panel.add(rdbtnMaximize);
		
		JLabel lbMax = new JLabel("Select the maximum number of file to deletion:");
		lbMax.setBounds(35, 177, 222, 14);
		panel.add(lbMax);
		lbMax.setVisible(false);
		
		JLabel lbMin = new JLabel("Select the minimum number of space to free:");
		lbMin.setBounds(35, 177, 214, 14);
		panel.add(lbMin);
		lbMin.setVisible(false);
		
		numK = new JTextField();
		numK.setBounds(259, 174, 86, 20);
		panel.add(numK);
		numK.setColumns(10);
		numK.setVisible(false);
		
		JLabel lbMax2 = new JLabel("\u2265   \u03A3 file");
		lbMax2.setBounds(355, 177, 40, 14);
		panel.add(lbMax2);
		lbMax2.setVisible(false);
		
		JLabel lbMin2 = new JLabel("\u2264   \u03A3 block.size");
		lbMin2.setBounds(355, 177, 72, 14);
		panel.add(lbMin2);
		lbMin2.setVisible(false);

		btnStart = new JButton("Start");
		btnStart.setBounds(196, 254, 57, 23);
		panel.add(btnStart);
		btnStart.setEnabled(false);
		
		JButton btnHelp = new JButton("Help");
		btnHelp.setBounds(387, 7, 53, 23);
		panel.add(btnHelp);
		
		//	Help window
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(panel,
						"                                                                   Using our System\n\n"
					    + "Welcome to our Software Engineering Final Project 2017 - Itai & Omer.\n\n"
					    + "In order to upload file for analysis click on 'Browse' and select a .txt file.\n"
					    + "You can select one from the two objective options:\n\n"
					    + "     1. Minimize   \u03A3 file - In this option you need to select the minimum amount of space to free.\n"
					    + "		         The system will try to minimize the number of files for deletion, considering the number you entered.\n"
					    + "         Note : The minimum amount of space to free can be entered in percentages (by adding '%' in the\n" 
					    + "                     end of the number).\n\n"
					    + "     2. Maximize  \u03A3 block.size - In this option you need to select the maximum number of files to delete.\n"
					    + "		         The system will try to maximize the amount of space to free, considering the number you entered.\n"
					    + "         Note : The maximum number of files to delete can be entered in percentages (by adding '%' in the\n" 
					    + "                     end of the number).\n\n"
					    + "Click on the 'Start' to run the file analysis on the given input.",
					    "Help Information",
					    JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		//	Maximize objective 
		rdbtnMaximize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnMinimize.setSelected(false);
				lbMin.setVisible(false);
				lbMin2.setVisible(false);
				lbMax.setVisible(true);
				lbMax2.setVisible(true);
				numK.setVisible(true);
				objective = rdbtnMaximize.getText();
				subject = lbMax2.getText();
				obj = false;
			}
		});
		
		//	Minimize objective
		rdbtnMinimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnMaximize.setSelected(false);
				lbMax.setVisible(false);
				lbMax2.setVisible(false);
				lbMin.setVisible(true);
				lbMin2.setVisible(true);
				numK.setVisible(true);
				objective = rdbtnMinimize.getText();
				subject = lbMin2.getText();
				obj = true;
			}
		});
			
		numK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(numK.isValidateRoot()){
					btnStart.setEnabled(true);
				}
			}
		});
		
		numK.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent e) {
				changed();
			}
			public void insertUpdate(DocumentEvent e) {
				changed();
			}
			public void changedUpdate(DocumentEvent e) {
				changed();
			}
			
			public void changed() {
				if(numK.getText().equals("") || fileName == null)
					btnStart.setEnabled(false);
				else
					btnStart.setEnabled(true);
			}
		});
				
	}
	
	//	This function start the file analysis process
	public void startSolve() {
		solve.solve(fileName, numK.getText(), obj);
		files = new ArrayList<file>(solve.getFiles());
		blocks = new ArrayList<block>(solve.getBlocks());
		output = new ArrayList<String>(solve.getOutput());
		time = solve.getTime();
		timeInput = solve.getTimeInput();
		totalFreeSpace = solve.getTotalFreeSpace();
		numOfFiles = solve.getNumOfFiles();
		numOfBlocks = solve.getNumOfBlocks();
		deleteFile = new ArrayList<Integer>(solve.getDeleteFile());
		deleteBlock = new ArrayList<Integer>(solve.getDeleteBlock());
		inputSize = solve.getInputSize();
		deletedB = solve.getDeletedB();
		sizeB = solve.getSizeB();
	}
	
	public JPanel getPanel() {
		return panel;
	}

	public JButton getBtnStart() {
		return btnStart;
	}

	public static void setSolve(Solver solve) {
		HomePanel.solve = solve;
	}

	public Solver getSolve() {
		return solve;
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

	public String getObjective() {
		return objective;
	}
	
	public String getNumK() {
		return numK.getText();
	}

	public String getSubject() {
		return subject;
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
