/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Logic;

import com.example.beans.AsistenteEvento;
import com.example.beans.Entrada;
import com.example.echo.Echo;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;


/**
 *
 * @author joseluissacanamboy
 */
public class PagoLogica {

    private static final Logger log = Logger.getLogger(PagoLogica.class.getName());

    //private Echo delegate;
    //private EntradaLogic el;

    public PagoLogica() {
      //  delegate = new Echo();
        //el = new EntradaLogic();
    }

    @ApiMethod(httpMethod = ApiMethod.HttpMethod.POST, name = "confirmarPago", path = "confirmarPago")
    public void confirmarPago(@Named("state_pol") String state_pol, @Named("extra1") String extra1, @Named("email_buyer") String email_buyer) throws BadRequestException {

        log.info("Entro al post");
        //Double state_pol = Double.parseDouble(req.getParameter("state_pol"));
        //String state_pol =req.getParameter("state_pol");
        String message = "";
        System.out.println("Entro al doPost");
        if (state_pol.equalsIgnoreCase("4")) {
            //int idEvento = Integer.parseInt(req.getParameter("extra1"));
            int idEvento = Integer.parseInt(extra1);
            System.out.println("Trajo el evento");
            System.out.println(idEvento + "");
            //String correoUsu = req.getParameter("email_buyer");
            String correoUsu = email_buyer;
            System.out.println("Trajo el correo");
            System.out.println(correoUsu + "");

            try {

               // AsistenteEvento as = delegate.createAssistantEvent(correoUsu, idEvento);
                System.out.println("trajo el asisevento");
                //Entrada e = delegate.createTicket(as.getIdAsistenteEvento());
                System.out.println("creo la entrada");

               // String qrcode = e.getCodigoQR();
                InputStreamReader inputStreamReader = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("templateCorreo.html"), "UTF-8");
                BufferedReader br = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {

                    sb.append(line);
                }
                String correoL = "";
                String content = sb.toString();

                //String qrL = content.replaceAll("\\{\\{codeQR\\}\\}", qrcode);

                //el.envioDeEntradasPorCorreo(correoUsu, qrL);
            } catch (IOException io) {
                throw new BadRequestException("");
            } catch (UnsupportedOperationException ex) {
                throw new BadRequestException("");
            }
        } else if (state_pol.equalsIgnoreCase("5")) {

        } else if (state_pol.equalsIgnoreCase("6")) {

        }

    }
}
