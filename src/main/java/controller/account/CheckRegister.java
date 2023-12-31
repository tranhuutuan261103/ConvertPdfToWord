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
 * Servlet implementation class CheckRegister
 */
@WebServlet("/account/CheckRegister")
public class CheckRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckRegister() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String user = request.getParameter("new-email");
		String pass = request.getParameter("confirm-password");

		Account registerRequest = new Account(user, pass);

		AccountBO accountBO = new AccountBO();
		boolean result = accountBO.Register(registerRequest);

		if (result == true) {
			response.sendRedirect("../home/index.jsp");
		} else {
			response.sendRedirect("../home/error.jsp");
		}
	}

}
