package spiel.gui;

import java.awt.Color;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

import spiel.gui.Point.*;
import spiel.logic.*;

public class Main extends JFrame implements GameListener, ActionListener, PointListener {

	private final int FELD_SIZE = 60;

	private int intSizeX = 10;
	private int intSizeY = 1;
	private int gegner = 3;
	
	private Logic logic;
	
	private JTextField Gegner;
	private JTextField SizeX;
	private JTextField SizeY;
	private JPanel FeldPanel;
	private PointPanel lastShoot;
	
	
	
	public Main() {
		try {
			logic = new Logic(intSizeX, gegner, Logic.ONE_DIMENSIONAL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logic.addListener(this);
		setTitle("Schiffe Versenken");
		setSize(new Dimension(620,710));
		setResizable(false);
		initComp();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	public Main(int SizeX, int SizeY, int Gegner) {
		try {
			logic = new Logic(intSizeX, gegner, Logic.ONE_DIMENSIONAL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logic.setFeldRect(SizeX, SizeY);
		logic.setGegner(Gegner);
		logic.addListener(this);
		setTitle("Schiffe Versenken");
		setSize(new Dimension(620,710));
		setResizable(false);
		initComp();

		this.Gegner.setText(String.valueOf(Gegner));
		this.SizeX.setText(String.valueOf(SizeX));
		this.SizeY.setText(String.valueOf(SizeY));

		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	private void initComp() {
		Gegner = new JTextField("3");
		SizeX = new JTextField("10");
		SizeY = new JTextField("1");
		JButton submit = new JButton("Bestätigen");
		submit.addActionListener(this);
		
		JLabel GegnerLab = new JLabel("Gegner (max. Anzahl an Felder durch 2): ");
		JLabel SizeXLab = new JLabel("Höhe (max. 10): ");
		JLabel SizeYLab = new JLabel("Breite (max. 10): ");
		JLabel submitLab = new JLabel("");
		JPanel header = new JPanel();
		//header.setBackground(Color.blue);
		header.setBounds(0,0,620,60);
		header.setLayout(new GridLayout(4,2));
		header.add(GegnerLab);
		header.add(Gegner);
		header.add(SizeXLab);
		header.add(SizeX);
		header.add(SizeYLab);
		header.add(SizeY);
		header.add(submitLab);
		header.add(submit);
		
		
		
		FeldPanel = new JPanel();
		//test.setBackground(Color.black);

		initFeld();
		add(header);
		add(FeldPanel);
	}
	
	private void initFeld() {
		boolean[][] Feld = logic.getFeld();
		FeldPanel.setLayout(null);
		FeldPanel.setSize(new Dimension((FELD_SIZE+2) * Feld[0].length, FELD_SIZE * Feld.length));
		FeldPanel.removeAll();
		for (int i = 0; i < Feld.length; i++){
			for (int j = 0; j < Feld[i].length; j ++){
				PointPanel Click = new PointPanel();
				Click.setPoint(i+1,j+1);
				Click.addListener(this);
				Click.setBounds(FELD_SIZE * j, (FELD_SIZE * i) + 60, FELD_SIZE, FELD_SIZE);
				FeldPanel.add(Click);
			}
		}
	}

	private int safeParseInt(String text, int def) {
		try {
			return Integer.parseInt(text);
		} catch (NumberFormatException e) {}
		return def;	
	}
	
	
	@Override
	public void gameOver() {
		Object[] message = new Object[]{
			"Du hast Gewonnen!",
			"Du hast " + logic.getHits().size() + " Gegner mit " + logic.getShoots().size() + " Schüssen getroffen!",
			"Willst du noch eine Runde spielen?"
		};
		int option = JOptionPane.showConfirmDialog(this, message, "Game Over", JOptionPane.YES_NO_OPTION);
		if (option == JOptionPane.YES_OPTION){
			dispose();
			new Main(intSizeX, intSizeY, gegner);
		}
		if (option == JOptionPane.NO_OPTION) dispose();
	}

	@Override
	public void shot(int kind) {
		switch(kind){
			case GameListener.SHOOT_HIT: {
				lastShoot.hit();
				break;
			}
			case GameListener.SHOOT_MISS: {
				lastShoot.miss();
				break;
			}
			case GameListener.SHOOT_INVALID: {
				lastShoot.setBackground(Color.cyan);
				break;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String GegnerStr = Gegner.getText();
		String SizeXText = SizeX.getText();
		String SizeYText = SizeY.getText();
		
		gegner =  safeParseInt(GegnerStr, gegner);
		intSizeX = safeParseInt(SizeXText, intSizeX);
		intSizeY = safeParseInt(SizeYText, intSizeY);

		if (intSizeX > 10) intSizeX = 10;
		if (intSizeY > 10) intSizeY = 10;
		if (gegner > (intSizeX*intSizeY)) gegner = (intSizeX * intSizeY) / 2;

		dispose();
		new Main(intSizeX, intSizeY, gegner);
	}


	@Override
	public void PointEvent(PointEvent p) {
		lastShoot = p.getSource();
		logic.shoot((int) p.getX(), (int) p.getY());
	}
}
