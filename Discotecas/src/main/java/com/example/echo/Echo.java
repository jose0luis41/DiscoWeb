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

import com.example.Logic.*;

import java.util.List;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.Named;
import java.util.Date;
import com.example.beans.*;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.ForbiddenException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.ServletException;

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

    private final AsistenteLogica assistantLogic;
    private final ReservaLogic reservaLogic;
    private final EntradaLogic entradaLogic;
    private final AdministradorLogica administratorLogic;
    private final DiscotecaLogic discotecaLogic;
    private final EventoLogica eventoLogica;
    private final AsistenteEventoLogica asistenteEventoLogic;
    private final PagoLogica pagoLogica;

    public Echo() {
        eventoLogica = new EventoLogica();
        discotecaLogic = new DiscotecaLogic();
        assistantLogic = new AsistenteLogica();
        administratorLogic = new AdministradorLogica();
        reservaLogic = new ReservaLogic();
        entradaLogic = new EntradaLogic();
        asistenteEventoLogic = new AsistenteEventoLogica();
        pagoLogica = new PagoLogica();
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
    //@author joseluissacanamboy
    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    //---------------------------------------ASISTENTES----------------------------------------------
    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    /**
     * Name: getAsistants Description: Delegado que consulta todos los asistente
     * en el sistema Method: Get
     *
     * @return Lista de usuarios en el sistema
     */
    public List<Asistente> getAsistants() {
        return assistantLogic.getAssistants();
    }

    /**
     * Name: createAssistant Description: Delegado que crea un nuevo asistente
     * en el sistema
     *
     * @param assistant
     * @return
     * @throws Exception
     */
    public Asistente createAssistant(Asistente assistant) throws Exception {

        return assistantLogic.createAssistant(assistant);
    }

    /**
     * Name: editAssistant Description: Delegado que edita un asistente del
     * sistema Method: Put
     *
     * @param cedula
     * @param assistantName
     * @param correo
     * @param telefono
     * @param fechaNacimiento
     * @param contrasena
     * @return
     * @throws Exception cuando un dato es null
     */
    public Asistente editAssistant(@Named("Id") String cedula, @Named("Name") String assistantName, @Named("Email") String correo, @Named("Telephone") String telefono, @Named("Birthday") Date fechaNacimiento, @Named("Password") String contrasena) throws Exception {

        return assistantLogic.editAssistant(cedula, assistantName, correo, telefono, fechaNacimiento, contrasena);
    }

    /**
     * Name: deleteAssistant Description: Delegado que elimina un asistente del
     * sistema Method: DELETE
     *
     * @param cedula
     * @return Asistente borrado del sistema
     */
    public Asistente deleteAssistant(@Named("cedula") String cedula) {

        return assistantLogic.deleteAssistant(cedula);
    }

    /**
     * Name: findAssistant Description: Delegado que busca un asistente del
     * sistema
     *
     * @param cedula
     * @return Asistente encontrado del sistema
     */
    public Asistente findAssistant(@Named("cedula") String cedula) throws Exception {
        return assistantLogic.findAssistant(cedula);
    }

    /**
     * Name: getLoginAsistente Description: Delegado que busca un asistente del
     * sistema por el correo
     *
     * @param correo
     * @return
     * @throws NotFoundException
     * @throws BadRequestException
     */
    public JWTE getLoginAsistente(@Named("correo") String correo) throws BadRequestException, NotFoundException, UnsupportedEncodingException {
        return assistantLogic.getLoginAsistente(correo);
    }

    public Asistente findAssistantByCorreo(@Named("correo") String correo) throws Exception {
        return assistantLogic.findAssistantByCorreo(correo);
    }

    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    //---------------------------------------ADMINISTRADOR-----------------------------------------
    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    /**
     * Name: getAdministrators Description: Delegado que consulta todos los
     * administradores en el sistema Method: Get
     *
     * @return Lista de usuarios en el sistema
     */
    public List<Adminstrador> getAdministrators() {
        return administratorLogic.getAdministrators();
    }

    /**
     * Name: createAdministrator Description: Delegado que crea un nuevo
     * administrador en el sistema
     *
     * @param adminstrador
     * @param idDiscoteca
     * @return Administador creado
     * @throws Exception
     */
    public Adminstrador createAdministrator(Adminstrador adminstrador, @Named("idDiscoteca") Integer idDiscoteca) throws Exception {
        return administratorLogic.createAdministrator(adminstrador, idDiscoteca);
    }

    /**
     * Name: editAdministrator Description: Delegado que edita un administrador
     * del sistema Method: PUT
     *
     * @param cedula
     * @param administadorName
     * @param correo
     * @param telefono
     * @param fechaNacimiento
     * @param contrasena
     * @return
     * @throws Exception cuando un dato es null
     */
    public Adminstrador editAdministrator(@Named("Id") String cedula, @Named("Name") String administadorName, @Named("Email") String correo, @Named("Telephone") String telefono, @Named("Birthday") Date fechaNacimiento, @Named("Password") String contrasena) throws Exception {
        return administratorLogic.editAdministrator(cedula, administadorName, correo, telefono, fechaNacimiento, contrasena);
    }

    /**
     * Name: deleteAdministrator Description: Delegado que elimina un
     * administrador del sistema Method: DELETE
     *
     * @param cedula
     * @return Administrador borrado del sistema
     */
    public Adminstrador deleteAdministrator(@Named("cedula") String cedula) {
        return administratorLogic.deleteAdministrator(cedula);
    }

    /**
     * Name: findAdministrator Description: Delegado que busca un administrador
     * del sistema Method: PUT
     *
     * @param cedula
     * @return Administrador encontrado del sistema
     */
    public Adminstrador findAdministrator(@Named("cedula") String cedula) throws Exception {
        return administratorLogic.findAdministrator(cedula);
    }

    public JWTE loginAdministrator(@Named("correo") String correo) throws Exception {
        return administratorLogic.getLoginAdministrator(correo);
    }

    public Adminstrador findAdministratorByCorreo(@Named("correo") String correo) throws Exception {
        return administratorLogic.findAdministratorByCorreo(correo);
    }

    public List<Evento> EventsByAdministrators(@Named("idDiscoteca") Integer idDiscoteca) {
        return administratorLogic.getEventsByAdministrators(idDiscoteca);
    }

    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    //---------------------------------------ENTRADA-----------------------------------------
    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    /**
     * Name: getTicekts Description: Delegado que consulta todas las entradas en
     * el sistema Method: GET
     *
     * @return Lista de entradas en el sistema
     */
    public List<Entrada> getTicekts() {
        return entradaLogic.getTicekts();
    }

    /**
     * Name: findTicekt Description: Delegado que crea un nuevo administrador en
     * el sistema Method: GET
     *
     * @param codigoQR
     * @return
     */
    public Entrada findTicekt(@Named("codigoQR") String codigoQR) {
        return entradaLogic.findTicekt(codigoQR);
    }

    /**
     * Name: createTicket Description: Delegado que crea un nuevo ticket en el
     * sistema Method: POST
     *
     * @param idAsistenteEvento
     * @return Entrada creada
     * 
     */
    public Entrada createTicket(@Named("Idasistente") Integer idAsistenteEvento){
        return entradaLogic.createTicket(idAsistenteEvento);
    }

    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    //---------------------------------------DISCOTECA---------------------------------------------
    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    /**
     * Name: getDiscos Description: Delegado que consulta todas las discotecas
     * en el sistema Method: Get
     *
     * @return Lista de discotecas en el sistema
     */
    public List<Discoteca> getDiscos() {
        return discotecaLogic.getDiscos();
    }

    /**
     * Name: findDisco Description: Delegado que consulta una discoteca en el
     * sistema con su nombre Method: GET
     *
     * @param nombre
     * @return Discoteca encontrada
     */
    public Discoteca findDisco(@Named("nombre") String nombre) {
        return discotecaLogic.findDisco(nombre);
    }

    /**
     * Name: createDisco Description: Delegado que crea una nueva discoteca en
     * el sistema Method: POST
     *
     * @param nombre
     * @return
     * @throws Exception
     */
    public Discoteca createDisco(@Named("Nombre") String nombre) throws Exception {
        return discotecaLogic.createDisco(nombre);
    }

    /**
     * Name: editDisco Description: Delegado que edita una discoteca del sistema
     * Method: PUT
     *
     * @param idDiscoteca
     * @param nombre
     * @param jwt
     * @return
     * @throws BadRequestException
     * @throws ForbiddenException
     * @throws NotFoundException
     * @throws UnauthorizedException
     * @throws Exception
     */
    public Discoteca editDisco(@Named("id") Integer idDiscoteca, @Named("nombre") String nombre, @Named("jwt") String jwt) throws BadRequestException, ForbiddenException, NotFoundException, UnauthorizedException, Exception {
        return discotecaLogic.editDisco(idDiscoteca, nombre, jwt);
    }

    /**
     *
     *
     * @param nombre
     * @return
     */
    /**
     * Name: deleteDisco Description: Delegado que elimina una discoteca del
     * sistema Method: DELETE
     *
     * @param nombre
     * @param jwt
     * @return
     * @throws BadRequestException
     * @throws ForbiddenException
     * @throws NotFoundException
     * @throws UnauthorizedException
     */
    public Discoteca deleteDisco(@Named("nombre") String nombre, @Named("jwt") String jwt) throws BadRequestException, ForbiddenException, NotFoundException, UnauthorizedException, Exception {
        return discotecaLogic.deleteDisco(nombre, jwt);
    }

    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    //---------------------------------------EVENTO------------------------------------------------
    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    /**
     * Name: getDiscos Description: Delegado que consulta todas las discotecas
     * en el sistema Method: Get
     *
     * @param jwt
     * @return
     * @throws Exception
     * @throws BadRequestException
     * @throws ForbiddenException
     * @throws NotFoundException
     * @throws UnauthorizedException
     */
    public List<Evento> getEvents(@Named("jwt") String jwt) throws Exception, BadRequestException, ForbiddenException, NotFoundException, UnauthorizedException {
        return eventoLogica.getEvents(jwt);
    }

    /**
     * Name: findEvento Description: Delegado que consulta un evento en el
     * sistema con su nombre Method: GET
     *
     * @param idEvento
     * @throws Exception
     * @return Evento encontrado
     */
    public Evento findEvento(@Named("idEvento") Integer idEvento) throws Exception {
        return eventoLogica.findEvent(idEvento);
    }

    /**
     * Name: createEvent Description: Delegado que crea un evento en una
     * discoteca del sistema Method: POST
     *
     * @param evento
     * @param idDisco
     * @param precioEvento
     * @param jwt
     * @return
     * @throws Exception
     * @throws BadRequestException
     * @throws ForbiddenException
     * @throws NotFoundException
     * @throws UnauthorizedException
     */
    public Evento createEvent(Evento evento, @Named("idDisco") Integer idDisco, @Named("precioEvento") Integer precioEvento, @Named("jwt") String jwt) throws Exception, BadRequestException, ForbiddenException, NotFoundException, UnauthorizedException {
        return eventoLogica.createEvent(evento, idDisco, precioEvento, jwt);
    }

    /**
     * Name: editEvent Description: Delegado que edita un evento del sistema
     * Method: PUT
     *
     * @param idEvento
     * @param fechaInicio
     * @param fechaFinal
     * @param maxEntradas
     * @param maxReservas
     * @param nombre
     * @param precio
     * @param jwt
     * @return
     * @throws BadRequestException
     * @throws ForbiddenException
     * @throws NotFoundException
     * @throws UnauthorizedException
     * @throws Exception
     */
    public Evento editEvent(@Named("idEvento") Integer idEvento, @Named("fechaInicio") Date fechaInicio, @Named("fechaFinal") Date fechaFinal, @Named("maxEntradas") Integer maxEntradas, @Named("maxReservas") Integer maxReservas, @Named("nombre") String nombre, @Named("precio") Integer precio, @Named("jwt") String jwt) throws BadRequestException, ForbiddenException, NotFoundException, UnauthorizedException, Exception {
        return eventoLogica.editEvent(idEvento, fechaInicio, fechaFinal, maxEntradas, maxReservas, nombre, precio, jwt);
    }

    /**
     * Name: deleteEvento Description: Delegado que elimina un evento del
     * sistema Method: DELETE
     *
     * @param idEvento
     * @param jwt
     * @return
     * @throws BadRequestException
     * @throws ForbiddenException
     * @throws NotFoundException
     * @throws UnauthorizedException
     */
    public Evento deleteEvent(@Named("idEvento") Integer idEvento, @Named("jwt") String jwt) throws BadRequestException, ForbiddenException, NotFoundException, UnauthorizedException, Exception {
        return eventoLogica.deleteEvent(idEvento, jwt);
    }

    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    //---------------------------------------ASISTENTE EVENTO--------------------------------------
    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    /**
     * Name: getAsistantsEvents Description: Delegado que consulta todos los
     * asistente a un evento en el sistema Method: Get
     *
     * @return Lista de asistentes a cualquier evento en el sistema
     */
    public List<AsistenteEvento> getAsistantsEvents() {
        return asistenteEventoLogic.getAssistantsEvents();
    }

    /**
     * Name: createAssistantEvent Description: Delegado que crea un nuevo
     * asistente en el sistema
     *
     * @param cedula
     * @param idEvento
     * @return
     * @throws BadRequestException
     */
    public AsistenteEvento createAssistantEvent(@Named("cedulaAsistente") String cedula, @Named("idEvento") Integer idEvento)throws BadRequestException {

        return asistenteEventoLogic.createAssistantEvent(cedula, idEvento);
    }

    /**
     * Name: deleteAssistantEvent Description: Delegado que elimina un asistente
     * a un evento del sistema Method: DELETE
     *
     * @param idAsistenteEvento
     * @return Asistente borrado del sistema
     */
    public AsistenteEvento deleteAssistantEvent(@Named("idAsistenteEvento") Integer idAsistenteEvento) {

        return asistenteEventoLogic.deleteAssistantEvent(idAsistenteEvento);
    }

    /**
     * Name: findAssistant Description: Delegado que busca un asistente del
     * sistema Method: Put
     *
     * @param idAsistenteEvento
     * @throws Exception
     * @return Asistente encontrado del sistema
     */
    public AsistenteEvento findAssistantEvent(@Named("idAsistenteEvento") Integer idAsistenteEvento) throws Exception {
        return asistenteEventoLogic.findAssistantEvent(idAsistenteEvento);
    }

    public void confirmarPago(@Named("state_pol") String state_pol, @Named("extra1") String extra1, @Named("email_buyer") String email_buyer) throws BadRequestException{
        pagoLogica.confirmarPago(state_pol, extra1, email_buyer);
    }

}
