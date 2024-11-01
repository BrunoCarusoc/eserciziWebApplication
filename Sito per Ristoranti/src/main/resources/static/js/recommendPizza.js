console.log("Caricato con Successo");

document.getElementById("recommendPizzaForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const pizzasTable = document.getElementById("specialPizzaTable");
    const pizzaName = document.getElementById("inputPizzaName").value;
    const pizzaPrice = document.getElementById("inputPizzaPrice").value;
    const pizzaIngs = document.getElementById("inputPizzaIngredients").value;

    document.getElementById("inputPizzaName").value = "";
    document.getElementById("inputPizzaPrice").value = "";
    document.getElementById("inputPizzaIngredients").value = "";

    addTo(pizzasTable, pizzaName, pizzaIngs, pizzaPrice);
});

function addTo(pizzasTable, pizzaName, pizzaIngs, pizzaPrice) {

    const newSpecialPizzaRow= pizzasTable.insertRow();
    newSpecialPizzaRow.classList.add("customer-recommended");

    const pizzaNameCell = newSpecialPizzaRow.insertCell(0);
    const pizzaIngsCell = newSpecialPizzaRow.insertCell(1);
    const pizzaPriceCell = newSpecialPizzaRow.insertCell(2);

    pizzaNameCell.textContent = pizzaName;
    pizzaIngsCell.textContent = pizzaIngs;
    pizzaPriceCell.textContent = "€" + pizzaPrice;

    console.log("Aggiunta una nuova riga per la pizza " + pizzaName);
}