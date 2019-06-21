package controllers;

import dice.DiceRoller;
import dto.Adventurer;
import dto.Combatant;
import dto.Side;
import fileOps.FileIOManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Controller {
    private static final Logger LOGGER = LogManager.getLogger(FileIOManager.class.getName());

    public TextField advNameField;
    public TextField initiativeField;
    public TextField perceptionField;
    public Button saveAdventurerButton;
    public TextArea textArea;
    public Button loadAdventurersButton;
    public Button updateAdventurerButton;
    public Button deleteAdventurerButton;
    public Button addOppButton;
    public TextField oppNameField;
    public TextField oppInitiativeField;
    public Button startCombatButton;
    public Button perceptionCheckButton;
    public Button imperialButton;
    public Button dwarvenButton;
    public Button saveNPCButton;
    public Button loadNPCsButton;
    public Button updateNPCsButton;

    List<Adventurer> adventurerList = new ArrayList<>();
    List<Combatant> combatantList = new ArrayList<>();

    @FXML
    public void initialize() {
        textArea.setStyle("-fx-font-family: monospace");
    }

    public void saveAdventurer(ActionEvent event) {
        LOGGER.debug("Attempting to save adventurer: " + advNameField.getText());
        FileIOManager fileIOManager = new FileIOManager();
        fileIOManager.saveAdventurer(advNameField, initiativeField, perceptionField);
        textArea.clear();
        textArea.appendText(advNameField.getText() + " saved.");
        clearAdventurerFields();
    }

    public void loadAdventurers(ActionEvent event) {
        LOGGER.debug("Attempting to load adventurers.");
        FileIOManager fileIOManager = new FileIOManager();
        adventurerList.clear();
        adventurerList.addAll(fileIOManager.loadAdventurers());
        textArea.clear();
        textArea.appendText("Adventurers loaded.\n");
        for (Adventurer adv : adventurerList) {
            textArea.appendText(adv.toString() + "\n");
        }
    }

    public void updateAdventurer(ActionEvent event) {
        LOGGER.debug("Attempting to update adventurer: " + advNameField.getText());
        FileIOManager fileIOManager = new FileIOManager();
        boolean isSuccessful = fileIOManager.updateAdventurer(advNameField, initiativeField, perceptionField);
        textArea.clear();
        if (isSuccessful) {
            textArea.appendText(advNameField.getText() + " has been updated.");
            clearAdventurerFields();
        } else {
            textArea.appendText(advNameField.getText() + " couldn't be updated.");
        }
    }

    public void deleteAdventurer(ActionEvent event) {
        LOGGER.debug("Attempting to delete adventurer: " + advNameField.getText());
        FileIOManager fileIOManager = new FileIOManager();
        boolean isSuccessful = fileIOManager.deleteAdventurer(advNameField);
        textArea.clear();
        if (isSuccessful) {
            textArea.appendText(advNameField.getText() + " has been deleted.");
            clearAdventurerFields();
        } else {
            textArea.appendText(advNameField.getText() + " couldn't be deleted.");
        }
    }

    public void addCombatant(ActionEvent event) {
        LOGGER.debug("Attempting to add combatant: " + oppNameField.getText() + " to combat.");
        Combatant combatant = new Combatant(oppNameField.getText(), Integer.parseInt(oppInitiativeField.getText()), Side.OPPONENT);
        combatantList.add(combatant);
        textArea.appendText(oppNameField.getText() + " added to combat.\n");
        clearOpponentFields();
    }

    public void startCombat(ActionEvent event) {
        LOGGER.debug("Attempting to start combat.");
        loadAdventurers(event);
        textArea.clear();
        addAdventurersToCombat();
        DiceRoller diceRoller = new DiceRoller();
        List<Combatant> initiativeOrder = diceRoller.rollInitiative(combatantList);
        for (Combatant combatant : initiativeOrder) {
            textArea.appendText(combatant.toString() + "\n");
        }
        combatantList.clear();
    }

    public void perceptionCheck(ActionEvent event) {
        LOGGER.debug("Attempting perception check.");
        loadAdventurers(event);
        textArea.clear();
        DiceRoller diceRoller = new DiceRoller();
        Map<String, Integer[]> perceptionResults = diceRoller.rollPerception(adventurerList);
        showResults(perceptionResults);
    }

    private void showResults(Map<String, Integer[]> perceptionResults) {
        for(Map.Entry<String, Integer[]>entry : perceptionResults.entrySet()) {
            if (entry.getValue()[0] != 0) {
                textArea.appendText(String.format("%-16s PERCEPTION CHECK: %d SL\n", entry.getKey(), entry.getValue()[0]));
            } else if (entry.getValue()[1] == -1){
                textArea.appendText(String.format("%-16s PERCEPTION CHECK: -%d SL\n", entry.getKey(), entry.getValue()[0]));
            } else {
                textArea.appendText(String.format("%-16s PERCEPTION CHECK: %d SL\n", entry.getKey(), entry.getValue()[0]));
            }
        }
    }

    private void addAdventurersToCombat() {
        for (Adventurer adv : adventurerList) {
            Combatant combatant = new Combatant(adv.getName(), adv.getInitiative(), Side.PLAYER);
            combatantList.add(combatant);
        }
    }

    private void clearAdventurerFields() {
        advNameField.clear();
        initiativeField.clear();
        perceptionField.clear();
    }

    private void clearOpponentFields() {
        oppNameField.clear();
        oppInitiativeField.clear();
    }

    public void getRandomImperialName(ActionEvent event) {
        FileIOManager fileIOManager = new FileIOManager();
        String imperial = fileIOManager.getImperial();
        textArea.clear();
        textArea.appendText(imperial);
    }

    public void getRandomDwarvenName(ActionEvent event) {
        FileIOManager fileIOManager = new FileIOManager();
        String imperial = fileIOManager.getDwarven();
        textArea.clear();
        textArea.appendText(imperial);
    }

    public void saveNPC(ActionEvent event) {
        FileIOManager fileIOManager = new FileIOManager();
        fileIOManager.saveNPC(textArea);
        textArea.clear();
        textArea.appendText("NPC saved.");
    }

    public void loadNPCs(ActionEvent event) {
        FileIOManager fileIOManager = new FileIOManager();
        List<String> npcs = fileIOManager.loadNPCs();
        textArea.clear();
        for (String line : npcs) {
            textArea.appendText(line + System.lineSeparator());
        }
    }

    public void updateNPCs(ActionEvent event) {
        FileIOManager fileIOManager = new FileIOManager();
        fileIOManager.updateNPCs(textArea);
        textArea.clear();
        textArea.appendText("NPCs updated.");
    }
}
