import java.util.ArrayList;

/**
 * Created by Stephen Yingling on 4/24/14.
 */
public class Cluster<T extends DistCalc> {

    protected ArrayList<DataPoint<T>> in;


    public Cluster(){
        in = new ArrayList<DataPoint<T>>();
    }

    public void addToCluster(DataPoint<T> p){
        in.add(p);
        p.setClustered(true);
    }

    public ArrayList<DataPoint<T>> getIn() {
        return in;
    }

    public void setIn(ArrayList<DataPoint<T>> in) {
        this.in = in;
    }
}
