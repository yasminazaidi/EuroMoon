package Model;

/**
 * Locomotief met type en capaciteit.
 */
public class Locomotief {
    private LocomotiefType type;
    private int capaciteit = 80;

    public Locomotief(LocomotiefType type) {
        this.type = type;
    }

    public LocomotiefType getType() { return type; }
    public int getCapaciteit() { return capaciteit; }
    public int getMaxWagons() { return type.getMaxWagons(); }

    @Override
    public String toString() {
        return type.name();
    }
}