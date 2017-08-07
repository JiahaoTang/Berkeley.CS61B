/**
 * Created by Administrator on 2017/8/7.
 */
public class GuitarHero {
    private static final Double StdFrequency = 440.0;
    private static final String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public static void main(String[] args){
        /*Create a array of 37 GuitarString Object*/
        synthesizer.GuitarString[] keyboardObject = new synthesizer.GuitarString[37];
        for(int i = 0; i < 37; i++){
            keyboardObject[i] = new synthesizer.GuitarString(440.0 * Math.pow(2, (i -24) / 12));
        }

        while(true){

            /*check if the user has typed a key; if so, process it.*/
            if(StdDraw.hasNextKeyTyped()){
                char key = StdDraw.nextKeyTyped();
                keyboardObject[keyboard.indexOf(key)].pluck();
            }

            /*compute the superposition of samples.*/
            Double sample = 0.0;
            for(synthesizer.GuitarString keyObject:keyboardObject){
                sample += keyObject.sample();
            }

            /*play the sample on the standard audio.*/
            StdAudio.play(sample);

            for(synthesizer.GuitarString keyObject:keyboardObject){
                keyObject.tic();
            }
        }
    }
}
