package aix.study.orix.servlet;

import aix.study.res.ResourceService;
import aix.study.res.YouTubeDomain;
import java.io.IOException;
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
@WebServlet(name = "ContentServlet", urlPatterns = {"/content"})
public class ContentServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ContentServlet.class.getName());
    private final ResourceService resourceService;

    private static final String CREATE_UTUBE = "cr_ut";
    private static final String EDIT_UTUBE = "ed_ut";
    private static final String LIST_CONT = "li_ct";

    @Inject
    public ContentServlet(ResourceService resourceService) {
        super();
        this.resourceService = resourceService;
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("a");
        if (action == null || action.trim().isEmpty()) {
            action = LIST_CONT;
        }

        String page = "/fast/list_content.xhtml";
        switch (action) {
            case CREATE_UTUBE:
                page = "/fast/edit_utube.xhtml";
                break;
            case EDIT_UTUBE:
                page = "/fast/edit_utube.xhtml";
                break;
            case LIST_CONT:              
            default:
                break;
        }
        
        request.setAttribute("mode", action);
        request.setAttribute("postUrl", request.getRequestURL() + "?" + request.getQueryString());
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "ContentServlet";
    }
}
