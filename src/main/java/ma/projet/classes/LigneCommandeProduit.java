package ma.projet.classes;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class LigneCommandeProduit {

    @EmbeddedId
    private LigneCommandeProduitPK id;

    private int quantite;

    @ManyToOne
    @JoinColumn(name = "commandeId", referencedColumnName = "id", insertable = false, updatable = false)
    private Commande commande;

    @ManyToOne
    @JoinColumn(name = "produitId", referencedColumnName = "id", insertable = false, updatable = false)
    private Produit produit;

    public LigneCommandeProduit() {
    }

    public LigneCommandeProduit(LigneCommandeProduitPK id, int quantite) {
        this.id = id;
        this.quantite = quantite;
    }

    public LigneCommandeProduitPK getId() {
        return id;
    }

    public void setId(LigneCommandeProduitPK id) {
        this.id = id;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }
}