package logica;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class TipoDocumento implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idTipoDocumento;
    private String tipoDoc;
    
    /*
    El nombre que use aqui 'tipoDocumento' debe ser el MISMO del atributo
    con el que llamo desde Persona a ESTA CLASE, o sea 
    'private TipoDocumento tipoDocumento;'  , que es con quien yo quiero relacionarme*/
    @OneToMany(mappedBy="tipoDocumento")
    private List<Persona> listaPersonas;

    public TipoDocumento() {
    }

    public TipoDocumento(int idTipoDocumento, String tipoDoc, List<Persona> listaPersonas) {
        this.idTipoDocumento = idTipoDocumento;
        this.tipoDoc = tipoDoc;
        this.listaPersonas = listaPersonas;
    }

    public int getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(int idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public List<Persona> getListaPersonas() {
        return listaPersonas;
    }

    public void setListaPersonas(List<Persona> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }
    
    
    
}
