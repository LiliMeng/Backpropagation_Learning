package EECEBP;

public class ErrorBackPropagation {
	   
		//Constructor
		public ErrorBackPropagation(int numOfUnits[],
  				double totalTrainingInput[][],
  				double outputTrainingSets[][],
  				double learnRate,
  				double moment,
  				double minimumError,
  				long maximumIterations)
	   			{
					int i, j;
		   			numberOfTrainingSets = totalTrainingInput.length;
		   			minError=minimumError;
		   			learningRate = learnRate;
		   			momentum = moment;
		   			numberOfLayers = numOfUnits.length;
		   			maxIter = maximumIterations;

		   			layer = new Layer[numberOfLayers];

		   			//assign the number of node to the input layer
		   			layer[0] = new Layer(numOfUnits[0], numOfUnits[0]);

		   			//assign the number of node to each layer
		   			for(i =1; i<numberOfLayers; i++)
		   			{
		   				layer[i] = new Layer(numOfUnits[i],numOfUnits[i-1]);
		   			}

		   			trainingInput = new double[numberOfTrainingSets][layer[0].unitVec.length];
		   			expectedOutput = new double[numberOfTrainingSets][layer[numberOfLayers-1].unitVec.length];
		   			actualOutput = new double[numberOfTrainingSets][layer[numberOfLayers-1].unitVec.length];

		   			//Assign training set
		   			for(i=0; i<numberOfTrainingSets; i++)
		   			{
		   				for(j=0; j< layer[numberOfLayers-1].unitVec.length; j++)
		   				{
		   					expectedOutput[i][j]=outputTrainingSets[i][j];
		   				}
		   			}

	   			} 
	
			private double	overallError=0;

			//The minimum error defined by user. In this assignment, it's 0.05
			private double	minError;
			
			// User defined learning rate - used for updating the network weights
			private double	learningRate=0.2;

			// Users defined momentum - used for updating the network weights
			private double	momentum;

			// Number of layers in the network - includes the input, output and hidden layers, in this assignment, 1 input layer, 4-hidden layer and 1 output layer
			private  int	numberOfLayers;
			
			// Public Variables
			public  Layer layer[];

			// The input for each neurons bias weight
			final double bias = 1.0;  

			
			// Number of training sets
			private  int numberOfTrainingSets;


			// Maximum number of Epochs before the training stops training - user defined
			private long maxIter;
	        
			
			
			//training set Input
			private double[][] trainingInput;
			
			//training set Outputs
			private  double[][] expectedOutput;
			
			//Output Initialization
			private double[][] actualOutput;
			private double output[];
			
			
			// Calculate all the units feedFroward Propagation
			public void feedForwardPropagation(){

				int i,j;
				//for the layer 0, the input value is equal to output value
				for(i=0; i<layer[0].unitVec.length; i++)
				{
					layer[0].unitVec[i].output = layer[0].inputVec[i];
				}
				
				layer[1].inputVec=layer[0].inputVec;
				
				for(i=1; i< numberOfLayers; i++)
				{
					layer[i].feedForward();
					
					if(i!=numberOfLayers-1)
					{
						layer[i+1].inputVec = layer[i].outputVector();
					}
				}
			} 
			
			//update the weights 
			public void updateWeights()
			{
				calculateSignalErrors();
				backPropagationError();
			}
			
			
			//calculate signal errors
			private void calculateSignalErrors()
			{
				int i, j, k;
				double sum;
				
				int outputLayer = numberOfLayers - 1;
				
				//calculate all output signal error
				for(i=0; i<layer[outputLayer].unitVec.length; i++)
				{
					layer[outputLayer].unitVec[i].signalError=layer[outputLayer].unitVec[i].output*(1-layer[outputLayer].unitVec[i].output)*(expectedOutput[outputLayer][i]-layer[outputLayer].unitVec[i].output);
				}
				
			    //calculate signal error for all units in the hidden layer 
				for(i=numberOfLayers-2; i>0; i--)
				{
					for(j=0; j<layer[i].unitVec.length; j++)
					{
						sum = 0;
						for(k=0; k<layer[i+1].unitVec.length; k++)
						{
							sum = sum + layer[i+1].unitVec[k].weight[j]*layer[i+1].unitVec[k].signalError;
						}
					}
				}
				
			}

			
			//calculate backPropoagationError
			private void backPropagationError()
			{
				int i, j, k;
				
				//update weights
				for(i = numberOfLayers - 1; i>0; i--)
				{
					for(j=0; j<layer[i].unitVec.length; j++)
					{
						//calculate bias weight difference to unit j;
						layer[i].unitVec[j].thresholdDiff = learningRate*layer[i].unitVec[j].signalError+ momentum*layer[i].unitVec[j].thresholdDiff;
						
						//update bias weight to node j
						layer[i].unitVec[j].threshold = layer[i].unitVec[j].threshold + layer[i].unitVec[j].thresholdDiff;
						
						//update weights
						
						for(k=0; k<layer[i].inputVec.length; k++)
						{
							layer[i].unitVec[j].weightDiff[k] = learningRate* layer[i].unitVec[j].signalError*layer[i-1].unitVec[k].output
									+momentum*layer[i].unitVec[j].weightDiff[k];
							
							//update weight between node j and k
							layer[i].unitVec[j].weight[k]=layer[i].unitVec[j].weight[k]+layer[i].unitVec[j].weightDiff[k];
						}
					}
				}
			}
			
			//calculateOverallError
			private void calculateOverallError()
			{
				for(int i=0; i<numberOfTrainingSets; i++)
				{
					for(int j=0; j<layer[numberOfLayers-1].unitVec.length; j++)
					{
						overallError = overallError + 0.5*(Math.pow(expectedOutput[i][j]-actualOutput[i][j], 2));
					}
				}
			}

}
