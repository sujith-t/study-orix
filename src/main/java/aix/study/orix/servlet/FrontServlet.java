
package aix.study.orix.servlet;

import aix.study.res.ResourceAppException;
import aix.study.res.ResourceDomain;
import aix.study.res.ResourceService;
import aix.study.res.YouTubeDomain;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Sujith T
 * 
 * <!In God We Trust>
 */
@WebServlet(name = "FrontServlet", urlPatterns = {"/front"})
public class FrontServlet extends HttpServlet {
    
    private static final Logger logger = Logger.getLogger(FrontServlet.class.getName());
    private final ResourceService resourceService;

    @Inject
    public FrontServlet(ResourceService resourceService) {
        super();
        this.resourceService = resourceService;
    }
    
    /**
     * processing of GET Request
     */
    private void processGet(HttpServletRequest request, HttpServletResponse response, String param)
            throws ServletException, IOException, ResourceAppException {
            
        String url = "/fast/index.xhtml";
        if(param != null && !param.trim().equals("")) {
            param = param.trim();

            ResourceDomain content = this.resourceService.getResourceByUrlKey(param);
            if(content == null) {
                url = "/fast/nocontent.xhtml";
            } else {
                switch(content.getResourceType()) {
                    case "UTUBE":
                        url = "/fast/utube.xhtml";
                        request.setAttribute("material", (YouTubeDomain) content);
                        break;
                }
            }
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String q = request.getParameter("q");
        try {
            processGet(request, response, q);
        } catch (ResourceAppException | ServletException | IOException ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "FrontServlet";
    }
}