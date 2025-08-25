package logica;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import logica.Odontologo;
import logica.Secretario;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2025-08-24T19:19:16")
@StaticMetamodel(EstadoLaboral.class)
public class EstadoLaboral_ { 

    public static volatile SingularAttribute<EstadoLaboral, Integer> idEstadoLaboral;
    public static volatile ListAttribute<EstadoLaboral, Secretario> listaSecretarios;
    public static volatile ListAttribute<EstadoLaboral, Odontologo> listaOdontologos;
    public static volatile SingularAttribute<EstadoLaboral, String> estadoLaboral;

}