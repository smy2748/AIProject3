/**
 * Created by Stephen Yingling on 4/24/14.
 */
public class PitchBox {

    public static int boxPitch(double pitchMean){
        if(pitchMean < 65){
            return -1;
        }
        if(pitchMean > 70){
            return 1;
        }

        return 0;
    }
}
