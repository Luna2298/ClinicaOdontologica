package logica;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import persistencia.ControladoraPersistencia;


public class ControladoraLogica {
    
    ControladoraPersistencia controlPersis = new ControladoraPersistencia();

    public void crearUsuario(String nombreUsuario, String contra, String rol) {
        
        Usuario usu = new Usuario();
        usu.setNombreUsuario(nombreUsuario);
        usu.setContrasenia(contra);
        usu.setRol(rol);
        
        controlPersis.crearUsuario(usu);
    }

    public List<Usuario> getListUsers() {
        
        return controlPersis.getListUsers();
    }

    public void borrarUsuario(int id) {
 
        controlPersis.borrarUsuario(id);
    }

    public Usuario traerUsuario(int id) {

        return controlPersis.traerUsuario(id);
    }

    public void editarUsuario(Usuario usu) {
        
        controlPersis.editarUsuario(usu);
    }

    public boolean validarLogin(String usuario, String contra) {
    
        boolean validado = false;
        
        List<Usuario> listaUsuarios = new ArrayList<Usuario>();
        listaUsuarios = this.getListUsers();
        
        for ( Usuario usu:listaUsuarios ) {
            
            if ( usu.getNombreUsuario().equals(usuario) ) {
                
                if ( usu.getContrasenia().equals(contra) ) {
                    
                    validado = true;
                }
            }
        }
        
        return validado;
    }
    
    //====================== Odontologo ==================================

    public void crearOdonto(String nombre, String apellido, String dni, 
            String telefono, String direccion, String fechaNac, 
            String especialidad, TipoSangre tipoSangre, TipoDocumento tipoDoc,
            Usuario usuario) {
      
        Odontologo odo = new Odontologo();
        odo.setNombre(nombre);
        odo.setApellido(apellido);
        odo.setDni(dni);
        odo.setTelefono(telefono);
        odo.setDireccion(direccion);
        
        //Paso de String a Date la Fecha de Nacimiento
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date fechaConvertida=null;

        try {
            Date parsed =  dateFormat.parse(fechaNac);
            fechaConvertida = new java.sql.Date(parsed.getTime());
        } catch(Exception e) {
            System.out.println("Error occurred"+ e.getMessage());
        }
        
        odo.setFecha_nac(fechaConvertida);
        odo.setEspecialidad(especialidad);
        
        odo.setTipoSangre(tipoSangre);
        odo.setTipoDocumento(tipoDoc);
        odo.setUsuario(usuario);
        
        
        controlPersis.crearOdonto(odo);
    }

    public List<Odontologo> traerListaOdontologos() {
        
        return controlPersis.traerListaOdontologos();
    }

    public Odontologo traerOdonto(int id) {
        
        return controlPersis.traerOdonto(id);
    }

    public void editarOdonto(Odontologo odo, String nombre, String apellido, 
            String dni, String telefono, String direccion, 
            Date fechaFormateada, TipoDocumento tipoDoc, TipoSangre tipo_Sangre, 
            Usuario usuario, String especialidad) {
        
        odo.setNombre(nombre);
        odo.setApellido(apellido);
        odo.setDni(dni);
        odo.setTelefono(telefono);
        odo.setDireccion(direccion);
        
        odo.setFecha_nac(fechaFormateada);
        odo.setEspecialidad(especialidad);
        
        odo.setTipoSangre(tipo_Sangre);
        odo.setTipoDocumento(tipoDoc);
        odo.setUsuario(usuario);
        
        controlPersis.editarOdonto(odo);
    }

    public void borrarOdonto(int idOdonto) {
        
        controlPersis.borrarOdonto(idOdonto);
    }
    
    //====================== Paciente ==================================

    public void crearPaciente(String nombre, String apellido, String dni, 
            String telefono, String direccion, String fechaNac, 
            boolean tieneObraSocial, TipoSangre tipoSangre, TipoDocumento tipoDoc) {
        
        Paciente pa = new Paciente();
        pa.setNombre(nombre);
        pa.setApellido(apellido);
        pa.setDni(dni);
        pa.setTelefono(telefono);
        pa.setDireccion(direccion);
        pa.setTieneObraSocial(tieneObraSocial);
        pa.setTipoSangre(tipoSangre);
        pa.setTipoDocumento(tipoDoc);
        
        
        //Paso de String a Date la Fecha de Nacimiento
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date fechaConvertida=null;

        try {
            Date parsed =  dateFormat.parse(fechaNac);
            fechaConvertida = new java.sql.Date(parsed.getTime());
        } catch(Exception e) {
            System.out.println("Error occurred"+ e.getMessage());
        }
        
        pa.setFecha_nac(fechaConvertida);
        
        
        controlPersis.crearPaciente(pa);
    }

    public List<Paciente> traerListaPacientes() {
        
        return controlPersis.traerListaPacientes();
    }

    public Paciente traerPaciente(int id) {
        
        return controlPersis.traerPaciente(id);
    }

    public void editarPaciente(Paciente paciente) {
        
        controlPersis.editarPaciente(paciente);
    }

    public void borrarPaciente(int id) {
        controlPersis.borrarPaciente(id);
    }
    
    
    /*======================= TipoSangre ====================*/
    public void crearTipoSangre(String tipoSangre) {
        
        TipoSangre tipoSan = new TipoSangre();
        tipoSan.setTipo_Sangre(tipoSangre);
        
        controlPersis.crearTipoSangre(tipoSan);
    }
    
    public List<TipoSangre> traerListaTiposSangre() {
        
        return controlPersis.traerListaTiposSangre();
    }
    
    public TipoSangre traerTipoSangre(int id) {
        
        return controlPersis.traerTipoSangre(id);
    }
    
    public void editarTipoSangre(TipoSangre tipoSangre) {
        
        controlPersis.editarTipoSangre(tipoSangre);
    }
    
    public void borrarTipoSangre(int id) {
        
        controlPersis.borrarTipoSangre(id);
    }
    
    
    /*======================= TipoDocumento ====================*/
    public void crearTipoDocumento(String tipoDocumento) {
        
        TipoDocumento tipoDoc = new TipoDocumento();
        tipoDoc.setTipoDoc(tipoDocumento);
        
        controlPersis.crearTipoDocumento(tipoDoc);
    }
    
    public TipoDocumento traerTipoDocumento(int idTipoDoc) {
        
        return controlPersis.traerTipoDocumento(idTipoDoc);
    }
    
    public List<TipoDocumento> traerListaTipoDocumento() {
    
        return controlPersis.traerListaTipoDocumento();
    }
    
    public void editarTipoDocumento(TipoDocumento tipoDoc) {
    
        controlPersis.editarTipoDocumento(tipoDoc);
    }
    
    public void borrarTipoDocumento(int idTipoDoc) {
        
        controlPersis.borrarTipoDocumento(idTipoDoc);
    }

    
    //====================== Secretario =================================
    
    public void crearSecretario(String nombre, String apellido, String dni, 
            String telefono, String direccion, String fechaNac, 
            TipoSangre tipoSangre, TipoDocumento tipoDoc, String sector,
            Usuario usuario) {
        
        
        Secretario se = new Secretario();
        se.setNombre(nombre);
        se.setApellido(apellido);
        se.setDni(dni);
        se.setTelefono(telefono);
        se.setDireccion(direccion);
        se.setTipoSangre(tipoSangre);
        se.setTipoDocumento(tipoDoc);
        se.setSector(sector);
        se.setUsuario(usuario);
        
        
        //Paso de String a Date la Fecha de Nacimiento
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date fechaConvertida=null;

        try {
            Date parsed =  dateFormat.parse(fechaNac);
            fechaConvertida = new java.sql.Date(parsed.getTime());
        } catch(Exception e) {
            System.out.println("Error occurred"+ e.getMessage());
        }
        
        se.setFecha_nac(fechaConvertida);
        
        
        controlPersis.crearSecretario(se);
        
    }
    
    
    /*public void editarSecretario(Secretario secretarioOriginal) {
        
        controlPersis.editarSecretario(secretarioOriginal);
    }*/
    
    public void editarSecretario(Secretario se, String nombre, String apellido, 
            String dni, String telefono, String direccion, Date fechaFormateada,
            TipoDocumento tipoDoc, TipoSangre tipo_Sangre, Usuario usuario, 
            String sector) {
        
        se.setNombre(nombre);
        se.setApellido(apellido);
        se.setDni(dni);
        se.setTelefono(telefono);
        se.setDireccion(direccion);
        se.setTipoSangre(tipo_Sangre);
        se.setTipoDocumento(tipoDoc);
        se.setSector(sector);
        se.setUsuario(usuario);
        se.setFecha_nac(fechaFormateada);
        
        controlPersis.editarSecretario(se);
        
    }
    
    public void borrarSecretario(int idSecretario) {
        
        controlPersis.borrarSecretario(idSecretario);
    }
    
    public List<Secretario> traerListaSecretarios() {
    
        return controlPersis.traerListaSecretarios();
    }

    public Secretario traerSecretario(int idSecretario) {
        
        return controlPersis.traerSecretario(idSecretario);
    }

    public Persona traerPersona(int id) {
        
        return controlPersis.traerPersona(id);
    }
    
    //==================== Persona =============================================

    public void editarPersona(Persona personaOriginal) {
        
        controlPersis.editarPersona(personaOriginal);
    }

    

    

    

    

    

    
}
