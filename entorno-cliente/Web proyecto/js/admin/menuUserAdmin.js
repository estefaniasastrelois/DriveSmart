window.onload = function () {
    var header = document.querySelector('header');
    var nombre = document.createElement('h1');
    nombre.textContent = "Panel de administrador";
    var logo = document.createElement('img');
    logo.src = "../../imagenes/logo.png";
    logo.alt = "DriveSmart logo";
    header.append(nombre, logo);

    var nav=document.querySelector('nav');
    var usuarios=document.createElement('a');
    usuarios.href="admin_usuarios.html";
    usuarios.textContent="Usuarios";
    usuarios.classList="enlaceMenuPrincipal";
    var test=document.createElement('a');
    test.href="admin_test.html";
    test.textContent="Test";
    test.classList="enlaceMenuPrincipal";
    var clases=document.createElement('a');
    clases.href="admin_clases.html";
    clases.textContent="Clases";
    clases.classList="enlaceMenuPrincipal";
    var permisos=document.createElement('a');
    permisos.href="admin_permisos.html";
    permisos.textContent="Permisos";
    permisos.classList="enlaceMenuPrincipal";
   
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
    var cerrarSesion = document.createElement('a');
    cerrarSesion.href = "#";
    cerrarSesion.textContent = "Cerrar sesión";
    cerrarSesion.addEventListener('click', function () {
        // Lógica para cerrar sesión (por ejemplo, limpiar el localStorage y redirigir a la página de inicio)
        localStorage.clear();
        window.location.href = '/html/home.html';
    });
    perfilMenu.append(cerrarSesion);

    divPerfil.append(perfilMenu);

    nav.append(usuarios, test, clases, permisos, divPerfil);

    document.body.insertBefore(nav, document.body.firstChild);
    document.body.insertBefore(header, document.body.firstChild);


     // Evento para mostrar/ocultar el menú desplegable
     divPerfil.addEventListener('click', function (event) {
        event.stopPropagation();
        perfilMenu.classList.toggle('perfilMenuVisibleAdmin');
        perfilMenu.classList.toggle('perfilMenuOculto');
    });

    // Ocultar el menú cuando se hace clic fuera de él
    document.addEventListener('click', function () {
        if (perfilMenu.classList.contains('perfilMenuVisibleAdmin')) {
            perfilMenu.classList.remove('perfilMenuVisibleAdmin');
            perfilMenu.classList.add('perfilMenuOculto');
        }
    });

    perfilMenu.addEventListener('click', function (event) {
        event.stopPropagation();
    });
}
