document.addEventListener('DOMContentLoaded', () => {
    const userBtn = document.getElementById('abrir-login');
    const menu = document.getElementById('menu-user');

    if (userBtn && menu) {
        userBtn.addEventListener('click', () => {
            menu.style.display = menu.style.display === 'flex' ? 'none' : 'flex';
        });

        document.addEventListener('click', (e) => {
            if (!userBtn.contains(e.target) && !menu.contains(e.target)) {
                menu.style.display = 'none';
            }
        });
    }

    // Selecciona el contenedor principal para las secciones
    const main = document.getElementById('main-content');

    let reportsData = JSON.parse(localStorage.getItem('reportsData')) || [
        {
            id: 'rep001',
            contentId: 'post123',
            contentType: 'post',
            reason: 'Contenido inapropiado: lenguaje ofensivo',
            reporter: '@andreaLondoño03',
            date: '03 Jun 2025',
            status: 'pending',
            details: 'El post "MySql o SqlServer | ¿Cuál preferís tú?" contiene palabras altisonantes en la sección de comentarios. Admins, por favor revisar.'
        },
        {
            id: 'rep002',
            contentId: 'comm456',
            contentType: 'comment',
            reason: 'Spam o publicidad',
            reporter: '@juanpa00',
            date: '02 Jun 2025',
            status: 'pending',
            details: 'El comentario de "@miguelitoToTo" en el post "Tips para programadores junior" es una promoción de un producto externo.'
        },
        {
            id: 'rep003',
            contentId: 'rec789',
            contentType: 'recurso',
            reason: 'No relevante para el blog',
            reporter: '@Majo_Ypz',
            date: '01 Jun 2025',
            status: 'pending',
            details: 'El recurso "Aprende a hacer postre de Tiramisu" no tiene relación alguna con la temática de programación o tecnología del blog. Puede ser spam o bite.'
        }
    ];

    // Función para guardar los reportes en localStorage
    const saveReports = () => {
        localStorage.setItem('reportsData', JSON.stringify(reportsData));
    };

    // Función para actualizar el estado de un reporte
    function updateReportStatus(id, newStatus) {
        const reportIndex = reportsData.findIndex(r => r.id === id);
        if (reportIndex !== -1) {
            reportsData[reportIndex].status = newStatus;
            saveReports();
            loadReports(); // Recarga la lista para reflejar el cambio
        }
    }

    // Función para eliminar un reporte
    function deleteReport(id) {
        reportsData = reportsData.filter(r => r.id !== id);
        saveReports();
        loadReports();
    }

    function handleReportButtonClick(e) {
        const target = e.target;
        const reportCard = target.closest('.report-card');

        if (!reportCard) return;

        const reportId = reportCard.getAttribute('data-report-id');

        if (target.classList.contains('mark-reviewed-btn')) {
            if (!target.disabled) {
                updateReportStatus(reportId, 'reviewed');
            }
        } else if (target.classList.contains('take-action-btn')) {
            if (!target.disabled) {
                alert(`📝 Acción tomada para reporte ${reportId}. Esto implicaría eliminar el contenido, bloquear al usuario, etc.`);
                updateReportStatus(reportId, 'action_taken');
            }
        } else if (target.classList.contains('delete-report-btn')) {
            if (confirm(`🔴 ¿Estás seguro de que quieres eliminar el reporte ${reportId}? Esta acción no se puede deshacer.`)) {
                deleteReport(reportId);
            }
        }
    }

    // --- FUNCIONES DE CARGA DE SECCIONES ---

    function loadPosts() {
        const postsData = [
            { title: "Aprendiendo a implementar Chart.js", date: "04 Jun 2025", author: "@juanDaPPT", content: "Un resumen de las últimas mejoras y características añadidas al blog." },
            { title: "¿Por qué deberías implementar ya las metodologías agiles en programación?", date: "30 May 2025", author: "@MariGarci4", content: "Consejos esenciales para mejorar tu productividad y calidad de código como desarrollador." },
            { title: "Resumen del evento DevFest: Lo mejor y Lo peor. ¿Volvería a ir?", date: "29 May 2025", author: "@carlitosRuizzz", content: "Lo más destacado del DevFest de este año, incluyendo charlas y talleres innovadores." },
        ];

        const postsListContainer = document.getElementById('posts-list');

        if (postsListContainer) {
            postsListContainer.innerHTML = '';

            postsData.forEach(post => {
                const li = document.createElement('li');
                li.classList.add('post-card');

                li.innerHTML = `
                    <div class="card-header">
                        <h3>${post.title}</h3>
                        <span class="post-date">${post.date}</span>
                    </div>
                    <div class="card-body">
                        <p>${post.content ? post.content.substring(0, 100) + '...' : 'Sin contenido.'}</p>
                        <span class="post-author">Por: ${post.author}</span>
                    </div>
                    <div class="card-actions">
                        <button class="edit-post-btn">Editar</button>
                        <button class="delete-post-btn">Eliminar</button>
                    </div>
                `;
                postsListContainer.appendChild(li);
            });

            postsListContainer.addEventListener('click', (e) => {
                const target = e.target;
                const postCard = target.closest('.post-card');
                if (!postCard) return;

                const postTitle = postCard.querySelector('h3').textContent; // Obtener el título para el mensaje

                if (target.classList.contains('edit-post-btn')) {
                    alert('Editar post: ' + postTitle);
                    // Aquí iría la lógica real de edición
                } else if (target.classList.contains('delete-post-btn')) {
                    if (confirm('🔴👀 Eliminar post: ' + postTitle) === true) {
                        postCard.remove();
                    }
                }
            });

        } else {
            console.error("El contenedor '#posts-list' no se encontró en el DOM.");
        }
    }

    function loadReports() {
        const reportsListContainer = document.getElementById('reports-list');

        if (!reportsListContainer) {
            console.error("El contenedor '#reports-list' no se encontró en el DOM.");
            return;
        }

        reportsListContainer.innerHTML = ''; // Limpia el contenido previo

        if (reportsData.length === 0) {
            reportsListContainer.innerHTML = '<p class="no-reports-message">¡No hay reportes pendientes en este momento!</p>';
            return;
        } else {
            reportsData.forEach(report => {
                const li = document.createElement('li');
                li.classList.add('report-card');
                li.setAttribute('data-report-id', report.id);

                let statusClass = '';
                if (report.status === 'pending') {
                    statusClass = 'status-pending';
                } else if (report.status === 'reviewed') {
                    statusClass = 'status-reviewed';
                } else if (report.status === 'action_taken') {
                    statusClass = 'status-action-taken';
                }

                li.innerHTML = `
                <div class="card-header">
                    <h3>Reporte #${report.id} - Tipo: ${report.contentType.toUpperCase()}</h3>
                    <span class="report-date">${report.date}</span>
                </div>
                <div class="card-body">
                    <p><strong>Razón:</strong> ${report.reason}</p>
                    <p><strong>Detalles:</strong> ${report.details}</p>
                    <p><strong>Reportado por:</strong> ${report.reporter}</p>
                    <p><strong>Estado:</strong> <span class="report-status ${statusClass}">${report.status.replace(/_/g, ' ')}</span></p>
                </div>
                <div class="card-actions">
                    <button class="mark-reviewed-btn" ${report.status !== 'pending' ? 'disabled' : ''}>Marcar como Revisado</button>
                    <button class="take-action-btn" ${report.status === 'action_taken' ? 'disabled' : ''}>Tomar Acción</button>
                    <button class="delete-report-btn">Eliminar Reporte</button>
                </div>
            `;
                reportsListContainer.appendChild(li);
            });
        }



        // --- DELEGACIÓN DE EVENTOS PARA BOTONES DE REPORTE ---
        // Este listener se adjunta una única vez al contenedor padre.
        // Lo removemos y volvemos a añadir para asegurar que no se duplique
        // si loadReports es llamado varias veces (aunque la delegación ya lo maneja mejor).
        // Una bandera simple o un removeEventListener previo ayudan a controlar esto.
        if (!reportsListContainer.__reportButtonListener) { // Usamos una propiedad personalizada para marcar el listener
            reportsListContainer.addEventListener('click', handleReportButtonClick);
            reportsListContainer.__reportButtonListener = true;
        }
    }

    // CONTROL DE NAVEGACIÓN DE SECCIONES (sidebar-admin)
    document.querySelectorAll('.sidebar-admin [data-section]').forEach(item => {
        item.addEventListener('click', e => {
            e.preventDefault();
            const section = e.target.dataset.section;
            loadSection(section);
        });
    });


    // Función principal para cargar el contenido de la sección
    function loadSection(section) {
        main.innerHTML = '';

        switch (section) {
            case "posts":
                main.innerHTML = `
                    <div class="main-header"><h2>Gestión de Entradas Nuevas</h2></div>
                    <ul id="posts-list" class="posts-container"></ul>
                `;
                loadPosts();
                break;

            case "dashboard":
                window.location.href = '/html_css/indexA.html';
                break;

            case "users":
                window.location.href = '/html_css/indexU.html';
                break;

            case "public":
                window.location.href = '/html_css/index.html';
                break;

            case "reports":
                main.innerHTML = `
                    <div class="main-header"><h2>Gestión de Reportes de Contenido</h2></div>
                    <ul id="reports-list" class="reports-container"></ul> `;
                loadReports();
                break;

            default:
                main.innerHTML = `<p class="no-reports-message">Selecciona una sección del menú de administración.</p>`;
                break;
        }
    }

    // Funciones para gráficos y posts recientes (probablemente en el dashboard)
    function updateRecentPosts() {
        const posts = [
            { title: "Aprendiendo a implementar Chart.js", date: "04 Jun 2025", author: "@juanDaPPT" },
            { title: "¿Por qué deberías implementar ya las metodologías agiles en programación?", date: "30 May 2025", author: "@MariGarci4" },
            { title: "Resumen del evento DevFest: Lo mejor y Lo peor. ¿Volvería a ir?", date: "29 May 2025", author: "@carlitosRuizzz" },
        ];

        const list = document.getElementById('recent-posts-list');
        if (list) {
            list.innerHTML = '';
            posts.forEach(post => {
                const li = document.createElement('li');
                li.innerHTML = `
                    <div class="post-content">
                        <span class="post-title">${post.title}</span>
                        <span class="post-date">${post.date}</span>
                    </div>
                    <div class="post-author">
                        <span>Por: </span><span class="author-name">${post.author}</span>
                    </div>
                `;
                list.appendChild(li);
            });
        }
    }
    updateRecentPosts();

    const barCtx = document.getElementById('barChart');
    if (barCtx) {
        new Chart(barCtx.getContext('2d'), {
            type: 'bar',
            data: {
                labels: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio'],
                datasets: [{
                    label: 'Entradas Creadas por Mes',
                    data: [3, 5, 2, 8, 6, 4],
                    backgroundColor: [
                        'rgba(54, 162, 235, 0.9)', 'rgba(255, 99, 132, 0.9)', 'rgba(75, 192, 192, 0.9)',
                        'rgba(255, 159, 64, 0.9)', 'rgba(153, 102, 255, 0.9)', 'rgba(201, 203, 207, 0.9)'
                    ],
                    borderColor: [
                        'rgba(54, 162, 235, 1)', 'rgba(255, 99, 132, 1)', 'rgba(75, 192, 192, 1)',
                        'rgba(255, 159, 64, 1)', 'rgba(153, 102, 255, 1)', 'rgba(201, 203, 207, 1)'
                    ],
                    borderWidth: 1,
                    borderRadius: 8,
                    hoverBackgroundColor: 'rgba(52, 73, 94, 1)',
                    hoverBorderColor: 'rgba(52, 73, 94, 1)',
                    hoverBorderWidth: 2
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    title: {
                        display: true,
                        text: 'Volumen de Entradas por Mes',
                        font: { size: 18, weight: 'bold' },
                        color: '#333'
                    },
                    legend: { display: false },
                    tooltip: {
                        backgroundColor: 'rgba(0, 0, 0, 0.85)',
                        titleColor: '#fff',
                        bodyColor: '#fff',
                        padding: 12,
                        cornerRadius: 6,
                        displayColors: false,
                        bodyFont: { size: 14 }
                    }
                },
                scales: {
                    x: { grid: { display: false }, ticks: { color: '#666', font: { size: 12 } } },
                    y: {
                        beginAtZero: true,
                        ticks: { stepSize: 1, color: '#666', font: { size: 12 } },
                        grid: { color: 'rgba(200, 200, 200, 0.2)', drawBorder: false }
                    }
                },
                animation: { duration: 900, easing: 'easeOutCubic', onComplete: null }
            }
        });
    }

    const pieCtx = document.getElementById('pieChart');
    if (pieCtx) {
        new Chart(pieCtx.getContext('2d'), {
            type: 'pie',
            data: {
                labels: ['Artículos', 'Recursos', 'Opiniones', 'Eventos'],
                datasets: [{
                    label: 'Cantidad de Publicaciones',
                    data: [6, 3, 4, 2],
                    backgroundColor: ['#4CAF50', '#2196F3', '#FFC107', '#F44336'],
                    borderColor: '#fff',
                    borderWidth: 2,
                    hoverOffset: 10
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    title: {
                        display: true,
                        text: 'Distribución de Contenido por Categoría',
                        font: { size: 18, weight: 'bold' },
                        color: '#333'
                    },
                    legend: {
                        position: 'right',
                        labels: { font: { size: 12 }, color: '#444', boxWidth: 20, padding: 15 }
                    },
                    tooltip: {
                        backgroundColor: 'rgba(0, 0, 0, 0.85)',
                        titleColor: '#fff',
                        bodyColor: '#fff',
                        padding: 12,
                        cornerRadius: 6,
                        bodyFont: { size: 14 },
                        callbacks: {
                            label: function (context) {
                                let label = context.label || '';
                                if (label) { label += ': '; }
                                if (context.parsed !== null) { label += context.parsed + ' unidades'; }
                                return label;
                            }
                        }
                    }
                },
                animation: { animateRotate: true, animateScale: true, duration: 1000, easing: 'easeOutBounce', onComplete: null }
            }
        });
    }
});
