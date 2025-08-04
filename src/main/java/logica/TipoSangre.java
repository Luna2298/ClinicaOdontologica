package logica;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class TipoSangre implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idTipoSangre;
    private String tipo_Sangre;
    
    /*
    El nombre que use aqui 'tipoSangre' debe ser el MISMO del atributo
    con el que llamo desde Persona a ESTA CLASE, o sea 
    'private TipoSangre tipoSangre;'  , que es con quien yo quiero relacionarme*/
    @OneToMany(mappedBy="tipoSangre")
    private List<Persona> listaPersonas;

    public TipoSangre() {
    }

    public TipoSangre(int idTipoSangre, String tipo_Sangre, List<Persona> listaPersonas) {
        this.idTipoSangre = idTipoSangre;
        this.tipo_Sangre = tipo_Sangre;
        this.listaPersonas = listaPersonas;
    }

    public int getIdTipoSangre() {
        return idTipoSangre;
    }

    public void setIdTipoSangre(int idTipoSangre) {
        this.idTipoSangre = idTipoSangre;
    }

    public String getTipo_Sangre() {
        return tipo_Sangre;
    }

    public void setTipo_Sangre(String tipo_Sangre) {
        this.tipo_Sangre = tipo_Sangre;
    }

    public List<Persona> getListaPersonas() {
        return listaPersonas;
    }

    public void setListaPersonas(List<Persona> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }
    
    
}
