/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Logic;

import com.example.beans.Entrada;
import com.example.beans.AsistenteEvento;
import com.example.persistence.ClassEntityManagerFactory;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.sendgrid.*;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author joseluissacanamboy
 */
public class EntradaLogic {

    public static String API_KEY = "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";

    /**
     * Name: getTicekts Description: Endpoint que consulta todas las entradas en
     * el sistema Method: Get
     *
     * @return Lista de entradas en el sistema
     */
    @ApiMethod(name = "showTickets")
    public List<Entrada> getTicekts() {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();
        List<Entrada> tickets = em.createNamedQuery("Entrada.findAll", Entrada.class).getResultList();
        em.getTransaction().commit();
        return tickets;
    }

    /**
     * Name: findTicekt Description: Endpoint que consulta una entrada en el
     * sistema Method: Get
     *
     * @param codigoQR
     * @return Entrada encontrada
     */
    @ApiMethod(name = "findTicket")
    public Entrada findTicekt(@Named("codigoQR") String codigoQR) {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();
        Entrada ticket = em.createNamedQuery("Entrada.findByCodigoQR", Entrada.class).setParameter("codigoQR", codigoQR).getSingleResult();
        em.getTransaction().commit();
        return ticket;
    }

    /**
     * Name: createTicket Description: Endpoint que crea una nueva entrada en el
     * sistema Method: Post
     *
     * @param idAsistenteEvento
     * @return Entrada nueva en el sistema
     * @throws Exception cuando el id del asistente a un evento no exista
     */
    @ApiMethod(httpMethod = ApiMethod.HttpMethod.POST, name = "createTicket", path = "createTicket/success")
    public Entrada createTicket(@Named("Idasistente") Integer idAsistenteEvento) throws Exception {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();

        em.getTransaction().begin();
        AsistenteEvento eventAsisstant = em.createNamedQuery("AsistenteEvento.findByIdAsistenteEvento", AsistenteEvento.class).setParameter("idAsistenteEvento", idAsistenteEvento).getSingleResult();

        Date dateToday = new Date(System.currentTimeMillis());

        Entrada ticket = new Entrada();
        ticket.setFechaCompra(dateToday);
        ticket.setAsistenteEventoidAsistenteEvento(eventAsisstant);
        ticket.setIdEntrada(generarNumeroConsecuenteEntrada());
        ticket.setCodigoQR(ticket.getIdEntrada() + "-" + ticket.getAsistenteEventoidAsistenteEvento().getIdAsistenteEvento());

        em.persist(ticket);
        envioDeEntradasPorCorreo(eventAsisstant.getAsistenteidAsistente().getCorreo(), ticket.getCodigoQR());

        em.getTransaction().commit();
        return ticket;
    }

    private void envioDeEntradasPorCorreo(String usuario, String contenido) throws IOException {
        Email from = new Email("contacto@discoweb.com.co");
        String subject = "Ticket";
        Email to = new Email(usuario);
        Content content = new Content("text/html", contenido);
        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(API_KEY);
        Request request = new Request();

        try {
            request.method = Method.POST;
            request.endpoint = "mail/send";
            request.body = mail.build();
            Response response = sg.api(request);
            System.out.println(response.statusCode);
            // Sytln(response.headers);stem.out.println(response.body);
            // System.out.println(response.headers);
        } catch (IOException ex) {
            throw ex;
        }
    }


    private int generarNumeroConsecuenteEntrada() {
        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();
        int cantidad = 0;
        if (em.createQuery("SELECT MAX (ent.idEntrada) FROM Entrada ent").getSingleResult() == null) {
            cantidad = 1;
        } else {
            cantidad = (int) em.createQuery("SELECT MAX (ent.idEntrada) FROM Entrada ent").getSingleResult();
            cantidad = cantidad + 1;

        }

        em.getTransaction().commit();
        return cantidad;
    }
}
