package Model;

/**
 * Ticket voor een passagier op een reis.
 */
public class Ticket {
    private Passagier passagier;
    private Reis reis;
    private boolean eersteKlasse;

    public Ticket(Passagier passagier, Reis reis, boolean eersteKlasse) {
        this.passagier = passagier;
        this.reis = reis;
        this.eersteKlasse = eersteKlasse;
    }

    public Passagier getPassagier() { return passagier; }
    public Reis getReis() { return reis; }
    public boolean isEersteKlasse() { return eersteKlasse; }

    @Override
    public String toString() {
        return passagier + " | " + (eersteKlasse ? "1e klasse" : "2e klasse");
    }
}