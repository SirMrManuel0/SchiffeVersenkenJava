package spiel.logic;

public interface GameListener {
	public static int SHOOT_MISS = 0;
	public static int SHOOT_HIT = 1;
	public static int SHOOT_ALREADY_SHOT = 2;
	public static int SHOOT_INVALID = 3;
	public void gameOver();
	public void shot(int kind);
}
