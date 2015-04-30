import java.text.*;
import java.util.*;


public class WeatherDataPoint {
        static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	public int station;
	public Date date;
	public int tmax;
	public int tmin;
	public int tavg;
	public int depart;
	public int dewpoint;
 	public int wetbulb;
	public int heat;
	public int cool;
	public String sunrise;
	public String sunset;
	public String codesum;
	public double snowfall;
	public double preciptotal;
	public double stnpressure;
	public double sealevel;
	public double resultspeed;
	public int resultdir;
	public double avgspeed;

	public WeatherDataPoint(int station, Date date, int tmax, int tmin, int tavg, int depart, int dewpoint, int wetbulb, int heat, int cool, String sunrise, String sunset,
		String codesum, double snowfall, double preciptotal, double stnpressure, double sealevel, double resultspeed, int resultdir, double avgspeed) {
		this.station = station;
		this.date = date;
		this.tmax = tmax;
		this.tmin = tmin;
		this.tavg = tavg;
		this.depart = depart;
		this.dewpoint = dewpoint;
		this.wetbulb = wetbulb;
		this.heat = heat;
		this.cool = cool;
		this.sunrise = sunrise;
		this.sunset = sunset;
		this.codesum = codesum;
		this.snowfall = snowfall;
		this.preciptotal = preciptotal;
		this.stnpressure = stnpressure;
		this.sealevel = sealevel;
		this.resultspeed = resultspeed;
		this.resultdir = resultdir;
		this.avgspeed = avgspeed;
	}

	public static WeatherDataPoint parse (String line) {
		WeatherDataPoint dp = null;
		try {
			String[] pieces = line.split(",");
			int station = parseInt(pieces[0]);
			Date date = df.parse(pieces[1]);
			int tmax = parseInt(pieces[2]);
			int tmin = parseInt(pieces[3]);
			int tavg = parseInt(pieces[4]);
			int depart = parseInt(pieces[5]);
			int dewpoint = parseInt(pieces[6]);
			int wetbulb = parseInt(pieces[7]);
			int heat = parseInt(pieces[8]);
			int cool = parseInt(pieces[9]);
			String sunrise = pieces[10];
			String sunset = pieces[11];
			String codesum = pieces[12];
			double snowfall = parseDouble(pieces[13]);
			double preciptotal = parseDouble(pieces[14]);
			double stnpressure = parseDouble(pieces[15]);
			double sealevel = parseDouble(pieces[16]);
			double resultspeed = parseDouble(pieces[17]);
			int resultdir = parseInt(pieces[18]);
			double avgspeed = parseDouble(pieces[19]);

			dp = new WeatherDataPoint(station, date, tmax, tmin, tavg, depart, dewpoint, wetbulb, heat, cool, sunrise, sunset,
				codesum, snowfall, preciptotal, stnpressure, sealevel, resultspeed, resultdir, avgspeed);
		} catch (Exception e) { e.printStackTrace(); }
		return dp;
	}

	private static int parseInt (String str) {
		str = str.replaceAll(" ", "");
		str = str.replaceAll("\"", "");
		if (str.equals("M"))
			return -1000;
		else
			return Integer.parseInt(str);
	}

	private static double parseDouble (String str) {
		str = str.replaceAll(" ", "");
		str = str.replaceAll("\"", "");
		if (str.equals("M"))
			return -1000;
		else if (str.equals("T"))
			return 0.1;
		else
			return Double.parseDouble(str);
	}
}
