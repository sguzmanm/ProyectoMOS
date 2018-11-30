import java.util.Comparator;

/**
 * Class for the chromosome
 * @author s.guzmanm
 *
 */
public class Chromosome {

	// Allele
	private String code;
	// Fitness
	private double fitness;
	// F1 Value
	private double f1;
	//F2 Value
	private double f2;
	//Strength value (0 = non-dominated)
	private int strength;
	
	//Fitness comparator
	
	public static final Comparator<Chromosome> FITNESS_COMPARATOR = new Comparator<Chromosome>() {

		@Override
		public int compare(Chromosome o1, Chromosome o2) {
			//10000 times as a small difference might return equality when they're not
			return (int) Math.round(10000*( o1.fitness - o2.fitness ));
		}
		
	};
	
	//Constructor
	public Chromosome(String code)
	{
		this.code=code;
	}
	public void setF2(double f2) {
		this.f2=f2;
	}
	public void setF1(double f1) {
		this.f1=f1;
	}
	
	//Set the fitness of the chromosome
	public void setFitness(double fitness)
	{
		this.fitness=fitness;
	}
	
	public void setStrength(int strength){
		this.strength = strength;
	}
	
	public int getStrength(){
		return strength;
	}
	
	//Gets f1 and f2
	public double getF1()
	{
		return f1;
	}
	
	public double getF2()
	{
		return f2;
	}
	
	public String getCode(){
		return this.code;
	}
	
	public String toString()
	{
		return "Code: "+code+"("+f1+","+f2+")";
	}
	
	public double getFitness() {
		return fitness;
	}
	
	
}
