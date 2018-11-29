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
	//Constructor
	public Chromosome(String code,double f1, double f2)
	{
		this.code=code;
		this.f1=f1;
		this.f2=f2;
	}
	private void calculateF2() {
		// TODO Auto-generated method stub
		
	}
	private void calculateF1() {
		// TODO Auto-generated method stub
		
	}
	//Set the fitness of the chromosome
	public void setFitness(double fitness)
	{
		this.fitness=fitness;
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
	
	
}
