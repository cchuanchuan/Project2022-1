import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    private static File file = new File("data.txt");

    public static List<Rank> readFile(){
        List<Rank> list = new ArrayList<>();

        if (!file.exists()) {
            return list;
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                String strs[] = line.split(",");
                try {
                    Rank rank = new Rank(strs[0],Integer.parseInt(strs[1]));
                    list.add(rank);
                } catch (Exception e) {
                    continue;
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void writeToFile(List<Rank> data){
        try {
            if (!file.exists()){
                file.createNewFile();
            }
            PrintWriter printWriter = new PrintWriter(new FileOutputStream(file,false));
            for (Rank s : data) {
                printWriter.println(s);
            }
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
