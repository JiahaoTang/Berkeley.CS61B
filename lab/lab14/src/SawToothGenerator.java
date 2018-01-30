import lab14lib.Generator;

public class SawToothGenerator implements Generator{
	int period;
	int state;

	public SawToothGenerator(int period){
		state = 0;
		this.period = period;
	}

    @Override
    public double next(){
    	if(state == period - 1){
    		state = 0;
    	}else{
    		state += 1;
    	}
    	
        return normalize(state);
    }

    public double normalize(int value){
    	return (double)value / (double)period * 2 - 1.0;
    }
}
