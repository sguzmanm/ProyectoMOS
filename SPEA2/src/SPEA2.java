import java.io.*;
import java.lang.reflect.Array;
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
	// Current generation
	private int t;
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
	public SPEA2(int N, int np, int t,int kp, int n,int d, int min_d, int max_d, int s,String CV,String CT,String R,String S) {
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
		this.kp=kp;
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
	
	private static SPEA2 scenario(int N, int Np, int T,int kp, int id)
	{
		//	int N, int np, int t,int n, int d, int min_d, int max_d, int s,String CV,String CT,String R,String S
		if(id==1)
			return new SPEA2(N,Np,T,kp,4,4,1,1,1,"Random","Random","Random","Random");
		else if (id==2)
			return new SPEA2(N,Np,T,kp,2,8,3,5,1,"Random","Random",null,"scoresBase2.txt");
		else if (id==3)
			return new SPEA2(N,Np,T,kp,2,8,3,5,1,"lifeCostsBase3.txt",null,"Random","Random");
		else if (id==4)
			return new SPEA2(N,Np,T,kp,3,3,1,1,1,null,"transportCostsBase4.txt","Random","Random");
		else if (id==5)
			return new SPEA2(N,Np,T,kp,10,15,1,13,6,"lifeCostsMedium.txt",null,null,"scoresMedium.txt");
		else if (id==6)
			return new SPEA2(N,Np,T,kp,2,8,3,5,1,"Random","Random",null,"scoresBase2.txt");
		else return null;
	}

	private void initialization()
	{
		// TODO Calculate f1 and f2 for each member of P and Pp
		this.Pp=new ArrayList<>();
		String code=null;
		int temp=0;
		System.out.println("n "+4);
		for(int i=0;i<N;i++)
		{
			//Allele generation
			code=s+"";
			for(int j=1;j<d;j++)
			{
				temp=(int)(Math.random()*n)+1;
				if(temp>n)
					temp--;
				code+="-"+temp;
			}
			if(isValid(code))
				this.P.add(new Chromosome(code));
			else
				i--;
		}
	}
	
	private double avgArray(double[] array) {
		double ans=0;
		for(int i=0;i<array.length;i++)
			ans+=array[i];
		return ans/array.length;
	}

	private void fitnessAssignment()
	{
		union= new ArrayList<>();
		union.addAll(P);
		union.addAll(Pp);
		
		//Calculate f1 and f2 for everything
		double f1=0;
		double f2=0;
		double avgScores=avgArray(Puntaje);
		double avgLifeCosts=avgArray(CV);
		double avgTransportCosts=0;
		for(int i=0;i<CT.length;i++)
			avgTransportCosts+=avgArray(CT[i]);
		avgTransportCosts/=(CT.length*CT.length);
		String[] data=null;
		for(Chromosome chromosome:union)
		{
			String code=chromosome.getCode();
			data=code.split("-");
			//F1
			for(int j=0;j<data.length;j++)
				f1+=Puntaje[Integer.parseInt(data[j])-1];
			f1=(avgScores+1)/(f1+1);
			//F2
			for(int j=1;j<data.length;j++)
			{
				int ini=Integer.parseInt(data[j-1]);
				int fin=Integer.parseInt(data[j]);
				f2+=CV[ini-1]+CT[ini-1][fin-1];
			}
			f2+=CV[Integer.parseInt(data[data.length-1])-1];
			f2/=(avgLifeCosts*d+avgTransportCosts*d/min_d);
			chromosome.setF1(f1);
			chromosome.setF2(f2);
		}
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
		ArrayList<Double>[] sigma=new ArrayList[union.size()];
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
		
		double[] stats = calcIterationStats();
		System.out.println("[t=" + t + "]: \t Pavg = "+ stats[0] + " \t Ppavg = " + stats[1] + " \t unionAvg = " + stats[2]);

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
			Pp=truncatedPp;
		}
		
		return k;
	}
	
	private ArrayList<Chromosome> matingSelection()
	{
		int matingSize = Math.max(2, Math.min(Np,(int) Math.floor(k/kp)));
		System.out.println("K "+k+" KP "+kp);
		//Initialize Mating pool
		ArrayList<Chromosome> B = new ArrayList<>();
		
		for(int i = 0; i < matingSize; i++){
			B.add(Pp.get(i));
		}
		
		return B;
	}
	
	private void variation()
	{
		P = new ArrayList<>();
		
		for(int i = 0; i < N; i++) {
		
			int a = (int) Math.floor(Math.random()*B.size());
			int b = a;
			
			while(b == a) {
				b = (int) Math.floor(Math.random()*B.size());
			}
			
			Chromosome offspring = null;
			double dice = Math.random();
			
			if(dice < 5.0/11.0) {
				offspring = doubleCutCrossover( B.get(a), B.get(b) );
			}
			else if (dice < 10.0/11.0) {
				offspring =  uniformCrossover(B.get(a), B.get(b));
			}
			else {
				offspring = mutation(B.get(a));
			}
			if(isValid(offspring.getCode()))
				P.add(offspring);
			else
				i--;
		}
	}
	
	private double[] calcIterationStats() {
		double Pavg = 0, PpAvg = 0, unionAvg = 0;
		
		for(Chromosome i: union) {
			unionAvg += i.getFitness();
		}
		
		unionAvg = unionAvg/union.size();
		
		for(Chromosome i: Pp) {
			PpAvg += i.getFitness();
		}
		
		PpAvg = PpAvg/Pp.size();
		
		for(Chromosome i: P) {
			Pavg += i.getFitness();
		}
		
		Pavg = Pavg/P.size();
		
		return new double[]{Pavg, PpAvg, unionAvg,P.size(),Pp.size()};		
	}
	
	private Chromosome doubleCutCrossover(Chromosome a, Chromosome b){
		
		//Choose two random numbers between 0 and the half of the days
		int i = (int) Math.floor( Math.random() * d/2 );
		int j = (int) Math.floor( Math.random() * d/2 );
		
		String[] aStr = a.getCode().split("-");
		String[] bStr = b.getCode().split("-");
		
		String[] newCode = new String[aStr.length];
		
		for(int index = 0; index < i; index++){
			newCode[index] = aStr[index];
		}
		
		for(int index = i; index < j; index++){
			newCode[index] = bStr[index];
		}
		
		for(int index = j; index < aStr.length; index++){
			newCode[index] = aStr[index];
		}
		
		//TODO calc f1 and f2 for newborn code
		return new Chromosome(String.join("-", newCode));
	}
	
	private Chromosome uniformCrossover(Chromosome a, Chromosome b){
		
		String[] aStr = a.getCode().split("-");
		String[] bStr = b.getCode().split("-");
		
		String[] newCode = new String[aStr.length];
		
		for(int index = 0; index < aStr.length; index++){
			if(Math.random() > 0.5){
				newCode[index] = aStr[index];
			}
			else{
				newCode[index] = bStr[index];
			}
		}	
		
		String code = String.join("-", newCode);
		
		return new Chromosome(code);
	}
	
	private Chromosome mutation(Chromosome a){
		
		String[] codeStr = a.getCode().split("-");
		
		for(int i = 0; i < codeStr.length; i++) {
			
			if(Math.random() < 0.1) {
				int newCity = (int) Math.floor(Math.random()*n) + 1;
				codeStr[i] = Integer.toString(newCity);
			}
			
		}
		
		return new Chromosome(String.join("-", codeStr));
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
	
	private boolean isValid(String code)
	{
		String[] data= code.split("-");
		int[] quantity=new int[n];
		for(String s:data)
		{
			quantity[Integer.parseInt(s)-1]++;
		}
		for(int i=0;i<n;i++)
			if(quantity[i]!=0 && (quantity[i]<min_d || quantity[i]>max_d))
				return false;
		return true;
			
	}
	
	public static void main (String[] args)
	{

		SPEA2 spea=scenario(30,6,10000,3,2);
		spea.initialization();
		System.out.println("Initialization");
		int t=0;
		ArrayList<Chromosome> A= null;
		while(t++<spea.T)
		{
			System.out.println("Gen "+(t-1));
			//(Pavg, PPavg, unionAvg)
			spea.fitnessAssignment();
			System.out.println("Fitness Assignment");
			spea.k=spea.environmentalSelection();
			System.out.println("Environmental Selection");
			if(t>=spea.T)
			{
				A=spea.Pp;
				continue;
			}
			spea.B=spea.matingSelection();
			System.out.println("Mating selection");
			spea.variation();
			System.out.println("Variation");
		}
		System.out.println("Solutions");
		for(Chromosome s:A)
			System.out.println(s);
		
	}
	
	
		
	
	
	
}
