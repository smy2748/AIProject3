import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Stephen Yingling on 4/24/14.
 */
public class Parser {

    public static void main(String args[]) throws IOException {
        File f = new File("chorales.lisp");
        Scanner sc = new Scanner(f);
        ArrayList<Chorale> chorales = new ArrayList<Chorale>();
        while (sc.hasNext()){
            chorales.add(parseChorale(sc));
        }
        sc.close();
        File file = new File("3group.csv");
        FileWriter fw = new FileWriter(file);
        ArrayList<Note> notes;
        ArrayList<DataPoint<NoteGroup>> ngs = new ArrayList<DataPoint<NoteGroup>>();
        for(Chorale c : chorales){
            notes = c.getNotes();
            String out = c.number + ",";
            for(int i=0; i< notes.size()-3; i+=3){
                NoteGroup noteGrp = new NoteGroup();
                noteGrp.addNote(notes.get(i));
                noteGrp.addNote(notes.get(i+1));
                noteGrp.addNote(notes.get(i+2));
                noteGrp.setLabel("C"+c.number +"NG"+(i/3));
                DataPoint<NoteGroup> dp = new DataPoint<NoteGroup>(noteGrp);
                ngs.add(dp);
                out += notes.get(i).toCsv() +","+notes.get(i+1).toCsv() +","+notes.get(i+2).toCsv()+"\n";
                fw.write(out);
                out = c.number +",";

            }
        }
        fw.close();

        DBSCAN dbs = new DBSCAN();
        ArrayList<Cluster<NoteGroup>> custers =DBSCAN.DBSCAN(ngs,.5,10);

        System.out.println(custers.size());

        for(Cluster<NoteGroup> c : custers){
            System.out.println(c);
        }
    }

    public static Chorale parseChorale(Scanner sc){
        String line = sc.nextLine();
        if(line.trim().equals("")){
            line = sc.nextLine();
        }
        Chorale c = new Chorale();
       // System.out.println(line);
        String num = line.substring(1,line.indexOf('(',1)).trim();
        c.number = Integer.parseInt(num);

        String b ="";
        int count =0;
        for(int i=line.indexOf("(",1); i<line.length();i++){
            b+= line.charAt(i);

            if(line.charAt(i) == '('){
                count += 1;
            }
            if(line.charAt(i) == ')'){
                count -= 1;
            }

            if(count ==0){
                c.addNote(parseNote(b));
                b ="";
            }
        }

        return c;
    }

    public static Note parseNote(String str){
        Note n = new Note();
        String buffer = "";
        for(int i=0; i< str.length()-1; i++){
            buffer += str.charAt(i);
            if(str.charAt(i) == ')'){
                parseAttr(n,buffer);
                buffer ="";
            }
        }
        return n;
    }

    public static void parseAttr(Note n, String str){
        String attr[];
        str = str.trim();
        str = str.replace("(","");
        str = str.replace(")","");
        attr = str.split(" ");

        if(attr[0].equals("st")){
            n.startTime = Integer.parseInt(attr[1]);
        }
        else if(attr[0].equals("pitch")){
            n.pitch = Integer.parseInt(attr[1]);
        }
        else if(attr[0].equals("dur")){
            n.duration = Integer.parseInt(attr[1]);
        }
        else if(attr[0].equals("keysig")){
            n.key = Integer.parseInt(attr[1]);
        }
        else if(attr[0].equals("timesig")){
            n.time = Integer.parseInt(attr[1]);
        }
        else if(attr[0].equals("fermata")){
            n.fermata = Integer.parseInt(attr[1]);
        }
    }
}
