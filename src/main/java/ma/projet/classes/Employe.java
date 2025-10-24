package ma.projet.classes;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String prenom;
    private String telephone;

    @OneToMany(mappedBy = "chefDeProjet")
    private List<Projet> projetsGeres;

    @OneToMany(mappedBy = "employe", fetch = FetchType.LAZY)
    private List<EmployeTache> tachesAssignees;

    public Employe() {
    }

    public Employe(String nom, String prenom, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<Projet> getProjetsGeres() {
        return projetsGeres;
    }

    public void setProjetsGeres(List<Projet> projetsGeres) {
        this.projetsGeres = projetsGeres;
    }

    public List<EmployeTache> getTachesAssignees() {
        return tachesAssignees;
    }

    public void setTachesAssignees(List<EmployeTache> tachesAssignees) {
        this.tachesAssignees = tachesAssignees;
    }
}