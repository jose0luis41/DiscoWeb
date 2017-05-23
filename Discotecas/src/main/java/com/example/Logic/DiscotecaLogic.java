/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Logic;

import com.example.beans.Discoteca;
import com.example.persistence.ClassEntityManagerFactory;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.ForbiddenException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author joseluissacanamboy
 */
public class DiscotecaLogic {

    /**
     * Name: getDiscos Description: Endpoint que consulta todas las discotecas
     * en el sistema Method: Get
     *
     * @return Lista de entradas en el sistema
     */
    @ApiMethod(name = "showDiscos")
    public List<Discoteca> getDiscos() {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();
        List<Discoteca> tickets = em.createNamedQuery("Discoteca.findAll", Discoteca.class).getResultList();
        em.getTransaction().commit();
        return tickets;
    }

    /**
     * Name: findDisco Description: Endpoint que consulta una discoteca en el
     * sistema Method: Get
     *
     * @param nombre
     * @return Entrada encontrada
     */
    @ApiMethod(name = "findDisco")
    public Discoteca findDisco(@Named("nombre") String nombre) {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();
        Discoteca ticket = em.createNamedQuery("Discoteca.findByNombre", Discoteca.class).setParameter("nombre", nombre).getSingleResult();
        em.getTransaction().commit();
        return ticket;
    }

    /**
     * Name: createDisco Description: Endpoint que crea una nueva discoteca en
     * el sistema Method: Post
     *
     * @param nombre
     * @return Entrada nueva en el sistema
     * @throws Exception cuando el nombre de la discoteca no sea ingresado
     */
    @ApiMethod(httpMethod = ApiMethod.HttpMethod.POST, name = "createDisco", path = "createDisco/success")
    public Discoteca createDisco(@Named("Nombre") String nombre) throws Exception {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();

        System.out.println("com.example.Logic.DiscotecaLogic.createDisco()        ANTES DEL NEW");
        Discoteca discoteca = new Discoteca();
        System.out.println("com.example.Logic.DiscotecaLogic.createDisco()        ANTES DEL SETEO DEL ID");

        discoteca.setIdDiscoteca(generarNumeroConsecuenteDiscoteca());
        System.out.println("com.example.Logic.DiscotecaLogic.createDisco()        DESPUES DEL SETEO DEL ID");

        discoteca.setNombre(nombre);
        System.out.println("com.example.Logic.DiscotecaLogic.createDisco()        ANTES DEL PERSIST");

        em.persist(discoteca);
        em.getTransaction().commit();
        return discoteca;
    }

    /**
     * Name: editDisco Description: Endpoint que edita un asistente del sistema
     * Method: Put
     *
     * @param idDiscoteca
     * @param nombre
     * @param jwt
     * @return
     * @throws BadRequestException
     * @throws ForbiddenException
     * @throws NotFoundException
     * @throws UnauthorizedException
     * @throws Exception
     */
    @ApiMethod(httpMethod = ApiMethod.HttpMethod.PUT, name = "editDisco", path = "editDisco")
    public Discoteca editDisco(@Named("id") Integer idDiscoteca, @Named("nombre") String nombre,@Named("jwt") String jwt) throws BadRequestException, ForbiddenException, NotFoundException, UnauthorizedException, Exception {

        int[] claims = AdministradorLogica.verificarJWT(jwt);

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();

        if (idDiscoteca == null) {
            throw new Exception("El id es null");
        }

        Discoteca discoFound = em.createNamedQuery("Discoteca.findByIdDiscoteca", Discoteca.class).setParameter("idDiscoteca", idDiscoteca).getSingleResult();

        if (discoFound.getIdDiscoteca() == claims[1]) {
            discoFound.setNombre(nombre);

            em.persist(discoFound);
            em.getTransaction().commit();
            em.refresh(discoFound);
            return discoFound;
        } else {
            throw new UnauthorizedException("Admin is not allowed to edit a disco different from his");
        }
    }


    /**
     * Name: deleteDisco Description: Endpoint que elimina una discoteca del
     * sistema Method: Delete
     *
     * @param nombre
     * @param jwt
     * @return
     * @throws BadRequestException
     * @throws ForbiddenException
     * @throws NotFoundException
     * @throws UnauthorizedException
     */
    @ApiMethod(name = "deleteDisco", path = "deleteDisco/{cedula}")
    public Discoteca deleteDisco(@Named("nombre") String nombre, @Named("jwt") String jwt) throws BadRequestException, ForbiddenException, NotFoundException, UnauthorizedException,Exception {

        int claims[] = AdministradorLogica.verificarJWT(jwt);

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();
        Discoteca discoFound = em.createNamedQuery("Discoteca.findByNombre", Discoteca.class).setParameter("nombre", nombre).getSingleResult();
        if (discoFound.getIdDiscoteca() == claims[1]) {
            em.remove(discoFound);
            em.getTransaction().commit();
            return discoFound;
        } else {
            throw new UnauthorizedException("Admin is not allowed to delete a disco different from his");
        }
    }

    private int generarNumeroConsecuenteDiscoteca() {
        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();
        int cantidad = 0;
        if (em.createQuery("SELECT MAX (disc.idDiscoteca) FROM Discoteca disc").getSingleResult() == null) {
            cantidad = 1;
        } else {
            cantidad = (int) em.createQuery("SELECT MAX (disc.idDiscoteca) FROM Discoteca disc").getSingleResult();
            cantidad = cantidad + 1;

        }

        em.getTransaction().commit();
        return cantidad;
    }

}
