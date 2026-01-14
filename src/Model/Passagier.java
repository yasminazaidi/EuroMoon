package Model;

import java.time.LocalDate;

/**
 * Passagier die tickets kan kopen.
 */
public class Passagier extends Persoon {
    public Passagier(String naam, String achternaam, String rijksregisternummer, LocalDate geboortedatum) {
        super(naam, achternaam, rijksregisternummer, geboortedatum);
    }
}