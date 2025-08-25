package logica;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Odontologo extends Persona implements Serializable{
    
    /*private int idOdontologo;*/
    private String especialidad;
    @OneToMany(mappedBy="odonto")
    private List<Turno> listaTurnos;
    @OneToOne
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name="idEstadoLaboral")
    private EstadoLaboral estadoLaboralOdonto;
    
    @OneToOne
    private HorarioTrabajo horarioTrabajo;

    public Odontologo() {
    }

    public Odontologo(String especialidad, List<Turno> listaTurnos, 
            Usuario usuario, EstadoLaboral estadoLaboralOdonto, 
            HorarioTrabajo horarioTrabajo, 
            int id, String nombre, String apellido, 
            String dni, String telefono, String direccion, 
            Date fecha_nac, TipoSangre tipoSangre, 
            TipoDocumento tipoDocumento, ObraSocial obraSocial) {
        
        super(id, nombre, apellido, dni, telefono, direccion, fecha_nac, 
                tipoSangre, tipoDocumento, obraSocial);
        this.especialidad = especialidad;
        this.listaTurnos = listaTurnos;
        this.usuario = usuario;
        this.estadoLaboralOdonto = estadoLaboralOdonto;
        this.horarioTrabajo = horarioTrabajo;
    }

    

    /*public int getIdOdontologo() {
        return idOdontologo;
    }

    public void setIdOdontologo(int idOdontologo) {
        this.idOdontologo = idOdontologo;
    }*/

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public List<Turno> getListaTurnos() {
        return listaTurnos;
    }

    public void setListaTurnos(List<Turno> listaTurnos) {
        this.listaTurnos = listaTurnos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public HorarioTrabajo getHorarioTrabajo() {
        return horarioTrabajo;
    }

    public void setHorarioTrabajo(HorarioTrabajo horarioTrabajo) {
        this.horarioTrabajo = horarioTrabajo;
    }

    public EstadoLaboral getEstadoLaboralOdonto() {
        return estadoLaboralOdonto;
    }

    public void setEstadoLaboralOdonto(EstadoLaboral estadoLaboralOdonto) {
        this.estadoLaboralOdonto = estadoLaboralOdonto;
    }
    
    
}
