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
			int item = Integer.parseInt(pieces[1]);
		} catch (Exception e) { e.printStackTrace(); }
		return dp;
	}
}
