// Obtener el modal y el botón de cierre
const modal = document.getElementById("modal");
const closeBtn = document.getElementsByClassName("close")[0];
const confirmModal = document.getElementById("confirmModal");
const confirmYes = document.getElementById("confirmYes");
const confirmNo = document.getElementById("confirmNo");
const permisosBody = document.getElementById('permisosBody');
const btnGuardar = document.getElementById('guardar');

// Obtener el botón "Nuevo permiso"
const nuevoPermisoBtn = document.querySelector(".nuevo");

// Cuando se haga clic en el botón "Nuevo permiso", mostrar el modal
nuevoPermisoBtn.onclick = function() {
    //Mostramos la ventana modal
    modal.style.display = "block";
    document.getElementById('modalTitle').textContent = "Nuevo permiso";
    document.getElementById('permisoId').value = ""; // Limpiar el campo ID
    document.getElementById('nombre').value = ""; // Limpiar el campo nombre
    document.getElementById('descripcion').value = ""; // Limpiar el campo descripción
}

// Cuando se haga clic en la X, cerrar el modal
closeBtn.onclick = function() {
    modal.style.display = "none";
}

// Cuando se haga clic fuera del modal, cerrar el modal
window.onclick = function(even) {
    if (even.target == modal) {
        modal.style.display = "none";
    }
}

// Agregar evento de clic al botón "Guardar"
btnGuardar.addEventListener("click", function() {
     // Aquí obtienes los valores de los campos de entrada (nombre y descripcion)
    const permisoId = document.getElementById('permisoId').value;
    const nombre = document.getElementById("nombre").value;
    const descripcion = document.getElementById("descripcion").value;
    
    if (nombre === '' || descripcion === '') {
        console.error('Por favor, complete todos los campos.');
        return;
    }

    const permiso = {
        nombre: nombre,
        descripcion: descripcion
    };

    let url = 'http://localhost:8080/permiso/add';
    let method = 'POST';
    if (permisoId) {
        url = `http://localhost:8080/permiso/update/${permisoId}`;
        method = 'PUT';
    }
    
    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(permiso)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Error en la solicitud');
        }
        return response.json();
    })
    .then(data => {
        console.log('Permiso registrado exitosamente', data);
        modal.style.display = "none";
        muestraPermisos();
    })
    .catch(error => {
        console.error('Error:', error);
    });
});

// Función para cargar los detalles de un permiso para editar
function muestraVentanaEdicion(idPermiso) {
    fetch(`http://localhost:8080/permiso/listado/${idPermiso}`)
    .then(response => response.json())
    .then(data => {
        document.getElementById('modalTitle').textContent = "Editar permiso";
        document.getElementById('permisoId').value = data.idpermiso;
        document.getElementById('nombre').value = data.nombre;
        document.getElementById('descripcion').value = data.descripcion;
        modal.style.display = "block";
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

// Función para eliminar un permiso
function eliminarPermiso(idPermiso) {
    confirmModal.style.display="none";
    fetch(`http://localhost:8080/permiso/delete/${idPermiso}`, {
        method: 'DELETE'
    })
    .then(response => {
        if (response.ok) {
            // Eliminación exitosa, actualiza la lista de permisos
            muestraPermisos();
        } else {
            console.error('Error al eliminar el permiso.');
        }
    })
    .catch(error => {
        console.error('Se produjo un error al eliminar el permiso:', error);
    });
}

// Función para mostrar la lista de permisos
function muestraPermisos() {
    fetch('http://localhost:8080/permiso/listado', {
        method: 'GET'
    })
    .then(response => response.json())
    .then(data => {
        // Limpiar la tabla antes de agregar nuevos datos
        permisosBody.innerHTML = '';

        // Agregar cada permiso como una fila en la tabla
        data.forEach(permiso => {
            const fila = document.createElement('tr');
            const columnaNombre = document.createElement('td');
            columnaNombre.classList = "nombre";
            const columnaDescripcion = document.createElement('td');
            columnaDescripcion.classList = "descripcion";

            // Nueva columna para el ícono de edición
            const columnaEditar = document.createElement('td');
            columnaEditar.classList = "editar";
            const iconoEditar = document.createElement('i');
            iconoEditar.classList = 'bx bxs-edit-alt';
            iconoEditar.addEventListener('click', () => muestraVentanaEdicion(permiso.idpermiso));
            columnaEditar.appendChild(iconoEditar);

            // Nueva columna para el ícono de eliminación
            const columnaEliminar = document.createElement('td');
            columnaEliminar.classList = "eliminar";
            const iconoEliminar = document.createElement('i');
            iconoEliminar.classList = 'bx bxs-trash';
            iconoEliminar.addEventListener('click', () => lanzarConfirmacion(permiso.idpermiso));
            columnaEliminar.appendChild(iconoEliminar);

            columnaNombre.textContent = permiso.nombre;
            columnaDescripcion.textContent = permiso.descripcion;

            fila.appendChild(columnaNombre);
            fila.appendChild(columnaDescripcion);
            fila.appendChild(columnaEditar);
            fila.appendChild(columnaEliminar);

            permisosBody.appendChild(fila);
        });
    })
    .catch(error => {
        console.error('Se produjo un error al obtener los permisos:', error);
    });
}

function lanzarConfirmacion(idPermiso){
    confirmModal.style.display="block";
    // Usamos una función anónima para que elimine el permiso solo cuando se confirme
    confirmYes.addEventListener("click", function() {
        eliminarPermiso(idPermiso);
    });
}

// Cuando se haga clic en la X, cerrar el modal
confirmNo.onclick = function() {
    confirmModal.style.display = "none";
}

// Al cargar el contenido, mostrar la lista de permisos
document.addEventListener('DOMContentLoaded', muestraPermisos);
