public class KeyDataPoint {
	public int store;
	public int station;

	public KeyDataPoint(int store, int station) {
		this.store = store;
		this.station = station;
	}

	public static KeyDataPoint parse (String line) {
		KeyDataPoint dp = null;
		try {
			String[] pieces = line.split(",");
			int store = Integer.parseInt(pieces[0]);
			int station = Integer.parseInt(pieces[1]);
			dp = new KeyDataPoint(store, station);
		} catch (Exception e) { e.printStackTrace(); }
		return dp;
	}
}
