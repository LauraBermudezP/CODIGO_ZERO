document.addEventListener('DOMContentLoaded', function () {
    const users = [
        { id: 1, username: 'anaR415' },
        { id: 2, username: 'juanpa00' }
    ];

    const currentUser = users[0];

    const popup = document.getElementById('popup-editor');
    const form = document.getElementById('article-form');
    const publicationsContainer = document.getElementById('publication-list');
    const createBtn = document.getElementById('create-post-btn');

    const quill = new Quill('#editor', {
        theme: 'snow',
        modules: {
            toolbar: [
                [{ header: [1, 2, 3, false] }],
                ['bold', 'italic', 'underline', 'strike'],
                ['link', 'image'],
                [{ list: 'ordered' }, { list: 'bullet' }],
                [{ color: [] }, { background: [] }],
                ['clean']
            ]
        }
    });

    const savedPublications = JSON.parse(localStorage.getItem('forumPublications')) || [];
    savedPublications.slice().reverse().forEach(pub => displayPublication(pub));

    createBtn.addEventListener('click', () => {
        popup.classList.remove('hidden');
    });

    document.addEventListener('click', function (event) {
        const isClickInside = popup.contains(event.target) || createBtn.contains(event.target);
        if (!isClickInside) {
            popup.classList.add('hidden');
        }
    });

    form.addEventListener('submit', function (e) {
        e.preventDefault();

        const title = form.querySelector('#title').value.trim();
        const level = form.querySelector('#level').value;
        const category = form.querySelector('#category').value;
        const tags = form.querySelector('#tags').value.trim();
        const content = quill.root.innerHTML.trim();

        if (!title || level === '' || category === '' || !tags || !content) {
            alert('Todos los campos son requeridos.');
            return;
        }

        const newPublication = {
            title,
            level,
            category,
            tags,
            content,
            user: {
                id: currentUser.id,
                username: currentUser.username,
            }
        };

        const publications = JSON.parse(localStorage.getItem('forumPublications')) || [];
        publications.push(newPublication);
        localStorage.setItem('forumPublications', JSON.stringify(publications));

        displayPublication(newPublication);

        form.reset();
        quill.setText('');
        popup.classList.add('hidden');
    });

    function displayPublication(publication) {
        const pubDiv = document.createElement('div');
        pubDiv.classList.add('publication-item');

        pubDiv.innerHTML = `
            <h3>${publication.title}</h3>
            <p><em>Por: ${publication.user ? publication.user.username : 'Anónimo'}</em></p>
            <p><strong>Nivel:</strong> ${publication.level}</p>
            <p><strong>Categoría:</strong> ${publication.category}</p>
            <p><strong>Etiquetas:</strong> ${publication.tags || 'Sin etiquetas'}</p>
            <div class="publication-content">${publication.content}</div>

            <p class="details" style="display:none;">
                UN ESPACIO PARA AÑADIR DETALLES <br>
                ¿Por Qué la Confusión? Una Historia de Marketing y Coincidencias<br>
                La historia de los nombres es fascinante y explica gran parte de la confusión. Java fue
                desarrollado por Sun Microsystems (ahora parte de Oracle) a principios de los 90, con el
                objetivo de ser un lenguaje "escribe una vez, ejecuta en cualquier lugar" (WORA por sus siglas
                en inglés), ideal para la programación de aplicaciones robustas.
            </p>

            <div class="forum-actions">
                <button type="button" class="btn-like" aria-label="Like">
                    <svg class="icon-heart" xmlns="http://www.w3.org/2000/svg" width="20" height="20"
                        fill="none" stroke="#555" stroke-width="2" stroke-linecap="round"
                        stroke-linejoin="round" viewBox="0 0 24 24">
                        <path
                            d="M12 21C12 21 4 13.5 4 8.5C4 6 6 4 8.5 4C10 4 11 5 12 6C13 5 14 4 15.5 4C18 4 20 6 20 8.5C20 13.5 12 21 12 21Z" />
                    </svg>
                    <span class="like-count">0</span>
                </button>
                <button type="button" class="btn-report" aria-label="Report">
                    <svg class="icon-flag" xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="none"
                        stroke="#555" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                        viewBox="0 0 24 24">
                        <path d="M4 15V4a1 1 0 0 1 1-1h12l-3 5 3 5H5a1 1 0 0 1-1-1z" />
                        <line x1="4" y1="22" x2="4" y2="15" />
                    </svg>
                </button>
            </div>

            <button type="button" class="forum-btn">Explorar artículo</button>
        `;

        // Insertar publicación arriba
        publicationsContainer.insertBefore(pubDiv, publicationsContainer.firstChild);

        const exploreBtn = pubDiv.querySelector('.forum-btn');
        const details = pubDiv.querySelector('.details');

        exploreBtn.addEventListener('click', () => {
            const visible = details.style.display === 'block';
            details.style.display = visible ? 'none' : 'block';
            exploreBtn.textContent = visible ? 'Explorar artículo' : 'Cerrar artículo';
        });

        agregarListenersALosBotones(pubDiv);
    }

    function agregarListenersALosBotones(card) {
        const likeBtn = card.querySelector('.btn-like');
        const reportBtn = card.querySelector('.btn-report');

        likeBtn.addEventListener('click', () => {
            const heartIcon = likeBtn.querySelector('.icon-heart');
            const countSpan = likeBtn.querySelector('.like-count');
            let count = parseInt(countSpan.textContent);
            const liked = heartIcon.getAttribute('fill') === 'red';

            if (liked) {
                heartIcon.setAttribute('fill', 'none');
                heartIcon.setAttribute('stroke', '#555');
                count--;
            } else {
                heartIcon.setAttribute('fill', 'red');
                heartIcon.setAttribute('stroke', 'red');
                count++;
            }
            countSpan.textContent = count < 0 ? 0 : count;
        });

        reportBtn.addEventListener('click', () => {
            const flagIcon = reportBtn.querySelector('.icon-flag');
            const reported = flagIcon.getAttribute('fill') === 'red';

            if (reported) {
                flagIcon.setAttribute('fill', 'none');
                flagIcon.setAttribute('stroke', '#555');
            } else {
                flagIcon.setAttribute('fill', 'red');
                flagIcon.setAttribute('stroke', 'red');
                prompt('🟥🏴Gracias por reportar este contenido. Nuestro equipo de moderación se hará cargo pronto.\nPor favor, explíca brevemente el motivo de tu reporte:');
            }
        });
    }
});
