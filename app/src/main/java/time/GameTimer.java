package time;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;



public class GameTimer implements Serializable{

	private static final long serialVersionUID = 1L;

//	private static int moveInterval = 1;
	private static int influenceInterval = 5;
	private static int ressourceInterval = 60;

	private Timer timer = new Timer();

	private final static GameTimer gameTimer = new GameTimer();

	public void start() {
		timer = new Timer();

		TimerTask influenceTask = new TimerTask() {
			@Override
			public void run() {
				Planet.influenceTick();
				StarmapStage.getMainStarmapStage().updateInfluences();
			}
		};

		TimerTask ressourceTask = new TimerTask() {
			@Override
			public void run() {
				Planet.ressourceTick();
				GameTime.getGameTime().addAPlayedMinute();
				BankAccount.addAllNextGoods();
				BankPane.update();
				StarmapStage.getMainStarmapStage().updateGalacticPower();
				if(GameTime.getGameTime().getMinutesPlayed()%20 == 0) {
					Database.safetySaveDatabase();
				}
			}
		};

		double gameSpeed = GameTime.getGameTime().getGameSpeed();
	//	timer.scheduleAtFixedRate(moveTask, 0, (long) (1000*moveInterval/gameSpeed));
		timer.scheduleAtFixedRate(influenceTask, 0, (long) (1000*influenceInterval/gameSpeed));
		timer.scheduleAtFixedRate(ressourceTask, 0, (long) (1000*ressourceInterval/gameSpeed));
	}

	public void stop() {
		timer.cancel();
	}

	public static GameTimer getGameTimer() {
		return gameTimer;
	}


}
