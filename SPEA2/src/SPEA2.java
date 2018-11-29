import java.util.ArrayList;
import java.util.Collections;

public class SPEA2 {
	
	// Population size
	private int N;
	// Archive size
	private int Np;
	// Max generations
	private int T;
	// Mating pool size divisor
	private int k;
	// Mating pool
	private ArrayList<Chromosome> B;
	// City length
	private int n;
	// Number of days
	private int d;
	// Min days
	private int min_d;
	// Max days
	private int max_d;
	// Start point
	private int s;
	// Transport costs
	private double[][] CT;
	// Daily costs
	private double[] CV;
	// Scores 
	private double [][] S;
	// Number of reviews
	private double [][] R;
	// Population
	private ArrayList<Chromosome> P=new ArrayList<Chromosome>();
	// Archivo population
	private ArrayList<Chromosome> Pp=new ArrayList<Chromosome>();
	// Union of previous generations
	private ArrayList<Chromosome> union;
	
	/**
	 * Constructor with given params
	 */
	public SPEA2(int n, int np, int t, int k, int n2, int d, int min_d, int max_d, int s) {
		super();
		N = n;
		Np = np;
		T = t;
		this.k = k;
		n = n2;
		this.d = d;
		this.min_d = min_d;
		this.max_d = max_d;
		this.s = s;
		initCosts();
	}
	
	private void initCosts()
	{
		//TODO 
	}
	
	private void initialization()
	{
		// TODO Calculate f1 and f2 for each member of P and Pp
	}
	
	private void fitnessAssignment()
	{
		union= new ArrayList<>();
		union.addAll(P);
		union.addAll(Pp);
		//Calculate strengths (s)
		int[] S=new int[union.size()];
		for(int index=0;index<union.size();index++)
		{
			Chromosome i=union.get(index);
			for(Chromosome j:union)
			{
				if(dominate(i,j))
					S[index]++;
			}
		}
		//Calculate raw fitness (r)
		int[] R=new int[union.size()];
		Chromosome current=null;
		Chromosome other=null;
		for(int i=0;i<union.size();i++)
		{
			current=union.get(i);
			for(int j=0;j<union.size();j++)
			{
				other=union.get(j);
				if(dominate(other,current))
					R[i]+=S[j];
			}
		}
		//Calculate distance (D)
		ArrayList<Double>[] sigma=(ArrayList<Double>[])new Object[union.size()];
		int k=(int) Math.floor(Math.sqrt(union.size()));
		double[]D=new double[union.size()];
		for(int i=0;i<union.size();i++)
		{
			current=union.get(i);
			sigma[i]=new ArrayList<>();
			for(int j=0;j<union.size();j++)
			{
				other=union.get(j);
				sigma[i].add(distance(current,other));
			}
			Collections.sort(sigma[i]);
			D[i]=1/(sigma[i].get(k)+2);
		}
		//Calculate all fitness
		for(int i=0;i<union.size();i++)
		{
			union.get(i).setFitness(R[i]+D[i]);
		}
	}
	
	private int environmentalSelection()
	{
		return 0;
	}
	
	private ArrayList<Chromosome> matingSelection()
	{
		return null;
	}
	
	private void variation()
	{
		
	}
	
	
	
	private double distance(Chromosome i, Chromosome j)
	{
		double firstTerm=Math.pow(j.getF1()-i.getF1(),2);
		double secondTerm=Math.pow(j.getF2()-i.getF2(),2);
		return Math.sqrt(firstTerm+secondTerm);
	}
	
	private boolean dominate(Chromosome i, Chromosome j)
	{
		return i.getF1()<j.getF1() && i.getF2()<j.getF2();
	}
	
	public static void main (String[] args)
	{
		SPEA2 spea=new SPEA2(0,0,0,0,0,0,0,0,0);
		spea.initialization();
		int t=0;
		ArrayList<Chromosome> A= null;
		while(t++<spea.T)
		{
			spea.fitnessAssignment();
			spea.k=spea.environmentalSelection();
			if(t>=spea.T)
			{
				A=spea.Pp;
				continue;
			}
			spea.B=spea.matingSelection();
			spea.variation();
		}
		System.out.println("Solutions");
		for(Chromosome s:A)
			System.out.println(s);
		
	}
	
	
		
	
	
	
}
