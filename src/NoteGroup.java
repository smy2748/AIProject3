import java.util.ArrayList;

/**
 * Created by Stephen Yingling on 4/24/14.
 */
public class NoteGroup extends DistCalc {

    protected ArrayList<Note> notes;
    protected String label;

    public NoteGroup(){
        notes = new ArrayList<Note>();
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    public void addNote(Note n){
        notes.add(n);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getKey(){
        if(notes.size() >0){
            return notes.get(0).key;
        }
        return 0;
    }

    public int getTime(){
        if(notes.size() >0){
            return notes.get(0).time;
        }
        return 0;
    }

    public int getPitchBox(){
        double avg=0,num=0;

        for(Note n : notes){
            avg +=n.pitch;
            num++;
        }

        avg = avg/num;

        return PitchBox.boxPitch(avg);
    }

    public String toCsv(){
        StringBuilder res = new StringBuilder();
        for(Note n : notes){
            res.append(n.toCsv() +",");
        }

        return res.toString();
    }

    @Override
    public Double calculateDistance(DistCalc dc, WeightParams wp) {

        if(dc instanceof NoteGroup){
            NoteGroup other = (NoteGroup) dc;
            double dKey, dTime, dPitchBox;

            dKey = getKey() - other.getKey();
            dTime = getTime() - other.getTime();
            dPitchBox = getPitchBox() - other.getPitchBox();
            dPitchBox *= .5;
            double under = Math.pow(dKey,2) + Math.pow(dTime,2) + Math.pow(dPitchBox,2);
            return Math.sqrt(under);
        }

        return null;
    }

    @Override
    DistCalc copy() {
        //TODO: Left as an exercise to the implementer
        return null;
    }

    @Override
    public String toString(){
        String str ="";
        str += "Note Group: " + label;
        str += "\nNotes in Group:\n";
        for(Note n : notes){
            str += "\t\t" + n +"\n";
        }
        return str;
    }
}
