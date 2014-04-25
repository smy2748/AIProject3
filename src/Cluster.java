import java.util.ArrayList;

/**
 * Created by Stephen Yingling on 4/24/14.
 */
public class Cluster<T extends DistCalc> {

    protected ArrayList<DataPoint<T>> in;
    protected int clusterNumber;
    protected static int counter =0;

    public Cluster(){
        in = new ArrayList<DataPoint<T>>();
        clusterNumber = counter;
        counter ++;
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

    @Override
    public String toString(){
        String str = "Cluster: " + clusterNumber + inCluster();

        return str;
    }

    public String inCluster(){
        String s = "";
        s +="\n NoteGroups: \n\n";
        for(DataPoint<T> dp : in){
            s += dp + "\n";
        }
        return s;
    }
}
