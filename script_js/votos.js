// Código para manejar like y report
document.querySelectorAll('.btn-like').forEach(button => {
    button.addEventListener('click', () => {
        const heartIcon = button.querySelector('.icon-heart');
        const countSpan = button.querySelector('.like-count');
        let count = parseInt(countSpan.textContent);
        const liked = heartIcon.getAttribute('fill') === 'red';

        if (liked) {
            // Dislike
            heartIcon.setAttribute('fill', 'none');
            heartIcon.setAttribute('stroke', '#555');
            count--;
        } else {
            // Like
            heartIcon.setAttribute('fill', 'red');
            heartIcon.setAttribute('stroke', 'red');
            count++;
        }
        countSpan.textContent = count < 0 ? 0 : count;
    });
});

document.querySelectorAll('.btn-report').forEach(button => {
    button.addEventListener('click', () => {
        const flagIcon = button.querySelector('.icon-flag');
        const reported = flagIcon.getAttribute('fill') === 'red';

        if (reported) {
            // Desreportar
            flagIcon.setAttribute('fill', 'none');
            flagIcon.setAttribute('stroke', '#555');
        } else {
            // Reportar
            flagIcon.setAttribute('fill', 'red');
            flagIcon.setAttribute('stroke', 'red');
            prompt('🟥🏴Gracias por reportar este contenido. Nuestro equipo de moderación se hará cargo pronto.\nPor favor, explíca brevemente el motivo de tu reporte:');
        }
    });
});
