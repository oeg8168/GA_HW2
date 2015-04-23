import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

/**
 * 
 * @author LISM_OEG
 *
 */
public class RealValuedGA {

	public static int recordInterval = 1;
	public static File outputFolder;

	public static ArrayList<Individual> populations;
	public static ArrayList<Double> avgValue;
	public static ArrayList<ArrayList<Individual>> pathList;

	public static int maxWorld = 5;
	public static int maxPopulation = 20;
	public static int maxGeneration = 100;
	public static double crossoverRate = 0.80;
	public static double mutationRate = 0.10;

	public static int world = 0;

	/**
	 * Initialization
	 */
	public static void initialize() {
		populations = new ArrayList<Individual>();
		avgValue = new ArrayList<Double>();
		pathList = new ArrayList<ArrayList<Individual>>();

		for (int i = 0; i < maxPopulation; i++) {
			populations.add(new Individual());
		}

		sortPopulations();
	}// end of initialize()

	/**
	 * Sort populations by increasing order (top element is minimum)
	 */
	public static void sortPopulations() {
		populations.sort(new Comparator<Individual>() {
			public int compare(Individual ind1, Individual ind2) {
				return Double.compare(ind2.getFitness(), ind1.getFitness());
			}
		});
	} // end of sortPopulations()

	/**
	 * Do selection using Roulette Wheel method
	 * 
	 * @return selected index
	 */
	public static int selection() {
		double totalFitness = 0;
		for (Individual i : populations) {
			totalFitness += i.getFitness();
		}

		// Roulette Wheel selection
		double shoot = Math.random();

		double relativeFitness = 0;
		double cumulativeFitness = 0;

		for (int i = 0; i < populations.size(); i++) {
			relativeFitness = populations.get(i).getFitness() / totalFitness;
			cumulativeFitness += relativeFitness;

			if (shoot < cumulativeFitness) {
				return i;
			}
		}

		return -1;
	}// end of selection()

	/**
	 * Make two parent individual crossover and generate children.<br>
	 * <br>
	 * (Due each individual have only two variables, no need to select<br>
	 * cut point, just simply exchange x and y)
	 * 
	 * @param p1
	 *            - parent
	 * @param p2
	 *            - parent
	 * @return two new individuals
	 */
	public static ArrayList<Individual> crossover(Individual p1, Individual p2) {
		ArrayList<Individual> temp = new ArrayList<Individual>();

		temp.add(new Individual(p1.getX(), p2.getY()));
		temp.add(new Individual(p2.getX(), p1.getY()));

		return temp;
	} // end of crossover()

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

	/**
	 * Keep top N individuals
	 */
	public static void keepBest() {
		while (populations.size() > maxPopulation) {
			populations.remove(populations.size() - 1);
		}
	}// end of keepBest()

	/**
	 * Count average value of given populations
	 * 
	 * @param populations
	 * @return average value
	 */
	public static double countAvgValue(ArrayList<Individual> populations) {
		double totalValue = 0;
		for (Individual i : populations) {
			totalValue += i.getValue();
		}

		return totalValue / populations.size();
	} // end of countAvgValue()

	/**
	 * Operation taken in each generation
	 */
	public static void nextGeneration() {
		// Selection and Reproduction
		int index1;
		int index2;
		while (Math.random() < crossoverRate) {
			index1 = selection();
			index2 = selection();
			populations.addAll(crossover(populations.get(index1), populations.get(index2)));
		}

		// Mutation
		int index;
		while (Math.random() < mutationRate) {
			index = (int) (Math.random() * populations.size());
			populations.set(index, mutate(populations.get(index)));
		}

		sortPopulations();
		keepBest();
	} // end of nextGeneration()

	/**
	 * Create log folders if it doesn't exist
	 */
	public static void createLogFolder() {
		if (!new File("./Log/").exists())
			new File("./Log/").mkdirs();

		// Create output folder
		Date date = new Date();
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		outputFolder = new File("./Log/" + sdFormat.format(date));
		if (!outputFolder.exists()) {
			outputFolder.mkdir();
		}
	}// end of createLogFolder()

	/**
	 * Write down parameters
	 */
	public static void writeParam() {
		try {
			FileWriter paramFW = new FileWriter(outputFolder.getAbsolutePath() + "/Parameters.txt");
			paramFW.write("recordInterval = " + recordInterval + "\r\n");
			paramFW.write("maxPopulation = " + maxPopulation + "\r\n");
			paramFW.write("maxGeneration = " + maxGeneration + "\r\n");
			paramFW.write("crossoverRate = " + crossoverRate + "\r\n");
			paramFW.write("mutationRate = " + mutationRate + "\r\n");
			paramFW.close();
		} catch (IOException e) {
			System.err.println("Error while output parameter!");
			e.printStackTrace();
		}
	} // end of writeParam()

	/**
	 * Output results for one world
	 */
	public static void output() {
		try {
			// Write pathList
			FileWriter fw = new FileWriter(outputFolder.getAbsolutePath() + "/" + world + "_pathList.csv");
			fw.write("x,y,f" + "\r\n");
			for (int i = 0; i < pathList.size(); i++) {
				fw.write("Generation " + i * recordInterval + "\r\n");

				for (Individual individual : pathList.get(i)) {
					fw.write(individual.getX() + "," + individual.getY() + "," + individual.getValue() + "\r\n");
				}
			}
			fw.close();

			// Write avgValue
			fw = new FileWriter(outputFolder.getAbsolutePath() + "/" + world + "_avgValue.txt");
			for (int i = 0; i < avgValue.size(); i++) {
				fw.write("Generation " + i * recordInterval + "\t" + avgValue.get(i) + "\r\n");
			}
			fw.close();

		} catch (IOException e) {
			System.err.println("Error while output results");
			e.printStackTrace();
		}

	} // end of output()

	/**
	 * Operation taken in each world
	 */
	public static void newWorld() {
		initialize();

		for (int i = 0; i < maxGeneration; i++) {
			nextGeneration();

			// Record at given interval
			if (i % recordInterval == 0) {
				pathList.add(new ArrayList<Individual>(populations));
				avgValue.add(countAvgValue(populations));
			}
		}

		output();
		world++;
	} // end of newWorld()

	public static void main(String[] args) {

		createLogFolder();
		writeParam();

		for (int i = 0; i < maxWorld; i++) {
			newWorld();
		}

		System.out.println("Program finished.");
	} // end of main()

} // end of class RealValuedGA
