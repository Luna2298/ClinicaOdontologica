package logica;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Rol implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idRol;
    private String rol;
    
    /*
    El nombre que use aqui 'tipoRol' debe ser el MISMO del atributo
    con el que llamo desde Usuario a ESTA CLASE, o sea 
    'private Rol tipoRol;'  , que es con quien yo quiero relacionarme*/
    @OneToMany(mappedBy="tipoRol")
    private List<Usuario> listaUsuarios;

    public Rol() {
    }

    public Rol(int idRol, String rol, List<Usuario> listaUsuarios) {
        this.idRol = idRol;
        this.rol = rol;
        this.listaUsuarios = listaUsuarios;
    }

    

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
    
    
}
