package EECEBP;

public class Unit {
	
	   // Constructor
	   public Unit (int numberOfInputUnits) 
	    {
	    
	    	//Create an array of weight with the same size as the vector of inputs to the unit
	    	weight = new double[numberOfInputUnits];
	    	
	    	weightDiff = new double[numberOfInputUnits];
	    	
	    	//Initialize the weights and threshold to the node
	    	initializeWeights();
	    	
	    }
		
	    //vector of weights from previous units to current unit
	    public double weight[];

	    //weight difference between the (n-1)th and nth iteration
	    public double weightDiff[];
	    
	    public double output;
	    
	    public double signalError;
	
        //Initialize both bias and unit weights to random values in the range -0.5 to +0.5
	    private void initializeWeights(){
	    	
	    	for(int i=0; i<weight.length; i++)
	    	{
	    		//Initialize weights to random values in the range -0.5 to +0.5
	    		weight[i] = Math.random()-0.5;
	    		
	    		//Initially, weightDiff is assigned to 0 so that the momentum term
		    	//can work during the 1st iteration
	    		weightDiff[i] = 0;
	    	}  	

	    }
};
