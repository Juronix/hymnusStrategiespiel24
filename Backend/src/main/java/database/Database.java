package database;

import java.io.Serializable;

import com.example.demo.GameService;


public class Database implements Serializable {

	private static final long serialVersionUID = 1L;

	private final int gameID;

	// put your Data here
	private final GameService gameService;
	
	private static Database activDatabase;

	private static final GenericDataIO<Database> databaseIO = new GenericDataIO<>();


	public Database(final int gameID, GameService gameService) {
		this.gameID = gameID;
		this.gameService = gameService;
		activDatabase = this;
		resaveDatabase();
	}


	public static void resaveDatabase() {
		databaseIO.save(activDatabase, "database-G"+activDatabase.getGameID());
	}

	public static void safetySaveDatabase(final int hour, final int minute) {
		databaseIO.save(activDatabase, "database-G"+activDatabase.getGameID());
		databaseIO.save(activDatabase, "database-G"+activDatabase.getGameID()+"-"+hour+"-"+minute);
	}

	public static void safetySaveDatabase() {
		int minutesPlayed = getGameService().getGameTime().getMinutesPlayed();
		int minute = minutesPlayed%60;
		String minuteString;
		if(minute<10) {
			minuteString = "0"+minute;
		} else {
			minuteString = String.valueOf(minute);
		}
		int hour = minutesPlayed/60;
		databaseIO.save(activDatabase, "database-G"+activDatabase.getGameID()+"-"+hour+"-"+minuteString);
	}


	public static void loadDatabase(final int gameID) throws Exception {
		activDatabase = databaseIO.load("database-G"+gameID);
		GameService.reloadStaticGameService(activDatabase.gameService);
	}

	public static void loadSafetySaveDatabse(final int gameID, final int hour, final int minute) throws Exception {
		activDatabase = databaseIO.load("database-G"+gameID+"-"+hour+"-"+minute);
		GameService.reloadStaticGameService(activDatabase.gameService);
	}

	public static void loadDatabaseFromString(final String saveID) throws Exception {
		if(saveID.length()==1) {
			loadDatabase(Integer.valueOf(saveID));
		} else {
			activDatabase = databaseIO.load("database-"+saveID);
			GameService.reloadStaticGameService(activDatabase.gameService);
		}
	}


	// put your getters  here

	public static GameService getGameService() {
		return activDatabase.gameService;
	}

	public int getGameID() {
		return gameID;
	}



}
