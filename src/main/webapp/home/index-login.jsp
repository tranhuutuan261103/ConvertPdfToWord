<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
  <head>
  	<title>Convert PDF to Word</title>
    <link rel="stylesheet" href="../assets/css/styleIndex.css" />
    <script src="../assets/js/common.js"></script>
  </head>

  <body>
    <div class="header">
      <h1 class="title"><a href="../home/index" style="color: inherit; text-decoration: none">PDF To Word</a></h1>
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
          <a href="../fileStorage/GetAllFile">History</a>
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
          <label class="abc" for="fileInput">Choose file</label>
          <input type="file" name="myfile[]" id="fileInput" multiple="multiple" hidden />
        </div>
      </form>
      <div class="text">
        <p>Add PDF, images, Word, Excel, and PowerPoint files</p>
        <p>Supported file types: PDF, DOC, XLS, PPT, PNG, JPG</p>
      </div>
    </div>
  </body>
  <script type="text/javascript">
  document.getElementById('fileInput').addEventListener('change', function () {
      // Get the form element
      var form = document.getElementById('uploadForm');
      console.log("Oke");
      // Trigger the form submission
      form.submit();
  });
  </script>
</html>
