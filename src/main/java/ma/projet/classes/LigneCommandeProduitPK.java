package ma.projet.classes;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class LigneCommandeProduitPK implements Serializable {

    private int commandeId;
    private int produitId;

    public LigneCommandeProduitPK() {
    }

    public int getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(int commandeId) {
        this.commandeId = commandeId;
    }

    public int getProduitId() {
        return produitId;
    }

    public void setProduitId(int produitId) {
        this.produitId = produitId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) commandeId;
        hash += (int) produitId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof LigneCommandeProduitPK)) {
            return false;
        }
        LigneCommandeProduitPK other = (LigneCommandeProduitPK) object;
        if (this.commandeId != other.commandeId) {
            return false;
        }
        if (this.produitId != other.produitId) {
            return false;
        }
        return true;
    }
}