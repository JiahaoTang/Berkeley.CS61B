import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator{
	int period;
	int state;
    double accelerator;

	public AcceleratingSawToothGenerator(int period, double accelerator){
		state = 0;
		this.period = period;
        this.accelerator = accelerator;
	}

    @Override
    public double next(){
    	if(state == period - 1){
    		state = 0;
            period = (int)(accelerator * period);
    	}else{
    		state += 1;
    	}
    	
        return normalize(state);
    }

    public double normalize(int value){
    	return (double)value / (double)period * 2 - 1.0;
    }
}
