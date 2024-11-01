// console.log("Caricato con Successo");

const reviews = [
    {
        author: "Maria R.",
        rating: 5,
        text: "Finalmente una pizzeria che sa cosa significa vera tradizione italiana! La pizza è leggera, con ingredienti freschissimi e sapori autentici. Ambiente accogliente e perfetto per una serata in famiglia. Torneremo sicuramente!"
    },
    {
        author: "Gianni L.",
        rating: 5,
        text: "La Diavola è fantastica, con il giusto tocco di piccante e una mozzarella che si scioglie in bocca! Personale gentile e disponibile, e un’atmosfera che ti fa sentire subito a casa."
    },
    {
        author: "Elena M.",
        rating: 5,
        text: "HomeTown è diventato il mio posto preferito per la pizza. Ingredienti di qualità e sapori unici come nella Pistacchio e Mortadella, che è un capolavoro!"
    },
    {
        author: "Marco D.",
        rating: 5,
        text: "Una vera scoperta! HomeTown è il posto giusto per chi cerca una pizza autentica, preparata con attenzione e passione. Da provare assolutamente, e il tiramisù è la ciliegina sulla torta!"
    },
    {
        author: "Luca B.",
        rating: 5,
        text: "Un’esperienza unica! La pizza è eccezionale, ma anche l’atmosfera è fantastica. Ottimo per una serata con gli amici. Consigliatissimo!"
    },
    {
        author: "Sara P.",
        rating: 4,
        text: "Le pizze sono davvero buonissime, e si sente che gli ingredienti sono freschi. Servizio un po’ lento, ma ne vale la pena. Torneremo presto!"
    },
    {
        author: "Alessio C.",
        rating: 5,
        text: "Che dire, la pizza Pistacchio e Mortadella è semplicemente incredibile. Sapore e qualità al top. Perfetto per una serata speciale."
    },
    {
        author: "Giulia F.",
        rating: 5,
        text: "Ottima esperienza! Personale gentile e ambiente accogliente. La pizza è tra le migliori che abbia mai mangiato, e il tiramisù è sublime!"
    },
    {
        author: "Francesco R.",
        rating: 4,
        text: "Locale molto carino e pizza buonissima, con ingredienti freschi e ben abbinati. Prezzi giusti per la qualità. Una scoperta!"
    }
];

function displayReviews() {
    const reviewsContainer = document.getElementById("reviewSectionDisplay");
    // console.log("1. Preso il container");

    reviews.forEach(review => {
        // console.log("Inserimento della Review di " + review.author + " ------ ")

        const reviewCard = document.createElement("div");
        reviewCard.classList.add("card", "review-card");

        // console.log("Creo HTML di supporto")
        reviewCard.innerHTML = `
                <div class="card-body">
                    <h5 class="card-title">${review.author}</h5>
                    <h6 class="card-subtitle mb-2 text-muted mb-3">${'⭐'.repeat(review.rating)}</h6>
                    <p class="card-text">${review.text}</p>
                </div>
            `;

        // console.log(" --- Aggiungo la Recensione di " + review.author);
        reviewsContainer.appendChild(reviewCard);

    })


}

displayReviews();