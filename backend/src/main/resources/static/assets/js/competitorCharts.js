/**
 * Competitor Bar Chart
 */
let ctx = document.getElementById("chart1").getContext("2d");
let ctx2 = document.getElementById("chart2").getContext("2d");
$.ajax({
    url: "/api/competitors/points/" + $("#licenseId").text(),
    method: 'get'
}).done((ans) => {
    if (ans != null) {
        let bronzes = 0;
        let silvers = 0;
        let golds = 0;
        let total = 0;
        let list = [];
        let tags = [];
        let acc = 0;
        let it = 0;
        for (let item of ans) {
            switch (item) {
                case 1:
                    bronzes++;
                    break;
                case 2:
                    silvers++;
                    break;
                case 3:
                    golds++;
                    break;
            }
            total++;
        }
        for (let item of ans) {
            acc += parseInt(item);
            it += 1;
            tags.push(it);
            list.push(acc);
        }
        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: ['Bronce', 'Plata', 'Oro', 'Competiciones'],
                datasets: [{
                    label: 'Mis medallas',
                    data: [bronzes, silvers, golds, total],
                    backgroundColor: [
                        'rgb(205,127,50)',
                        'rgb(192, 192, 192)',
                        'rgb(255, 215, 0)',
                        'rgb(125, 127, 125)'
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
        let competitorLineChartData = {
            labels: tags,
            datasets: [{
                label: "Puntos del Ranking",
                data: list,
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
    }
});