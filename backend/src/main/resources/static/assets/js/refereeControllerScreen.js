/**
 * STOPWATCH SIMULATOR FOR JUDO - v1.0
 * This is the stopwatch simulator created and developed for Juding Web App.
 * It allows referees to use either keyboard and/or mouse and buttons in UI to use this simulator to referee fights in
 * competitions attending to judo basic rules and laws.
 *
 * Developed by Diego Guerrero, Ismael González, Alberto Pérez and José Luis Toledano.
 * (C) March 2021 Group 2 Web Applications Development (DAW) - University Rey Juan Carlos.
 */
$(function () {
    /*
     * GLOBAL VARIABLES
     * These variables are needed during all the simulator execution.
     */
    // PLAYERS VARIABLES: include, in the following order:
    // [0]: Points of player X during fight (max 10)
    // [1]: HTML UI element for showing number of points
    // [2]: Penalties of player X during fight (max 3)
    // [3]: HTML UI element for showing number of penalties
    // [4]: Nickname associated when selecting fight
    let player1 = [0, $("#points1"), 0, $("#penalties1")];
    let player2 = [0, $("#points2"), 0, $("#penalties2")];

    // STOPWATCHES VARIABLES
    // BIG STOPWATCH VARIABLE: include, in the following order:
    // [0]: HTML UI element for showing stopwatch value (in format 00:00)
    // [1]: Minute number of stopwatch
    // [2]: Second number of stopwatch (max 59)
    // [3]: isActivated (false if stopped, true if currently counting)
    let bigStopwatch = [$("#bigStopwatch"), 0, 0, false];

    // SMALL STOPWATCH VARIABLE
    // [0]: HTML UI element for showing stopwatch value (in format 00)
    // [1]: Player using currently small stopwatch (either 1 or 2, 0 if deactivated)
    // [2]: Second number of stopwatch (max 20)
    // [3]: isActivated (false if stopped, true if currently counting)
    let smallStopwatch = [$("#smallStopwatch"), 0, 0, false];

    // HTML UI ELEMENTS
    // Every HTML selector needed for the script is saved here in some variables
    // Their use is explained where they're used
    let keyG = $("#keyG"); // Button related to key G
    let keyQ = $("#keyQ"); // Button related to key Q
    let keyW = $("#keyW"); // Button related to key W
    let keyE = $("#keyE"); // Button related to key E
    let keyS = $("#keyS"); // Button related to key S
    let keyD = $("#keyD"); // Button related to key D
    let keyV = $("#keyV"); // Button related to key V
    let keyB = $("#keyB"); // Button related to key B
    let keyP = $("#keyP"); // Button related to key P
    let keyI = $("#keyI"); // Button related to key I
    let keyO = $("#keyO"); // Button related to key O
    let keyK = $("#keyK"); // Button related to key K
    let keyL = $("#keyL"); // Button related to key L
    let keySpace1 = $("#keySpace1");  // Button related to key space (1)
    let keySpace2 = $("#keySpace2");  // Button related to key space (2)
    let fightSel = $("#fight"); // Select of fight (step 1)
    let endBtn = $("#endBtn"); // Button for ending fight

    // CONTROL VARIABLES
    // screenFullFunc (true if screen is working, false if not)
    let screenFullFunc = false;
    // smallStopwatchInterv: saves the interval of small stopwatch when it's set (to be able to clearInterval() later)
    let smallStopwatchInterv;
    // bigStopwatchInterv: saves the interval of big stopwatch when it's set (to be able to clearInterval() later)
    let bigStopwatchInterv;

    /*
     * STOPWATCH INITIALIZATION
     *
     * STEP 1: Choose a fight
     * UI contains a select list where referee can choose a match of a competition.
     * When chosen, select is disabled and button for starting fight is enabled.
     */
    fightSel.change(function () {
        // IF a valid fight is chosen
        if ($(this).val() !== "-1") {
            // Enabling start Button
            keyG.prop("disabled", false);
            // Disabling select list
            $(this).blur().attr("disabled", "");
            // Getting current fighters
            let selector = $("#fight option[value=" + $("#fight").val() + "]");
            player1.push(selector.data("nick1"));
            player2.push(selector.data("nick2"));
        }
    });

    /*
     * STEP 2: Start
     * All buttons are enabled and control boolean screenFullFunc is enabled (key events are recognized).
     */
    function startFight() {
        keyQ.removeAttr("disabled");
        keyW.removeAttr("disabled");
        keyE.removeAttr("disabled");
        keyS.removeAttr("disabled");
        keyD.removeAttr("disabled");
        keyV.removeAttr("disabled");
        keyB.removeAttr("disabled");
        keyP.removeAttr("disabled");
        keyI.removeAttr("disabled");
        keyO.removeAttr("disabled");
        keyK.removeAttr("disabled");
        keyL.removeAttr("disabled");
        keySpace1.removeAttr("disabled");
        keySpace2.removeAttr("disabled");
        endBtn.removeAttr("disabled");
        keyG.blur();
        keyG.prop("disabled", true);
        $("#nick1").text(player1[4]);
        $("#nick2").text(player2[4]);
        screenFullFunc = true;
    }

    /*
     * STEP 3: Fight (and referee)!
     * Many functionalities are enabled during this step.
     */

    // 3.1: Adding and substracting points and penalties functions
    // These functions can be executed with keystrokes and with UI buttons
    /**
     * addPointPlayer1()
     * Adds a point for player 1 and updates UI info.
     * If this player gets 10 points, the fight is ended (and player 1 wins).
     */
    function addPointPlayer1() {
        player1[0] = (player1[0] + 1 >= 10) ? 10 : player1[0] + 1;
        player1[1].html(player1[0]);
        if (player1[0] >= 10)
            endOfFight();
    }

    /**
     * subtractPointPlayer1()
     * Subtracts a point for player 1 and updates UI info (no negative values allowed).
     */
    function subtractPointPlayer1() {
        player1[0] = (player1[0] - 1 >= 0) ? player1[0] - 1 : 0;
        player1[1].html(player1[0]);
    }

    /**
     * addPenaltyPlayer1()
     * Adds a penalty for player 1 and updates UI info.
     * If this player gets 3 penalties, the fight is ended (and player 2 wins).
     */
    function addPenaltyPlayer1() {
        player1[2] = (player1[2] + 1 >= 3) ? 3 : player1[2] + 1;
        player1[3].html(player1[2]);
        if (player1[2] >= 3)
            endOfFight();
    }

    /**
     * subtractPenaltyPlayer1()
     * Subtracts a penalty for player 1 and updates UI info (no negative values allowed).
     */
    function subtractPenaltyPlayer1() {
        player1[2] = (player1[2] - 1 >= 0) ? player1[2] - 1 : 0;
        player1[3].html(player1[2]);
    }

    /**
     * addPointPlayer2()
     * Adds a point for player 2 and updates UI info.
     * If this player gets 10 points, the fight is ended (and player 2 wins).
     */
    function addPointPlayer2() {
        player2[0] = (player2[0] + 1 >= 10) ? 10 : player2[0] + 1;
        player2[1].html(player2[0]);
        if (player2[0] >= 10)
            endOfFight();
    }

    /**
     * subtractPointPlayer2()
     * Subtracts a point for player 2 and updates UI info (no negative values allowed).
     */
    function subtractPointPlayer2() {
        player2[0] = (player2[0] - 1 >= 0) ? player2[0] - 1 : 0;
        player2[1].html(player2[0]);
    }

    /**
     * addPenaltyPlayer2()
     * Adds a penalty for player 2 and updates UI info.
     * If this player gets 3 penalties, the fight is ended (and player 1 wins).
     */
    function addPenaltyPlayer2() {
        player2[2] = (player2[2] + 1 >= 3) ? 3 : player2[2] + 1;
        player2[3].html(player2[2]);
        if (player2[2] >= 3)
            endOfFight();
    }

    /**
     * subtractPenaltyPlayer2()
     * Subtracts a penalty for player 2 and updates UI info (no negative values allowed).
     */
    function subtractPenaltyPlayer2() {
        player2[2] = (player2[2] - 1 >= 0) ? player2[2] - 1 : 0;
        player2[3].html(player2[2]);
    }

    // 3.2: Big stopwatch controllers
    // These functions are controlling the main stopwatch.
    /**
     * bigStopwatchTurnOn()
     * Starts or restarts (if previously started) the stopwatch.
     */
    function bigStopwatchTurnOn() {
        if (!bigStopwatch[3]) {
            // isActivated = true (big stopwatch gets active)
            bigStopwatch[3] = true;
            // CSS UI changes (changing outline by normal style in active button)
            keySpace1.removeClass("btn-outline-primary").addClass("btn-primary");
            keySpace2.removeClass("btn-danger").addClass("btn-outline-danger");
            // Setting interval: lambda function inside is executed every 1000 milliseconds (1 second)
            bigStopwatchInterv = setInterval(function () {
                // 1 more second
                bigStopwatch[2]++;
                // If 60 secs are accumulated, +1 minute and 0 seconds
                if (bigStopwatch[2] === 60) {
                    bigStopwatch[1]++;
                    bigStopwatch[2] = 0;
                }
                // HTML UI change: new time
                bigStopwatch[0].html(("0" + bigStopwatch[1]).slice(-2) + ":" + ("0" + bigStopwatch[2]).slice(-2));
            }, 1000);
        }
    }

    /**
     * bigStopwatchTurnOff()
     * Stops (pauses) the big stopwatch.
     */
    function bigStopwatchTurnOff() {
        if (bigStopwatch[3]) {
            // isActivated = false since it gets turned off.
            bigStopwatch[3] = false;
            // CSS UI changes (changing outline by normal style in active button)
            keySpace1.addClass("btn-outline-primary").removeClass("btn-primary");
            keySpace2.addClass("btn-danger").removeClass("btn-outline-danger");
            // STOP HANDLER function of setInterval
            clearInterval(bigStopwatchInterv);
        }
    }

    // 3.3: Small stopwatch controllers
    // These functions are controlling the little auxiliary stopwatch.
    // This stopwatch changes its color depending on the player that needs it.
    /**
     * smallStopwatchTurnOn()
     * Starts or restarts (if previously started) the stopwatch attending to current player.
     */
    function smallStopwatchTurnOn(jugador) {
        if (!smallStopwatch[3]) {
            // Small stopwatch gets activated.
            smallStopwatch[3] = true;
            // IF the player that needs the stopwatch is NOT the same that used it the last time...
            if (jugador !== smallStopwatch[1]) {
                // ... it's necessary to restart the count
                smallStopwatch[2] = 0;
                // ... and to load new value 0 in HTML view
                smallStopwatch[0].html("00");
            }
            // SETTING UP the new player
            smallStopwatch[1] = jugador;
            // IF player is...
            // ... 1 (white player), then stopwatch's text color will change to black.
            if (smallStopwatch[1] === 1) {
                smallStopwatch[0].css({"color": "black"});
                keyQ.addClass("btn-primary").removeClass("btn-outline-primary");
                keyP.addClass("btn-outline-primary").removeClass("btn-primary");
                keyV.addClass("btn-outline-danger").removeClass("btn-danger");
            }
            // ... 2 (blue player), then stopwatch's text color will change to blue.
            else {
                smallStopwatch[0].css({"color": "#90CAF9"});
                keyP.addClass("btn-primary").removeClass("btn-outline-primary");
                keyQ.addClass("btn-outline-primary").removeClass("btn-primary");
                keyV.addClass("btn-outline-danger").removeClass("btn-danger");
            }
            // Setting interval: lambda function inside is executed every 1000 milliseconds (1 second)
            smallStopwatchInterv = setInterval(function () {
                if (smallStopwatch[2] === 20) {
                    // IF time is 20, then interval must be stopped, player gets 10 points and the victory.
                    clearInterval(smallStopwatchInterv);
                    if (smallStopwatch[1] === 1) for (let i = 0; i < 10; i++) addPointPlayer1();
                    else for (let i = 0; i < 10; i++) addPointPlayer2();
                } else {
                    smallStopwatch[2]++;
                }
                if (smallStopwatch[2] === 12){
                    if (smallStopwatch[1] === 1) addPointPlayer1();
                    else addPointPlayer2();
                }
                // HTML UI change: new time
                smallStopwatch[0].html(("0" + smallStopwatch[2]).slice(-2));
            }, 1000);
        }
        // IF a stopwatch was already started...
        else {
            // STOP current stopwatch
            clearInterval(smallStopwatchInterv);
            // Set stopped stopwatch and null player (both in variables and in HTML UI)
            smallStopwatch[2] = 0;
            smallStopwatch[3] = false;
            smallStopwatch[0].html("00");
            // START stopwatch for new player
            smallStopwatchTurnOn(jugador);
        }
    }

    /**
     * smallStopwatchTurnOff()
     * Stops (pauses) the small stopwatch.
     */
    function smallStopwatchTurnOff() {
        if (smallStopwatch[3]) {
            // SET small stopwatch is stopped (isActivated = false)
            smallStopwatch[3] = false;
            // CSS UI changes (buttons getting outlined and filled)
            keyQ.addClass("btn-outline-primary").removeClass("btn-primary");
            keyP.addClass("btn-outline-primary").removeClass("btn-primary");
            keyV.addClass("btn-danger").removeClass("btn-outline-danger");
            // STOP interval handler function for small stopwatch
            clearInterval(smallStopwatchInterv);
        }
    }

    /**
     * smallStopwatchReset()
     * Resets the small stopwatch.
     */
    function smallStopwatchReset() {
        // STOP interval handler function for small stopwatch
        clearInterval(smallStopwatchInterv);
        // RESTART values: timing is set to 0 (both value and HTML UI showed time)
        smallStopwatch[2] = 0;
        smallStopwatch[0].html("--").css({"color": "black"});
        // CSS UI changes (buttons getting outlined and filled)
        keyQ.addClass("btn-outline-primary").removeClass("btn-primary");
        keyP.addClass("btn-outline-primary").removeClass("btn-primary");
        keyV.addClass("btn-outline-danger").removeClass("btn-danger");
    }


    /*
     * STEP 4: End of fight! Player X won!
     */
    /**
     * endOfFight()
     * Fight has ended and either player 1 or player 2 have won the fight.
     * Screen functionalities get disabled and winner and loser info are packed in JSON array (ready for backend).
     */
    function endOfFight() {
        // DISABLING all UI elements
        keyQ.prop("disabled", true);
        keyW.prop("disabled", true);
        keyE.prop("disabled", true);
        keyS.prop("disabled", true);
        keyD.prop("disabled", true);
        keyV.prop("disabled", true);
        keyB.prop("disabled", true);
        keyP.prop("disabled", true);
        keyI.prop("disabled", true);
        keyO.prop("disabled", true);
        keyK.prop("disabled", true);
        keyL.prop("disabled", true);
        keySpace1.prop("disabled", true);
        keySpace2.prop("disabled", true);
        screenFullFunc = false;

        // Stopping stopwatches
        bigStopwatchTurnOff();
        smallStopwatchTurnOff();

        // Package JSON Array
        if (player1[0] === 10 || player1[2] === 3 || player2[0] === 10 || player2[2] === 3) {
            $.ajax({
                data: {
                    "winner": (player1[0] === 10 || player2[2] === 3) ? player1[4] : player2[4],
                    "loser": (player1[0] === 10 || player2[2] === 3) ? player2[4] : player1[4]
                },
                url: "/competitions/" + idCompetition + "/control",
                method: 'get'
            }).done((ans) => {
                if (ans) {
                    alert("Combate finalizado y registrado correctamente. Cierre para continuar.");
                    window.location.replace(window.location.pathname.slice(0, -8));
                }
                else alert("Error fatal: anota los resultados antes de cerrar la página.");
            });
        } else {
            alert("Ninguno de los competidores cumple las condiciones para ser declarado ganador.");
        }
    }


    /*
     * TRIGGERS (I): event onKeyPress
     * All the functions introduced on simulator can be used by pressing some keys.
     */
    $(document).keypress(function (evt) {
        // Keyboard shortcuts is only working when screenFullFunc is enabled or when it's starting the fight
        if (screenFullFunc || evt.keyCode === 71 || evt.keyCode === 103) {
            switch (evt.keyCode) {
                case 87: // W
                case 119: // w
                    addPointPlayer1();
                    break;
                case 69: // E
                case 101: // e
                    subtractPointPlayer1();
                    break;
                case 83: // S
                case 115: // s
                    addPenaltyPlayer1();
                    break;
                case 68: // D
                case 100: // d
                    subtractPenaltyPlayer1();
                    break;
                case 73: // I
                case 105: // i
                    addPointPlayer2();
                    break;
                case 79: // O
                case 111: // o
                    subtractPointPlayer2();
                    break;
                case 75: // K
                case 107: // k
                    addPenaltyPlayer2();
                    break;
                case 76: // L
                case 108: // l
                    subtractPenaltyPlayer2();
                    break;
                case 71: // G
                case 103: // g
                    startFight();
                    break;
                case 32: // Space
                    if (bigStopwatch[3])
                        bigStopwatchTurnOff();
                    else
                        bigStopwatchTurnOn();
                    break;
                case 81: // Q
                case 113: // q
                    smallStopwatchTurnOn(1);
                    break;
                case 80: // P
                case 112: // p
                    smallStopwatchTurnOn(2);
                    break;
                case 86: // V
                case 118: // v
                    smallStopwatchTurnOff();
                    break;
                case 66: // B
                case 98: // b
                    smallStopwatchReset();
                    break;
            }
        }
    });


    /*
     * TRIGGERS (II): event onClick
     * All the functions introduced on simulator can be used by clicking some buttons on HTML UI.
     */
    keyG.click(() => startFight());

    keyW.click(() => addPointPlayer1());
    keyE.click(() => subtractPointPlayer1());
    keyS.click(() => addPenaltyPlayer1());
    keyD.click(() => subtractPenaltyPlayer1());
    keyI.click(() => addPointPlayer2());
    keyO.click(() => subtractPointPlayer2());
    keyK.click(() => addPenaltyPlayer2());
    keyL.click(() => subtractPenaltyPlayer2());

    keyQ.click(() => smallStopwatchTurnOn(1));
    keyP.click(() => smallStopwatchTurnOn(2));
    keyV.click(() => smallStopwatchTurnOff());
    keyB.click(() => smallStopwatchReset());

    keySpace1.click(() => bigStopwatchTurnOn());
    keySpace2.click(() => bigStopwatchTurnOff());
    endBtn.click(() => endOfFight());

    $("#return").click(function(){
        if (confirm("¿Desea abandonar la página? Se perderán los valores actuales."))
            $(location).attr("href", "/myHome");
    })
});