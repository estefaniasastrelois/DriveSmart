const listaPermisos = document.querySelector(".lista-permisos");
const btnVolver = document.getElementById("btnVolver");
const btnGlobal = document.querySelector(".btnGlobal");
const btnTemas = document.querySelector(".btnTemas");
const permisoSeleccionado = document.querySelector(".permiso-seleccionado");
const contenedorSeleccionAmbito = document.querySelector(".contenedor-seleccion-ambito-oculto");
const contenedorTestFiltrados = document.querySelector(".contenedor-test-filtrados-oculto");
const pasoUno = document.querySelector(".pasoUno");
const pasoDos = document.querySelector(".pasoDos");
const tituloPrincipal = document.querySelector(".tituloPrincipal");
const modal = document.getElementById("modal");
const closeBtn = document.getElementsByClassName("close")[0];
const btnTerminar = document.getElementById("btnTerminar");
const btnCerrar = document.getElementById("btnCerrar");
const preguntasTest = document.querySelector(".preguntasTest");
const mensajeMatricula = document.getElementById("mensaje-matricula");

muestraPermisos();

function muestraPermisos() {
    // Obtiene el idUsuario de localStorage
    const idUsuario = localStorage.getItem("usuarioId");

    fetch(`http://localhost:8080/matricula/${idUsuario}/permisos`, {
        method: 'GET'
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.text(); // Cambiar a text() en lugar de json()
    })
    .then(text => {
        if (!text) {
            mensajeMatricula.classList.remove("mensaje-matricula-oculto");
            mensajeMatricula.classList.add("mensaje-matricula-visible");
            return;
        }

        const data = JSON.parse(text);
        // Limpiar la tabla antes de agregar nuevos datos
        listaPermisos.innerHTML = "";

        // Agregar cada permiso como una fila en la tabla
        data.forEach(permiso => {
            const divPermiso = document.createElement("a");
            divPermiso.classList = "divPermiso";
            const divDatos = document.createElement("div");
            divDatos.classList = "divDatos";
            const nombrePermiso = document.createElement("h2");
            nombrePermiso.textContent = permiso.nombre;
            nombrePermiso.classList='nombrePermiso';
            // Modifica la descripción para mostrar solo el texto antes del primer punto "."
            const divDescripcion = document.createElement("div");
            divDescripcion.classList = "divDescripcion";
            const descripcionPermisoPrincipal = document.createElement('p');
            const descripcionPermisoSecundaria = document.createElement('p');
            descripcionPermisoSecundaria.classList = "descripcionSecundaria";
            const puntoIndex = permiso.descripcion.indexOf(".");
            if (puntoIndex !== -1) {
                descripcionPermisoPrincipal.textContent = permiso.descripcion.slice(0, puntoIndex);
            } else {
                descripcionPermisoPrincipal.textContent = permiso.descripcion;
            }

            divPermiso.style.backgroundImage = `url('../../imagenes/permiso_${permiso.nombre}.png')`;

            divDescripcion.append(descripcionPermisoPrincipal, descripcionPermisoSecundaria);
            divDatos.append(nombrePermiso, divDescripcion);
            divPermiso.append(divDatos);
            listaPermisos.append(divPermiso);

            // Agregar evento click a cada divPermiso
            divPermiso.addEventListener('click', function () {
                permisoSeleccionado.append(divPermiso);
                listaPermisos.style.display = "none";
                contenedorSeleccionAmbito.classList = 'contenedor-seleccion-ambito-visible';
                permisoSeleccionado.style.display = "block";

                // Agregamos evento a los botones Global y Temas.
                btnGlobal.addEventListener("click", cambiarAmbito);
                btnTemas.addEventListener("click", cambiarAmbito);

                function cambiarAmbito() {
                    pasoUno.style.display = "none";
                    const nombreAmbitoSeleccionado = this.id === "btnGlobal" ? "global" : "temas"; //asigna el parámetro en función de la opción escogida
                    // Utiliza el operador + para concatenar los strings
                    tituloPrincipal.textContent = "Test del permiso " + permiso.nombre + " - " + nombreAmbitoSeleccionado;
                    filtrarTest(permiso.nombre, nombreAmbitoSeleccionado);
                }
            });
        });
    })
    .catch(error => {
        console.error('Se produjo un error al obtener los permisos:', error);
        mensajeMatricula.classList.remove("mensaje-matricula-oculto");
        mensajeMatricula.classList.add("mensaje-matricula-visible");
    });
}

btnVolver.addEventListener('click', function () {
    location.reload(); // Esto recargará la página
});

function filtrarTest(PermisoNombre, Ambito) {
    contenedorTestFiltrados.classList = "contenedor-test-filtrados-visible";

    fetch(`http://localhost:8080/test/findByPermisoNombreAndAmbito?PermisoNombre=${PermisoNombre}&Ambito=${Ambito}`, {
        method: 'GET'
    })
    .then(response => response.json())
    .then(data => {
        contenedorTestFiltrados.innerHTML = '';

        if (Ambito === "global") {
            const divTestGlobales = document.createElement("div");
            divTestGlobales.classList = "divTestGlobales";

            data.forEach(test => {
                const divTest = document.createElement("div");
                divTest.classList = "divTest";
                const lastUnderscoreIndex = test.referencia.lastIndexOf("_");
                const numeroTest = test.referencia.substring(lastUnderscoreIndex + 1);
                divTest.textContent = numeroTest;

                divTest.addEventListener("click", () => muestraVentanaTest(test.idtest)); // Añade el evento click

                divTestGlobales.appendChild(divTest);
            });

            contenedorTestFiltrados.appendChild(divTestGlobales);
        } else if (Ambito === "temas") {
            const temas = {};

            data.forEach(test => {
                const lastUnderscoreIndex = test.referencia.lastIndexOf("_");
                const numeroTest = test.referencia.substring(lastUnderscoreIndex + 1);
                const temaInfo = test.referencia.substring(test.referencia.lastIndexOf("T") + 1, lastUnderscoreIndex);
                const tema = "Tema " + temaInfo;

                if (!temas[tema]) {
                    temas[tema] = [];
                }

                const divTest = document.createElement('div');
                divTest.classList = 'divTest';
                divTest.textContent = numeroTest;

                divTest.addEventListener('click', () => muestraVentanaTest(test.idtest)); // Añade el evento click

                temas[tema].push(divTest);
            });

            for (let i = 1; i <= 4; i++) {
                const tema = "Tema " + i;
                if (!temas[tema]) {
                    temas[tema] = [];
                }
            }

            for (const tema in temas) {
                if (temas.hasOwnProperty(tema)) {
                    const divTema = document.createElement('div');
                    divTema.classList = 'divTema';
                    const tituloTema = document.createElement('h3');
                    tituloTema.textContent = tema;
                    const divTestTema = document.createElement('div');
                    divTestTema.classList = 'divTestTema';
                    divTema.append(tituloTema, divTestTema);

                    temas[tema].forEach(divTest => {
                        divTestTema.appendChild(divTest);
                    });

                    divTema.appendChild(divTestTema);
                    contenedorTestFiltrados.appendChild(divTema);
                }
            }
        }
    })
    .catch(error => {
        console.error('Se produjo un error al obtener los permisos:', error);
    });
}

// Cuando se haga clic en la X, cerrar el modal
closeBtn.onclick = function () {
    resetModal();
    modal.style.display = "none";
}

// AL DAR AL BOTÓN TERMINAR COMPRUEBA LOS RESULTADOS
btnTerminar.addEventListener('click', function () {
    const preguntas = document.querySelectorAll('.divPregunta');

    let respuestasCorrectas = 0;
    let todasRespondidas = true;
    let totalPreguntas = preguntas.length;

    const resultados = [];

    preguntas.forEach((pregunta, index) => {
        const enunciado = pregunta.querySelector('.enunciado').textContent;
        const respuestas = pregunta.querySelectorAll('.divRespuesta');
        let respuestaSeleccionada = null;
        let respuestaCorrecta = null;

        respuestas.forEach(respuesta => {
            // Guarda qué respuesta es la que fue seleccionada
            if (respuesta.classList.contains('seleccionado')) {
                respuestaSeleccionada = respuesta;
            }
            // Guarda cuál es la respuesta correcta
            if (respuesta.dataset.correcta === '1') {
                respuestaCorrecta = respuesta;
            }
        });
        // Verifica si hay una respuesta seleccionada, si no, marca que faltan respuestas
        if (!respuestaSeleccionada) {
            todasRespondidas = false;
        }

        // Si la seleccionada y la correcta son la misma, entonces es correcto y sube el contador
        const esCorrecta = respuestaSeleccionada === respuestaCorrecta;
        if (esCorrecta) {
            respuestasCorrectas++;
        }
        resultados.push({ enunciado, esCorrecta, respuestaCorrecta, respuestaSeleccionada });

    });

    // Si no todas las preguntas han sido respondidas, muestra el modal de mensaje
    if (!todasRespondidas) {
        const modalMensajeTestIncompleto = document.getElementById('modalMensajeTestIncompleto');
        modalMensajeTestIncompleto.style.display = 'block';

        setTimeout(() => {
            modalMensajeTestIncompleto.style.display = 'none';
        }, 3000);

        return;
    }

    // Calcula el número de fallos
    const fallos = totalPreguntas - respuestasCorrectas;

    // Mostrar el número de fallos
    const fallosTest = document.getElementById('fallosTest');
    fallosTest.textContent = `Número de fallos: ${fallos}`;
    fallosTest.classList.remove('oculto'); // Mostrar el mensaje de fallos

    // Iterar sobre los resultados para aplicar el estilo según si la respuesta es correcta o incorrecta
    resultados.forEach((resultado, index) => {
        const enunciado = resultado.enunciado;
        const respuestaCorrecta = resultado.respuestaCorrecta;
        const respuestaSeleccionada = resultado.respuestaSeleccionada;
        const esCorrecta = resultado.esCorrecta;

        // Obtener el div de la pregunta actual
        const divPregunta = Array.from(preguntas).find(pregunta => {
            return pregunta.querySelector('.enunciado').textContent === enunciado;
        });

        if (!divPregunta) {
            console.error(`No se encontró divPregunta para la pregunta ${index + 1}`);
            return;
        }

        // Obtener el divEnunciado de la pregunta actual
        const divEnunciado = divPregunta.querySelector('.divEnunciado');

        // Obtener todas las respuestas de la pregunta actual
        const respuestas = divPregunta.querySelectorAll('.divRespuesta');

        // Aplicar el estilo al divPregunta según si es correcta o incorrecta con colores claros
        if (esCorrecta) {
            divPregunta.style.backgroundColor = '#aff1bd'; // Verde claro
        } else {
            divPregunta.style.backgroundColor = '#f1b4b9'; // Rojo claro
        }

        // Aplicar el estilo al divEnunciado (fondo gris oscuro)
        divEnunciado.style.backgroundColor = '#616161';
        divEnunciado.style.color = 'white';

        // Iterar sobre las respuestas para aplicar el estilo
        respuestas.forEach(respuesta => {
            const respuestaTexto = respuesta.querySelector('.respuesta').textContent.trim();

            // Si la respuesta es la correcta, aplicar estilo verde
            if (respuesta.dataset.correcta === '1') {
                respuesta.style.backgroundColor = 'green';
                respuesta.style.color = 'white';
            }
            // Si la respuesta seleccionada es incorrecta, aplicar estilo rojo
            if (respuesta === respuestaSeleccionada && !esCorrecta) {
                respuesta.style.backgroundColor = 'red';
                respuesta.style.color = 'white';
            }
        });
    });

    // Añadir la clase no-hover para desactivar el hover
    preguntasTest.classList.add('no-hover');

    // Cambiar el título del modal
    const modalTitle = document.getElementById('modalTitle');
    modalTitle.textContent = 'CORRECCIÓN DEL TEST';
    modalTitle.style.color = 'var(--color-morado-fuerte)';

    // Mostrar el botón Cerrar y ocultar el botón Terminar
    btnTerminar.style.display = 'none';
    btnCerrar.style.display = 'block';

    // Cambiar el color del modal-content a gris
    document.querySelector('.ventanaTest .modal-content').classList.add('modal-content-gris');

    // Llamar a la función para guardar la estadística
    guardarEstadistica(respuestasCorrectas);
});

btnCerrar.addEventListener('click', function () {
    resetModal();
    modal.style.display = 'none';
});

function resetModal() {
    // Restablecer el color de fondo del modal-content a azul-suave
    document.querySelector('.ventanaTest .modal-content').classList.remove('modal-content-gris');
    document.querySelector('.ventanaTest .modal-content').classList.add('modal-content');

    // Restablecer el título del modal
    const modalTitle = document.getElementById('modalTitle');
    modalTitle.textContent = 'CONTENIDO DEL TEST';
    modalTitle.style.color = 'var(--color-morado-medio)';

    // Mostrar el botón Terminar y ocultar el botón Cerrar
    btnCerrar.style.display = 'none';
    btnTerminar.style.display = 'block';

    // Ocultar el mensaje de fallos
    const fallosTest = document.getElementById('fallosTest');
    fallosTest.classList.add('oculto');

    // Quitar la clase no-hover para permitir el hover en el próximo test
    preguntasTest.classList.remove('no-hover');
}


function muestraVentanaTest(idTest) {
    // Ocultar el mensaje de fallos cada vez que se abre un nuevo test
    const fallosTest = document.getElementById('fallosTest');
    fallosTest.classList.add('oculto');
    fallosTest.textContent = ''; // Limpiar el contenido del mensaje de fallos

    fetch(`http://localhost:8080/test/listado/${idTest}`)
    .then(response => response.json())
    .then(data => {
        preguntasTest.innerHTML = "";

        fetch(`http://localhost:8080/pregunta/findByTestIdtest?TestIdtest=${idTest}`)
        .then(response => response.json())
        .then(preguntas => {
            preguntas.forEach((pregunta, index) => {
                const divPregunta = document.createElement('div');
                divPregunta.classList = "divPregunta";

                const divEnunciado = document.createElement('div');
                divEnunciado.classList = "divEnunciado";
                const enunciado = document.createElement('span');
                enunciado.textContent = pregunta.enunciado;
                enunciado.classList = "enunciado";
                divEnunciado.append(enunciado);

                // Añadir la imagen si la foto no es null
                if (pregunta.foto) {
                    const imgPregunta = document.createElement('img');
                    imgPregunta.src = "../../imagenes/preguntas_test/"+pregunta.foto+".png";
                    imgPregunta.classList = "imgPregunta";
                    divEnunciado.append(imgPregunta);
                }

                const divRespuestas = document.createElement('div');
                divRespuestas.classList = "divRespuestas";

                fetch(`http://localhost:8080/opcion/findByPreguntaIdpregunta?PreguntaIdpregunta=${pregunta.idpregunta}`)
                .then(response => response.json())
                .then(opciones => {
                    opciones.forEach(opcion => {
                        const divRespuesta = document.createElement('div');
                        divRespuesta.classList = "divRespuesta opcion";
                        const flecha = document.createElement('img');
                        flecha.classList = "flecha";
                        flecha.src = '../../imagenes/flecha.png';
                        const respuesta = document.createElement('p');
                        respuesta.classList = "respuesta";
                        respuesta.textContent = opcion.respuesta;

                        // Si la opción es correcta, añade el atributo data-correcta
                        if (opcion.correcta === 1) {
                            divRespuesta.setAttribute('data-correcta', '1');
                        }

                        // Evento clic para seleccionar la respuesta
                        divRespuesta.addEventListener('click', function () {
                            // Desselecciona todas las respuestas dentro de la misma pregunta
                            divRespuestas.querySelectorAll('.divRespuesta').forEach(respuesta => {
                                respuesta.classList.remove('seleccionado');
                                respuesta.removeAttribute('data-seleccionado'); // Quita el atributo data-seleccionado
                            });

                            // Selecciona la respuesta clicada
                            divRespuesta.classList.add('seleccionado');
                            divRespuesta.setAttribute('data-seleccionado', '1'); // Añade el atributo data-seleccionado
                        });

                        divRespuesta.append(flecha, respuesta);
                        divRespuestas.append(divRespuesta);
                    });

                    divPregunta.append(divEnunciado, divRespuestas);
                    preguntasTest.append(divPregunta);
                });
            });

            // Se muestra la ventana modal con todo lo añadido anteriormente
            modal.style.display = "block";
        });
        // Asignar el atributo data-idtest al modal con el ID del test
        modal.setAttribute('data-idtest', idTest);

        // Se muestra la ventana modal con todo lo añadido anteriormente
        modal.style.display = "block";
    })
    .catch(error => {
        console.error('Error:', error);
    });
}


function guardarEstadistica(respuestasCorrectas) {
    // Obtener ID del usuario del localStorage
    const idUsuario = localStorage.getItem('usuarioId');
    // Obtener ID del test del atributo data-idtest del modal
    const idTest = modal.getAttribute('data-idtest');

    // Obtener datos del usuario mediante una solicitud GET
    fetch(`http://localhost:8080/usuario/listado/${idUsuario}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al obtener los datos del usuario');
            }
            return response.json();
        })
        .then(usuario => {
            // Obtener datos del test mediante una solicitud GET
            fetch(`http://localhost:8080/test/listado/${idTest}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Error al obtener los datos del test');
                    }
                    return response.json();
                })
                .then(test => {
                    // Obtener la fecha y hora actual del sistema del cliente
                    const fechaHoraLocal = new Date();
                    // Ajustar la hora según la zona horaria correcta (por ejemplo, +2 horas)
                    fechaHoraLocal.setHours(fechaHoraLocal.getHours() + 2); // ajuste de 2 horas
                    // Convertir la fecha y hora ajustadas a formato ISO
                    const fechaHoraAjustada = fechaHoraLocal.toISOString();

                    // Datos a enviar en la solicitud POST
                    const data = {
                        usuario: usuario,
                        test: test,
                        nota: respuestasCorrectas,
                        fechahora: fechaHoraLocal
                    };

                    // Configurar la solicitud POST
                    const options = {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(data)
                    };

                    // Realizar la solicitud POST
                    fetch('http://localhost:8080/realiza/add', options)
                        .then(response => {
                            if (!response.ok) {
                                throw new Error('Error al guardar la estadística');
                            }
                            return response.json();
                        })
                        .then(data => {
                            console.log('Estadística guardada exitosamente:', data);
                        })
                        .catch(error => {
                            console.error('Error al guardar la estadística:', error);
                        });
                })
                .catch(error => {
                    console.error('Error al obtener los datos del test:', error);
                });
        })
        .catch(error => {
            console.error('Error al obtener los datos del usuario:', error);
        });
}
