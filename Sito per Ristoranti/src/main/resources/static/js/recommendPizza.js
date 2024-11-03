// console.log("Caricato con Successo");

document.getElementById("recommendPizzaForm").addEventListener("submit", function (event) {
    event.preventDefault();

    if (!validateRecommendForm()) { return; }

    const pizzasTable = document.getElementById("specialPizzaTable");
    const pizzaName = document.getElementById("inputPizzaName").value;
    const pizzaPrice = document.getElementById("inputPizzaPrice").value;
    const pizzaIngs = document.getElementById("inputPizzaIngredients").value;

    document.getElementById("inputPizzaName").value = "";
    document.getElementById("inputPizzaPrice").value = "";
    document.getElementById("inputPizzaIngredients").value = "";

    addTo(pizzasTable, pizzaName, pizzaIngs, pizzaPrice);
});

function validateRecommendForm() {

    let pizzaName = document.getElementById("inputPizzaName").value;
    if (pizzaName.length < 3 ) {
        alert("Inserire un Nome Valido. Si possono usare lettere maiuscole e minuscole, spazi, ma non numeri o caratteri speciali");
        return false;
    }

    let price = document.getElementById("inputPizzaPrice").value;
    if (!price.match(/^\d+(,\d{2}){0,1}$/) ) {
        alert("Inserire il Prezzo in un Formato Valido: solo numeri e, in caso si voglia aggiungere anche i centesimi, due numeri dopo una virgola");
        return false;
    }

    let ings = document.getElementById("inputPizzaIngredients").value;
    if (ings.length < 3) {
        alert("Suggerisci quali ingredienti metteresti sulla tua pizza!");
        return false;
    }

    return true;
}

function addTo(pizzasTable, pizzaName, pizzaIngs, pizzaPrice) {

    const newSpecialPizzaRow= pizzasTable.insertRow();
    newSpecialPizzaRow.classList.add("customer-recommended");

    const pizzaNameCell = newSpecialPizzaRow.insertCell(0);
    const pizzaIngsCell = newSpecialPizzaRow.insertCell(1);
    const pizzaPriceCell = newSpecialPizzaRow.insertCell(2);

    const deleteButton = document.createElement("button");
    deleteButton.classList.add("btn", "color-default", "delete-button", "material-symbols-outlined", "icon-personal");
    deleteButton.textContent = "do_not_disturb_on";
    deleteButton.addEventListener("click", function() {
        const row = deleteButton.closest("tr");
        row.parentNode.removeChild(row);
    });

    pizzaNameCell.textContent = pizzaName;
    pizzaNameCell.appendChild(deleteButton);
    pizzaIngsCell.textContent = pizzaIngs;
    pizzaPriceCell.textContent = "€" + pizzaPrice;

    console.log("Aggiunta una nuova riga per la pizza " + pizzaName);
}
