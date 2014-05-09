import java.util.ArrayList;

/**
 * Created by Stephen Yingling on 4/24/14.
 */
public class DBSCAN {
    public static ArrayList<Cluster> DBSCAN(ArrayList<DataPoint> dataset, double eps, int minpts, WeightParams wp){
        ArrayList<Cluster> clusters = new ArrayList<Cluster>();

        ArrayList<DataPoint> neighbors;
        for(DataPoint dp: dataset){
            if(!dp.isVisited()){
                dp.setVisited(true);
                neighbors = regionQuery(dp,eps,dataset,wp);
                if(neighbors.size() < minpts){
                    dp.setNoise(true);
                }
                else{
                    Cluster<Seed> c = new Cluster<Seed>();
                    clusters.add(c);
                    expandCluster(dp, neighbors, c, eps, minpts, dataset,wp);
                }
            }
        }

        return clusters;
    }

    public static void
                  expandCluster(DataPoint p,
                                ArrayList<DataPoint> neigh,
                                Cluster cluster,
                                double eps,
                                int minPts,
                                ArrayList<DataPoint> dataset,
                                WeightParams wp){
        cluster.addToCluster(p);
        for(int i=0; i<neigh.size(); i++){
            DataPoint<NoteGroup> pprime = neigh.get(i);
            if(!pprime.isVisited()){
                pprime.setVisited(true);
                ArrayList<DataPoint> NP = regionQuery(pprime,eps,dataset,wp);
                if(NP.size() >= minPts){
                    neigh.addAll(NP);
                }
            }
            if(!pprime.isClustered()){
                cluster.addToCluster(pprime);
            }
        }
    }

    public static ArrayList<DataPoint> regionQuery(DataPoint<NoteGroup> p,double eps, ArrayList<DataPoint> dataset, WeightParams wp){
        ArrayList<DataPoint> NP = new ArrayList<DataPoint>();
        for(DataPoint<NoteGroup> cur : dataset){
            if(cur.distanceTo(p,wp) < eps){
                NP.add(cur);
            }
        }

        return NP;
    }
}
