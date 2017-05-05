/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Logic;

import com.example.beans.Reporte;
import com.example.beans.Reserva;
import com.example.beans.Usuario;
import com.example.persistence.ClassEntityManagerFactory;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import java.sql.Date;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author joseluissacanamboy
 */
public class ReservaLogic {

    /**
     * Name: getAllReservations Description: Endpoint que consulta todos las
     * reservaciones en el sistema Method: Get
     *
     * @return Lista de reservas en el sistema
     */
    @ApiMethod(name = "showReservations")
    public List<Reserva> getAllReservations() {

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
     * @param reserva
     * @param cedula
     * @return
     * @throws Exception
     */
    @ApiMethod(httpMethod = ApiMethod.HttpMethod.POST, name = "createReservation", path = "createReservation/success")
    public Reserva createReservation(Reserva reserva, @Named("cedulaCliente") String cedula) throws Exception {
        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();

        em.getTransaction().begin();

        Usuario user = em.createNamedQuery("Usuario.findByCedula", Usuario.class).setParameter("cedula", cedula).getSingleResult();
        Reserva tmp = new Reserva();
        
        tmp.setUsuarioidUsuario(user);
        tmp.setIdReserva(generarNumeroConsecuenteReserva());
        tmp.setFechaCaducidad(new Date(System.currentTimeMillis()));
        Date dateToday = new Date(System.currentTimeMillis());
        tmp.setEstado(new Short("0"));
        tmp.setFechaReserva(dateToday);
        em.persist(tmp);
        em.getTransaction().commit();
        return tmp;
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
