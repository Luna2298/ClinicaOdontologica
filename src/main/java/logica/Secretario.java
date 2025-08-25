package logica;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Secretario extends Persona{
    
    /*private int idSecretario;*/
    private String sector;
    @OneToOne
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name="idEstadoLaboral")
    private EstadoLaboral estadoLaboralSecre;

    public Secretario() {
    }

    public Secretario(String sector, Usuario usuario, 
            EstadoLaboral estadoLaboralSecre, 
            int id, String nombre, String apellido, String dni, 
            String telefono, String direccion, Date fecha_nac, 
            TipoSangre tipoSangre, TipoDocumento tipoDocumento, 
            ObraSocial obraSocial) {
        
        super(id, nombre, apellido, dni, telefono, direccion, fecha_nac, 
                tipoSangre, tipoDocumento, obraSocial);
        
        this.sector = sector;
        this.usuario = usuario;
        this.estadoLaboralSecre = estadoLaboralSecre;
    }

    

    /*public int getIdSecretario() {
        return idSecretario;
    }

    public void setIdSecretario(int idSecretario) {
        this.idSecretario = idSecretario;
    }*/

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public EstadoLaboral getEstadoLaboralSecre() {
        return estadoLaboralSecre;
    }

    public void setEstadoLaboralSecre(EstadoLaboral estadoLaboralSecre) {
        this.estadoLaboralSecre = estadoLaboralSecre;
    }
    
    
}
