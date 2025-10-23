package ma.projet.util;

import ma.projet.classes.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            // Créer une nouvelle configuration Hibernate
            Configuration config = new Configuration();

            // CORRECTION:
            // Nous supprimons la ligne "config.configure();"
            // Le "ServiceRegistry" d'Hibernate trouve et charge
            // "hibernate.properties" tout seul (on le voit dans le log).
            // L'appel à "config.configure()" forçait la recherche d'un "hibernate.cfg.xml"
            // qui n'existe pas.

            // config.configure(); // LIGNE SUPPRIMÉE

            // Ajouter toutes nos classes entités à la configuration
            config.addAnnotatedClass(Categorie.class);
            config.addAnnotatedClass(Commande.class);
            config.addAnnotatedClass(Produit.class);
            config.addAnnotatedClass(LigneCommandeProduit.class);
            config.addAnnotatedClass(LigneCommandeProduitPK.class);

            // Construire la SessionFactory
            // C'est à ce moment que les "hibernate.properties" sont VRAIMENT utilisées.
            sessionFactory = config.buildSessionFactory();

        } catch (Throwable ex) {
            // En cas d'erreur lors de l'initialisation
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Méthode publique statique pour obtenir la SessionFactory
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}