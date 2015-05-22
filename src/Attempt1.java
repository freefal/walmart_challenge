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
		attachWeatherData(salesData);
		attachWeatherData(testData);
		OLSInput[][] olsinputs = new OLSInput[storeCount][itemCount];
		
		for (int i = 0; i < olsinputs.length; i++) {
			for (int j = 0; j < olsinputs[i].length; j++) {
				olsinputs[i][j] = new OLSInput();
			}
		}
	
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
		
		double[][][] betas = new double[storeCount][itemCount][2];
		for (int i = 0; i < olsinputs.length; i++) {
			for (int j = 0; j < olsinputs[i].length; j++) {

				double[] y = ArrayUtils.toPrimitive(olsinputs[i][j].y.toArray(new Double[0]));
				double[][] x = new double[olsinputs[i][j].x1.size()][2];
				for (int k = 0; k < x.length; k++) {
					x[k][0] = olsinputs[i][j].x1.get(k);
					x[k][1] = olsinputs[i][j].x2.get(k);
				}
					
				OLSMultipleLinearRegression ols = new OLSMultipleLinearRegression();
				ols.newSampleData(y, x);
				try {
					betas[i][j] = ols.estimateRegressionParameters();
				} catch (Exception e) {
					betas[i][j][0] = 0.0;
					betas[i][j][1] = 0.0;
				}
			}
		}
		olsinputs = null;

		System.out.println("id,units");
		for (SalesDataPoint sdp : testData) {
			WeatherDataPoint wdp = sdp.todayWeather;
			double[] b = betas[sdp.store-1][sdp.item-1];
			try {
			double precipitation = (wdp.snowfall > 0.0 || wdp.preciptotal > 0.0) ? 1.0 : 0.0;
			int units = (int) (b[0]*1.0 + b[1]*precipitation);
			sdp.units = units;
			System.out.println(sdp.toString());
			} catch (Exception e) {
				/*
				System.err.println(sdp);
				System.err.println(sdp.todayWeather);
				System.err.println(b);
				*/
			}
		}
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
		for (WeatherDataPoint wdp : weatherData) {
			weatherMap.put(wdp.station + "-" + SalesDataPoint.formatDate(wdp.date), wdp);
		}
		return weatherMap;	
	}
	
	public static void attachWeatherData (ArrayList<SalesDataPoint> data) {
		for (SalesDataPoint sdp : data) {
			int station = keyMap.get(sdp.store);
			WeatherDataPoint todayWeather = weatherMap.get(station + "-" + SalesDataPoint.formatDate(sdp.date));

			if (todayWeather == null)
				System.err.println(station + "-" + SalesDataPoint.formatDate(sdp.date));
			//Date tomorrowDate = new Date(date.
			//WeatherDataPoint tomorrowWeather = weatherMap.get(station + "-" + tomorrowDate);
			sdp.todayWeather = todayWeather;
		}
	}
}
