document.addEventListener("DOMContentLoaded", function() {
    const footer = document.createElement("footer");
    footer.innerHTML = `
        <div class="filaPrincipal">
            <div class="enlaces">
                <h4>Enlaces</h4>
                <a href="../html/politica_privacidad.html">Política de privacidad</a>
                <a href="../html/aviso_legal.html">Aviso legal</a>
                <a href="../html/acerca_de.html">Acerca de</a>
            </div>
            <div class="contacto">
                <h4>Contacto</h4>
                <p>Teléfono: 985 453 362</p>
                <p>Email: info@drivesmart.com</p>
                <a href="./contacto.html">Más información</a>
            </div>
            <div class="redes">
                <h4>Redes Sociales</h4>
                <a href="#">Facebook</a>
                <a href="#">Twitter</a>
                <a href="#">Instagram</a>
            </div>
        </div>
        <div class="info">
            <p>&copy; 2024 Autoescuela DriveSmart. Todos los derechos reservados.</p>
        </div>
    `;
    document.body.appendChild(footer);
});
