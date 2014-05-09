import java.util.ArrayList;

/**
 * Created by Stephen Yingling on 4/24/14.
 */
public class Chorale {
    protected ArrayList<Note> notes;
    public int number;
    public String artist;

    public Chorale(){
        notes = new ArrayList<Note>();
    }

    public void addNote(Note n){
        notes.add(n);
    }

    public ArrayList<Note> getNotes(){
        return notes;
    }
}
