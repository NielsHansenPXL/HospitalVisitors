package be.pxl.ja2.examen.servlet;

import be.pxl.ja2.examen.model.Bezoeker;
import be.pxl.ja2.examen.service.BezoekersService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/afdeling")
public class AfdelingServlet extends HttpServlet {

    @Autowired
    BezoekersService bezoekersService;

    @Override
    public void init() throws ServletException {
        super.init();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String afdelingscode = req.getParameter("code");
        List<Bezoeker> bezoekerslijst = bezoekersService.getBezoekersVoorAfdeling(afdelingscode);
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<html>");
            out.println("<body>");
            out.println("<table>");
            for (Bezoeker bezoeker : bezoekerslijst) {
                out.println("<tr><td> Tijdstip:" + bezoeker.getTijdstip() + " Patiënt: " + bezoeker.getPatient().getCode() + " Bezoeker: " +  bezoeker.getNaam() + " " + bezoeker.getVoornaam() + " (tel: " + bezoeker.getTelefoonnummer() + ")" + "</td></tr>");
        }
            out.println("</table");
            out.println("</body>");
            out.println("</html>");
        }
    }
}

