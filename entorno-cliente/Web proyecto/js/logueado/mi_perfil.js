document.addEventListener('DOMContentLoaded', () => {
    const userId = localStorage.getItem('usuarioId');

    cargarDatosUsuario(userId);
    configurarBotonCambiarPass();
    configurarBotonesGuardar(userId);
});

function cargarDatosUsuario(userId) {
    fetch(`http://localhost:8080/usuario/listado/${userId}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('nombre').value = data.nombre;
            document.getElementById('apellidos').value = data.apellidos;
            document.getElementById('dni').value = data.dni;
            document.getElementById('email').value = data.email;
            document.getElementById('telefono').value = data.telefono;
            document.getElementById('miPass').value = data.password;
        })
        .catch(error => console.error('Error:', error));
}

function configurarBotonCambiarPass() {
    document.getElementById('cambiarPass').addEventListener('click', () => {
        const changePasswordFields = document.getElementById('cambiarPassCampos');
        const guardarSinPass = document.getElementById('guardarSinPass');
        const guardarConPass = document.getElementById('guardarConPass');

        if (changePasswordFields.style.display === 'none') {
            changePasswordFields.style.display = 'block';
            guardarSinPass.style.display = 'none';
            guardarConPass.style.display = 'block';
        } else {
            changePasswordFields.style.display = 'none';
            guardarSinPass.style.display = 'block';
            guardarConPass.style.display = 'none';
        }
    });
}

function configurarBotonesGuardar(userId) {
    document.getElementById('guardarSinPass').addEventListener('click', () => {
        const usuarioEditadoSinPass = {
            nombre: document.getElementById('nombre').value,
            apellidos: document.getElementById('apellidos').value,
            dni: document.getElementById('dni').value,
            email: document.getElementById('email').value,
            telefono: document.getElementById('telefono').value,
            password: document.getElementById('miPass').value
        };
        updateUserData(userId, usuarioEditadoSinPass);
    });

    document.getElementById('guardarConPass').addEventListener('click', () => {
        const oldPassword = document.getElementById('old-password').value;
        const newPassword = document.getElementById('new-password').value;
        const confirmPassword = document.getElementById('confirm-password').value;
        const mensajePass = document.getElementById('mensajePass');

        mensajePass.classList.remove('mensaje-pass-visible');
        mensajePass.classList.add('mensaje-pass-oculto');

        if (newPassword !== confirmPassword) {
            mensajePass.textContent = 'La nueva contraseña y la confirmación no coinciden.';
            mensajePass.classList.remove('mensaje-pass-oculto');
            mensajePass.classList.add('mensaje-pass-visible');
            return;
        }

        const hashedOldPassword = sha256(oldPassword);
        verificarPasswordAnterior(userId, hashedOldPassword, newPassword);
    });
}

function sha256(str) {
    let hash = 0;
    for (let i = 0; i < str.length; i++) {
        const char = str.charCodeAt(i);
        hash = (hash << 5) - hash + char;
        hash |= 0; // Convierte a entero de 32 bits
    }
    return hash.toString(16); // Convertir a representación hexadecimal
}

function verificarPasswordAnterior(userId, hashedOldPassword, newPassword) {
    fetch(`http://localhost:8080/usuario/listado/${userId}`)
        .then(response => response.json())
        .then(data => {
            const mensajePass = document.getElementById('mensajePass');
            if (hashedOldPassword !== data.password) {
                mensajePass.textContent = 'La contraseña anterior no es correcta.';
                mensajePass.classList.remove('mensaje-pass-oculto');
                mensajePass.classList.add('mensaje-pass-visible');
                return;
            }
            const usuarioEditadoConPass = {
                nombre: document.getElementById('nombre').value,
                apellidos: document.getElementById('apellidos').value,
                dni: document.getElementById('dni').value,
                email: document.getElementById('email').value,
                telefono: document.getElementById('telefono').value,
                password: sha256(newPassword).toString()
            };
            updateUserData(userId, usuarioEditadoConPass);
        })
        .catch(error => console.error('Error:', error));
}

function updateUserData(userId, usuarioEditado) {
    fetch(`http://localhost:8080/usuario/update/${userId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(usuarioEditado)
    })
    .then(response => {
        if (response.ok) {
            showModal('Perfil actualizado correctamente');
            const mensajePass = document.getElementById('mensajePass');
            mensajePass.classList.remove('mensaje-pass-visible');
            mensajePass.classList.add('mensaje-pass-oculto');
        } else {
            alert('Error al actualizar el perfil');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error al actualizar el perfil');
    });
}

function showModal(message) {
    const modal = document.getElementById('successModal');
    const modalContent = modal.querySelector('.modalMensaje-content p');
    modalContent.textContent = message;
    modal.style.display = 'flex';
    setTimeout(() => {
        modal.style.display = 'none';
    }, 3000);
}