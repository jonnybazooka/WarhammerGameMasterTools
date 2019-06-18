package dice;

import dto.Combatant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiceRoller {

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
        Random random = new Random();
        return ini + random.nextInt(10)+1;
    }
}
