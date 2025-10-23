package ma.projet.classes;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Produit.findPrixSup100",
                query = "from Produit p where p.prix > 100.0")
})
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String reference;
    private float prix;

    // Relation : Plusieurs produits peuvent appartenir à une catégorie
    @ManyToOne
    private Categorie categorie;

    // Relation : Un produit peut être dans plusieurs lignes de commande
    @OneToMany(mappedBy = "produit")
    private List<LigneCommandeProduit> lignes;

    // Constructeurs
    public Produit() {
    }

    public Produit(String reference, float prix, Categorie categorie) {
        this.reference = reference;
        this.prix = prix;
        this.categorie = categorie;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public List<LigneCommandeProduit> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneCommandeProduit> lignes) {
        this.lignes = lignes;
    }
}