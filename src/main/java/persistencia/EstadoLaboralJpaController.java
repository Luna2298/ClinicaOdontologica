package persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import logica.Odontologo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import logica.EstadoLaboral;
import logica.Secretario;
import persistencia.exceptions.NonexistentEntityException;


public class EstadoLaboralJpaController implements Serializable {

    public EstadoLaboralJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
    
    public EstadoLaboralJpaController() {
    
        emf = Persistence.createEntityManagerFactory("ConsultorioOdontologico_PU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadoLaboral estadoLaboral) {
        if (estadoLaboral.getListaOdontologos() == null) {
            estadoLaboral.setListaOdontologos(new ArrayList<Odontologo>());
        }
        if (estadoLaboral.getListaSecretarios() == null) {
            estadoLaboral.setListaSecretarios(new ArrayList<Secretario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Odontologo> attachedListaOdontologos = new ArrayList<Odontologo>();
            for (Odontologo listaOdontologosOdontologoToAttach : estadoLaboral.getListaOdontologos()) {
                listaOdontologosOdontologoToAttach = em.getReference(listaOdontologosOdontologoToAttach.getClass(), listaOdontologosOdontologoToAttach.getId());
                attachedListaOdontologos.add(listaOdontologosOdontologoToAttach);
            }
            estadoLaboral.setListaOdontologos(attachedListaOdontologos);
            List<Secretario> attachedListaSecretarios = new ArrayList<Secretario>();
            for (Secretario listaSecretariosSecretarioToAttach : estadoLaboral.getListaSecretarios()) {
                listaSecretariosSecretarioToAttach = em.getReference(listaSecretariosSecretarioToAttach.getClass(), listaSecretariosSecretarioToAttach.getId());
                attachedListaSecretarios.add(listaSecretariosSecretarioToAttach);
            }
            estadoLaboral.setListaSecretarios(attachedListaSecretarios);
            em.persist(estadoLaboral);
            for (Odontologo listaOdontologosOdontologo : estadoLaboral.getListaOdontologos()) {
                EstadoLaboral oldEstadoLaboralOdontoOfListaOdontologosOdontologo = listaOdontologosOdontologo.getEstadoLaboralOdonto();
                listaOdontologosOdontologo.setEstadoLaboralOdonto(estadoLaboral);
                listaOdontologosOdontologo = em.merge(listaOdontologosOdontologo);
                if (oldEstadoLaboralOdontoOfListaOdontologosOdontologo != null) {
                    oldEstadoLaboralOdontoOfListaOdontologosOdontologo.getListaOdontologos().remove(listaOdontologosOdontologo);
                    oldEstadoLaboralOdontoOfListaOdontologosOdontologo = em.merge(oldEstadoLaboralOdontoOfListaOdontologosOdontologo);
                }
            }
            for (Secretario listaSecretariosSecretario : estadoLaboral.getListaSecretarios()) {
                EstadoLaboral oldEstadoLaboralSecreOfListaSecretariosSecretario = listaSecretariosSecretario.getEstadoLaboralSecre();
                listaSecretariosSecretario.setEstadoLaboralSecre(estadoLaboral);
                listaSecretariosSecretario = em.merge(listaSecretariosSecretario);
                if (oldEstadoLaboralSecreOfListaSecretariosSecretario != null) {
                    oldEstadoLaboralSecreOfListaSecretariosSecretario.getListaSecretarios().remove(listaSecretariosSecretario);
                    oldEstadoLaboralSecreOfListaSecretariosSecretario = em.merge(oldEstadoLaboralSecreOfListaSecretariosSecretario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstadoLaboral estadoLaboral) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadoLaboral persistentEstadoLaboral = em.find(EstadoLaboral.class, estadoLaboral.getIdEstadoLaboral());
            List<Odontologo> listaOdontologosOld = persistentEstadoLaboral.getListaOdontologos();
            List<Odontologo> listaOdontologosNew = estadoLaboral.getListaOdontologos();
            List<Secretario> listaSecretariosOld = persistentEstadoLaboral.getListaSecretarios();
            List<Secretario> listaSecretariosNew = estadoLaboral.getListaSecretarios();
            List<Odontologo> attachedListaOdontologosNew = new ArrayList<Odontologo>();
            for (Odontologo listaOdontologosNewOdontologoToAttach : listaOdontologosNew) {
                listaOdontologosNewOdontologoToAttach = em.getReference(listaOdontologosNewOdontologoToAttach.getClass(), listaOdontologosNewOdontologoToAttach.getId());
                attachedListaOdontologosNew.add(listaOdontologosNewOdontologoToAttach);
            }
            listaOdontologosNew = attachedListaOdontologosNew;
            estadoLaboral.setListaOdontologos(listaOdontologosNew);
            List<Secretario> attachedListaSecretariosNew = new ArrayList<Secretario>();
            for (Secretario listaSecretariosNewSecretarioToAttach : listaSecretariosNew) {
                listaSecretariosNewSecretarioToAttach = em.getReference(listaSecretariosNewSecretarioToAttach.getClass(), listaSecretariosNewSecretarioToAttach.getId());
                attachedListaSecretariosNew.add(listaSecretariosNewSecretarioToAttach);
            }
            listaSecretariosNew = attachedListaSecretariosNew;
            estadoLaboral.setListaSecretarios(listaSecretariosNew);
            estadoLaboral = em.merge(estadoLaboral);
            for (Odontologo listaOdontologosOldOdontologo : listaOdontologosOld) {
                if (!listaOdontologosNew.contains(listaOdontologosOldOdontologo)) {
                    listaOdontologosOldOdontologo.setEstadoLaboralOdonto(null);
                    listaOdontologosOldOdontologo = em.merge(listaOdontologosOldOdontologo);
                }
            }
            for (Odontologo listaOdontologosNewOdontologo : listaOdontologosNew) {
                if (!listaOdontologosOld.contains(listaOdontologosNewOdontologo)) {
                    EstadoLaboral oldEstadoLaboralOdontoOfListaOdontologosNewOdontologo = listaOdontologosNewOdontologo.getEstadoLaboralOdonto();
                    listaOdontologosNewOdontologo.setEstadoLaboralOdonto(estadoLaboral);
                    listaOdontologosNewOdontologo = em.merge(listaOdontologosNewOdontologo);
                    if (oldEstadoLaboralOdontoOfListaOdontologosNewOdontologo != null && !oldEstadoLaboralOdontoOfListaOdontologosNewOdontologo.equals(estadoLaboral)) {
                        oldEstadoLaboralOdontoOfListaOdontologosNewOdontologo.getListaOdontologos().remove(listaOdontologosNewOdontologo);
                        oldEstadoLaboralOdontoOfListaOdontologosNewOdontologo = em.merge(oldEstadoLaboralOdontoOfListaOdontologosNewOdontologo);
                    }
                }
            }
            for (Secretario listaSecretariosOldSecretario : listaSecretariosOld) {
                if (!listaSecretariosNew.contains(listaSecretariosOldSecretario)) {
                    listaSecretariosOldSecretario.setEstadoLaboralSecre(null);
                    listaSecretariosOldSecretario = em.merge(listaSecretariosOldSecretario);
                }
            }
            for (Secretario listaSecretariosNewSecretario : listaSecretariosNew) {
                if (!listaSecretariosOld.contains(listaSecretariosNewSecretario)) {
                    EstadoLaboral oldEstadoLaboralSecreOfListaSecretariosNewSecretario = listaSecretariosNewSecretario.getEstadoLaboralSecre();
                    listaSecretariosNewSecretario.setEstadoLaboralSecre(estadoLaboral);
                    listaSecretariosNewSecretario = em.merge(listaSecretariosNewSecretario);
                    if (oldEstadoLaboralSecreOfListaSecretariosNewSecretario != null && !oldEstadoLaboralSecreOfListaSecretariosNewSecretario.equals(estadoLaboral)) {
                        oldEstadoLaboralSecreOfListaSecretariosNewSecretario.getListaSecretarios().remove(listaSecretariosNewSecretario);
                        oldEstadoLaboralSecreOfListaSecretariosNewSecretario = em.merge(oldEstadoLaboralSecreOfListaSecretariosNewSecretario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = estadoLaboral.getIdEstadoLaboral();
                if (findEstadoLaboral(id) == null) {
                    throw new NonexistentEntityException("The estadoLaboral with id " + id + " no longer exists.");
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
            EstadoLaboral estadoLaboral;
            try {
                estadoLaboral = em.getReference(EstadoLaboral.class, id);
                estadoLaboral.getIdEstadoLaboral();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadoLaboral with id " + id + " no longer exists.", enfe);
            }
            List<Odontologo> listaOdontologos = estadoLaboral.getListaOdontologos();
            for (Odontologo listaOdontologosOdontologo : listaOdontologos) {
                listaOdontologosOdontologo.setEstadoLaboralOdonto(null);
                listaOdontologosOdontologo = em.merge(listaOdontologosOdontologo);
            }
            List<Secretario> listaSecretarios = estadoLaboral.getListaSecretarios();
            for (Secretario listaSecretariosSecretario : listaSecretarios) {
                listaSecretariosSecretario.setEstadoLaboralSecre(null);
                listaSecretariosSecretario = em.merge(listaSecretariosSecretario);
            }
            em.remove(estadoLaboral);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstadoLaboral> findEstadoLaboralEntities() {
        return findEstadoLaboralEntities(true, -1, -1);
    }

    public List<EstadoLaboral> findEstadoLaboralEntities(int maxResults, int firstResult) {
        return findEstadoLaboralEntities(false, maxResults, firstResult);
    }

    private List<EstadoLaboral> findEstadoLaboralEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadoLaboral.class));
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

    public EstadoLaboral findEstadoLaboral(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoLaboral.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoLaboralCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadoLaboral> rt = cq.from(EstadoLaboral.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
