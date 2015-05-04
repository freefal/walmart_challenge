import java.io.*;
import java.text.*;
import java.util.*;

public class Attempt1 {
	static ArrayList<SalesDataPoint> salesData;
	static ArrayList<WeatherDataPoint> weatherData;
	static ArrayList<KeyDataPoint> keyData;
	static ArrayList<SalesDataPoint> testData;
	static HashMap<Integer, Integer> keyMap;
	static HashMap<String, WeatherDataPoint> weatherMap;
	static int storeCount;
	static int itemCount;

	public static void main(String[] args) {
		try {
			salesData = readSalesData(args[0]);
			weatherData = readWeatherData(args[1]);
			keyData = readKeyData(args[2]);
			testData = readTestData(args[3]);
		} catch (Exception e) { e.printStackTrace(); }
		
		keyMap = createKeyLookup(keyData);
		weatherMap = createWeatherLookup(weatherData);

		// attachWeatherData(salesData, weatherData, keyData);
		
		// OLSInput[][] olsinputs = new OLSInput[storeCount][itemCount];
		
	}

	public static ArrayList<SalesDataPoint> readSalesData (String filename) throws Exception {
		ArrayList<SalesDataPoint> data = new ArrayList<SalesDataPoint>();
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line = null;
		line = br.readLine(); // Throw away the header row
		while ((line = br.readLine()) != null) {
			SalesDataPoint dp = SalesDataPoint.parse(line);
			data.add(dp);
		}
		br.close();
		return data;
	}
	
	public static ArrayList<WeatherDataPoint> readWeatherData (String filename) throws Exception {
		ArrayList<WeatherDataPoint> data = new ArrayList<WeatherDataPoint>();
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line = null;
		line = br.readLine(); // Throw away the header row
		while ((line = br.readLine()) != null) {
			WeatherDataPoint dp = WeatherDataPoint.parse(line);
			data.add(dp);
		}
		br.close();
		return data;
	}

	public static ArrayList<KeyDataPoint> readKeyData (String filename) throws Exception {
		ArrayList<KeyDataPoint> data = new ArrayList<KeyDataPoint>();
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line = null;
		line = br.readLine(); // Throw away the header row
		while ((line = br.readLine()) != null) {
			KeyDataPoint dp = KeyDataPoint.parse(line);
			data.add(dp);
		}
		br.close();
		return data;
	}

	public static ArrayList<SalesDataPoint> readTestData (String filename) throws Exception {
		ArrayList<SalesDataPoint> data = new ArrayList<SalesDataPoint>();
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line = null;
		line = br.readLine(); // Throw away the header row
		while ((line = br.readLine()) != null) {
			SalesDataPoint dp = SalesDataPoint.parseTest(line);
			if (dp.store > storeCount)
				storeCount = dp.store;
			if (dp.item > itemCount)
				itemCount = dp.item;
			data.add(dp);
		}
		return data;
	}

	public static HashMap<Integer,Integer> createKeyLookup(ArrayList<KeyDataPoint> keyData) {
		HashMap<Integer,Integer> keyMap = new HashMap<Integer,Integer>();
		for (KeyDataPoint kdp : keyData)
			keyMap.put(kdp.store, kdp.station);
		return keyMap;	
	}
	
	public static HashMap<String,WeatherDataPoint> createWeatherLookup(ArrayList<WeatherDataPoint> weatherData) {
		HashMap<String,WeatherDataPoint> weatherMap = new HashMap<String,WeatherDataPoint>();
		for (WeatherDataPoint wdp : weatherData)
			weatherMap.put(wdp.station + "-" + wdp.date, wdp);
		return weatherMap;	
	}
	
	public static void attachWeatherData (ArrayList<SalesDataPoint> salesData, ArrayList<WeatherDataPoint> weatherData, ArrayList<KeyDataPoint> keyData) {
		for (SalesDataPoint sdp : salesData) {
			int station = keyMap.get(sdp.store);
			WeatherDataPoint todayWeather = weatherMap.get(station + "-" + sdp.date);
			//Date tomorrowDate = new Date(date.
			//WeatherDataPoint tomorrowWeather = weatherMap.get(station + "-" + tomorrowDate);
			sdp.todayWeather = todayWeather;
		}
	}
}
