class GestionarModal {
    constructor() {
        this.setupListeners();
    }

    setupListeners() {
        var item6 = document.querySelector('.item6');
        var modal = document.getElementById('modal');
        var modalContent = modal.querySelector('.modal-content');
        var contenedorVentana = document.querySelector('.contenedor-ventana'); 

        item6.addEventListener('click', lanzarRecomendacion);

        function lanzarRecomendacion() {
            item6.removeEventListener('click', lanzarRecomendacion);
            var numFallosElements = document.querySelectorAll('.numFallos');
            var totalFallos = 0;
            numFallosElements.forEach(element => {
                totalFallos += parseInt(element.textContent);
            });
            var mediaFallos = totalFallos / numFallosElements.length;

            var modalMessage = document.getElementById('modal-message');
            var modalIcon = document.querySelector('.iconoModal');
            
            if (mediaFallos <= 2) {
                modalMessage.textContent = '¡Estás listo! Preséntate al examen teórico.';
                modalContent.style.backgroundColor = 'green';
                modalIcon.src = "../../imagenes/celebraMinion.gif";
                modalMessage.style.color = 'white';
            } else if (mediaFallos === 3) {
                modalMessage.textContent = 'Sigue practicando. ¡Estás muy cerca!';
                modalContent.style.backgroundColor = 'yellow';
                modalIcon.src = "../../imagenes/animaMinion.gif";
            } else {
                modalMessage.textContent = 'Todavía tienes que practicar bastante. ¡Pero no te rindas!';
                modalContent.style.backgroundColor = 'red';
                modalIcon.src = "../../imagenes/malMinion.gif";
                modalMessage.style.color = 'white';
            }

            modal.classList='lanzaModal';
            contenedorVentana.classList.add('mostrar-fondo-oscuro');

            item6.addEventListener('click', lanzarRecomendacion);

            setTimeout(() => {
                contenedorVentana.classList.remove('mostrar-fondo-oscuro');
                modal.classList='modalOculto';
            },5000);
        };
    }
}

function verificarTestsRealizados(usuarioId) {
    fetch(`http://localhost:8080/realiza/testPorUsuario/${usuarioId}`)
        .then(response => response.json())
        .then(data => {
            if (data.length === 0) {
                document.getElementById('contenidoPrincipal').style.display = 'none';
                document.getElementById('mensajeError').style.display = 'block';
            } else {
                document.getElementById('contenidoPrincipal').style.display = 'block';
                document.getElementById('mensajeError').style.display = 'none';
           
            }
        })
        .catch(error => {
            console.error('Error al verificar los tests realizados:', error);
        });
}

// Crear una instancia de la clase GestionarModal cuando se cargue el DOM
document.addEventListener('DOMContentLoaded', () => {
    new GestionarModal();
    calcularPorcentajeAprobados();
    calcularPorcentajeSuspensos();
    calcularTotalTest();
    calcularMediaFallos();
    calcularTestsPorTema();
    calcularTestsGlobales();
    obtenerTemaMasSuspendido();
    obtenerTemaMenosSuspendido();
    obtenerProgresoMesActual(); 
    obtenerTemaMenosPracticado();
    obtenerPorcentajesPorTema();
});

function calcularPorcentajeAprobados() {
    const userId = localStorage.getItem('usuarioId');
    if (!userId) {
        console.error('Usuario no logueado.');
        return;
    }

    const url = `http://localhost:8080/realiza/porcentajeTestAprobados/${userId}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            const aprobadosElement = document.getElementById('aprobados');
            aprobadosElement.textContent = `${data.toFixed(2)}%`;
            aprobadosElement.style.width = `${data}%`;  // Ajustar el ancho dinámicamente
        })
        .catch(error => {
            console.error('Error al obtener el porcentaje de aprobados:', error);
            const aprobadosElement = document.getElementById('aprobados');
            aprobadosElement.textContent = 'Error al cargar';
        });
}

function calcularPorcentajeSuspensos() {
    const userId = localStorage.getItem('usuarioId');
    if (!userId) {
        console.error('Usuario no logueado.');
        return;
    }

    const url = `http://localhost:8080/realiza/porcentajeTestSuspensos/${userId}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            const suspensosElement = document.getElementById('suspensos');
            suspensosElement.textContent = `${data.toFixed(2)}%`;
            suspensosElement.style.width = `${data}%`;  // Ajustar el ancho dinámicamente
        })
        .catch(error => {
            console.error('Error al obtener el porcentaje de suspensos:', error);
            const suspensosElement = document.getElementById('suspensos');
            suspensosElement.textContent = 'Error al cargar';
        });
}

function calcularTotalTest(){
    const userId = localStorage.getItem('usuarioId');
    if (!userId) {
        console.error('Usuario no logueado.');
        return;
    }

    const url = `http://localhost:8080/realiza/recuentoTest/${userId}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            const totalTestsElement = document.getElementById('total-test');
            totalTestsElement.textContent = data.count;
        })
        .catch(error => {
            console.error('Error al obtener el número de test:', error);
            const totalTestsElement = document.getElementById('total-test');
            totalTestsElement.textContent = 'Error al cargar';
        });
}

function calcularMediaFallos() {
    const userId = localStorage.getItem('usuarioId');
    if (!userId) {
        console.error('Usuario no logueado.');
        return;
    }

    const url = `http://localhost:8080/realiza/mediaFallos/${userId}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            const mediaFallosElement = document.getElementById('media-fallos');
            const mediaFallosRedondeada = Math.ceil(data); // Redondear hacia arriba
            mediaFallosElement.textContent = mediaFallosRedondeada;
        })
        .catch(error => {
            console.error('Error al obtener la media de fallos:', error);
            const mediaFallosElement = document.getElementById('media-fallos');
            mediaFallosElement.textContent = 'Error al cargar';
        });
}
function calcularTestsPorTema() {
    const userId = localStorage.getItem('usuarioId');
    if (!userId) {
        console.error('Usuario no logueado.');
        return;
    }

    const url = `http://localhost:8080/realiza/countTestByTema/${userId}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            const testsPorTemasElement = document.getElementById('test-por-temas');
            testsPorTemasElement.textContent = data;
        })
        .catch(error => {
            console.error('Error al obtener el número de tests por temas:', error);
            const testsPorTemasElement = document.getElementById('test-por-temas');
            testsPorTemasElement.textContent = 'Error al cargar';
        });
}

function calcularTestsGlobales() {
    const userId = localStorage.getItem('usuarioId');
    if (!userId) {
        console.error('Usuario no logueado.');
        return;
    }

    const url = `http://localhost:8080/realiza/countTestGlobales/${userId}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            const testsGlobalesElement = document.getElementById('test-globales');
            testsGlobalesElement.textContent = data;
        })
        .catch(error => {
            console.error('Error al obtener el número de tests globales:', error);
            const testsGlobalesElement = document.getElementById('test-globales');
            testsGlobalesElement.textContent = 'Error al cargar';
        });
}
function obtenerTemaMasSuspendido() {
    const userId = localStorage.getItem('usuarioId');
    if (!userId) {
        console.error('Usuario no logueado.');
        return;
    }

    const url = `http://localhost:8080/realiza/temaMasSuspendido/${userId}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            const temaMasSuspendidoElement = document.querySelector('.temaMasSuspendido .resultado');
            temaMasSuspendidoElement.textContent = data.tema;
        })
        .catch(error => {
            console.error('Error al obtener el tema más suspendido:', error);
            const temaMasSuspendidoElement = document.querySelector('.temaMasSuspendido .resultado');
            temaMasSuspendidoElement.textContent = 'Error al cargar';
        });
}

function obtenerTemaMenosSuspendido() {
    const userId = localStorage.getItem('usuarioId');
    if (!userId) {
        console.error('Usuario no logueado.');
        return;
    }

    const url = `http://localhost:8080/realiza/temaMasAprobado/${userId}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            const temaMasSuspendidoElement = document.querySelector('.temaMasAprobado .resultado');
            temaMasSuspendidoElement.textContent = data.tema;
        })
        .catch(error => {
            console.error('Error al obtener el tema menos suspendido:', error);
            const temaMasSuspendidoElement = document.querySelector('.temaMasAprobado .resultado');
            temaMasSuspendidoElement.textContent = 'Error al cargar';
        });
}
function obtenerProgresoMesActual() {
    const userId = localStorage.getItem('usuarioId');
    if (!userId) {
        console.error('Usuario no logueado.');
        return;
    }

    const url = `http://localhost:8080/realiza/progresionMesActual/${userId}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            const progresoMesElement = document.querySelector('.progresoMesActual .resultado');
            progresoMesElement.textContent = data.progresion ? "Adecuado" : "Desfavorable";
        })
        .catch(error => {
            console.error('Error al obtener el progreso del mes actual:', error);
            const progresoMesElement = document.querySelector('.progresoMesActual .resultado');
            progresoMesElement.textContent = 'Error al cargar';
        });
}
function obtenerTemaMenosPracticado() {
    const userId = localStorage.getItem('usuarioId');
    if (!userId) {
        console.error('Usuario no logueado.');
        return;
    }

    const url = `http://localhost:8080/realiza/temaMenosPracticado/${userId}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            const progresoMesElement = document.querySelector('.temaMenosPracticado .resultado');
            progresoMesElement.textContent = data.tema;
        })
        .catch(error => {
            console.error('Error al obtener el progreso del mes actual:', error);
            const progresoMesElement = document.querySelector('.temaMenosPracticado .resultado');
            progresoMesElement.textContent = 'Error al cargar';
        });
}

/* --------------OBTENER DATOS PARA EL ITEM 4 - GRÁFICO CIRCULAR---------------*/
function obtenerPorcentajesPorTema() {
    const userId = localStorage.getItem('usuarioId');
    if (!userId) {
        console.error('Usuario no logueado.');
        return;
    }

    const url = `http://localhost:8080/realiza/porcentajeTestsPorTema/${userId}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            // Procesar los datos para el gráfico
            const temas = Object.keys(data);
            const porcentajes = Object.values(data);
            crearGraficoTemas(temas, porcentajes);
        })
        .catch(error => {
            console.error('Error al obtener los porcentajes por tema:', error);
        });
}
function crearGraficoTemas(temas, porcentajes) {
    const colores = ['#fbd2fb', '#ae43b7', '#238b9b', '#2DCDD4'];
    const ctx = document.getElementById('temasChart').getContext('2d');
    new Chart(ctx, {
        type: 'pie',
        data: {
            labels: temas,
            datasets: [{
                label: 'Porcentajes por Tema',
                data: porcentajes,
                backgroundColor: colores,
                borderWidth: 0 //Sin bordes
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    display: false // Ocultar la leyenda predeterminada
                },
                tooltip: {
                    callbacks: {
                        label: function(tooltipItem) {
                            return `${tooltipItem.label}: ${tooltipItem.raw.toFixed(2)}%`;
                        }
                    }
                }
            }
        }
    });

    // Asignar etiquetas a las leyendas manualmente con colores
    const leyendasIzquierda = document.querySelectorAll('.leyendas-izquierda .leyenda');
    const leyendasDerecha = document.querySelectorAll('.leyendas-derecha .leyenda');
    
    temas.slice(0, 2).forEach((tema, index) => {
        leyendasIzquierda[index].querySelector('.leyenda-texto').textContent = tema;
        leyendasIzquierda[index].querySelector('.color-box').style.backgroundColor = colores[index];
    });
    
    temas.slice(2).forEach((tema, index) => {
        leyendasDerecha[index].querySelector('.leyenda-texto').textContent = tema;
        leyendasDerecha[index].querySelector('.color-box').style.backgroundColor = colores[index + 2];
    });
}