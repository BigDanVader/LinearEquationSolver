package LEPackage;

/*Dan Luoma
 *CS 3010.01
 *Programming Assn. #1
 *October 16th, 2018*/

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class driver {

	/*Driver file for @class GaussMatrix and @class GaussIterative.
	* Takes number of equations, lets user choose between manual input or importing a file,
	* then solves the system of equations using the specified method.
	*/
	public static void main(String[] args) throws IOException 
	{
		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
		System.out.println("Enter number of equations to be used: ");
		int equations = s.nextInt();
		double[][] matrixA = new double[equations][equations];
		double[] matrixB = new double[equations];
		double[] solution = new double[equations];
		
		System.out.println("Select desired input; (1): Import file  (2): User input: ");
		int choice = s.nextInt();
		
		switch (choice) 
		{
			case 1:
				System.out.println("Enter file name: ");
				File file = new File(s.next() + ".txt");
				Scanner f = new Scanner(file);
				while (f.hasNextDouble())
				{
					for (int i = 0; i < equations; i++)
					{
						for (int j = 0; j < equations; j++)
						{
							matrixA[i][j] = f.nextDouble();
						}
						
						matrixB[i] = f.nextDouble();
					}
				}
				break;
				
			case 2:
				System.out.println("Enter coefficients of first equation followed by B matrix componant: ");
				
				for (int i = 0; i < equations; i++)
				{
					matrixA[0][i] = s.nextDouble();
				}
				matrixB[0] = s.nextDouble();
				
				for (int i = 1; i < equations; i++)
				{
					System.out.println("Enter next equation: ");
					
					for (int j = 0; j < equations; j++)
					{
						matrixA[i][j] = s.nextDouble();
					}
					
					matrixB[i] = s.nextDouble();
				}
				break;
		}
		
		System.out.println("Choose desired operation; (1) Scaled Partial Pivoting, (2) Jacobi, (3) Guass-Seidel: ");
		int choice2 = s.nextInt();
		
		switch (choice2)
		{
			case 1:
				GaussMatrix gm = new GaussMatrix(matrixA, matrixB);
				gm.solve();
				solution = gm.getSolution();
				break;
				
			case 2:
				System.out.println("Enter desired error: ");
				double epsilon = s.nextDouble();
				GaussIterative gi = new GaussIterative(matrixA, matrixB, epsilon, true);
				gi.solve();
				solution = gi.getSolution();
				break;
				
			case 3:
				System.out.println("Enter desired error: ");
				double eps = s.nextDouble();
				GaussIterative gig = new GaussIterative(matrixA, matrixB, eps, false);
				gig.solve();
				solution = gig.getSolution();
				break;
		}
		
		System.out.println("Solutions: ");
		for (int i = 0; i < equations; i ++)
		{
			System.out.println("x(" + (i + 1) + ") = " + solution[i]);
		}
		
	}

}
