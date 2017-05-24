/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Logic;

import com.example.beans.Evento;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.beans.Adminstrador;
import com.example.beans.Discoteca;
import com.example.persistence.ClassEntityManagerFactory;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.ForbiddenException;
import com.google.api.server.spi.response.NotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
     * Name: getAdministrators Description: Endpoint que consulta todos los
     * adminsitradores en el sistema Method: Get
     *
     * @param idDiscoteca
     * @return Lista de asistentes en el sistema
     */
    @ApiMethod(name = "showEventsByAdministrator")
    public List<Evento> getEventsByAdministrators(@Named("idDiscoteca") Integer idDiscoteca) {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();
        Discoteca discoActual = em.createNamedQuery("Discoteca.findByIdDiscoteca", Discoteca.class).setParameter("idDiscoteca", idDiscoteca).getSingleResult();
        List<Evento> events = discoActual.getEventoList();
        em.getTransaction().commit();
        return events;
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
        System.out.println("com.example.Logic.AdministradorLogica.createAdministrator():    " + adminstrador.getFechaNac());
        if (adminstrador == null) {
            throw new Exception("El administrador es null");
        } else if (adminstrador.getCedula() == null || adminstrador.getCedula().equalsIgnoreCase("")) {
            throw new Exception("La cedula es null");
        } else if (adminstrador.getContrasena() == null || adminstrador.getContrasena().equalsIgnoreCase("")) {
            throw new Exception("La contrasena es null");
        } else if (adminstrador.getCorreo() == null || adminstrador.getCorreo().equalsIgnoreCase("")) {
            throw new Exception("El correo es null");
        } else if (adminstrador.getFechaNac() == null) {
            throw new Exception("La fecha es null");
        } else if (adminstrador.getNombre() == null || adminstrador.getNombre().equalsIgnoreCase("")) {
            throw new Exception("El nombre es null");
        } else if (adminstrador.getTelefono() == null || adminstrador.getTelefono().equalsIgnoreCase("")) {
            throw new Exception("El telefono es null");
        } else if (idDiscoteca < 0 || String.valueOf(idDiscoteca).equalsIgnoreCase("")) {
            throw new Exception("El id de la discoteca es null");
        }

        Discoteca discoteca = em.createNamedQuery("Discoteca.findByIdDiscoteca", Discoteca.class).setParameter("idDiscoteca", idDiscoteca).getSingleResult();

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
     * Name: findAdministrator Description: Endpoint que encuentra un
     * administrador del sistema Method: Post
     *
     * @param correo
     * @return
     * @throws Exception
     */
    @ApiMethod(name = "findAdministratorByCorreo", path = "findAdministratorByCorreo")
    public Adminstrador findAdministratorByCorreo(@Named("correo") String correo) throws Exception {
        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();

        Adminstrador administratorFound = em.createNamedQuery("Adminstrador.findByCorreo", Adminstrador.class).setParameter("correo", correo).getSingleResult();
        if (administratorFound == null) {
            throw new Exception("No existe el administrador con el correo: " + correo);
        }

        em.getTransaction().commit();

        return administratorFound;
    }

    /**
     * Name: loginAdministrator Description: Endpoint que consulta un
     * adminsitrador en el sistema Method: Get
     *
     * @param correo
     * @return
     * @throws BadRequestException
     * @throws ForbiddenException
     * @throws NotFoundException
     * @throws ParseException
     * @throws UnsupportedEncodingException
     */
    @ApiMethod(name = "loginAdministrators", path = "loginAdministrators/success")
    public JWTE getLoginAdministrator(@Named("correo") String correo) throws BadRequestException, ForbiddenException, NotFoundException, ParseException, UnsupportedEncodingException {

        if (correo == null || correo.equalsIgnoreCase("")) {
            throw new BadRequestException("No se ha escrito correos");

        }

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();
        Adminstrador adminstrador = em.createNamedQuery("Adminstrador.findByCorreo", Adminstrador.class).setParameter("correo", correo).getSingleResult();
        if (adminstrador == null) {
            throw new NotFoundException("No existe el administrador con correo " + correo);
        }
        em.getTransaction().commit();

        Calendar c = Calendar.getInstance();
        Date date = new Date();
        String dt = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) + " " + (c.get(Calendar.HOUR_OF_DAY) + 1) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        c.setTime(sdf.parse(dt));
        dt = sdf.format(c.getTime());

        /*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new D;
        dateFormat.format(date);
         */
        String jwtToken = JWT.create().withClaim("Id", adminstrador.getIdAdministrador()).withClaim("disco", adminstrador.getDiscotecaidDiscoteca().getIdDiscoteca()).withExpiresAt(c.getTime()).sign(Algorithm.HMAC256("QWHDIKSEUNSJHDE"));

        JWTE token = new com.example.Logic.JWTE(adminstrador.getIdAdministrador().toString(), jwtToken);

        return token;
    }
/*
    private void prueba() throws Exception {
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        String dt = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) + " " + (c.get(Calendar.HOUR_OF_DAY) + 1) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);
        System.out.println("dt apenas se crea: " + dt);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        c.setTime(sdf.parse(dt));
        c.add(Calendar.DATE, 1);
        dt = sdf.format(c.getTime());

        System.out.println("total " + c.getTime());
        System.out.println("Dia ---> " + c.getTime().getDate());
        System.out.println("Mes ---> " + (c.getTime().getMonth() + 1));
        System.out.println("Hora---> " + c.getTime().getHours());
        System.out.println("Minuto ---> " + c.getTime().getMinutes());

    }

    public static void main(String[] args){
        AdministradorLogica administradorLogica = new AdministradorLogica();
       try{
        administradorLogica.prueba();
       }catch(Exception e){
           e.getMessage();
       }
    }*/
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

    /**
     * Se encarga de verificar la validez del jwt
     *
     * @param jwt
     * @return el contenido del payload, en caso de que se necesite: claims[0] =
     * idAdmin, claims[1] = idDiscoteca
     * @throws BadRequestException
     * @throws ForbiddenException
     * @throws NotFoundException
     */
    public static int[] verificarJWT(String jwt) throws BadRequestException, ForbiddenException, NotFoundException, Exception {
        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        DecodedJWT dec = JWT.decode(jwt);
        int[] claims = new int[2];


        Calendar c = Calendar.getInstance();
        Date date = new Date();
        String dt = "2017" + "-" + (dec.getExpiresAt().getMonth() + 1) + "-" + dec.getExpiresAt().getDate() + " " + dec.getExpiresAt().getHours() + ":" + dec.getExpiresAt().getMinutes() + ":" + dec.getExpiresAt().getSeconds();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        c.setTime(sdf.parse(dt));
        dt = sdf.format(c.getTime());

        
        if (c.getTime().compareTo(Calendar.getInstance().getTime()) < 0) {

            throw new ForbiddenException("Token has expired");
        }

        Claim claim1 = dec.getClaim("Id");
        Claim claim2 = dec.getClaim("disco");

        if (claim1.isNull() || claim2.isNull()) {
            throw new BadRequestException("claim is null");
        }

        int idAdmin = claim1.asInt();
        int idDisco = claim2.asInt();

        Adminstrador adminstrador = em.createNamedQuery("Adminstrador.findByIdAdministrador", Adminstrador.class).setParameter("idAdministrador", idAdmin).getSingleResult();

        if (adminstrador == null) {
            throw new NotFoundException("Admin with id[" + idAdmin + "] doesn't exist");
        }

        if (adminstrador.getDiscotecaidDiscoteca().getIdDiscoteca() != idDisco) {
            throw new ForbiddenException("user is not an admin of disco " + idDisco);
        }

        claims[0] = idAdmin;
        claims[1] = idDisco;

        return claims;
    }
}
