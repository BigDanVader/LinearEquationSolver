package LEPackage;

/*Dan Luoma
 *CS 3010.01
 *Programming Assn. #1
 *October 16th, 2018*/
public class GaussMatrix {
	/*A class for performing scaled partial pivoting Guassian elimination on a system of equations.
	 * Stores the A and B matrix, performs the SPP elimination, and stores and outputs the result for each variable.
	*/
	
	private double[][] matrixA;
	private double[] matrixB;
	private double[] solution;

	//Constructor
	public GaussMatrix(double[][]A, double[] B)
	{
		this.matrixA = new double[B.length][B.length];
		this.matrixB = new double[B.length];
		this.solution = new double[B.length];
		
		for (int i = 0; i < B.length; i++)
		{
			this.matrixB[i] = B[i];
			
			for (int j = 0; j < B.length; j++)
				this.matrixA[i][j] = A[i][j];
		}
	}
	
	//Public method user can call to solve linear system of equations.
	public void solve()
	{
		this.solution = this.privateSolve(this.matrixA, this.matrixB);
	}
	
	//Private method called by public solve() method.
	private double[] privateSolve(double[][] aMatrix, double[]bMatrix)
	{
		int length = bMatrix.length;
		
		//Creates an array of largest coefficients of the equations.
		for (int i = 0; i < length; i++) 
		{
			int max = i;
			
			for (int j = i + 1; j < length; j++) 
			{
				if (Math.abs(aMatrix[j][i]) > Math.abs(aMatrix[max][i]))
					max = j;
			}
			//Places next pivot in topmost non-pivot position.
			double[]temp = aMatrix[i];
			aMatrix[i] = aMatrix[max];
			aMatrix[max] = temp;
			
			double t = bMatrix[i];
			bMatrix[i] = bMatrix[max];
			bMatrix[max] = t;
			
			//Performs reduction of other equations to isolate variables.
			for (int k = i + 1; k < length; k++)
			{
				double factor = aMatrix[k][i] / aMatrix[i][i];
				bMatrix[k] -= factor * bMatrix[i];
				
				for (int l = i; l < length; l++) 
				{
					aMatrix[k][l] -= factor * aMatrix[i][l];
				}
			}
			
			printMatrix(aMatrix, bMatrix);	
		}
		
		//Goes through altered matrix and produces an array of solutions for each variable.
		double[] solution = new double[length];
		for (int i = length - 1; i >= 0; i--)
		{
			double sum = 0.0;
			for (int j = i + 1; j < length; j++)
			{
				sum += aMatrix[i][j] * solution[j];
			}
			solution[i] = (bMatrix[i] - sum) / aMatrix[i][i];
		}
		
		return solution;
		
	}

	//Prints the contents of @param matrixA and @param matrixB in matrix form.
	public void printMatrix(double[][] aMatrix, double[] bMatrix) 
	{
		int length = bMatrix.length;
		
		for (int i = 0; i < length; i++)
		{
			for (int j = 0; j < length; j++)
			{
				System.out.printf("%.3f ", aMatrix[i][j]);
			}
			System.out.printf("| %.3f\n", bMatrix[i]);
		}
		
		System.out.println();
	}
	
	//Returns @param solution.
	public double[] getSolution()
	{
		return this.solution;
	}
}
