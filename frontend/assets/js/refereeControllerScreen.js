$(function () {
    // Simulator is initialized
    // Needed variables:
    let points1 = 0;
    let points2 = 0;
    let penalties1 = 0;
    let penalties2 = 0;
    let bigCrono = [0, 0, 0];
    let smallCrono = [0, 0, 0];

    // All buttons are triggered by their ID and by their keystroke assigned
    let fightSel = $("#fight");
    let keyG = $("#keyG");
    let keyQ = $("#keyQ");
    let keyW = $("#keyW");
    let keyE = $("#keyE");
    let keyS = $("#keyS");
    let keyD = $("#keyD");
    let keyV = $("#keyV");
    let keyB = $("#keyB");
    let keySpace1 = $("#keySpace1");
    let keySpace2 = $("#keySpace2");
    let endBtn = $("#endBtn");
    let keyP = $("#keyP");
    let keyI = $("#keyI");
    let keyO = $("#keyO");
    let keyK = $("#keyK");
    let keyL = $("#keyL");
    let points1Screen = $("#points1");
    let points2Screen = $("#points2");
    let penalties1Screen = $("#penalties1");
    let penalties2Screen = $("#penalties2");

    let screenFullFunc = false;

    /**
     * Button for starting the simulator should be only available when a fight is selected
     */
    fightSel.change(function () {
        if ($(this).val() !== "-1") {
            console.log("Value was selected");
            keyG.removeAttr("disabled");
            $(this).attr("disabled", "");
            $(this).blur();
        }
    });

    /**
     * Simulator is fully available only when keyG is pressed
     */
    function keyGAction() {
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
        keyG.prop("disabled", "");
        screenFullFunc = true;
    }

    keyG.click(keyGAction);

    /**
     * Adding a point to fighter 1
     */
    function keyWPress() {
        points1++;
        points1Screen.html(points1);
    }

    /**
     * Subtracting a point to fighter 1
     */
    function keyEPress() {
        points1 = (points1 - 1 >= 0) ? points1 - 1 : 0;
        points1Screen.html(points1);
    }

    /**
     * Adding a penalty to fighter 1
     */
    function keySPress() {
        penalties1++;
        penalties1Screen.html(penalties1);
    }

    /**
     * Subtracting a penalty to fighter 1
     */
    function keyDPress() {
        penalties1 = (penalties1 - 1 >= 0) ? penalties1 - 1 : 0;
        penalties1Screen.html(penalties1);
    }

    /**
     * Adding a point to fighter 2
     */
    function keyIPress() {
        points2++;
        points2Screen.html(points2);
    }

    /**
     * Subtracting a point to fighter 2
     */
    function keyOPress() {
        points2 = (points2 - 1 >= 0) ? points2 - 1 : 0;
        points2Screen.html(points2);
    }

    /**
     * Adding a penalty to fighter 2
     */
    function keyKPress() {
        penalties2++;
        penalties2Screen.html(penalties2);
    }

    /**
     * Subtracting a penalty to fighter 2
     */
    function keyLPress() {
        penalties2 = (penalties2 - 1 >= 0) ? penalties2 - 1 : 0;
        penalties2Screen.html(penalties2);
    }


    $(document).keypress(function (evt) {
        switch (evt.key) {
            case 'w':
            case 'W':
                if (screenFullFunc) keyWPress();
                break;
            case 'e':
            case 'E':
                if (screenFullFunc) keyEPress();
                break;
            case 's':
            case 'S':
                if (screenFullFunc) keySPress();
                break;
            case 'd':
            case 'D':
                if (screenFullFunc) keyDPress();
                break;
            case 'i':
            case 'I':
                if (screenFullFunc) keyIPress();
                break;
            case 'o':
            case 'O':
                if (screenFullFunc) keyOPress();
                break;
            case 'k':
            case 'K':
                if (screenFullFunc) keyKPress();
                break;
            case 'l':
            case 'L':
                if (screenFullFunc) keyLPress();
                break;
            case 'g':
            case 'G':
                keyGAction();
                break;
        }
    });
    keyW.click(() => keyWPress());
    keyE.click(() => keyEPress());
    keyS.click(() => keySPress());
    keyD.click(() => keyDPress());
    keyI.click(() => keyIPress());
    keyO.click(() => keyOPress());
    keyK.click(() => keyKPress());
    keyL.click(() => keyLPress());
});