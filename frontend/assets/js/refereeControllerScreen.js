$(function(){
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

    /**
     * Button for starting the simulator should be only available when a fight is selected
     */
    fightSel.change(function(){
        if ($(this).val() !== "-1"){
            console.log("Value was selected");
            keyG.removeAttr("disabled");
            $(this).attr("disabled", "");
        }
    });

    /**
     * Simulator is fully available only when keyG is pressed
     */
    keyG.click(function(){
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
        $(this).attr("disabled", "");
    });

    /**
     * Adding a point for player 1
     */
    function keyWFunc(){
        points1++;
        points1Screen.html(points1);
    };
});