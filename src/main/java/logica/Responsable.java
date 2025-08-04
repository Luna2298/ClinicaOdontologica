package logica;

import java.util.Date;
import javax.persistence.Entity;

@Entity
public class Responsable extends Persona{
    
    /*private int idResponsable;*/
    private String tipoResponsable;

    public Responsable() {
    }

    public Responsable(String tipoResponsable, int id, String nombre, 
            String apellido, String dni, String telefono, String direccion, 
            Date fecha_nac, TipoSangre tipoSangre, TipoDocumento tipoDocumento) {
        
        super(id, nombre, apellido, dni, telefono, direccion, fecha_nac, 
              tipoSangre, tipoDocumento);
        
        this.tipoResponsable = tipoResponsable;
    }

    /*public int getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(int idResponsable) {
        this.idResponsable = idResponsable;
    }*/

    public String getTipoResponsable() {
        return tipoResponsable;
    }

    public void setTipoResponsable(String tipoResponsable) {
        this.tipoResponsable = tipoResponsable;
    }
    
    
    
}
