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
import logica.HorarioTrabajo;
import persistencia.exceptions.NonexistentEntityException;


public class HorarioTrabajoJpaController implements Serializable {

    public HorarioTrabajoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public HorarioTrabajoJpaController() {
    
        emf = Persistence.createEntityManagerFactory("ConsultorioOdontologico_PU");
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HorarioTrabajo horarioTrabajo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(horarioTrabajo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HorarioTrabajo horarioTrabajo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            horarioTrabajo = em.merge(horarioTrabajo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = horarioTrabajo.getIdHorario();
                if (findHorarioTrabajo(id) == null) {
                    throw new NonexistentEntityException("The horarioTrabajo with id " + id + " no longer exists.");
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
            HorarioTrabajo horarioTrabajo;
            try {
                horarioTrabajo = em.getReference(HorarioTrabajo.class, id);
                horarioTrabajo.getIdHorario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The horarioTrabajo with id " + id + " no longer exists.", enfe);
            }
            em.remove(horarioTrabajo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HorarioTrabajo> findHorarioTrabajoEntities() {
        return findHorarioTrabajoEntities(true, -1, -1);
    }

    public List<HorarioTrabajo> findHorarioTrabajoEntities(int maxResults, int firstResult) {
        return findHorarioTrabajoEntities(false, maxResults, firstResult);
    }

    private List<HorarioTrabajo> findHorarioTrabajoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HorarioTrabajo.class));
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

    public HorarioTrabajo findHorarioTrabajo(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HorarioTrabajo.class, id);
        } finally {
            em.close();
        }
    }

    public int getHorarioTrabajoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HorarioTrabajo> rt = cq.from(HorarioTrabajo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
