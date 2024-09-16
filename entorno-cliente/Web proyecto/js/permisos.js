const listaPermisos=document.querySelector(".lista-permisos");

muestraPermisos();

function muestraPermisos(){
    fetch('http://localhost:8080/permiso/listado', {
        method: 'GET'
    })
    .then(response => response.json())
    .then(data => {

        // Limpiar la tabla antes de agregar nuevos datos
        listaPermisos.innerHTML = '';

        // Agregar cada permiso como una fila en la tabla
        data.forEach(permiso => {
            const divPermiso = document.createElement('a');
            divPermiso.classList='divPermiso';
            divPermiso.href = `permiso_${permiso.nombre}.html`;
            const divDatos = document.createElement('div');
            divDatos.classList='divDatos';
            const nombrePermiso = document.createElement('h2');
            nombrePermiso.textContent = permiso.nombre;
            // Modifica la descripción para mostrar solo el texto antes del primer punto "."
            const divDescripcion = document.createElement('div');
            divDescripcion.classList='divDescripcion';
            const descripcionPermisoPrincipal = document.createElement('p');
            descripcionPermisoPrincipal.classList='descripcionPrincipal';
            const descripcionPermisoSecundaria = document.createElement('p');
            descripcionPermisoSecundaria.classList='descripcionSecundaria';
            const puntoIndex = permiso.descripcion.indexOf(".");
            if (puntoIndex !== -1) {
                descripcionPermisoPrincipal.textContent = permiso.descripcion.slice(0, puntoIndex);
            } else {
                descripcionPermisoPrincipal.textContent = permiso.descripcion;
            }

            // Añade un evento de hover para mostrar el texto completo
            descripcionPermisoPrincipal.addEventListener('mouseover', () => {
                descripcionPermisoSecundaria.textContent = permiso.descripcion.slice(puntoIndex + 1);
            });

            // Añade un evento de salida del hover para volver a mostrar solo el texto antes del primer punto
            descripcionPermisoPrincipal.addEventListener('mouseout', () => {
                if (puntoIndex !== -1) {
                    descripcionPermisoPrincipal.textContent = permiso.descripcion.slice(0, puntoIndex);
                }
            });
            divPermiso.style.backgroundImage = `url('../imagenes/permiso_${permiso.nombre}.png')`;
            
            divDescripcion.append(descripcionPermisoPrincipal, descripcionPermisoSecundaria);
            divDatos.append(nombrePermiso, divDescripcion);
            divPermiso.append(divDatos);
            listaPermisos.append(divPermiso);

        });
    })
    .catch(error => {
        console.error('Se produjo un error al obtener los permisos:', error);
    });
}