package com.example.echo;

import com.example.Logic.EntradaLogic;
import com.example.beans.AsistenteEvento;
import com.example.beans.Entrada;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import com.google.api.server.spi.config.ApiMethod;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "pagoDisco", value = "/pagoDisco")
public class PagoDisco extends HttpServlet {

    public static final long serialversionUID = 1L;
    private Echo delegate;
    private EntradaLogic el;

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
    @ApiMethod(name = "pagoDisco")
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.err.println("ENTRO GET");
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Double state_pol = Double.parseDouble(req.getParameter("state_pol"));
        String message = "";
        System.out.println("Entro al doPost");
        if (state_pol == 4) {
            int idEvento = Integer.parseInt(req.getParameter("extra1"));
            System.out.println("Trajo el evento");
            System.out.println(idEvento + "");
            String correoUsu = req.getParameter("email_buyer");
            System.out.println("Trajo el correo");
            System.out.println(correoUsu + "");

            try {

                AsistenteEvento as = delegate.createAssistantEvent(correoUsu, idEvento);
                System.out.println("trajo el asisevento");
                Entrada e = delegate.createTicket(as.getIdAsistenteEvento());
                System.out.println("creo la entrada");
                
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

                el.envioDeEntradasPorCorreo(correoUsu, qrL);
            } catch (Exception ex) {
                Logger.getLogger(PagoDisco.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (state_pol == 5) {

        } else if (state_pol == 6) {

        }

    }
}
