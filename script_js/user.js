
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

            let editingPublicationId = null;

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

            function renderAllPublications() {
                publicationsContainer.innerHTML = ''; 
                let publications = JSON.parse(localStorage.getItem('forumPublications')) || [];

                publications.sort((a, b) => parseInt(b.id) - parseInt(a.id));

                publications.forEach(pub => {
                    const pubDiv = document.createElement('div');
                    pubDiv.classList.add('publication-item');

                    // Asegurarse de que la publicación tenga un ID único
                    if (!pub.id) {
                        pub.id = Date.now().toString() + Math.random().toString(36).substring(2, 9);

                        const savedPubs = JSON.parse(localStorage.getItem('forumPublications')) || [];
                        const index = savedPubs.findIndex(p => p.title === pub.title && p.user.username === pub.user.username && !p.id);
                        if (index !== -1) {
                            savedPubs[index].id = pub.id;
                            localStorage.setItem('forumPublications', JSON.stringify(savedPubs));
                        }
                    }

                    pubDiv.innerHTML = `
                    <article class="forum-card" data-category="${pub.category.toLowerCase()}" data-id="${pub.id}">
                        <h3>${pub.title}</h3>
                        <p class="author">
                            <em>03/06/2025 - por ${pub.user ? pub.user.username : 'Anónimo'}</em>
                            ${currentUser && currentUser.username === pub.user.username ? `
                                <button type="button" class="btn-edit-card" aria-label="Editar publicación">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-edit-2">
                                        <path d="M17 3a2.828 2.828 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5L17 3z"></path>
                                    </svg>
                                </button>
                            ` : ''}
                        </p>

                        <div class="caracteristicas">
                            <span class="category">${pub.category}</span>
                            <span class="level">${pub.level}</span>
                        </div>

                        <div class="tags">${pub.tags || 'Sin etiquetas'}</div>

                        <p class="description">
                            ${pub.intro.slice(0, 300)}... </p>

                        <div class="details" style="display:none;">
                            <span style="text-align: center; margin-left: 280px;">┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄</span><br>
                            ${pub.content || '<em>Sin detalles adicionales.</em>'}
                        </div>

                        <div class="forum-actions">
                            <button type="button" class="btn-like" aria-label="Like">
                                <svg class="icon-heart" xmlns="http://www.w3.org/2000/svg" width="20" height="20"
                                    fill="none" stroke="#555" stroke-width="2" stroke-linecap="round"
                                    stroke-linejoin="round" viewBox="0 0 24 24">
                                    <path d="M12 21C12 21 4 13.5 4 8.5C4 6 6 4 8.5 4C10 4 11 5 12 6C13 5 14 4 15.5 4C18 4 20 6 20 8.5C20 13.5 12 21 12 21Z" />
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
                    </article>
                `;

                    publicationsContainer.appendChild(pubDiv);

                    const exploreBtn = pubDiv.querySelector('.forum-btn');
                    const details = pubDiv.querySelector('.details');

                    exploreBtn.addEventListener('click', () => {
                        const visible = details.style.display === 'block';
                        details.style.display = visible ? 'none' : 'block';
                        exploreBtn.textContent = visible ? 'Explorar artículo' : 'Cerrar artículo';
                    });

                    agregarListenersALosBotones(pubDiv);
                    agregarListenerEdicion(pubDiv, pub);
                });
            }

            function agregarListenersALosBotones(card) {
                const likeBtn = card.querySelector('.btn-like');
                const reportBtn = card.querySelector('.btn-report');

                if (likeBtn) {
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
                }

                if (reportBtn) {
                    reportBtn.addEventListener('click', () => {
                        const flagIcon = reportBtn.querySelector('.icon-flag');
                        const reported = flagIcon.getAttribute('fill') === 'red';

                        if (reported) {
                            flagIcon.setAttribute('fill', 'none');
                            flagIcon.setAttribute('stroke', '#555');
                        } else {
                            flagIcon.setAttribute('fill', 'red');
                            flagIcon.setAttribute('stroke', 'red');
                            prompt('🟥🏴Gracias por reportar este contenido. Nuestro equipo de moderación se hará cargo pronto.\nPor favor, explica brevemente el motivo de tu reporte:');
                        }
                    });
                }
            }

            function agregarListenerEdicion(cardElement, publicationData) {
                const editBtn = cardElement.querySelector('.btn-edit-card');

                if (editBtn) { 
                    editBtn.addEventListener('click', () => {
                        // Mostrar el popup de edición
                        popup.classList.remove('hidden');

                        // Rellenar el formulario con los datos de la publicación
                        form.querySelector('#title').value = publicationData.title;
                        form.querySelector('#level').value = publicationData.level;
                        form.querySelector('#category').value = publicationData.category;
                        form.querySelector('#tags').value = publicationData.tags;
                        form.querySelector('#intro').value = publicationData.intro;
                        quill.root.innerHTML = publicationData.content;

                        // Guardar el ID de la publicación que se está editando
                        editingPublicationId = publicationData.id;

                        form.querySelector('button[type="submit"]').textContent = 'Guardar Cambios';
                    });
                }
            }

            if (createBtn) {
                createBtn.addEventListener('click', () => {
                    popup.classList.remove('hidden');
                    form.reset(); // Limpia el formulario
                    quill.setText(''); // Limpia el editor Quill
                    editingPublicationId = null;
                    form.querySelector('button[type="submit"]').textContent = 'Publicar'; // Vuelve al texto original
                });
            }

            form.addEventListener('submit', function (e) {
                e.preventDefault();

                const title = form.querySelector('#title').value.trim();
                const level = form.querySelector('#level').value;
                const category = form.querySelector('#category').value;
                const tags = form.querySelector('#tags').value.trim();
                const intro = form.querySelector('#intro').value.trim();
                const content = quill.root.innerHTML.trim();

                if (!title || level === '' || category === '' || !tags || !intro || !content) {
                    alert('Todos los campos son requeridos.');
                    return;
                }

                let publications = JSON.parse(localStorage.getItem('forumPublications')) || [];

                if (editingPublicationId) {
                    const index = publications.findIndex(pub => pub.id === editingPublicationId);
                    if (index !== -1) {
                        // Actualiza solo los campos editables
                        publications[index].title = title;
                        publications[index].level = level;
                        publications[index].category = category;
                        publications[index].tags = tags;
                        publications[index].intro = intro;
                        publications[index].content = content;
                    }
                    editingPublicationId = null; 
                } else {
                    // modo creación
                    const newPublication = {
                        id: Date.now().toString() + Math.random().toString(36).substring(2, 9), 
                        title,
                        level,
                        category,
                        tags,
                        intro,
                        content,
                        user: {
                            id: currentUser.id,
                            username: currentUser.username,
                        }
                    };
                    publications.push(newPublication);
                }

                localStorage.setItem('forumPublications', JSON.stringify(publications));

                renderAllPublications();

                form.reset();
                quill.setText('');
                popup.classList.add('hidden');
                form.querySelector('button[type="submit"]').textContent = 'Publicar';
            });
            const closePopupBtn = popup.querySelector('.close-popup');
            if (closePopupBtn) {
                closePopupBtn.addEventListener('click', () => {
                    popup.classList.add('hidden');
                    form.reset();
                    quill.setText('');
                    editingPublicationId = null; 
                    form.querySelector('button[type="submit"]').textContent = 'Publicar'; 
                });
            }

            renderAllPublications();
        });
