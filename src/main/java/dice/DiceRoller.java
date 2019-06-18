package dice;

import dto.Adventurer;
import dto.Combatant;

import java.util.*;

public class DiceRoller {
    private Random random;

    public DiceRoller() {
        this.random = new Random();
    }

    public List<Combatant> rollInitiative(List<Combatant> combatantList) {
        List<Combatant> initiativeOrder = new ArrayList<>();
        for (Combatant combatant : combatantList) {
            combatant.setInitiative(rollIni(combatant.getInitiative()));
            initiativeOrder.add(combatant);
        }
        initiativeOrder.sort(Combatant::compareTo);
        return initiativeOrder;
    }

    private int rollIni(int ini) {
        return ini + random.nextInt(10)+1;
    }

    public Map<String, Integer[]> rollPerception(List<Adventurer> adventurerList) {
        Map<String, Integer[]> perceptionResults = new HashMap<>();
        for (Adventurer adv : adventurerList) {
            Integer[] successLevel = determineSuccessLevels(adv.getPerception());
            perceptionResults.put(adv.getName(), successLevel);
        }
        return perceptionResults;
    }

    private Integer[] determineSuccessLevels(int perception) {
        int roll = random.nextInt(100)+1;
        int successLevel = getTens(perception) - getTens(roll);
        int successSign = perception >= roll ? 1 : -1;
        return new Integer[]{successLevel, successSign};
    }

    private int getTens(int number) {
        int counter = 0;
        for (int i = 0; i < 10; i++)
            if (number - 10 >= 0) {
                number -= 10;
                counter++;
            }
        return counter;
    }
}
