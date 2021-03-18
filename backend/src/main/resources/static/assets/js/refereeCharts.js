/**
 * Referee Donut Chart
 */
let ctx2 = document.getElementById("chart1").getContext("2d")
new Chart(ctx2, {
    type: 'doughnut',
    data: {
        labels: ['Asistencias', 'Ausencias'],
        datasets: [{
            data: [5, 2],
            backgroundColor: [
                "rgb(25, 255, 61)",
                "rgb(255, 69, 8)",
            ]
        }]
    }
});


/**
 * Referee Bar Chart
 */
let ctx = document.getElementById("chart2").getContext("2d")
new Chart(ctx, {
    type: 'bar',
    data: {
        labels: ['Confirmaciones', 'Convocatorias'],
        datasets: [{
            label: 'Competiciones',
            data: [7, 10],
            backgroundColor: [
                'rgb(50, 205, 50)',
                'rgb(125, 127, 125)',

            ]
        }],

    },
    options: {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }],
            xAxes: [{
                gridLines: {
                    offsetGridLines: true
                }
            }]
        },
        legend: {
            display: false
        }
    }
});