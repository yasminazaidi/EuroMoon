package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Een reis gaat tussen twee stations op een bepaald tijdstip.
 * Een reis kan eerst aangemaakt worden ZONDER trein, en later kan je een trein koppelen.
 * Een reis heeft minstens 1 bestuurder en minimum 3 stewards.
 */
public class Reis {

    private String vertrekStation;
    private String aankomstStation;
    private LocalDateTime vertrekTijd;

    private Trein trein; // kan null zijn tot je koppelt
    private Bestuurder bestuurder;
    private int aantalStewards;

    private List<Ticket> tickets = new ArrayList<>();

    /**
     * Maakt een nieuwe reis aan zonder trein (trein kan later gekoppeld worden).
     */
    public Reis(String vertrekStation, String aankomstStation, LocalDateTime vertrekTijd,
                Bestuurder bestuurder, int aantalStewards) {

        this.vertrekStation = vertrekStation;
        this.aankomstStation = aankomstStation;
        this.vertrekTijd = vertrekTijd;
        this.bestuurder = bestuurder;
        this.aantalStewards = Math.max(aantalStewards, 3);
    }

    public String getVertrekStation() {
        return vertrekStation;
    }

    public String getAankomstStation() {
        return aankomstStation;
    }

    public LocalDateTime getVertrekTijd() {
        return vertrekTijd;
    }

    public Trein getTrein() {
        return trein;
    }

    public Bestuurder getBestuurder() {
        return bestuurder;
    }

    public int getAantalStewards() {
        return aantalStewards;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    /**
     * Koppel een trein aan deze reis.
     */
    public void setTrein(Trein trein) {
        this.trein = trein;
    }

    /**
     * Voeg een ticket toe als er nog plaats is op de trein.
     * @return true als ticket toegevoegd is, false als trein ontbreekt of vol is.
     */
    public boolean voegTicketToe(Ticket ticket) {
        if (trein == null) return false;
        if (tickets.size() >= trein.getCapaciteit()) return false;

        tickets.add(ticket);
        return true;
    }

    /**
     * Bestandsnaam voor boardinglijst, bv: Brussel_Parijs_2026-03-05T12-30.txt
     */
    public String getBoardingBestandsNaam() {
        String datum = vertrekTijd.toString().replace(":", "-");
        return vertrekStation + "_" + aankomstStation + "_" + datum + ".txt";
    }

    @Override
    public String toString() {
        return vertrekStation + " -> " + aankomstStation +
                " | " + vertrekTijd +
                " | Trein: " + (trein == null ? "GEEN" : trein.getId()) +
                " | Bestuurder: " + bestuurder.getNaam() + " " + bestuurder.getAchternaam() +
                " | Stewards: " + aantalStewards +
                " | Tickets: " + tickets.size();
    }
}