package Model;

/**
 * Types locomotieven met max wagons.
 */
public enum LocomotiefType {
    CLASS_373(12),
    CLASS_374(14);

    private final int maxWagons;

    LocomotiefType(int maxWagons) {
        this.maxWagons = maxWagons;
    }

    public int getMaxWagons() {
        return maxWagons;
    }
}