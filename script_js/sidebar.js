const categoryLinks = document.querySelectorAll(".sidebar a");
const contentTypes = [
    { selector: ".forum-card", display: "flex" },
    { selector: ".event-card", display: "block" },
    { selector: ".resource-card", display: "block" }
];
const emptyMessage = document.getElementById("empty-message");

categoryLinks.forEach(link => {
    link.addEventListener("click", e => {
        e.preventDefault();

        categoryLinks.forEach(l => l.classList.remove("active"));
        link.classList.add("active");

        const selectedCategory = link.dataset.category;
        let visibleCount = 0;

        contentTypes.forEach(type => {
            const cards = document.querySelectorAll(type.selector);
            cards.forEach(card => {
                const match = selectedCategory === "all" || card.dataset.category === selectedCategory;
                card.style.display = match ? type.display : "none";
                if (match) visibleCount++;
            });
        });

        if (emptyMessage) {
            emptyMessage.style.display = (visibleCount === 0) ? "block" : "none";
        }
    });
});








