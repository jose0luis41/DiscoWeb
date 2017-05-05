/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Logic;

import com.example.beans.Entrada;
import com.example.beans.Usuario;
import com.example.persistence.ClassEntityManagerFactory;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import com.sendgrid.*;

/**
 *
 * @author joseluissacanamboy
 */
public class EntradaLogic {

    /**
     * Name: getAllReports Description: Endpoint que consulta todos los reportes
     * en el sistema Method: Get
     *
     * @return Lista de reportes en el sistema
     */
    @ApiMethod(name = "showReports")
    public List<Entrada> getAllTicekts() {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();
        List<Entrada> tickets = em.createNamedQuery("Entrada.findAll", Entrada.class).getResultList();
        em.getTransaction().commit();
        return tickets;
    }
    
     @ApiMethod(name = "showTicket")
    public Entrada getTicekt(@Named("codigoQR") String codigoQR) {

         System.out.println("com.example.Logic.EntradaLogic.getTicekt()    espaaaaaaa");
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
     * @param reporte
     * @param cedulaUsu
     * @return reporte nuevo en el sistema
     * @throws Exception cuando la cedula o el correo ya existan
     */
    @ApiMethod(httpMethod = ApiMethod.HttpMethod.POST, name = "createTicket", path = "createTicket/success")
    public Entrada createTicket(@Named("cedula") String cedulaUsu) throws Exception {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();

        em.getTransaction().begin();
        System.out.println("com.example.Logic.EntradaLogic.createTicket()     paso1");
        Usuario user = em.createNamedQuery("Usuario.findByCedula", Usuario.class).setParameter("cedula", cedulaUsu).getSingleResult();
        System.out.println("com.example.Logic.EntradaLogic.createTicket()     paso2");

        BigDecimal precioActual = (BigDecimal) em.createQuery("SELECT con.precio FROM Configuracion con").getSingleResult();
        System.out.println("com.example.Logic.EntradaLogic.createTicket()     paso3:  " + precioActual);

        Date dateToday = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        int dayActual = cal.get(Calendar.DATE);

        Date dateFinish = new Date(System.currentTimeMillis());

        cal.set(Calendar.HOUR_OF_DAY, 06);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DATE, dayActual + 1);
        dateFinish.setTime(cal.getTimeInMillis());
        int id = generarNumeroConsecuenteEntrada();
        Entrada ticket = new Entrada(id, user, precioActual, dateToday, dateFinish, "Entrada " + id + "-" + user.getCedula());
        
        em.persist(ticket);
        System.out.println("com.example.Logic.EntradaLogic.createTicket():    Antes de enviar el correo");
        envioDeEntradasPorCorreo(user, ticket.getCodigoQR());

        em.getTransaction().commit();
        return ticket;
    }

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

        System.out.println("com.example.Logic.EntradaLogic.generarNumeroConsecuenteEntrada()" + cantidad);
        em.getTransaction().commit();
        return cantidad;
    }
}
