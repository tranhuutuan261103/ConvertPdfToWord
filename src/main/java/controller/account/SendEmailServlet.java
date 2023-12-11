package controller.account;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

import java.util.Properties;

/**
 * Servlet implementation class SendEmailServlet
 */
@WebServlet("/account/SendEmailServlet")
public class SendEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SendEmailServlet() {
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
		RequestDispatcher rd = request.getRequestDispatcher("../home/index.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String recipientEmail = request.getParameter("validate-email");
		Random rand = new Random();
		String otp = Integer.toString(rand.nextInt(900000) + 100000);

		System.out.println(recipientEmail);
		Properties properies = new Properties();
		properies.put("mail.smtp.host", "smtp.gmail.com");
		properies.put("mail.smtp.port", "465");
		properies.put("mail.smtp.auth", "true");
		properies.put("mail.smtp.starttls.enable", "true");
		properies.put("mail.smtp.starttls.required", "true");
		properies.put("mail.smtp.ssl.protocols", "TLSv1.2");
		properies.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getInstance(properies, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("nngann2402@gmail.com", "qjxdnmnjvezvejns");
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(recipientEmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
			message.setSubject("Valid code");
			message.setText(otp);
			Transport.send(message);
			request.getSession().setAttribute("showPopupOTP", "true");
			request.getSession().setAttribute("otp", otp);
			request.getSession().setAttribute("emailChangepass", recipientEmail);
			response.sendRedirect("../home/index.jsp");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			response.sendRedirect("../home/error.jsp");

		}

	}

}
