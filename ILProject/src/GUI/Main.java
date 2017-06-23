package GUI;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import Solver.Solver;

public class Main {
	private JFrame frame = new JFrame("Software Engineering Final Project 2017 - Itai & Omer");
	private JPanel panelCont = new JPanel();
	private HomePanel homePanel = new HomePanel();
	private ResultPanel resultPanel;
	private CardLayout cl = new CardLayout();
	private static Solver solve = new Solver();
	
	public Main() {
		HomePanel.setSolve(solve);
		panelCont.setLayout(cl);
		panelCont.add(homePanel.getPanel(), "1");
		cl.show(panelCont, "1");
		
		homePanel.getBtnStart().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				homePanel.startSolve();
				resultPanel = new ResultPanel(homePanel);
				panelCont.add(resultPanel.getPanel(), "2");
				cl.show(panelCont, "2");
			}
		});
		
		frame.setResizable(false);
		frame.getContentPane().add(panelCont);
		frame.setPreferredSize(new Dimension(450, 330));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
	    frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
		
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {			
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					new Main();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}