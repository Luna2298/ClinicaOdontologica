package persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import logica.Persona;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import logica.ObraSocial;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author lunal
 */
public class ObraSocialJpaController implements Serializable {

    public ObraSocialJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
    
    public ObraSocialJpaController() {
    
        emf = Persistence.createEntityManagerFactory("ConsultorioOdontologico_PU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ObraSocial obraSocial) {
        if (obraSocial.getListaPersonas() == null) {
            obraSocial.setListaPersonas(new ArrayList<Persona>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Persona> attachedListaPersonas = new ArrayList<Persona>();
            for (Persona listaPersonasPersonaToAttach : obraSocial.getListaPersonas()) {
                listaPersonasPersonaToAttach = em.getReference(listaPersonasPersonaToAttach.getClass(), listaPersonasPersonaToAttach.getId());
                attachedListaPersonas.add(listaPersonasPersonaToAttach);
            }
            obraSocial.setListaPersonas(attachedListaPersonas);
            em.persist(obraSocial);
            for (Persona listaPersonasPersona : obraSocial.getListaPersonas()) {
                ObraSocial oldObraSocialOfListaPersonasPersona = listaPersonasPersona.getObraSocial();
                listaPersonasPersona.setObraSocial(obraSocial);
                listaPersonasPersona = em.merge(listaPersonasPersona);
                if (oldObraSocialOfListaPersonasPersona != null) {
                    oldObraSocialOfListaPersonasPersona.getListaPersonas().remove(listaPersonasPersona);
                    oldObraSocialOfListaPersonasPersona = em.merge(oldObraSocialOfListaPersonasPersona);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ObraSocial obraSocial) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ObraSocial persistentObraSocial = em.find(ObraSocial.class, obraSocial.getIdObraSocial());
            List<Persona> listaPersonasOld = persistentObraSocial.getListaPersonas();
            List<Persona> listaPersonasNew = obraSocial.getListaPersonas();
            List<Persona> attachedListaPersonasNew = new ArrayList<Persona>();
            for (Persona listaPersonasNewPersonaToAttach : listaPersonasNew) {
                listaPersonasNewPersonaToAttach = em.getReference(listaPersonasNewPersonaToAttach.getClass(), listaPersonasNewPersonaToAttach.getId());
                attachedListaPersonasNew.add(listaPersonasNewPersonaToAttach);
            }
            listaPersonasNew = attachedListaPersonasNew;
            obraSocial.setListaPersonas(listaPersonasNew);
            obraSocial = em.merge(obraSocial);
            for (Persona listaPersonasOldPersona : listaPersonasOld) {
                if (!listaPersonasNew.contains(listaPersonasOldPersona)) {
                    listaPersonasOldPersona.setObraSocial(null);
                    listaPersonasOldPersona = em.merge(listaPersonasOldPersona);
                }
            }
            for (Persona listaPersonasNewPersona : listaPersonasNew) {
                if (!listaPersonasOld.contains(listaPersonasNewPersona)) {
                    ObraSocial oldObraSocialOfListaPersonasNewPersona = listaPersonasNewPersona.getObraSocial();
                    listaPersonasNewPersona.setObraSocial(obraSocial);
                    listaPersonasNewPersona = em.merge(listaPersonasNewPersona);
                    if (oldObraSocialOfListaPersonasNewPersona != null && !oldObraSocialOfListaPersonasNewPersona.equals(obraSocial)) {
                        oldObraSocialOfListaPersonasNewPersona.getListaPersonas().remove(listaPersonasNewPersona);
                        oldObraSocialOfListaPersonasNewPersona = em.merge(oldObraSocialOfListaPersonasNewPersona);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = obraSocial.getIdObraSocial();
                if (findObraSocial(id) == null) {
                    throw new NonexistentEntityException("The obraSocial with id " + id + " no longer exists.");
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
            ObraSocial obraSocial;
            try {
                obraSocial = em.getReference(ObraSocial.class, id);
                obraSocial.getIdObraSocial();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The obraSocial with id " + id + " no longer exists.", enfe);
            }
            List<Persona> listaPersonas = obraSocial.getListaPersonas();
            for (Persona listaPersonasPersona : listaPersonas) {
                listaPersonasPersona.setObraSocial(null);
                listaPersonasPersona = em.merge(listaPersonasPersona);
            }
            em.remove(obraSocial);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ObraSocial> findObraSocialEntities() {
        return findObraSocialEntities(true, -1, -1);
    }

    public List<ObraSocial> findObraSocialEntities(int maxResults, int firstResult) {
        return findObraSocialEntities(false, maxResults, firstResult);
    }

    private List<ObraSocial> findObraSocialEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ObraSocial.class));
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

    public ObraSocial findObraSocial(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ObraSocial.class, id);
        } finally {
            em.close();
        }
    }

    public int getObraSocialCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ObraSocial> rt = cq.from(ObraSocial.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
