package ma.projet;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;
import ma.projet.util.HibernateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestExercice3 {

    public static void main(String[] args) {
        HibernateUtil.getSessionFactory();

        HommeService hs = new HommeService();
        FemmeService fs = new FemmeService();
        MariageService ms = new MariageService();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            System.out.println("--- Création des personnes ---");
            Femme f1 = new Femme("SAID", "SALIMA", "0611111111", "Rabat", dateFormat.parse("1990-01-01"));
            Femme f2 = new Femme("RAMI", "AMAL", "0622222222", "Casa", dateFormat.parse("1985-05-15"));
            Femme f3 = new Femme("ALAOUI", "MAHA", "0633333333", "Fes", dateFormat.parse("1992-03-10"));
            Femme f4 = new Femme("ALAMI", "KARIMA", "0644444444", "Tanger", dateFormat.parse("1988-07-20"));
            Femme f5 = new Femme("WAFA", "WAFA", "0655555555", "Marrakech", dateFormat.parse("1995-11-30"));
            fs.create(f1);
            fs.create(f2);
            fs.create(f3);
            fs.create(f4);
            fs.create(f5);

            Homme h1 = new Homme("SAFI", "SAID", "0666666666", "Agadir", dateFormat.parse("1988-01-01"));
            hs.create(h1);
            for (int i = 2; i <= 10; i++) {
                hs.create(new Homme("NomH" + i, "PrenomH" + i, "06000000" + i, "Ville" + i, dateFormat.parse("199" + (i % 9) + "-01-01")));
            }

            System.out.println("--- Création des mariages ---");
            ms.create(new Mariage(dateFormat.parse("1990-09-03"), null, 4, h1, f1));
            ms.create(new Mariage(dateFormat.parse("1995-09-03"), null, 2, h1, f2));
            ms.create(new Mariage(dateFormat.parse("2000-11-04"), null, 3, h1, f5));
            ms.create(new Mariage(dateFormat.parse("1989-09-03"), dateFormat.parse("1990-09-03"), 0, h1, f4));

            ms.create(new Mariage(dateFormat.parse("2010-01-01"), null, 1, hs.findById(2), f3));
            ms.create(new Mariage(dateFormat.parse("2012-01-01"), null, 2, hs.findById(3), f3));


            System.out.println("\n--- DÉBUT DES TESTS ---");
            System.out.println("\nTest 1: Liste des femmes :");
            for (Femme f : fs.findAll()) {
                System.out.println("  - " + f.getNom() + " " + f.getPrenom());
            }

            System.out.println("\nTest 2: Femme la plus âgée :");
            List<Femme> femmes = fs.findAll();
            Femme plusAgee = femmes.get(0);
            for (Femme f : femmes) {
                if (f.getDateNaissance().before(plusAgee.getDateNaissance())) {
                    plusAgee = f;
                }
            }
            System.out.println("  - " + plusAgee.getNom() + " " + plusAgee.getPrenom() + " (Née le " + dateFormat.format(plusAgee.getDateNaissance()) + ")");

            System.out.println("\nTest 3: Épouses de " + h1.getNom() + " " + h1.getPrenom() + " (entre 1980 et 2000) :");
            Date d1 = dateFormat.parse("1980-01-01");
            Date d2 = dateFormat.parse("2000-12-31");
            for (Mariage m : hs.findEpousesEntreDates(h1, d1, d2)) {
                System.out.println("  - " + m.getFemme().getNom() + " " + m.getFemme().getPrenom() + " (Mariage le: " + dateFormat.format(m.getDateDebut()) + ")");
            }

            System.out.println("\nTest 4: Nombre d'enfants de " + f1.getNom() + " " + f1.getPrenom() + " (Mariages entre 1980 et 2025) :");
            Long nbrEnfants = fs.getNombreEnfants(f1, dateFormat.parse("1980-01-01"), dateFormat.parse("2025-01-01"));
            System.out.println("  - Nombre d'enfants : " + nbrEnfants);

            System.out.println("\nTest 5: Femmes mariées 2 fois ou plus :");
            for (Femme f : fs.findFemmesMarieesDeuxFois()) {
                System.out.println("  - " + f.getNom() + " " + f.getPrenom());
            }

            System.out.println("\nTest 6: Hommes mariés à 4 femmes (ou plus) entre 1980 et 2025 (API Criteria) :");
            Long nbrHommes = fs.getNombreHommesMaries(4, dateFormat.parse("1980-01-01"), dateFormat.parse("2025-01-01"));
            System.out.println("  - Nombre d'hommes trouvés : " + nbrHommes);

            System.out.println("\nTest 7: Détails des mariages de " + h1.getNom() + " " + h1.getPrenom() + " :");
            List<Mariage> mariagesDetails = ms.findMariagesDetails(h1);
            System.out.println("Mariages En Cours :");
            int i = 1;
            for (Mariage m : mariagesDetails) {
                if (m.getDateFin() == null) {
                    System.out.println("  " + i + ". Femme : " + m.getFemme().getPrenom() + " " + m.getFemme().getNom() +
                            "\t Date Début : " + dateFormat.format(m.getDateDebut()) +
                            "\t Nbr Enfants : " + m.getNbrEnfants());
                    i++;
                }
            }
            System.out.println("Mariages Échoués :");
            for (Mariage m : mariagesDetails) {
                if (m.getDateFin() != null) {
                    System.out.println("  1. Femme : " + m.getFemme().getPrenom() + " " + m.getFemme().getNom() +
                            "\t Date Début : " + dateFormat.format(m.getDateDebut()) +
                            "\t Date Fin : " + dateFormat.format(m.getDateFin()) +
                            "\t Nbr Enfants : " + m.getNbrEnfants());
                }
            }

            System.out.println("\n--- FIN DES TESTS ---");

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}