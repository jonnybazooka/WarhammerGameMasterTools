package dto;

import java.util.Objects;

public class Adventurer {

    private String name;
    private int initiative;
    private int perception;

    public Adventurer(String name, int initiative, int perception) {
        this.name = name;
        this.initiative = initiative;
        this.perception = perception;
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

    public int getPerception() {
        return perception;
    }

    public void setPerception(int perception) {
        this.perception = perception;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adventurer that = (Adventurer) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return String.format("%-16s Initiative: %d | Perception: %d", name, initiative, perception);
    }
}
