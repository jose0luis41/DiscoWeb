/*
 * Copyright (c) 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not  use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.example.echo;

import com.example.Logic.EntradaLogic;
import com.example.Logic.UsuarioLogica;
import com.example.Logic.ReporteLogic;
import com.example.Logic.ReservaLogic;

import java.util.List;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.Named;
import java.util.Date;
import com.example.beans.*;
/**
 * The Echo API which Endpoints will be exposing.
 */
// [START echo_api_annotation]
@Api(
        name = "echo",
        version = "v1"
// [END_EXCLUDE]
)
// [END echo_api_annotation]

public class Echo {

    private UsuarioLogica usuarioLogica;
    private ReporteLogic reporteLogic;
    private ReservaLogic reservaLogic;
    private EntradaLogic entradaLogic;

    public Echo() {
        usuarioLogica = new UsuarioLogica();
        reporteLogic = new ReporteLogic();
        reservaLogic = new ReservaLogic();
        entradaLogic = new EntradaLogic();
    }

    /**
     * Echoes the received message back. If n is a non-negative integer, the
     * message is copied that many times in the returned message.
     *
     * Note that name is specified and will override the default name of "{class
     * name}.{method name}". For example, the default is "echo.echo".
     *
     * Note that httpMethod is not specified. This will default to a reasonable
     * HTTP method depending on the API method name. In this case, the HTTP
     * method will default to POST.
     */
    // [START echo_method]
    //---------------------------------------USUARIOS----------------------------------------------//
    //@author joseluissacanamboy
    /**
     * Name: darTodosUsuarios Description: Delegado que consulta todos los
     * usuarios en el sistema Method: Get
     *
     * @return Lista de usuarios en el sistema
     */
    public List<Usuario> darTodosUsuarios() {
        return usuarioLogica.getAllUsers();
    }

    /**
     * Name: createUser Description: Delegado que crea un nuevo usuario en el
     * sistema
     *
     * @param usuario
     * @param Rol
     * @return Usuario nuevo en el sistema
     * @throws Exception cuando la cedula o el correo ya existan
     */
    public Usuario createUser(Usuario usuario, @Named("Rol") int Rol) throws Exception {

        return usuarioLogica.createUser(usuario, Rol);
    }

    /**
     * Name: editUser Description: Delegado que edita un usuario del sistema
     * Method: Put
     *
     * @param cedula
     * @param rol
     * @param userName
     * @param correo
     * @param telefono
     * @param fechaNac
     * @param contrasena
     * @return Usuario modificado
     * @throws Exception cuando el rol ingresado no existe
     */
    public Usuario editUser(@Named("Id") String cedula, @Named("Rol") int rol, @Named("Name") String userName, @Named("Email") String correo, @Named("Telephone") String telefono, @Named("Birthday") Date fechaNac, @Named("Password") String contrasena) throws Exception {

        return usuarioLogica.editUser(cedula, rol, userName, correo, telefono, fechaNac, contrasena);
    }

    /**
     * Name: deleteUser Description: Delegado que elimina un usuario del sistema
     * Method: Put
     *
     * @param cedula
     * @return Usuario borrado del sistema
     */
    public Usuario deleteUser(@Named("cedula") String cedula) {

        return usuarioLogica.deleteUser(cedula);
    }

    /**
     * Name: findUser Description: Delegado que busca un usuario del sistema
     * Method: Put
     *
     * @param cedula
     * @return Usuario encontrado del sistema
     */
    public Usuario findUser(@Named("cedula") String cedula) throws Exception {
        return usuarioLogica.findUser(cedula);
    }

    //---------------------------------------REPORTES----------------------------------------------//
    //@author dylantorres
    /**
     * Name: getAllReports Description: Delegado que consulta todos los reportes
     * en el sistema Method: Get
     *
     * @return Lista de reportes en el sistema
     */
    public List<Reporte> getAllReports() {

        return reporteLogic.getAllReports();
    }

    /**
     * Name: createReporte Description: Delegado que crea un nuevo reporte en el
     * sistema
     *
     * @param reporte
     * @param tiporeporte
     * @param cedulaUsu
     * @return Usuario nuevo en el sistema
     * @throws Exception cuando la cedula o el correo ya existan
     */
    public Reporte createReporte(Reporte reporte, @Named("TipoReporte") int TipoReporte, @Named("cedula") String cedulaUsu) throws Exception {

        return reporteLogic.createReport(reporte, TipoReporte, cedulaUsu);
    }

    /**
     * Name: getReportsUser Description: Delegado que busca todod los reportes
     * de un usuario dado Method: Put
     *
     * @param cedula
     * @return Reporte borrado del sistema
     */
    public List<Reporte> getReportsUser(@Named("cedula") String cedula) throws Exception {
        return reporteLogic.findReportsUser(cedula);
    }

    /**
     * Name: deleteReport Description: Delegado que elimina un reporte del
     * sistema Method: Put
     *
     * @param id
     * @return Reporte borrado del sistema
     */
    public Reporte deleteReport(@Named("id") int id) {

        return reporteLogic.deleteReport(id);
    }

    /**
     * Name: findReporte Description: Delegado que busca un reporte del sistema
     * Method: Put
     *
     * @param id
     * @return Usuario encontrado del sistema
     */
    public Reporte findReporte(@Named("id") int id) throws Exception {
        return reporteLogic.findReport(id);
    }

    /**
     * Name: getAllReservations Description: Delegado que consulta todas las
     * reservas en el sistema Method: Get
     *
     * @return Lista de usuarios en el sistema
     */
    public List<Reserva> getAllReservations() {
        return reservaLogic.getAllReservations();
    }

    /**
     * Name: createReservation Description: Delegado que crea una 
     * reserva en el sistema Method: Get
     *
     * @return Reserva creada
     */
    public Reserva createReservation(Reserva reserva, @Named("cedulaCliente") String cedula) throws Exception {
        return reservaLogic.createReservation(reserva, cedula);
    }

    /**
     * Name: deleteReservation Description: Delegado que borra una
     * reserva en el sistema Method: Get
     *
     * @return Reserva eliminada
     */
    public Reserva deleteReservation(@Named("id") int id) {

        return reservaLogic.deleteReservation(id);
    }

    /**
     * Name: findReservation Description: Delegado que consulta una 
     * reserva en el sistema Method: Get
     *
     * @return Reserva consultada
     */
    public Reserva findReservation(@Named("id") int id) throws Exception {

        return reservaLogic.findReservation(id);
    }
    
      /**
     * Name: getAllTicekts Description: Delegado que consulta todas las
     * entradas en el sistema Method: Get
     *
     * @return Lista de usuarios en el sistema
     */
    public List<Entrada> getAllTicekts() {
        return entradaLogic.getAllTicekts();
    }
    
    public Entrada createTicket(@Named("cedula") String cedulaUsu) throws Exception {

        return entradaLogic.createTicket(cedulaUsu);
    }
    
     public Entrada getTicekt(@Named("codigoQR") String codigoQR) {

        return entradaLogic.getTicekt(codigoQR);
    }
}
