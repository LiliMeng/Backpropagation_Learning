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

		//unit threshold / bias
	    public double threshold;

        //weight difference between the (n-1)th and nth iteration
	    public double weightDiff[];

		//threshold difference between the (n-1)th and nth iteration
	    public double thresholdDiff;
	    
	    //Output signal error
	    public double signalError;

        //Initialize weights to random values in the range -0.5 to +0.5
	    private void initializeWeights(){
	    	
	    	//Initialize threshold units with a random number between -0.5 and 0.5
	    	threshold = 0.5*(-1+2*Math.random());
	    	
	    	//Initially, thresholdDiff is assigned to 0 so that the momentum term
	    	//can work during the 1st iteration
	    	thresholdDiff = 0;
	    	
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

