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

let forbiddenLicenseId;
let forbiddenDni;
let forbiddenNickname;
let forbiddenMaxWeight;
let forbiddenEndDate;

$("#licenseId").on("blur", function () {
    if ($("#licenseId")[0].checkValidity()) {
        $.ajax({
            data: {"licenseId": $("#licenseId").val()},
            url: "/formCheck/signUp/licenseId",
            method: 'post'
        }).done(function (ans) {
            $("#licenseIdBanner").remove();
            if (ans) {
                $("#licenseId").after('<div class="alert alert-success mt-1" id="licenseIdBanner">Este número de licencia no está registrado</div>');
                forbiddenLicenseId = false;
            } else {
                $("#licenseId").after('<div class="alert alert-danger mt-1" id="licenseIdBanner">Este número de licencia ya está en uso</div>');
                forbiddenLicenseId = true;
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
            method: 'post'
        }).done(function (ans) {
            $("#nicknameBanner").remove();
            if (ans) {
                $("#nickname").after('<div class="alert alert-success mt-1" id="nicknameBanner">Este apodo no está registrado</div>');
                forbiddenNickname = false;
            } else {
                $("#nickname").after('<div class="alert alert-danger mt-1" id="nicknameBanner">Este apodo ya está en uso</div>');
                forbiddenNickname = true;
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
            method: 'post'
        }).done(function (ans) {
            $("#dniBanner").remove();
            if (ans) {
                $("#dni").after('<div class="alert alert-success mt-1" id="dniBanner">Este DNI no está registrado para este rol</div>');
                forbiddenDni = false;
            } else {
                $("#dni").after('<div class="alert alert-danger mt-1" id="dniBanner">Este DNI ya está en uso para este rol</div>');
                forbiddenDni = true;
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
        } else {
            forbiddenMaxWeight = false;
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
        } else {
            forbiddenEndDate = false;
        }
    } else{
        $("#endDateBanner").remove();
    }
})

$(".juding-form").on("submit", function(evt){
    evt.preventDefault();
    console.log(forbiddenDni || forbiddenNickname || forbiddenLicenseId || forbiddenMaxWeight || forbiddenEndDate)
    if (forbiddenDni || forbiddenNickname || forbiddenLicenseId || forbiddenMaxWeight || forbiddenEndDate)
        $(this).off("submit");
});