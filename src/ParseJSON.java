import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Michael Beam
 */

public class ParseJSON {
    public static void main(String[] args) {
        String filePath = "src/map.json";

        String[][][] t = parse(filePath);

        for (String[][] xdxdxd : t) {
            System.out.println("NEW WORLD");
            System.out.println();
            for (String[] aT : xdxdxd) {
                System.out.println("NEW LEVEL");
                System.out.println();
                for (String anAT : aT) {
                    System.out.println(anAT);
                }
                System.out.println();
            }
            System.out.println();
        }

    }

    private static String[][][] parse(String filePath) {
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

        String[] s = jsonContents.toString().split("\n");

        ArrayList<String[][]> worlds = new ArrayList<>();

        for (int i = 0; i < s.length; i++) {
            if (s[i].indexOf('"') > 0 && s[i].indexOf('{') > 0) {
                StringBuilder iterContent = new StringBuilder();
                for (int j = i; j < s.length; j++) {
                    if (s[j].indexOf('}') > 0) {
                        i = j;
                        break;
                    }
                    iterContent.append(s[j]);
                }
                worlds.add(getArr(iterContent.toString()));
            }
        }

        String[][][] worldMap = new String[worlds.size()][][];

        for (int i = 0; i < worldMap.length; i++) {
            worldMap[i] = worlds.get(i);
        }

        return worldMap;
    }

    private static String[][] getArr(String contents) {
        // Split jsonContents into an array, one entry per line
        String[] s = contents.split("\n");

        // Scan through s, clean it up a bit and calculate array bounds
        int Y = -1;
        int X = -1;

        for (int i = 0; i < s.length; i++) {
            String startTemp = s[i].substring(s[i].indexOf('"') + 1);
            if (s[i].indexOf('[') > 0) {
                Y = Integer.parseInt(startTemp.substring(0, startTemp.indexOf('"')));
                X = -1;
            } else if (s[i].indexOf('{') == -1 && s[i].indexOf('}') == -1 && s[i].indexOf(']') == -1) {
                X++;
                s[i] = s[i].substring(s[i].indexOf('"') + 1);
                s[i] = s[i].substring(0, s[i].indexOf('"'));
            }
        }

        // Create 2 dimmensional array to hold JSON values
        String[][] jsonArray = new String[Y + 1][X + 1];

        // Loop through s array and assign values to jsonArray 2D array
        for (int i = 0; i < s.length; i++) {
            String startTemp = s[i].substring(s[i].indexOf('"') + 1);
            if (s[i].indexOf('[') > 0) {
                Y = Integer.parseInt(startTemp.substring(0, startTemp.indexOf('"')));
                X = -1;
            } else if (s[i].indexOf('{') == -1 && s[i].indexOf('}') == -1 && s[i].indexOf(']') == -1) {
                X++;
                jsonArray[Y][X] = s[i];
            }
        }

        return jsonArray;
    }

//    ,
//    "1": [
//      "B                                          B",
//      "B                                          B",
//      "B                                          B",
//      "B   p                                      B",
//      "B   gggttt                                 B",
//      "B                gggggg                    B",
//      "B               gggggg                     B",
//      "B              gggggg    tt                B",
//      "B             gggggg       ttt             B",
//      "Bgggggggg    gggggg                        B"
//    ],
//    "2": [
//      "B                                          B",
//      "B                                          B",
//      "B                                          B",
//      "B   p                                      B",
//      "B   gggttt                                 B",
//      "B             gtgtgtg                      B",
//      "B               tttt  t                    B",
//      "B                        tt                B",
//      "B             tt           ttt             B",
//      "B     gg    gggggg                         B"
//    ],
//    "3": [
//      "B                                          B",
//      "B                                          B",
//      "B                                          B",
//      "B   p                                      B",
//      "B   t                                      B",
//      "B                t                         B",
//      "B                   t                      B",
//      "B              t         tg                B",
//      "B                 t                        B",
//      "Bgggggggg    ttgggg                        B"
//    ],
//    "4": [
//      "B                                          B",
//      "B                           tt             B",
//      "B                                          B",
//      "B   p                            ggg       B",
//      "B      gtt                                 B",
//      "B                gttt                      B",
//      "B                                          B",
//      "B                        tt                B",
//      "B                          ggggggtg        B",
//      "Bgg   ttg                                  B"
//    ]
}