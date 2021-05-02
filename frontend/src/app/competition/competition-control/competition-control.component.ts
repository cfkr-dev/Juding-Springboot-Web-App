import {Component, OnInit} from '@angular/core';
import {Competition} from '../competition.model';
import {ActivatedRoute} from '@angular/router';
import {CompetitionService} from '../competition.service';
import {Fight} from '../../fight/fight.model';
import {Router} from '@angular/router';
import {HostListener} from '@angular/core';
import {LoggedInUserService} from '../../logged-in-user.service';

interface FightsTournament {
    final: Fight[];
    semifinal: Fight[];
    quarterFinals: Fight[];
    roundOfSixteen: Fight[];
}

@Component({
    selector: 'app-competitioncontrol',
    templateUrl: './competition-control.html',
    providers: [CompetitionService],
    styleUrls: ['../../../assets/vendor/bootstrap/v4/css/bootstrap.css',
        '../../../assets/vendor/font-awesome/css/all.css',
        '../../../assets/vendor/aos/aos.css',
        '../../../assets/css/style.css',
        '../../../assets/css/header.css',
        '../../../assets/css/bootstrapAccomodations.css',
        '../../../assets/css/responsiveTable.css',
        '../../../assets/css/competitionController.css']
})
export class CompetitionControlComponent implements OnInit {
    competition: Competition;
    fights: FightsTournament;
    selectItems: string;

    fullLoaded: boolean;

    player1: Array<any>;
    player2: Array<any>;
    bigStopwatch: Array<any>;
    bigStopwatchInterv;
    smallStopwatch: Array<any>;
    smallStopwatchInterv;

    screenFullFunc: boolean;

    disabled: boolean;
    disabledG: boolean;
    disabledSelect: boolean;
    selectedFight: string;
    selectedFightNumber: number;


    constructor(private router: Router, activatedRouter: ActivatedRoute, private competitionService: CompetitionService, public loginInUserService: LoggedInUserService) {
        // PLAYERS VARIABLES: include, in the following order:
        // [0]: Points of player X during fight (max 10)
        // [1]: Penalties of player X during fight (max 3)
        // [2]: Nickname associated when selecting fight
        this.player1 = [0, 0, ' '];
        this.player2 = [0, 0, ' '];
        const id = activatedRouter.snapshot.params.id;
        this.fights = {final: [], quarterFinals: [], roundOfSixteen: [], semifinal: []};
        competitionService.getCompetition(id).subscribe(
            competition => {
                this.competition = competition;
                for (let fight = 14; fight >= 7; fight--) {
                    this.fights.roundOfSixteen.push(this.competition.fights[fight]);
                }
                for (let fight = 6; fight >= 3; fight--) {
                    this.fights.quarterFinals.push(this.competition.fights[fight]);
                }
                for (let fight = 2; fight >= 1; fight--) {
                    this.fights.semifinal.push(this.competition.fights[fight]);
                }
                this.fights.final.push(this.competition.fights[0]);
                this.fullLoaded = true;
            },
            error => this.router.navigate(['/500']),
        );
    }

    ngOnInit(): void {
        this.loginInUserService.getLoggedUser().subscribe(
            user => {
                if (user.roles.includes('R')) {
                    /*
                    * GLOBAL VARIABLES
                    * These variables are needed during all the simulator execution.
                    */

                    // STOPWATCHES VARIABLES
                    // BIG STOPWATCH VARIABLE: include, in the following order:
                    // [0]: Minute number of stopwatch
                    // [1]: Second number of stopwatch (max 59)
                    // [2]: isActivated (false if stopped, true if currently counting)
                    this.bigStopwatch = [0, 0, false];

                    // SMALL STOPWATCH VARIABLE
                    // [0]: Player using currently small stopwatch (either 1 or 2, 0 if deactivated)
                    // [1]: Second number of stopwatch (max 20)
                    // [2]: isActivated (false if stopped, true if currently counting)
                    this.smallStopwatch = [0, 0, false];

                    // CONTROL VARIABLES
                    // screenFullFunc (true if screen is working, false if not)
                    this.screenFullFunc = false;

                    this.disabled = true;
                    this.disabledG = true;
                    this.disabledSelect = false;
                }
            },
            error => this.router.navigate(['/403']),
        );
    }

    /**
     * STOPWATCH SIMULATOR FOR JUDO - v2.0
     * This is the stopwatch simulator created and developed for Juding Web App.
     * It allows referees to use either keyboard and/or mouse and buttons in UI to use this simulator to referee fights in
     * competitions attending to judo basic rules and laws.
     *
     * Developed by Diego Guerrero, Ismael González, Alberto Pérez and José Luis Toledano.
     * (C) May 2021 Group 2 Web Applications Development (DAW) - University Rey Juan Carlos.
     */

    /*
     * STOPWATCH INITIALIZATION
     *
     * STEP 1: Choose a fight
     * Select list where referee can choose a match of a competition.
     * When chosen, select is disabled and button for starting fight is enabled.
     */
    fightSelected(): void {
        // IF a valid fight is chosen
        this.selectedFightNumber = parseInt(this.selectedFight, 10);
        if (this.selectedFightNumber !== -1
        ) {

            // Enabling start Button and Disabling select list
            this.disabledG = false;
            // Getting current fighters
            for (let i = 0; i < this.competition.fights.length; i++) {
                if (this.selectedFightNumber === this.competition.fights[i].idFight) {
                    this.player1[2] = this.competition.fights[i].winner.nickname;
                    this.player2[2] = this.competition.fights[i].loser.nickname;
                }
            }
        }
    }

    /*
     * STEP 2: Start
     * All buttons are enabled and control boolean screenFullFunc is enabled (key events are recognized).
     */
    startFight(): void {
        this.disabled = false;
        this.disabledG = true;
        this.disabledSelect = true;
        this.screenFullFunc = true;
    }

    /*
     * STEP 3: Fight (and referee)!
     * Many functionalities are enabled during this step.
     */

// 3.1: Adding and substracting points and penalties functions
// These functions can be executed with keystrokes and with buttons
    /**
     * addPointPlayer1()
     * Adds a point for player 1 and updates info.
     * If this player gets 10 points, the fight is ended (and player 1 wins).
     */
    addPointPlayer1(): void {
        if (this.player1[0] < 10
        ) {
            this.player1[0] = this.player1[0] + 1;
        }
        if (this.player1[0] === 10) {
            this.endOfFight();
        }
    }

    /**
     * subtractPointPlayer1()
     * Subtracts a point for player 1 and updates info (no negative values allowed).
     */
    subtractPointPlayer1(): void {
        if (this.player1[0] > 0
        ) {
            this.player1[0] = this.player1[0] - 1;
        }
    }

    /**
     * addPenaltyPlayer1()
     * Adds a penalty for player 1 and updates info.
     * If this player gets 3 penalties, the fight is ended (and player 2 wins).
     */
    addPenaltyPlayer1(): void {
        if (this.player1[1] < 3
        ) {
            this.player1[1] = this.player1[1] + 1;
        }
        if (this.player1[1] === 3) {
            this.endOfFight();
        }
    }

    /**
     * subtractPenaltyPlayer1()
     * Subtracts a penalty for player 1 and updates info (no negative values allowed).
     */
    subtractPenaltyPlayer1(): void {
        if (this.player1[1] > 0
        ) {
            this.player1[1] = this.player1[1] - 1;
        }
    }


    /**
     * addPointPlayer2()
     * Adds a point for player 2 and updates info.
     * If this player gets 10 points, the fight is ended (and player 2 wins).
     */
    addPointPlayer2(): void {
        if (this.player2[0] < 10
        ) {
            this.player2[0] = this.player2[0] + 1;
        }
        if (this.player2[0] === 10) {
            this.endOfFight();
        }
    }

    /**
     * subtractPointPlayer2()
     * Subtracts a point for player 2 and updates info (no negative values allowed).
     */
    subtractPointPlayer2(): void {
        if (this.player2[0] > 0
        ) {
            this.player2[0] = this.player2[0] - 1;
        }
    }

    /**
     * addPenaltyPlayer2()
     * Adds a penalty for player 2 and updates info.
     * If this player gets 3 penalties, the fight is ended (and player 1 wins).
     */
    addPenaltyPlayer2(): void {
        if (this.player2[1] < 3
        ) {
            this.player2[1] = this.player2[1] + 1;
        }
        if (this.player2[1] === 3) {
            this.endOfFight();
        }
    }

    /**
     * subtractPenaltyPlayer2()
     * Subtracts a penalty for player 2 and updates info (no negative values allowed).
     */
    subtractPenaltyPlayer2(): void {
        if (this.player2[1] > 0
        ) {
            this.player2[1] = this.player2[1] - 1;
        }
    }

// 3.2: Big stopwatch controllers
// These functions are controlling the main stopwatch.
    /**
     * bigStopwatchTurnOn()
     * Starts or restarts (if previously started) the stopwatch.
     */
    bigStopwatchTurnOn(): void {
        if (!
            this.bigStopwatch[2]
        ) {
            // isActivated = true (big stopwatch gets active)
            this.bigStopwatch[2] = true;
            // Setting interval: lambda function inside is executed every 1000 milliseconds (1 second)
            this.bigStopwatchInterv = setInterval(() => {
                // 1 more second
                this.bigStopwatch[1] = this.bigStopwatch[1] + 1;
                // If 60 secs are accumulated, +1 minute and 0 seconds
                if (this.bigStopwatch[1] === 60) {
                    this.bigStopwatch[0]++;
                    this.bigStopwatch[1] = 0;
                }
            }, 1000);
        }
    }

    /**
     * bigStopwatchTurnOff()
     * Stops (pauses) the big stopwatch.
     */
    bigStopwatchTurnOff(): void {
        if (this.bigStopwatch[2]
        ) {
            // STOP HANDLER function of setInterval
            clearInterval(this.bigStopwatchInterv);
            // isActivated = false since it gets turned off.
            this.bigStopwatch[2] = false;
        }
    }

// 3.3: Small stopwatch controllers
// These functions are controlling the little auxiliary stopwatch.
// This stopwatch changes its color depending on the player that needs it.
    /**
     * smallStopwatchTurnOn()
     * Starts or restarts (if previously started) the stopwatch attending to current player.
     */
    smallStopwatchTurnOn(player: number): void {
        if (!
            this.smallStopwatch[2]
        ) {
            // Small stopwatch gets activated.
            this.smallStopwatch[2] = true;
            // IF the player that needs the stopwatch is NOT the same that used it the last time...
            if (player !== this.smallStopwatch[0]) {
                // ... it's necessary to restart the count
                this.smallStopwatch[1] = 0;
            }
            // SETTING UP the new player
            this.smallStopwatch[0] = player;
            // Setting interval: lambda function inside is executed every 1000 milliseconds (1 second)
            this.smallStopwatchInterv = setInterval(() => {
                if (this.smallStopwatch[1] === 20) {
                    // IF time is 20, then interval must be stopped, player gets 10 points and the victory.
                    clearInterval(this.smallStopwatchInterv);
                    if (this.smallStopwatch[0] === 1) {
                        for (let i = 0; i < 10; i++) {
                            this.addPointPlayer1();
                        }
                    } else {
                        for (let i = 0; i < 10; i++) {
                            this.addPointPlayer2();
                        }
                    }
                } else {
                    this.smallStopwatch[1] = this.smallStopwatch[1] + 1;
                }
                if (this.smallStopwatch[1] === 12) {
                    if (this.smallStopwatch[0] === 1) {
                        this.addPointPlayer1();
                    } else {
                        this.addPointPlayer2();
                    }
                }
            }, 1000);
        }
        // IF a stopwatch was already started...
        else {
            // STOP current stopwatch
            clearInterval(this.smallStopwatchInterv);
            // Set stopped stopwatch and null player (both in variables)
            this.smallStopwatch[0] = 0;
            this.smallStopwatch[2] = false;
            // START stopwatch for new player
            this.smallStopwatchTurnOn(player);
        }
    }

    /**
     * smallStopwatchTurnOff()
     * Stops (pauses) the small stopwatch.
     */
    smallStopwatchTurnOff(): void {
        if (this.smallStopwatch[2]
        ) {
            // SET small stopwatch is stopped (isActivated = false)
            this.smallStopwatch[2] = false;
            // STOP interval handler function for small stopwatch
            clearInterval(this.smallStopwatchInterv);
        }
    }

    /**
     * smallStopwatchReset()
     * Resets the small stopwatch.
     */
    smallStopwatchReset(): void {
        // STOP interval handler function for small stopwatch
        clearInterval(this.smallStopwatchInterv
        );
        // RESTART values: timing is set to 0
        this.smallStopwatch[1] = 0;
        // RESTART values: player
        this.smallStopwatch[0] = 0;
    }

    @HostListener('window:keypress', ['$event'])
    keyboardHandler(evt: KeyboardEvent):
        void {
        // Keyboard shortcuts is only working when screenFullFunc is enabled or when it's starting the fight
        if (this.screenFullFunc || evt.key === 'G' || evt.key === 'g'
        ) {
            switch (evt.key) {
                case 'W': // 87
                case 'w': // 119
                    this.addPointPlayer1();
                    break;
                case 'E': // 69
                case 'e': // 101
                    this.subtractPointPlayer1();
                    break;
                case 'S': // 83
                case 's': // 115
                    this.addPenaltyPlayer1();
                    break;
                case 'D': // 68
                case 'd': // 100
                    this.subtractPenaltyPlayer1();
                    break;
                case 'I': // 73
                case 'i': // 105
                    this.addPointPlayer2();
                    break;
                case 'O': // 79
                case 'o': // 111
                    this.subtractPointPlayer2();
                    break;
                case 'K': // 75
                case 'k': // 107
                    this.addPenaltyPlayer2();
                    break;
                case 'L': // 76
                case 'l': // 108
                    this.subtractPenaltyPlayer2();
                    break;
                case 'G': // 71
                case 'g': // 103
                    this.startFight();
                    break;
                case 'Spacebar':
                case ' ': // 32
                    if (this.bigStopwatch[2]) {
                        this.bigStopwatchTurnOff();
                    } else {
                        this.bigStopwatchTurnOn();
                    }
                    break;
                case 'Q': // 81
                case 'q': // 113
                    this.smallStopwatchTurnOn(1);
                    break;
                case 'P': // 80
                case 'p': // 112
                    this.smallStopwatchTurnOn(2);
                    break;
                case 'V': // 86
                case 'v': // 118
                    this.smallStopwatchTurnOff();
                    break;
                case 'B': // 66
                case 'b': // 98
                    this.smallStopwatchReset();
                    break;
            }
        }
    }


    /*
     * STEP 4: End of fight! Player X won!
     */
    /**
     * endOfFight()
     * Fight has ended and either player 1 or player 2 have won the fight.
     * Screen functionalities get disabled and winner and loser info are packed.
     */
    endOfFight(): void {
        // Stopping stopwatches
        this.bigStopwatchTurnOff();
        this.smallStopwatchTurnOff();

        // Answer of the fight
        if (this.player1[0] === 10 || this.player1[1] === 3 || this.player2[0] === 10 || this.player2[1] === 3
        ) {
            if (this.player1[0] === 10 || this.player2[1] === 3) {
                this.competitionService.updateFight(this.competition.idCompetition, this.player1[2], this.player2[2]).subscribe(
                    finish => {
                        this.router.navigate(['competitions/' + this.competition.idCompetition]);
                        this.disabled = true;
                        this.screenFullFunc = false;
                        this.exit();
                    },
                    error => alert('Error fatal: anota los resultados antes de cerrar la página.')
                );
            } else {
                this.competitionService.updateFight(this.competition.idCompetition, this.player2[2], this.player1[2]).subscribe(
                    finish => {
                        this.router.navigate(['competitions/' + this.competition.idCompetition]);
                        this.disabled = true;
                        this.screenFullFunc = false;
                        this.exit();
                    },
                    error => alert('Error fatal: anota los resultados antes de cerrar la página.')
                );
            }
        } else {
            alert('Ninguno de los competidores cumple las condiciones para ser declarado ganador.');
        }
    }

    exit(): void {
        if (confirm('¿Desea abandonar la página? Se perderán los valores actuales.')
        ) {
            this.router.navigate(['competitions/' + this.competition.idCompetition]);
        }
    }
}



