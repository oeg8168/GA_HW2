/**
 * 
 * @author LISM_OEG
 *
 */
public class Individual {
	private double x;
	private double y;

	/**
	 * Constructor
	 */
	public Individual() {
		x = Math.random() * GA_HW2.getRange() + GA_HW2.lower;
		y = Math.random() * GA_HW2.getRange() + GA_HW2.lower;
	}

	/**
	 * Constructor
	 * 
	 * @param x
	 * @param y
	 */
	public Individual(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter of x
	 * 
	 * @return x
	 */
	public double getX() {
		return x;
	} // end of getX()

	/**
	 * Getter of y
	 * 
	 * @return y
	 */
	public double getY() {
		return y;
	}// end of getY()

	/**
	 * Calculate equation value
	 * 
	 * @return equation value
	 */
	public double getValue() {
		return GA_HW2.equation(x, y);
	} // end of getValue()

	/**
	 * Calculate fitness (1/equation value)
	 * 
	 * @return fitness (1/equation value)
	 */
	public double getFitness() {
		//return 4000-GA_HW2.equation(x, y);
		return 1 / Math.sqrt(GA_HW2.equation(x, y));
	} // end of getFitness()

} // end of class Individual
