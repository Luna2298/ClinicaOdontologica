package persistencia;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.Odontologo;
import logica.Paciente;
import logica.Persona;
import logica.Secretario;
import logica.TipoDocumento;
import logica.TipoSangre;
import logica.Usuario;
import persistencia.exceptions.NonexistentEntityException;


public class ControladoraPersistencia {
    
    HorarioTrabajoJpaController horaJpa /*= new HorarioTrabajoJpaController()*/;
    OdontologoJpaController odontoJpa /*= new OdontologoJpaController()*/;
    PacienteJpaController pacienteJpa /*= new PacienteJpaController()*/;
    ResponsableJpaController respoJpa /*= new ResponsableJpaController()*/;
    SecretarioJpaController secreJpa /*= new SecretarioJpaController()*/;
    TurnoJpaController turnoJpa /*= new TurnoJpaController()*/;
    UsuarioJpaController usuarioJpa/* = new UsuarioJpaController()*/;
    TipoSangreJpaController tipoSanJpa;
    TipoDocumentoJpaController tipoDocJpa;
    PersonaJpaController personaJpa;

    public ControladoraPersistencia() {
        
        usuarioJpa = new UsuarioJpaController();
        turnoJpa = new TurnoJpaController();
        secreJpa = new SecretarioJpaController();
        respoJpa = new ResponsableJpaController();
        pacienteJpa = new PacienteJpaController();
        odontoJpa = new OdontologoJpaController();
        horaJpa = new HorarioTrabajoJpaController();
        tipoSanJpa = new TipoSangreJpaController();
        tipoDocJpa = new TipoDocumentoJpaController();
        personaJpa = new PersonaJpaController();
    }

    
    
    public void crearUsuario(Usuario usu) {
        
        usuarioJpa.create(usu);
    }

    public List<Usuario> getListUsers() {
       
        return usuarioJpa.findUsuarioEntities();
    }

    public void borrarUsuario(int id) {
        
        try {
            usuarioJpa.destroy(id);
            
        } catch (NonexistentEntityException ex) {
            
            Logger.getLogger(ControladoraPersistencia.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    public Usuario traerUsuario(int id) {
        
        return usuarioJpa.findUsuario(id);
    }

    public void editarUsuario(Usuario usu) { 
        
        try {
            usuarioJpa.edit(usu);
            
        } catch (Exception ex) {
            
            Logger.getLogger(ControladoraPersistencia.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }
    
    //====================== Odontologo ==================================

    public void crearOdonto(Odontologo odo) {
       
        odontoJpa.create(odo);
    }

    public List<Odontologo> traerListaOdontologos() {
        
        return odontoJpa.findOdontologoEntities();
    }

    public Odontologo traerOdonto(int id) {
        
        return odontoJpa.findOdontologo(id);
    }

    public void editarOdonto(Odontologo odo) {
        
        try {
            
            odontoJpa.edit(odo);
            
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    public void borrarOdonto(int idOdonto) {
        
        try {
            
            odontoJpa.destroy(idOdonto);
            
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    
    //====================== Paciente ==================================
    
    public void crearPaciente(Paciente pa) {
        
        pacienteJpa.create(pa);
    }

    public List<Paciente> traerListaPacientes() {
        
        return pacienteJpa.findPacienteEntities();
    }

    public Paciente traerPaciente(int id) {
        
        return pacienteJpa.findPaciente(id);
    }

    public void editarPaciente(Paciente paciente) {
        
        try {
            
            pacienteJpa.edit(paciente);
            
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    public void borrarPaciente(int id) {
        
        try {
            
            pacienteJpa.destroy(id);
            
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    public void crearTipoSangre(TipoSangre tipoSan) {
        
        tipoSanJpa.create(tipoSan);
    }

    public List<TipoSangre> traerListaTiposSangre() {
        
        return tipoSanJpa.findTipoSangreEntities();
    }

    public TipoSangre traerTipoSangre(int id) {
        
        return tipoSanJpa.findTipoSangre(id);
    }

    public void editarTipoSangre(TipoSangre tipoSangre) {
        
        try {
            
            tipoSanJpa.edit(tipoSangre);
            
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    public void borrarTipoSangre(int id) {
        
        try {
            
            tipoSanJpa.destroy(id);
            
        } catch (NonexistentEntityException ex) {
            
            Logger.getLogger(ControladoraPersistencia.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    public void crearTipoDocumento(TipoDocumento tipoDoc) {
        
        tipoDocJpa.create(tipoDoc);
    }
    
    public TipoDocumento traerTipoDocumento(int idTipoDoc) {
        
        return tipoDocJpa.findTipoDocumento(idTipoDoc);
    }

    public List<TipoDocumento> traerListaTipoDocumento() {
        
        return tipoDocJpa.findTipoDocumentoEntities();
    }

    public void editarTipoDocumento(TipoDocumento tipoDoc) {
        
        try {
            
            tipoDocJpa.edit(tipoDoc);
            
        } catch (Exception ex) {
            
            Logger.getLogger(ControladoraPersistencia.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    public void borrarTipoDocumento(int idTipoDoc) {
        
        try {
            
            tipoDocJpa.destroy(idTipoDoc);
            
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }
    
    
    //========================== Secretario =====================
    
    public void crearSecretario(Secretario se) {
        
        secreJpa.create(se);
    }
    
    public void editarSecretario(Secretario secretarioOriginal) {
        
        try {
            
            secreJpa.edit(secretarioOriginal);
            
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }
    
    public void borrarSecretario(int idSecretario) {
        
        try {
            
            secreJpa.destroy(idSecretario);
            
        } catch (NonexistentEntityException ex) {
            
            Logger.getLogger(ControladoraPersistencia.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    public List<Secretario> traerListaSecretarios() {
        
        return secreJpa.findSecretarioEntities();
    }

    public Secretario traerSecretario(int idSecretario) {
        
        return secreJpa.findSecretario(idSecretario);
    }

    //===================== Persona ========================================== 
    public Persona traerPersona(int id) {
        
        return personaJpa.findPersona(id);
    }

    public void editarPersona(Persona personaOriginal) {
        
        try {
            
            personaJpa.edit(personaOriginal);
            
        } catch (Exception ex) {
            
            Logger.getLogger(ControladoraPersistencia.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    

    

    
    
    
}
