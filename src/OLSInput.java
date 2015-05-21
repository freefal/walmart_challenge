import java.text.*;
import java.util.*;


public class OLSInput {
	ArrayList<Double> y;
	ArrayList<Double> x1;
	ArrayList<Double> x2;

	public OLSInput () {
		y = new ArrayList<Double>();
		x1 = new ArrayList<Double>();
		x2 = new ArrayList<Double>();
	}

	public void add (double y, double x2) {
		this.y.add(y);
		this.x1.add(1.0);
		this.x2.add(x2);
	}
}
