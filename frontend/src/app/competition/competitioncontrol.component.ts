import {Component} from "@angular/core";
import {Competition} from "./competition.model";
import {ActivatedRoute, Route} from "@angular/router";
import {CompetitionService} from "./competition.service";
import {Fight} from "../fight/fight.model";

@Component({
    selector: 'competitioncontrol',
    templateUrl: 'competitioncontrol.html'
})
export class CompetitionControl {
    competition: Competition
    selectItems: string

    constructor(private route: Route, activatedRoute: ActivatedRoute, private service: CompetitionService) {
        let id = activatedRoute.snapshot.params['id'];
        service.getCompetition(id).subscribe(
            competition => {
                this.competition = competition;
                this.selectItems = this.showControlScreen(this.competition);

            },
            error => console.error(error),
        );
    }

    private showControlScreen(competition: Competition){
        let fights = this.competition.fights;
        let sb: string;
        sb.concat("<optgroup label=\"Octavos\">");
        for (let i= 14; i>=7; i--) {
            if (!fights[i].isFinished){
                if (fights[i].winner !== null && fights[i].loser !== null) {
                    sb.concat("<option value=\"",
                        i.toString(),
                        "\" data-nick1=\"",
                        fights[i].winner.nickname,
                        "\" data-nick2=\"",
                        fights[i].loser.nickname,
                        "\">",
                        fights[i].winner.nickname,
                        " - ",
                        fights[i].loser.nickname,
                        "</option>")
                }
            }
        }
        sb.concat("</optgroup>");

        sb.concat("<optgroup label=\"Cuartos\">");
        for (let i= 6; i>=3; i--) {
            if (!fights[i].isFinished){
                if (fights[i].winner !== null && fights[i].loser !== null) {
                    sb.concat("<option value=\"",
                        i.toString(),
                        "\" data-nick1=\"",
                        fights[i].winner.nickname,
                        "\" data-nick2=\"",
                        fights[i].loser.nickname,
                        "\">",
                        fights[i].winner.nickname,
                        " - ",
                        fights[i].loser.nickname,
                        "</option>")
                }
            }
        }
        sb.concat("</optgroup>");

        sb.concat("<optgroup label=\"Semifinales\">");
        for (let i= 2; i>=1; i--) {
            if (!fights[i].isFinished){
                if (fights[i].winner !== null && fights[i].loser !== null) {
                    sb.concat("<option value=\"",
                        i.toString(),
                        "\" data-nick1=\"",
                        fights[i].winner.nickname,
                        "\" data-nick2=\"",
                        fights[i].loser.nickname,
                        "\">",
                        fights[i].winner.nickname,
                        " - ",
                        fights[i].loser.nickname,
                        "</option>")
                }
            }
        }
        sb.concat("</optgroup>");

        sb.concat("<optgroup label=\"Final\">");
            if (!fights[0].isFinished) {
                if (fights[0].winner !== null && fights[0].loser !== null) {
                    sb.concat("<option value=\"",
                        "0",
                        "\" data-nick1=\"",
                        fights[0].winner.nickname,
                        "\" data-nick2=\"",
                        fights[0].loser.nickname,
                        "\">",
                        fights[0].winner.nickname,
                        " - ",
                        fights[0].loser.nickname,
                        "</option>")
                }
            }
        sb.concat("</optgroup>");
        return sb;

    }
}

