/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Logic;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.beans.Asistente;
import com.example.beans.Evento;
import com.example.beans.Discoteca;
import com.example.persistence.ClassEntityManagerFactory;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.ForbiddenException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author joseluissacanamboy
 */
public class EventoLogica {

 
    /**
     * Name: getEvents Description: Endpoint que consulta todos los eventos en
     * el sistema Method: Get
     *
     * @param jwt
     * @return
     * @throws Exception
     * @throws BadRequestException
     * @throws ForbiddenException
     * @throws NotFoundException
     * @throws UnauthorizedException
     */
    @ApiMethod(name = "showAssistants")
    public List<Evento> getEvents(@Named("jwt") String jwt) throws Exception, BadRequestException, ForbiddenException, NotFoundException, UnauthorizedException {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();

        DecodedJWT dec = JWT.decode(jwt);
        int[] claims = new int[2];
        
      

        if (dec.getExpiresAt().compareTo(Calendar.getInstance().getTime()) < 0) {

            throw new ForbiddenException("Token has expired");
        }

        Claim claim1 = dec.getClaim("Id");

        int idAsistente = claim1.asInt();
        
        Asistente asistenteTmp = em.createNamedQuery("Asistente.findByIdAsistente", Asistente.class).setParameter("idAsistente", idAsistente).getSingleResult();
                
        if(asistenteTmp ==null){
            throw new UnauthorizedException("No puedes acceder a esta pagina");
        }
        em.getTransaction().begin();
        List<Evento> events = em.createNamedQuery("Evento.findAll", Evento.class).getResultList();
        em.getTransaction().commit();
        return events;
    }

    /**
     * Name: createUser Description: Endpoint que crea un nuevo usuario en el
     * sistema Method: Post
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
    @ApiMethod(httpMethod = ApiMethod.HttpMethod.POST, name = "createEvent", path = "createEvent/success")
    public Evento createEvent(Evento evento, @Named("idDisco") Integer idDisco, @Named("precioEvento") Integer precioEvento, @Named("jwt") String jwt) throws Exception, BadRequestException, ForbiddenException, NotFoundException, UnauthorizedException {

        int[] claims = AdministradorLogica.verificarJWT(jwt);

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();

        if (evento == null) {
            throw new BadRequestException("El evento es null");
        } else if (evento.getFechaInicio() == null) {
            throw new BadRequestException("La fecha de inicio es null");
        } else if (evento.getFechaFinal() == null) {
            throw new BadRequestException("La fecha final es null");
        } else if (idDisco == null) {
            throw new BadRequestException("El id del discoteca a la cual se le va a sociar el evento es null");
        } else if (evento.getMaxEntradas() < 0 || String.valueOf(evento.getMaxEntradas()).equalsIgnoreCase("")) {
            throw new BadRequestException("El maximo de entradas es null o es invalido");
        } else if (evento.getMaxReservas() < 0 || String.valueOf(evento.getMaxReservas()).equalsIgnoreCase("")) {
            throw new BadRequestException("El maximo de reservas es null o es invalido");
        } else if (evento.getNombre() == null || evento.getNombre().equalsIgnoreCase("")) {
            throw new BadRequestException("El nombre del evento es null");
        } else if (String.valueOf(precioEvento).equalsIgnoreCase("")) {
            throw new BadRequestException("El precio es null o es invalido");
        }

        Discoteca discoteca = em.createNamedQuery("Discoteca.findByIdDiscoteca", Discoteca.class).setParameter("idDiscoteca", idDisco).getSingleResult();
        if (discoteca.getIdDiscoteca() == claims[1]) {

            evento.setIdEvento(generarNumeroConsecuenteEvento());
            evento.setPrecio(BigDecimal.valueOf(precioEvento));
            evento.setDiscotecaidDiscoteca(discoteca);
            em.persist(evento);
            em.getTransaction().commit();
        } else {
            throw new UnauthorizedException("Admin is not allowed to create an event in another disco different from his");
        }

        return evento;
    }

    /**
     * Name: editEvent Description: Endpoint que edita una discoteca del sistema
     * Method: Put
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
    @ApiMethod(httpMethod = ApiMethod.HttpMethod.PUT, name = "editEvent", path = "editEvent")
    public Evento editEvent(@Named("idEvento") Integer idEvento, @Named("fechaInicio") Date fechaInicio, @Named("fechaFinal") Date fechaFinal, @Named("maxEntradas") Integer maxEntradas, @Named("maxReservas") Integer maxReservas, @Named("nombre") String nombre, @Named("precio") Integer precio, @Named("jwt") String jwt) throws BadRequestException, ForbiddenException, NotFoundException, UnauthorizedException, Exception {

        int[] claims = AdministradorLogica.verificarJWT(jwt);

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();

        if (fechaInicio == null) {
            throw new Exception("La fecha de inicio es null");
        } else if (idEvento == null) {
            throw new Exception("El id del evento es null");
        } else if (fechaFinal == null) {
            throw new Exception("La fecha final es null");
        } else if (maxEntradas < 0 || String.valueOf(maxEntradas).equalsIgnoreCase("")) {
            throw new Exception("El maximo de entradas es null o es invalido");
        } else if (maxReservas < 0 || String.valueOf(maxReservas).equalsIgnoreCase("")) {
            throw new Exception("El maximo de reservas es null o es invalido");
        } else if (nombre == null || nombre.equalsIgnoreCase("")) {
            throw new Exception("El nombre del evento es null");
        } else if (String.valueOf(precio).equalsIgnoreCase("")) {
            throw new Exception("El precio es null o es invalido");
        }

        Evento eventFound = em.createNamedQuery("Evento.findByIdEvento", Evento.class).setParameter("idEvento", idEvento).getSingleResult();

        if (eventFound.getDiscotecaidDiscoteca().getIdDiscoteca() == claims[1]) {

            eventFound.setFechaFinal(fechaFinal);
            eventFound.setFechaInicio(fechaInicio);
            eventFound.setMaxEntradas(maxEntradas);
            eventFound.setMaxReservas(maxReservas);
            eventFound.setNombre(nombre);
            BigDecimal bigDecimalPrecio = BigDecimal.valueOf(precio);
            eventFound.setPrecio(bigDecimalPrecio);

            em.persist(eventFound);
            em.getTransaction().commit();
            em.refresh(eventFound);
            return eventFound;
        } else {
            throw new UnauthorizedException("Admin is not allowed to modify an event in another disco different from his");
        }
    }

    /**
     *
     *
     * @param idEvento
     * @return Asistente borrado del sistema
     */
    /**
     * Name: deleteEvent Description: Endpoint que elimina un evento de una
     * discoteca del sistema Method: Delete
     *
     * @param idEvento
     * @param jwt
     * @return
     * @throws BadRequestException
     * @throws ForbiddenException
     * @throws NotFoundException
     * @throws UnauthorizedException
     */
    @ApiMethod(name = "deleteEvent", path = "deleteEvent/{idEvento}")
    public Evento deleteEvent(@Named("idEvento") Integer idEvento, @Named("jwt") String jwt) throws BadRequestException, ForbiddenException, NotFoundException, UnauthorizedException, Exception {

        int[] claims = AdministradorLogica.verificarJWT(jwt);

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();
        Evento eventFound = em.createNamedQuery("Evento.findByIdEvento", Evento.class).setParameter("idEvento", idEvento).getSingleResult();

        if (eventFound.getDiscotecaidDiscoteca().getIdDiscoteca() == claims[1]) {
            em.remove(eventFound);
            em.getTransaction().commit();
            return eventFound;
        } else {
            throw new UnauthorizedException("Admin is not allowed to delete an event in another disco different from his");
        }
    }

    /**
     * Name: findEvent Description: Endpoint que encuentra un evento del sistema
     * Method: Post
     *
     * @param idEvento
     * @return
     * @throws Exception
     */
    @ApiMethod(name = "findEvent", path = "findEvent")
    public Evento findEvent(@Named("idEvento") Integer idEvento) throws Exception {
        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();

        Evento assistantFound = em.createNamedQuery("Evento.findByIdEvento", Evento.class).setParameter("idEvento", idEvento).getSingleResult();
        if (assistantFound == null) {
            throw new Exception("No existe el evento con id: " + idEvento);
        }

        em.getTransaction().commit();

        return assistantFound;
    }

    private int generarNumeroConsecuenteEvento() {
        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();

        em.getTransaction().begin();
        int cantidad = 0;

        if (em.createQuery("SELECT MAX (even.idEvento) FROM Evento even").getSingleResult() == null) {
            cantidad = 1;
        } else {
            cantidad = (int) em.createQuery("SELECT MAX (even.idEvento) FROM Evento even").getSingleResult();
            cantidad = cantidad + 1;
        }
        em.getTransaction().commit();
        return cantidad;
    }
}
