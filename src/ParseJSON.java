/* ParseJSON.java */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ParseJSON
{
    public static void main(String[] args) {

        String filePath = "C:/users/mjbeam/documents/LevelMaps01.json";

        String[][] t = getJSON(filePath);

        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                System.out.println(t[i][j]);
            }
        }

    }

    private static String[][] getJSON(String filePath) {

        String jsonContents = "";

        File jsonFile = new File(filePath);
        FileInputStream infile = null;

        // Read contents of JSON file into jsonContents string
        try {
            infile = new FileInputStream(jsonFile);
            int content;
            while ((content = infile.read()) != -1) {
                jsonContents+=(char) content;
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
        String[] s = jsonContents.split("\r\n");

        // Scan through s, clean it up a bit and calculate array bounds
        int Y = -1;
        int X = -1;

        for (int i = 0; i < s.length; i++) {
            if (s[i].indexOf('[') > 0) {
                String temp = s[i].substring(s[i].indexOf('"') + 1);
                Y = Integer.parseInt(temp.substring(0, temp.indexOf('"')));
                X = -1;
            } else if (s[i].indexOf('{') == -1 && s[i].indexOf('}') == -1 && s[i].indexOf(']') == -1) {
                X++;
                s[i]=s[i].substring(s[i].indexOf('"') + 1);
                s[i]=s[i].substring(0,s[i].indexOf('"'));
            }
        }

        // Create 2 dimmensional array to hold JSON values
        String[][] jsonArray = new String[Y + 1][X + 1];

        // Loop through s array and assign values to jsonArray 2D array
        for (int i = 0; i < s.length; i++) {
            if (s[i].indexOf('[') > 0) {
                String temp = s[i].substring(s[i].indexOf('"') + 1);
                Y = Integer.parseInt(temp.substring(0, temp.indexOf('"')));
                X = -1;
            } else if (s[i].indexOf('{') == -1 && s[i].indexOf('}') == -1 && s[i].indexOf(']') == -1) {
                X++;
                jsonArray [Y][X]=s[i];
            }
        }

        return jsonArray;
    }
}