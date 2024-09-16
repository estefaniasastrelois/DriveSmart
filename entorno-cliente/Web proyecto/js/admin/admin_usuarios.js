/*-------------------------------------DECLARACIÓN DE VARIABLES---------------------------------------*/
let usuarioSeleccionado = null;
let usuarios = [];

// Obtener el modal y el botón de cierre
const modal = document.getElementById("modal");
const closeBtn = document.querySelector(".close");

// Obtener los botones de guardar y actualizar
const guardarBtn = document.getElementById("guardar");
const actualizarBtn = document.getElementById("actualizar");

// Obtener el botón "Nuevo usuario"
const nuevoUsuarioBtn = document.querySelector(".nuevo");
const matriculasBtn = document.querySelector(".matriculas");

// Modal de matriculación
const matriculacionModal = document.getElementById("matriculacionModal");
const guardarMatriculaBtn = document.getElementById("guardarMatriculacion");
const closeMatriculacion = document.querySelector(".closeMatriculacion");
const usuarioBusquedaMatricula = document.getElementById('usuario-busqueda-matricula');
const usuarioResultadosMatricula = document.getElementById('usuario-resultados-matricula');






/*-------------------------------------ASPECTOS GENERALES Y EVENTOS DEL DOM---------------------------------------*/
// Mostrar la ventana modal de nuevo usuario
nuevoUsuarioBtn.onclick = function() {
    modal.style.display = "block";
    document.getElementById('modalTitle').textContent = "Nuevo usuario";
    document.getElementById("usuarioForm").reset();
    document.getElementById("password").value = "drivesmart";
    guardarBtn.style.display = "inline-block";
    actualizarBtn.style.display = "none";
}

// Cerrar el modal
closeBtn.onclick = function() {
    modal.style.display = "none";
}

// Mostrar la ventana modal de matriculación
matriculasBtn.onclick = function() {
    usuarioSeleccionado = null;
    usuarioBusquedaMatricula.value = '';
    usuarioResultadosMatricula.innerHTML = '';
    matriculacionModal.style.display = "block";
    cargarPermisos();
}

// Cerrar la ventana modal de matriculación
closeMatriculacion.onclick = function() {
    matriculacionModal.style.display = "none";
    usuarioSeleccionado = null;
    usuarioBusquedaMatricula.value = '';
    usuarioResultadosMatricula.innerHTML = '';
}

// Cerrar los modales cuando se hace clic fuera de ellos
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    } else if (event.target == matriculacionModal) {
        matriculacionModal.style.display = "none";
        usuarioSeleccionado = null;
        usuarioBusquedaMatricula.value = '';
        usuarioResultadosMatricula.innerHTML = '';
    }
}

// Normalizar texto para el buscador
function normalizarTexto(texto) {
    return texto.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
}

// Función para calcular el hash SHA-256 de una cadena (implementación simple)
function sha256(str) {
    let hash = 0;
    for (let i = 0; i < str.length; i++) {
        const char = str.charCodeAt(i);
        hash = (hash << 5) - hash + char;
        hash |= 0; // Convierte a entero de 32 bits
    }
    return hash.toString(16); // Convertir a representación hexadecimal
}




/*-------------------------------------LISTA DE USUARIOS---------------------------------------*/
// Mostrar lista de usuarios
function muestraUsuarios() {
    fetch('http://localhost:8080/usuario/listado', {
        method: 'GET'
    })
    .then(response => response.json())
    .then(data => {
        usuarios = data; // Actualizar la lista global de usuarios
        const usuariosBody = document.getElementById('usuariosBody');
        usuariosBody.innerHTML = '';
        data.forEach(usuario => {
            const fila = document.createElement('tr');
            fila.innerHTML = `
                <td class="nombre">${usuario.nombre}</td>
                <td class="apellidos">${usuario.apellidos}</td>
                <td class="permisos"></td>
                <td class="editar"><i class='bx bxs-edit-alt' onclick="muestraVentanaEdicion(${usuario.idusuario})"></i></td>
                <td class="eliminar"><i class='bx bxs-trash' onclick="lanzarConfirmacion(${usuario.idusuario})"></i></td>
            `;
            usuariosBody.appendChild(fila);
            fetch(`http://localhost:8080/matricula/${usuario.idusuario}/permisos`)
            .then(response => response.json())
            .then(permisos => {
                const columnaPermisos = fila.querySelector('.permisos');
                columnaPermisos.textContent = permisos.length > 0 ? permisos.map(permiso => permiso.nombre).join(', ') : '-';
            })
            .catch(error => console.error('Error al obtener los permisos:', error));
        });
    })
    .catch(error => console.error('Se produjo un error al obtener los usuarios:', error));
}





/*-------------------------------------NUEVO USUARIO - VENTANA MODAL DE CREACIÓN---------------------------------------*/
// Cargar permisos para la ventana de matriculación
function cargarPermisos() {
    fetch('http://localhost:8080/permiso/listado', {
        method: 'GET'
    })
    .then(response => response.json())
    .then(permisos => {
        const checkboxContainer = document.getElementById('checkboxContainer');
        checkboxContainer.innerHTML = '';
        permisos.forEach(permiso => {
            const checkboxDiv = document.createElement('div');
            checkboxDiv.classList.add('seccionCheckbox');
            const checkbox = document.createElement('input');
            checkbox.type = 'checkbox';
            checkbox.id = `permiso_${permiso.idpermiso}`;
            checkbox.name = 'permisos';
            checkbox.value = permiso.idpermiso;
            checkbox.dataset.descripcion = permiso.descripcion;

            const label = document.createElement('label');
            label.htmlFor = `permiso_${permiso.idpermiso}`;
            label.textContent = permiso.nombre;

            checkboxDiv.appendChild(checkbox);
            checkboxDiv.appendChild(label);
            checkboxContainer.appendChild(checkboxDiv);
        });
    })
    .catch(error => console.error('Error al obtener los permisos:', error));
}
// Filtrar usuarios en tiempo real - para que aparezca el usuario recién creado en el buscador
usuarioBusquedaMatricula.addEventListener('input', function() {
    const query = usuarioBusquedaMatricula.value.trim().toLowerCase();
    const usuariosFiltrados = usuarios.filter(usuario => {
        const nombreCompleto = normalizarTexto(`${usuario.nombre} ${usuario.apellidos}`.toLowerCase());
        return nombreCompleto.includes(query);
    });
    mostrarResultadosUsuariosMatricula(usuariosFiltrados);
});
// Guardar nuevo usuario
guardarBtn.addEventListener("click", function() {
    const usuario = {
        nombre: document.getElementById("nombre").value,
        apellidos: document.getElementById("apellidos").value,
        dni: document.getElementById("dni").value,
        email: document.getElementById("email").value,
        telefono: document.getElementById("telefono").value,
        password: sha256(document.getElementById("password").value),
        rol: { idrol: document.getElementById('rolId').value }
    };

    fetch('http://localhost:8080/usuario/add', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(usuario)
    })
    .then(response => response.json())
    .then(data => {
        console.log('Usuario registrado exitosamente', data);
        document.getElementById("usuarioId").value = data.idusuario;
        modal.style.display = "none";
        muestraUsuarios(); // Actualizar la lista de usuarios
        usuarioSeleccionado = data; // Seleccionar el nuevo usuario
        matriculacionModal.style.display = "block"; //Abrir automáticamente ventana de matriculación
        cargarPermisos();
    })
    .catch(error => console.error('Error:', error));
});





/*-------------------------------------VENTANA MODAL DE MATRICULACIÓN---------------------------------------*/
// Mostrar resultados de búsqueda de usuarios para la matriculación
function mostrarResultadosUsuariosMatricula(usuarios) {
    usuarioResultadosMatricula.innerHTML = '';
    if (usuarios.length > 0) {
        usuarios.forEach(usuario => {
            const div = document.createElement('div');
            div.textContent = `${usuario.nombre} ${usuario.apellidos}`;
            div.dataset.usuarioId = usuario.idusuario;
            div.classList.add('usuario-item');
            div.addEventListener('click', function() {
                seleccionarUsuarioMatricula(usuario);
            });
            usuarioResultadosMatricula.appendChild(div);
        });
        usuarioResultadosMatricula.style.display = 'block';
    } else {
        usuarioResultadosMatricula.style.display = 'none';
    }
}

// Seleccionar usuario para la matriculación
function seleccionarUsuarioMatricula(usuario) {
    usuarioSeleccionado = usuario;
    usuarioBusquedaMatricula.value = `${usuario.nombre} ${usuario.apellidos}`;
    usuarioResultadosMatricula.style.display = 'none';
    marcarCheckboxes();
}

// Marcar los checkboxes de permisos para el usuario seleccionado
function marcarCheckboxes() {
    if (!usuarioSeleccionado) {
        console.error('No se ha seleccionado ningún usuario para matricular.');
        return;
    }

    fetch(`http://localhost:8080/matricula/${usuarioSeleccionado.idusuario}/permisos`, {
        method: 'GET'
    })
    .then(response => response.json())
    .then(matriculas => {
        const checkboxes = document.querySelectorAll('#checkboxContainer input[type="checkbox"]');
        checkboxes.forEach(checkbox => checkbox.checked = false);

        matriculas.forEach(matricula => {
            const checkbox = document.getElementById(`permiso_${matricula.idpermiso}`);
            if (checkbox) {
                checkbox.checked = true;
            }
        });
    })
    .catch(error => console.error('Error al marcar los checkboxes:', error));
}

// Funciones para manejar matrículas eliminadas y nuevas
guardarMatriculaBtn.addEventListener('click', function() {
    comprobarMatriculasEliminadas();
    setTimeout(comprobarMatriculasNuevas, 200);
});

// Comprobar y eliminar matrículas deseleccionadas
function comprobarMatriculasEliminadas() {
    if (!usuarioSeleccionado) {
        console.error('No se ha seleccionado ningún usuario para matricular.');
        return;
    }

    const checkboxesDeseleccionados = document.querySelectorAll('#checkboxContainer input[type="checkbox"]:not(:checked)');
    checkboxesDeseleccionados.forEach(checkbox => {
        const permisoId = checkbox.value;

        fetch(`http://localhost:8080/matricula/isMatriculado/${usuarioSeleccionado.idusuario}/${permisoId}`, {
            method: 'GET'
        })
        .then(response => response.json())
        .then(data => {
            if (data.isMatriculado) {
                eliminarMatricula(data.idMatricula);
            }
        })
        .catch(error => console.error('Error al comprobar la matrícula:', error));
    });
}

// Eliminar matrícula
function eliminarMatricula(idMatricula) {
    fetch(`http://localhost:8080/matricula/delete/${idMatricula}`, {
        method: 'DELETE'
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Error en la solicitud de eliminación');
        }
        return response.json();
    })
    .then(data => {
        console.log('Matrícula eliminada exitosamente', data);
        matriculacionModal.style.display = "none";
    })
    .catch(error => console.error('Error al eliminar la matrícula:', error));
}

// Comprobar y agregar nuevas matrículas seleccionadas
function comprobarMatriculasNuevas() {
    if (!usuarioSeleccionado) {
        console.error('No se ha seleccionado ningún usuario para matricular.');
        return;
    }

    const checkboxesMarcados = document.querySelectorAll('#checkboxContainer input[type="checkbox"]:checked');
    checkboxesMarcados.forEach(checkbox => {
        const permisoId = checkbox.value;
        const permisoNombre = checkbox.nextSibling.textContent;

        fetch(`http://localhost:8080/matricula/isMatriculado/${usuarioSeleccionado.idusuario}/${permisoId}`, {
            method: 'GET'
        })
        .then(response => response.json())
        .then(data => {
            if (!data.isMatriculado) {
                const permiso = {
                    idpermiso: permisoId,
                    nombre: permisoNombre,
                    descripcion: checkbox.dataset.descripcion
                };
                insertarMatricula(permiso);
            }
        })
        .catch(error => console.error('Error al comprobar la matrícula:', error));
    });

    // Actualizar la tabla de usuarios después de comprobar las matrículas
    setTimeout(muestraUsuarios, 200);
}

// Insertar nueva matrícula
function insertarMatricula(permiso) {
    if (!usuarioSeleccionado) {
        console.error('No se ha seleccionado ningún usuario para matricular.');
        return;
    }

    const matricula = {
        usuario: usuarioSeleccionado,
        permiso: permiso
    };

    fetch('http://localhost:8080/matricula/add', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(matricula)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Error en la solicitud');
        }
        return response.json();
    })
    .then(data => {
        console.log('Matrícula insertada exitosamente', data);
        matriculacionModal.style.display = "none";
        muestraUsuarios();
    })
    .catch(error => console.error('Error al insertar la matrícula:', error));
}





/*------------------------------------EDICIÓN DE USUARIO---------------------------------------*/
// Funciones adicionales para editar y eliminar usuarios
function muestraVentanaEdicion(idUsuario) {
    fetch(`http://localhost:8080/usuario/listado/${idUsuario}`)
    .then(response => response.json())
    .then(data => {
        document.getElementById('modalTitle').textContent = "Editar usuario";
        document.getElementById('usuarioId').value = data.idusuario;
        document.getElementById('nombre').value = data.nombre;
        document.getElementById('apellidos').value = data.apellidos;
        document.getElementById('dni').value = data.dni;
        document.getElementById('email').value = data.email;
        document.getElementById('telefono').value = data.telefono;
        document.getElementById('password').value = data.password;
        guardarBtn.style.display = "none";
        actualizarBtn.style.display = "inline-block";
        modal.style.display = "block";
    })
    .catch(error => console.error('Error:', error));
}
actualizarBtn.addEventListener("click", function() {
    const usuarioId = document.getElementById('usuarioId').value;
    const usuario = {
        nombre: document.getElementById("nombre").value,
        apellidos: document.getElementById("apellidos").value,
        dni: document.getElementById("dni").value,
        email: document.getElementById("email").value,
        telefono: document.getElementById("telefono").value,
        password: document.getElementById("password").value,
        rol: { idrol: document.getElementById('rolId').value }
    };

    fetch(`http://localhost:8080/usuario/update/${usuarioId}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(usuario)
    })
    .then(response => response.json())
    .then(data => {
        console.log('Usuario actualizado exitosamente', data);
        modal.style.display = "none";
        muestraUsuarios();
    })
    .catch(error => console.error('Error:', error));
});





/*-------------------------------------ELIMINACIÓN DE USUARIO---------------------------------------*/
function eliminarUsuario(idUsuario) {
    confirmModal.style.display = "none";
    fetch(`http://localhost:8080/usuario/delete/${idUsuario}`, {
        method: 'DELETE'
    })
    .then(response => {
        if (response.ok) {
            muestraUsuarios();
        } else {
            console.error('Error al eliminar el usuario.');
        }
    })
    .catch(error => console.error('Se produjo un error al eliminar el usuario:', error));
}

function lanzarConfirmacion(idUsuario) {
    confirmModal.style.display = "block";
    confirmYes.addEventListener("click", function() {
        realizaComprobaciones(idUsuario);
    });
}

confirmNo.onclick = function() {
    confirmModal.style.display = "none";
}

document.addEventListener('DOMContentLoaded', function() {
    muestraUsuarios();
    cargarPermisos();
    limpiarBusquedaMatricula();
});

function limpiarBusquedaMatricula() {
    const guardarMatriculaBtn = document.getElementById('guardarMatriculacion');
    guardarMatriculaBtn.addEventListener('click', function() {
        usuarioBusquedaMatricula.value = ''; // Limpiar el campo de búsqueda de usuario
        comprobarMatriculasEliminadas();
        setTimeout(comprobarMatriculasNuevas, 200);
    });
}

// Funciones adicionales para manejar eliminaciones y dependencias - antes de eliminar un suario, es necesario eliminar sus matrículas, registros de test realizados y clases prácticas
async function realizaComprobaciones(idUsuario) {
    await compruebaMatriculas(idUsuario);
    await eliminaRealizaciones(idUsuario);
    await eliminaPracticas(idUsuario);
}
// Comprueba si el usuario que se quiere eliminar tiene matrículas y las elimina
async function compruebaMatriculas(idUsuario) {
    try {
        const response = await fetch(`http://localhost:8080/matricula/${idUsuario}/permisos`, {
            method: 'GET'
        });
        const permisos = await response.json();
        if (permisos.length > 0) {
            await fetch(`http://localhost:8080/matricula/delete/all/${idUsuario}`, {
                method: 'DELETE'
            });
        }
        eliminarUsuario(idUsuario);
    } catch (error) {
        console.error('Error al comprobar las matrículas:', error);
        eliminarUsuario(idUsuario);
    }
}
// Comprueba si el usuario que se quiere eliminar tiene prácticas y las elimina
async function eliminaPracticas(idUsuario) {
    try {
        await fetch(`http://localhost:8080/practica/deleteByUsuario/${idUsuario}`, {
            method: 'DELETE'
        });
        eliminarUsuario(idUsuario);
    } catch (error) {
        console.error('Error al comprobar las prácticas:', error);
        eliminarUsuario(idUsuario);
    }
}
// Comprueba si hay registros de estadísticas de los test realizados por el usuario que se quiere eliminar y los elimina
async function eliminaRealizaciones(idUsuario) {
    try {
        await fetch(`http://localhost:8080/realiza/deleteByUsuario/${idUsuario}`, {
            method: 'DELETE'
        });
        eliminarUsuario(idUsuario);
    } catch (error) {
        console.error('Error al comprobar las realizaciones:', error);
        eliminarUsuario(idUsuario);
    }
}
