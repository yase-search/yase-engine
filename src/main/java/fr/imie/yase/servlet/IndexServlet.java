package fr.imie.yase.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.imie.yase.business.Search;
import fr.imie.yase.helpers.PagingHelper;
import fr.imie.yase.helpers.SearchFormat;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/yolo")
public class IndexServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;

	/**    
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String search = request.getParameter("search");
			String parameterPaging = request.getParameter("paging");
			Integer numberPage = 1;
			if(search == null || search.length() == 0){
				response.sendRedirect("/");
			} else {
				System.out.println(request.getParameter("search"));
				
				// On récupére le paramètre de la pagination
                if (parameterPaging != null) {
                    numberPage = Integer.parseInt(parameterPaging);
                }
				
				Search searchResults = new Search(request.getParameter("search"), numberPage);
				// On récupére le block de la pagination
				PagingHelper paging = new PagingHelper(numberPage, searchResults.getNumberPages(), search);
				
				request.setAttribute("search", searchResults);
				request.setAttribute("paging", paging.generatePagingHTML());

				SearchFormat searchFormat = new SearchFormat();
				request.setAttribute("searchFormat", searchFormat);

				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Search.jsp");
				rd.forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
