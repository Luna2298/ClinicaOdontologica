<%@page import="java.text.SimpleDateFormat"%>
<%@page import="logica.Secretario"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<%@include file="components/header.jsp"%>
<%@include file="components/bodyprimeraparte.jsp"%>

<!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Page Heading -->
                    <h1 class="h3 mb-2 text-gray-800">Lista de Secretarios</h1>
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
                                            <th>SECRETARIO</th>
                                            <th>DNI</th>
                                            <th>TELEFONO</th>
                                            <th>FECHA NAC</th>
                                            <th>SECTOR</th>
                                            <th>TIPO SANGRE</th>
                                            <th>DIRECCION</th>
                                            <th style="width: 210px">Acción</th>
                                        </tr>
                                    </thead>
                                    <tfoot>
                                        <tr>
                                            <th>Id</th>
                                            <th>SECRETARIO</th>
                                            <th>DNI</th>
                                            <th>TELEFONO</th>
                                            <th>FECHA NAC</th>
                                            <th>SECTOR</th>
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
                                        List<Secretario> listaSecretarios = (List<Secretario>) request.getAttribute("listaSecretarios"); %>
                                    <tbody>
                                        <%for( Secretario se: listaSecretarios ) {%>
                                        <tr>
                                            <td id="id_pa<%=se.getId() %>"><%=se.getId()%></td>
                                            <td><%=se.getApellido() %>, <%=se.getNombre() %></td>
                                            <td><%=se.getDni() %></td>
                                            <td><%=se.getTelefono() %></td>
                                            
                                            <!-- Con esto logro convertir de Date a Date SIMPLE
                                            y ultimo a String, la Fecha que llega desde la BD.
                                            Como llega y se muestra la fecha: Wed Feb 12 00:00:00 ART 1986,
                                            Pero con SimpleDateFormat, le doy un formato mas SIMPLE
                                            a la Fecha, y la paso de Date a String, 
                                            entonces me queda: 12/02/1986-->
                                            <% SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); %>
                                            <td><%=sdf.format(se.getFecha_nac()) %></td>
                                            <td><%=se.getSector()%></td>
                                            <td><%=se.getTipoSangre().getTipo_Sangre() %></td>
                                            <td><%=se.getDireccion()%></td>
                                            <td style="display: flex; gap: 10px;">
                                                <div style="flex: 1;">
                                                    <button type="button" class="btn btn-danger btn-sm btn-block"
                                                            onclick="eliminarSecretario(<%=se.getId() %>)">
                                                        <i class="fas fa-trash-alt"></i> Eliminar
                                                    </button>
                                                </div>
                                                <div style="flex: 1;">
                                                    <form action="SvEmpleado" method="GET">
                                                        <input type="hidden" name="id" value="<%=se.getId() %>">
                                                        <button type="submit" name="accion" value="buscarSecre"
                                                                class="btn btn-info btn-sm btn-block">
                                                            <i class="fas fa-pencil-alt"></i> Editar
                                                        </button>
                                                    </form>
                                                </div>
                                                <div style="flex: 1;">
                                                    <form action="SvEmpleado" method="GET">
                                                        <input type="hidden" name="id" value="<%=se.getId() %>">
                                                        <button type="submit" name="accion" value="infoSecre"
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
function eliminarSecretario(idSe) {
    if (confirm("¿Estás seguro de que querés eliminar este secretario?")) {
        fetch("SvSecretario?idSecretario=" + idSe, {
            method: "DELETE"
        })
        .then(res => {
            if (res.ok) {
                Swal.fire({
                    //Aca es donde arma y muestra el mensaje
                   icon: "success",
                   title: "Éxito",
                   text: "Secretario eliminado correctamente",
                   confirmButtonColor: "#3085d6",
                   confirmButtonText: "Aceptar"
                }).then(() => {
                //Luego de mostrar el Mensaje
                //Redireccionamos al Servlet, por default ejecuta el .doGet(), 
                //haciendo que este actualice la Lista de Usuario, 
                //y termina redireccionando a mostrar la Lista.
                    window.location.href = "SvSecretario?accion=listarSecre"; 
                });
            }else {
                Swal.fire({
                    icon: "error",
                    title: "Error",
                    text: "No se pudo eliminar el secretario",
                    confirmButtonColor: "#d33",
                    confirmButtonText: "Cerrar"
                });
            }
        });
    }
}
</script>                
                
                
<%@include file="components/bodyfinal.jsp"%>



