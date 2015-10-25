package EECEBP;

public class Main {

	public static void main()
	{
		int numOfUnitsInEachLayer[]= {2,4,1};
		
		//XOR training input
		double totalTrainingInput[][] = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } };
		
		//XOR training output
		double outputTrainingSets[][] ={{0},{1},{1},{1}};
		
		double learningRate= 0.2;
		double momentum= 0.0;
		double minimumError = 0.05;
		long maxIterations = 10000;
	 
	    ErrorBackPropagation  BP = new ErrorBackPropagation(numOfUnitsInEachLayer, totalTrainingInput, outputTrainingSets,learningRate, momentum, minimumError, maxIterations);
	    BP.runBP();
	    
	}
}
