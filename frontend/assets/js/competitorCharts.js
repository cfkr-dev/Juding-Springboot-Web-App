/**
 * Competitor Bar Chart
 */
let ctx=document.getElementById("medalChart").getContext("2d")
new Chart(ctx, {
    type: 'bar',
    data: {
            labels: ['Bronce','Plata','Oro','Competiciones'],
            datasets: [{
                label: 'Mis medallas',
                data:[5,4,3,20],
                backgroundColor:[
                    'rgb(205,127,50)',
                    'rgb(192, 192, 192)',
                    'rgb(255, 215, 0)',
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

/**
 * Competitor Line Chart
 */
let ctx2=document.getElementById("rankingChart").getContext("2d")

var competitorLineChartData = {
    labels: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"],
    datasets: [{
        label: "Puntos del Ranking",
        data: [0, 0, 0, 0, 1, 1, 3, 3, 4, 4, 5, 8, 10, 11, 12, 15, 17, 17, 20, 22],
        lineTension: 0,
        fill: false,
        borderColor: 'black',
        backgroundColor: 'transparent',
        borderDash: [5, 5],
        pointBorderColor: 'black',
        pointBackgroundColor: 'rgba(0,0,0,1)',
        pointRadius: 5,
        pointHoverRadius: 10,
        pointHitRadius: 30,
        pointBorderWidth: 2,
        pointStyle: 'rectRounded'
    }]
};

var chartOptions = {
    legend: {
        display: true,
        position: 'top',
        labels: {
            boxWidth: 80,
            fontColor: 'black'
        }
    }
};
new Chart(ctx2, {
    type: 'line',
    data: competitorLineChartData,
    options: chartOptions
});


