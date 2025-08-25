package persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import logica.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import logica.Rol;
import persistencia.exceptions.NonexistentEntityException;


public class RolJpaController implements Serializable {

    public RolJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
    
    public RolJpaController() {
    
        emf = Persistence.createEntityManagerFactory("ConsultorioOdontologico_PU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rol rol) {
        if (rol.getListaUsuarios() == null) {
            rol.setListaUsuarios(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Usuario> attachedListaUsuarios = new ArrayList<Usuario>();
            for (Usuario listaUsuariosUsuarioToAttach : rol.getListaUsuarios()) {
                listaUsuariosUsuarioToAttach = em.getReference(listaUsuariosUsuarioToAttach.getClass(), listaUsuariosUsuarioToAttach.getIdUsuario());
                attachedListaUsuarios.add(listaUsuariosUsuarioToAttach);
            }
            rol.setListaUsuarios(attachedListaUsuarios);
            em.persist(rol);
            for (Usuario listaUsuariosUsuario : rol.getListaUsuarios()) {
                Rol oldTipoRolOfListaUsuariosUsuario = listaUsuariosUsuario.getTipoRol();
                listaUsuariosUsuario.setTipoRol(rol);
                listaUsuariosUsuario = em.merge(listaUsuariosUsuario);
                if (oldTipoRolOfListaUsuariosUsuario != null) {
                    oldTipoRolOfListaUsuariosUsuario.getListaUsuarios().remove(listaUsuariosUsuario);
                    oldTipoRolOfListaUsuariosUsuario = em.merge(oldTipoRolOfListaUsuariosUsuario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rol rol) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol persistentRol = em.find(Rol.class, rol.getIdRol());
            List<Usuario> listaUsuariosOld = persistentRol.getListaUsuarios();
            List<Usuario> listaUsuariosNew = rol.getListaUsuarios();
            List<Usuario> attachedListaUsuariosNew = new ArrayList<Usuario>();
            for (Usuario listaUsuariosNewUsuarioToAttach : listaUsuariosNew) {
                listaUsuariosNewUsuarioToAttach = em.getReference(listaUsuariosNewUsuarioToAttach.getClass(), listaUsuariosNewUsuarioToAttach.getIdUsuario());
                attachedListaUsuariosNew.add(listaUsuariosNewUsuarioToAttach);
            }
            listaUsuariosNew = attachedListaUsuariosNew;
            rol.setListaUsuarios(listaUsuariosNew);
            rol = em.merge(rol);
            for (Usuario listaUsuariosOldUsuario : listaUsuariosOld) {
                if (!listaUsuariosNew.contains(listaUsuariosOldUsuario)) {
                    listaUsuariosOldUsuario.setTipoRol(null);
                    listaUsuariosOldUsuario = em.merge(listaUsuariosOldUsuario);
                }
            }
            for (Usuario listaUsuariosNewUsuario : listaUsuariosNew) {
                if (!listaUsuariosOld.contains(listaUsuariosNewUsuario)) {
                    Rol oldTipoRolOfListaUsuariosNewUsuario = listaUsuariosNewUsuario.getTipoRol();
                    listaUsuariosNewUsuario.setTipoRol(rol);
                    listaUsuariosNewUsuario = em.merge(listaUsuariosNewUsuario);
                    if (oldTipoRolOfListaUsuariosNewUsuario != null && !oldTipoRolOfListaUsuariosNewUsuario.equals(rol)) {
                        oldTipoRolOfListaUsuariosNewUsuario.getListaUsuarios().remove(listaUsuariosNewUsuario);
                        oldTipoRolOfListaUsuariosNewUsuario = em.merge(oldTipoRolOfListaUsuariosNewUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = rol.getIdRol();
                if (findRol(id) == null) {
                    throw new NonexistentEntityException("The rol with id " + id + " no longer exists.");
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
            Rol rol;
            try {
                rol = em.getReference(Rol.class, id);
                rol.getIdRol();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rol with id " + id + " no longer exists.", enfe);
            }
            List<Usuario> listaUsuarios = rol.getListaUsuarios();
            for (Usuario listaUsuariosUsuario : listaUsuarios) {
                listaUsuariosUsuario.setTipoRol(null);
                listaUsuariosUsuario = em.merge(listaUsuariosUsuario);
            }
            em.remove(rol);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rol> findRolEntities() {
        return findRolEntities(true, -1, -1);
    }

    public List<Rol> findRolEntities(int maxResults, int firstResult) {
        return findRolEntities(false, maxResults, firstResult);
    }

    private List<Rol> findRolEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rol.class));
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

    public Rol findRol(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rol.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rol> rt = cq.from(Rol.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
