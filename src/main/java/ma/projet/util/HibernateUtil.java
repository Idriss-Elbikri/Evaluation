package ma.projet.util;

// Importer les classes de l'exercice 2
import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.EmployeTachePK;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            // Créer une nouvelle configuration Hibernate
            Configuration config = new Configuration();

            // PAS BESOIN de config.configure()
            // Hibernate trouvera "hibernate.properties" tout seul.

            // Ajouter les classes entités de L'EXERCICE 2
            config.addAnnotatedClass(Employe.class);
            config.addAnnotatedClass(Projet.class);
            config.addAnnotatedClass(Tache.class);
            config.addAnnotatedClass(EmployeTache.class);
            config.addAnnotatedClass(EmployeTachePK.class);

            // Construire la SessionFactory
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