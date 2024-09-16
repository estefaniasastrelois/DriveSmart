/* -------------------------------------DECLARACIÓN DE VARIABLES-------------------------------*/
const nuevoTestBtn = document.querySelector('.nuevoTestBtn')
const modalNuevoTest = document.querySelector('.modalNuevoTest')
const closeModalNuevoTest = document.querySelector('.modalNuevoTest-close')
const ambitoSelect = document.getElementById("ambitoSelect");
const seleccionTemaNuevoTest = document.getElementById("seleccionTemaNuevoTest");
const permisoSelect = document.getElementById("permisoSelect");
const temaSelect = document.getElementById("temaSelect");

const listaPermisos = document.querySelector('.lista-permisos')
const btnVolver = document.getElementById('btnVolver')
const btnGlobal = document.querySelector('.btnGlobal')
const btnTemas = document.querySelector('.btnTemas')
const permisoSeleccionado = document.querySelector('.permiso-seleccionado')
const contenedorSeleccionAmbito = document.querySelector(
    '.contenedor-seleccion-ambito-oculto'
)
const contenedorTestFiltrados = document.querySelector(
    '.contenedor-test-filtrados-oculto'
)
const pasoUno = document.querySelector('.pasoUno')
const pasoDos = document.querySelector('.pasoDos')
const tituloPrincipal = document.querySelector('.tituloPrincipal')
const modal = document.querySelector('.ventanaTest')
const closeBtn = document.getElementsByClassName('close')[0]
const preguntasTest = document.querySelector('.preguntasTest')
const btnGuardar = document.querySelector('.btnGuardar')
const btnAgregarPregunta = document.querySelector('.btnAgregarPregunta')
let totalPreguntas = 0





/* -------------------------------------CREACIÓN DE UN NUEVO TEST-------------------------------*/
/* Apertura y cierre del modalNuevoTest*/
nuevoTestBtn.addEventListener('click', function () {
    modalNuevoTest.style.display = 'block'
})
closeModalNuevoTest.addEventListener('click', function () {
    modalNuevoTest.style.display = 'none'
})

//Configurar el modal nuevoTest para que cuando se seleccione ámbito por temas permita escoger tema
ambitoSelect.addEventListener("change", function() {
    if (ambitoSelect.value === "temas") {
        seleccionTemaNuevoTest.style.display = "block";
    } else {
        seleccionTemaNuevoTest.style.display = "none";
    }
});
/* Carga de desplegables con datos dinámicos */
document.addEventListener("DOMContentLoaded", function() {
    cargarPermisosDesplegableModalNuevoTest();
    cargarTemasDesplegableModalNuevoTest();

    ambitoSelect.addEventListener("change", function() {
        if (ambitoSelect.value === "temas") {
            seleccionTemaNuevoTest.style.display = "block";
        } else {
            seleccionTemaNuevoTest.style.display = "none";
        }
    });
    document.getElementById("nuevoTestForm").addEventListener("submit", function(event) {
        event.preventDefault();
        crearNuevoTest();
        seleccionTemaNuevoTest.style.display = "none"; //Volvemos a ocultar la selección de tema para que no salga al volver a crear otro test hasta que se seleccione ambito temas
    });    
});
function cargarPermisosDesplegableModalNuevoTest() {
    // Cargar permisos desde la API
    fetch('http://localhost:8080/permiso/listado')
        .then(response => response.json())
        .then(data => {
            data.forEach(permiso => {
                const option = document.createElement('option');
                option.value = permiso.idpermiso;  
                option.textContent = permiso.nombre;
                permisoSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error al cargar los permisos:', error));
}
function cargarTemasDesplegableModalNuevoTest() {
    // Cargar temas desde la API
    fetch('http://localhost:8080/tema/listado')
        .then(response => response.json())
        .then(data => {
            data.forEach(tema => {
                const option = document.createElement('option');
                option.value = tema.idtema;  
                option.textContent = tema.nombre;
                temaSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error al cargar los temas:', error));
}
/* Creación del nuevo test */
async function crearNuevoTest() {
    const permisoId = document.getElementById("permisoSelect").value;
    const ambito = document.getElementById("ambitoSelect").value;
    const temaId = ambito === "temas" ? document.getElementById("temaSelect").value : "";

    try {
        const permisoResponse = await fetch(`http://localhost:8080/permiso/listado/${permisoId}`);
        const permisoData = await permisoResponse.json();

        let temaData = null;
        if (ambito === "temas") {
            const temaResponse = await fetch(`http://localhost:8080/tema/listado/${temaId}`);
            temaData = await temaResponse.json();
        }

        const referencia = await generarReferencia(permisoData.nombre, ambito, temaData ? temaId : null);

        const nuevoTest = {
            referencia: referencia,
            ambito: ambito,
            permiso: permisoData,
            tema: temaData || null
        };

        const response = await fetch('http://localhost:8080/test/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(nuevoTest)
        });

        const result = await response.json();
        if (result.success) {
            console.log("Test creado exitosamente!");
            modalNuevoTest.style.display = 'none';
            console.log(result);
            muestraVentanaNuevoTest(result.test.idtest);
        } else {
            console.log("Error al crear el test: " + result.message);
            modalNuevoTest.style.display = 'none';
            muestraVentanaNuevoTest(result.test.idtest);
        }
    } catch (error) {
        console.error('Error al crear el test:', error);
    }
}
/* Generación de la referencia correspondiente para el nuevo test*/
async function generarReferencia(nombrePermiso, ambito, numeroTema) {
    let prefix = `${nombrePermiso}_`;
    let suffix = "";
    if (ambito === "global") {
        prefix += "G_";
    } else if (ambito === "temas" && numeroTema !== null) {
        prefix += `T${numeroTema}_`;
    }

    const testResponse = await fetch('http://localhost:8080/test/listado');
    const testData = await testResponse.json();
    const testsFiltrados = testData.filter(test => test.referencia.startsWith(prefix));
    const numeroTest = testsFiltrados.length + 1;

    suffix = numeroTest.toString();

    return prefix + suffix;
}
// Función para mostrar el modal de crear un nuevo test (sin preguntas ni opciones)
function muestraVentanaNuevoTest(idTest) {
     // Limpiar contenido anterior del modal de test
     preguntasTest.innerHTML = '';
     totalPreguntas = 0; // Restablecer el contador de preguntas a 0
 
     // Asignar el ID del nuevo test al modal
     modal.setAttribute('data-idtest', idTest);
 
     // Mostrar el modal de test vacío
     modal.style.display = 'block';
 
     // Configurar el modal para el nuevo test
     const modalTitle = document.getElementById('modalTitle');
     modalTitle.textContent = 'Nuevo Test';
 
     // Limpiar cualquier dato del modal
     const enunciadoInputs = document.querySelectorAll('.enunciadoInput');
     enunciadoInputs.forEach(input => input.value = '');
 
     const respuestaInputs = document.querySelectorAll('.respuestaInput');
     respuestaInputs.forEach(input => input.value = '');
 
     // Ocultar el contador de preguntas restantes
     const contador = document.getElementById('contador');
     contador.style.display = 'none';
}





/* -------------------------------------MOSTRAR PERMISOS Y PROCESO DE SELECCIÓN DE TEST-------------------------------*/
muestraPermisos()
/* Muestra los permisos */
function muestraPermisos() {
    fetch('http://localhost:8080/permiso/listado', {
        method: 'GET'
    })
    .then(response => response.json())
    .then(data => {
        listaPermisos.innerHTML = ''
        data.forEach(permiso => {
            const divPermiso = document.createElement('a')
            divPermiso.classList = 'divPermiso'
            const divDatos = document.createElement('div')
            divDatos.classList = 'divDatos'
            const nombrePermiso = document.createElement('h2')
            nombrePermiso.textContent = permiso.nombre
            const divDescripcion = document.createElement('div')
            divDescripcion.classList = 'divDescripcion'
            const descripcionPermisoPrincipal = document.createElement('p')
            const descripcionPermisoSecundaria = document.createElement('p')
            descripcionPermisoSecundaria.classList = 'descripcionSecundaria'
            const puntoIndex = permiso.descripcion.indexOf('.')
            if (puntoIndex !== -1) {
                descripcionPermisoPrincipal.textContent = permiso.descripcion.slice(0, puntoIndex)
            } else {
                descripcionPermisoPrincipal.textContent = permiso.descripcion
            }
            divPermiso.style.backgroundImage = `url('../../imagenes/permiso_${permiso.nombre}.png')`
            divDescripcion.append(descripcionPermisoPrincipal, descripcionPermisoSecundaria)
            divDatos.append(nombrePermiso, divDescripcion)
            divPermiso.append(divDatos)
            listaPermisos.append(divPermiso)
            divPermiso.addEventListener('click', function () {
                nuevoTestBtn.style.display = 'none';
                permisoSeleccionado.append(divPermiso)
                listaPermisos.style.display = 'none'
                contenedorSeleccionAmbito.classList = 'contenedor-seleccion-ambito-visible'
                permisoSeleccionado.style.display = 'block'
                btnGlobal.addEventListener('click', cambiarAmbito)
                btnTemas.addEventListener('click', cambiarAmbito)
                function cambiarAmbito() {
                    pasoUno.style.display = 'none'
                    const nombreAmbitoSeleccionado = this.id === 'btnGlobal' ? 'global' : 'temas'
                    tituloPrincipal.textContent = 'Test del permiso ' + permiso.nombre + ' - ' + nombreAmbitoSeleccionado
                    contenedorTestFiltrados.setAttribute('data-idpermiso', permiso.idpermiso) // Guardar el idpermiso en el contenedor
                    filtrarTest(permiso.nombre, nombreAmbitoSeleccionado)
                }
            })
        })
    })
    .catch(error => {
        console.error('Se produjo un error al obtener los permisos:', error)
    })
}
/* Permite volver al paso uno */
btnVolver.addEventListener('click', function () {
    location.reload() // Esto recargará la página
})
/* Filtra los test del permiso y ámbito seleccionado en el paso uno*/
function filtrarTest(PermisoNombre, Ambito) {
    contenedorTestFiltrados.classList = 'contenedor-test-filtrados-visible';

    fetch(`http://localhost:8080/test/findByPermisoNombreAndAmbito?PermisoNombre=${PermisoNombre}&Ambito=${Ambito}`, {
        method: 'GET'
    })
    .then(response => response.json())
    .then(data => {
        contenedorTestFiltrados.innerHTML = '';

        if (Ambito === 'global') {
            const divTestGlobales = document.createElement('div');
            divTestGlobales.classList = 'divTestGlobales';

            data.forEach((test, index) => {
                const divTest = document.createElement('div');
                divTest.classList = 'divTest';
                const numeroTest = index + 1; // Asignar número secuencial
                divTest.textContent = numeroTest;

                divTest.addEventListener('click', () =>
                    muestraVentanaTest(test.idtest)
                );

                divTestGlobales.appendChild(divTest);
            });

            contenedorTestFiltrados.appendChild(divTestGlobales);
        } else if (Ambito === 'temas') {
            const temas = {};

            data.forEach(test => {
                const lastUnderscoreIndex = test.referencia.lastIndexOf('_');
                const temaInfo = test.referencia.substring(test.referencia.lastIndexOf('T') + 1, lastUnderscoreIndex);
                const tema = 'Tema ' + temaInfo;

                if (!temas[tema]) {
                    temas[tema] = [];
                }

                temas[tema].push(test);
            });

            for (const tema in temas) {
                if (temas.hasOwnProperty(tema)) {
                    const divTema = document.createElement('div');
                    divTema.classList = 'divTema';
                    const tituloTema = document.createElement('h3');
                    tituloTema.textContent = tema;
                    const divTestTema = document.createElement('div');
                    divTestTema.classList = 'divTestTema';
                    divTema.append(tituloTema, divTestTema);

                    temas[tema].forEach((test, index) => {
                        const divTest = document.createElement('div');
                        divTest.classList = 'divTest';
                        const numeroTest = index + 1; // Asignar número secuencial
                        divTest.textContent = numeroTest;

                        divTest.addEventListener('click', () =>
                            muestraVentanaTest(test.idtest)
                        ); 

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





/* -------------------------------------EDICIÓN DE TEST-------------------------------*/
// Función para cargar los detalles de un test para editar
function muestraVentanaTest(idTest) {
    // Limpiar contenido anterior
    preguntasTest.innerHTML = '';
    totalPreguntas = 0; // Restablecer el contador de preguntas a 0
    modalTitle.textContent = 'Contenido del Test';

    // Fetch para obtener los detalles del test
    fetch(`http://localhost:8080/test/listado/${idTest}`)
        .then(response => response.json())
        .then(data => {
            preguntasTest.innerHTML = '';

            // Fetch para obtener las preguntas del test
            fetch(
                `http://localhost:8080/pregunta/findByTestIdtest?TestIdtest=${idTest}`
            )
                .then(response => response.json())
                .then(data => {
                    data.forEach(pregunta => {
                        const divPregunta = document.createElement('div');
                        divPregunta.classList = 'divPregunta';

                        // Asociar el atributo data-idpregunta con el ID de la pregunta
                        divPregunta.setAttribute('data-idpregunta', pregunta.idpregunta);

                        // Crear un campo de entrada para la pregunta
                        const enunciadoInput = document.createElement('input');
                        enunciadoInput.setAttribute('type', 'text');
                        enunciadoInput.classList = 'enunciadoInput';
                        enunciadoInput.value = pregunta.enunciado;

                        // Añadir la imagen si la foto no es null
                        if (pregunta.foto) {
                            const imgPregunta = document.createElement('img');
                            imgPregunta.src = "../../imagenes/preguntas_test/"+pregunta.foto+".png";
                            imgPregunta.classList = "imgPregunta";
                            divPregunta.append(imgPregunta);
                        }

                        const divRespuestas = document.createElement('div');
                        divRespuestas.classList = 'divRespuestas';

                        // Fetch para obtener las opciones de respuesta de la pregunta actual
                        fetch(
                            `http://localhost:8080/opcion/findByPreguntaIdpregunta?PreguntaIdpregunta=${pregunta.idpregunta}`
                        )
                            .then(response => response.json())
                            .then(data => {
                                data.forEach(opcion => {
                                    const divRespuesta = document.createElement('div');
                                    divRespuesta.classList = 'divRespuesta';
                                    // Asignar el atributo idopcion al divRespuesta
                                    divRespuesta.setAttribute('data-idopcion', opcion.idopcion); // Suponiendo que idopcion es el nombre del atributo que quieres asignar

                                    // Crear un radio button para la respuesta
                                    const radioInput = document.createElement('input');
                                    radioInput.setAttribute('type', 'radio');
                                    radioInput.setAttribute(
                                        'name',
                                        `pregunta_${pregunta.idpregunta}`
                                    ); // Asignar un nombre único para cada conjunto de radio buttons
                                    radioInput.classList = 'radioInput';

                                    // Si la opción es correcta, marcar el radio button por defecto
                                    if (opcion.correcta !== undefined && opcion.correcta == '1') {
                                        radioInput.checked = true;
                                        // Asociar el atributo data-correcta=1 a la respuesta
                                        divRespuesta.setAttribute('data-correcta', '1');
                                    }

                                    // Crear un campo de entrada para la respuesta
                                    const respuestaInput = document.createElement('input');
                                    respuestaInput.setAttribute('type', 'text');
                                    respuestaInput.classList = 'respuestaInput';
                                    respuestaInput.value = opcion.respuesta;

                                    divRespuesta.append(radioInput, respuestaInput);
                                    divRespuestas.append(divRespuesta);
                                });
                            });
                        
                        // Crear el divPapelera y agregar el evento de clic
                        const divPapelera = document.createElement('div');
                        divPapelera.classList = 'divPapelera';
                        const imgPapelera = document.createElement('img');
                        imgPapelera.src = '/../imagenes/papelera.png';
                        imgPapelera.alt = 'Eliminar';
                        imgPapelera.classList = 'iconoPapelera';
                        const spanEliminar = document.createElement('span');
                        spanEliminar.textContent = 'Eliminar';

                        divPapelera.append(imgPapelera, spanEliminar);
                        divPapelera.addEventListener('click', function() {
                            const modalConfirmacionBorrarPregunta = document.getElementById('modal-confirmacion-borrar-pregunta');
                            modalConfirmacionBorrarPregunta.style.display = 'block';

                            document.getElementById('btnSiBorrarPregunta').onclick = function() {
                                borrarPregunta(pregunta.idpregunta);
                                modalConfirmacionBorrarPregunta.style.display = 'none';
                            };

                            document.getElementById('btnNoBorrarPregunta').onclick = function() {
                                modalConfirmacionBorrarPregunta.style.display = 'none';
                            };

                            document.querySelector('.close-confirmacion-borrar-pregunta').onclick = function() {
                                modalConfirmacionBorrarPregunta.style.display = 'none';
                            };
                        });

                        divPregunta.append(enunciadoInput, divRespuestas, divPapelera);
                        preguntasTest.appendChild(divPregunta);
                        totalPreguntas++;
                    });

                    // Asignar al modal el data-idtest para luego usar este parámetro para actualizar el test
                    modal.setAttribute('data-idtest', idTest);
                    // Mostrar la ventana modal con el contenido actualizado
                    modal.style.display = 'block';
                })
                .catch(error => {
                    console.error('Error:', error);
                    console.log(idTest);
                    muestraVentanaNuevoTest(idTest);
                });
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

// Cuando se haga clic en la X, cerrar el modal
closeBtn.onclick = function () {
    modal.style.display = 'none'
    totalPreguntas = 0
    //Escondo el mensaje de preguntas restantes
    const contador = document.getElementById('contador')
    contador.style.display = 'none'
    actualizarContadores() // Restablecer el contador a cero
}
// Agregar evento click al botón de guardar
btnGuardar.addEventListener('click', function () {
    // Verificar si el total de preguntas es menor que 30
    if (totalPreguntas < 30) {
        // Mostrar ventana modal de confirmación
        mostrarVentanaConfirmacionCrear()
    } else {
        // Si hay al menos 30 preguntas, continuar con la acción de guardar
        guardarTest()
    }
})
/* Agrega gráficamente la estructura para insertar una nueva pregunta con sus opciones*/
btnAgregarPregunta.addEventListener('click', function () {
    agregarPregunta();
    const contador = document.getElementById('contador');
    contador.style.display = 'block';
});
function agregarPregunta() {
    const divPregunta = document.createElement('div');
    divPregunta.classList.add('divPregunta');

    const enunciadoInput = document.createElement('input');
    enunciadoInput.setAttribute('type', 'text');
    enunciadoInput.classList.add('enunciadoInput');
    enunciadoInput.setAttribute('placeholder', 'Enunciado de la pregunta');

    const divRespuestas = document.createElement('div');
    divRespuestas.classList.add('divRespuestas');

    for (let i = 0; i < 3; i++) {
        const divRespuesta = document.createElement('div');
        divRespuesta.classList.add('divRespuesta');

        const radioInput = document.createElement('input');
        radioInput.setAttribute('type', 'radio');
        radioInput.setAttribute('name', `respuesta_${totalPreguntas}`);
        radioInput.classList.add('radioInput');

        const respuestaInput = document.createElement('input');
        respuestaInput.setAttribute('type', 'text');
        respuestaInput.classList.add('respuestaInput');
        respuestaInput.setAttribute('placeholder', `Respuesta ${i + 1}`);

        divRespuesta.appendChild(radioInput);
        divRespuesta.appendChild(respuestaInput);
        divRespuestas.appendChild(divRespuesta);
    }

    const divPapelera = document.createElement('div');
    divPapelera.classList.add('divPapelera');
    const imgPapelera = document.createElement('img');
    imgPapelera.src = '../../imagenes/papelera.png';
    imgPapelera.alt = 'Eliminar';
    imgPapelera.classList.add('iconoPapelera');
    const spanEliminar = document.createElement('span');
    spanEliminar.textContent = 'Eliminar';

    divPapelera.append(imgPapelera, spanEliminar);
    divPapelera.addEventListener('click', function() {
        const modalConfirmacionBorrarPregunta = document.getElementById('modal-confirmacion-borrar-pregunta');
        modalConfirmacionBorrarPregunta.style.display = 'block';
    
        document.getElementById('btnSiBorrarPregunta').onclick = function() {
            divPregunta.remove();
            totalPreguntas--; // Disminuir el contador de preguntas
            actualizarContadores(); // Actualizar los contadores
            modalConfirmacionBorrarPregunta.style.display = 'none';
        };
    
        document.getElementById('btnNoBorrarPregunta').onclick = function() {
            modalConfirmacionBorrarPregunta.style.display = 'none';
        };
    
        document.querySelector('.close-confirmacion-borrar-pregunta').onclick = function() {
            modalConfirmacionBorrarPregunta.style.display = 'none';
        };
    });

    divPregunta.appendChild(enunciadoInput);
    divPregunta.appendChild(divRespuestas);
    divPregunta.appendChild(divPapelera);
    preguntasTest.appendChild(divPregunta);

    totalPreguntas++;
    actualizarContadores();

    nuevaPregunta(divPregunta);  //Llamada a la función para agregar la pregunta a la base de datos
}

/* Guarda la nueva pregunta en la base de datos, junto con sus opciones */
function nuevaPregunta(divPregunta) {
    const idTest = modal.getAttribute('data-idtest');

    const nuevaPreguntaData = {
        enunciado: 'Insertar enunciado',
        test: {
            idtest: idTest
        }
    };

    fetch('http://localhost:8080/pregunta/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(nuevaPreguntaData)
    })
    .then(response => response.json())
    .then(data => {
        if (data && data.pregunta) {
            // Asociar el atributo data-idpregunta con el ID de la pregunta generada
            divPregunta.setAttribute('data-idpregunta', data.pregunta.idpregunta);
            nuevasOpciones(divPregunta);  // Llamada a la función para agregar las opciones de esta pregunta a la base de datos
        } else {
            console.error('Error al insertar la pregunta:', data);
        }
    })
    .catch(error => {
        console.error('Error al insertar la pregunta:', error);
    });
}
/* Guarda las nuevas opciones de una pregunta en la base de datos */
function nuevasOpciones(divPregunta) {
    const idPregunta = divPregunta.getAttribute('data-idpregunta');
    const divRespuestas = divPregunta.querySelectorAll('.divRespuesta');

    divRespuestas.forEach(divRespuesta => {
        const nuevaOpcionData = {
            respuesta: 'Asignar respuesta',
            correcta: 0,
            pregunta: {
                idpregunta: idPregunta
            }
        };

        fetch('http://localhost:8080/opcion/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(nuevaOpcionData)
        })
        .then(response => response.json())
        .then(data => {
            if (data && data.opcion) {
                divRespuesta.setAttribute('data-idopcion', data.opcion.idopcion);
            } else {
                console.error('Error al insertar la opción:', data);
            }
        })
        .catch(error => {
            console.error('Error al insertar la opción:', error);
        });
    });
}
/* Actualiza el contador para llevar la cuenta de cuántas preguntas van introduciéndose - En caso de ser <30 al guardar avisa*/
function actualizarContadores() {
    const totalPreguntasRestantes = 30 - totalPreguntas // 3 respuestas por pregunta

    // Actualiza el texto que muestra el número de preguntas y respuestas restantes
    const contador = document.getElementById('contador')
    contador.textContent = `Preguntas añadidas: ${totalPreguntas} | Preguntas restantes: ${totalPreguntasRestantes}`
}

// Función para mostrar la ventana modal de confirmación en caso de haber <30 preguntas
function mostrarVentanaConfirmacionCrear() {
    // Mostrar la ventana modal de confirmación
    const modalConfirmacionCrear = document.getElementById('modal-confirmacion-crear')
    modalConfirmacionCrear.style.display = 'block'

    // Agregar evento click al botón "Sí"
    const btnSi = document.getElementById('btnSi')
    btnSi.addEventListener('click', function () {
        // Cerrar la ventana modal de confirmación
        modalConfirmacionCrear.style.display = 'none'
        modal.style.display = 'none'
        // Guardar el test
        guardarTest()
        // Esconder el mensaje de preguntas restantes
        const contador = document.getElementById('contador')
        contador.style.display = 'none'
    })

    // Agregar evento click al botón "No"
    const btnNo = document.getElementById('btnNo')
    btnNo.addEventListener('click', function () {
        // Cerrar la ventana modal de confirmación
        modalConfirmacionCrear.style.display = 'none'
    })

    // Agregar evento click al botón de cerrar
    const closeBtnConfirmacion = document.querySelector(
        '.modal-confirmacion-crear .close-confirmacion-crear'
    )
    closeBtnConfirmacion.addEventListener('click', function () {
        // Cerrar la ventana modal de confirmación
        modalConfirmacionCrear.style.display = 'none'
    })
}

// Método para guardar un test
function guardarTest() {
    const divPreguntas = document.querySelectorAll('.divPregunta')

    divPreguntas.forEach(divPregunta => {
        const idPregunta = divPregunta.getAttribute('data-idpregunta')
        const enunciadoInput = divPregunta.querySelector('.enunciadoInput').value

        const preguntaData = {
            idpregunta: idPregunta,
            enunciado: enunciadoInput,
            test: {
                idtest: modal.getAttribute('data-idtest')
            }
        }

        fetch(`http://localhost:8080/pregunta/update/${idPregunta}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(preguntaData)
        })
        .then(response => response.json())
        .then(data => {
            if (data && data.pregunta) {
                console.log('Pregunta actualizada:', data.pregunta)
            } else {
                console.error('Error al actualizar la pregunta:', data)
            }
        })
        .catch(error => {
            console.error('Error al actualizar la pregunta:', error)
        })

        const divRespuestas = divPregunta.querySelectorAll('.divRespuesta')

        divRespuestas.forEach(divRespuesta => {
            const idOpcion = divRespuesta.getAttribute('data-idopcion')
            const respuestaInput = divRespuesta.querySelector('.respuestaInput').value
            const correcta = divRespuesta.querySelector('.radioInput').checked ? 1 : 0

            const opcionData = {
                idopcion: idOpcion,
                respuesta: respuestaInput,
                correcta: correcta,
                pregunta: {
                    idpregunta: idPregunta
                }
            }

            fetch(`http://localhost:8080/opcion/update/${idOpcion}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(opcionData)
            })
            .then(response => response.json())
            .then(data => {
                if (data && data.opcion) {
                    console.log('Opción actualizada:', data.opcion)
                } else {
                    console.error('Error al actualizar la opción:', data)
                    modal.style.display = 'none'
                }
            })
            .catch(error => {
                console.error('Error al actualizar la opción:', error)
            })
        })
    })

    // Esconder el contador de preguntas
    const contador = document.getElementById('contador')
    contador.style.display = 'none'
}







/* -------------------------------------ELIMINACIÓN DE TEST-------------------------------*/
document.addEventListener("DOMContentLoaded", function() {
    const btnEliminar = document.querySelector('.btnEliminar');
    const modalConfirmacionBorrar = document.getElementById('modal-confirmacion-borrar');
    const closeConfirmacionBorrar = document.querySelector('.close-confirmacion-borrar');
    const btnSiBorrar = document.getElementById('btnSiBorrar');
    const btnNoBorrar = document.getElementById('btnNoBorrar');

    // Mostrar el modal de confirmación de borrar
    btnEliminar.addEventListener('click', function() {
        modalConfirmacionBorrar.style.display = 'block';
    });

    // Cerrar el modal de confirmación de borrar
    closeConfirmacionBorrar.addEventListener('click', function() {
        modalConfirmacionBorrar.style.display = 'none';
    });

    btnNoBorrar.addEventListener('click', function() {
        modalConfirmacionBorrar.style.display = 'none';
    });

    // Eliminar el test si se confirma
    btnSiBorrar.addEventListener('click', async function() {
        const idTest = modal.getAttribute('data-idtest');
        const referenciaTest = await obtenerReferenciaTest(idTest);
        await iniciarProcesoBorrado(idTest);
        modalConfirmacionBorrar.style.display = 'none';
        modal.style.display = 'none';
        //-----actualizar filtro test
        const permiso = permisoSeleccionado.querySelector('h2').textContent;
        const ambito = tituloPrincipal.textContent.includes('global') ? 'global' : 'temas';
        filtrarTest(permiso, ambito);
        //-----actualizar referencias
        await actualizarReferencias(referenciaTest);
    });
});
/* Método para obtener la referencia del test que estamos borrando */
async function obtenerReferenciaTest(idTest) {
    try {
        const response = await fetch(`http://localhost:8080/test/listado/${idTest}`);
        const test = await response.json();
        return test.referencia;
    } catch (error) {
        console.error('Error al obtener la referencia del test:', error);
        return null;
    }
}
/* El proceso de borrado debe borrar las opciones, preguntas y realizaciones antes de eliminar el test*/
async function iniciarProcesoBorrado(idTest) {
    await borrarPreguntasTest(idTest);
    await borrarRealizacionesTest(idTest);
    await eliminarTestDefinitivamente(idTest);
}
/* Proceso que recorre todas las preguntas de un test, borrando sus opciones, y después elimina todas las preguntas*/
async function borrarPreguntasTest(idTest) {
    try {
        const response = await fetch(`http://localhost:8080/pregunta/findByTestIdtest?TestIdtest=${idTest}`);
        const preguntas = await response.json();
        
        for (const pregunta of preguntas) {
            await borrarOpcionesPregunta(pregunta.idpregunta);
        }

        await fetch(`http://localhost:8080/pregunta/deleteByTest/${idTest}`, {
            method: 'DELETE'
        });
    } catch (error) {
        console.error('Error al borrar las preguntas del test:', error);
    }
}
async function borrarOpcionesPregunta(idPregunta) {
    try {
        await fetch(`http://localhost:8080/opcion/deleteByPregunta/${idPregunta}`, {
            method: 'DELETE'
        });
    } catch (error) {
        console.error('Error al borrar las opciones de la pregunta:', error);
    }
}
/* Método que elimina todos los registros de la tabla realiza antes de eliminar el test */
async function borrarRealizacionesTest(idTest) {
    try {
        await fetch(`http://localhost:8080/realiza/deleteByTest/${idTest}`, {
            method: 'DELETE'
        });
    } catch (error) {
        console.error('Error al borrar las realizaciones del test:', error);
    }
}
/* Método que elimina el test, una vez se hayan eliminado todas las preguntas, opciones y realizaciones */
async function eliminarTestDefinitivamente(idTest) {
    try {
        await fetch(`http://localhost:8080/test/delete/${idTest}`, {
            method: 'DELETE'
        });
    } catch (error) {
        console.error('Error al eliminar el test definitivamente:', error);
    }
}
/* Método que actualiza las referencias en la base de datos para que se numeren otra vez correctamente, sin dejar huecos de numeración */
async function actualizarReferencias(referenciaEliminada) {
    const [permiso, ambito, numero] = referenciaEliminada.split('_');
    const esGlobal = ambito === 'G';
    const tema = esGlobal ? null : ambito.replace('T', '');

    try {
        const response = await fetch('http://localhost:8080/test/listado');
        const tests = await response.json();

        let testsFiltrados;
        if (esGlobal) {
            testsFiltrados = tests.filter(test => test.referencia.startsWith(`${permiso}_G_`));
        } else {
            testsFiltrados = tests.filter(test => test.referencia.startsWith(`${permiso}_T${tema}_`));
        }

        // Ordenar los tests filtrados por su número de referencia
        testsFiltrados.sort((a, b) => {
            const numA = parseInt(a.referencia.split('_')[2]);
            const numB = parseInt(b.referencia.split('_')[2]);
            return numA - numB;
        });

        // Actualizar las referencias de los tests que sean posteriores al eliminado
        for (let i = 0; i < testsFiltrados.length; i++) {
            const test = testsFiltrados[i];
            const num = parseInt(test.referencia.split('_')[2]);

            if (num > parseInt(numero)) {
                const nuevaReferencia = esGlobal 
                    ? `${permiso}_G_${num - 1}`
                    : `${permiso}_T${tema}_${num - 1}`;

                const actualizado = {
                    ...test,
                    referencia: nuevaReferencia
                };

                await fetch(`http://localhost:8080/test/update/${test.idtest}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(actualizado)
                });

                console.log(`Referencia actualizada: ${test.referencia} -> ${nuevaReferencia}`);
            }
        }
    } catch (error) {
        console.error('Error al actualizar las referencias:', error);
    }
}





/* -------------------------------------ELIMINACIÓN DE PREGUNTA-------------------------------*/
document.addEventListener('DOMContentLoaded', function() {
    // Añadir evento de clic para abrir el modal de confirmación de eliminación de pregunta
    document.querySelectorAll('.divPapelera').forEach(divPapelera => {
        divPapelera.addEventListener('click', function() {
            const modalConfirmacionBorrarPregunta = document.getElementById('modal-confirmacion-borrar-pregunta');
            modalConfirmacionBorrarPregunta.style.display = 'block';

            const divPregunta = divPapelera.closest('.divPregunta');
            const idPregunta = divPregunta.getAttribute('data-idpregunta');

            document.getElementById('btnSiBorrarPregunta').addEventListener('click', function() {
                borrarPregunta(idPregunta);
                modalConfirmacionBorrarPregunta.style.display = 'none';
            });

            document.getElementById('btnNoBorrarPregunta').addEventListener('click', function() {
                modalConfirmacionBorrarPregunta.style.display = 'none';
            });

            document.querySelector('.close-confirmacion-borrar-pregunta').addEventListener('click', function() {
                modalConfirmacionBorrarPregunta.style.display = 'none';
            });
        });
    });
});
function borrarPregunta(idPregunta) {
    borrarOpcionesPregunta(idPregunta)
        .then(() => {
            return fetch(`http://localhost:8080/pregunta/delete/${idPregunta}`, {
                method: 'DELETE'
            });
        })
        .then(response => {
            if (response.ok) {
                document.querySelector(`.divPregunta[data-idpregunta="${idPregunta}"]`).remove();
                totalPreguntas--; // Disminuir el contador de preguntas
                actualizarContadores(); // Actualizar los contadores
            } else {
                console.error('Error al eliminar la pregunta');
            }
        })
        .catch(error => {
            console.error('Error al eliminar la pregunta:', error);
        });
}