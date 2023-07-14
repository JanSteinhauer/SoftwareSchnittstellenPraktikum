let username = sessionStorage.getItem("username");
document.getElementById("username").innerHTML = username;

    let email = document.getElementById("email");

    function getMail() {
    let username = sessionStorage.getItem("username");
    fetch("/profile", {
        method: "POST",
        body: JSON.stringify({username: username.value}),
        headers: {'Content-Type': 'application/json'}
    })
        .then(response => {

            })
            .then(data => {
            console.log(data);
             })
            }