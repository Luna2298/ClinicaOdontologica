<%@page import="logica.Paciente"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="logica.Odontologo"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<%@include file="components/header.jsp"%>
<%@include file="components/bodyprimeraparte.jsp"%>

<!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Page Heading -->
                    <h1 class="h3 mb-2 text-gray-800">Lista de Pacientes</h1>
                    <!--<p class="mb-4">DataTables is a third party plugin that is used to generate the demo table below.
                        For more information about DataTables, please visit the <a target="_blank"
                            href="https://datatables.net">official DataTables documentation</a>.</p>-->

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <!--<div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">DataTables Example</h6>
                        </div>-->
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
                                        <tr>
                                            <th>Id</th>
                                            <th>PACIENTE</th>
                                            <th>DNI</th>
                                            <th>TELEFONO</th>
                                            <th>FECHA NAC</th>
                                            <th>OBRA SOCIAL</th>
                                            <th>TIPO SANGRE</th>
                                            <th>DIRECCION</th>
                                            <th style="width: 210px">Acción</th>
                                        </tr>
                                    </thead>
                                    <tfoot>
                                        <tr>
                                            <th>Id</th>
                                            <th>Odontólogo</th>
                                            <th>DNI</th>
                                            <th>TELEFONO</th>
                                            <th>FECHA NAC</th>
                                            <th>OBRA SOCIAL</th>
                                            <th>TIPO SANGRE</th>
                                            <th>DIRECCION</th>
                                            <th style="width: 210px">Acción</th>
                                        </tr>
                                    </tfoot>
                                    <% /*
                                          Forma de la Profe:
                                          List<Paciente> listPacientes = 
                                          (List) request.getSession().getAttribute("listaPacientes");*/%>
                                            
                                    <%  /*Forma ChatGPT para hacer que traiga la 
                                        Lista Actualizada luego de cada Ediccion o Eliminacion */
                                        List<Paciente> listaPacientes = (List<Paciente>) request.getAttribute("listaPacientes"); %>
                                    <tbody>
                                        <%for( Paciente pa: listaPacientes ) {%>
                                        <tr>
                                            <td id="id_pa<%=pa.getId() %>"><%=pa.getId()%></td>
                                            <td><%=pa.getApellido() %>, <%=pa.getNombre() %></td>
                                            <td><%=pa.getDni() %></td>
                                            <td><%=pa.getTelefono() %></td>
                                            
                                            <!-- Con esto logro convertir de Date a Date SIMPLE
                                            y ultimo a String, la Fecha que llega desde la BD.
                                            Como llega y se muestra la fecha: Wed Feb 12 00:00:00 ART 1986,
                                            Pero con SimpleDateFormat, le doy un formato mas SIMPLE
                                            a la Fecha, y la paso de Date a String, 
                                            entonces me queda: 12/02/1986-->
                                            <% SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); %>
                                            <td><%=sdf.format(pa.getFecha_nac()) %></td>
                                            <td><%=pa.getTieneObraSocial() %></td>
                                            <td><%=pa.getTipoSangre().getTipo_Sangre() %></td>
                                            <td><%=pa.getDireccion()%></td>
                                            <td style="display: flex; gap: 10px;">
                                                <div style="flex: 1;">
                                                    <button type="button" class="btn btn-danger btn-sm btn-block"
                                                            onclick="eliminarPaciente(<%=pa.getId() %>)">
                                                        <i class="fas fa-trash-alt"></i> Eliminar
                                                    </button>
                                                </div>
                                                <div style="flex: 1;">
                                                    <form action="SvPaciente" method="GET">
                                                        <input type="hidden" name="id" value="<%=pa.getId() %>">
                                                        <button type="submit" name="accion" value="buscar"
                                                                class="btn btn-info btn-sm btn-block">
                                                            <i class="fas fa-pencil-alt"></i> Editar
                                                        </button>
                                                    </form>
                                                </div>
                                                <div style="flex: 1;">
                                                    <form action="SvPaciente" method="GET">
                                                        <input type="hidden" name="id" value="<%=pa.getId() %>">
                                                        <button type="submit" name="accion" value="info"
                                                                class="btn btn-info btn-sm btn-block">
                                                            <!--<i class="fa-solid fa-info"></i>-->
                                                            <i class="fa-solid fa-circle-info"></i>Info
                                                        </button>
                                                    </form>
                                                </div>
                                            </td>
                                        </tr> 
                                        <%}%>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                </div>
                <!-- /.container-fluid -->

            
                
                
                
<script>
    /*
    El nombre de la Funcion DEBE COINCIDIR con el nombre que esta en el 'onclick'
    onclick="eliminarOdonto(//odo.getId() )"
    Ya que de lo contrario, la funcion no tomara el valor del id del registro*/
function eliminarPaciente(idPa) {
    if (confirm("¿Estás seguro de que querés eliminar este paciente?")) {
        fetch("SvPaciente?idPaciente=" + idPa, {
            method: "DELETE"
        })
        .then(res => {
            if (res.ok) {
                Swal.fire({
                    //Aca es donde arma y muestra el mensaje
                   icon: "success",
                   title: "Éxito",
                   text: "Paciente eliminado correctamente",
                   confirmButtonColor: "#3085d6",
                   confirmButtonText: "Aceptar"
                }).then(() => {
                //Luego de mostrar el Mensaje
                //Redireccionamos al Servlet, por default ejecuta el .doGet(), 
                //haciendo que este actualice la Lista de Usuario, 
                //y termina redireccionando a mostrar la Lista.
                    window.location.href = "SvPaciente"; 
                });
            }else {
                Swal.fire({
                    icon: "error",
                    title: "Error",
                    text: "No se pudo eliminar el paciente",
                    confirmButtonColor: "#d33",
                    confirmButtonText: "Cerrar"
                });
            }
        });
    }
}
</script>                
                
                
<%@include file="components/bodyfinal.jsp"%>


