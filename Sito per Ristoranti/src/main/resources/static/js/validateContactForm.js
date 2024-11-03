console.log("Caricatio con Successo");

function validateContactForm(event) {
    event.preventDefault();

    let firstName = document.getElementById("inputFirstName").value;
    if (firstName.length < 3 ) {
        alert("Inserire un Nome Valido. Si possono usare lettere maiuscole e minuscole, spazi, ma non numeri o caratteri speciali")
        return;
    }

    let lastName = document.getElementById("inputLastName").value;
    if (lastName.length < 3 ) {
        alert("Inserire un Cognome Valido. Si possono usare lettere maiuscole e minuscole, spazi, ma non numeri o caratteri speciali");
        return;
    }

    let address = document.getElementById("inputAddress").value;
    if (address.length < 3 || !address.match(/\d+/) ) {
        alert("Inserire un Indirizzo Valido. Si possono usare lettere maiuscole e minuscole, spazi, numeri e virgole");
        return;
    }

    let city = document.getElementById("inputCity").value;
    if (city.length < 3 ) {
        alert("Inserire un Nome di Città o Paese Valido. Si possono usare lettere maiuscole e minuscole, spazi, apostrofi");
        return;
    }

    let message = document.getElementById("messageTextArea").value;
    if (message.length < 1) {
        alert("Lasciaci un messaggio per poter valutare la tua esperienza!");
        return;
    }

    let form = document.getElementById("contactForm");
    form.submit();
}

form = document.getElementById("contactForm");
form.addEventListener("submit", validateContactForm);
