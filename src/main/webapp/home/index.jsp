<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%> <% String otpVerified = (String)
request.getSession().getAttribute("otpVerified"); String showPopup = (String)
session.getAttribute("showPopupOTP"); %>
<script>
            window.onload = function() {
                <% if ("true".equals(otpVerified)) {
      request.getSession().removeAttribute("otpVerified");
  %>
                showNewPass();
                <% }
     if ("true".equals(showPopup)) {
      session.removeAttribute("showPopupOTP");
  %>
                showEnterValid();
                <% }
     if ("false".equals(otpVerified)) {
  %>
                alert("Invalid OTP. Please try again.");
                showEnterValid();
                <% }
  %>
            };
</script>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Convert PDF to Word</title>
    <link rel="stylesheet" href="../assets/css/styleIndex.css" />
    <script src="../assets/js/common.js"></script>
  </head>

  <body>
    <div class="header">
      <h1 class="title">
        <a href="../home/index" style="color: inherit; text-decoration: none"
          >PDF To Word</a
        >
      </h1>
      <button class="show-login" onclick="showSignIn()">
        <img src="../assets/image/user.svg" alt="Login" /> Sign In
      </button>
    </div>
    <div class="container">
      <h1>PDF to Word Converter</h1>
      <form
        id="uploadForm"
        action="../home/PdfToWord"
        method="post"
        enctype="multipart/form-data"
      >
        <div class="upload-btn-wrapper">
          <label class="abc" for="fileInput">Choose file</label>
          <input
            type="file"
            name="myfile"
            id="fileInput"
            accept=".pdf"
            hidden="hidden"
          />
        </div>
      </form>
      <div class="popup">
        <div class="close-btn" onclick="unshowSignIn()">&times;</div>
        <form class="form" action="../account/CheckLogin" method="post">
          <h2>Log in</h2>
          <div class="form-element">
            <label class="view-label" for="email">Email</label>
            <input
              type="text"
              name="email"
              id="email"
              placeholder="Enter email"
              required
            />
          </div>
          <div class="form-element">
            <label class="view-label" for="password">Password</label>
            <input
              type="password"
              name="password"
              id="password"
              placeholder="Enter password"
              required
            />
          </div>
          <div class="form-element checkbox-container">
            <input type="checkbox" id="remember-me" />
            <label for="remember-me">Remember me</label>
          </div>
          <div class="form-element">
            <button>Sign in</button>
          </div>
          <div class="form-element">
            <a href="#" onclick="showSignUp()">Create an account</a>
            <a href="#" onclick="showForgot()"> Forgot password</a>
          </div>
        </form>
      </div>

      <div class="popup-sign-up">
        <div class="close-btn" onclick="unshowSignUp()">&times;</div>
        <form class="form" action="../account/CheckRegister">
          <h2>Sign up</h2>
          <div class="form-element">
            <label class="view-label" for="email">Email</label>
            <input
              type="text"
              name="new-email"
              id="new-email"
              placeholder="Enter email"
              required
              oninput="validateEmail()"
            />
          </div>
          <div class="form-element">
            <label class="view-label" for="password">Password</label>
            <input
              type="password"
              name="new-password"
              id="new-password"
              placeholder="Enter password"
              required
              oninput="checkPasswordMatch()"
            />
          </div>
          <div class="form-element">
            <label class="view-label" for="password">Comfirm password </label>
            <input
              type="password"
              name="confirm-password"
              id="confirm-password"
              placeholder="Enter password"
              required
              oninput="checkPasswordMatch()"
            />
          </div>
          <div class="form-element checkbox-container">
            <input type="checkbox" id="remember-me" required />
            <label for="remember-me"
              >I agree to the Terms of Service and Privacy Policy.</label
            >
          </div>
          <div class="form-element">
            <button>Sign up</button>
          </div>
          <div class="form-element">
            <a href="#" onclick="showSignIn()">Already member?</a>
            <a href="#" onclick="showForgot()">Forgot password</a>
          </div>
        </form>
      </div>

      <div class="popup-forgot-pass">
        <div class="close-btn" onclick="unshowForgot()">&times;</div>
        <form class="form" action="../account/SendEmailServlet" method="post">
          <h2>Forgot your password</h2>
          <div class="form-element">
            <label class="view-label" for="email"
              >Enter your email address</label
            >
            <input
              type="text"
              name="validate-email"
              id="validate-email"
              placeholder="Enter email"
              required
              oninput="validateEmail()"
            />
          </div>
          <div class="form-element">
            <button>Next</button>
          </div>
          <div class="form-element">
            <a href="#" onclick="showSignIn()">Already member? Login</a>
          </div>
        </form>
      </div>

      <div class="popup-otp">
        <div class="close-btn" onclick="unshowEnterValid()">&times;</div>
        <form class="form" action="../account/ValidateOtpServlet" method="post">
          <h2>Verification</h2>
          <div class="form-element">
            <label class="view-label" for="email"
              >Please enter 6 digits code you receive</label
            >
            <input
              type="text"
              name="code"
              id="code"
              placeholder="Enter digits"
              required
            />
          </div>
          <div class="form-element">
            <button>Next</button>
          </div>
          <div class="form-element">
            <a href="#" onclick="showSignIn()">Already member? Login</a>
          </div>
        </form>
      </div>

      <div class="popup-create-new-pass">
        <div class="close-btn" onclick="unshowNewPass()">&times;</div>
        <form class="form" action="../account/ChangePasswordServlet">
          <h2>Reset your password</h2>
          <div class="form-element">
            <label class="view-label" for="password">Password</label>
            <input
              type="password"
              name="new-password-email"
              id="new-password-email"
              placeholder="Enter password"
              required
              oninput="checkPasswordMatch()"
            />
          </div>
          <div class="form-element">
            <label class="view-label" for="password">Comfirm password </label>
            <input
              type="password"
              name="comfirm-password-email"
              id="comfirm-password-email"
              placeholder="Enter password"
              required
              oninput="checkPasswordMatch()"
            />
          </div>
          <div class="form-element">
            <button>Next</button>
          </div>
          <div class="form-element">
            <a href="#" onclick="showSignIn()">Already member? Login</a>
          </div>
        </form>
      </div>

      <div class="text">
        <p>Please select the file in PDF format</p>
        <p>Only supports converting 1 file, please log in to convert more</p>
      </div>
    </div>

    <!-- Modal -->
    <div class="modal">
      <div class="body">
        <!-- <img alt="Loading" src="../assets/image/loading.gif" class="modal-icon"> -->
        <div class="ring">Converting <span></span></div>
      </div>
    </div>
  </body>
  <script type="text/javascript">
    document
      .getElementById("fileInput")
      .addEventListener("change", function () {
        // Get the form element
        var form = document.getElementById("uploadForm");
        console.log("Oke");
        document.querySelector(".modal").classList.add("active");
        document.querySelector(".container").classList.add("unactive");
        // Trigger the form submission
        form.submit();
      });
  </script>
</html>
