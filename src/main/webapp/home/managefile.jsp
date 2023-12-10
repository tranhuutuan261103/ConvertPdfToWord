<%@page import="model.Bean.FileStorageVM"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
    	<title>Convert PDF to Word</title>
        <link rel="stylesheet" href="../assets/css/styleIndex.css" />
        <script src="../assets/js/common.js"></script>
        <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
            integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
            crossorigin="anonymous"
            referrerpolicy="no-referrer"
        />
        <script type="text/javascript">
        	function showConfirmDelete(Id) {
	    		document.getElementById("deleteId").value = Id;
	        	document.querySelector(".popup-delete").classList.add("active");
	      	}
	
	      	function unshowConfirmDelete() {
	        	document.querySelector(".popup-delete").classList.remove("active");
	      	}
	      
	      	function showConfirmDownload(Id) {
		    	document.getElementById("downloadId").value = Id;
		        document.querySelector(".popup-download").classList.add("active");
		    }
		
		    function unshowConfirmDownload() {
		        document.querySelector(".popup-download").classList.remove("active");
		    }
		    
		    function showConfirmDeleteAll() {
		    	var selects = document.getElementsByName("select");
		    	var listIdDelete = [];
		    	for (var i = 0; i < selects.length; i++) {
					if(selects[i].checked == true) {
						listIdDelete.push(selects[i].value);
					}
				}
	    		document.getElementById("deleteIdAll").value = listIdDelete;
	        	document.querySelector(".delete-all").classList.add("active");
	      	}
	
	      	function unshowConfirmDeleteAll() {
	        	document.querySelector(".delete-all").classList.remove("active");
	      	}
	      
	      	function showConfirmDownloadAll() {
	      		var selects = document.getElementsByName("select");
		    	var listIdDownload = [];
		    	for (var i = 0; i < selects.length; i++) {
					if(selects[i].checked == true) {
						listIdDownload.push(selects[i].value);
					}
				}
		    	document.getElementById("downloadIdAll").value = listIdDownload;
		        document.querySelector(".download-all").classList.add("active");
		    }
		
		    function unshowConfirmDownloadAll() {
		        document.querySelector(".download-all").classList.remove("active");
		    }
		    
		    function showAction() {
		    	var selects = document.getElementsByName("select");
		    	for (var i = 0; i < selects.length; i++) {
					if(selects[i].checked == true) {
						document.querySelector(".manage-container__action").classList.add("manage-container__action-active");
						return;
					}
				}    	
		    	document.getElementById("select-all").checked = false;
		    	document.querySelector(".manage-container__action").classList.remove("manage-container__action-active");
			}
		    
			function slectedAll() {
				var selects = document.getElementsByName("select");
				var status = false;
				if(document.getElementById("select-all").checked == true) {
					status = true;
					document.querySelector(".manage-container__action").classList.add("manage-container__action-active");
				}
				else {
					document.querySelector(".manage-container__action").classList.remove("manage-container__action-active");
				}
				for (var i = 0; i < selects.length; i++) {
					selects[i].checked = status;
				}
			}
        </script>
    </head>

    <body>
        <div class="header">
            <h1 class="title"><a href="../home/index" style="color: inherit; text-decoration: none">PDF To Word</a></h1>
            <div class="dropdown">
                <% String username = (String) session.getAttribute("username");
                if (username != null && !username.isEmpty()) { %>
                <button class="dropbtn" onclick="toggleDropdown()">
                    <%= username %>
                </button>
                <% } else { %>
                <button class="dropbtn" onclick="toggleDropdown()">User</button>
                <% } %>
                <div id="myDropdown" class="dropdown-content">
                    <a href="#history">History</a>
                    <a href="../account/LogoutServlet">Log out</a>
                </div>
            </div>
        </div>
        <div class="manage-container">
            <div class="manage-container__header">
            	<h2 class="manage-container__title">My document</h2>
            	<div class="manage-container__action">
            		<button class="delete-btn table-btn" onclick="showConfirmDeleteAll()">
                         <i class="fa-solid fa-trash table-btn-icon"></i>
                    </button>
                    <button class="download-btn table-btn" onclick="showConfirmDownloadAll()">
                         <i class="fa-solid fa-file-arrow-down table-btn-icon"></i>
                    </button>
            	</div>
            </div>
            <div class="manage-container__table">
                <table class="styled-table">
                    <thead class="manage-container__table-header">
                        <tr>
                        	<th><input id="select-all"  type="checkbox" class="select" onclick="slectedAll()"/></th>
                            <th style="width: 65%">Name File</th>
                            <th>Date created</th>
                            <th><i class="fa-solid fa-ellipsis-vertical"></i></th>
                        </tr>
                    </thead>
                    <tbody>
					<%
						List<FileStorageVM> listFile = (List<FileStorageVM>)request.getSession().getAttribute("listFile");
						if(listFile != null) {
							for(FileStorageVM File : listFile) {
					%>
                        <tr>
                        	<th><input name="select" type="checkbox" class="select" onclick="showAction()" value="<%= File.getId() %>"/></th>
                            <td><%= File.getFileName() %></td>  
                            <td><%= File.getCreationDate() %></td>    
                            <td class="table-td-action">
                                <button class="delete-btn table-btn" onclick="showConfirmDelete('<%= File.getId() %>')">
                                    <i class="fa-solid fa-trash table-btn-icon"></i>
                                </button>
                                <button class="download-btn table-btn" onclick="showConfirmDownload('<%= File.getId() %>')">
                                    <i class="fa-solid fa-file-arrow-down table-btn-icon"></i>
                                </button>
                            </td>
                        </tr>
                    <%
							}
						}
                    %>
                   </tbody>            
                </table>               
            </div>
        </div>
        
        <div class="popup-delete popup">
      		<form  action="../fileStorage/DeleteFile" method="POST" class="popup__form delete-form">
      			<input type="hidden" id="deleteId" name="deleteId" value="" />
        		<p>Are you sure to delete ?</p>
		        <div class="popup__action">
			        <button type="submit" class="btn success-btn load-target" name="delete">Confirm</button>
			        <button type="reset" class="btn success-btn load-target" onclick="unshowConfirmDelete()">Cancel</button>
		        </div>
		    </form>
      	</div>
      	
      	<div class="popup-download popup">
      		<form action="../fileStorage/DownloadFile" method="POST" class="popup__form download-form">
      			<input type="hidden" id="downloadId" name="downloadId" value="" />
        		<p>Are you sure to download ?</p>
		        <div class="popup__action">
			        <button type="submit" class="" name="download">Confirm</button>
			        <button type="reset" class="" onclick="unshowConfirmDownload()">Cancel</button>
		        </div>
		    </form>
      	</div>
      	
      	<div class="popup-delete popup delete-all">
      		<form action="../fileStorage/DeleteAllFiles" method="POST" class="popup__form delete-form">
      			<input type="hidden" id="deleteIdAll" name="deleteIdAll" value="" />
        		<p>Are you sure to delete ?</p>
		        <div class="popup__action">
			        <button type="submit" class="btn success-btn load-target" name="deleteAll">Confirm</button>
			        <button type="reset" class="btn success-btn load-target" onclick="unshowConfirmDeleteAll()">Cancel</button>
		        </div>
		    </form>
      	</div>
      	
      	<div class="popup-download popup download-all">
      		<form action="../fileStorage/DownloadAllFiles" method="POST" class="popup__form download-form">
      			<input type="hidden" id="downloadIdAll" name="downloadIdAll" value="" />
        		<p>Are you sure to download ?</p>
		        <div class="popup__action">
			        <button type="submit" class="" name="downloadAll">Confirm</button>
			        <button type="reset" class="" onclick="unshowConfirmDownloadAll()">Cancel</button>
		        </div>
		    </form>
      	</div>
    </body>
</html>
