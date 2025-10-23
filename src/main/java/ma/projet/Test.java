package ma.projet;

import ma.projet.classes.*;
import ma.projet.service.CategorieService;
import ma.projet.service.CommandeService;
import ma.projet.service.LigneCommandeService;
import ma.projet.service.ProduitService;
import ma.projet.util.HibernateUtil; // Force l'initialisation d'Hibernate

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        // Initialiser Hibernate (juste pour être sûr)
        HibernateUtil.getSessionFactory();

        // Instancier tous nos services
        CategorieService cs = new CategorieService();
        ProduitService ps = new ProduitService();
        CommandeService comS = new CommandeService();
        LigneCommandeService lcs = new LigneCommandeService();

        // Formatteur de date pour les tests
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // --- 1. Création des Catégories ---
        Categorie cat1 = new Categorie("EL", "Electronique");
        Categorie cat2 = new Categorie("IN", "Informatique");
        cs.create(cat1);
        cs.create(cat2);
        System.out.println("--- Catégories créées ---");

        // --- 2. Création des Produits ---
        Produit p1 = new Produit("ES12", 120.0f, cat2); // Informatique
        Produit p2 = new Produit("ZR85", 100.0f, cat1); // Electronique
        Produit p3 = new Produit("EE85", 280.0f, cat1); // Electronique
        Produit p4 = new Produit("AX20", 90.0f, cat2);  // Informatique (prix < 100)
        ps.create(p1);
        ps.create(p2);
        ps.create(p3);
        ps.create(p4);
        System.out.println("--- Produits créés ---");

        // --- 3. Création d'une Commande ---
        Date dateCmd = null;
        try {
            // Date de la commande, similaire à l'exemple
            dateCmd = dateFormat.parse("2013-03-14");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Commande cmd1 = new Commande(dateCmd);
        comS.create(cmd1);
        System.out.println("--- Commande créée ---");

        // --- 4. Ajout de Lignes de Commande (lier produits et commande) ---
        // On simule l'exemple d'affichage
        // Produit ES12, Qte 7
        LigneCommandeProduitPK pk1 = new LigneCommandeProduitPK();
        pk1.setCommandeId(cmd1.getId());
        pk1.setProduitId(p1.getId());
        lcs.create(new LigneCommandeProduit(pk1, 7));

        // Produit ZR85, Qte 14
        LigneCommandeProduitPK pk2 = new LigneCommandeProduitPK();
        pk2.setCommandeId(cmd1.getId());
        pk2.setProduitId(p2.getId());
        lcs.create(new LigneCommandeProduit(pk2, 14));

        // Produit EE85, Qte 5
        LigneCommandeProduitPK pk3 = new LigneCommandeProduitPK();
        pk3.setCommandeId(cmd1.getId());
        pk3.setProduitId(p3.getId());
        lcs.create(new LigneCommandeProduit(pk3, 5));
        System.out.println("--- Lignes de commande créées ---");


        // --- 5. Phase de TEST des fonctionnalités ---
        System.out.println("\n--- DÉBUT DES TESTS ---");

        // Test 1: Afficher les produits d'une catégorie (ex: Electronique)
        System.out.println("\nTest 1: Produits de la catégorie 'Electronique' (cat1):");
        List<Produit> produitsCat1 = ps.findProduitsByCategorie(cat1);
        for (Produit p : produitsCat1) {
            System.out.println("  - " + p.getReference() + " (Prix: " + p.getPrix() + ")");
        }

        // Test 2: Afficher les produits dont le prix est > 100 DH
        System.out.println("\nTest 2: Produits avec prix > 100 DH:");
        List<Produit> produitsChers = ps.findProduitsPrixSuperieurA100();
        for (Produit p : produitsChers) {
            System.out.println("  - " + p.getReference() + " (Prix: " + p.getPrix() + ")");
        }

        // Test 3: Afficher les produits commandés dans une commande donnée (cmd1)
        //
        System.out.println("\nTest 3: Affichage de la commande 4 (ID: " + cmd1.getId() + ")");
        // On récupère la commande depuis la BDD pour avoir les lignes
        Commande commandeComplete = comS.findById(cmd1.getId());

        System.out.println("Commande : " + commandeComplete.getId() +
                "   Date : " + dateFormat.format(commandeComplete.getDate()));
        System.out.println("Liste des produits :");
        System.out.println("Référence\tPrix\t\tQuantité");
        for (LigneCommandeProduit l : commandeComplete.getLignes()) {
            Produit p = l.getProduit();
            System.out.println(p.getReference() + "\t\t" + p.getPrix() + " DH\t" + l.getQuantite());
        }

        // Test 4: Afficher les produits commandés entre deux dates
        try {
            Date dateDebut = dateFormat.parse("2013-01-01");
            Date dateFin = dateFormat.parse("2013-12-31");
            System.out.println("\nTest 4: Produits commandés en 2013:");
            List<Produit> produitsDates = ps.findProduitsCommandesEntreDates(dateDebut, dateFin);
            for (Produit p : produitsDates) {
                System.out.println("  - " + p.getReference());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("\n--- FIN DES TESTS ---");
    }
}