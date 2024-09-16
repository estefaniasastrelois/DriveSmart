/*-------------------------------------DECLARACIÓN DE VARIABLES---------------------------------------*/
const fechaInput = document.getElementById("fecha-input");
const nombreApellidosInput = document.getElementById("nombre-apellidos-input");
const btnVistaTabla = document.getElementById("btnVistaTabla");
const btnVistaCalendario = document.getElementById("btnVistaCalendario");
const vistaTabla = document.querySelector(".vistaTabla");
const vistaCalendario = document.querySelector(".vistaCalendario");
const mensajeClases = document.getElementById("mensaje-clases");
const permisoSeleccion = document.getElementById("permiso");
const permisoTablaSeleccion = document.getElementById("permiso-tabla");

let fechaSeleccionada = fechaInput.value || new Date().toISOString().split('T')[0];

const etiquetaDias = document.querySelector(".dias"),
    fechaActual = document.querySelector(".fecha-actual"),
    iconoPrevSiguiente = document.querySelectorAll(".iconos span"),
    turnoSeleccion = document.querySelector(".turno"),
    modal = document.getElementById('modal');

let fecha = new Date(),
    añoActual = fecha.getFullYear(),
    mesActual = fecha.getMonth();

const meses = [
    "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
    "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
];

/* Variables para el modal de confirmación */
const confirmModal = document.getElementById("confirmModal");
const confirmYes = document.getElementById("confirmYes");
const confirmNo = document.getElementById("confirmNo");
let reservaSeleccionada = null;



/*-------------------------------------ACCIONES GENERALES---------------------------------------*/
// Inicializar la vista
vistaTabla.classList.add("mostrar");
vistaCalendario.classList.add("oculto");

btnVistaTabla.addEventListener("click", function () {
    vistaTabla.classList.add("mostrar");
    vistaTabla.classList.remove("oculto");
    vistaCalendario.classList.add("oculto");
    vistaCalendario.classList.remove("mostrar");
});

btnVistaCalendario.addEventListener("click", function () {
    vistaCalendario.classList.add("mostrar");
    vistaCalendario.classList.remove("oculto");
    vistaTabla.classList.add("oculto");
    vistaTabla.classList.remove("mostrar");
});

// Función para normalizar los caracteres con tilde - ignorar tildes en el buscador
const normalizarTexto = (texto) => {
    return texto.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
};





/*-------------------------------------TABLA DE PRÁCTICAS - AGENDA---------------------------------------*/
// Función para buscar prácticas por día, nombre/apellidos y permiso
function obtenerPracticas(fecha = null, nombreApellidos = "", idPermiso = "") {
    const nombreApellidosNormalizado = normalizarTexto(nombreApellidos);

    let url;
    if (fecha) {
        url = `http://localhost:8080/practica/findPracticasByDay?day=${fecha}T00:00:00`;
    } else {
        url = 'http://localhost:8080/practica/listado';
    }

    fetch(url)
        .then(response => response.json())
        .then(data => {
            const practicasFiltradas = data.filter(practica => {
                const nombreCompleto = `${practica.usuario.nombre} ${practica.usuario.apellidos}`;
                const nombreCompletoNormalizado = normalizarTexto(nombreCompleto);
                const coincideNombre = nombreCompletoNormalizado.toLowerCase().includes(nombreApellidosNormalizado.toLowerCase());
                const coincidePermiso = !idPermiso || practica.permiso.idpermiso === parseInt(idPermiso);
                return coincideNombre && coincidePermiso;
            });

            actualizarTablaPracticas(practicasFiltradas);

            if (practicasFiltradas.length === 0) {
                mensajeClases.innerText = 'No se encontró ninguna práctica programada.';
            } else {
                mensajeClases.innerText = '';
            }
        })
        .catch(error => {
            console.error('Error:', error);
            actualizarTablaPracticas([]);  // Vaciar la tabla en caso de error
            mensajeClases.innerText = 'No se encontró ninguna práctica programada.';
        });
}
// Función para actualizar la tabla con las prácticas filtradas
function actualizarTablaPracticas(practicas) {
    const tablaCuerpo = document.getElementById('tablaCuerpo');
    tablaCuerpo.innerHTML = '';

    practicas.forEach(practica => {
        const row = document.createElement('tr');
        const fechaHora = practica.fechahora;
        const fecha = `${fechaHora[2]}/${fechaHora[1]}/${fechaHora[0]}`;
        const hora = `${fechaHora[3].toString().padStart(2, '0')}:00`;

        const fechaCell = document.createElement('td');
        fechaCell.textContent = fecha;
        const horaCell = document.createElement('td');
        horaCell.textContent = hora;
        const lugarCell = document.createElement('td');
        lugarCell.textContent = practica.lugar;
        const usuarioCell = document.createElement('td');
        usuarioCell.textContent = `${practica.usuario.nombre} ${practica.usuario.apellidos}`;
        const permisoCell = document.createElement('td');
        permisoCell.textContent = practica.permiso.nombre;
        const eliminarCell = document.createElement('td');
        eliminarCell.innerHTML = `<i class='bx bxs-trash' onclick="lanzarConfirmacion(${practica.idpractica})"></i>`; // Nueva celda para eliminar

        row.appendChild(fechaCell);
        row.appendChild(horaCell);
        row.appendChild(lugarCell);
        row.appendChild(usuarioCell);
        row.appendChild(permisoCell);
        row.appendChild(eliminarCell);
        tablaCuerpo.appendChild(row);
    });
}

// Función para lanzar la confirmación de eliminación de reserva
function lanzarConfirmacion(idReserva) {
    reservaSeleccionada = idReserva;
    confirmModal.style.display = "block";
}

confirmYes.onclick = function() {
    eliminarReserva(reservaSeleccionada);
}

confirmNo.onclick = function() {
    confirmModal.style.display = "none";
}

// Función para eliminar la reserva
function eliminarReserva(idReserva) {
    fetch(`http://localhost:8080/practica/delete/${idReserva}`, {
        method: 'DELETE'
    })
    .then(response => {
        if (response.ok) {
            confirmModal.style.display = "none";
            obtenerPracticas(fechaSeleccionada, nombreApellidosInput.value, permisoTablaSeleccion.value);
            cargarClasesCalendario(); // Para actualizar el calendario
        } else {
            console.error('Error al eliminar la reserva.');
        }
    })
    .catch(error => console.error('Se produjo un error al eliminar la reserva:', error));
}


// Event listeners para los filtros
fechaInput.addEventListener("change", function () {
    fechaSeleccionada = fechaInput.value;
    obtenerPracticas(fechaSeleccionada, nombreApellidosInput.value, permisoTablaSeleccion.value);
});

nombreApellidosInput.addEventListener("input", function () {
    obtenerPracticas(fechaSeleccionada, nombreApellidosInput.value, permisoTablaSeleccion.value);
});

permisoTablaSeleccion.addEventListener("change", function () {
    obtenerPracticas(fechaSeleccionada, nombreApellidosInput.value, permisoTablaSeleccion.value);
});

fechaInput.addEventListener("input", function () {
    if (!fechaInput.value) {
        obtenerPracticas(null, nombreApellidosInput.value, permisoTablaSeleccion.value);
    }
});
// Cargar las prácticas al iniciar la página
obtenerPracticas(fechaSeleccionada, nombreApellidosInput.value, permisoTablaSeleccion.value);





/*-------------------------------------CALENDARIO DE PRÁCTICAS---------------------------------------*/
// Agregar eventos a las clases ocupadas
function agregarEventosClasesOcupadas() {
    const clasesOcupadas = document.querySelectorAll('.clase.ocupada');

    clasesOcupadas.forEach(clase => {
        clase.addEventListener('mouseover', mostrarDetallesReserva);
        clase.addEventListener('mouseout', ocultarDetallesReserva);
    });
}
// Función para obtener los detalles de la reserva y mostrarlos en el modal de modal-detalles-reserva al pasar el ratón por encima (mouseover)
function mostrarDetallesReserva(event) {
    const modalDetallesReserva = document.getElementById('modal-detalles-reserva');
    const detallesReservaTexto = document.getElementById('detalles-reserva-texto');
    const practica = JSON.parse(event.target.getAttribute('data-practica'));

    if (practica) {
        detallesReservaTexto.innerText = `Lugar: ${practica.lugar}\nUsuario: ${practica.usuario.nombre} ${practica.usuario.apellidos}`;
        modalDetallesReserva.style.display = 'block';

        // Obtener la posición del ratón
        const x = event.clientX;
        const y = event.clientY;

        // Ajustar la posición del modal para que aparezca justo a la derecha del ratón
        modalDetallesReserva.style.top = `${y + window.scrollY}px`;
        modalDetallesReserva.style.left = `${x + 10 + window.scrollX}px`;
    }
}
// Función para ocultar el modal-detalles-reserva cuando se quite el ratón de encima (mouseout)
function ocultarDetallesReserva() {
    const modalDetallesReserva = document.getElementById('modal-detalles-reserva');
    modalDetallesReserva.style.display = 'none';
}
//Función para renderizar el calendario
const renderizarCalendario = (clases = []) => {
    let primerDiaDelMes = (new Date(añoActual, mesActual, 1).getDay() + 6) % 7,
        ultimaFechaDelMes = new Date(añoActual, mesActual + 1, 0).getDate(),
        ultimoDiaDelMes = (new Date(añoActual, mesActual, ultimaFechaDelMes).getDay() + 6) % 7,
        ultimaFechaDelMesPasado = new Date(añoActual, mesActual, 0).getDate();
    let etiquetaLi = "";

    for (let i = primerDiaDelMes; i > 0; i--) {
        etiquetaLi += `<li class="inactivo">
                    <div class="divDia">${ultimaFechaDelMesPasado - i + 1}</div>
                    <div class="divEventos" style="background-color: #ccc;"></div>
                </li>`;
    }

    for (let i = 1; i <= ultimaFechaDelMes; i++) {
        let esHoy =
            i === fecha.getDate() &&
                mesActual === new Date().getMonth() &&
                añoActual === new Date().getFullYear()
                ? "activo"
                : "";

        let diaDeLaSemana = (new Date(añoActual, mesActual, i).getDay() + 6) % 7;
        let esFinDeSemana = diaDeLaSemana === 5 || diaDeLaSemana === 6 ? "fin-de-semana" : "";
        let claseDiaActual = esHoy ? "diaActual" : "";

        if (diaDeLaSemana < 5 && (añoActual > new Date().getFullYear() || (añoActual === new Date().getFullYear() && mesActual > new Date().getMonth()) || (añoActual === new Date().getFullYear() && mesActual === new Date().getMonth() && i >= new Date().getDate()))) { // Solo mostrar días de lunes a viernes y a partir de hoy
            etiquetaLi += `<li class="${esHoy} ${esFinDeSemana}" onclick="seleccionarFecha(this)">
                    <div class="divDia ${claseDiaActual}">${i}</div>`;

            const clasesEnEsteDia = clases.filter(
                (clase) =>
                    clase.fechahora[0] === añoActual &&
                    clase.fechahora[1] === mesActual + 1 &&
                    clase.fechahora[2] === i
            );

            const horasOcupadas = clasesEnEsteDia.map(clase => clase.fechahora[3]);
            etiquetaLi += `<div class="divEventos">`;

            for (let hora = 10; hora < 20; hora++) {
                if (filtraTurno(hora)) {
                    let estadoClase = horasOcupadas.includes(hora) ? 'ocupada' : 'disponible';
                    etiquetaLi += `<div class="clase ${estadoClase}" ${estadoClase === 'ocupada' ? `data-practica='${JSON.stringify(clasesEnEsteDia.find(clase => clase.fechahora[3] === hora))}'` : ''} onclick="${estadoClase === 'disponible' ? 'seleccionarHora(this)' : ''}">${formatearHora(hora, 0)}</div>`;
                }
            }

            etiquetaLi += `</div></li>`;
        } else { // Fines de semana o días pasados
            etiquetaLi += `<li class="inactivo ${esFinDeSemana}">
                    <div class="divDia ${claseDiaActual}">${i}</div>
                    <div class="divEventos"></div>
                    </li>`;
        }
    }

    for (let i = ultimoDiaDelMes; i < 6; i++) {
        etiquetaLi += `<li class="inactivo">
                    <div class="divDia">${i - ultimoDiaDelMes + 1}</div>
                    <div class="divEventos" style="background-color: #ccc;"></div>
                </li>`;
    }

    fechaActual.innerText = `${meses[mesActual]} ${añoActual}`;
    etiquetaDias.innerHTML = etiquetaLi;

    // Agregar los eventos después de renderizar el calendario
    agregarEventosClasesOcupadas();
};

//Maneja el classList de la clase seleccionada, para que cambie de color
function seleccionarHora(elemento) {
    // Remover la clase 'seleccionada' de cualquier hora previamente seleccionada
    const previamenteSeleccionado = document.querySelector('.clase.seleccionada');
    if (previamenteSeleccionado) {
        previamenteSeleccionado.classList.remove('seleccionada');
    }
    // Añadir la clase 'seleccionada' a la hora seleccionada
    elemento.classList.add('seleccionada');

    // Capturar la hora seleccionada
    horaSeleccionada = parseInt(elemento.textContent);
}

// Filtra las horas del turno seleccionado 
const filtraTurno = (hora) => {
    const turno = turnoSeleccion.value;
    if (turno === 'mañana') {
        return hora >= 10 && hora <= 14;
    } else if (turno === 'tarde') {
        return hora >= 15 && hora <= 19;
    }
    return false; // Mostrar todas las clases si no se selecciona ningún turno
};
// Formatea la hora
const formatearHora = (hora, minuto) => {
    let minutoFormateado = minuto < 10 ? `0${minuto}` : minuto;
    return `${hora}:${minutoFormateado}`;
};

let diaSeleccionado = null;
let horaSeleccionada = null;
let diaSeleccionadoAnterior = null; // Mantener una referencia al día seleccionado anteriormente

function seleccionarFecha(elemento) { // Hacer la función global
    if (elemento.classList.contains('fin-de-semana') || elemento.classList.contains('inactivo')) {
        return;
    }
    let horaSeleccionadaElemento = elemento.querySelector('.clase.seleccionada');
    if (!horaSeleccionadaElemento) {
        alert('Por favor, seleccione una hora disponible.');
        return;
    }

    // Eliminar la clase 'diaSeleccionado' del día seleccionado anteriormente
    if (diaSeleccionadoAnterior !== null) {
        diaSeleccionadoAnterior.classList.remove('diaSeleccionado');
    }

    diaSeleccionado = parseInt(elemento.querySelector('.divDia').textContent);
    const horaSeleccionada = horaSeleccionadaElemento.textContent;
    const mesSeleccionado = meses[mesActual];

    const modalMessage = document.getElementById('modal-message');
    modalMessage.innerHTML = `¿Desea reservar el <span class="destacar">día ${diaSeleccionado} de ${mesSeleccionado}</span> a las <span class="destacar">${horaSeleccionada}</span>?`;

    // Agregar clase 'diaSeleccionado' al día seleccionado y cambiar color desde CSS
    const divDiaSeleccionado = elemento.querySelector('.divDia');
    divDiaSeleccionado.classList.add('diaSeleccionado');

    diaSeleccionadoAnterior = divDiaSeleccionado; // Actualizar la referencia al día seleccionado anteriormente

    const modal = document.getElementById('modal');
    modal.style.display = 'block';
}

/* Cargar clases cada vez que se cambie el turno o permiso seleccionado */
turnoSeleccion.addEventListener("change", cargarClases);
permisoSeleccion.addEventListener("change", cargarClases);

/* Cargar calendario si se cambia de mes */
iconoPrevSiguiente.forEach((icono) => {
    icono.addEventListener("click", () => {
        mesActual = icono.id === "anterior" ? mesActual - 1 : mesActual + 1;

        if (mesActual < 0 || mesActual > 11) {
            fecha = new Date(añoActual, mesActual, new Date().getDate());
            añoActual = fecha.getFullYear();
            mesActual = fecha.getMonth();
        }
        cargarClasesCalendario();
    });
});

/* Manejo del cambio de vista */
document.addEventListener("DOMContentLoaded", function () {
    const btnVistaTabla = document.getElementById("btnVistaTabla");
    const btnVistaCalendario = document.getElementById("btnVistaCalendario");
    const vistaTabla = document.querySelector(".vistaTabla");
    const vistaCalendario = document.querySelector(".vistaCalendario");

    // Inicialmente, ocultar ambas vistas
    vistaTabla.classList.add("oculto");
    vistaCalendario.classList.add("oculto");

    // Mostrar y animar la vista deseada en la carga inicial
    // Vamos a mostrar "vistaTabla" con la animación de entrada
    vistaTabla.classList.remove("oculto");
    vistaTabla.classList.add("mostrar", "desdeDerechaInicial");

    // Asignar eventos a los botones para cambiar entre vistas
    btnVistaTabla.addEventListener("click", function () {
        if (!vistaTabla.classList.contains("mostrar")) {
            vistaTabla.classList.add("mostrar");
            vistaTabla.classList.remove("oculto", "desdeCentro");
            vistaTabla.classList.add("desdeDerecha");
        }
        if (vistaCalendario.classList.contains("mostrar")) {
            vistaCalendario.classList.remove("mostrar");
            vistaCalendario.classList.add("oculto", "desdeCentro");
        }
    });

    btnVistaCalendario.addEventListener("click", function () {
        if (!vistaCalendario.classList.contains("mostrar")) {
            vistaCalendario.classList.add("mostrar");
            vistaCalendario.classList.remove("oculto", "desdeCentro");
            vistaCalendario.classList.add("desdeDerecha");
        }
        if (vistaTabla.classList.contains("mostrar")) {
            vistaTabla.classList.remove("mostrar");
            vistaTabla.classList.add("oculto", "desdeCentro");
        }
    });
});





/*-------------------------------------CARGAR LAS CLASES EN AMBAS VISTAS---------------------------------------*/
function cargarClases() {
    cargarClasesCalendario();
    cargarClasesTabla();
}

/* Cargar las prácticas reservadas en el calendario */
function cargarClasesCalendario() {
    const idPermiso = permisoSeleccion.value;
    let url = 'http://localhost:8080/practica/listado';
    if (idPermiso) {
        url = `http://localhost:8080/practica/permiso/${idPermiso}`;
    }

    fetch(url)
        .then(response => response.json())
        .then(clases => {
            renderizarCalendario(clases);
        })
        .catch(error => {
            console.error('Error al obtener las clases:', error);
        });
}

function cargarClasesTabla() {
    const fechaInput = document.getElementById("fecha-input");
    const nombreApellidosInput = document.getElementById("nombre-apellidos-input");

    // Obtener la fecha actual
    const fechaActual = new Date().toISOString().split('T')[0]; // Formato YYYY-MM-DD

    // Establecer la fecha actual como valor por defecto en el input
    fechaInput.value = fechaActual;

    // Escuchar cambios en la fecha seleccionada
    fechaInput.addEventListener("change", function () {
        const fechaSeleccionada = fechaInput.value;
        const nombreApellidos = nombreApellidosInput.value;
        obtenerPracticas(fechaSeleccionada, nombreApellidos, permisoTablaSeleccion.value);
    });

    // Escuchar cambios en el campo de nombre y apellidos
    nombreApellidosInput.addEventListener("input", function () {
        const fechaSeleccionada = fechaInput.value;
        const nombreApellidos = nombreApellidosInput.value;
        obtenerPracticas(fechaSeleccionada, nombreApellidos, permisoTablaSeleccion.value);
    });

    permisoTablaSeleccion.addEventListener("change", function () {
        const fechaSeleccionada = fechaInput.value;
        const nombreApellidos = nombreApellidosInput.value;
        obtenerPracticas(fechaSeleccionada, nombreApellidos, permisoTablaSeleccion.value);
    });

    fechaInput.addEventListener("input", function () {
        if (!fechaInput.value) {
            obtenerPracticas(null, nombreApellidosInput.value, permisoTablaSeleccion.value);
        }
    });

    // Obtener prácticas para la fecha actual por defecto al cargar la página
    obtenerPracticas(fechaActual, nombreApellidosInput.value, permisoTablaSeleccion.value);
}

// Inicializar la carga de clases
cargarClases();
cargarPermisos();

function cargarPermisos() {
    fetch('http://localhost:8080/permiso/listado')
        .then(response => response.json())
        .then(data => {
            permisoSeleccion.innerHTML = '';
            permisoTablaSeleccion.innerHTML = '<option value="">Todos</option>';

            data.forEach(permiso => {
                var option = document.createElement('option');
                option.value = permiso.idpermiso;
                option.textContent = permiso.nombre;
                permisoSeleccion.appendChild(option);

                var optionTabla = document.createElement('option');
                optionTabla.value = permiso.idpermiso;
                optionTabla.textContent = permiso.nombre;
                permisoTablaSeleccion.appendChild(optionTabla);
            });

            cargarClases();
        })
        .catch(error => {
            console.error('Error al obtener los permisos:', error);
        });
}





/*-------------------------------------MODAL DE NUEVA RESERVA---------------------------------------*/
document.addEventListener("DOMContentLoaded", function () {
    const usuarioBusqueda = document.getElementById('usuario-busqueda');
    const usuarioResultados = document.getElementById('usuario-resultados');
    let usuarios = [];
    let usuarioSeleccionado = null;

    // Cargar todos los usuarios al abrir el modal
    fetch('http://localhost:8080/usuario/listado')
        .then(response => response.json())
        .then(data => {
            usuarios = data;
        })
        .catch(error => {
            console.error('Error al cargar usuarios:', error);
        });

    // Filtrar usuarios en tiempo real y mostrar resultados
    usuarioBusqueda.addEventListener('input', function () {
        const query = usuarioBusqueda.value.trim().toLowerCase();
        if (query) {
            const usuariosFiltrados = usuarios.filter(usuario => {
                const nombreCompleto = normalizarTexto(`${usuario.nombre} ${usuario.apellidos}`.toLowerCase());
                return nombreCompleto.includes(query);
            });
            mostrarResultadosUsuarios(usuariosFiltrados);
        } else {
            usuarioResultados.style.display = 'none';
        }
    });

    usuarioBusqueda.addEventListener('focus', function () {
        if (usuarioBusqueda.value.trim()) {
            usuarioResultados.style.display = 'block';
        }
    });

    document.addEventListener('click', function (event) {
        if (!usuarioBusqueda.contains(event.target) && !usuarioResultados.contains(event.target)) {
            usuarioResultados.style.display = 'none';
        }
    });

    function mostrarResultadosUsuarios(usuarios) {
        usuarioResultados.innerHTML = '';
        if (usuarios.length > 0) {
            usuarios.forEach(usuario => {
                const div = document.createElement('div');
                div.textContent = `${usuario.nombre} ${usuario.apellidos}`;
                div.dataset.usuarioId = usuario.idusuario;
                div.classList.add('usuario-item');
                div.addEventListener('click', function () {
                    seleccionarUsuario(usuario);
                });
                usuarioResultados.appendChild(div);
            });
            usuarioResultados.style.display = 'block';
        } else {
            usuarioResultados.style.display = 'none';
        }
    }

    function seleccionarUsuario(usuario) {
        usuarioSeleccionado = usuario;
        usuarioBusqueda.value = `${usuario.nombre} ${usuario.apellidos}`;
        usuarioResultados.style.display = 'none';
    }

    // Modificar la función de confirmación para usar el usuario seleccionado
    const confirmarBtn = document.getElementById('confirmar');
    confirmarBtn.addEventListener('click', function () {
        if (!usuarioSeleccionado) {
            alert('Por favor, seleccione un usuario.');
            return;
        }

        const lugarSelect = document.getElementById('lugar');
        const lugarSeleccionado = lugarSelect.options[lugarSelect.selectedIndex].text;

        const fechaHora = [añoActual, mesActual + 1, diaSeleccionado, horaSeleccionada, 0];

        const practica = {
            fechahora: fechaHora,
            lugar: lugarSeleccionado,
            usuario: {
                idusuario: usuarioSeleccionado.idusuario
            },
            permiso: {
                idpermiso: permisoSeleccion.value
            }
        };

        fetch('http://localhost:8080/practica/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(practica)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error en la solicitud');
                }
                return response.json();
            })
            .then(data => {
                console.log('Práctica registrada exitosamente', data);
                modal.style.display = "none";
                cargarClases();
            })
            .catch(error => {
                console.error('Error:', error);
            });
        console.log(practica);
    });

    // Event listeners para cerrar el modal
    const cancelarBtn = document.getElementById('cancelar');
    cancelarBtn.addEventListener('click', function () {
        modal.style.display = 'none';
    });
});
