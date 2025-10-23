package ma.projet.dao;

import java.util.List;

// T est un type générique (Employe, Projet, Tache...)
public interface IDao<T> {

    // Crée un objet T
    boolean create(T o);

    // Supprime un objet T
    boolean delete(T o);

    // Met à jour un objet T
    boolean update(T o);

    // Trouve un objet T par son ID (int ou clé composite)
    T findById(Object id);

    // Renvoie la liste de tous les objets T
    List<T> findAll();

}