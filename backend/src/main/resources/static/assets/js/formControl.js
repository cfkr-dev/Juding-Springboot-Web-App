$(function () {
    /**
     * Function for showing datepicker in registration pages
     */
    if ($("#birthDate").length) {
        $("#birthDate").datepicker({
            clearBtn: true,
            format: "dd/mm/yyyy",
            language: "es",
            todayHighlight: true,
            weekStart: 1
        });
    }
});

/**
 * Function for showing password
 */
if ($("#showPassword").length) {
    $('#showPassword').click(function () {
        let passwordField = $("#password");
        if (passwordField.attr("type") === "password") {
            $("#showPassword i, #showPassword + i").removeClass('fa-eye-slash').addClass('fa-eye');
            passwordField.attr("type", "text");
        } else {
            $("#showPassword i, #showPassword + i").removeClass('fa-eye').addClass('fa-eye-slash');
            passwordField.attr("type", "password");
        }
    });
}


/*
 * CONTROLLING SUBMISSION WITH FORBIDDEN VALUES
 */

let forbiddenLicenseId = false;
let forbiddenDni = false;
let forbiddenNickname = false;
let forbiddenMaxWeight = false;
let forbiddenEndDate = false;
let somethingChanged = true;

$("#licenseId").on("blur", function () {
    if ($("#licenseId")[0].checkValidity()) {
        $.ajax({
            data: {"licenseId": $("#licenseId").val()},
            url: "/formCheck/signUp/licenseId",
            method: 'get'
        }).done(function (ans) {
            $("#licenseIdBanner").remove();
            if (ans) {
                $("#licenseId").after('<div class="alert alert-success mt-1" id="licenseIdBanner">Este número de licencia se puede utilizar</div>');
                forbiddenLicenseId = false;
                somethingChanged = true;
            } else {
                $("#licenseId").after('<div class="alert alert-danger mt-1" id="licenseIdBanner">Este número de licencia no se puede utilizar</div>');
                forbiddenLicenseId = true;
                somethingChanged = false;
            }
        });
    } else {
        $("#licenseIdBanner").remove();
    }
})

$("#nickname").on("blur", function () {
    if ($("#nickname")[0].checkValidity()) {
        $.ajax({
            data: {"nickname": $("#nickname").val()},
            url: "/formCheck/signUp/nickname",
            method: 'get'
        }).done(function (ans) {
            $("#nicknameBanner").remove();
            if (ans) {
                $("#nickname").after('<div class="alert alert-success mt-1" id="nicknameBanner">Este apodo se puede utilizar</div>');
                forbiddenNickname = false;
                somethingChanged = true;
            } else {
                $("#nickname").after('<div class="alert alert-danger mt-1" id="nicknameBanner">Este apodo no se puede utilizar</div>');
                forbiddenNickname = true;
                somethingChanged = false;
            }
        });
    } else {
        $("#nicknameBanner").remove();
    }
})

$("#dni").on("blur", function () {
    if ($("#dni")[0].checkValidity()) {
        $.ajax({
            data: {
                "dni": $("#dni").val(),
                "role": $("#dni").data("role")
            },
            url: "/formCheck/signUp/dni",
            method: 'get'
        }).done(function (ans) {
            $("#dniBanner").remove();
            if (ans) {
                $("#dni").after('<div class="alert alert-success mt-1" id="dniBanner">Este DNI se puede utilizar para este rol</div>');
                forbiddenDni = false;
                somethingChanged = true;
            } else {
                $("#dni").after('<div class="alert alert-danger mt-1" id="dniBanner">Este DNI no se puede utilizar para este rol</div>');
                forbiddenDni = true;
                somethingChanged = false;
            }
        });
    } else {
        $("#dniBanner").remove();
    }
});

$("#maxWeight").on("blur", function (){
    console.log($("#maxWeight").parent().siblings(".minWeight").children("#minWeight").val());
    if ($("#maxWeight")[0].checkValidity()) {
        $("#maxWeightBanner").remove();
        if ($("#maxWeight").val() <= $("#maxWeight").parent().siblings(".minWeight").children("#minWeight").val()) {
            $("#maxWeight").after('<div class="alert alert-danger mt-1" id="maxWeightBanner">No se puede introducir un peso máximo menor que el peso mínimo</div>');
            forbiddenMaxWeight = true;
            somethingChanged = true;
        } else {
            forbiddenMaxWeight = false;
            somethingChanged = false;
        }
    } else{
        $("#maxWeightBanner").remove();
    }
})

$("#endDate").on("blur", function (){
    if ($("#endDate")[0].checkValidity()) {
        $("#endDateBanner").remove();
        if ($("#endDate").val() <= $("#endDate").parent().siblings(".startDate").children("#startDate").val()) {
            $("#endDate").after('<div class="alert alert-danger mt-1" id="endDateBanner">No se puede introducir una fecha de fin anterior a la fecha de inicio</div>');
            forbiddenEndDate = true;
            somethingChanged = true;
        } else {
            forbiddenEndDate = false;
            somethingChanged = false;
        }
    } else{
        $("#endDateBanner").remove();
    }
})

// $(".juding-form-user").on("submit", function(evt){
//     evt.preventDefault();
//     if (somethingChanged || forbiddenDni || forbiddenNickname || forbiddenLicenseId)
//         $(this).off("submit");
// });
//
// $(".juding-form-competition").on("submit", function(evt){
//     evt.preventDefault();
//     if (somethingChanged || forbiddenMaxWeight || forbiddenEndDate)
//         $(this).off("submit");
// });