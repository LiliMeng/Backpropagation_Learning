package EECEBP;

public class Layer {
	//Constructor
	public Layer(int numberOfUnits, int numberOfInputs)
	{   
		unitVec = new Unit[numberOfUnits];
		
		for(int i = 0; i< numberOfUnits; i++)
		{
			//numberOfInputs for the usual input
			unitVec[i] = new Unit(numberOfInputs);
		}	
		
		inputVec = new double[numberOfInputs];
		
		valueVec = new double[numberOfUnits];
		
		for(int i = 0; i< numberOfUnits; i++)
		{
			//numberOfInputs for the usual input
			valueVec[i] =0;
		}	
		
		
		outputVec = new double[numberOfUnits];
		
		initializeBiasWeight();
		
	}
	
	//vector of input signals from previous layer to the current layer
    public double inputVec[];
    
    //vector of output signals from the current layer
    public double outputVec[];
    
    //vector of units in current layer
    public Unit unitVec[];
    
    public double valueVec[]; 
    
	//unit bias
    public double bias;
    
    //unit bias weight
    public double biasWeight;
    
	//bias weight difference between the (n-1)th and nth iteration
    public double biasWeightDiff;
    
    public void initializeBiasWeight(){
    	
    	//Initialize the bias units with a random number between -0.5 and 0.5
    	biasWeight = Math.random()-0.5;
    	
    	//Initially, biasDiff is assigned to 0 so that the momentum term
    	//can work during the 1st iteration
    	biasWeightDiff = 0;
    	
    }
	
	//The feedForward propagation
    public void feedForward()
    {
    	for(int i=0; i<unitVec.length; i++)
    	{
    		
    		for(int j=0; j<unitVec[i].weight.length; j++)
    		{
    			//System.out.printf("inputVec[i]*unitVec[i].weight[j]:%f,%f\n",inputVec[j],unitVec[i].weight[j]);
    			valueVec[i] = valueVec[i] + inputVec[j]*unitVec[i].weight[j];
    			
    			//System.out.printf("inputVec[i]*unitVec[i].weight[j]:%f,%f\n",inputVec[j],unitVec[i].weight[j]);
    		}
    		
    		valueVec[i] = valueVec[i] + bias*biasWeight;
    		
    		unitVec[i].output = binarySigmoid(valueVec[i]);
    		
    		outputVec[i]=unitVec[i].output;
    	}
    }

    
    /**
	 * Return a binary sigmoid of the input X
	 * @param x The input
	 * @return f(x) = 1 / (1+e(-x)) 
	 */
	public double binarySigmoid(double x)
	{
		return 1/(1+Math.exp(-x));
	}
	
	/**
	 * Return a bipolar Sigmoid of the input X
	 * @param x The input
	 * @return f(x) = 2 / (1+e(-x)) -1
	 */
	public double bipolarSigmoid(double x)
	{
		return 2/(1+Math.exp(-x))-1;
	}

}
