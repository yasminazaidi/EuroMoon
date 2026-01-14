package Model;

/**
 * Trein met locomotief en aantal wagons.
 */
public class Trein {
    private String id;
    private int aantalWagons;
    private Locomotief locomotief;

    public Trein(String id, Locomotief locomotief, int aantalWagons) {
        this.id = id;
        this.locomotief = locomotief;

        if (aantalWagons > locomotief.getMaxWagons()) {
            this.aantalWagons = locomotief.getMaxWagons();
        } else {
            this.aantalWagons = aantalWagons;
        }
    }

    public String getId() { return id; }
    public int getAantalWagons() { return aantalWagons; }
    public int getCapaciteit() { return locomotief.getCapaciteit(); }
    public Locomotief getLocomotief() { return locomotief; }

    @Override
    public String toString() {
        return "Trein " + id +
                " | Type: " + locomotief.getType() +
                " | Wagons: " + aantalWagons +
                " | Capaciteit: " + getCapaciteit();
    }
}