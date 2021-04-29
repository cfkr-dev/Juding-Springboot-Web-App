import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {CompetitionInterface} from '../competition/competition.interface';
import {HttpClient} from '@angular/common/http';
import {LoggedInUserService} from '../logged-in-user.service';
import {UserInterface} from '../user/user.interface';
import {MyHomeService} from './my-home.service';
import {RefereeRangeService} from '../auxTypes/refereeRange.service';

interface Auxiliar {
  list: CompetitionInterface[];
  state: boolean;
}

@Component({
  selector: 'app-my-home',
  templateUrl: './my-home.component.html',
  styleUrls: ['../../assets/vendor/font-awesome/css/all.css', '../../assets/css/style.css', '../../assets/css/header.css', '../../assets/css/bootstrapAccomodations.css', '../../assets/css/profiles.css', '../../assets/css/responsiveTable.css', '../../assets/css/beltAssignations.css']
})
export class MyHomeComponent implements OnInit {

  fullLoaded: boolean;
  currentUser: UserInterface;
  pastCompetitions: CompetitionInterface[];
  currentCompetitions: CompetitionInterface[];
  futureJoinedCompetitions: CompetitionInterface[];
  futureNotJoinedCompetitions: CompetitionInterface[];

  constructor(private http: HttpClient,
              private loggedInUser: LoggedInUserService,
              private router: Router,
              private homeInfo: MyHomeService,
              public rangeService: RefereeRangeService) {
    this.fullLoaded = false;
  }

  public chart1 = {
    barChartOptions: {
      responsive: true,
      // We use these empty structures as placeholders for dynamic theming.
      scales: {xAxes: [], yAxes: [{ticks: {beginAtZero: true, stepSize: 1}}]},
      plugins: {}
    },
    barChartLabels: ['Mis medallas'],
    barChartType: 'bar',
    barChartLegend: true,
    barChartData: undefined
  };

  public chart2 = {
    lineChartLabels: [],
    lineChartOptions: {
      responsive: true,
      scales: {
        xAxes: [{}],
        yAxes: [{ticks: {beginAtZero: true, stepSize: 1}}]
      }
    },
    lineChartColors: [{
      borderColor: '#333333',
      backgroundColor: 'transparent',
      pointBorderColor: '#333333',
      pointBackgroundColor: '#333333',
      pointHoverBackgroundColor: '#333333',
      pointHoverBorderColor: '#333333',
    }],
    lineChartLegend: true,
    lineChartType: 'line',
    lineChartPlugins: [],
    lineChartData: []
  };

  ngOnInit(): void {
    this.loggedInUser.getLoggedUser().subscribe(
      ((currentUser: UserInterface) => {
        this.currentUser = currentUser;
        this.loggedInUser.getLoggedUserImage(currentUser.roles.includes('C') ? 'competitors' : 'referees', currentUser.licenseId).subscribe(
          ((image: Blob) => {
            const reader = new FileReader();
            reader.addEventListener('load', () => {
              this.currentUser.imageFile = reader.result.toString();
            }, false);
            if (image) {
              reader.readAsDataURL(image);
            }
            this.homeInfo.getCompetitions(currentUser, 'past').subscribe(
              ((pastCompetitions: CompetitionInterface[]) => {
                this.pastCompetitions = pastCompetitions;
                this.homeInfo.getCompetitions(currentUser, 'current').subscribe(
                  ((currentCompetitions: CompetitionInterface[]) => {
                    this.currentCompetitions = currentCompetitions;
                    this.homeInfo.getCompetitions(currentUser, 'future?joined=true').subscribe(
                      ((futureJoinedCompetitions: CompetitionInterface[]) => {
                        this.futureJoinedCompetitions = futureJoinedCompetitions;
                        if (currentUser.roles.includes('C')) {
                          this.homeInfo.getCompetitions(currentUser, 'future?joined=false').subscribe(
                            ((futureNotJoinedCompetitions: CompetitionInterface[]) => {
                              this.futureNotJoinedCompetitions = futureNotJoinedCompetitions;
                            }));
                          this.homeInfo.getCharts(currentUser).subscribe(
                            ((chartInfo: number[]) => {
                              let bronzes = 0;
                              let silvers = 0;
                              let golds = 0;
                              let total = 0;
                              let acc = 0;
                              let it = 0;
                              const tags = [];
                              const list = [];
                              for (const item of chartInfo) {
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
                                acc += item;
                                it += 1;
                                tags.push(it);
                                list.push(acc);
                                total++;

                                this.chart1.barChartData = [
                                  {data: [bronzes], label: 'Bronce', backgroundColor: '#CD7F32', hoverBackgroundColor: '#CD7F32'},
                                  {data: [silvers], label: 'Plata', backgroundColor: '#C0C0C0', hoverBackgroundColor: '#C0C0C0'},
                                  {data: [golds], label: 'Oro', backgroundColor: '#FFD700', hoverBackgroundColor: '#FFD700'},
                                  {data: [total], label: 'Competiciones', backgroundColor: '#7D7F7D', hoverBackgroundColor: '#7D7F7D'}
                                ];

                                this.chart2.lineChartData = [
                                  {data: list, label: 'Evolución de puntos', fill: false, tension: 0}
                                ];
                                this.chart2.lineChartLabels = tags;

                                this.fullLoaded = true;

                                // this.chart2.lineChartData = list;
                              }
                            })
                          );
                        } else if (currentUser.roles.includes('R')) {
                          this.fullLoaded = true;
                        }
                      })
                    );
                  })
                );
              })
            );
          })
        );
      }), (error => this.router.navigate(['/login']))
    );
  }
}
