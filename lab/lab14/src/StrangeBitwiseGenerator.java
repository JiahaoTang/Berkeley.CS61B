import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator{
	int period;
	int state;

	public StrangeBitwiseGenerator(int period){
		state = 0;
		this.period = period;
	}

    @Override
    public double next(){
    	state = state + 1;
        int weirdState = state & (state >> 7) % period;
    	
        return normalize(weirdState);
    }

    public double normalize(int value){
    	return (double)value / (double)period * 2 - 1.0;
    }
}
