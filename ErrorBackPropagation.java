package EECEBP;

public class ErrorBackPropagation {
	   	//Constructor
		public ErrorBackPropagation(int numOfUnitsInEachLayer[],
  				double totalTrainingInput[][],
  				double outputTrainingSets[][],
  				double learnRate,
  				double moment,
  				double minimumError,
  				long maxIteration)
	   			{
		   			numberOfTrainingSets = totalTrainingInput.length;
		   			minError = minimumError;
		   			learningRate = learnRate;
		   			momentum = moment;
		   			numberOfLayers = numOfUnitsInEachLayer.length;
		   			maxIter = maxIteration;
		   			layer = new Layer[numberOfLayers];
		   		
		   			//assign the number of units to the input layer
		   			layer[0] = new Layer(numOfUnitsInEachLayer[0], numOfUnitsInEachLayer[0]);

		   			//assign the number of units to each layer
		   			for(int i =1; i<numberOfLayers; i++)
		   			{
		   				layer[i] = new Layer(numOfUnitsInEachLayer[i],numOfUnitsInEachLayer[i-1]);
		   			}
		   			
		   			trainingInput = new double[numberOfTrainingSets][layer[0].unitVec.length];
		   			
		   			//Assign training set input to the trainingInput
		   			for(int numberOfTrainingSetIndex=0; numberOfTrainingSetIndex<numberOfTrainingSets; numberOfTrainingSetIndex++)
		   			{
		   				for(int j=0; j<layer[0].unitVec.length; j++)
		   				{
		   					trainingInput[numberOfTrainingSetIndex][j]=totalTrainingInput[numberOfTrainingSetIndex][j];
		   					
		   				}
		   			}
		   			
		   			
		   			expectedOutput = new double[numberOfTrainingSets][layer[numberOfLayers-1].unitVec.length];
		   		
		   			//Assign training set output to the expectedOutput
		   			for(int trainingSetIndex=0;  trainingSetIndex<numberOfTrainingSets;  trainingSetIndex++)
		   			{
		   				for(int j=0; j< layer[numberOfLayers-1].unitVec.length; j++)
		   				{
		   					expectedOutput[trainingSetIndex][j]=outputTrainingSets[trainingSetIndex][j];
//		   					System.out.printf("Expected output onlyiiii: %f\n",expectedOutput[trainingSetIndex][j]);
		   				}
		   			}
                   
		   			actualOutput = new double[numberOfTrainingSets][layer[numberOfLayers-1].unitVec.length];
		   			
	   			} 
	
		    // Total error 
			public double	totalError;

			//The minimum error defined by user. In this assignment, it's 0.05
			public double	minError;
			
			// User defined learning rate - used for updating the network weights
			private double	learningRate;

			// momentum - used for updating the network weights,  in assignment 1)a), momentum is 0, and the 1)c) momentum is 0.9
			private double	momentum;

			// Number of layers in the network - includes the input, output and hidden layers, in this assignment, 1 input layer, 1-hidden layer and 1 output layer
			private  int	numberOfLayers;
			
			// Public Variables
			public  Layer layer[];

			// The input for each neurons bias weight
			final double bias = 1.0;  

			// Number of training sets
			private  int numberOfTrainingSets;
			
			//training set Input
			private double[][] trainingInput;
			
			//training set Outputs
			private  double[][] expectedOutput;
			
			//Output Initialization
			private double[][] actualOutput;
			
			boolean stopTraining = false;
			long maxIter;

			// Calculate all the units feedFroward Propagation
			public void feedForwardPropagation()
			{
				//for the layer 0, the input value is equal to output value
				for(int j=0; j<layer[0].unitVec.length; j++)
				{
					layer[0].unitVec[j].output = layer[0].inputVec[j];
					layer[0].outputVec[j]=layer[0].inputVec[j];
					//System.out.println("layer[0]inputvec midddle " +j+" "+layer[0].inputVec[j]);
				}
				
				//System.out.println("+++++++0");
				//
				
				for(int i=0; i< numberOfLayers-1; i++)
				{
					for(int j=0; j<layer[i].outputVec.length; j++)
					{
						layer[i+1].inputVec[j] = layer[i].outputVec[j];
					}
					
					System.out.println("+++++++"+i);
					layer[i+1].feedForward();
					
				}
				
				
				/*System.out.printf("layer[1].unitVec[0].weight[0]: %f \n",layer[1].unitVec[0].weight[0]);
				System.out.printf("layer[1].unitVec[0].weight[1]: %f \n",layer[1].unitVec[0].weight[1]);
				System.out.printf("layer[1].unitVec[1].weight[0]: %f \n",layer[1].unitVec[1].weight[0]);
				System.out.printf("layer[1].unitVec[1].weight[1]: %f \n",layer[1].unitVec[1].weight[1]);
				System.out.printf("layer[1].unitVec[2].weight[0]: %f \n",layer[1].unitVec[2].weight[0]);
				System.out.printf("layer[1].unitVec[2].weight[1]: %f \n",layer[1].unitVec[2].weight[1]);
				System.out.printf("layer[1].unitVec[3].weight[0]: %f \n",layer[1].unitVec[3].weight[0]);
				System.out.printf("layer[1].unitVec[3].weight[1]: %f \n",layer[1].unitVec[3].weight[1]);
				*/
				
			} 
			
			//update the weights 
			public void updateWeights(int idx)
			{
				calculateSignalErrors(idx);
				backPropagationError();
			}
			
			
			//calculate signal errors
			private void calculateSignalErrors(int idx)
			{
				
				double sum;
				
				int outputLayer = numberOfLayers - 1;
				//System.out.println("outputLayer "+outputLayer);
//				for(int i=0; i<numberOfTrainingSets; i++)
//	   			{
//	   				for(int j=0; j< layer[numberOfLayers-1].unitVec.length; j++)
//	   				{
//	   					System.out.printf("Expected output only: %f\n",expectedOutput[i][j]);
//	   				}
//	   			}
				//calculate output signal error
				for(int i=0; i<layer[outputLayer].unitVec.length; i++)
				{
					layer[outputLayer].unitVec[i].signalError=layer[outputLayer].unitVec[i].output*(1-layer[outputLayer].unitVec[i].output)*(expectedOutput[idx][i]-layer[outputLayer].unitVec[i].output);
//					System.out.printf("error: %f\n ",expectedOutput[idx][i]-layer[outputLayer].unitVec[i].output);
				}
				
			    //calculate signal error for all units in the hidden layer
				for(int i=numberOfLayers-2; i>0; i--)
				{
					for(int j=0; j<layer[i].unitVec.length; j++)
					{
						sum = 0;
						
						for(int k=0; k<layer[i+1].unitVec.length; k++)    // i=0
						{
							sum = sum + layer[i+1].unitVec[k].weight[j]*layer[i+1].unitVec[k].signalError;
						}
						
						//for hidden layer units, the error signal is the weighted sum of the errors at the units above
						layer[i].unitVec[j].signalError = layer[i].unitVec[j].output*(1-layer[i].unitVec[j].output)*sum;
					}
				}
				
			}
			
			//calculate backPropoagationError
			private void backPropagationError()
			{
				//update weights
				for(int i = numberOfLayers - 1; i > 0; i--)
				{
					for(int j=0; j<layer[i].unitVec.length; j++)
					{
						//update weights
						
						//System.out.printf("layer[1].unitVec[0].weight[0]: %f middle before\n",this.layer[1].unitVec[0].weight[0]);
						for(int k=0; k<layer[i].inputVec.length; k++)
						{
							//System.out.printf("layer[%d].unitVec[%d].weight[%d]%f before update\n",i,j,k,layer[i].unitVec[j].weight[k]);
							this.layer[i].unitVec[j].weightDiff[k] = momentum*layer[i].unitVec[j].weightDiff[k]+learningRate*layer[i].unitVec[j].signalError*layer[i-1].unitVec[k].output;
							System.out.printf("layer[%d].unitVec[%d].output%f before update\n",i,j,layer[i-1].unitVec[k].output);
							this.layer[i].unitVec[j].weight[k]=layer[i].unitVec[j].weight[k]+layer[i].unitVec[j].weightDiff[k];
							//System.out.printf("layer[%d].unitVec[%d].weight[%d]%f after update111\n",i,j,k,layer[1].unitVec[0].weight[0]);
							
						}
						
						//System.out.printf("layer[1].unitVec[0].weight[0]: %f middle after\n",this.layer[1].unitVec[0].weight[0]);
						
						// Calculate Bias difference 
						layer[i].biasWeightDiff 
							= momentum*layer[i].biasWeightDiff+learningRate * layer[i].unitVec[j].signalError*bias;
						
						//update bias weight for unitVec j
						layer[i].biasWeight = layer[i].biasWeight+layer[i].biasWeightDiff;

					}
				}
			}
			
			//calculateTotalError
			private void calculateTotalError()
			{
				totalError=0;
				for(int i=0; i<numberOfTrainingSets; i++)
				{
					for(int j=0; j<layer[numberOfLayers-1].unitVec.length; j++)
					{
						totalError = totalError + 0.5*(Math.pow(expectedOutput[i][j]-actualOutput[i][j], 2));
					}
				}
			}
			
			public void trainNeuralNetwork()
			{
				long iter;
				
				for(iter=0; iter<1; iter++)
				{//Assign training set input to the trainingInput
					for(int trainingsetNum=0; trainingsetNum<numberOfTrainingSets; trainingsetNum++)
					{
						for(int i=0; i<layer[0].unitVec.length; i++)
						{
							layer[0].inputVec[i]=trainingInput[trainingsetNum][i];
							System.out.println("layer[0]inputvec " +i+" "+layer[0].inputVec[i]);
						}
	   			
						feedForwardPropagation();
						
						
						//System.out.printf("layer[1].unitVec[0].weight[0]: %f \n",layer[1].unitVec[0].weight[0]);
						//System.out.printf("layer[1].unitVec[0].weight[1]: %f \n",layer[1].unitVec[0].weight[1]);
						//System.out.printf("layer[1].unitVec[0].weight[0]: %f \n",layer[1].unitVec[0].weightDiff[0]);
						//System.out.printf("layer[1].unitVec[0].weight[1]: %f \n",layer[1].unitVec[0].weightDiff[1]);
						
					
						
	   			       
						//Assign calculated output vector from neural network to actualOutput
						for(int i=0; i<layer[numberOfLayers-1].unitVec.length; i++)
						{
							actualOutput[trainingsetNum][i]=layer[numberOfLayers-1].unitVec[i].output;
						  // System.out.println("actualOutput  "+trainingsetNum+ " "+i+" "+ actualOutput[trainingsetNum][i]);
						}
	   			    
						//System.out.printf("layer[1].unitVec[0].weight[0]: %f before\n",layer[1].unitVec[0].weight[0]);
						//System.out.printf("layer[1].unitVec[0].weight[1]: %f before \n",layer[1].unitVec[0].weight[1]);
						
						updateWeights(trainingsetNum);
						
					}
	  
					calculateTotalError();
					System.out.println("totalError is "+totalError);
									
				//System.out.println("When we have reached the minError, the iteration is " + iter);
				
			}
		}
			

			// to notify the network to stop training.
			public void kill() 
			{ 
				stopTraining = true; 
			}
			
			public static void main(String[] args)
			{
				int numOfUnitsInEachLayer[]= {2,4,1};
				
				//XOR training input
				double totalTrainingInput[][] = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } };
				
				//XOR training output
				double outputTrainingSets[][] ={{0},{1},{1},{0}};
				
				double learningRate= 0.2;
				
				double momentum= 0;
				
				double minimumError = 0.05;
				
				long maxIteration=10000;
			 
			    ErrorBackPropagation  BP = new ErrorBackPropagation(numOfUnitsInEachLayer, totalTrainingInput, outputTrainingSets,learningRate, momentum, minimumError, maxIteration);
			    
			    BP.trainNeuralNetwork();
			    //System.out.println("totalError" + BP.totalError);
			    
			}

}



