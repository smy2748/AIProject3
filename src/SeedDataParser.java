import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Stephen Yingling on 5/6/14.
 */
public class SeedDataParser {
    protected File file;

    public SeedDataParser(){}

    public SeedDataParser(File f){
        file = f;
    }

    public ArrayList<Seed> parseFile() throws FileNotFoundException {
        ArrayList<Seed> res =  new ArrayList<Seed>();

        if(file != null){
            Scanner sc = new Scanner(file);
            while (sc.hasNext()){
                String s = sc.nextLine();
                String[] splits = s.replace("\t\t","\t").split("\t");
                Seed seed= new Seed();
                seed.setArea(Double.parseDouble(splits[0]));
                seed.setPerimeter(Double.parseDouble(splits[1]));
                seed.setCompactness(Double.parseDouble(splits[2]));
                seed.setLength(Double.parseDouble(splits[3]));
                seed.setWidth(Double.parseDouble(splits[4]));
                seed.setAsymmetry(Double.parseDouble(splits[5]));
                seed.setGroove(Double.parseDouble(splits[6]));
                seed.setClassNum(Integer.parseInt(splits[7]));

                res.add(seed);
            }
        }

        return res;
    }


    public static void main(String args[]) throws IOException {
        SeedDataParser sdp = new SeedDataParser(new File("seeds_dataset.txt"));
        ArrayList<Seed> seeds =sdp.parseFile();

        SeedDataPreprocessor sdpp = new SeedDataPreprocessor();
        sdpp.initialPass(seeds);

        FileWriter fw = new FileWriter(new File("seeds.csv"));
        ArrayList<DataPoint> points = new ArrayList<DataPoint>();
        for(Seed s : seeds){
            points.add(new DataPoint(s));
            fw.write(s.toCSV()+"\n");
        }

        fw.close();

        sdpp.normalize(seeds);

        fw = new FileWriter(new File("seeds_norm.csv"));
        points = new ArrayList<DataPoint>();
        for(Seed s : seeds){
            points.add(new DataPoint(s));
            fw.write(s.toCSV()+"\n");
        }

        fw.close();

        ArrayList<DataPoint> used = WeightFindingGenAlg.cloneList(points);
        ArrayList<Cluster> clusters = DBSCAN.DBSCAN(used,.1562,7, new WeightParams(1,.889,.8882,.8882,.8941,.780796,1,7,.15626));


        printConfusionMatr(clusters, used);
        WeightFindingGenAlg gen = new WeightFindingGenAlg();
        WeightParams best = gen.runAlgOnDataSet(points,250,1000);

        System.out.println(best);

        used = WeightFindingGenAlg.cloneList(points);
        clusters = DBSCAN.DBSCAN(used,best.getEps(),best.getMinClustSize(), best);
        printConfusionMatr(clusters, used);



    }

    public static void printConfusionMatr(List<Cluster> clusters, List<DataPoint> pts){

        int clus=0, noise=0;

        for(DataPoint dp : pts){
            if(dp.isNoise()){
                noise++;
            }
            if(dp.isClustered()){
                clus++;
            }
        }

        System.out.println("Out of " + pts.size() + " data points:");
        System.out.println(noise + " were marked as noise (unclustered).");
        System.out.println(clus + " were clustered.\n");
        System.out.println(((double)clus /(double)pts.size())*100 + "% of all points were clustered.\n");


        String classNums = "";
        for(int i=1; i<=3; i++){
            classNums += i +"\t";
        }
        classNums += "<-- Class of Points";

        System.out.println(classNums);
        for(Cluster c : clusters){
            for(int i : c.getClassCounts()){
                System.out.print(i +"\t");
            }
            System.out.println("Cluster " + c.clusterNumber + " (assigned as class " + c.determineClass() +")");
        }


    }
}
