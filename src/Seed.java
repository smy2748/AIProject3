/**
 * Created by Stephen Yingling on 5/6/14.
 */
public class Seed extends DistCalc{
    protected double area;
    protected double perimeter;
    protected double compactness;
    protected double length;
    protected double width;
    protected double asymmetry;
    protected double groove;
    protected int classNum;

    public Seed() {
    }

    public Seed(Seed s){
        if(s != null){
            area = s.getArea();
            perimeter = s.getPerimeter();
            compactness = s.getCompactness();
            length = s.getLength();
            width = s.getWidth();
            asymmetry = s.getAsymmetry();
            groove = s.getGroove();
            classNum = s.getClassNum();
        }
    }

    public Seed(double area, double perimeter,
                double compactness, double length,
                double width, double asymmetry,
                double groove, int classNum) {
        this.area = area;
        this.perimeter = perimeter;
        this.compactness = compactness;
        this.length = length;
        this.width = width;
        this.asymmetry = asymmetry;
        this.groove = groove;
        this.classNum = classNum;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(double perimeter) {
        this.perimeter = perimeter;
    }

    public double getCompactness() {
        return compactness;
    }

    public void setCompactness(double compactness) {
        this.compactness = compactness;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getAsymmetry() {
        return asymmetry;
    }

    public void setAsymmetry(double asymmetry) {
        this.asymmetry = asymmetry;
    }

    public double getGroove() {
        return groove;
    }

    public void setGroove(double groove) {
        this.groove = groove;
    }

    public int getClassNum() {
        return classNum;
    }

    public void setClassNum(int classNum) {
        this.classNum = classNum;
    }

    public void normalize(Seed min, Seed max){
        this.area = normalize_attr(area,min.getArea(), max.getArea(),0,1.0);
        this.asymmetry = normalize_attr(asymmetry,min.getAsymmetry(), max.getAsymmetry(),0,1.0);
        this.compactness = normalize_attr(compactness,min.getCompactness(), max.getCompactness(),0,1.0);
        this.groove = normalize_attr(groove,min.getGroove(), max.getGroove(),0,1.0);
        this.length = normalize_attr(length,min.getLength(), max.getLength(),0,1.0);
        this.perimeter = normalize_attr(perimeter,min.getPerimeter(), max.getPerimeter(),.0,1.0);
        this.width = normalize_attr(width,min.getWidth(), max.getWidth(),0,1.0);
    }

    protected static double normalize_attr(double val, double min,
                                    double max, double newMin, double newMax){
        double res =0;

        res = (((val-min)/(max-min))*(newMax-newMin)) + newMin;

        return res;
    }

    public void setMaxes(Seed other){
        if(other == null){
            return;
        }

        if(this.area < other.getArea()){
            this.area = other.getArea();
        }

        if(this.asymmetry < other.getAsymmetry()){
            this.asymmetry = other.getAsymmetry();
        }

        if(this.compactness < other.getCompactness()){
            this.compactness = other.getCompactness();
        }

        if(this.groove < other.getGroove()){
            this.groove = other.getGroove();
        }

        if(this.length < other.getLength()){
            this.length = other.getLength();
        }

        if(this.perimeter < other.getPerimeter()){
            this.perimeter = other.getPerimeter();
        }

        if(this.width < other.getWidth()){
            this.width = other.getWidth();
        }
    }

    public void setMins(Seed other){
        if(other == null){
            return;
        }

        if(this.area > other.getArea()){
            this.area = other.getArea();
        }

        if(this.asymmetry > other.getAsymmetry()){
            this.asymmetry = other.getAsymmetry();
        }

        if(this.compactness > other.getCompactness()){
            this.compactness = other.getCompactness();
        }

        if(this.groove > other.getGroove()){
            this.groove = other.getGroove();
        }

        if(this.length > other.getLength()){
            this.length = other.getLength();
        }

        if(this.perimeter > other.getPerimeter()){
            this.perimeter = other.getPerimeter();
        }

        if(this.width > other.getWidth()){
            this.width = other.getWidth();
        }
    }

    @Override
    Double calculateDistance(DistCalc dc, WeightParams wp) {
        if(dc instanceof Seed){
            Seed other = (Seed) dc;
            double dArea = other.getArea() - area,
                   dPerim = other.getPerimeter() - perimeter,
                   dCompact= other.getCompactness() - compactness,
                   dLength = other.getLength() - length,
                   dWidth = other.getWidth() - width,
                   dAsym = other.getAsymmetry() - asymmetry,
                   dGroove = other.getGroove() - groove;

            dArea *= wp.getAreaWeight();
            dPerim *= wp.getPerimWeight();
            dCompact *= wp.getCompactWeight();
            dLength *= wp.getLengthWeight();
            dWidth *= wp.getWidthWeight();
            dAsym *= wp.getAsymWeight();
            dGroove *= wp.getGrooveWeight();

            double under = Math.pow(dArea,2) + Math.pow(dPerim,2)+
                           Math.pow(dCompact,2) + Math.pow(dLength,2) +
                           Math.pow(dWidth,2) + Math.pow(dAsym,2) +
                           Math.pow(dGroove,2);
            return Math.sqrt(under);

        }
        return null;
    }

    @Override
    DistCalc copy() {
        return new Seed(area,perimeter,compactness,length,width,asymmetry,groove,classNum);
    }

    @Override
    public String toString() {
        return "Seed{" +
                "classNum=" + classNum +
                '}';
    }

    public String toCSV() {
        return area +"," + perimeter +"," +compactness +"," + length +","+width+","+asymmetry+","+groove+","+classNum;
    }
}
