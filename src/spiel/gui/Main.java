package spiel.gui;

import java.awt.Color;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;

import spiel.logic.*;

public class Main extends JFrame implements GameListener, ActionListener{

	private static int DEFAULT_SIZE_X = 10;
	private static int DEFAULT_SIZE_Y = 1;
	private static int GEGNER = 3;
	
	private Logic logic;
	
	private JTextField Gegner;
	private JTextField SizeX;
	private JTextField SizeY;
	private JButton Submit;
	private JPanel FeldPanel;
	private JPanel[][] Felder;
	
	
	
	public Main() {
		try {
			logic = new Logic(DEFAULT_SIZE_X, GEGNER, Logic.ONE_DIMENSIONAL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logic.addListener(this);
		setTitle("Schiffe Versenken");
		setSize(new Dimension(500,500));
		setResizable(false);
		initComp();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void initComp() {
		Gegner = new JTextField("3");
		SizeX = new JTextField("10");
		SizeY = new JTextField("1");
		Submit = new JButton("Bestätigen");
		Submit.addActionListener(this);
		
		JLabel GegnerLab = new JLabel("Gegner: ");
		JLabel SizeXLab = new JLabel("SizeX: ");
		JLabel SizeYLab = new JLabel("SizeY: ");
		JLabel submitLab = new JLabel("Ändere: ");
		JPanel header = new JPanel();
		//header.setBackground(Color.blue);
		header.setBounds(0,0,500,60);
		header.setLayout(new GridLayout(4,2));
		header.add(GegnerLab);
		header.add(Gegner);
		header.add(SizeXLab);
		header.add(SizeX);
		header.add(SizeYLab);
		header.add(SizeY);
		header.add(submitLab);
		header.add(Submit);
		
		
		
		FeldPanel = new JPanel();
		//test.setBackground(Color.black);
		FeldPanel.setBounds(0,20,500,440);
		
		reloadFeld();
		
		add(header);
		add(FeldPanel);
	}
	
	private void reloadFeld() {
		Felder = new JPanel[DEFAULT_SIZE_X][DEFAULT_SIZE_Y];
	}
	
	private int safeGetInt(String text, int def) {
		try {
			return Integer.parseInt(text);
		} catch (NumberFormatException e) {}
		return def;	
	}
	
	
	@Override
	public void gameOver() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shot(int kind) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String GegnerStr = Gegner.getText();
		String SizeXText = SizeX.getText();
		String SizeYText = SizeY.getText();
		
		int GegnerAnzahl =  safeGetInt(GegnerStr, GEGNER);
		int SizeXInt = safeGetInt(SizeXText, DEFAULT_SIZE_X);
		int SizeYInt = safeGetInt(SizeYText, DEFAULT_SIZE_Y);
		
		logic.setFeldRect(SizeXInt, SizeYInt);
		logic.setGegner(GegnerAnzahl);
		reloadFeld();
	}
	
	
	
	
	
	
	
}
