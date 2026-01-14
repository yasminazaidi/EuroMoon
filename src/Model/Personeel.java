package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Personeelslid met certificaties.
 */
public abstract class Personeel extends Persoon {
    private List<String> certificaties = new ArrayList<>();

    public Personeel(String naam, String achternaam, String rijksregisternummer, LocalDate geboortedatum) {
        super(naam, achternaam, rijksregisternummer, geboortedatum);
    }

    public void voegCertificatieToe(String certificatie) {
        certificaties.add(certificatie);
    }

    public List<String> getCertificaties() {
        return certificaties;
    }
}