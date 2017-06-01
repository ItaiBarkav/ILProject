import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ResultPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private ArrayList<file> files;
	private ArrayList<block> blocks;
	private ArrayList<String> output;
	private JTable fileTable;
	private JTable blockTable;
	private ArrayList<Integer> deleteFile;
	private ArrayList<Integer> deleteBlock;

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
		
		JLabel lblTime2 = new JLabel("Time:");
		lblTime2.setBounds(128, 120, 26, 14);
		panel.add(lblTime2);
		
		JLabel lblRam2 = new JLabel("RAM:");
		lblRam2.setBounds(128, 145, 26, 14);
		panel.add(lblRam2);
		
		JLabel lblTotalFreeSpace2 = new JLabel("Total free space:");
		lblTotalFreeSpace2.setBounds(72, 170, 82, 14);
		panel.add(lblTotalFreeSpace2);
		
		JLabel lblNumberOfFiles2 = new JLabel("Number of  files for deletion:");
		lblNumberOfFiles2.setBounds(17, 195, 137, 14);
		panel.add(lblNumberOfFiles2);
		
		JLabel lblNumberOfBlocks2 = new JLabel("Number of blocks for deletion:");
		lblNumberOfBlocks2.setBounds(10, 220, 144, 14);
		panel.add(lblNumberOfBlocks2);
		
		JButton btnExport = new JButton("Export");
		btnExport.setBounds(65, 267, 89, 23);
		panel.add(btnExport);
		
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
		
		JLabel lblRam = new JLabel("");
		lblRam.setBounds(164, 145, 82, 14);
		panel.add(lblRam);
		
		JLabel lblTotalFreeSpace = new JLabel("");
		lblTotalFreeSpace.setBounds(164, 170, 82, 14);
		panel.add(lblTotalFreeSpace);
		
		JLabel lblNumFiles = new JLabel("");
		lblNumFiles.setBounds(164, 195, 82, 14);
		panel.add(lblNumFiles);
		
		JLabel lblNumblocks = new JLabel("");
		lblNumblocks.setBounds(164, 220, 82, 14);
		panel.add(lblNumblocks);
		
		
		lblFileName.setText(homePanel.getFileName());
		lblObjective.setText(homePanel.getObjective());
		lblSubject.setText(homePanel.getNumK() + "  " + homePanel.getSubject());
		lblTime.setText(String.valueOf(homePanel.getTime()));
		lblTotalFreeSpace.setText(String.valueOf(homePanel.getTotalFreeSpace()));
		lblNumFiles.setText(String.valueOf(homePanel.getNumOfFiles()));
		lblNumblocks.setText(String.valueOf(homePanel.getNumOfBlocks()));
		
		files = new ArrayList<file>(homePanel.getFiles());
		blocks = new ArrayList<block>(homePanel.getBlocks());
		output = new ArrayList<String>(homePanel.getOutput());
		deleteFile = new ArrayList<Integer>(homePanel.getDeleteFile());
		deleteBlock = new ArrayList<Integer>(homePanel.getDeleteBlock());
		
		String[] col = {"SN", "ID", "Size"};
		String[][] fileData = new String[deleteFile.size()][3];
		for(int i=0; i<deleteFile.size(); i++) {
			fileData[i][0] = String.valueOf(files.get(deleteFile.get(i)).Sn);
			fileData[i][1] = files.get(deleteFile.get(i)).Id;
			fileData[i][2] = String.valueOf(files.get(deleteFile.get(i)).size);
		}
		String[][] blockData = new String[deleteBlock.size()][3];
		for(int i=0; i<deleteBlock.size(); i++) {
			blockData[i][0] = String.valueOf(blocks.get(deleteBlock.get(i)).Sn);
			blockData[i][1] = blocks.get(deleteBlock.get(i)).Id;
			blockData[i][2] = String.valueOf(blocks.get(deleteBlock.get(i)).size);
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
		jspFile.setSize(99, 145);
		jspFile.setLocation(339, 145);
		panel.add(jspFile);
		jspFile.setVisible(false);
		
		JScrollPane jspBlock = new JScrollPane(blockTable);
		jspBlock.setEnabled(false);
		jspBlock.setSize(99, 145);
		jspBlock.setLocation(339, 145);
		panel.add(jspBlock);
		jspBlock.setVisible(false);
		
		rdbtnFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnBlocks.setSelected(false);
				jspBlock.setVisible(false);
				jspFile.setVisible(true);
				
			}
		});
		
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
