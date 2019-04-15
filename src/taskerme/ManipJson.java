package taskerme;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JOptionPane;
import model.Json;

public class ManipJson {

    public static String fileName = "tasks.json";

    public static void createFile() {
        try {
            FileWriter arq = new FileWriter("./" + fileName);
            PrintWriter gravarArq = new PrintWriter(arq);
            gravarArq.print("{}");
            arq.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.exit(0);
        }
    }

    public Json readJson() throws FileNotFoundException, IOException {
        FileReader arq = new FileReader("./" + fileName);
        BufferedReader lerArq = new BufferedReader(arq);

        String text = "";
        String line = "";
        while (line != null) {
            line = lerArq.readLine();
            if (line != null) {
                text += line;
            }
        }
        Gson gson = new Gson();
        return gson.fromJson(text, Json.class);
    }

    public void writeJson(Json json) throws IOException {
        Gson gson = new Gson();
        String jsonString = gson.toJson(json);        
        FileWriter arq = new FileWriter("./" + fileName);
        PrintWriter gravarArq = new PrintWriter(arq);
        gravarArq.print(jsonString);
        arq.close();
    }
}
