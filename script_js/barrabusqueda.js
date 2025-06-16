document.addEventListener('DOMContentLoaded', () => {
    const searchInput = document.getElementById('searchInput');
    const posts = document.querySelectorAll('.forum-card');
    const faqs = document.querySelectorAll('.faq');
    const eventCards = document.querySelectorAll('.event-card');
    const resources = document.querySelectorAll('.resource-card');
    const categoryLinks = document.querySelectorAll(".sidebar a");
    const emptyMessage = document.getElementById("empty-message");

    // BUSCADOR GENERAL
    searchInput.addEventListener('input', () => {
        const searchTerm = searchInput.value.toLowerCase().trim();
        let forumVisible = 0;
        let eventVisible = 0;
        let resourcesVisible = 0;
        let faqsVisible = 0;

        // Filtra posts por texto
        posts.forEach(post => {
            const title = post.querySelector('h3')?.textContent.toLowerCase() || '';
            const tags = post.querySelector('.tags')?.textContent.toLowerCase() || '';
            const description = post.querySelector('.description')?.textContent.toLowerCase() || '';
            const category = post.dataset.category?.toLowerCase() || '';
            const combinedPost = `${title} ${tags} ${description} ${category}`;

            const match = combinedPost.includes(searchTerm);
            post.style.display = match ? 'flex' : 'none';
            if (match) forumVisible++;
        });

        // RECURSOS ACADÉMICOS
        resources.forEach(resource => {
            const title = resource.querySelector('h3')?.textContent.toLowerCase() || '';
            const author = resource.querySelector('.author')?.textContent.toLowerCase() || '';
            const category = resource.querySelector('.category')?.textContent.toLowerCase() || '';
            const tags = resource.querySelector('.tags')?.textContent.toLowerCase() || '';
            const description = resource.querySelector('.description')?.textContent.toLowerCase() || '';
            const combinedResource = `${title} ${author} ${category} ${tags} ${description}`;

            const match = combinedResource.includes(searchTerm);
            resource.style.display = match ? 'block' : 'none';
            if (match) resourcesVisible++;
        });

        // PREGUNTAS FRECUENTES (FAQs)
        faqs.forEach(faq => {
            const question = faq.querySelector('.faq-question')?.textContent.toLowerCase() || '';
            const answer = faq.querySelector('.faq-answer')?.textContent.toLowerCase() || '';
            const combinedFaq = `${question} ${answer}`;

            const match = combinedFaq.includes(searchTerm);
            faq.style.display = match ? 'block' : 'none';
            if (match) faqsVisible++;
        });

        // EVENTOS
        eventCards.forEach(card => {
            const title = card.querySelector('h3')?.textContent.toLowerCase() || '';
            const category = card.dataset.category?.toLowerCase() || '';
            const tags = card.querySelector('.tags')?.textContent.toLowerCase() || '';
            const description = card.querySelector('.description')?.textContent.toLowerCase() || '';
            const date = card.querySelector('.date')?.textContent.toLowerCase() || '';
            const combinedEvent = `${title} ${category} ${tags} ${description} ${date}`;

            const match = combinedEvent.includes(searchTerm);
            card.style.display = match ? 'block' : 'none';
            if (match) eventVisible++;
        });

        // Mostrar mensaje solo si TODO está vacío
        if (emptyMessage) {
            const totalVisible = forumVisible + eventVisible + resourcesVisible + faqsVisible;
            emptyMessage.style.display = (totalVisible === 0) ? 'block' : 'none';
        }
    });

    // FILTRO POR CATEGORÍA (FORO + EVENTOS)
    categoryLinks.forEach(link => {
        link.addEventListener("click", e => {
            e.preventDefault();

            categoryLinks.forEach(link => link.classList.remove("active"));
            link.classList.add("active");

            const category = link.dataset.category;
            let visibleCount = 0;

            // FORO
            posts.forEach(card => {
                if (category === "all" || card.dataset.category === category) {
                    card.style.display = "flex";
                    visibleCount++;
                } else {
                    card.style.display = "none";
                }
            });

            // EVENTOS
            eventCards.forEach(card => {
                if (category === "all" || card.dataset.category === category) {
                    card.style.display = "block";
                    visibleCount++;
                } else {
                    card.style.display = "none";
                }
            });

            // RECURSOS
            resources.forEach(card => {
                if (category === "all" || card.dataset.category === category) {
                    card.style.display = "block";
                    visibleCount++;
                } else {
                    card.style.display = "none";
                }
            });

            // Mensaje si no hay tarjetas visibles
            if (emptyMessage) {
                emptyMessage.style.display = visibleCount === 0 ? "block" : "none";
            }
        });
    });
});



