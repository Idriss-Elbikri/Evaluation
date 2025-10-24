package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.persistence.criteria.Predicate;

import java.util.Date;
import java.util.List;

public class FemmeService implements IDao<Femme> {

    @Override
    public boolean create(Femme o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean delete(Femme o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean update(Femme o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Femme findById(Object id) {
        Session session = null;
        Transaction tx = null;
        Femme femme = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            femme = (Femme) session.get(Femme.class, (Integer) id);
            tx.commit();
            return femme;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Femme> findAll() {
        Session session = null;
        Transaction tx = null;
        List<Femme> femmes = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            femmes = session.createQuery("from Femme").list();
            tx.commit();
            return femmes;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Long getNombreEnfants(Femme femme, Date dateDebut, Date dateFin) {
        Session session = null;
        Transaction tx = null;
        Long nbrEnfants = 0L;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            Query query = session.getNamedQuery("Femme.findNbrEnfants");
            query.setParameter("femme", femme);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            nbrEnfants = (Long) query.uniqueResult();
            tx.commit();
            return nbrEnfants != null ? nbrEnfants : 0L;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return 0L;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Femme> findFemmesMarieesDeuxFois() {
        Session session = null;
        Transaction tx = null;
        List<Femme> femmes = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            Query query = session.getNamedQuery("Femme.findFemmesMarieesDeuxFois");
            femmes = query.list();
            tx.commit();
            return femmes;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Long getNombreHommesMaries(int nombreEpouses, Date dateDebut, Date dateFin) {
        Session session = null;
        Transaction tx = null;
        Long count = 0L;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Homme> homme = cq.from(Homme.class);
            cq.select(cb.count(homme));

            Subquery<Integer> subquery = cq.subquery(Integer.class);
            Root<Mariage> mariage = subquery.from(Mariage.class);
            subquery.select(mariage.get("homme").get("id"));

            Predicate datePredicate = cb.between(mariage.get("dateDebut"), dateDebut, dateFin);
            subquery.where(datePredicate);

            subquery.groupBy(mariage.get("homme").get("id"));

            subquery.having(cb.greaterThanOrEqualTo(cb.count(mariage.get("femme")), (long) nombreEpouses));

            cq.where(homme.get("id").in(subquery));

            Query<Long> query = session.createQuery(cq);
            count = query.uniqueResult();

            tx.commit();
            return count != null ? count : 0L;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return 0L;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}