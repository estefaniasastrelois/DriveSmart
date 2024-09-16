window.onload = function () {
    var header = document.querySelector('header');
    var nombre = document.createElement('h1');
    nombre.textContent = "Conduce con confianza";
    var logo = document.createElement('img');
    logo.src = "../../imagenes/logo.png";
    logo.alt = "DriveSmart logo";
    header.append(nombre, logo);

    // Obtener el nombre del usuario desde localStorage
    var usuarioNombre = localStorage.getItem('usuarioNombre');

    var divPerfil = document.createElement('a');
    divPerfil.classList = "divPerfil enlaceMenuPrincipal";
    var imgPerfil = document.createElement('img');
    imgPerfil.src = "../../imagenes/usuario.png";
    imgPerfil.classList = "iconoPerfil";
    var perfilTexto = document.createElement('p');
    perfilTexto.textContent = usuarioNombre;  // Cambiar "Mi perfil" por el nombre del usuario
    divPerfil.append(imgPerfil, perfilTexto);

    // Crear el menú desplegable
    var perfilMenu = document.createElement('nav');
    perfilMenu.classList = "perfilMenuOculto"; // Clase oculta por defecto
    var editarPerfil = document.createElement('a');
    editarPerfil.href = "mi_perfil.html";
    editarPerfil.textContent = "Mi perfil";
    var cerrarSesion = document.createElement('a');
    cerrarSesion.href = "#";
    cerrarSesion.textContent = "Cerrar sesión";
    cerrarSesion.addEventListener('click', function () {
        // Lógica para cerrar sesión (por ejemplo, limpiar el localStorage y redirigir a la página de inicio)
        localStorage.clear();
        window.location.href = '/html/home.html';
    });
    perfilMenu.append(editarPerfil, cerrarSesion);

    divPerfil.append(perfilMenu);

    // Agregar la barra de usuario encima del header
    document.body.append(header);

    var nav = document.querySelector('nav');
    var estadisticas = document.createElement('a');
    estadisticas.href = "estadisticas.html";
    estadisticas.textContent = "Estadísticas";
    estadisticas.classList = "enlaceMenuPrincipal";
    var test = document.createElement('a');
    test.href = "practica_test.html";
    test.textContent = "Test";
    test.classList = "enlaceMenuPrincipal";
    var clases = document.createElement('a');
    clases.href = "usuario_clases.html";
    clases.textContent = "Clases";
    clases.classList = "enlaceMenuPrincipal";

    nav.append(estadisticas, test, clases, divPerfil);

    document.body.insertBefore(nav, document.body.firstChild);
    document.body.insertBefore(header, document.body.firstChild);

    // Evento para mostrar/ocultar el menú desplegable
    divPerfil.addEventListener('click', function (event) {
        event.stopPropagation();
        perfilMenu.classList.toggle('perfilMenuVisible');
        perfilMenu.classList.toggle('perfilMenuOculto');
    });

    // Ocultar el menú cuando se hace clic fuera de él
    document.addEventListener('click', function () {
        if (perfilMenu.classList.contains('perfilMenuVisible')) {
            perfilMenu.classList.remove('perfilMenuVisible');
            perfilMenu.classList.add('perfilMenuOculto');
        }
    });

    perfilMenu.addEventListener('click', function (event) {
        event.stopPropagation();
    });

    document.addEventListener('DOMContentLoaded', function() {
    // Obtener el elemento divPerfil y perfilMenuVisible

const perfilMenuVisible = document.querySelector('.perfilMenuVisible');

// Función para ajustar el ancho de perfilMenuVisible al ancho de divPerfil
function ajustarAnchoPerfilMenu() {
  // Obtener el ancho actual del divPerfil
  const anchoDivPerfil = divPerfil.offsetWidth;
  // Aplicar el ancho al perfilMenuVisible
  perfilMenuVisible.style.width = `${anchoDivPerfil}px`;
}

// Evento hover para activar la función de ajuste de ancho
divPerfil.addEventListener('mouseenter', ajustarAnchoPerfilMenu);
divPerfil.addEventListener('mouseleave', ajustarAnchoPerfilMenu);
    });
}