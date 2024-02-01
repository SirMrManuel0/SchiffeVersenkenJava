package spiel.ui;

import java.util.Scanner;

import spiel.logic.*;

public class OneDSpiel implements GameListener{
	
	private Logic logic;
	private Scanner Read;
	private boolean running;
	
	public OneDSpiel() {
		try {
			logic = new Logic(10, 3, Logic.ONE_DIMENSIONAL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Read = new Scanner(System.in);
		running = true;
		logic.addListener(this);
	}
	
	private int readInput() {
		
		String line = Read.nextLine();
		int returne = 0;
		try {
			returne = Integer.parseInt(line);
			if (returne <= 0) throw new NumberFormatException();
		} catch (NumberFormatException e) {
			System.out.println("Ungültige Eingabe!");
		}
		
		return returne;
	}
	
	public void run() {
		System.out.println("******************************************");
	 	System.out.println("Schiffe Versenken mit");
	 	System.out.println("10 Feldern und");
	 	System.out.println("3 Gegnern");
	 	System.out.println("******************************************");
	 	while(running) {
	 		System.out.println();
	 		System.out.println("Dein nächster Tipp: ");
	 		int eingabe = readInput();
	 		logic.shoot(1, eingabe);
	 	}
	 	System.out.println();
	 	System.out.println("Du hast " + logic.getShoots().size() + " Schüsse benötigt, um"
	 			+ " 3 Genger zu treffen!");
	 	System.out.println("Und so sah das Spielfeld aus:");
	 	for (boolean bool : logic.getFeld()[0]) {
	 		if (bool) System.out.print("X");
	 		else System.out.print("O");
	 		System.out.print(" ");
	 	}
	}

	@Override
	public void gameOver() {
		running = false;
	}

	@Override
	public void shot(int kind) {
		switch(kind) {
		case GameListener.SHOOT_ALREADY_SHOT:
			System.out.println("Da hast du schon hingeschossen!");
			break;
		case GameListener.SHOOT_HIT:
			System.out.println("Treffer!");
			break;
		case GameListener.SHOOT_MISS:
			System.out.println("Daneben!");
			break;
		default:
			System.out.println("Bitte gebe eine Zahl zwischen 1 und 10 ein!");
		}
	}

}
