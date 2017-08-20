import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 @author Michael Beam, Shivashriganesh Mahato
*/
public class ParseJSON {
    public static void main(String[] args) {
        String filePath = "src/map.json";

        String[][][] t = getJSON(filePath);

        for (String[][] aT : t) {
            for (String[] anAT : aT) {
                for (String anAnAT : anAT) {
                    System.out.println(anAnAT);
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    private static String[][][] getJSON(String filePath) {
        StringBuilder jsonContents = new StringBuilder();

        File jsonFile = new File(filePath);
        FileInputStream infile = null;

        // Read contents of JSON file into jsonContents string
        try {
            infile = new FileInputStream(jsonFile);
            int content;
            while ((content = infile.read()) != -1) {
                jsonContents.append((char) content);
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                if (infile != null)
                    infile.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        // Split jsonContents into an array, one entry per line
        String[] s = jsonContents.toString().split("\n");

        // Scan through s, clean it up a bit and calculate array bounds
        int world = -1;
        int level = -1;
        int tempLvl = -1;
        int obj = -1;
        int tempObj;

        for (int i = 0; i < s.length; i++) {
            if (s[i].indexOf('[') > 0) {
                if (s[i].indexOf('"') > 0) {
                    String temp = s[i].substring(s[i].indexOf('"') + 1);
                    world = Integer.parseInt(temp.substring(0, temp.indexOf('"')));
                    tempLvl = -1;
                } else {
                    tempObj = -1;
                    tempLvl++;
                    for (int j = i + 1; j < s.length; j++) {
                        if (s[j].indexOf(']') > 0) {
                            i = j;
                            break;
                        }
                        tempObj++;
                        s[j]=s[j].substring(s[j].indexOf('"') + 1);
                        s[j]=s[j].substring(0,s[j].indexOf('"'));
                    }

                    if (tempObj > obj)
                        obj = tempObj;
                }
            }

            if (tempLvl > level)
                level = tempLvl;
        }


        // Create 3 dimensional array to hold JSON values
        String[][][] jsonArray = new String[world + 1][level + 1][obj + 1];

        // Loop through s array and assign values to jsonArray 3D array
        for (int i = 0; i < s.length; i++) {
            if (s[i].indexOf('[') > 0) {
                if (s[i].indexOf('"') > 0) {
                    String temp = s[i].substring(s[i].indexOf('"') + 1);
                    world = Integer.parseInt(temp.substring(0, temp.indexOf('"')));
                    tempLvl = -1;
                } else {
                    tempObj = -1;
                    tempLvl++;
                    for (int j = i + 1; j < s.length; j++) {
                        if (s[j].indexOf(']') > 0) {
                            i = j;
                            break;
                        }
                        tempObj++;
                        jsonArray[world][tempLvl][tempObj] = s[j];
                    }

                    if (tempObj > obj)
                        obj = tempObj;
                }
            }

            if (tempLvl > level)
                level = tempLvl;
        }

        return jsonArray;
    }
}
