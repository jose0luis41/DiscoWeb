/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Logic;

import com.example.beans.Reserva;
import com.example.beans.AsistenteEvento;
import com.example.persistence.ClassEntityManagerFactory;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author joseluissacanamboy
 */
public class ReservaLogic {

    /**
     * Name: getReservations Description: Endpoint que consulta todos las
     * reservaciones en el sistema Method: Get
     *
     * @return Lista de reservas en el sistema
     */
    @ApiMethod(name = "showReservations")
    public List<Reserva> getReservations() {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();
        List<Reserva> reservas = em.createNamedQuery("Reserva.findAll", Reserva.class).getResultList();
        em.getTransaction().commit();
        return reservas;
    }

    /**
     * Name: createReservation Description: Endpoint que crea una reserva en el
     * sistema Method: Get
     *
     * @param idAsistenteEvento
     * @return
     * @throws Exception
     */
    @ApiMethod(httpMethod = ApiMethod.HttpMethod.POST, name = "createReservation", path = "createReservation/success")
    public Reserva createReservation(@Named("Id asistente") Integer idAsistenteEvento) throws Exception {
        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();

        AsistenteEvento eventAsisstant = em.createNamedQuery("AsistenteEvento.findByIdAsistenteEvento", AsistenteEvento.class).setParameter("idAsistenteEvento", idAsistenteEvento).getSingleResult();
        Reserva reserva = new Reserva();
        reserva.setEstado(new Short("0"));
        reserva.setAsistenteEventoidAsistenteEvento(eventAsisstant);
        reserva.setFechaReserva(new Date(System.currentTimeMillis()));
        reserva.setIdReserva(generarNumeroConsecuenteReserva());
        Date fechaCaducidad = (Date) eventAsisstant.getEventoidEvento().getFechaFinal();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 22);
        cal.set(Calendar.MINUTE, 30);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        fechaCaducidad.setTime(cal.getTimeInMillis());
        reserva.setFechaCaducidad(fechaCaducidad);

        em.persist(reserva);
        em.getTransaction().commit();
        return reserva;
    }

    /**
     * Name: deleteReservation Description: Endpoint que elimina una reserva del
     * sistema Method: Put
     *
     * @param id
     * @return Reserva borrado del sistema
     */
    @ApiMethod(name = "deleteReservation", path = "deleteReservation/{id}")
    public Reserva deleteReservation(@Named("id") int id) {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();
        Reserva reservationFound = em.createNamedQuery("Reserva.findByIdReserva", Reserva.class).setParameter("idReserva", id).getSingleResult();

        em.remove(reservationFound);
        em.getTransaction().commit();
        return reservationFound;
    }

    /**
     * Name: findReservation Description: Endpoint que busca una reserva del
     * sistema Method: put
     *
     * @param id
     * @return Reserva consultado del sistema
     * @throws Exception      *
     */
    @ApiMethod(name = "consultarReserva", path = "consultarReserva")
    public Reserva findReservation(@Named("id") int id) throws Exception {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();

        Reserva findReservation = em.createNamedQuery("Reserva.findByIdReserva", Reserva.class).setParameter("idReserva", id).getSingleResult();
        if (findReservation == null) {
            throw new Exception("No existe el reporte");
        }

        em.getTransaction().commit();

        return findReservation;
    }

    private int generarNumeroConsecuenteReserva() {
        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();

        em.getTransaction().begin();
        int cantidad = 0;

        if (em.createQuery("SELECT MAX (res.idReserva) FROM Reserva res").getSingleResult() == null) {
            cantidad = 1;
        } else {
            cantidad = (int) em.createQuery("SELECT MAX (res.idReserva) FROM Reserva res").getSingleResult();
            cantidad = cantidad + 1;

        }
        em.getTransaction().commit();
        return cantidad;
    }
}
