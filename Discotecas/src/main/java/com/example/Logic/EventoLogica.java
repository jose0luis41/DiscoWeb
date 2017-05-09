/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Logic;

import com.example.beans.Evento;
import com.example.beans.Discoteca;
import com.example.persistence.ClassEntityManagerFactory;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import java.math.BigDecimal;
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
     * @return Lista de eventos en el sistema
     */
    @ApiMethod(name = "showAssistants")
    public List<Evento> getEvents() {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
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
     * @param idDiscoteca
     * @param precio
     * @return Asistente que se ha creado
     * @throws Exception cuando los parametros obligatorios no son ingresados
     */
    @ApiMethod(httpMethod = ApiMethod.HttpMethod.POST, name = "createEvent", path = "createEvent/success")
    public Evento createEvent(Evento evento, @Named("idDiscoteca")Integer idDiscoteca,@Named("precio") Integer precio) throws Exception {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();

        if (evento == null) {
            throw new Exception("El evento es null");
        } else if (evento.getFechaInicio() == null) {
            throw new Exception("La fecha de inicio es null");
        } else if (evento.getFechaFinal() == null) {
            throw new Exception("La fecha final es null");
        } else if (idDiscoteca == null) {
            throw new Exception("El id del discoteca a la cual se le va a sociar el evento es null");
        } else if (evento.getMaxEntradas() < 0 || String.valueOf(evento.getMaxEntradas()).equalsIgnoreCase("")) {
            throw new Exception("El maximo de entradas es null o es invalido");
        } else if (evento.getMaxReservas() < 0 || String.valueOf(evento.getMaxReservas()).equalsIgnoreCase("")) {
            throw new Exception("El maximo de reservas es null o es invalido");
        } else if (evento.getNombre() == null || evento.getNombre().equalsIgnoreCase("")) {
            throw new Exception("El nombre del evento es null");
        } else if (String.valueOf(precio).equalsIgnoreCase("")) {
            throw new Exception("El precio es null o es invalido");
        }

        Discoteca discoteca = em.createNamedQuery("Discoteca.findByIdDiscoteca", Discoteca.class).setParameter("idDiscoteca", idDiscoteca).getSingleResult();
        evento.setIdEvento(generarNumeroConsecuenteEvento());
        evento.setPrecio(BigDecimal.valueOf(precio));
        evento.setDiscotecaidDiscoteca(discoteca);
        em.persist(evento);
        em.getTransaction().commit();

        return evento;
    }

    /**
     * Name: editEvent Description: Endpoint que edita una discoteca del
     * sistema Method: Put
     *
     * @param idEvento
     * @param fechaInicio
     * @param fechaFinal
     * @param maxEntradas
     * @param nombre
     * @param maxReservas
     * @param precio
     * @return
     * @throws Exception
     */
    @ApiMethod(httpMethod = ApiMethod.HttpMethod.PUT, name = "editEvent", path = "editEvent")
    public Evento editEvent(@Named("idEvento") Integer idEvento, @Named("fechaInicio") Date fechaInicio, @Named("fechaFinal") Date fechaFinal,@Named("maxEntradas") Integer maxEntradas, @Named("maxReservas") Integer maxReservas, @Named("nombre") String nombre, @Named("precio") Integer precio) throws Exception {

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
    }

    /**
     * Name: deleteEvent Description: Endpoint que elimina un evento de una discoteca del
     * sistema Method: Delete
     *
     * @param idEvento
     * @return Asistente borrado del sistema
     */
    @ApiMethod(name = "deleteEvent", path = "deleteEvent/{idEvento}")
    public Evento deleteEvent(@Named("idEvento") Integer idEvento) {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();
        Evento eventFound = em.createNamedQuery("Evento.findByIdEvento", Evento.class).setParameter("idEvento", idEvento).getSingleResult();

        em.remove(eventFound);
        em.getTransaction().commit();
        return eventFound;
    }

    /**
     * Name: findEvent Description: Endpoint que encuentra un evento del
     * sistema Method: Post
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