package com.example.echo;

import com.google.api.server.spi.config.ApiMethod;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "pagoDisco", value = "/pagoDisco")
public class PagoDisco extends HttpServlet {

    public static final long serialversionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PagoDisco() {
        super();
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

        int transactionState = Integer.parseInt(req.getParameter("transactionState"));

        String mensajeRespuesta = "";

        switch (transactionState) {
            case 4:
                mensajeRespuesta = "Transacción exitosa <br>" + "Fecha de la transacción:  " + req.getParameter("processingDate") + "<br>"
                        + "Por el monto: " + Double.parseDouble(req.getParameter("TX_VALUE"))+ "  " + req.getParameter("currency") + "<br>" + "La referencia es:  "
                        + Double.parseDouble(req.getParameter("referenceCode"));
                break;
            case 7:
                mensajeRespuesta = "pending ...";
                break;
            case 6:
                mensajeRespuesta = "Transacción inválida";
                break;
            default:
                break;
        }

        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("<t1>" + mensajeRespuesta + "</t1>");
        out.println("</body>");
        out.println("</html>");
        out.println();
    }

    //PAGINA DE CONFIRMACION
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Double state_pol = Double.parseDouble(req.getParameter("state_pol"));
        String m = "";

        if (state_pol == 4) {

        } else if (state_pol == 5) {

        } else if (state_pol == 6) {

        }

    }
}
