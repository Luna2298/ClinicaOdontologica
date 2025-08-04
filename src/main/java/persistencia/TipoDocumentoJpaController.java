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
import logica.TipoDocumento;
import persistencia.exceptions.NonexistentEntityException;


public class TipoDocumentoJpaController implements Serializable {

    public TipoDocumentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
    
    public TipoDocumentoJpaController() {
    
        emf = Persistence.createEntityManagerFactory("ConsultorioOdontologico_PU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoDocumento tipoDocumento) {
        if (tipoDocumento.getListaPersonas() == null) {
            tipoDocumento.setListaPersonas(new ArrayList<Persona>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Persona> attachedListaPersonas = new ArrayList<Persona>();
            for (Persona listaPersonasPersonaToAttach : tipoDocumento.getListaPersonas()) {
                listaPersonasPersonaToAttach = em.getReference(listaPersonasPersonaToAttach.getClass(), listaPersonasPersonaToAttach.getId());
                attachedListaPersonas.add(listaPersonasPersonaToAttach);
            }
            tipoDocumento.setListaPersonas(attachedListaPersonas);
            em.persist(tipoDocumento);
            for (Persona listaPersonasPersona : tipoDocumento.getListaPersonas()) {
                TipoDocumento oldTipoDocumentoOfListaPersonasPersona = listaPersonasPersona.getTipoDocumento();
                listaPersonasPersona.setTipoDocumento(tipoDocumento);
                listaPersonasPersona = em.merge(listaPersonasPersona);
                if (oldTipoDocumentoOfListaPersonasPersona != null) {
                    oldTipoDocumentoOfListaPersonasPersona.getListaPersonas().remove(listaPersonasPersona);
                    oldTipoDocumentoOfListaPersonasPersona = em.merge(oldTipoDocumentoOfListaPersonasPersona);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoDocumento tipoDocumento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoDocumento persistentTipoDocumento = em.find(TipoDocumento.class, tipoDocumento.getIdTipoDocumento());
            List<Persona> listaPersonasOld = persistentTipoDocumento.getListaPersonas();
            List<Persona> listaPersonasNew = tipoDocumento.getListaPersonas();
            List<Persona> attachedListaPersonasNew = new ArrayList<Persona>();
            for (Persona listaPersonasNewPersonaToAttach : listaPersonasNew) {
                listaPersonasNewPersonaToAttach = em.getReference(listaPersonasNewPersonaToAttach.getClass(), listaPersonasNewPersonaToAttach.getId());
                attachedListaPersonasNew.add(listaPersonasNewPersonaToAttach);
            }
            listaPersonasNew = attachedListaPersonasNew;
            tipoDocumento.setListaPersonas(listaPersonasNew);
            tipoDocumento = em.merge(tipoDocumento);
            for (Persona listaPersonasOldPersona : listaPersonasOld) {
                if (!listaPersonasNew.contains(listaPersonasOldPersona)) {
                    listaPersonasOldPersona.setTipoDocumento(null);
                    listaPersonasOldPersona = em.merge(listaPersonasOldPersona);
                }
            }
            for (Persona listaPersonasNewPersona : listaPersonasNew) {
                if (!listaPersonasOld.contains(listaPersonasNewPersona)) {
                    TipoDocumento oldTipoDocumentoOfListaPersonasNewPersona = listaPersonasNewPersona.getTipoDocumento();
                    listaPersonasNewPersona.setTipoDocumento(tipoDocumento);
                    listaPersonasNewPersona = em.merge(listaPersonasNewPersona);
                    if (oldTipoDocumentoOfListaPersonasNewPersona != null && !oldTipoDocumentoOfListaPersonasNewPersona.equals(tipoDocumento)) {
                        oldTipoDocumentoOfListaPersonasNewPersona.getListaPersonas().remove(listaPersonasNewPersona);
                        oldTipoDocumentoOfListaPersonasNewPersona = em.merge(oldTipoDocumentoOfListaPersonasNewPersona);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = tipoDocumento.getIdTipoDocumento();
                if (findTipoDocumento(id) == null) {
                    throw new NonexistentEntityException("The tipoDocumento with id " + id + " no longer exists.");
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
            TipoDocumento tipoDocumento;
            try {
                tipoDocumento = em.getReference(TipoDocumento.class, id);
                tipoDocumento.getIdTipoDocumento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoDocumento with id " + id + " no longer exists.", enfe);
            }
            List<Persona> listaPersonas = tipoDocumento.getListaPersonas();
            for (Persona listaPersonasPersona : listaPersonas) {
                listaPersonasPersona.setTipoDocumento(null);
                listaPersonasPersona = em.merge(listaPersonasPersona);
            }
            em.remove(tipoDocumento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoDocumento> findTipoDocumentoEntities() {
        return findTipoDocumentoEntities(true, -1, -1);
    }

    public List<TipoDocumento> findTipoDocumentoEntities(int maxResults, int firstResult) {
        return findTipoDocumentoEntities(false, maxResults, firstResult);
    }

    private List<TipoDocumento> findTipoDocumentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoDocumento.class));
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

    public TipoDocumento findTipoDocumento(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoDocumento.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoDocumentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoDocumento> rt = cq.from(TipoDocumento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
