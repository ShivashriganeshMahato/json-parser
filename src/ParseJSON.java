/* ParseJSON.java */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

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

        // Scan through s, clean it up and store the data in a list
        ArrayList<ArrayList<ArrayList<String>>> jsonList = new ArrayList<>();

        int world = -1;
        int level = -1;

        for (int i = 0; i < s.length; i++) {
            if (s[i].indexOf('[') > 0) {
                if (s[i].indexOf('"') > 0) {
                    String temp = s[i].substring(s[i].indexOf('"') + 1);
                    jsonList.add(new ArrayList<>());
                    world = Integer.parseInt(temp.substring(0, temp.indexOf('"')));
                    level = -1;
                } else {
                    jsonList.get(world).add(new ArrayList<>());
                    level++;
                    for (int j = i + 1; j < s.length; j++) {
                        if (s[j].indexOf(']') > 0) {
                            i = j;
                            break;
                        }
                        s[j] = s[j].substring(s[j].indexOf('"') + 1);
                        s[j] = s[j].substring(0, s[j].indexOf('"'));
                        jsonList.get(world).get(level).add(s[j]);
                    }
                }
            }
        }


        // Create 3 dimensional array to hold JSON values from the list
        String[][][] jsonArray = new String[jsonList.size()][][];

        for (int i = 0; i < jsonList.size(); i++) {
            jsonArray[i] = new String[jsonList.get(i).size()][];
            for (int j = 0; j < jsonList.get(i).size(); j++) {
                jsonArray[i][j] = new String[jsonList.get(i).get(j).size()];
                for (int k = 0; k < jsonList.get(i).get(j).size(); k++) {
                    jsonArray[i][j][k] = jsonList.get(i).get(j).get(k);
                }
            }
        }

        return jsonArray;
    }
}