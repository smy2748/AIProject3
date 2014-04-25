import java.util.HashMap;

/**
 * Created by Stephen Yingling on 4/24/14.
 */
public class DataPoint<T extends DistCalc> {

    protected T data;
    protected boolean visited;
    protected boolean noise = false;
    protected boolean clustered = false;
    protected HashMap<DataPoint<T>,Double> pointDistance;

    public DataPoint(){
        visited = false;
        pointDistance = new HashMap<DataPoint<T>, Double>();
    }

    public DataPoint(T data){
        this.data = data;
        visited = false;
        pointDistance = new HashMap<DataPoint<T>, Double>();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isNoise() {
        return noise;
    }

    public void setNoise(boolean noise) {
        this.noise = noise;
    }

    public boolean isClustered() {
        return clustered;
    }

    public void setClustered(boolean clustered) {
        this.clustered = clustered;
    }

    public void setDist(DataPoint other, Double dist){
        if(!pointDistance.containsKey(other)){
            pointDistance.put(other,dist);
        }
    }

    public Double distanceTo(DataPoint<T> dp){

        if(pointDistance.containsKey(dp)){
            return pointDistance.get(dp);
        }

        Double d = distanceTo(dp);
        pointDistance.put(dp,d);
        return d;
    }
}
