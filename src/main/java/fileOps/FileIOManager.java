package fileOps;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dto.Adventurer;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileIOManager {

    private static final Logger LOGGER = LogManager.getLogger(FileIOManager.class.getName());
    private final String FILE_DIR = "./adventurers/";
    private final String ADV_PATH = "./adventurers/adventurers.txt";
    private Gson gson;

    public FileIOManager() {
        this.gson = new Gson();
    }

    public void saveAdventurer(TextField advNameField, TextField initiativeField, TextField perceptionField) {
        File dir = new File(FILE_DIR);
        File file = new File(ADV_PATH);
        dir.mkdirs();
        List<Adventurer> adventurers;
        Adventurer adventurer = new Adventurer(advNameField.getText()
                , Integer.parseInt(initiativeField.getText())
                , Integer.parseInt(perceptionField.getText()));
        if (file.exists()) {
            adventurers = loadAdventurers();
        } else {
            adventurers = new ArrayList<>();
        }
        adventurers.add(adventurer);
        save(adventurers);
    }

    public List<Adventurer> loadAdventurers() {
        List<Adventurer> adventurers = new ArrayList<>();
        File file = new File(ADV_PATH);
        try {
            FileInputStream in = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder sB = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sB.append(line);
            }
            String json = sB.toString();
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(json).getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject object = jsonArray.get(i).getAsJsonObject();
                String name = object.get("name").getAsString();
                int ini = object.get("initiative").getAsInt();
                int per = object.get("perception").getAsInt();
                Adventurer adventurer = new Adventurer(name, ini, per);
                adventurers.add(adventurer);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return adventurers;
    }

    public boolean updateAdventurer(TextField advNameField, TextField initiativeField, TextField perceptionField) {
        Adventurer updatedAdventurer = new Adventurer(advNameField.getText()
                , Integer.parseInt(initiativeField.getText())
                , Integer.parseInt(perceptionField.getText()));
        List<Adventurer> adventurers = loadAdventurers();
        for (Adventurer adv : adventurers) {
            if (adv.equals(updatedAdventurer)) {
                adventurers.remove(adv);
                adventurers.add(updatedAdventurer);
                save(adventurers);
                return true;
            }
        }
        return false;
    }

    public boolean deleteAdventurer(TextField advNameField) {
        List<Adventurer> adventurers = loadAdventurers();
        for (Adventurer adv : adventurers) {
            if (adv.getName().equals(advNameField.getText())) {
                adventurers.remove(adv);
                save(adventurers);
                return true;
            }
        }
        return false;
    }

    private void save(List<Adventurer> adventurers) {
        File file = new File(ADV_PATH);
        String json = gson.toJson(adventurers);
        try {
            FileOutputStream out = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(out);
            writer.write(json);
            writer.close();
            out.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
