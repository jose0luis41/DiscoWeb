/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Logic;

import com.example.echo.Entrada;
import com.example.echo.Usuario;
import com.example.persistence.ClassEntityManagerFactory;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;

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

        BigDecimal precioActual = (BigDecimal) em.createQuery("SELECT con.precioEntrada FROM Configuracion con").getSingleResult();
        System.out.println("com.example.Logic.EntradaLogic.createTicket()     paso3:  "+precioActual);

        Date dateToday = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        int dayActual = cal.get(Calendar.DATE);
        
        Date dateFinish = new Date(System.currentTimeMillis());

        cal.set(Calendar.HOUR_OF_DAY, 06);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DATE, dayActual+1);
        dateFinish.setTime(cal.getTimeInMillis());
        int id =generarNumeroConsecuenteEntrada();
        Entrada ticket = new Entrada(id, user, precioActual, dateToday, dateFinish, "Entrada "+id+"-"+user.getCedula());

        em.persist(ticket);
        em.getTransaction().commit();
        return ticket;
    }

    private int generarNumeroConsecuenteEntrada() {
        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();
        int cantidad = 0;
        if(em.createQuery("SELECT MAX (ent.idEntrada) FROM Entrada ent").getSingleResult() == null){
            cantidad = 1;
        }else{
            cantidad = (int) em.createQuery("SELECT MAX (ent.idEntrada) FROM Entrada ent").getSingleResult();
            cantidad = cantidad + 1;

        }

            
        System.out.println("com.example.Logic.EntradaLogic.generarNumeroConsecuenteEntrada()"+ cantidad);
        em.getTransaction().commit();
        return cantidad;
    }
}
