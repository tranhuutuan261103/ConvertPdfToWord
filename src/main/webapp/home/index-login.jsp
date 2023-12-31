<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%> <% String username = (String)
session.getAttribute("username"); if (username == null || username.isEmpty()) {
response.sendRedirect("../home/index.jsp"); } %>
<!DOCTYPE html>
<html>
  <head>
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
      <div class="dropdown">
        <button class="dropbtn" onclick="toggleDropdown()">
          <%= username %>
        </button>

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
          <input
            type="file"
            name="myfile[]"
            id="fileInput"
            multiple="multiple"
            accept=".pdf"
            hidden="hidden"
          />
        </div>
      </form>
      <div class="text">
        <p>Please select the file in PDF format</p>
        <p>Can convert multiple files at once</p>
      </div>
    </div>

    <!-- Modal -->
    <div class="modal">
      <div class="body">
        <!-- <img alt="Loading" src="../assets/image/loading.gif" class="modal-icon"> -->
        <div class="ring">
          Converting
          <span></span>
        </div>
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
