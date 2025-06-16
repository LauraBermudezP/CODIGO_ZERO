document.addEventListener('DOMContentLoaded', () => {
    const popup = document.getElementById('popup-editor');
    const btnCrear = document.getElementById('create-post-btn');
    const form = document.getElementById('article-form');

    // Función para mostrar mensajes
    function mostrarSnackbar(mensaje) {
        const snackbar = document.getElementById('snackbar');
        snackbar.textContent = mensaje;
        snackbar.classList.add('show');
        setTimeout(() => {
            snackbar.classList.remove('show');
        }, 3000);
    }

    if (!popup || !btnCrear || !form) {
        console.error('Elementos clave no encontrados en el DOM');
        return; 
    }

    btnCrear.addEventListener('click', () => {
        popup.classList.remove('hidden');
    });

    popup.addEventListener('click', (e) => {
        if (e.target === popup) {
            popup.classList.add('hidden');
        }
    });

    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape') {
            popup.classList.add('hidden');
        }
    });

    form.addEventListener('submit', (e) => {
        e.preventDefault();

        const titulo = form.querySelector('#title').value.trim();
        const nivel = form.querySelector('#level').value;
        const categoria = form.querySelector('#category').value;
        const etiquetas = form.querySelector('#tags').value.trim();
        const contenido = quill.root.innerHTML.trim();

        // Validaciones
        if (!titulo) return mostrarSnackbar("🟥 El título es obligatorio");
        if (!etiquetas) return mostrarSnackbar("🟥 Debes ingresar etiquetas");
        if (!nivel) return mostrarSnackbar("🟥 Selecciona un nivel válido");
        if (!categoria) return mostrarSnackbar("🟥 Selecciona una categoría válida");
        if (!contenido || contenido === '<p><br></p>') return mostrarSnackbar("🟥 El contenido no puede estar vacío");

        console.log('Título:', titulo);
        console.log('Nivel:', nivel);
        console.log('Categoría:', categoria);
        console.log('Etiquetas:', etiquetas);
        console.log('Contenido:', contenido);

        mostrarSnackbar("✅ Publicación creada con éxito");

        // Resetear editor y formulario, y cerrar popup
        quill.setContents([]);
        form.reset();
        popup.classList.add('hidden');
    });
});

