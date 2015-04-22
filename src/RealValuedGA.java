/**
 * 
 * @author LISM_OEG
 *
 */
public class RealValuedGA {

	public static double crossoverRate = 0.80;
	public static double mutationRate = 0.10;

	/**
	 * Do mutation at x or y or both
	 * 
	 * @param input
	 *            - Individual
	 * @return mutated Individual
	 */
	public static Individual mutate(Individual input) {
		int xy = (int) (Math.random() * 3);

		if (xy == 0) { // mutate all
			return new Individual();
		} else if (xy == 1) { // mutate x
			return new Individual(Math.random() * GA_HW2.getRange() + GA_HW2.lower, input.getY());
		} else { // mutate y
			return new Individual(input.getX(), Math.random() * GA_HW2.getRange() + GA_HW2.lower);
		}
	} // end of mutation()

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

} // end of class RealValuedGA
