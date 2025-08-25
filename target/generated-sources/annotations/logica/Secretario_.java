package logica;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import logica.EstadoLaboral;
import logica.Usuario;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2025-08-24T19:19:16")
@StaticMetamodel(Secretario.class)
public class Secretario_ extends Persona_ {

    public static volatile SingularAttribute<Secretario, EstadoLaboral> estadoLaboralSecre;
    public static volatile SingularAttribute<Secretario, Usuario> usuario;
    public static volatile SingularAttribute<Secretario, String> sector;

}