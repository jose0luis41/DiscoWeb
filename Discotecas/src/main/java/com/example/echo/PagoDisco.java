package com.example.echo;

import com.example.Logic.AsistenteEventoLogica;
import com.example.Logic.AsistenteLogica;
import com.example.Logic.EntradaLogic;
import com.example.Logic.EventoLogica;
import com.example.beans.Asistente;
import com.example.beans.AsistenteEvento;
import com.example.beans.Evento;
import com.google.api.server.spi.config.ApiMethod;
import java.io.IOException;
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
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PagoDisco() {
        super();
        delegate = new Echo();
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

        String mensajeRespuesta = "Senor(a)" + "<br>" + "La informacion de su transaccion es la siguiente:" + "<br>";
        String estado = "";
        switch (transactionState) {
            case 4:
                estado = "APROBADA";
                mensajeRespuesta = mensajeRespuesta + "<br>" + "FECHA:  " + req.getParameter("processingDate") + "<br>"
                        + "MONTO: " + Double.parseDouble(req.getParameter("TX_VALUE")) + "  " + req.getParameter("currency") + "<br>" + "REFRENCIA:  "
                        + req.getParameter("referenceCode") + "<br>" + "Por favor revise su correo electronico " + req.getParameter("buyerEmail") + "  y:" + "<br>" 
                        + "Descargue su TICKET (QR Code)"
                        + "<br>"+ "PayU envia el estado de la transaccion tambien a su correo." + "<br>" + "Gracias por comprar por nuestro pagina web";
                break;
            case 7:
                estado = "PENDIENTE";
                mensajeRespuesta = "la transacción esta en estado pendiente pendiente ...";
                break;
            case 6:
                estado = "INVALIDA";
                mensajeRespuesta = "Señor usuario verifique si existen entradas o si no tiene fondos.";
                break;
            default:
                break;
        }

        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<!--CSS files-->");
        out.println("<link rel=\"stylesheet\" href=\"/css/styles.css\"/>");
        out.println("<meta charset=\"utf-8\">");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
        out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">");
        out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>");
        out.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>");
        out.println("<title id=\"name\"></title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<ul>");
        out.println("<li><a href=\"/index.html\">Home</a></li>");
        out.println("<li><a href=\"#news\">News</a></li>");
        out.println("<li><a href=\"#contact\">Contact</a></li>");
        out.println("<li><a href=\"#about\">About</a></li>");
        out.println("</ul>");
        out.println("<div class=\"jumbotron\">");
        out.println("<h1 align =\"center\">" + "TRANSACCION   "+ estado + "</h1>");
        out.println("</div>");
        out.println("<p style=\"font-size:160%;\">" + mensajeRespuesta + "</p>");
        out.println("<button type=\"button\" onclick = \"newPage()\">Volver a eventos</button>");
        out.println("<script language=\"JavaScript\">");
        out.println("function newPage(){");
        out.println(" window.location=\"/Events/VistaEventos.html\";");
        out.println("</script>");
        out.println("");
        out.println("</body>");
        out.println("</html>");

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
                AsistenteEvento as =  delegate.createAssistantEvent(correoUsu, idEvento);
                System.out.println("trajo el asisevento");
                delegate.createTicket(as.getIdAsistenteEvento());
                System.out.println("creo la entrada");
            } catch (Exception ex) {
                Logger.getLogger(PagoDisco.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (state_pol == 5) {

        } else if (state_pol == 6) {

        }

    }
}
