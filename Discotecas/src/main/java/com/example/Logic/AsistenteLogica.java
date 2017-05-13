/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Logic;

import com.example.beans.*;
import com.example.persistence.ClassEntityManagerFactory;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 *
 * @author joseluissacanamboy
 */
public class AsistenteLogica {

    /**
     * Name: getAssistants Description: Endpoint que consulta todos los
     * asistentes en el sistema Method: Get
     *
     * @return Lista de asistentes en el sistema
     */
    @ApiMethod(name = "showAssistants")
    public List<Asistente> getAssistants() {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();
        List<Asistente> asistentes = em.createNamedQuery("Asistente.findAll", Asistente.class).getResultList();
        em.getTransaction().commit();
        return asistentes;
    }

    /**
     * Name: createUser Description: Endpoint que crea un nuevo usuario en el
     * sistema Method: Post
     *
     * @param assistant
     * @return Asistente que se ha creado
     * @throws Exception cuando los parametros obligatorios no son ingresados
     */
    @ApiMethod(httpMethod = ApiMethod.HttpMethod.POST, name = "createAssistant", path = "createAssistant/success")
    public Asistente createAssistant(Asistente assistant) throws Exception {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();

        em.getTransaction().begin();

        if (assistant == null) {
            throw new Exception("El asistente es null");
        } else if (assistant.getCedula() == null || assistant.getCedula().equalsIgnoreCase("")) {
            throw new Exception("La cedula es null");
        } else if (assistant.getContrasena() == null || assistant.getContrasena().equalsIgnoreCase("")) {
            throw new Exception("La contrasena es null");
        } else if (assistant.getCorreo() == null || assistant.getCorreo().equalsIgnoreCase("")) {
            throw new Exception("El correo es null");
        } else if (assistant.getFechaNacimiento() == null) {
            throw new Exception("La cedula es null");
        } else if (assistant.getNombre() == null || assistant.getNombre().equalsIgnoreCase("")) {
            throw new Exception("El nombre es null");
        } else if (assistant.getTelefono() == null || assistant.getTelefono().equalsIgnoreCase("")) {
            throw new Exception("El telefono es null");
        }

        assistant.setIdAsistente(generarNumeroConsecuenteAsistente());
        em.persist(assistant);
        em.getTransaction().commit();

        return assistant;
    }

    /**
     * Name: editAssistant Description: Endpoint que edita un asistente del
     * sistema Method: Put
     *
     * @param cedula
     * @param assistantName
     * @param correo
     * @param telefono
     * @param fechaNacimiento
     * @param contrasena
     * @return
     * @throws Exception
     */
    @ApiMethod(httpMethod = ApiMethod.HttpMethod.PUT, name = "editAssistant", path = "editAssistant")
    public Asistente editAssistant(@Named("Id") String cedula, @Named("Name") String assistantName, @Named("Email") String correo, @Named("Telephone") String telefono, @Named("Birthday") Date fechaNacimiento, @Named("Password") String contrasena) throws Exception {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();

        if (cedula == null || cedula.equalsIgnoreCase("")) {
            throw new Exception("La cedula es null");
        } else if (assistantName == null || assistantName.equalsIgnoreCase("")) {
            throw new Exception("El nombre es null");
        } else if (correo == null || correo.equalsIgnoreCase("")) {
            throw new Exception("El correo es null");
        } else if (fechaNacimiento == null) {
            throw new Exception("La fecha de nacimiento es null");
        } else if (telefono == null || telefono.equalsIgnoreCase("")) {
            throw new Exception("El telefono es null");
        } else if (contrasena == null || contrasena.equalsIgnoreCase("")) {
            throw new Exception("La contrasena es null");
        }

        Asistente assistantFound = em.createNamedQuery("Asistente.findByCedula", Asistente.class).setParameter("cedula", cedula).getSingleResult();

        assistantFound.setContrasena(contrasena);
        assistantFound.setCorreo(correo);
        assistantFound.setFechaNacimiento(fechaNacimiento);
        assistantFound.setNombre(assistantName);
        assistantFound.setTelefono(telefono);

        em.persist(assistantFound);
        em.getTransaction().commit();
        em.refresh(assistantFound);
        return assistantFound;
    }

    /**
     * Name: deleteUser Description: Endpoint que elimina un asistente del
     * sistema Method: Delete
     *
     * @param cedula
     * @return Asistente borrado del sistema
     */
    @ApiMethod(name = "deleteAssistant", path = "deleteAssistant/{cedula}")
    public Asistente deleteAssistant(@Named("cedula") String cedula) {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();
        Asistente assistantFound = em.createNamedQuery("Asistente.findByCedula", Asistente.class).setParameter("cedula", cedula).getSingleResult();

        em.remove(assistantFound);
        em.getTransaction().commit();
        return assistantFound;
    }

    /**
     * Name: findAssistant Description: Endpoint que encuentra un asistente del
     * sistema Method: Post
     *
     * @param cedula
     * @return
     * @throws Exception
     */
    @ApiMethod(name = "findAssistant", path = "findAssistant")
    public Asistente findAssistant(@Named("cedula") String cedula) throws Exception {
        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();

        Asistente assistantFound = em.createNamedQuery("Asistente.findByCedula", Asistente.class).setParameter("cedula", cedula).getSingleResult();
        if (assistantFound == null) {
            throw new Exception("No existe el asistente con la cedula: " + cedula);
        }

        em.getTransaction().commit();

        return assistantFound;
    }

    /**
     * Name: getLoginAdministrator Description: Endpoint que consulta un
     * asistente en el sistema Method: Get
     *
     * @param correo
     * @throws Exception
     * @return Asistente encontrado
     */
    @ApiMethod(name = "loginAssistant")
    public Asistente getLoginAsistente(@Named("correo") String correo) throws Exception {

        if (correo == null || correo.equalsIgnoreCase("")) {
            throw new BadRequestException("No se ha escrito correos");

        }

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();
        Asistente asistente = em.createNamedQuery("Asistente.findByCorreo", Asistente.class).setParameter("correo", correo).getSingleResult();
        if (asistente == null) {
            throw new NoResultException("No existe el administrador con correo " + correo);
        }
        em.getTransaction().commit();
        return asistente;
    }

    private int generarNumeroConsecuenteAsistente() {
        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();

        em.getTransaction().begin();
        int cantidad = 0;

        if (em.createQuery("SELECT MAX (asis.idAsistente) FROM Asistente asis").getSingleResult() == null) {
            cantidad = 1;
        } else {
            cantidad = (int) em.createQuery("SELECT MAX (asis.idAsistente) FROM Asistente asis").getSingleResult();
            cantidad = cantidad + 1;
        }
        em.getTransaction().commit();
        return cantidad;
    }

}
