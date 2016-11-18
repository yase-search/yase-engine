package fr.imie.yase.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.imie.yase.admin.database.dao.AdminWebSiteDAO;
import fr.imie.yase.admin.dto.AdminWebSite;
import fr.imie.yase.database.dao.KeywordsDAO;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminWebSiteDAO websiteDAO = new AdminWebSiteDAO();
		KeywordsDAO keywordsDAO = new KeywordsDAO();
		Map<String, Integer> mapKeywords = new HashMap<String, Integer>();
		List<AdminWebSite> listWebsite = new ArrayList<AdminWebSite>();
		Integer numberPages = 0;
		try {
            listWebsite = websiteDAO.findAll();
            mapKeywords = KeywordsDAO.findAllKeywords();
            for (AdminWebSite website : listWebsite) {
                numberPages = website.getNumberPage() + numberPages;
            }
            // On trie par ASC
            Collections.sort(listWebsite);
        } catch (SQLException e) {
            e.printStackTrace();
        }
		request.setAttribute("websites", listWebsite);
		request.setAttribute("numberPages", numberPages);
		request.setAttribute("numberWebsite", listWebsite.size());
		request.setAttribute("numberKeywords", mapKeywords.size());
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Admin.jsp");
        rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
