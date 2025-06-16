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

    const usuario = JSON.parse(localStorage.getItem('usuarioActivo'));

    // Validar si hay una sesión activa
    if (!usuario) {
        alert("🟥 No has iniciado sesión. Serás redirigid@ al inicio.");
        window.location.href = "/html_css/index.html"; // redirige al inicio si no hay usuario
        return; 
    }

    const tituloPerfil = document.querySelector('.perfil-usuario h2');
    const fotoPerfil = document.getElementById('foto-perfil');

    // Mostrar datos del usuario
    if (document.getElementById('nombre-usuario')) {
        document.getElementById('nombre-usuario').textContent = usuario.nombre || 'Nombre no disponible';
    }
    if (document.getElementById('username-usuario')) {
        document.getElementById('username-usuario').textContent = usuario.username || 'Sin username';
    }
    if (document.getElementById('email-usuario')) {
        document.getElementById('email-usuario').textContent = usuario.email || 'Sin correo';
    }
    if (document.getElementById('biografia-usuario')) {
        document.getElementById('biografia-usuario').textContent = usuario.biografia || 'Sin biografía';
    }
    if (document.getElementById('fecha-registro')) {
        document.getElementById('fecha-registro').textContent = usuario.fechaRegistro || 'Sin fecha';
    }

    if (usuario.username === "Lau04" && tituloPerfil) {
        if (!tituloPerfil.querySelector('.admin-tag')) {
            const adminTag = document.createElement('span');
            adminTag.textContent = 'Admin';
            adminTag.classList.add('admin-tag');

            // APLICANDO ESTILOS DIRECTAMENTE AL ELEMENTO SPAN
            adminTag.style.backgroundColor = '#1a1b69';
            adminTag.style.color = '#ffffff';
            adminTag.style.fontSize = '0.75em';
            adminTag.style.fontWeight = 'bold';
            adminTag.style.padding = '4px 8px';
            adminTag.style.borderRadius = '4px';
            adminTag.style.marginLeft = '10px';
            adminTag.style.verticalAlign = 'middle';
            adminTag.style.textTransform = 'uppercase';
            adminTag.style.letterSpacing = '0.5px';

            tituloPerfil.appendChild(adminTag); 
        }
        if (fotoPerfil) {
            fotoPerfil.src = "/assets/perfilAdminTemp.jpg"; 
            fotoPerfil.alt = "Foto de perfil del Administrador";
        }
    } else {
        const existingAdminTag = tituloPerfil ? tituloPerfil.querySelector('.admin-tag') : null;
        if (existingAdminTag) {
            existingAdminTag.remove();
        }
        if (fotoPerfil && fotoPerfil.src !== "/assets/perfilUserTemp.jpg") {
             fotoPerfil.src = "/assets/perfilUserTemp.jpg"; // Tu foto de perfil por defecto
             fotoPerfil.alt = "Foto de perfil del usuario";
        }
    }

    // Elementos relacionados con la edición de biografía
    const btnEditar = document.getElementById('btn-editar-bio');
    const btnGuardar = document.getElementById('btn-guardar-bio');
    const bioTexto = document.getElementById('biografia-usuario');
    const bioTextarea = document.getElementById('editar-bio');

    if (btnEditar && btnGuardar && bioTexto && bioTextarea) {
        btnEditar.addEventListener('click', () => {
            bioTextarea.value = usuario.biografia || ''; // Carga la biografía actual
            bioTexto.classList.add('oculto'); // Oculta el texto
            bioTextarea.classList.remove('oculto'); // Muestra el textarea
            btnEditar.classList.add('oculto'); // Oculta el botón de editar
            btnGuardar.classList.remove('oculto'); // Muestra el botón de guardar
        });

        // Guarda la nueva biografía al hacer clic en "Guardar"
        btnGuardar.addEventListener('click', () => {
            const nuevaBio = bioTextarea.value.trim();

            if (!nuevaBio) {
                alert("🟥 La biografía no puede estar vacía.");
                return;
            }

            // Actualiza la biografía en el objeto de usuario activo
            usuario.biografia = nuevaBio;
            localStorage.setItem('usuarioActivo', JSON.stringify(usuario));

            // actualiza la biografía en la lista de usuarios registrados
            const usuariosRegistrados = JSON.parse(localStorage.getItem('usuariosRegistrados')) || [];
            const index = usuariosRegistrados.findIndex(u => u.email === usuario.email); 
            if (index !== -1) {
                usuariosRegistrados[index].biografia = nuevaBio;
                localStorage.setItem('usuariosRegistrados', JSON.stringify(usuariosRegistrados));
            }

            // Actualiza la biografía mostrada en la interfaz
            bioTexto.textContent = nuevaBio;

            // Restaura la visibilidad original de los elementos
            bioTexto.classList.remove('oculto');
            bioTextarea.classList.add('oculto');
            btnEditar.classList.remove('oculto');
            btnGuardar.classList.add('oculto');
            alert('Biografía actualizada!');
        });
    }
});