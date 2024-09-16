const etiquetaDias = document.querySelector(".dias"),
    fechaActual = document.querySelector(".fecha-actual"),
    iconoPrevSiguiente = document.querySelectorAll(".iconos span"),
    turnoSeleccion = document.querySelector(".turno"),
    permisoSeleccion = document.querySelector(".permiso"),
    modal = document.getElementById('modal');

let fecha = new Date(),
    añoActual = fecha.getFullYear(),
    mesActual = fecha.getMonth();

const meses = [
    "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
    "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
];

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

            let clasesEnEsteDia = [];
            for (let j = 0; j < clases.length; j++) {
                if (
                    clases[j].fechahora[0] === añoActual &&
                    clases[j].fechahora[1] === mesActual + 1 &&
                    clases[j].fechahora[2] === i
                ) {
                    clasesEnEsteDia.push(clases[j]);
                }
            }

            const horasOcupadas = clasesEnEsteDia.map(clase => clase.fechahora[3]);
            etiquetaLi += `<div class="divEventos">`;

            for (let hora = 10; hora < 20; hora++) {
                if (filtraTurno(hora)) {
                    let estadoClase = horasOcupadas.includes(hora) ? 'ocupada' : 'disponible';
                    if (estadoClase === 'ocupada') {
                        const esMiReserva = clasesEnEsteDia.find(clase => clase.fechahora[3] === hora && clase.usuario.idusuario === parseInt(localStorage.getItem('usuarioId')));
                        if (esMiReserva) {
                            estadoClase = 'mi-reserva';
                        }
                    }
                    etiquetaLi += `<div class="clase ${estadoClase}" onclick="${estadoClase === 'disponible' ? 'seleccionarHora(this)' : ''}">${formatearHora(hora, 0)}</div>`;
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
};

const renderizarCalendarioVacio = () => {
    fechaActual.innerText = `${meses[mesActual]} ${añoActual}`;
    etiquetaDias.innerHTML = `<div class="mensaje-vacio">Debe acudir a su autoescuela para matricularse antes de poder reservar clases prácticas</div>`;
};

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

/* Filtra las horas del turno seleccionado */
const filtraTurno = (hora) => {
    const turno = turnoSeleccion.value;
    if (turno === 'mañana') {
        return hora >= 10 && hora <= 14;
    } else if (turno === 'tarde') {
        return hora >= 15 && hora <= 19;
    }
    return false; // Mostrar todas las clases si no se selecciona ningún turno
};

const formatearHora = (hora, minuto) => {
    let minutoFormateado = minuto < 10 ? `0${minuto}` : minuto;
    return `${hora}:${minutoFormateado}`;
};

let diaSeleccionado = null;
let horaSeleccionada = null;
let diaSeleccionadoAnterior = null; // Mantener una referencia al día seleccionado anteriormente

function seleccionarFecha(elemento) {
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

// Añadir la reserva seleccionada si se da click en confirmar
const confirmarBtn = document.getElementById('confirmar');

confirmarBtn.addEventListener('click', function () {
    if (horaSeleccionada === null) {
        alert('Por favor, seleccione una hora.');
        return;
    }

    const lugarSelect = document.getElementById('lugar'); // Obtener el elemento select
    const lugarSeleccionado = lugarSelect.options[lugarSelect.selectedIndex].text; // Obtener el texto seleccionado

    // Rellenar fechaHora con los datos seleccionados
    const fechaHora = [añoActual, mesActual + 1, diaSeleccionado, horaSeleccionada, 0];

    const practica = {
        fechahora: fechaHora,
        lugar: lugarSeleccionado,
        usuario: {
            idusuario: localStorage.getItem('usuarioId')
        },
        permiso: {
            idpermiso: permisoSeleccion.value
        }
    }

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
            console.log('practica registrado exitosamente', data);
            modal.style.display = "none";
            cargarClases();
        })
        .catch(error => {
            console.error('Error:', error);
        });
    console.log(practica);
});

/* Cerrar el modal si se hace clic en el botón de cancelar */
const cancelarBtn = document.getElementById('cancelar');
cancelarBtn.addEventListener('click', function () {
    const modal = document.getElementById('modal');
    modal.style.display = 'none';
});

// Agregar eventos de clic a las papeleras
function agregarEventosBorrado() {
    document.querySelectorAll('.papelera').forEach(papelera => {
        papelera.addEventListener('click', function () {
            const idPractica = this.dataset.id;
            mostrarModalConfirmacion(idPractica);
        });
    });
}

// Mostrar el modal de confirmación de borrado
function mostrarModalConfirmacion(idPractica) {
    const modalBorrar = document.getElementById('modal-confirmacion-borrar');
    modalBorrar.style.display = 'block';

    const confirmarBorrarBtn = document.getElementById('confirmar-borrar');
    confirmarBorrarBtn.onclick = function () {
        eliminarReserva(idPractica);
    };

    const cancelarBorrarBtn = document.getElementById('cancelar-borrar');
    cancelarBorrarBtn.onclick = function () {
        modalBorrar.style.display = 'none';
    };
}

// Eliminar la reserva
function eliminarReserva(idPractica) {
    fetch(`http://localhost:8080/practica/delete/${idPractica}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error en la solicitud');
            }
            return response.json();
        })
        .then(data => {
            console.log('Reserva eliminada exitosamente', data);
            const modalBorrar = document.getElementById('modal-confirmacion-borrar');
            modalBorrar.style.display = 'none';
            cargarClases();
        })
        .catch(error => {
            console.error('Error:', error);
        });
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

document.addEventListener("DOMContentLoaded", function () {
    cargarClases();
    cargarPermisos();
    agregarEventosBorrado();
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

function cargarClases() {
    cargarClasesTabla();
    cargarClasesCalendario();
}

function cargarClasesTabla() {
    // Obtener el usuarioId del almacenamiento local
    var usuarioId = localStorage.getItem('usuarioId');
    // URL del servicio web
    var url = `http://localhost:8080/practica/findByUsuarioIdusuarioOrderByFechahoraAsc?UsuarioIdUsuario=${usuarioId}`;

    // Realizar la solicitud GET
    fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error en la solicitud');
            }
            return response.json();
        })
        .then(data => {
            // Obtener el cuerpo de la tabla
            var tablaCuerpo = document.getElementById('tablaCuerpo');

            // Limpiar la tabla anterior (si la hay)
            tablaCuerpo.innerHTML = '';

            // Añadir cada clase como una fila en la tabla
            data.forEach(clase => {
                var row = document.createElement('tr');

                // Crear la celda para el día en el formato "27 de mayo"
                var diaCell = document.createElement('td');
                var fecha = new Date(clase.fechahora[0], clase.fechahora[1] - 1, clase.fechahora[2]);
                var dia = fecha.getDate();
                var mes = fecha.toLocaleString('default', { month: 'long' });
                diaCell.textContent = `${dia} de ${mes}`;

                // Crear la celda para la hora
                var horaCell = document.createElement('td');
                horaCell.textContent = formatearHora(clase.fechahora[3], clase.fechahora[4]);

                // Crear la celda para el lugar
                var lugarCell = document.createElement('td');
                lugarCell.textContent = clase.lugar;

                // Crear la celda para las acciones
                var accionesCell = document.createElement('td');
                
                // Añadir la clase 'clasePasada' si la fecha es anterior a hoy
                var hoy = new Date();
                hoy.setHours(0, 0, 0, 0); // Ajustar la hora para comparar solo fechas
                if (fecha < hoy) {
                    row.classList.add('clasePasada');
                } else {
                    var papeleraIcon = document.createElement('img');
                    papeleraIcon.src = '/../../imagenes/papelera.png';
                    papeleraIcon.className = 'material-symbols-rounded papelera';
                    papeleraIcon.dataset.id = clase.idpractica; // Asignar el id de la práctica
                    accionesCell.appendChild(papeleraIcon);
                }

                row.appendChild(diaCell);
                row.appendChild(horaCell);
                row.appendChild(lugarCell);
                row.appendChild(accionesCell);

                tablaCuerpo.appendChild(row);
            });
            agregarEventosBorrado();
        })
        .catch(error => {
            console.error('Error:', error);
        });
}


function cargarPermisos() {
    var usuarioId = localStorage.getItem('usuarioId');
    var url = `http://localhost:8080/matricula/${usuarioId}/permisos`;

    fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => {
            var permisoSelect = document.getElementById('permiso');
            permisoSelect.innerHTML = '';

            data.forEach(permiso => {
                var option = document.createElement('option');
                option.value = permiso.idpermiso;
                option.textContent = permiso.nombre;
                permisoSelect.appendChild(option);
            });

            if (data.length > 0) {
                permisoSelect.value = data[0].idpermiso;
                cargarClasesCalendario(); // Llamar a cargarClasesCalendario solo después de establecer el idPermiso
            } else {
                renderizarCalendarioVacio(); // Si no hay permisos, renderizar calendario vacío
            }

            cargarClases(); // Llamar a cargarClases después de cargar los permisos
        })
        .catch(error => {
            console.error('Error al obtener los permisos:', error);
            renderizarCalendarioVacio(); // Renderizar calendario vacío en caso de error
        });
}

/* Cargar las prácticas reservadas */
function cargarClasesCalendario() {
    var idPermiso = permisoSeleccion.value;
    var url = `http://localhost:8080/practica/permiso/${idPermiso}`;

    if (!idPermiso) {
        console.error('idPermiso no está seleccionado.');
        renderizarCalendarioVacio();
        return; // No realizar la solicitud si idPermiso es inválido
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
