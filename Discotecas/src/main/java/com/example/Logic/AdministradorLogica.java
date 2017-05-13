/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Logic;

import com.example.beans.Adminstrador;
import com.example.beans.Discoteca;
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
public class AdministradorLogica {

    /**
     * Name: getAdministrators Description: Endpoint que consulta todos los
     * adminsitradores en el sistema Method: Get
     *
     * @return Lista de asistentes en el sistema
     */
    @ApiMethod(name = "showAdministrators")
    public List<Adminstrador> getAdministrators() {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();
        List<Adminstrador> asistentes = em.createNamedQuery("Adminstrador.findAll", Adminstrador.class).getResultList();
        em.getTransaction().commit();
        return asistentes;
    }

    /**
     * Name: createUser Description: Endpoint que crea un nuevo adminstrador en
     * el sistema Method: Post
     *
     * @param adminstrador
     * @param idDiscoteca
     * @return Adminstrador que se ha creado
     * @throws Exception cuando los parametros obligatorios no son ingresados
     */
    @ApiMethod(httpMethod = ApiMethod.HttpMethod.POST, name = "createAdministrator", path = "createAdministrator/success")
    public Adminstrador createAdministrator(Adminstrador adminstrador, @Named("idDiscoteca") Integer idDiscoteca) throws Exception {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();

        em.getTransaction().begin();

        if (adminstrador == null) {
            throw new Exception("El administrador es null");
        } else if (adminstrador.getCedula() == null || adminstrador.getCedula().equalsIgnoreCase("")) {
            throw new Exception("La cedula es null");
        } else if (adminstrador.getContrasena() == null || adminstrador.getContrasena().equalsIgnoreCase("")) {
            throw new Exception("La contrasena es null");
        } else if (adminstrador.getCorreo() == null || adminstrador.getCorreo().equalsIgnoreCase("")) {
            throw new Exception("El correo es null");
        } else if (adminstrador.getFechaNac() == null) {
            throw new Exception("La cedula es null");
        } else if (adminstrador.getNombre() == null || adminstrador.getNombre().equalsIgnoreCase("")) {
            throw new Exception("El nombre es null");
        } else if (adminstrador.getTelefono() == null || adminstrador.getTelefono().equalsIgnoreCase("")) {
            throw new Exception("El telefono es null");
        } else if (idDiscoteca < 0 || String.valueOf(idDiscoteca).equalsIgnoreCase("")) {
            throw new Exception("El id de la discoteca es null");
        }

        System.out.println("com.example.Logic.AdministradorLogica.createAdministrator()   ANTES DE LA CONSULTA DISCOTECAS");
        Discoteca discoteca = em.createNamedQuery("Discoteca.findByIdDiscoteca", Discoteca.class).setParameter("idDiscoteca", idDiscoteca).getSingleResult();
        System.out.println("com.example.Logic.AdministradorLogica.createAdministrator()   DESPUES DE LA CONSULTA DISCOTECAS");

        adminstrador.setDiscotecaidDiscoteca(discoteca);
        adminstrador.setIdAdministrador(generarNumeroConsecuenteAdministrador());
        em.persist(adminstrador);
        em.getTransaction().commit();

        return adminstrador;
    }

    /**
     * Name: editAdministrator Description: Endpoint que edita un administrador
     * del sistema Method: Put
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
    @ApiMethod(httpMethod = ApiMethod.HttpMethod.PUT, name = "editAdministrator", path = "editAdministrator")
    public Adminstrador editAdministrator(@Named("Id") String cedula, @Named("Name") String assistantName, @Named("Email") String correo, @Named("Telephone") String telefono, @Named("Birthday") Date fechaNacimiento, @Named("Password") String contrasena) throws Exception {

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

        Adminstrador administratorFound = em.createNamedQuery("Adminstrador.findByCedula", Adminstrador.class).setParameter("cedula", cedula).getSingleResult();

        administratorFound.setContrasena(contrasena);
        administratorFound.setCorreo(correo);
        administratorFound.setFechaNac(fechaNacimiento);
        administratorFound.setNombre(assistantName);
        administratorFound.setTelefono(telefono);

        em.persist(administratorFound);
        em.getTransaction().commit();
        em.refresh(administratorFound);
        return administratorFound;
    }

    /**
     * Name: deleteUser Description: Endpoint que elimina un administrador del
     * sistema Method: Delete
     *
     * @param cedula
     * @return Administrador borrado del sistema
     */
    @ApiMethod(name = "deleteAdministrator", path = "deleteAdministrator/{cedula}")
    public Adminstrador deleteAdministrator(@Named("cedula") String cedula) {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();
        Adminstrador administratorFound = em.createNamedQuery("Adminstrador.findByCedula", Adminstrador.class).setParameter("cedula", cedula).getSingleResult();

        em.remove(administratorFound);
        em.getTransaction().commit();
        return administratorFound;
    }

    /**
     * Name: findAdministrator Description: Endpoint que encuentra un
     * administrador del sistema Method: Post
     *
     * @param cedula
     * @return
     * @throws Exception
     */
    @ApiMethod(name = "findAdministrator", path = "findAdministrator")
    public Adminstrador findAdministrator(@Named("cedula") String cedula) throws Exception {
        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();

        Adminstrador administratorFound = em.createNamedQuery("Adminstrador.findByCedula", Adminstrador.class).setParameter("cedula", cedula).getSingleResult();
        if (administratorFound == null) {
            throw new Exception("No existe el asistente con la cedula: " + cedula);
        }

        em.getTransaction().commit();

        return administratorFound;
    }
    
     /**
     * Name: loginAdministrator Description: Endpoint que consulta un
     * adminsitrador en el sistema Method: Get
     *
     * @param correo
     * @return Administrador encontrado
     */
    @ApiMethod(name = "loginAdministrators")
    public Adminstrador getLoginAdministrator(@Named("correo") String correo) throws Exception {

        if (correo == null || correo.equalsIgnoreCase("")) {
            throw new BadRequestException("No se ha escrito correos");

        }

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();
        Adminstrador adminstrador = em.createNamedQuery("Adminstrador.findByCorreo", Adminstrador.class).setParameter("correo", correo).getSingleResult();
        if (adminstrador == null) {
            throw new NoResultException("No existe el administrador con correo " + correo);
        }
        em.getTransaction().commit();
        return adminstrador;
    }

    private int generarNumeroConsecuenteAdministrador() {
        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();

        em.getTransaction().begin();
        int cantidad = 0;


        if (em.createQuery("SELECT MAX (admin.idAdministrador) FROM Adminstrador admin").getSingleResult() == null) {
            cantidad = 1;
        } else {

            cantidad = (int) em.createQuery("SELECT MAX (admin.idAdministrador) FROM Adminstrador admin").getSingleResult();
            cantidad = cantidad + 1;
        }

        em.getTransaction().commit();
        return cantidad;
    }
}
