package com.example.echo;

import com.example.Logic.EntradaLogic;
import com.example.beans.AsistenteEvento;
import com.example.beans.Entrada;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PagoDisco extends HttpServlet {

    public static final long serialversionUID = 1L;
    private Echo delegate;
    private EntradaLogic el;

    private static final Logger log = LoggerFactory.getLogger(PagoDisco.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PagoDisco() {
        super();
        delegate = new Echo();
        el = new EntradaLogic();
    }

    //PAGINA DE RESPUESTA
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        log.info("Entro al get ");
        int transactionState = Integer.parseInt(req.getParameter("transactionState"));

        String fecha = req.getParameter("processingDate");
        String monto = Double.parseDouble(req.getParameter("TX_VALUE")) + "  " + req.getParameter("currency");
        String referencia = req.getParameter("referenceCode");
        String correoUsu = req.getParameter("buyerEmail");

        InputStreamReader inputStreamReader = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("templatePageRespuesta.html"), "UTF-8");
        BufferedReader br = new BufferedReader(inputStreamReader);

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {

            sb.append(line);
        }
        String correoL = "";
        String estadoL = "";
        String content = sb.toString();
        String estado = "";
        switch (transactionState) {
            case 4:

                estado = "APROBADA";
                estadoL = content.replaceAll("\\{\\{estadoT\\}\\}", estado);
                String fechaL = estadoL.replaceAll("\\{\\{fechaT\\}\\}", fecha);
                String montoL = fechaL.replaceAll("\\{\\{montoT\\}\\}", monto);
                String referenceL = montoL.replaceAll("\\{\\{referenceT\\}\\}", referencia);
                correoL = referenceL.replaceAll("\\{\\{correoT\\}\\}", correoUsu);
                break;
            case 7:
                estado = "PENDIENTE";
                estadoL = content.replaceAll("\\{\\{estadoT\\}\\}", estado);
                //mensajeRespuesta = "la transacción esta en estado pendiente pendiente ...";
                break;
            case 6:
                estado = "INVALIDA";
                estadoL = content.replaceAll("\\{\\{estadoT\\}\\}", estado);
                //  mensajeRespuesta = "Señor usuario verifique si existen entradas o si no tiene fondos.";
                break;
            default:
                break;
        }
        PrintWriter out = resp.getWriter();
        out.println(correoL);
    }

    //PAGINA DE CONFIRMACION
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        Double state_pol = Double.parseDouble(req.getParameter("state_pol"));
        if (state_pol == 4) {
         

        int idEvento = Integer.parseInt(req.getParameter("extra1"));

        try {
            String correoUsu = req.getParameter("email_buyer");
            AsistenteEvento as = delegate.createAssistantEvent(correoUsu, idEvento);
            Entrada e = delegate.createTicket(as.getIdAsistenteEvento());
            String qrcode = e.getCodigoQR();
            InputStreamReader inputStreamReader = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("templateCorreo.html"), "UTF-8");
            BufferedReader br = new BufferedReader(inputStreamReader);

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {

                sb.append(line);
            }
            String content = sb.toString();
            String qrL = content.replaceAll("\\{\\{codeQR\\}\\}", qrcode);
            el.envioDeEntradasPorCorreo(correoUsu, qrL);
        } catch (Exception ex) {
            log.info("Exception");
        }
        
    } else if (state_pol == 5) {

        } else if (state_pol == 6) {

        }
         
    }
}
