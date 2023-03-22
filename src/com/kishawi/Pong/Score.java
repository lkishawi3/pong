package com.kishawi.pong;

public class Score {
	private int player1Score = 0;
	private int player2Score = 0;
	private static final int MAX_SCORE = 7;
	
	public void incrementPlayer1Score() {
		player1Score++;
		checkScore();
	}
	
	public void incrementPlayer2Score() {
		player2Score++;
		checkScore();
	}
	
	private void checkScore() {
		if (player1Score == MAX_SCORE || player2Score == MAX_SCORE) {
			reset();
		}
	}
	
	public void reset() {
		player1Score = 0;
		player2Score = 0;
	}
	
	public int getPlayer1Score() {
		return player1Score;
	}
	
	public int getPlayer2Score() {
		return player2Score;
	}
}
