
document.addEventListener("DOMContentLoaded", () => {
    const articles = document.querySelectorAll(".forum-card");

    articles.forEach(article => {
        const exploreBtn = article.querySelector(".forum-btn");
        const commentSection = article.querySelector(".comment-section");
        const form = article.querySelector(".comment-form");
        const commentList = article.querySelector(".comments-list");

        // Mostrar la sección de comentarios al hacer clic
        exploreBtn.addEventListener("click", () => {
            commentSection.style.display = commentSection.style.display === "none" ? "block" : "none";
        });

        // Manejar envío del formulario de comentarios
        form.addEventListener("submit", e => {
            e.preventDefault();
            const textarea = form.querySelector("textarea");
            const text = textarea.value.trim();

            if (text) {
                const newComment = document.createElement("div");
                newComment.classList.add("comment");
                newComment.innerHTML = `<p><strong>@anaR415:</strong> ${text}</p>`;
                commentList.appendChild(newComment);
                textarea.value = "";
            }
        });
    });
});

