package fileOps;

import com.google.gson.*;
import dto.Adventurer;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FileIOManager {

    private static final Logger LOGGER = LogManager.getLogger(FileIOManager.class.getName());
    private final String FILE_DIR = "./adventurers/";
    private final String ADV_PATH = "./adventurers/adventurers.txt";
    private final String NPC_PATH = "./adventurers/npcs.txt";
    private final String IMPERIAL_NAME_PATH = "names/Imperial.txt";
    private final String IMPERIAL_FEMALE_NAME_PATH = "names/Imperial_Female.txt";
    private final String IMPERIAL_LAST_NAME_PATH = "names/Imperial_Last.txt";
    private final String DWARF_MALE_FORENAME = "names/Dwarf_Male_Name.txt";
    private final String DWARF_FEMALE_FORENAME = "names/Dwarf_Female_Name.txt";
    private final String DWARF_SURNAME = "names/Dwarf_Surname.txt";
    private final String ELF_MALE_NAME_PATH = "names/Elf_Male_Name.txt";
    private final String ELF_FEMALE_NAME_PATH = "names/Elf_Female_Name.txt";
    private final String ELF_SURNAME_PATH = "names/Elf_Surname.txt";
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

    public String getImperial() {
        String name = getName(IMPERIAL_NAME_PATH);
        String lastName = getName(IMPERIAL_LAST_NAME_PATH);
        assert name != null;
        assert lastName != null;
        return name.toUpperCase() + " " + lastName.toUpperCase();
    }

    public String getFemaleImperial() {
        String name = getName(IMPERIAL_FEMALE_NAME_PATH);
        String lastName = getName(IMPERIAL_LAST_NAME_PATH);
        assert name != null;
        assert lastName != null;
        return name.toUpperCase() + " " + lastName.toUpperCase();
    }

    private String getName(String source) {
        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(source);
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
            Random random = new Random();
            JsonObject object = jsonArray.get(random.nextInt(jsonArray.size())).getAsJsonObject();
            bufferedReader.close();
            in.close();
            return object.get("name").getAsString();

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    public String getDwarven() {
        Random random = new Random();
        String name = getName(DWARF_MALE_FORENAME);
        int surnameRoll = random.nextInt(100)+1;
        if (surnameRoll <= 75) {
            String surname = getName(DWARF_MALE_FORENAME);
            String appendix = random.nextInt(10) > 4 ? "sson" : "snev";
            surname = surname + appendix;
            assert name != null;
            return name.toUpperCase() + " " + surname.toUpperCase();
        } else {
            String surname = getName(DWARF_SURNAME);
            assert name != null;
            assert surname != null;
            return name.toUpperCase() + " " + surname.toUpperCase();
        }
    }

    public String getDwarvenFemale() {
        Random random = new Random();
        String name = getName(DWARF_FEMALE_FORENAME);
        int surnameRoll = random.nextInt(100)+1;
        if (surnameRoll <= 75) {
            String surname = getName(DWARF_MALE_FORENAME);
            String appendix = random.nextInt(10) > 4 ? "dottir" : "sniz";
            surname = surname + appendix;
            assert name != null;
            return name.toUpperCase() + " " + surname.toUpperCase();
        } else {
            String surname = getName(DWARF_SURNAME);
            assert name != null;
            assert surname != null;
            return name.toUpperCase() + " " + surname.toUpperCase();
        }
    }

    public String getElfMale() {
        String name = getName(ELF_MALE_NAME_PATH);
        String lastName = getName(ELF_SURNAME_PATH);
        assert name != null;
        assert lastName != null;
        return name.toUpperCase() + " " + lastName.toUpperCase();
    }

    public String getElfFemale() {
        String name = getName(ELF_FEMALE_NAME_PATH);
        String lastName = getName(ELF_SURNAME_PATH);
        assert name != null;
        assert lastName != null;
        return name.toUpperCase() + " " + lastName.toUpperCase();
    }

    public void saveNPC(TextArea textArea) {
        File dir = new File(FILE_DIR);
        File file = new File(NPC_PATH);
        dir.mkdirs();
        try {
            FileOutputStream out = new FileOutputStream(file, true);
            OutputStreamWriter writer = new OutputStreamWriter(out);
            String npc = textArea.getText();
            writer.write(npc + "\n");
            writer.close();
            out.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public List<String> loadNPCs() {
        File file = new File(NPC_PATH);
        try {
            FileInputStream in = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(reader);
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    public void updateNPCs(TextArea textArea) {
        File dir = new File(FILE_DIR);
        File file = new File(NPC_PATH);
        dir.mkdirs();
        try {
            FileOutputStream out = new FileOutputStream(file, false);
            OutputStreamWriter writer = new OutputStreamWriter(out);
            String npc = textArea.getText();
            writer.write(npc + "\n");
            writer.close();
            out.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }


}
