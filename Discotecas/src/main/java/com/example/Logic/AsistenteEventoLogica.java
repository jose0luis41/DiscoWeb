/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Logic;

import com.example.beans.AsistenteEvento;
import com.example.beans.Asistente;
import com.example.beans.Evento;
import com.example.persistence.ClassEntityManagerFactory;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author joseluissacanamboy
 */
public class AsistenteEventoLogica {

    /**
     * Name: getAssistants Description: Endpoint que consulta todos los
     * asistentes en el sistema Method: Get
     *
     * @return Lista de asistentes a eventos en el sistema
     */
    @ApiMethod(name = "showAssistantsEvents")
    public List<AsistenteEvento> getAssistantsEvents() {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();
        List<AsistenteEvento> asistentes = em.createNamedQuery("AsistenteEvento.findAll", AsistenteEvento.class).getResultList();
        em.getTransaction().commit();
        return asistentes;
    }

    /**
     * Name: createAssistantEvent Description: Endpoint que crea un nuevo
     * asistente a un evento en el sistema Method: Post
     *
     * @param cedula
     * @param idEvento
     * @return Asistente a evento que se ha creado
     * @throws Exception cuando los parametros obligatorios no son ingresados
     */
    @ApiMethod(httpMethod = ApiMethod.HttpMethod.POST, name = "createAssistantEvent", path = "createAssistantEvent/success")
    public AsistenteEvento createAssistantEvent(@Named("cedulaAsistente") String cedula, @Named("idEvento") Integer idEvento) throws Exception {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();

        em.getTransaction().begin();

        if (cedula == null) {
            throw new Exception("El id del asistente es null");
        } else if (idEvento == null) {
            throw new Exception("El id del evento es null");

        }

        Asistente asistente = em.createNamedQuery("Asistente.findByCedula", Asistente.class).setParameter("cedula", cedula).getSingleResult();
        Evento event = em.createNamedQuery("Evento.findByIdEvento", Evento.class).setParameter("idEvento", idEvento).getSingleResult();
        AsistenteEvento asistenteEvento = new AsistenteEvento();
        asistenteEvento.setIdAsistenteEvento(generarNumeroConsecuenteAsistenteEvento());
        asistenteEvento.setEventoidEvento(event);
        asistenteEvento.setAsistenteidAsistente(asistente);
        em.persist(asistenteEvento);
        em.getTransaction().commit();

        return asistenteEvento;
    }

  

    /**
     * Name: deleteAssistantEvent Description: Endpoint que elimina un asistente a un evento del
     * sistema Method: Delete
     *
     * @param idAsistenteEvento
     * @return Asistente a evento borrado del sistema
     */
    @ApiMethod(name = "deleteAssistantEvent", path = "deleteAssistantEvent/{idAsistenteEvento}")
    public AsistenteEvento deleteAssistantEvent(@Named("idAsistenteEvento") Integer idAsistenteEvento) {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();
        AsistenteEvento assistantFound = em.createNamedQuery("AsistenteEvento.findByIdAsistenteEvento", AsistenteEvento.class).setParameter("idAsistenteEvento", idAsistenteEvento).getSingleResult();

        em.remove(assistantFound);
        em.getTransaction().commit();
        return assistantFound;
    }

    /**
     * Name: findAssistant Description: Endpoint que encuentra un asistente del
     * sistema Method: Post
     *
     * @param idAsistenteEvento
     * @return
     * @throws Exception
     */
    @ApiMethod(name = "findAssistantEvent", path = "findAssistantEvent")
    public AsistenteEvento findAssistantEvent(@Named("idAsistenteEvento") Integer idAsistenteEvento) throws Exception {
        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();

        AsistenteEvento assistantFound = em.createNamedQuery("AsistenteEvento.findByIdAsistenteEvento", AsistenteEvento.class).setParameter("idAsistenteEvento", idAsistenteEvento).getSingleResult();
        if (assistantFound == null) {
            throw new Exception("No existe el asistente a un evento con id: " + idAsistenteEvento);
        }

        em.getTransaction().commit();

        return assistantFound;
    }

    private int generarNumeroConsecuenteAsistenteEvento() {
        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();

        em.getTransaction().begin();
        int cantidad = 0;

        if (em.createQuery("SELECT MAX (asisEvent.idAsistenteEvento) FROM AsistenteEvento asisEvent").getSingleResult() == null) {
            cantidad = 1;
        } else {
            cantidad = (int) em.createQuery("SELECT MAX (asisEvent.idAsistenteEvento) FROM AsistenteEvento asisEvent").getSingleResult();
            cantidad = cantidad + 1;
        }
        em.getTransaction().commit();
        return cantidad;
    }
}
