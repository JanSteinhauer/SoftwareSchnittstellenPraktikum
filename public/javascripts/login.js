window.onload = init;

function init() {
    document.getElementById("register-btn-font-only").onclick = function () {
        location.href = "register";
    };

    let password = document.getElementById("password");

    password.addEventListener("keypress", function (event) {
        if (event.key === "Enter") {
            event.preventDefault();
            document.getElementById("login-btn").click();
        }
    });
}

function login() {
    let username = document.getElementById("username");
    let password = document.getElementById("password");

    fetch("/login", {
        method: "POST",
        body: JSON.stringify({username: username.value, password: password.value}),
        headers: {'Content-Type': 'application/json'}
    })
        .then(response => {
            if (response.status >= 400) {
                let error = document.getElementById("login-error");
                error.style.visibility = "visible";

                username.value = "";
                password.value = "";
                username.focus();
            } else if (response.status === 200) {
                sessionStorage.setItem("username", username.value);
                location.href = "/main";
            }
        })
        .catch(error => {
            console.log("Error: ", error);
        });
}


const togglePassword = document.querySelector('#togglePassword');
const password = document.querySelector('#password');

togglePassword.addEventListener('click', function (e) {
    // toggle the type attribute
    const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
    password.setAttribute('type', type);
    // toggle the eye slash icon
    this.classList.toggle('fa-eye-slash');
});
