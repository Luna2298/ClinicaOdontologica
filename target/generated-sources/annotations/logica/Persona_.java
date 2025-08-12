package logica;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import logica.TipoDocumento;
import logica.TipoSangre;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2025-08-10T13:07:00")
@StaticMetamodel(Persona.class)
public class Persona_ { 

    public static volatile SingularAttribute<Persona, Date> fecha_nac;
    public static volatile SingularAttribute<Persona, TipoDocumento> tipoDocumento;
    public static volatile SingularAttribute<Persona, TipoSangre> tipoSangre;
    public static volatile SingularAttribute<Persona, String> apellido;
    public static volatile SingularAttribute<Persona, String> direccion;
    public static volatile SingularAttribute<Persona, Integer> id;
    public static volatile SingularAttribute<Persona, String> telefono;
    public static volatile SingularAttribute<Persona, String> nombre;
    public static volatile SingularAttribute<Persona, String> dni;

}