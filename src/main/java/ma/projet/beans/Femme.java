package ma.projet.beans;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Femme.findNbrEnfants",
                query = "SELECT SUM(m.nbrEnfants) FROM Mariage m " +
                        "WHERE m.femme = :femme " +
                        "AND m.dateDebut BETWEEN :dateDebut AND :dateFin"),

        @NamedQuery(name = "Femme.findFemmesMarieesDeuxFois",
                query = "SELECT f FROM Femme f WHERE SIZE(f.mariages) >= 2")
})
public class Femme extends Personne {

    @OneToMany(mappedBy = "femme", fetch = FetchType.LAZY)
    private List<Mariage> mariages;

    public Femme() {
        super();
    }

    public Femme(String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }

    public List<Mariage> getMariages() {
        return mariages;
    }

    public void setMariages(List<Mariage> mariages) {
        this.mariages = mariages;
    }
}