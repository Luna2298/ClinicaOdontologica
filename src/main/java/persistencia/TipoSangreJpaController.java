package persistencia;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import logica.TipoSangre;
import persistencia.exceptions.NonexistentEntityException;


public class TipoSangreJpaController implements Serializable {

    public TipoSangreJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    
    private EntityManagerFactory emf = null;
    
    public TipoSangreJpaController() {
    
        emf = Persistence.createEntityManagerFactory("ConsultorioOdontologico_PU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoSangre tipoSangre) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tipoSangre);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoSangre tipoSangre) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tipoSangre = em.merge(tipoSangre);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = tipoSangre.getIdTipoSangre();
                if (findTipoSangre(id) == null) {
                    throw new NonexistentEntityException("The tipoSangre with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoSangre tipoSangre;
            try {
                tipoSangre = em.getReference(TipoSangre.class, id);
                tipoSangre.getIdTipoSangre();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoSangre with id " + id + " no longer exists.", enfe);
            }
            em.remove(tipoSangre);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoSangre> findTipoSangreEntities() {
        return findTipoSangreEntities(true, -1, -1);
    }

    public List<TipoSangre> findTipoSangreEntities(int maxResults, int firstResult) {
        return findTipoSangreEntities(false, maxResults, firstResult);
    }

    private List<TipoSangre> findTipoSangreEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoSangre.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TipoSangre findTipoSangre(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoSangre.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoSangreCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoSangre> rt = cq.from(TipoSangre.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
