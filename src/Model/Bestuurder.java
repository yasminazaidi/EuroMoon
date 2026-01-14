package Model;

import java.time.LocalDate;

/**
 * Bestuurder van een trein.
 */
public class Bestuurder extends Personeel {
    public Bestuurder(String naam, String achternaam, String rijksregisternummer, LocalDate geboortedatum) {
        super(naam, achternaam, rijksregisternummer, geboortedatum);
    }
}