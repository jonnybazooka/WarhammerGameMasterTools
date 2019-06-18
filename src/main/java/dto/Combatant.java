package dto;

public class Combatant implements Comparable<Combatant>{

    private String name;
    private int initiative;
    private Side side;

    public Combatant(String name, int initiative, Side side) {
        this.name = name;
        this.initiative = initiative;
        this.side = side;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    @Override
    public int compareTo(Combatant o) {
        return o.getInitiative() - initiative;
    }

    @Override
    public String toString() {
        return String.format("%-14s Side:%-9s | INI: %02d | Wounds: " , name, side, initiative);
    }
}
