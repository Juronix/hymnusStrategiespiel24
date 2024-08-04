package  database;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import city.City;
import time.GameTime;



public class Database implements Serializable {

	private static final long serialVersionUID = 1L;

	private final int gameID;
	private final GameTime gameTime;

	// put your Data here
	private final Map<String, Integer> cityIdMap;
	private final Map<Integer, City> cityMap;
	
	
	private static Database activDatabase;

	private static final GenericDataIO<Database> databaseIO = new GenericDataIO<>();


	public Database(final int gameID) {
		this.gameID = gameID;
		this.gameTime = new GameTime();

		// create new instances of your Data here
		this.cityIdMap = new HashMap<String, Integer>();
		this.cityMap = new HashMap<Integer, City>();

		activDatabase = this;
		resaveDatabase();
	}

	public static void resaveDatabase() {
		databaseIO.save(activDatabase, "database-G"+activDatabase.getGameID());
	}
	public static void loadDatabase(final int gameID) throws Exception {
		activDatabase = databaseIO.load("database-G"+gameID);
	}

	public static void safetySaveDatabase(final int hour, final int minute) {
		databaseIO.save(activDatabase, "database-G"+activDatabase.getGameID());
		databaseIO.save(activDatabase, "database-G"+activDatabase.getGameID()+"-"+hour+"-"+minute);
	}
	public static void safetySaveDatabase() {
		int minutesPlayed = getGameTime().getMinutesPlayed();
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
	public static void loadSafetySaveDatabse(final int gameID, final int hour, final int minute) throws Exception {
		activDatabase = databaseIO.load("database-G"+gameID+"-"+hour+"-"+minute);
	}

	public static void loadDatabaseFromString(final String saveID) throws Exception {
		if(saveID.length()==1) {
			loadDatabase(Integer.valueOf(saveID));
		} else {
			activDatabase = databaseIO.load("database-"+saveID);
		}
	}

	// put your getters  here

	public static int getCityIdMap(String cityName) {
		return activDatabase.cityIdMap.get(cityName);
	}

	public static Set<City> getCitySet() {
		return new HashSet<City>(activDatabase.cityMap.values());
	}

	public static City getCity(final int cityID) {
		return activDatabase.cityMap.get(cityID);
	}

	public static GameTime getGameTime() {
		return activDatabase.gameTime;
	}

	public int getGameID() {
		return gameID;
	}



}
