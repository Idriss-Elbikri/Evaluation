package ma.projet.dao;

import java.util.List;

// T est un type générique (il sera remplacé par Produit, Categorie, etc.)
public interface IDao<T> {

    // Crée un objet T dans la base de données
    boolean create(T o);

    // Supprime un objet T de la base de données
    boolean delete(T o);

    // Met à jour un objet T dans la base de données
    boolean update(T o);

    // Trouve un objet T par son ID
    //T findById(int id);
    T findById(Object id);

    // Renvoie la liste de tous les objets T
    List<T> findAll();

}