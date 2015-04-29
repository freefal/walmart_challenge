import java.io.*;
import java.text.*;
import java.util.*;

public class Attempt1 {
	static ArrayList<SalesDataPoint> salesData;
	static ArrayList<WeatherDataPoint> salesData;
	static ArrayList<KeyDataPoint> keyData;

	public static void main(String[] args) {
		readData(args[0], args[1], args[2]);
		
	}

	public static void readData(String salesFilename, String weatherFilename, String keyFilename) {
		try {
			salesData = readSalesData(salesFilename);
			weatherData = readWeatherData(weatherFilename);
			keyData = readKeyData(keyFilename);
		} catch (Exception e) { e.printStackTrace(); }
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
		return data;
	}
} 
