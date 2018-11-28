package taskerme;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import model.Json;

public class ManipJson {

    public Json readJson() throws FileNotFoundException, IOException {
        FileReader arq = new FileReader("./tasks.json");
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

        FileWriter arq = new FileWriter("./tasks.json");
        PrintWriter gravarArq = new PrintWriter(arq);
        gravarArq.print(jsonString);
        arq.close();
    }
}
