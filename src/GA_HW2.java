/**
 * GA_HW2 general purpose
 * 
 * @author LISM_OEG
 *
 */
public class GA_HW2 {

	public static double maxFitness = 4000;
	public static double upper = 2.048;
	public static double lower = -2.048;

	/**
	 * Equation to be solve
	 * 
	 * @param x
	 * @param y
	 * @return equation value
	 */
	public static double equation(double x, double y) {

		double value = 0;
		value = 100 * Math.pow(Math.pow(x, 2) - y, 2) + Math.pow(1 - x, 2);

		return value;
	} // end of equation()

	/**
	 * Get range from upper bound to lower bound
	 * 
	 * @return Math.abs(upper - lower)
	 */
	public static double getRange() {
		return Math.abs(upper - lower);
	} // end of getRange()

} // end of class GA_HW2
