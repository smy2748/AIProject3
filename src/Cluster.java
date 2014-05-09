import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

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
        s +="\n DataPoints: " +in.size()+"\n\n";
        for(DataPoint<T> dp : in){
            s += dp + "\n";
        }
        return s;
    }

    public int getClusterNumber() {
        return clusterNumber;
    }

    public int[] getClassCounts(){
        int[] classCount = new int[3];

        for(DataPoint dp : in){
            Seed s = (Seed) dp.getData();
            classCount[s.getClassNum()-1] ++;
        }

        return classCount;
    }

    public int determineClass(){
        HashMap<Integer,Integer> counts = new HashMap<Integer, Integer>();
        for(DataPoint dp : in){
            Seed s = (Seed) dp.getData();
            if(!counts.containsKey(s.getClassNum())){
                counts.put(s.getClassNum(),1);
            }

            else{
                counts.put(s.getClassNum(), counts.get(s.getClassNum())+1);
            }
        }

        if(counts.size() == 1){
            ArrayList<Integer> i = new ArrayList<Integer>(counts.keySet());
            return i.get(0);
        }

        int max = 0, cnum = -1;
        boolean nonDetr = false;

        for(Integer classNum : counts.keySet()){

            if(.25 * counts.get(classNum) < max && counts.get(classNum) >= max){
                return -1;
            }

            if(.25 * counts.get(classNum) > max){
                max = counts.get(classNum);
                cnum = classNum;
            }



        }

        return cnum;
    }
}
