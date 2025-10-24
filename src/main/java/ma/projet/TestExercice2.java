package ma.projet;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.EmployeTachePK;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.service.EmployeService;
import ma.projet.service.EmployeTacheService;
import ma.projet.service.ProjetService;
import ma.projet.service.TacheService;
import ma.projet.util.HibernateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestExercice2 {

    public static void main(String[] args) {

        HibernateUtil.getSessionFactory();

        EmployeService es = new EmployeService();
        ProjetService ps = new ProjetService();
        TacheService ts = new TacheService();
        EmployeTacheService ets = new EmployeTacheService();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Employe emp1 = new Employe("El Bikri", "Idriss", "0612345678");
            Employe emp2 = new Employe("Alaoui", "Fatima", "0698765432");
            es.create(emp1);
            es.create(emp2);
            System.out.println("--- Employés créés ---");

            Projet proj1 = new Projet("Gestion de stock",
                    dateFormat.parse("2013-01-14"),
                    dateFormat.parse("2013-06-14"),
                    emp1);
            ps.create(proj1);
            System.out.println("--- Projet créé ---");

            Tache t1 = new Tache("Analyse", dateFormat.parse("2013-02-10"), dateFormat.parse("2013-02-20"), 1200.0, proj1);
            Tache t2 = new Tache("Conception", dateFormat.parse("2013-03-10"), dateFormat.parse("2013-03-15"), 900.0, proj1);
            Tache t3 = new Tache("Développement", dateFormat.parse("2013-04-10"), dateFormat.parse("2013-04-25"), 1500.0, proj1);
            ts.create(t1);
            ts.create(t2);
            ts.create(t3);
            System.out.println("--- Tâches créées ---");

            EmployeTachePK pk1 = new EmployeTachePK();
            pk1.setEmployeId(emp1.getId());
            pk1.setTacheId(t1.getId());
            ets.create(new EmployeTache(pk1, dateFormat.parse("2013-02-10"), dateFormat.parse("2013-02-20")));

            EmployeTachePK pk2 = new EmployeTachePK();
            pk2.setEmployeId(emp2.getId());
            pk2.setTacheId(t2.getId());
            ets.create(new EmployeTache(pk2, dateFormat.parse("2013-03-10"), dateFormat.parse("2013-03-15")));

            EmployeTachePK pk3 = new EmployeTachePK();
            pk3.setEmployeId(emp1.getId());
            pk3.setTacheId(t3.getId());
            ets.create(new EmployeTache(pk3, dateFormat.parse("2013-04-10"), dateFormat.parse("2013-04-25")));
            System.out.println("--- Tâches assignées aux employés ---");


            System.out.println("\n--- DÉBUT DES TESTS ---");

            System.out.println("\nTest 1: Affichage de l'exemple (Tâches planifiées du projet 1)");
            Projet pTest = ps.findById(proj1.getId());
            System.out.println("Projet : " + pTest.getId() +
                    "   Nom : " + pTest.getNom() +
                    "   Date début : " + dateFormat.format(pTest.getDateDebut()));
            System.out.println("Liste des tâches :");
            System.out.println("Num\tNom\t\tDate Début Prévue\tDate Fin Prévue");
            List<Tache> tachesPlanifiees = ps.findTachesPlanifiees(pTest);
            for (Tache t : tachesPlanifiees) {
                System.out.println(t.getId() + "\t" + t.getNom() + "\t\t" +
                        dateFormat.format(t.getDateDebut()) + "\t\t" +
                        dateFormat.format(t.getDateFin()));
            }

            System.out.println("\nTest 2: Tâches réalisées par l'employé " + emp1.getNom() + ":");
            List<EmployeTache> tachesEmp1 = es.findTachesRealiseesParEmploye(emp1);
            for (EmployeTache et : tachesEmp1) {
                System.out.println("  - Tâche: " + et.getTache().getNom() +
                        ", Date Fin Réelle: " + dateFormat.format(et.getDateFinReelle()));
            }

            System.out.println("\nTest 3: Projets gérés par " + emp1.getNom() + ":");
            List<Projet> projetsGeres = es.findProjetsGeresParEmploye(emp1);
            for (Projet p : projetsGeres) {
                System.out.println("  - Projet: " + p.getNom());
            }

            System.out.println("\nTest 4: Tâches avec prix > 1000 DH:");
            List<Tache> tachesChers = ts.findTachesPrixSup1000();
            for (Tache t : tachesChers) {
                System.out.println("  - " + t.getNom() + " (Prix: " + t.getPrix() + ")");
            }

            Date d1 = dateFormat.parse("2013-01-01");
            Date d2 = dateFormat.parse("2013-03-31");
            System.out.println("\nTest 5: Tâches terminées entre 2013-01-01 et 2013-03-31:");
            List<Tache> tachesDates = ts.findTachesRealiseesEntreDates(d1, d2);
            for (Tache t : tachesDates) {
                System.out.println("  - " + t.getNom());
            }

            System.out.println("\n--- FIN DES TESTS ---");

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}