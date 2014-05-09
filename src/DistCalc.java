/**
 * Created by Stephen Yingling on 4/24/14.
 */
public abstract class DistCalc {

    abstract Double calculateDistance(DistCalc dc,WeightParams wp);
    abstract DistCalc copy();
}
