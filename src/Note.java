/**
 * Created by Stephen Yingling on 4/24/14.
 */
public class Note {

    public Note(){

    }

    public int startTime;
    public int pitch;
    public int duration;
    public int key;
    public int time;
    public int fermata;


    public String toCsv(){
        return startTime +"," + pitch + "," + duration +"," + key +","+ time + "," +fermata;
    }

    @Override
    public String toString(){
        String s ="";
        s+= "{Start Time: " + startTime + ", Pitch: " + pitch +", Duration: " + duration + ", Key: " + key + ", Time: " + time + ", Fermata: " + fermata +"}";
        return s;
    }
}
