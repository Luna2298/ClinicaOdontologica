package logica;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class ObraSocial implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idObraSocial;
    private String obraSocial;
    
    /*
    El nombre que use aqui 'tipoDocumento' debe ser el MISMO del atributo
    con el que llamo desde Persona a ESTA CLASE, o sea 
    'private TipoDocumento tipoDocumento;'  , que es con quien yo quiero relacionarme*/
    @OneToMany(mappedBy="obraSocial")
    private List<Persona> listaPersonas;

    public ObraSocial() {
    }

    public ObraSocial(int idObraSocial, String obraSocial, List<Persona> listaPersonas) {
        this.idObraSocial = idObraSocial;
        this.obraSocial = obraSocial;
        this.listaPersonas = listaPersonas;
    }

    

    public int getIdObraSocial() {
        return idObraSocial;
    }

    public void setIdObraSocial(int idObraSocial) {
        this.idObraSocial = idObraSocial;
    }

    public String getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(String obraSocial) {
        this.obraSocial = obraSocial;
    }

    public List<Persona> getListaPersonas() {
        return listaPersonas;
    }

    public void setListaPersonas(List<Persona> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }
    
    
    
    
}
