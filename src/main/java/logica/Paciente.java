package logica;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Paciente extends Persona implements Serializable{
    
    /*private int idPaciente;*/
    private boolean tieneObraSocial;
    //private String tipoSangre;
    @OneToOne
    private Responsable responsable;
    
    @OneToMany(mappedBy="paciente")
    private List<Turno> listaTurnos;

    public Paciente() {
    }

    public Paciente(boolean tieneObraSocial, Responsable responsable, 
            List<Turno> listaTurnos, int id, String nombre, String apellido, 
            String dni, String telefono, String direccion, Date fecha_nac, 
            TipoSangre tipoSangre, TipoDocumento tipoDocumento) {
        
        super(id, nombre, apellido, dni, telefono, direccion, fecha_nac, 
              tipoSangre, tipoDocumento);
        
        this.tieneObraSocial = tieneObraSocial;
        this.responsable = responsable;
        this.listaTurnos = listaTurnos;
    }

    /*public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }*/

    public boolean getTieneObraSocial() {
        return tieneObraSocial;
    }

    public void setTieneObraSocial(boolean tieneObraSocial) {
        this.tieneObraSocial = tieneObraSocial;
    }

    /*public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }*/

    public Responsable getResponsable() {
        return responsable;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }

    public List<Turno> getListaTurnos() {
        return listaTurnos;
    }

    public void setListaTurnos(List<Turno> listaTurnos) {
        this.listaTurnos = listaTurnos;
    }
    
    
    
}
