
document.querySelector(".guardar").addEventListener("click", function() {
    const campos = [
        { id: "nombre", errorId: "nombreError", nombre: "Nombre" },
        { id: "apellidos", errorId: "apellidosError", nombre: "Apellidos" },
        { id: "dni", errorId: "dniError", nombre: "DNI" },
        { id: "email", errorId: "emailError", nombre: "Email" },
        { id: "telefono", errorId: "telefonoError", nombre: "Teléfono" },
        { id: "password", errorId: "passwordError", nombre: "Contraseña" }
    ];

    let valid = true;

    // Primero, oculta todos los mensajes de error
    campos.forEach(campo => {
        let error = document.getElementById(campo.errorId);
        if (error) {
            error.style.display = 'none';
        }
    });

    // Verifica cada campo y muestra el mensaje de error si está vacío
    campos.forEach(campo => {
        const input = document.getElementById(campo.id);
        let error = document.getElementById(campo.errorId);

        if (!input.value.trim()) {
            valid = false;
            mostrarError(campo.errorId, "Campo obligatorio", campo.id);
        }
    });

    // Validación adicional para DNI, email y teléfono
    if (!validarDNI(document.getElementById("dni").value)) {
        mostrarError("dniError", "DNI no válido", "dni");
        valid = false;
    }
    if (!validarEmail(document.getElementById("email").value)) {
        mostrarError("emailError", "Email no válido", "email");
        valid = false;
    }
    if (!validarTelefono(document.getElementById("telefono").value)) {
        mostrarError("telefonoError", "Teléfono no válido", "telefono");
        valid = false;
    }

    if (!valid) {
        console.error('Por favor, complete todos los campos correctamente.');
        return;
    }

    try {
        const usuario = {
            nombre: document.getElementById("nombre").value,
            apellidos: document.getElementById("apellidos").value,
            dni: document.getElementById("dni").value,
            email: document.getElementById("email").value,
            telefono: document.getElementById("telefono").value,
            password: sha256(document.getElementById("password").value),
            rol: {
                idrol: document.getElementById('rolId').value
            }
        };

        const jsonUsuario = JSON.stringify(usuario);
        enviarDatos(jsonUsuario);
    } catch (error) {
        console.error('Error al encriptar la contraseña:', error);
    }
});

function sha256(str) {
    let hash = 0;
    for (let i = 0; i < str.length; i++) {
        const char = str.charCodeAt(i);
        hash = (hash << 5) - hash + char;
        hash |= 0;
    }
    return hash.toString(16);
}

async function enviarDatos(jsonUsuario) {
    console.log(jsonUsuario);
    try {
        const response = await fetch('http://localhost:8080/usuario/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: jsonUsuario
        });
        if (!response.ok) {
            throw new Error('Error en la solicitud');
        }
        const data = await response.json();
        console.log('Usuario registrado exitosamente', data);
        mostrarModal();
        setTimeout(function() {
            window.location.href = 'home.html';
        }, 5000);
    } catch (error) {
        console.error('Error:', error);
    }
}

function mostrarModal() {
    document.getElementById('modal').style.display = 'block';
}

function mostrarError(errorId, mensaje, inputId) {
    let error = document.getElementById(errorId);
    const input = document.getElementById(inputId);
    if (!error) {
        error = document.createElement('p');
        error.id = errorId;
        error.className = 'error';
        input.parentNode.parentNode.insertBefore(error, input.parentNode.nextSibling);
    }
    error.innerText = mensaje;
    error.style.display = 'block';
}

function validarDNI(dni) {
    if (!dni.trim()) return true;  // Evitar mostrar "no válido" si el campo está vacío, ya se maneja arriba
    const dniRegex = /^[XYZ]?\d{5,8}[A-Z]$/;
    if (!dniRegex.test(dni)) return false;
    const numero = dni.substr(0, dni.length - 1).replace('X', 0).replace('Y', 1).replace('Z', 2);
    const letra = dni.substr(dni.length - 1, 1);
    const letras = 'TRWAGMYFPDXBNJZSQVHLCKET';
    const letraCorrecta = letras.charAt(numero % 23);
    return letra === letraCorrecta;
}

function validarEmail(email) {
    if (!email.trim()) return true;  // Evitar mostrar "no válido" si el campo está vacío, ya se maneja arriba
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

function validarTelefono(telefono) {
    if (!telefono.trim()) return true;  // Evitar mostrar "no válido" si el campo está vacío, ya se maneja arriba
    const telefonoRegex = /^[679]\d{8}$/;
    return telefonoRegex.test(telefono);
}