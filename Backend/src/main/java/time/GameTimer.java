package time;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

import com.example.demo.GameService;

import database.Database;


public class GameTimer implements Serializable{

	private static final long serialVersionUID = 1L;

	private static int influenceInterval = 5;	// interval in seconds
	private static int ressourceInterval = 60;

	private static Timer timer = new Timer();

	public static void start() {
		timer = new Timer();

		TimerTask reputationTask = new TimerTask() {
			@Override
			public void run() {
				GameService.getGameService().getFamilies().parallelStream().forEach(family -> family.giveReputationForTrade(GameService.getGameService().getCityMap()));
			}
		};

		TimerTask hymnenTask = new TimerTask() {
			@Override
			public void run() {
				GameService.getGameService().getFamilies().parallelStream().forEach(family -> family.giveHymnenForTrade());
				getGameTime().addAPlayedMinute();
				if(getGameTime().getMinutesPlayed()%20 == 0) {
					Database.safetySaveDatabase();
				}
			}
		};

		double gameSpeed = getGameTime().getGameSpeed();
		timer.scheduleAtFixedRate(reputationTask, 0, (long) (1000*influenceInterval/gameSpeed));
		timer.scheduleAtFixedRate(hymnenTask, 0, (long) (1000*ressourceInterval/gameSpeed));
	}

	public static void stop() {
		timer.cancel();
	}

	public static GameTime getGameTime() {
		return GameService.getGameService().getGameTime();
	}

}
