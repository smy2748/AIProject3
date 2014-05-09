import javax.sound.midi.*;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Created by Stephen Yingling on 4/25/14.
 */
public class MidiReader {

    public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;
    public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    public static void main(String[] args) throws Exception {
        Chorale c = new Chorale();
        Sequence sequence = MidiSystem.getSequence(new File("chopin5.mid"));

        int trackNumber = 0;
        for (Track track :  sequence.getTracks()) {
            trackNumber++;
            System.out.println("Track " + trackNumber + ": size = " + track.size());
            System.out.println();
            for (int i=0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                System.out.print("@" + event.getTick() + " ");
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    System.out.print("Channel: " + sm.getChannel() + " ");
                    if (sm.getCommand() == NOTE_ON) {
                        if(sm.getData2() != 0){
                        Note n = new Note();
                        n.duration =1;
                        n.fermata = 0;
                        n.key =0;
                        n.pitch = sm.getData1();
                        n.startTime = (i/2)-3;
                        n.time = 8;
                        c.addNote(n);
                        }
                        int key = sm.getData1();
                        int octave = (key / 12)-1;
                        int note = key % 12;
                        String noteName = NOTE_NAMES[note];
                        int velocity = sm.getData2();
                        System.out.println("Note on, " + noteName + octave + " key=" + key + " velocity: " + velocity);
                    } else if (sm.getCommand() == NOTE_OFF) {
                        int key = sm.getData1();
                        int octave = (key / 12)-1;
                        int note = key % 12;
                        String noteName = NOTE_NAMES[note];
                        int velocity = sm.getData2();
                        System.out.println("Note off, " + noteName + octave + " key=" + key + " velocity: " + velocity);
                    } else {
                        System.out.println("Command:" + sm.getCommand());
                    }
                } else {
                    //System.out.println("Other message: " + message.getClass());
                }
            }

            System.out.println();
        }

        File file = new File("chopin5.csv");
        FileWriter fw = new FileWriter(file);
        c.artist = "Chopin";
        c.number =1;
        fw.write("st,pitch,duration,key,time,fermata,cNum,cArtist\n");
        ArrayList<Note> notes = c.getNotes();
        ArrayList<NoteGroup> ngs = new ArrayList<NoteGroup>();
        for(int i=0; i<notes.size()-3; i+=3){
            NoteGroup ng = new NoteGroup();
            ng.addNote(notes.get(i));
            ng.addNote(notes.get(i+1));
            ng.addNote(notes.get(i+2));
            ngs.add(ng);
        }

        for(NoteGroup n : ngs){
            fw.write(n.toCsv()+c.number+","+c.artist+"\n");
        }
        fw.close();

    }
}
