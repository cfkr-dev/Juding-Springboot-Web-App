import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {CompetitionInterface} from '../competition/competition.interface';
import {HttpClient} from '@angular/common/http';
import {LoggedInUserService} from '../logged-in-user.service';
import {UserInterface} from '../user/user.interface';
import {Observable} from 'rxjs';
import * as pluginDataLabels from 'chartjs-plugin-datalabels';
import {ChartDataSets, ChartOptions, ChartType} from '../../assets/vendor/chart/Chart.js';
import {Label} from 'ng2-charts';


@Component({
  selector: 'app-my-home',
  templateUrl: './my-home.component.html',
  styleUrls: ['../../assets/vendor/font-awesome/css/all.css', '../../assets/css/style.css', '../../assets/css/header.css', '../../assets/css/bootstrapAccomodations.css' , '../../assets/css/profiles.css', '../../assets/css/responsiveTable.css', '../../assets/css/beltAssignations.css']
})
export class MyHomeComponent implements OnInit {

  constructor(private http: HttpClient, private loggedInUser: LoggedInUserService, private router: Router) {
  }

  currentUser: UserInterface;
  pastCompetitions: CompetitionInterface[];
  currentCompetitions: CompetitionInterface[];
  futureJoinedCompetitions: CompetitionInterface[];
  futureNotJoinedCompetitions: CompetitionInterface[];

  barChartOptions: ChartOptions = {
    responsive: true,
    // We use these empty structures as placeholders for dynamic theming.
    scales: { xAxes: [{}], yAxes: [{}] },
    plugins: {
      datalabels: {
        anchor: 'end',
        align: 'end',
      }
    }
  };
  barChartLabels: Label[] = ['Bronce', 'Plata', 'Oro', 'Competiciones'];
  barChartType: ChartType = 'barChartType';
  barChartLegend = true;
  barChartPlugins = [pluginDataLabels];
  barChartData: ChartDataSets[] = [0, 0, 0, 0];

  getCompetitions(currentUser: UserInterface, urlParameter: string): Observable<CompetitionInterface[]> {
    return this.http.get('/api/competitions/' + currentUser.licenseId + '/' + urlParameter, {withCredentials: true}) as Observable<CompetitionInterface[]>;
  }

  ngOnInit(): void {
    this.loggedInUser.getLoggedUser().subscribe(
      ((currentUser: UserInterface) => {
        this.currentUser = currentUser;
        this.getCompetitions(currentUser, 'past').subscribe(
          ((pastCompetitions: CompetitionInterface[]) => {
            console.clear();
            console.log(pastCompetitions);
            this.pastCompetitions = pastCompetitions;
            this.getCompetitions(currentUser, 'current').subscribe(
              ((currentCompetitions: CompetitionInterface[]) => {
                this.currentCompetitions = currentCompetitions;
                this.getCompetitions(currentUser, 'future?joined=true').subscribe(
                  ((futureJoinedCompetitions: CompetitionInterface[]) => {
                    this.futureJoinedCompetitions = futureJoinedCompetitions;
                    if (currentUser.roles.includes('C')) {
                      this.http.get('/api/competitors/points/' + currentUser.licenseId, {withCredentials: true}).subscribe(
                        (chartInformation: number[]) => {
                          let bronzes = 0;
                          let silvers = 0;
                          let golds = 0;
                          let total = 0;
                          for (let item of chartInformation) {
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

                          this.barChartData = [bronzes, silvers, golds, total];
                          console.log(this.barChartData);
                        }
                      );
                    } else if (currentUser.roles.includes('R')) {
                      this.getCompetitions(currentUser, 'future?joined=false').subscribe(
                        ((futureNotJoinedCompetitions: CompetitionInterface[]) => {
                          this.futureNotJoinedCompetitions = futureNotJoinedCompetitions;
                        })
                      );
                    }
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
