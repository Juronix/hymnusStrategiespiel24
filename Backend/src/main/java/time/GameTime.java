package time;

import java.io.Serializable;


public class GameTime implements Serializable{

	public GameTime() {
	}

	private static final long serialVersionUID = 1L;

	private int minutesPlayed = 0;
	private double gameSpeed = 1;

	public int getMinutesPlayed() {
		return minutesPlayed;
	}

	public void setMinutesPlayed(final int minutesPlayed) {
		this.minutesPlayed = minutesPlayed;
	}

	public double getGameSpeed() {
		return gameSpeed;
	}

	public void setGameSpeed(final float gameSpeed) {
		this.gameSpeed = gameSpeed;
	}

	public void addAPlayedMinute() {
		minutesPlayed++;
	}

}
