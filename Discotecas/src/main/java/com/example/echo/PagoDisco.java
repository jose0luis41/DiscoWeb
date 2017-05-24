package com.example.echo;

import com.example.Logic.EntradaLogic;
import com.example.beans.AsistenteEvento;
import com.example.beans.Entrada;
import com.google.api.server.spi.response.BadRequestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

//@WebServlet(name = "pagoDisco", value = "/pagoDisco")
public class PagoDisco extends HttpServlet {

    public static final long serialversionUID = 1L;
    private Echo delegate;
    private EntradaLogic el;

    private static final Logger log = Logger.getLogger(PagoDisco.class.getName());

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
        String content = sb.toString();
        String estado = "";
        switch (transactionState) {
            case 4:

                estado = "APROBADA";
                String estadoL = content.replaceAll("\\{\\{estadoT\\}\\}", estado);
                String fechaL = estadoL.replaceAll("\\{\\{fechaT\\}\\}", fecha);
                String montoL = fechaL.replaceAll("\\{\\{montoT\\}\\}", monto);
                String referenceL = montoL.replaceAll("\\{\\{referenceT\\}\\}", referencia);
                correoL = referenceL.replaceAll("\\{\\{correoT\\}\\}", correoUsu);
                break;
            case 7:
                estado = "PENDIENTE";
                String estadoP = content.replaceAll("\\{\\{estadoT\\}\\}", estado);
                //mensajeRespuesta = "la transacción esta en estado pendiente pendiente ...";
                break;
            case 6:
                estado = "INVALIDA";
                String estadoI = content.replaceAll("\\{\\{estadoT\\}\\}", estado);
                //  mensajeRespuesta = "Señor usuario verifique si existen entradas o si no tiene fondos.";
                break;
            default:
                break;
        }
        PrintWriter out = resp.getWriter();
        out.println(correoL);

    }

    //PAGINA DE CONFIRMACION
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        //Double state_pol = Double.parseDouble(req.getParameter("state_pol"));
        String state_pol =req.getParameter("state_pol");
        String message = "";
        if (state_pol.equalsIgnoreCase("4")) {
            int idEvento = Integer.parseInt(req.getParameter("extra1"));
            System.out.println(idEvento + "");
            String correoUsu = req.getParameter("email_buyer");
            System.out.println(correoUsu + "");

            try {

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
                String correoL = "";
                String content = sb.toString();

                String qrL = content.replaceAll("\\{\\{codeQR\\}\\}", qrcode);
                System.out.println("antes de enviar el correo");
                System.out.println(qrL);

                el.envioDeEntradasPorCorreo(correoUsu, qrL);
            } catch (BadRequestException | IOException ex) {
                StringWriter errors = new StringWriter();
                ex.printStackTrace(new PrintWriter(errors));
                log.info(errors.toString());
                
            }
        } else if (state_pol.equalsIgnoreCase("5")) {

        } else if (state_pol.equalsIgnoreCase("6")) {

        }

    }
}
