/**
 * Referee Bar Chart
 */
let ctx3=document.getElementById("performanceChart").getContext("2d")
new Chart(ctx3, {
    type: 'bar',
    data: {
        labels: ['Asistencias','Confirmaciones','Ausencias','Convocatorias'],
        datasets: [{
            label: 'Competiciones',
            data:[5,7,2,7],
            backgroundColor:[
                'rgb(0, 170, 228)',
                'rgb(50, 205, 50)',
                'rgb(255, 36, 0)',
                'rgb(125, 127, 125)',

            ]
        }],

    },
    options: {
        scales:{
            yAxes:[{
                ticks: {
                    beginAtZero: true
                }
            }],
            xAxes: [{
                gridLines: {
                    offsetGridLines: true
                }
            }]
        }
    }
});