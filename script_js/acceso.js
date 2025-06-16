const abrirLoginBtns = document.querySelectorAll(".abrir-login-btn");
const modal = document.getElementById("auth-modal");
const cerrarModalBtn = document.getElementById("cerrar-modal");

const btnLogin = document.getElementById("btn-login");
const btnRegistro = document.getElementById("btn-registro");

const formLogin = document.getElementById("form-login");
const formRegistro = document.getElementById("form-registro");

abrirLoginBtns.forEach(btn => {
  btn.addEventListener("click", () => {
    modal.style.display = "flex";
    formLogin.classList.remove("hidden");
    formRegistro.classList.add("hidden");
    btnLogin.classList.add("active");
    btnRegistro.classList.remove("active");
  });
});

cerrarModalBtn.addEventListener("click", () => {
  modal.style.display = "none";
});

window.addEventListener("click", (e) => {
  if (e.target === modal) {
    modal.style.display = "none";
  }
});

btnLogin.addEventListener("click", () => {
  formLogin.classList.remove("hidden");
  formRegistro.classList.add("hidden");
  btnLogin.classList.add("active");
  btnRegistro.classList.remove("active");
});

btnRegistro.addEventListener("click", () => {
  formLogin.classList.add("hidden");
  formRegistro.classList.remove("hidden");
  btnLogin.classList.remove("active");
  btnRegistro.classList.add("active");
});

// para el ojito de la cc

const eyeOpenSVG = `
<svg class="icon-eye" xmlns="http://www.w3.org/2000/svg" height="24" width="24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" >
  <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/>
  <circle cx="12" cy="12" r="3"/>
</svg>
`;

const eyeClosedSVG = `
<svg class="icon-eye" xmlns="http://www.w3.org/2000/svg" height="24" width="24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" >
  <path d="M17.94 17.94A10.94 10.94 0 0 1 12 20c-7 0-11-8-11-8a19.68 19.68 0 0 1 5.52-6.5"/>
  <path d="M1 1l22 22"/>
</svg>
`;

document.querySelectorAll('.toggle-password').forEach(icon => {
  icon.addEventListener('click', () => {
    const targetId = icon.getAttribute('data-target');
    const input = document.getElementById(targetId);

    if (input.type === 'password') {
      input.type = 'text';
      icon.innerHTML = eyeClosedSVG;
    } else {
      input.type = 'password';
      icon.innerHTML = eyeOpenSVG;
    }
  });
});

document.addEventListener('DOMContentLoaded', () => {

  // cargar usuarios desde localStorage o inicializar con datos quemados
  let usuariosRegistrados = JSON.parse(localStorage.getItem('usuariosRegistrados')) || [
    {
      nombre: 'Ana Maria Ramirez',
      email: 'ana_ra15@gmail.com',
      username: 'anaR415',
      password: '123456',
      biografia: "Amante del arte y de los gatos 🎨😼 Actualmente aprendiendo programación en CESDE y con Platzi. Hablo español e inglés y me gusta dormir en mis tiempos libres.",
      fechaRegistro: "2025-03-10",
    },
    {
      nombre: 'Juan Pablo Londoño',
      email: 'pablo00@gmail.com',
      username: 'juanpa00',
      password: 'qwerty',
      biografia: "Hola, soy Juan Pablo y me gusta programar en Python. 👨‍💻 Tengo 21 y en mis tiempos libres me gusta leer libros de ciencia ficsión o practicar natación.",
      fechaRegistro: "2025-06-21",
    },
    {
      nombre: 'Laura Bermudez P',
      email: 'lau@gmail.com',
      username: 'Lau04',
      password: 'admin123',
      biografia: "Soy Lau, la administradora de este blog 😎🔥",
      fechaRegistro: "2025-01-01"
    }
  ];

  // guardar usuarios en localStorage para persistencia
  const guardarUsuarios = () => {
    localStorage.setItem('usuariosRegistrados', JSON.stringify(usuariosRegistrados));
  };

  // función para cambiar a login (para después del registro)
  function mostrarLogin() {
    formLogin.classList.remove('hidden');
    formRegistro.classList.add('hidden');
    btnLogin.classList.add('active');
    btnRegistro.classList.remove('active');
  }

  // validar inicio de sesión
  formLogin.addEventListener('submit', (e) => {
    e.preventDefault();

    const identificador = document.getElementById('login-identificador').value.trim();
    const password = document.getElementById('login-password').value.trim();

    // buscar usuario por username/email
    const usuario = usuariosRegistrados.find(u =>
      (u.username === identificador || u.email === identificador) &&
      u.password === password
    );

    if (usuario) {
      alert("✅ Bienvenid@, " + usuario.nombre);

      localStorage.setItem('usuarioActivo', JSON.stringify(usuario));

      if (usuario.username === 'Lau04' || usuario.email === 'lau@gmail.com') {
        // ir a la vista de admin
        window.location.href = '/html_css/indexA.html';
      } else {
        // ir a la vista de usuario
        window.location.href = '/html_css/indexU.html';
      }

    } else {
      alert('🟥 Usuario o contraseña incorrectos. Intenta otra vez.');
    }
  });

  // validar registro
  formRegistro.addEventListener('submit', (e) => {
    e.preventDefault();

    const nombre = document.getElementById('registro-nombre').value.trim();
    const username = document.getElementById('registro-username').value.trim();
    const email = document.getElementById('registro-email').value.trim();
    const password = document.getElementById('registro-password').value.trim();

    // datos adicionales
    const biografia = "¡Hola! Soy nuev@ en la comunidad 💬";
    const fechaRegistro = new Date().toLocaleDateString();

    // validaciones básicas
    if (!nombre || !username || !email || !password) {
      alert('🟥 Por favor completa todos los campos.');
      return;
    }

    // validar si ya existe ese correo
    const existe = usuariosRegistrados.find(u => u.email === email || u.username === username);

    if (existe) {
      alert('🟥 Este correo o usuario ya está registrado. Prueba con otro distinto o inicia sesión.');
      return;
    }

    // guardar nuevo usuario con bio y fecha
    usuariosRegistrados.push({ nombre, username, email, password, biografia, fechaRegistro });
    guardarUsuarios();

    alert(`🟩 Cuenta creada exitosamente. Ahora inicia sesión.`);

    // volver a login y limpiar formulario
    mostrarLogin();
    formRegistro.reset();
  });
});

formRegistro.addEventListener('submit', (e) => {
  e.preventDefault();

  // Capturar inputs
  const nombre = document.getElementById('registro-nombre').value.trim();
  const username = document.getElementById('registro-username').value.trim();
  const email = document.getElementById('registro-email').value.trim();
  const password = document.getElementById('registro-password').value.trim();

  // Capturar divs de error
  const errorNombre = document.getElementById('error-nombre');
  const errorUsername = document.getElementById('error-username');
  const errorEmail = document.getElementById('error-email');
  const errorPassword = document.getElementById('error-password');

  // Limpiar errores anteriores
  errorNombre.textContent = '';
  errorUsername.textContent = '';
  errorEmail.textContent = '';
  errorPassword.textContent = '';

  let valido = true;

  // VALIDACIONES

  // Nombre vacío
  if (!nombre) {
    errorNombre.textContent = '🟥 El nombre es obligatorio.';
    valido = false;
  }

  // Username mínimo 5 caracteres y solo letras/números
  const regexUsername = /^[a-zA-Z0-9]{5,}$/;
  if (!regexUsername.test(username)) {
    errorUsername.textContent = '🟥 El usuario debe tener al menos 5 caracteres y solo letras/números.';
    valido = false;
  }

  // Email con @corre.com
  const regexEmail = /^[\w.-]+@corre\.com$/i;
  if (!regexEmail.test(email)) {
    errorEmail.textContent = '🟥 El correo debe terminar en @corre.com';
    valido = false;
  }

  // Contraseña segura: 8+ caracteres, mayúscula, número, símbolo
  const regexPassword = /^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]).{8,}$/;
  if (!regexPassword.test(password)) {
    errorPassword.textContent = '🟥 La contraseña debe tener mínimo 8 caracteres, una mayúscula, un número y un símbolo.';
    valido = false;
  }

  // ¿Usuario o correo ya existe?
  const existe = usuariosRegistrados.find(u => u.email === email || u.username === username);
  if (existe) {
    errorEmail.textContent = '🟥 Este correo o usuario ya está registrado.';
    valido = false;
  }

  // Si todo está ok, registrar
  if (valido) {
    const biografia = "¡Hola! Soy nuev@ en la comunidad 💬";
    const fechaRegistro = new Date().toLocaleDateString();

    usuariosRegistrados.push({ nombre, username, email, password, biografia, fechaRegistro });
    guardarUsuarios();

    alert('🟩 Cuenta creada exitosamente. Ahora inicia sesión.');
    mostrarLogin();
    formRegistro.reset();
  }
});

