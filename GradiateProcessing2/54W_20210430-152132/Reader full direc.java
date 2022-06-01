import java.io.*;
import java.util.Scanner;
public class Reader {
    public static void main (String[] args) {
        

       ''' System.out.print("JSON name? ");
        String jsonName = fname.nextLine();
        try{
            File file = new File("C:/Users/Victoria Lam/Documents/Java/intermediate data/" + jsonName + " JSON.txt");
            Scanner sc = new Scanner (file);
            int count = 0;
            //String keySave = null;
            String key = null;
            String physlum = null;
            String meanlum = null;
            String id = null;
            boolean dev = false;
            //System.out.println("Group, SpatialFrequency, ContrastRatio, Duration, Key, ");
            
            String prev = null;
            String sf = null;
            String cr = null;
            String dur = null;
            String trial = null;
            System.out.println("Group, ID, Trial, SpatialFrequency, ContrastRatio, Duration, Key");

            //boolean newline = false;
            while (sc.hasNextLine()) {
                //read in each line of data from the JSON
                String d = sc.nextLine();

                // rid of random spacing
                if (d.indexOf("  ")!= -1) d = d.replaceAll("  ", "");
                if (d.indexOf(",")!= -1) d = d.replaceAll(",", "");

                //read in admin data
                if (d.indexOf("name")!=-1){
                    id = d.replaceAll("\"name\":", "");
                    id = id.replaceAll("\"", "");
                    id = id.replaceAll(" ", "");
                    id = id.replaceAll("-", " ");
                    id = id.toUpperCase();
                    trial = id.substring(2);
                    id = id.substring(0,2);
                    //System.out.println(d);
                    count=0;
                } else if (d.indexOf("phys")!=-1){
                    if (sc.nextLine().indexOf("0.0")==-1 && sc.nextLine().indexOf("1.0")==-1) {
                        physlum = "Phys. Luminance Range, " + sc.nextLine().replaceAll(",","-") + sc.nextLine() + ", ";
                        dev = true;
                    }
                } else if (d.indexOf("mean")!=-1){
                    d = d.replaceAll("\"mean_luminance\":", "Mean Luminance, ");
                    if (d.indexOf("0.5")==-1) {
                        meanlum = d;
                        dev = true;
                    }
                } else if (d.indexOf("key")!=-1){
                    key = d.replaceAll("\"key\":", "");    
                }

                // read trial data, count==trial #
                if (d.indexOf("sf")!= -1) {
                    count++;
                    //System.out.print(count + ", ");
                    d = d.replaceAll("\"sf\":", "");
                    sf = d + ", ";
                } else if (d.indexOf("contrast")!= -1) {
                    d = d.replaceAll("\"contrast_ratio\":", "");
                    cr = d + ", ";
                } else if (d.indexOf("duration")!= -1) {
                    d = d.replaceAll("\"duration\":", "");
                    //System.out.println(d + ", " + key + ", ");
                    dur = d + ", " + key;
                } else if (d.indexOf("succ")!=-1) {
                    if (d.indexOf("true")!=-1) {
                        prev = count + ", " + id + ", " + trial + ", " + sf + cr + dur;
                    } else {
                        System.out.println(prev);
                        System.out.println(count + ", " + id + ", " + trial + ", " + sf + cr + dur);
                    }
                }
                
                if (sc.hasNextLine()==false) {
                    System.out.println(id);
                    if (dev == true) {
                        System.out.println(physlum);
                        System.out.println(meanlum);
                    }
                }
            }
            sc.close();
            fname.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
