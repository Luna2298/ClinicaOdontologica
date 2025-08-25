package logica;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class EstadoLaboral implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idEstadoLaboral;
    private String estadoLaboral;
    
    @OneToMany(mappedBy="estadoLaboralOdonto")
    private List<Odontologo> listaOdontologos;
    
    @OneToMany(mappedBy="estadoLaboralSecre")
    private List<Secretario> listaSecretarios;

    public EstadoLaboral() {
    }

    public EstadoLaboral(int idEstadoLaboral, String estadoLaboral, 
            List<Odontologo> listaOdontologos, 
            List<Secretario> listaSecretarios) {
        
        this.idEstadoLaboral = idEstadoLaboral;
        this.estadoLaboral = estadoLaboral;
        this.listaOdontologos = listaOdontologos;
        this.listaSecretarios = listaSecretarios;
    }

    

    public int getIdEstadoLaboral() {
        return idEstadoLaboral;
    }

    public void setIdEstadoLaboral(int idEstadoLaboral) {
        this.idEstadoLaboral = idEstadoLaboral;
    }

    public String getEstadoLaboral() {
        return estadoLaboral;
    }

    public void setEstadoLaboral(String estadoLaboral) {
        this.estadoLaboral = estadoLaboral;
    }

    public List<Odontologo> getListaOdontologos() {
        return listaOdontologos;
    }

    public void setListaOdontologos(List<Odontologo> listaOdontologos) {
        this.listaOdontologos = listaOdontologos;
    }

    public List<Secretario> getListaSecretarios() {
        return listaSecretarios;
    }

    public void setListaSecretarios(List<Secretario> listaSecretarios) {
        this.listaSecretarios = listaSecretarios;
    }
    
    
}
