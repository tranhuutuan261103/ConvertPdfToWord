function showSignIn() {
  document.querySelector(".popup").classList.add("active");
}

function unshowSignIn() {
  document.querySelector(".popup").classList.remove("active");
}

function showSignUp() {
  document.querySelector(".popup-sign-up").classList.add("active");
  unshowSignIn();
}

function unshowSignUp() {
  document.querySelector(".popup-sign-up").classList.remove("active");
}

// Hàm để chuyển đổi trạng thái hiển thị của dropdown
function toggleDropdown() {
  document.getElementById("myDropdown").classList.toggle("show");
}

// Đóng dropdown nếu click ra ngoài
window.onclick = function (event) {
  if (!event.target.matches(".dropbtn")) {
    var dropdowns = document.getElementsByClassName("dropdown-content");
    for (var i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains("show")) {
        openDropdown.classList.remove("show");
      }
    }
  }
};

function checkPasswordMatch() {
  var password = document.getElementById("new-password").value;
  var confirmPassword = document.getElementById("comfirm-password").value;
  if (password !== confirmPassword) {
    document
      .getElementById("comfirm-password")
      .setCustomValidity("Passwords do not match.");
  } else {
    document.getElementById("comfirm-password").setCustomValidity("");
  }
}

function validateEmail() {
  var email = document.getElementById("new-email").value;
  var regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  if (!regex.test(email)) {
    document
      .getElementById("new-email")
      .setCustomValidity("Please enter a valid email address.");
  } else {
    document.getElementById("new-email").setCustomValidity("");
  }
}
