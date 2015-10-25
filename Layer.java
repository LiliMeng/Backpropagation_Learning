package EECEBP;

public class Layer {
	//Constructor
	public Layer(int numberOfUnits, int numberOfInputs)
	{
		unitVec = new Unit[numberOfUnits];
		
		for(int i =0; i< numberOfUnits; i++)
		{
			unitVec[i] = new Unit(numberOfInputs);
		}
		
		inputVec = new double[numberOfInputs];
	}
	
	//vector of input signals from previous layer to the current layer
    public double inputVec[];
    
    //vector of output signals from the current layer
    public double outputVec[];
    
    //vector of units in current layer
    public Unit unitVec[];
    
    private double value; 
    
	//The feedForward propagation
    public void feedForward()
    {
    	for(int i=0; i<unitVec.length; i++)
    	{
    		value = unitVec[i].threshold;
    		
    		for(int j=0; j<unitVec[i].weight.length; j++)
    		{
    			value = value + inputVec[j]*unitVec[i].weight[j];
    		}
    		
    		unitVec[i].output = binarySigmoid(value);
    	}
    	
    }

    
    /**
	 * Return a binary sigmoid of the input X
	 * @param x The input
	 * @return f(x) = 2 / (1+e(-x)) -1
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
	
   //Return the output from all node in the layer in a vector form
	public double[] outputVector()
	{
		outputVec = new double[unitVec.length];
		
		for(int i=0; i<unitVec.length; i++)
		{
			outputVec[i]=unitVec[i].output;
		}
		
		return (outputVec);
	}

}
