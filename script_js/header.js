let lastScrollY = window.scrollY;
const header = document.querySelector("header");
const backToTopBtn = document.getElementById("back-to-top");

window.addEventListener("scroll", () => {
    const currentScrollY = window.scrollY;

    // header oculto al bajar, visible al subir
    if (currentScrollY > lastScrollY && currentScrollY > 700) {
        header.classList.add("header-hidden");
    } else if (currentScrollY < lastScrollY) {
        header.classList.remove("header-hidden");
    }

    // mostrar botón if scrollY
    if (currentScrollY > 700) {
        backToTopBtn.style.display = "block";
    } else {
        backToTopBtn.style.display = "none";
    }

    lastScrollY = currentScrollY;
});

// Scroll suave al top al hacer clic en el botón
backToTopBtn.addEventListener("click", () => {
    window.scrollTo({
        top: 0,
        behavior: "smooth"
    });
});
