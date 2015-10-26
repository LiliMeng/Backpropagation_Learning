package EECEBP;

public class Unit {
	
	   // Constructor
	   public Unit (int numberOfUnits) 
	    {
	    	//Create an array of weight with the same size as the vector of inputs to the unit
	    	weight = new double[numberOfUnits];
	    	
	    	weightDiff = new double[numberOfUnits];
	    	
	    	//Initialize the weights and threshold to the node
	    	initializeWeights();
	    	
	    }

		//Output signal from current unit
	    public double output;
		
	    //vector of weights from previous units to current unit
	    public double weight[];

		//unit bias
	    public double bias = 1.0;
	    
	    //unit bias weight
	    public double biasWeight;

        //weight difference between the (n-1)th and nth iteration
	    public double weightDiff[];

		//bias difference between the (n-1)th and nth iteration
	    public double biasDiff;
	    
	    //Output signal error
	    public double signalError;

        //Initialize both bias and unit weights to random values in the range -0.5 to +0.5
	    private void initializeWeights(){
	    	
	    	//Initialize the bias units with a random number between -0.5 and 0.5
	    	biasWeight = 0.5*(-1+2*Math.random());
	    	
	    	bias=bias*biasWeight;
	    	
	    	//Initially, thresholdDiff is assigned to 0 so that the momentum term
	    	//can work during the 1st iteration
	    	biasDiff = 0;
	    	
	    	for(int i=0; i<weight.length; i++)
	    	{
	    		//Initialize weights to random values in the range -0.5 to +0.5
	    		weight[i] = 0.5*(-1+2*Math.random());
	    		
	    		//Initially, weightDiff is assigned to 0 so that the momentum term
		    	//can work during the 1st iteration
	    		weightDiff[i] = 0;
	    	}  			
	    }
};


