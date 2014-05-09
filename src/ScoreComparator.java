import java.util.Comparator;

public class ScoreComparator implements Comparator<WeightParams> {

    public ScoreComparator(){}
    @Override
    public int compare(WeightParams o1, WeightParams o2) {
        return Integer.compare(o1.getScore(), o2.getScore());
    }
}