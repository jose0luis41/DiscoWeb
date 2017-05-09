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
import java.sql.Date;
import java.util.List;
import javax.persistence.EntityManager;
import com.sendgrid.*;

/**
 *
 * @author joseluissacanamboy
 */
public class EntradaLogic {

    /**
     * Name: getTicekts Description: Endpoint que consulta todas las entradas
     * en el sistema Method: Get
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
     * Name: findTicekt Description: Endpoint que consulta una entrada
     * en el sistema Method: Get
     * @param codigoQR
     * @return Entrada encontrada
     */
    
     @ApiMethod(name = "findTicket")
    public Entrada findTicekt(@Named("codigoQR") String codigoQR) {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();
        Entrada ticket = em.createNamedQuery("Entrada.findByCodigoQR", Entrada.class).setParameter("codigoQR",codigoQR).getSingleResult();
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
        ticket.setCodigoQR(ticket.getIdEntrada()+""+ticket.getAsistenteEventoidAsistenteEvento().getIdAsistenteEvento());
        
        em.persist(ticket);
        //envioDeEntradasPorCorreo(user, ticket.getCodigoQR());

        em.getTransaction().commit();
        return ticket;
    }
    
    //TODO arreglar en envio de entradas
/*
    private void envioDeEntradasPorCorreo(Usuario usuario,String htmlQR) throws Exception{
        System.out.println("com.example.Logic.EntradaLogic.envioDeEntradasPorCorreo():     Entra al método");
       
        SendGrid sendgrid = new SendGrid("esteband95","e12345678");
        SendGrid.Email email = new SendGrid.Email();

        email.addTo("jose0luis41@hotmail.com");
        email.setFrom("duragi307@hotmail.com");
        email.setSubject("Ticket para tu entrada en Living Nigth Club");
        email.setHtml(htmlQR);
        System.out.println("com.example.Logic.EntradaLogic.envioDeEntradasPorCorreo():    Enviando el correo");
        SendGrid.Response response = sendgrid.send(email);
        if(response.getCode()!=200){
            throw new Exception("com.example.Logic.EntradaLogic.envioDeEntradasPorCorreo():   Algo fallo");
        }
        System.out.println("com.example.Logic.EntradaLogic.envioDeEntradasPorCorreo()    "+ email.getFrom());
        System.out.println("com.example.Logic.EntradaLogic.envioDeEntradasPorCorreo()    "+email.getTos()[0]);
        System.out.println("com.example.Logic.EntradaLogic.envioDeEntradasPorCorreo()   "+ response.getMessage());
    }
*/
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
