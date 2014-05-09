import java.util.ArrayList;

/**
 * Created by Stephen Yingling on 5/6/14.
 */
public class SeedDataPreprocessor {

    protected Seed min;
    protected Seed max;

    public SeedDataPreprocessor(){}

    public void initialPass(ArrayList<Seed> seeds){
        if(seeds.size() <1){
            return;
        }

        if(min == null || max == null){
            min = new Seed(seeds.get(0));
            max = new Seed(seeds.get(0));
        }

        for(Seed s : seeds){
            min.setMins(s);
            max.setMaxes(s);
        }



    }

    public void normalize(ArrayList<Seed> seeds){

        for(Seed s: seeds){
            s.normalize(min,max);
        }
    }
}
