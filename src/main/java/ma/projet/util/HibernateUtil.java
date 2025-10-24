package ma.projet.util;

import ma.projet.classes.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration config = new Configuration();

            config.addAnnotatedClass(Categorie.class);
            config.addAnnotatedClass(Commande.class);
            config.addAnnotatedClass(Produit.class);
            config.addAnnotatedClass(LigneCommandeProduit.class);
            config.addAnnotatedClass(LigneCommandeProduitPK.class);

            sessionFactory = config.buildSessionFactory();

        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}