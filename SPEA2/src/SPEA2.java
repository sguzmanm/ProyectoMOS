import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class SPEA2 {
	
	// Population size
	private int N;
	// Archive size
	private int Np;
	// Max generations
	private int T;
	// Mating pool size divisor
	private int kp;
	//Mating pool size (before divisor)
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
	// Scores using S and R formula
	private double [] Puntaje;
	// Population
	private ArrayList<Chromosome> P=new ArrayList<Chromosome>();
	// Archivo population
	private ArrayList<Chromosome> Pp=new ArrayList<Chromosome>();
	// Union of previous generations
	private ArrayList<Chromosome> union;
	
	/**
	 * Constructor with given params
	 * @param CV File name for living costs per city
	 * @param CT File name for transport costs
	 * @param R File for the quantity of reviews
	 * @param S File for the scores of the city
	 */
	public SPEA2(int N, int np, int t, int n,int d, int min_d, int max_d, int s,String CV,String CT,String R,String S) {
		super();
		this.N = N;
		this.n=n;
		Np = np;
		T = t;
		this.d = d;
		this.min_d = min_d;
		this.max_d = max_d;
		this.s = s;
		initCosts(CV,CT,R,S);
	}
	
	private void initCosts(String CV,String CT,String R,String S)
	{
		BufferedReader br=null;
		boolean random=false; //Specify whether the given attribute needs a random matrix or not
		//Costs of living
		this.CV=new double[n];
		if(CV!=null)
		{
			random=CV.equals("Random");
			for(int i=0;i<n;i++)
				if(random)
					this.CV[i]=Math.floor(Math.random()*100000);
				else
				{
					try {
						br=new BufferedReader(new FileReader("./data/"+CV));
						for(String s:br.readLine().split(" "))
							this.CV[i]=Double.parseDouble(s);
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
					
		}
		else
		{
			Arrays.fill(this.CV, 1);
		}
		//Costs of transport
		this.CT=new double[n][n];
		if(CT!=null)
		{
			random=CT.equals("Random");
			readMatrix(CT, random, this.CT, br);
		}
		else
		{
			for(int i=0;i<n;i++)
				Arrays.fill(this.CT[i], 1);
		}
		//Quantity of reviews
		double[][]reviews=new double[n][2*max_d];
		if(R!=null)
		{
			random=R.equals("Random");
			readMatrix(R, random, reviews, br);
		}
		else
		{
			for(int i=0;i<n;i++)
				Arrays.fill(reviews[i], 1);
		}
		//Quantity of Scores
		double[][]scores=new double[n][2*max_d];
		if(S!=null)
		{
			random=S.equals("Random");
			readMatrix(S, random, scores, br);
		}
		else
		{
			for(int i=0;i<n;i++)
				Arrays.fill(scores[i], 1);
		}
		//Calculation of "Puntaje"
		this.Puntaje=new double[n];
		for(int i=0;i<n;i++)
		{
			for(int l=0;l<2*max_d;l++)
			{
				Puntaje[i]+=scores[i][l]/5*Math.sqrt(reviews[i][l]);
			}
		}
		//Sysout results
		System.out.println("Life Costs");
		for(int i=0;i<this.CV.length;i++)
			System.out.println(this.CV[i]);
		System.out.println("Transport costs");
		for(int i=0;i<this.CT.length;i++)
			for(int j=0;j<this.CT[0].length;j++)
				System.out.println(this.CT[i][j]);
		System.out.println("Scores");
		for(int i=0;i<this.Puntaje.length;i++)
			System.out.println(this.Puntaje[i]);
		
	}
	
	private void readMatrix(String matrixLocation, boolean random,double[][] matrix, BufferedReader br)
	{
		if(random)
		{
			for(int i=0;i<matrix.length;i++)
				for(int j=0;j<matrix[0].length;j++)
					matrix[i][j]=Math.floor(Math.random()*100000);
		}
		else
		{
			try
			{
				br=new BufferedReader(new FileReader("./data/"+matrixLocation));
				for(int i=0;i<matrix.length;i++)
				{
					String[] data=br.readLine().split(" ");
					for(int j=0;j<matrix[0].length;j++)
						matrix[i][j]=Double.parseDouble(data[j]);
				}
				br.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			
		}
	}
	
	private static SPEA2 scenario(int N, int Np, int T, int id)
	{
		//	int N, int np, int t,int n, int d, int min_d, int max_d, int s,String CV,String CT,String R,String S
		if(id==1)
			return new SPEA2(N,Np,T,4,4,1,1,1,"Random","Random","Random","Random");
		else if (id==2)
			return new SPEA2(N,Np,T,2,8,3,5,1,"Random","Random",null,"scoresBase2.txt");
		else if (id==3)
			return new SPEA2(N,Np,T,2,8,3,5,1,"lifeCostsBase3.txt",null,"Random","Random");
		else if (id==4)
			return new SPEA2(N,Np,T,3,3,1,1,1,null,"transportCostsBase4.txt","Random","Random");
		else if (id==5)
			return new SPEA2(N,Np,T,10,15,1,13,6,"lifeCostsMedium.txt",null,null,"scoresMedium.txt");
		else if (id==6)
			return new SPEA2(N,Np,T,2,8,3,5,1,"Random","Random",null,"scoresBase2.txt");
		else return null;
	}

	private void initialization()
	{
		// TODO Calculate f1 and f2 for each member of P and Pp
		this.Pp=new ArrayList<>();
		String code=null;
		int temp=0;
		double f1=0;
		double f2=0;
		double avgScores=avgArray(Puntaje);
		double avgLifeCosts=avgArray(CV);
		double avgTransportCosts=0;
		for(int i=0;i<CT.length;i++)
			avgTransportCosts+=avgArray(CT[i]);
		avgTransportCosts/=CT.length;
		String[] data=null;
		for(int i=0;i<N;i++)
		{
			//Allele generation
			code=s+"";
			for(int j=1;j<d;j++)
			{
				temp=(int)Math.random()*n+1;
				if(temp>n)
					temp--;
				code+="-"+temp;
			}
			//F1
			data=code.split("-");
			for(int j=0;j<data.length;j++)
				f1+=Puntaje[Integer.parseInt(data[j])-1];
			f1=avgScores/(f1+1);
			//F2
			//TODO Add f2 val normalized
			this.P.add(new Chromosome(code,f1,f2));
		}
	}
	
	private double avgArray(double[] array) {
		// TODO Auto-generated method stub
		return 0;
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
			i.setStrength(S[index]);
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
		//Return value (number of non-dominated solutions in union)
		int k = -1;
		Pp = new ArrayList<>();
		
		for (Chromosome i : union){
			//If chromosome is non-dominated
			if(i.getStrength() == 0){
				Pp.add(i);
			}
		}
		
		k = Pp.size();
		
		if(Pp.size() < Np){			
			union.sort(Chromosome.FITNESS_COMPARATOR);
			//llenar con elementos de P
			for(Chromosome i : union){
				//If the solution is dominated
				if(i.getStrength() != 0){
					Pp.add(i);
				}
				//Break when Pp meets size requirement 
				if(Pp.size() == Np){
					break;
				}
			}
		}
		else if (Pp.size() > Np){
			ArrayList<Chromosome> truncatedPp = new ArrayList<>(); 
			Pp.sort(Chromosome.FITNESS_COMPARATOR);
			
			for(Chromosome i : Pp){
				truncatedPp.add(i);
				if(truncatedPp.size() == Np){
					break;
				}
			}
			
		}
		
		return k;
	}
	
	private ArrayList<Chromosome> matingSelection()
	{
		int matingSize = Math.max(2, (int) Math.floor(k/kp));
		
		//Initialize Mating pool
		ArrayList<Chromosome> B = new ArrayList<>();
		
		for(int i = 0; i < matingSize; i++){
			B.add(Pp.get(i));
		}
		
		return B;
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

		SPEA2 spea=scenario(0,0,0,5);
		/*spea.initialization();
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
			System.out.println(s);*/
		
	}
	
	
		
	
	
	
}
