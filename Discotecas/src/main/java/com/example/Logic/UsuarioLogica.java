/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Logic;

import com.example.echo.Rol;
import com.example.echo.Usuario;
import com.example.persistence.ClassEntityManagerFactory;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author joseluissacanamboy
 */


public class UsuarioLogica {

    /**
     * Name: darTodosUsuarios Description: Endpoint que consulta todos los
     * usuarios en el sistema Method: Get
     *
     * @return Lista de usuarios en el sistema
     */
    @ApiMethod(name = "showUsers")
    public List<Usuario> getAllUsers() {
        
        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();
        List<Usuario> usuarios = em.createNamedQuery("Usuario.findAll", Usuario.class).getResultList();
        em.getTransaction().commit();
        return usuarios;
    }

    /**
     * Name: createUser Description: Endpoint que crea un nuevo usuario en el
     * sistema Method: Post
     * @param usuario
     * @param Rol
     * @return Usuario nuevo en el sistema
     * @throws Exception cuando la cedula o el correo ya existan
     */
    @ApiMethod(httpMethod = ApiMethod.HttpMethod.POST, name = "createUsuario", path = "createUsuario/success")
    public Usuario createUser(Usuario usuario, @Named("Rol") int Rol) throws Exception {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();

        em.getTransaction().begin();

        Rol rol = em.find(Rol.class, Rol);
        Usuario tmp = new Usuario(rol, generarNumeroConsecuenteUsuario(), usuario.getCedula(), usuario.getCorreo(), usuario.getNombre(), usuario.getTelefono(), usuario.getFechaNac(), usuario.getContrasena());
        em.persist(tmp);
        em.getTransaction().commit();
        return usuario;
    }

    /**
     * Name: editUser Description: Endpoint que edita un usuario del sistema
     * Method: Put
     * @param cedula
     * @param rol
     * @param userName
     * @param correo
     * @param telefono
     * @param fechaNac
     * @param contrasena
     * @return Usuario modificado
     * @throws Exception cuando el rol ingresado no existe
     */
    @ApiMethod(httpMethod = ApiMethod.HttpMethod.PUT, name = "editUser", path = "editUser")
    public Usuario editUser(@Named("Id") String cedula, @Named("Rol") int rol, @Named("Name") String userName, @Named("Email") String correo, @Named("Telephone") String telefono, @Named("Birthday") Date fechaNac, @Named("Password") String contrasena) throws Exception {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();

        Rol rolFound = em.find(Rol.class, rol);

        if (rolFound == null) {
            throw new Exception("El rol ingresado no existe");
        }

        Usuario userFound = em.createNamedQuery("Usuario.findByCedula", Usuario.class).setParameter("cedula", cedula).getSingleResult();

        userFound.setContrasena(contrasena);
        userFound.setCorreo(correo);
        userFound.setFechaNac(fechaNac);
        userFound.setNombre(userName);
        userFound.setTelefono(telefono);
        userFound.setRolidRol(rolFound);
        em.persist(userFound);
        em.getTransaction().commit();
        em.refresh(userFound);
        return userFound;
    }

    /**
     * Name: deleteUser Description: Endpoint que elimina un usuario del sistema
     * Method: Put
     * @param cedula
     * @return Usuario borrado del sistema
     */
    @ApiMethod(name = "deleteUser", path = "deleteUser/{cedula}")
    public Usuario deleteUser(@Named("cedula") String cedula) {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();
        Usuario userFound = em.createNamedQuery("Usuario.findByCedula", Usuario.class).setParameter("cedula", cedula).getSingleResult();

        em.remove(userFound);
        em.getTransaction().commit();
        return userFound;
    }

    @ApiMethod(name = "consultarUsuario", path = "consultarUsuario")
    public Usuario findUser(@Named("cedula") String cedula) throws Exception {
        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();

        Usuario userFound = em.createNamedQuery("Usuario.findByCedula", Usuario.class).setParameter("cedula", cedula).getSingleResult();
        if (userFound == null) {
            throw new Exception("No existe el usuario con la cedula: " + cedula);
        }

        em.getTransaction().commit();

        return userFound;
    }

    private int generarNumeroConsecuenteUsuario() {
        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();

        em.getTransaction().begin();
        int cantidad = (int) em.createQuery("SELECT MAX (usu.idUsuario) FROM Usuario usu").getSingleResult();
        System.out.println(cantidad);
        cantidad = cantidad + 1;
        em.getTransaction().commit();
        return cantidad;
    }

}
