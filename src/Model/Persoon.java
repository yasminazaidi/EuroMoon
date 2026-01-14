package Model;

import java.time.LocalDate;

/**
 * Basis klasse voor iedereen in het systeem.
 */
public abstract class Persoon {
    private String naam;
    private String achternaam;
    private String rijksregisternummer;
    private LocalDate geboortedatum;

    public Persoon(String naam, String achternaam, String rijksregisternummer, LocalDate geboortedatum) {
        this.naam = naam;
        this.achternaam = achternaam;
        this.rijksregisternummer = rijksregisternummer;
        this.geboortedatum = geboortedatum;
    }

    public String getNaam() { return naam; }
    public String getAchternaam() { return achternaam; }
    public String getRijksregisternummer() { return rijksregisternummer; }
    public LocalDate getGeboortedatum() { return geboortedatum; }

    @Override
    public String toString() {
        return naam + " " + achternaam + " (" + rijksregisternummer + ")";
    }
}