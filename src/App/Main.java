package App;

import Model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    // "Database" in memory (simpel)
    private static final List<Passagier> passagiers = new ArrayList<>();
    private static final List<Reis> reizen = new ArrayList<>();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            toonMenu();
            int keuze = leesInt("Kies optie: ");

            switch (keuze) {
                case 1 -> registreerPassagier();
                case 2 -> maakReisAan();
                case 3 -> koppelTreinAanReis();
                case 4 -> verkoopTicket();
                case 5 -> printBoardingLijst();
                case 0 -> running = false;
                default -> System.out.println("Ongeldige keuze.");
            }
        }

        System.out.println("Programma afgesloten.");
    }

    private static void toonMenu() {
        System.out.println("\n=== EUROMOON MENU ===");
        System.out.println("1) Registreren passagier");
        System.out.println("2) Aanmaken reis");
        System.out.println("3) Trein koppelen aan reis");
        System.out.println("4) Ticket verkopen aan passagier");
        System.out.println("5) Afdrukken boardinglijst (txt)");
        System.out.println("0) Stop");
    }

    // ========= OPTIE 1 =========
    private static void registreerPassagier() {
        System.out.println("\n--- Passagier registreren ---");
        String naam = leesString("Naam: ");
        String achternaam = leesString("Achternaam: ");
        String rrn = leesString("Rijksregisternummer: ");

        // simpele check: dubbel rrn niet toelaten
        if (zoekPassagierOpRrn(rrn) != null) {
            System.out.println("Deze passagier bestaat al (zelfde rijksregisternummer).");
            return;
        }

        LocalDate geboortedatum = leesDate("Geboortedatum (YYYY-MM-DD): ");
        Passagier p = new Passagier(naam, achternaam, rrn, geboortedatum);
        passagiers.add(p);

        System.out.println("Passagier toegevoegd: " + p);
    }

    // ========= OPTIE 2 =========
    private static void maakReisAan() {
        System.out.println("\n--- Reis aanmaken ---");
        String vertrek = leesString("Vertrekstation: ");
        String aankomst = leesString("Aankomststation: ");
        LocalDateTime vertrekTijd = leesDateTime("Vertrektijd (YYYY-MM-DDTHH:MM): ");

        System.out.println("\nBestuurder info:");
        String bNaam = leesString("Naam: ");
        String bAchternaam = leesString("Achternaam: ");
        String bRrn = leesString("Rijksregisternummer: ");
        LocalDate bGeboorte = leesDate("Geboortedatum (YYYY-MM-DD): ");
        Bestuurder bestuurder = new Bestuurder(bNaam, bAchternaam, bRrn, bGeboorte);

        int aantalStewards = leesInt("Aantal stewards (min 3): ");

        // Reis wordt gemaakt ZONDER trein (want je moet later koppelen)
        Reis reis = new Reis(vertrek, aankomst, vertrekTijd, bestuurder, aantalStewards);
        reizen.add(reis);

        System.out.println("Reis aangemaakt: " + reis);
    }

    // ========= OPTIE 3 =========
    private static void koppelTreinAanReis() {
        System.out.println("\n--- Trein koppelen aan reis ---");

        Reis reis = kiesReis();
        if (reis == null) return;

        String treinId = leesString("Trein ID: ");

        System.out.println("Locomotief type:");
        System.out.println("1) CLASS_373 (max 12 wagons)");
        System.out.println("2) CLASS_374 (max 14 wagons)");
        int typeKeuze = leesInt("Kies type: ");

        LocomotiefType type = (typeKeuze == 2) ? LocomotiefType.CLASS_374 : LocomotiefType.CLASS_373;

        int wagons = leesInt("Aantal wagons: ");
        Locomotief loco = new Locomotief(type);
        Trein trein = new Trein(treinId, loco, wagons);

        reis.setTrein(trein);

        System.out.println(" Trein gekoppeld: " + trein);
        System.out.println("Reis nu: " + reis);
    }

    // ========= OPTIE 4 =========
    private static void verkoopTicket() {
        System.out.println("\n--- Ticket verkopen ---");

        if (passagiers.isEmpty()) {
            System.out.println("Geen passagiers. Registreer eerst een passagier.");
            return;
        }
        Reis reis = kiesReis();
        if (reis == null) return;

        if (reis.getTrein() == null) {
            System.out.println(" Deze reis heeft nog geen trein. Koppel eerst een trein.");
            return;
        }

        Passagier passagier = kiesPassagier();
        if (passagier == null) return;

        System.out.println("Klasse:");
        System.out.println("1) Eerste klasse");
        System.out.println("2) Tweede klasse");
        int klasseKeuze = leesInt("Kies: ");
        boolean eersteKlasse = (klasseKeuze == 1);

        Ticket ticket = new Ticket(passagier, reis, eersteKlasse);

        boolean gelukt = reis.voegTicketToe(ticket);
        if (!gelukt) {
            System.out.println("Ticket niet verkocht (geen trein of trein is vol).");
        } else {
            System.out.println(" Ticket verkocht: " + ticket);
            System.out.println("Tickets verkocht: " + reis.getTickets().size() + "/" + reis.getTrein().getCapaciteit());
        }
    }

    // ========= OPTIE 5 =========
    private static void printBoardingLijst() {
        System.out.println("\n--- Boardinglijst afdrukken ---");

        Reis reis = kiesReis();
        if (reis == null) return;

        String fileName = reis.getBoardingBestandsNaam();

        StringBuilder sb = new StringBuilder();
        sb.append("Boardinglijst: ").append(reis.getVertrekStation())
                .append(" -> ").append(reis.getAankomstStation())
                .append(" | ").append(reis.getVertrekTijd())
                .append("\n\n");

        if (reis.getTickets().isEmpty()) {
            sb.append("Geen passagiers met ticket voor deze reis.\n");
        } else {
            for (Ticket t : reis.getTickets()) {
                Passagier p = t.getPassagier();
                sb.append(p.getNaam()).append(" ").append(p.getAchternaam())
                        .append(" | RRN: ").append(p.getRijksregisternummer())
                        .append(" | Geboorte: ").append(p.getGeboortedatum())
                        .append(" | ").append(t.isEersteKlasse() ? "1e klasse" : "2e klasse")
                        .append("\n");
            }
        }

        try {
            Files.writeString(Path.of(fileName), sb.toString());
            System.out.println("Boardinglijst opgeslagen als: " + fileName);
        } catch (IOException e) {
            System.out.println("Fout bij schrijven bestand: " + e.getMessage());
        }
    }

    // ========= HELPERS =========
    private static Reis kiesReis() {
        if (reizen.isEmpty()) {
            System.out.println("Geen reizen. Maak eerst een reis aan.");
            return null;
        }
        System.out.println("\nReizen:");
        for (int i = 0; i < reizen.size(); i++) {
            System.out.println((i + 1) + ") " + reizen.get(i));
        }
        int keuze = leesInt("Kies reis: ");
        if (keuze < 1 || keuze > reizen.size()) {
            System.out.println("Ongeldige reis.");
            return null;
        }
        return reizen.get(keuze - 1);
    }

    private static Passagier kiesPassagier() {
        System.out.println("\nPassagiers:");
        for (int i = 0; i < passagiers.size(); i++) {
            System.out.println((i + 1) + ") " + passagiers.get(i));
        }
        int keuze = leesInt("Kies passagier: ");
        if (keuze < 1 || keuze > passagiers.size()) {
            System.out.println("Ongeldige passagier.");
            return null;
        }
        return passagiers.get(keuze - 1);
    }

    private static Passagier zoekPassagierOpRrn(String rrn) {
        for (Passagier p : passagiers) {
            if (p.getRijksregisternummer().equals(rrn)) return p;
        }
        return null;
    }

    private static String leesString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private static int leesInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Geef een geldig getal.");
            }
        }
    }

    private static LocalDate leesDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                return LocalDate.parse(input);
            } catch (Exception e) {
                System.out.println("Gebruik formaat YYYY-MM-DD (bv. 2001-05-30).");
            }
        }
    }

    private static LocalDateTime leesDateTime(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                return LocalDateTime.parse(input);
            } catch (Exception e) {
                System.out.println("Gebruik formaat YYYY-MM-DDTHH:MM (bv. 2026-03-05T12:30).");
            }
        }
    }
}