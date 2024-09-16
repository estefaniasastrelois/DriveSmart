window.onload = function () {
    /* Cargar el menú para un usuario no logeado */
    var header = document.querySelector('header');
    var nombre = document.createElement('h1');
    nombre.textContent = "Conduce con confianza";
    var logo = document.createElement('img');
    logo.src = "../imagenes/logo.png";
    logo.alt = "DriveSmart logo";
    header.append(nombre, logo);

    var nav = document.querySelector('nav');
    var home = document.createElement('a');
    home.href = "home.html";
    home.textContent = "Inicio";
    home.classList = "enlaceMenuPrincipal enlaceMenuPrincipalAnonimo";
    var permisos = document.createElement('a');
    permisos.href = "permisos.html";
    permisos.textContent = "Permisos";
    permisos.classList = "enlaceMenuPrincipal enlaceMenuPrincipalAnonimo";
    var contacto = document.createElement('a');
    contacto.href = "contacto.html";
    contacto.textContent = "Contacto";
    contacto.classList = "enlaceMenuPrincipal enlaceMenuPrincipalAnonimo";
    var login = document.createElement('a');
    login.textContent = "Área de clientes";
    login.classList = "enlaceMenuPrincipal enlaceMenuPrincipalAnonimo";

    nav.append(home, permisos, contacto, login);

    document.body.insertBefore(nav, document.body.firstChild);
    document.body.insertBefore(header, document.body.firstChild);

    /* Configuración de la ventana de login - cuando un usuario no esté logeado */
    var divLogin = document.createElement('div');
    divLogin.classList = "divLoginOculto";
    document.body.append(divLogin);
    divLogin.innerHTML = `
    <form id="loginForm">
        <div class="datosSecciones">
            <div class="seccion">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div class="seccion">
                <label for="password">Clave:</label>    
                <input type="password" id="password" name="password" required>
            </div>
        </div>
        <div id="errorMessage" style="color: red; display: none; margin-bottom: 10px;">Los datos introducidos no son correctos</div>
        <div class="enviar">
            <button type="submit" class="btnLogin">Iniciar sesión</button>
        </div>
        <div class="registro">
            ¿No tienes cuenta? <a href="registro.html">Regístrate</a>
        </div>
    </form>
    `;

    /* Mostrar ventana de login cuando le demos al icono */
    login.addEventListener("click", function (event) {
        event.stopPropagation(); // Evita que el evento se propague al documento
        divLogin.classList = "divLoginVisible";
    });

    /* Ocultar ventana de login cuando hagamos clic fuera del div */
    document.addEventListener("click", function () {
        divLogin.classList = "divLoginOculto";
    });

    /* Evita que el divLogin se cierre cuando hagamos clic dentro de él */
    divLogin.addEventListener("click", function (event) {
        event.stopPropagation();
    });

    // Agregar evento de envío del formulario de inicio de sesión
    document.getElementById('loginForm').addEventListener('submit', function (event) {
        event.preventDefault(); // Evitar el envío del formulario por defecto
        
        // Obtener los valores de email y contraseña
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        // Realizar la solicitud Fetch para verificar el usuario
        fetch(`http://localhost:8080/usuario/listado/comprueba/${email}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Usuario no encontrado');
                }
                return response.json();
            })
            // Dentro del bloque then del inicio de sesión exitoso
            .then(usuario => {
                // Encriptar la contraseña proporcionada antes de compararla
                const hashedPassword = sha256(password);
                console.log(password);
                console.log(hashedPassword);
                // Verificar la contraseña
                if (usuario.password === hashedPassword) {
                    console.log('Inicio de sesión exitoso');
                    // Almacenar información del usuario en localStorage
                    localStorage.setItem('usuarioId', usuario.idusuario);
                    localStorage.setItem('usuarioNombre', usuario.nombre);
                    //localStorage.setItem('usuarioApellidos', usuario.apellidos);
                    localStorage.setItem('usuarioRol', usuario.rol.idrol);
                    // Redireccionar al usuario basado en el rol
                    if (usuario.rol.idrol === 1) {
                        window.location.href = '/html/admin/admin_test.html'; // Administrador
                    } else if (usuario.rol.idrol === 2) {
                        window.location.href = '/html/logueado/practica_test.html'; // Usuario normal
                    } else {
                        console.error('Rol de usuario no reconocido');
                    }
                } else {
                    mostrarMensajeError();
                    console.error('Contraseña incorrecta');
                }
            })
            .catch(error => {
                mostrarMensajeError();
                console.error('Error al iniciar sesión:', error);
            });
    });
}

// Función para mostrar el mensaje de error
function mostrarMensajeError() {
    const errorMessage = document.getElementById('errorMessage');
    errorMessage.style.display = 'block';
}

// Función para calcular el hash SHA-256 de una cadena (implementación simple)
function sha256(str) {
    // Esta implementación simple de SHA-256 no es la más segura
    // Se recomienda utilizar una biblioteca de hashing criptográfico para una seguridad adecuada
    let hash = 0;
    for (let i = 0; i < str.length; i++) {
        const char = str.charCodeAt(i);
        hash = (hash << 5) - hash + char;
        hash |= 0; // Convierte a entero de 32 bits
    }
    return hash.toString(16); // Convertir a representación hexadecimal
}