<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" href="../assets/css/styleIndex.css" />
    <script src="../assets/js/common.js"></script>
  </head>

  <body>
    <div class="header">
      <h1 class="title">PDF To Word</h1>
      <div class="dropdown">
         	<% 
                String username = (String) session.getAttribute("username");
                if (username != null && !username.isEmpty()) {
            %>
                <button class="dropbtn" onclick="toggleDropdown()"><%= username %></button>
            <% 
                } else {
            %>
                <button class="dropbtn" onclick="toggleDropdown()">User</button>
            <% 
                }
            %>
        <div id="myDropdown" class="dropdown-content">
          <a href="#history">History</a>
          <a href="../account/LogoutServlet">Log out</a>
        </div>
      </div>
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
          <button>Choose a file</button>
          <input type="file" name="myfile" id="fileInput" />
        </div>
      </form>
      <div class="text">
        <p>Add PDF, images, Word, Excel, and PowerPoint files</p>
        <p>Supported file types: PDF, DOC, XLS, PPT, PNG, JPG</p>
      </div>
    </div>
  </body>
</html>
