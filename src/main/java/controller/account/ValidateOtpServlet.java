package controller.account;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ValidateOtpServlet
 */
@WebServlet("/account/ValidateOtpServlet")
public class ValidateOtpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ValidateOtpServlet() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String inputOtp = request.getParameter("code");
		String sessionOtp = (String) request.getSession().getAttribute("otp");

		if (sessionOtp != null && sessionOtp.equals(inputOtp)) {
			request.getSession().setAttribute("otpVerified", "true");
			response.sendRedirect("../home/index.jsp");
		} else {
			request.getSession().setAttribute("otpVerified", "false");
			response.sendRedirect("../home/index.jsp");
		}
	}

}
