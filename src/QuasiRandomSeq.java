import java.util.ArrayList;

/**
 * Holton quasi-random sequences
 * 
 * @author LISM_OEG
 *
 */
public class QuasiRandomSeq {

	/**
	 * Check if input is prime or not
	 * 
	 * @param input
	 *            - integer
	 * @return boolean value whether input is prime or not
	 */
	private static boolean isPrime(int input) {
		if (input < 2) {
			return false;
		}

		for (int j = 2; j <= Math.sqrt(input); j++) {
			if (input % j == 0) {
				return false;
			}
		}

		return true;
	} // end of isPrime()

	/**
	 * Get one value of Holton quasi-random sequences
	 * 
	 * @param radix
	 *            - base number, must be a prime number
	 * @param index
	 *            - n
	 * @return Holton quasi-random sequences value at given index with radix
	 * @see <a href="http://en.wikipedia.org/wiki/Halton_sequence">
	 *      http://en.wikipedia.org/wiki/Halton_sequencef </a>
	 */
	private static double getOne(int radix, int index) {
		double result = 0.0;
		double f = 1.0;
		double i = index;

		while (i > 0) {
			f = f / radix;
			result = result + f * (i % radix);
			i = Math.floor(i / radix);
		}

		return result;
	} // end of getOne()

	/**
	 * Generate an ArrayList that contain n Holton quasi-random sequences with
	 * given radix
	 * 
	 * @param radix
	 *            - base number, must be a prime number
	 * @param n
	 * @return ArrayList consist n elements with given radix in Holton
	 *         quasi-random sequences
	 */
	public static ArrayList<Double> generate(int radix, int n) {
		if (!isPrime(radix)) {
			System.err.println("radix/base must be a prime number!");
			return null;
		}

		ArrayList<Double> output = new ArrayList<Double>();

		for (int i = 0; i < n; i++) {
			output.add(getOne(radix, i + 1));
		}

		return output;
	} // end of generate()

	public static void main(String[] args) {

		ArrayList<Double> test = generate(7, 100);
		for (Double d1 : test) {
			System.out.println(d1);
		}
	} // end of main()

} // end of class QuasiRandomSeq
