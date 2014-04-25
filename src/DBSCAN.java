import java.util.ArrayList;

/**
 * Created by Stephen Yingling on 4/24/14.
 */
public class DBSCAN {

    public static void DBSCAN(ArrayList<DataPoint<NoteGroup>> dataset, double eps, int minpts){
        ArrayList<Cluster<NoteGroup>> clusters = new ArrayList<Cluster<NoteGroup>>();

        ArrayList<DataPoint<NoteGroup>> neighbors;
        for(DataPoint<NoteGroup> dp: dataset){
            if(!dp.isVisited()){
                dp.setVisited(true);
                neighbors = regionQuery(dp,eps,dataset);
                if(neighbors.size() < minpts){
                    //mark all as noise
                }
                else{
                    Cluster<NoteGroup> c = new Cluster<NoteGroup>();
                    clusters.add(c);
                    expandCluster(dp, neighbors, c, eps, minpts, dataset);
                }
            }
        }
    }

    public static void
                  expandCluster(DataPoint<NoteGroup> p,
                                ArrayList<DataPoint<NoteGroup>> neigh,
                                Cluster cluster,
                                double eps,
                                int minPts,
                                ArrayList<DataPoint<NoteGroup>> dataset){
        //add p to C
        for(int i=0; i<neigh.size(); i++){
            DataPoint<NoteGroup> pprime = neigh.get(i);
            if(!pprime.isVisited()){
                pprime.setVisited(true);
                ArrayList<DataPoint<NoteGroup>> NP = regionQuery(pprime,eps,dataset);
                if(NP.size() >= minPts){
                    neigh.addAll(NP);
                }
            }
            if(!pprime.isClustered()){
                cluster.addToCluster(pprime);
            }
        }
    }

    public static ArrayList<DataPoint<NoteGroup>> regionQuery(DataPoint<NoteGroup> p,double eps, ArrayList<DataPoint<NoteGroup>> dataset){
        ArrayList<DataPoint<NoteGroup>> NP = new ArrayList<DataPoint<NoteGroup>>();
        for(DataPoint<NoteGroup> cur : dataset){
            if(cur.distanceTo(p) < eps){
                NP.add(cur);
            }
        }

        return NP;
    }
}
