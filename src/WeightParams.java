import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Created by Stephen Yingling on 5/6/14.
 */
public class WeightParams {
    protected double areaWeight;
    protected double perimWeight;
    protected double compactWeight;
    protected double lengthWeight;
    protected double widthWeight;
    protected double asymWeight;
    protected double grooveWeight;
    protected int minClustSize;
    protected double eps;
    protected int score;

    public WeightParams(){
        areaWeight=1;
        perimWeight=1;
        compactWeight=1;
        lengthWeight=1;
        widthWeight=1;
        asymWeight=1;
        grooveWeight=1;
        minClustSize = 7;
        eps = .176;
        score =0;
    }

    public WeightParams(double areaWeight, double perimWeight,
                        double compactWeight, double lengthWeight,
                        double widthWeight, double asymWeight,
                        double grooveWeight, int minClustSize, double eps) {
        this.areaWeight = areaWeight;
        this.perimWeight = perimWeight;
        this.compactWeight = compactWeight;
        this.lengthWeight = lengthWeight;
        this.widthWeight = widthWeight;
        this.asymWeight = asymWeight;
        this.grooveWeight = grooveWeight;
        this.minClustSize = minClustSize;
        this.eps = eps;
        score = 0;
    }

    public List<WeightParams> breed(WeightParams mate){
        ArrayList<WeightParams> litter = new ArrayList<WeightParams>();

        WeightParams maxChild =
                new WeightParams(
                Math.max(areaWeight,mate.getAreaWeight()),
                Math.max(perimWeight, mate.getPerimWeight()),
                Math.max(compactWeight, mate.getCompactWeight()),
                Math.max(lengthWeight, mate.getLengthWeight()),
                Math.max(widthWeight,mate.getWidthWeight()),
                Math.max(asymWeight, mate.getAsymWeight()),
                Math.max(grooveWeight, mate.getGrooveWeight()),
                Math.max(minClustSize, mate.getMinClustSize()),
                Math.max(eps,mate.getEps())),

        minChild = new WeightParams(
                Math.min(areaWeight, mate.getAreaWeight()),
                Math.min(perimWeight, mate.getPerimWeight()),
                Math.min(compactWeight, mate.getCompactWeight()),
                Math.min(lengthWeight, mate.getLengthWeight()),
                Math.min(widthWeight, mate.getWidthWeight()),
                Math.min(asymWeight, mate.getAsymWeight()),
                Math.min(grooveWeight, mate.getGrooveWeight()),
                Math.min(minClustSize, mate.getMinClustSize()),
                Math.min(eps, mate.getEps())
        ),
        middleChild = new WeightParams(
                (areaWeight + mate.getAreaWeight())/2,
                (perimWeight + mate.getPerimWeight())/2,
                (compactWeight + mate.getCompactWeight())/2,
                (lengthWeight + mate.getCompactWeight())/2,
                (widthWeight + mate.getWidthWeight())/2,
                (asymWeight + mate.getAsymWeight())/2,
                (grooveWeight + mate.getGrooveWeight())/2,
                (minClustSize + mate.getMinClustSize())/2,
                (eps + mate.getEps())/2.0
        ),
        leftChild = new WeightParams(areaWeight,
                perimWeight,
                compactWeight,
                lengthWeight,
                mate.getWidthWeight(),
                mate.getAsymWeight(),
                mate.getGrooveWeight(),
                mate.getMinClustSize(),
                mate.getEps()),
        rightChild = new WeightParams(mate.getAreaWeight(),
                mate.getPerimWeight(),
                mate.getCompactWeight(),
                mate.getLengthWeight(),
                widthWeight,
                asymWeight,
                grooveWeight,
                minClustSize,
                eps) ;

        litter.add(maxChild);
        litter.add(minChild);
        litter.add(middleChild);
        litter.add(leftChild);
        litter.add(rightChild);

        return litter;
    }

    public WeightParams mutantBud(){
        WeightParams mutant = new WeightParams();
        mutant.areaWeight = mutateAttr(areaWeight,1,0);
        mutant.asymWeight = mutateAttr(asymWeight,1,0);
        mutant.compactWeight = mutateAttr(compactWeight,1,0);
        mutant.grooveWeight = mutateAttr(grooveWeight,1,0);
        mutant.lengthWeight = mutateAttr(lengthWeight,1,0);
        mutant.perimWeight = mutateAttr(perimWeight,1,0);
        mutant.widthWeight = mutateAttr(widthWeight,1,0);
        mutant.minClustSize = mutateAttr(minClustSize,12,6);
        mutant.eps = mutateAttr(eps,.5,0.0001);
        return mutant;
    }

    public static int mutateAttr(int val, int max, int min){
        Random r = new Random();
        int ret = Math.min(Math.max(val + r.nextInt(2 * (max - min) + 1) - (max - min), min), max);
        return ret;
    }

    public static double mutateAttr(double val, double max, double min){
        Random r = new Random();
        double ret = Math.min(Math.max(val +(.5*(.5f- r.nextFloat())), min), max);
        return ret;
    }

    public double getAreaWeight() {
        return areaWeight;
    }

    public void setAreaWeight(double areaWeight) {
        this.areaWeight = areaWeight;
    }

    public double getPerimWeight() {
        return perimWeight;
    }

    public void setPerimWeight(double perimWeight) {
        this.perimWeight = perimWeight;
    }

    public double getCompactWeight() {
        return compactWeight;
    }

    public void setCompactWeight(double compactWeight) {
        this.compactWeight = compactWeight;
    }

    public double getLengthWeight() {
        return lengthWeight;
    }

    public void setLengthWeight(double lengthWeight) {
        this.lengthWeight = lengthWeight;
    }

    public double getWidthWeight() {
        return widthWeight;
    }

    public void setWidthWeight(double widthWeight) {
        this.widthWeight = widthWeight;
    }

    public double getAsymWeight() {
        return asymWeight;
    }

    public void setAsymWeight(double asymWeight) {
        this.asymWeight = asymWeight;
    }

    public double getGrooveWeight() {
        return grooveWeight;
    }

    public void setGrooveWeight(double grooveWeight) {
        this.grooveWeight = grooveWeight;
    }

    public int getMinClustSize() {
        return minClustSize;
    }

    public void setMinClustSize(int minClustSize) {
        this.minClustSize = minClustSize;
    }

    public double getEps() {
        return eps;
    }

    public void setEps(double eps) {
        this.eps = eps;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "WeightParams{" +
                "areaWeight=" + areaWeight +
                ", perimWeight=" + perimWeight +
                ", compactWeight=" + compactWeight +
                ", lengthWeight=" + lengthWeight +
                ", widthWeight=" + widthWeight +
                ", asymWeight=" + asymWeight +
                ", grooveWeight=" + grooveWeight +
                ", minClustSize=" + minClustSize +
                ", eps=" + eps +
                ", score=" + score +
                '}';
    }
}
