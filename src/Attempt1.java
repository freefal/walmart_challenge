import java.io.*;
import java.text.*;
import java.util.*;
import org.apache.commons.math3.stat.regression.*;
import org.apache.commons.math3.linear.*;
import org.apache.commons.lang3.*;

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
		
		System.out.println("Going to Attach Weather");
		attachWeatherData(salesData, weatherData, keyData);
		System.out.println("Attached Weather");
		
		OLSInput[][] olsinputs = new OLSInput[storeCount][itemCount];
		
		for (int i = 0; i < olsinputs.length; i++) {
			for (int j = 0; j < olsinputs[i].length; j++) {
				olsinputs[i][j] = new OLSInput();
			}
		}
	
		System.out.println("Going to set up OLSInputs");
		
		for (SalesDataPoint sdp : salesData) {
			OLSInput oi = olsinputs[sdp.store - 1][sdp.item - 1];
			double precipitation = 0.0;
			WeatherDataPoint wdp = sdp.todayWeather;
			precipitation = (wdp.snowfall > 0.0 || wdp.preciptotal > 0.0) ? 1.0 : 0.0;
			oi.add(sdp.units, precipitation);
		}
		salesData = null;
		weatherData = null;
		keyData = null;
		keyMap = null;
		
		System.out.println("Set up OLSInputs");

		double[][][] betas = new double[storeCount][itemCount][2];
		for (int i = 0; i < olsinputs.length; i++) {
			for (int j = 0; j < olsinputs[i].length; j++) {
				double[] y = ArrayUtils.toPrimitive(olsinputs[i][j].y.toArray(new Double[0]));
				double[][] x = new double[2][olsinputs[i][j].x1.size()];
				x[0] = ArrayUtils.toPrimitive(olsinputs[i][j].x1.toArray(new Double[0]));
				x[1] = ArrayUtils.toPrimitive(olsinputs[i][j].x2.toArray(new Double[0]));
				
				OLSMultipleLinearRegression ols = new OLSMultipleLinearRegression ();
				ols.newSampleData(y, x);
				betas[i][j] = ols.estimateRegressionParameters();
			}
		}
		olsinputs = null;
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
