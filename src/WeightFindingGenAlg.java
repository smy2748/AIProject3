import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Stephen Yingling on 5/6/14.
 */
public class WeightFindingGenAlg {

    public WeightFindingGenAlg(){}

    public WeightParams runAlgOnDataSet(ArrayList<DataPoint> data, int maxRounds, int minScore){
        ArrayList<DataPoint> inUse;
        WeightParams wp = new WeightParams(1,.889,.8882,.8882,.8941,.780796,1,7,.15626);
        ArrayList<WeightParams> curGen = new ArrayList<WeightParams>();
        curGen.add(wp);
        for(int i=0; i<3;i++){
            curGen.add(wp.mutantBud());
        }
        WeightParams best = wp;
        int score = 0;
        int round =0;


        while (round < maxRounds){
            for(WeightParams params : curGen){
                inUse = cloneList(data);
                ArrayList<Cluster> clusters = DBSCAN.DBSCAN(inUse,params.getEps(),params.getMinClustSize(),params);
                int curScore = scoreParams(clusters,params,inUse);
                params.setScore(curScore);

                if(score < curScore){
                    score = curScore;
                    best = params;
                }
            }

            curGen = breed(curGen);


            round++;
        }

        return best;
    }

    public static ArrayList<WeightParams> breed(ArrayList<WeightParams> wps){
        ArrayList<WeightParams> res = new ArrayList<WeightParams>();
        Collections.sort(wps, new ScoreComparator());


        WeightParams highest = wps.get(wps.size()-1),
                     secHigh = wps.get(wps.size()-2),
                     lowest = wps.get(0),
                     secLowest = wps.get(1);

        res.addAll(highest.breed(secHigh));
        res.addAll(highest.breed(lowest));
        res.addAll(secHigh.breed(lowest));
        res.addAll(secHigh.breed(secLowest));
        res.addAll(lowest.breed(secLowest));

        for(int i=0; i< 5; i++){
            res.add(highest.mutantBud());
        }

        return res;
    }

    public static int scoreParams(ArrayList<Cluster> clusters, WeightParams wp, ArrayList<DataPoint> pts){
        int score = 0;

        double expectedNoise = pts.size() * .1;
        int numNoise = 0;
        for(DataPoint dp : pts){
            if(dp.isNoise()){
                numNoise++;
            }

        }

        if(numNoise > expectedNoise){
            score -= .4 * (numNoise - expectedNoise);
        }
        if(clusters.size() >=3){
            score+=10;

            if(clusters.size() <= 7){
                score += 10 - clusters.size();
            }
        }



        if(clusters.size() >= 20){
            score -= 20;
        }

        int type[] = new int [3];
        for(Cluster c : clusters){
            ArrayList<DataPoint> pInClust = c.getIn();
            for(DataPoint p : pInClust){
                Seed s = (Seed)p.getData();
                type[s.getClassNum()-1] +=1;
            }

            if(c.determineClass() >0){
                score +=3;
            }
            else{
                score -= 5 * (c.getIn().size() *.25);
            }
            if((type[0] > 0 && type[1] == 0 && type[2] == 0) ||
               (type[0] == 0 && type[1] > 0 && type[2] == 0)||
               (type[0] == 0 && type[1] == 0 && type[2] > 0)){
                score += 7;
            }
            else{
                if(type[0] == 0){
                    score -= Math.min(type[1],type[2]);
                }
                if(type[1] == 0){
                    score -= Math.min(type[0],type[2]);
                }
                if(type[2] == 0){
                    score -= Math.min(type[1],type[0]);
                }

                if(type[1] != 0 && type[2] != 0 && type[0] != 0 ){
                    score -= Math.max(Math.max(Math.min(type[1],type[0]), Math.min(type[1],type[2])),Math.min(type[0],type[2]));
                }
            }

            type = new int[3];
        }

        return score;
    }

    public static ArrayList<DataPoint> cloneList(List<DataPoint> orig){
        ArrayList<DataPoint> result = new ArrayList<DataPoint>();

        for(DataPoint dp : orig){
            result.add(dp.copyData());
        }

        return result;
    }
}
