package controller.account;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BO.AccountBO;
import model.Bean.Account;

/**
 * Servlet implementation class ChangePasswordServlet
 */
@WebServlet("/account/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePasswordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String user = (String) request.getSession().getAttribute("emailChangepass");

		String pass = request.getParameter("comfirm-password-email");
		
		System.out.println(user + " - " + pass);

		Account changePasswordRequest = new Account(user, pass);

		AccountBO accountBO = new AccountBO();
		boolean result = accountBO.changePassword(changePasswordRequest);

		if (result == true) {
			response.sendRedirect("../home/index.jsp");
		} else {
			response.sendRedirect("../home/error.jsp");
		}	}

}
