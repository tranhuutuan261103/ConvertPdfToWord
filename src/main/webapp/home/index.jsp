<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
 <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>PDF to Word Converter</title>
    <link rel="stylesheet" href="../assets/css/styleIndex.css" />
    <script>
      function showSignIn() {
        document.querySelector(".popup").classList.add("active");
      }

      function unshowSignIn() {
        document.querySelector(".popup").classList.remove("active");
      }

      function showSignUp() {
        document.querySelector(".popup-sign-up").classList.add("active");
      }

      function unshowSignUp() {
        document.querySelector(".popup-sign-up").classList.remove("active");
      }
    </script>
</head>
  <body>
    <div class="header">
      <h1 class="title">PDF TO Word</h1>
      <div class="space"></div>
      <button class="show-login" onclick="showSignIn()">
        <img src="../assets/image/user.svg" alt="Login" /> Sign Up
      </button>
    </div>
    <div class="container">
      <h1>PDF to Word Converter</h1>
      <div class="upload-btn-wrapper">
        <button>Choose a file</button>
        <input type="file" name="myfile" />
      </div>
      <div class="popup">
        <div class="close-btn" onclick="unshowSignIn()">&times</div>
        <form class="form" action="../account/CheckLogin" method="post">
          <h2>Log in</h2>
          <div class="form-element">
            <label class="view-label" for="email">Email</label>
            <input type="text" name="email" id="email" placeholder="Enter email" />
          </div>
          <div class="form-element">
            <label class="view-label" for="password">Password</label>
            <input
              type="password"
              name="password"
              id="password"
              placeholder="Enter password"
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
            <a href="#">Forgot password</a>
          </div>
        </form>
      </div>
      <div class="popup-sign-up">
        <div class="close-btn" onclick="unshowSignUp()">&times</div>
        <form class="form" action="">
          <h2>Log in</h2>
          <div class="form-element">
            <label class="view-label" for="email">Email</label>
            <input type="text" name="" id="email" placeholder="Enter email" />
          </div>
          <div class="form-element">
            <label class="view-label" for="password">Password</label>
            <input
              type="password"
              name="password"
              id="password"
              placeholder="Enter password"
            />
          </div>
          <div class="form-element">
            <label class="view-label" for="password">Comfirm password </label>
            <input
              type="password"
              name="comfirm-password"
              id="comfirm-password"
              placeholder="Enter password"
            />
          </div>
          <div class="form-element checkbox-container">
            <input type="checkbox" id="remember-me" />
            <label for="remember-me"
              >I agree to the Terms of Service and Privacy Policy.</label
            >
          </div>
          <div class="form-element">
            <button>Sign up</button>
          </div>
          <div class="form-element">
            <a href="#" onclick="showSignIn()">Already member? Login</a>
            <a href="#">Forgot password</a>
          </div>
        </form>
      </div>
      <p>Add PDF, images, Word, Excel, and PowerPoint files</p>
      <p>Supported file types: PDF, DOC, XLS, PPT, PNG, JPG</p>
    </div>
  </body>
</html>