package Model;

import java.time.LocalDate;

/**
 * Steward aan boord.
 */
public class Steward extends Personeel {
    public Steward(String naam, String achternaam, String rijksregisternummer, LocalDate geboortedatum) {
        super(naam, achternaam, rijksregisternummer, geboortedatum);
    }
}