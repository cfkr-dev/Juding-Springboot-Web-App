/**
 * Competitor Bar Chart
 */
let ctx = document.getElementById("medalChart").getContext("2d")
new Chart(ctx, {
    type: 'bar',
    data: {
        labels: ['Bronce', 'Plata', 'Oro', 'Competiciones'],
        datasets: [{
            label: 'Mis medallas',
            data: [5, 4, 3, 20],
            backgroundColor: [
                'rgb(205,127,50)',
                'rgb(192, 192, 192)',
                'rgb(255, 215, 0)',
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

/**
 * Competitor Line Chart
 */
let ctx2 = document.getElementById("rankingChart").getContext("2d")

let competitorLineChartData = {
    labels: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"],
    datasets: [{
        label: "Puntos del Ranking",
        data: [0, 0, 0, 0, 1, 1, 3, 3, 4, 4, 5, 8, 10, 11, 12, 15, 17, 20, 22, 25],
        lineTension: 0,
        fill: false,
        borderColor: '#333333',
        backgroundColor: 'transparent',
        pointBorderColor: '#333333',
        pointBackgroundColor: '#333333',
        pointRadius: 4,
        pointHoverRadius: 5,
        pointHitRadius: 7,
        pointBorderWidth: 2,
        pointStyle: 'round'
    }]
};

let chartOptions = {
    legend: {
        display: false
    }
};
new Chart(ctx2, {
    type: 'line',
    data: competitorLineChartData,
    options: chartOptions
});


