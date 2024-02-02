package spiel.logic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import spiel.logic.*;

public class Logic {
	
	public static int ONE_DIMENSIONAL = 1;
	public static int TWO_DIMENSIONAL = 2;
	
	private Random Gen;
	private ArrayList<GameListener> Listeners;
	private ArrayList<Point> Shoots;
	private ArrayList<Point> Hits;
	
	private int Gegner;
	private int x;
	private int y;
	private boolean[][] Feld;
	
	public Logic(int Felder, int Gegner, int D) throws Exception {
		if (D == ONE_DIMENSIONAL) {
			Feld = new boolean[1][Felder];
			y = 1;
			x = Felder;
		}
		else if (D == TWO_DIMENSIONAL) {
			Feld = new boolean[Felder][Felder];
			x = y = Felder;
		}
		else throw new Exception();
		
		Gen = new Random();
		Listeners = new ArrayList<>();
		Shoots = new ArrayList<>();
	    Hits = new ArrayList<>();
		
		this.Gegner = Gegner;
		initFeld();
	}
	
	private void initFeld() {
		int amountFelder = y*x;
		int[] GegnerPosition = new int[Gegner];
		int pos = 0;

		while (hasDuplicate(GegnerPosition)){
			for (int i = 0; i < Gegner; i++){
				GegnerPosition[i] = Gen.nextInt(1, amountFelder);
			}
		}

		Arrays.sort(GegnerPosition);

		int countFeld = 1;
		int GegnerCount = 1;
		for (int i = 0; i < y; i++) {
			for (int ii = 0; ii < x; ii++) {
				if (countFeld == GegnerPosition[pos]) {
					if (GegnerCount == Gegner) continue;
					Feld[i][ii] = true;
					pos ++;
					GegnerCount ++;
				} else {
					Feld[i][ii] = false;
				}
				countFeld ++;
			}
		}

		if (amountTrue(Feld) != Gegner) initFeld();
	}

	private int amountTrue(boolean[][] array){
		int count = 0;
		for (boolean[] b_ : array){
			for (boolean b : b_){
				if (b) count ++;
			}
		}
		return count;
	}

	private void shotListener(int kind) {
		for (GameListener lis : Listeners) {
			lis.shot(kind);
		}
	}
	
	private void GameOver() {
		for (GameListener lis : Listeners) {
			lis.gameOver();
		}
	}

	private boolean hasDuplicate(int[] array) {
		if (array.length == 0) return true;
		for (int i = 0; i < array.length - 1; i++) {
			for (int j = i + 1; j < array.length; j++) {
				if (array[i] == array[j]) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	public void shoot(int x, int y) {
		if (x <= 0 || y <= 0) {shotListener(GameListener.SHOOT_INVALID); return;}
		if (x > Feld.length || y > Feld[0].length) {shotListener(GameListener.SHOOT_INVALID); return;}
		if (Shoots.contains(new Point(x-1,y-1))) {shotListener(GameListener.SHOOT_ALREADY_SHOT); return;}
		
		x -= 1;
		y -= 1;
		
		Shoots.add(new Point(x,y));
		if (Feld[x][y]) {
			Hits.add(new Point(x,y));
			Gegner -= 1;
			shotListener(GameListener.SHOOT_HIT);
			if (Gegner == 0) GameOver();
		} else {
			shotListener(GameListener.SHOOT_MISS);
		}
	}
	
	public void setFeldRect(int x, int y) {
		if (x <= 0 || y <= 0) return;
		if (x == 1 && y == 1) return;
		Feld = new boolean[y][x];
		this.x = x;
		this.y = y;
		initFeld();
	}
	
	public void setGegner(int anzahl) {
		if (anzahl <= 0) return;
		Gegner = anzahl;
		initFeld();
	}
	
	public void addListener(GameListener lis) {
		Listeners.add(lis);
	}
	
	public int getGegner(){
		return Gegner;
	}
	
	public boolean[][] getFeld(){
		return Feld;
	}
	
	public ArrayList<Point> getShoots(){
		return Shoots;
	}
	
	public ArrayList<Point> getHits(){
		return Hits;
	}
	
	public Point getRecentShoot() {
		return Shoots.get(Shoots.size()-1);
	}
	
	public Point getRecentHit() {
		return Hits.get(Hits.size()-1);
	}

	public Point getShoot(int index) {
		return Shoots.get(index);
	}
	
	public Point getHit(int index) {
		return Hits.get(index);
	}
	
}
