package LEPackage;

/*Dan Luoma
 *CS 3010.01
 *Programming Assn. #1
 *October 16th, 2018*/

public class GaussIterative {

	/*A class for performing Jacobi and Guass-Seidel iterative methods to solve a linear system of equations.
	 *Stores the A and B matrix, the desired error factor, and stores and outputs the results of each variable. 
	 */
	private double epsilon;
	private final int MAX_ITERATIONS = 50;
	private double[][] aMatrix;
	private double[] bMatrix;
	private double[] solution;
	private boolean jacobi;
	
	//Constructor
	public GaussIterative(double[][] A, double[] B, double eps, boolean bool)
	{
		this.aMatrix = new double[B.length][B.length];
		this.bMatrix = new double[B.length];
		this.solution = new double[B.length];
		for (int i = 0; i < B.length; i++)
		{
			this.bMatrix[i] = B[i];
			
			for (int j = 0; j < B.length; j++)
				this.aMatrix[i][j] = A[i][j];
		}
		
		this.epsilon = eps;
		this.jacobi = bool;
	}
	
	//Public method user can call to solve linear system of equations.
	public void solve()
	{
		this.solution = this.privateSolve(aMatrix, bMatrix);
	}
	
	//Private method called by public solve() method.
	private double[] privateSolve(double[][] matrixA, double[] matrixB)
	{
		int size = matrixA.length;
		//Creates the initial x matrix and fills it with 0's.
		double[] previous = new double[size];
		java.util.Arrays.fill(previous, 0);
		
		int iterations = 0;
		
		//Will run until @param epsilon is met or @param MAX_ITERATIONS is reached
		while (true)
		{
			iterations++;
			double[] current = new double[size];
			
			for (int i = 0; i < size; i++)
			{
				current[i] = matrixB[i];
				
				if (jacobi)
				{
					//Solves each variable using x matrix and stores result in @param current
					for (int j = 0; j < size; j++)
						if (i != j)
							current[i] -= matrixA[i][j] * previous[j];
				}
				else
				{
					//Solves each variable using x matrix and previously obtained solutions, then stores
					//result in @param current.
					for (int j = 0; j < size; j++)
					{
						if (i > j)
							current[i] -= matrixA[i][j] * current[j];
						else if (i < j)
							current[i] -= matrixA[i][j] * previous[j];
					}
				}
				
				current[i] /= matrixA[i][i];
			}
			
			double error = 0;
			
			//Compares current approximation with previous approximation to determine current error.
			for (double[] tempMatrix : matrixA)
			{
				double errorTemp = 0;
				for (int i = 0; i < size; i++)
				{
					errorTemp += tempMatrix[i] * current[i];
				}
				
				error += Math.abs(errorTemp - tempMatrix[size - 1]);
			}
			
			//Ends loop if conditions are met.
			if (error < epsilon || iterations >= MAX_ITERATIONS)
				break;
			
			previous = current;
			this.printIteration(previous, iterations);
		}
		
		return previous;
	}
	
	public void printIteration(double[] iter, int count)
	{
		int length = iter.length;
		System.out.print("x(" + count + "): ");
		for (int i = 0; i < length; i++)
			System.out.printf("%.3f ", iter[i]);
		
		System.out.println();
	}
	
	//Returns @param solution.
	public double[] getSolution()
	{
		return this.solution;
	}
}
