public class KeyDataPoint {
	public int station;
	public Data date;
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

	public KeyDataPoint(int station, Data date, int tmax, int tmin, int tavg, int depart, int dewpoint, int wetbulb, int heat, int cool, String sunrise, String sunset,
		String codesum, double snowfall, double preciptotal, double stnpressure, double sealevel, double resultspeed, int resultdir, double avgspeed;) {
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

	public static KeyDataPoint parse (String line) {
		KeyDataPoint dp = null;
		try {
			String[] pieces = line.split(",");
			int store = Integer.parseInt(pieces[0]);
			int item = Integer.parseInt(pieces[1]);
			dp = new WeatherDataPoint(store, item);
		} catch (Exception e) { e.printStackTrace(); }
		return dp;
	}
}
