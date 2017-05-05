/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Logic;

import com.example.beans.Reporte;
import com.example.beans.Usuario;
import com.example.beans.TipoReporte;
import com.example.persistence.ClassEntityManagerFactory;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author dylan9538
 */
public class ReporteLogic {

    /**
     * Name: getAllReports Description: Endpoint que consulta todos los reportes
     * en el sistema Method: Get
     *
     * @return Lista de reportes en el sistema
     */
    @ApiMethod(name = "showReports")
    public List<Reporte> getAllReports() {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();
        List<Reporte> reportes = em.createNamedQuery("Reporte.findAll", Reporte.class).getResultList();
        em.getTransaction().commit();
        return reportes;
    }

    /**
     * Name: createReport Description: Endpoint que crea un nuevo reporte en el
     * sistema Method: Post
     *
     * @param reporte
     * @param tipoReporte
     * @param cedulaUsu
     * @return reporte nuevo en el sistema
     * @throws Exception cuando la cedula o el correo ya existan
     */
    @ApiMethod(httpMethod = ApiMethod.HttpMethod.POST, name = "createReport", path = "createReport/success")
    public Reporte createReport(Reporte reporte, @Named("TipoReporte") int tipoReporte, @Named("cedula") String cedulaUsu) throws Exception {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();

        em.getTransaction().begin();

        TipoReporte tipoR = em.find(TipoReporte.class, tipoReporte);
        Usuario user = em.createNamedQuery("Usuario.findByCedula", Usuario.class).setParameter("cedula", cedulaUsu).getSingleResult();

        Reporte reporteCreado = new Reporte(tipoR, user, generarNumeroConsecuenteReporte(), reporte.getFecha(), reporte.getContenido());

        em.persist(reporteCreado);
        em.getTransaction().commit();
        return reporteCreado;
    }

    /**
     * Name: deleteReport Description: Endpoint que elimina un reporte del
     * sistema Method: Put
     *
     * @param id
     * @return Reporte borrado del sistema
     */
    @ApiMethod(name = "deleteReport", path = "deleteReport/{id}")
    public Reporte deleteReport(@Named("id") int id) {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();
        Reporte reportFound = em.createNamedQuery("Reporte.findByIdReporte", Reporte.class).setParameter("idReporte", id).getSingleResult();

        em.remove(reportFound);
        em.getTransaction().commit();
        return reportFound;
    }

    /**
     * Name: findReport Description: Endpoint que busca un reporte del sistema
     * Method: put
     *
     * @param id
     * @return Reporte consultado del sistema
     */
    @ApiMethod(name = "consultarReporte", path = "consultarReporte")
    public Reporte findReport(@Named("id") int id) throws Exception {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();

        Reporte findReport = em.createNamedQuery("Reporte.findByIdReporte", Reporte.class).setParameter("idReporte", id).getSingleResult();
        if (findReport == null) {
            throw new Exception("No existe el reporte");
        }

        em.getTransaction().commit();

        return findReport;
    }

    /**
     * Name: findReportsUser Description: Endpoint que busca un reporte del
     * sistema Method: put
     *
     * @param cedula
     * @return List Reportes del ususario dado
     */
    @ApiMethod(name = "consultarReportes", path = "consultarReportes")
    public List<Reporte> findReportsUser(@Named("cedula") String cedula) throws Exception {

        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();

        List<Reporte> reportesDelUsuario = em.createQuery("SELECT r FROM Reporte r WHERE r.usuarioidUsuario.cedula = :cedula", Reporte.class).setParameter("cedula", cedula).getResultList();
        if (reportesDelUsuario == null) {
            throw new Exception("No existen reportes para el usuario con la cedula: " + cedula);
        }
        em.getTransaction().commit();
        return reportesDelUsuario;
    }

    private int generarNumeroConsecuenteReporte() {
        EntityManager em = ClassEntityManagerFactory.get().createEntityManager();
        em.getTransaction().begin();
        int cantidad = 0;
        if (em.createQuery("SELECT MAX (r.idReporte) FROM Reporte r").getSingleResult() == null) {
            cantidad = 1;
        } else {
            cantidad = (int) em.createQuery("SELECT MAX (r.idReporte) FROM Reporte r").getSingleResult();
            cantidad = cantidad + 1;
        }
        cantidad = cantidad + 1;
        em.getTransaction().commit();
        return cantidad;
    }

}
