package ma.projet.classes;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.NamedQuery; // N'oubliez pas cet import
import javax.persistence.NamedQueries; // N'oubliez pas cet import

import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Tache.findPrixSup1000",
                query = "from Tache t where t.prix > 1000.0")
})
public class Tache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;

    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    private Date dateFin;

    private double prix;

    // Relation : Plusieurs tâches appartiennent à un projet
    @ManyToOne
    private Projet projet;

    // Relation : Une tâche peut être assignée à plusieurs employés
    @OneToMany(mappedBy = "tache", fetch = FetchType.LAZY)
    private List<EmployeTache> employesAssignes;

    // Constructeurs
    public Tache() {
    }

    public Tache(String nom, Date dateDebut, Date dateFin, double prix, Projet projet) {
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prix = prix;
        this.projet = projet;
    }

    // Getters et Setters
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

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public List<EmployeTache> getEmployesAssignes() {
        return employesAssignes;
    }

    public void setEmployesAssignes(List<EmployeTache> employesAssignes) {
        this.employesAssignes = employesAssignes;
    }
}