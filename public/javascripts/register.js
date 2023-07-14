function register1() {

    //let name = document.getElementById("name");
    //let surname = document.getElementById("surname");
    let username = document.getElementById("username");
    let password = document.getElementById("password");
    let email = document.getElementById("email");
    let error = document.getElementById("login-error");

    function resetFields(message) {
        error.innerHTML = message;
        error.style.visibility = "visible";
        username.value = "";
        email.value = "";
        password.value = "";
        username.focus();

        console.log(message)
    }


    fetch("/register", {
        method: "POST",
        body: JSON.stringify({username: username.value, password: password.value, email: email.value}),
        headers: {'Content-Type': 'application/json'}
    })
        .then(response => {
            if (response.status === 400) {
                resetFields("Nutzer existiert bereits!");

            } else if (response.status === 406) {
                resetFields("Email wird bereits verwendet!");
            } else if (response.status === 200) {
                location.href = "/login";
            } else {
                resetFields("Felder sind leer oder etwas anderes ist schief gelaufen!");
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