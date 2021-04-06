function licenseIdControl() {
    if ($("#licenseId")[0].checkValidity()) {
        $.ajax({
            data: {"licenseId": $("#licenseId").val()},
            url: "/formCheck/licenseId",
            method: 'get'
        }).done(function (ans) {
            $("#licenseIdBanner").remove();
            if (ans) {
                $("#licenseId").after('<div class="alert alert-success mt-1" id="licenseIdBanner">Este número de licencia se puede utilizar</div>');
                return true;
            } else {
                $("#licenseId").after('<div class="alert alert-danger mt-1" id="licenseIdBanner">Este número de licencia no se puede utilizar</div>');
            }
        });
    } else {
        $("#licenseIdBanner").remove();
    }
    return false;
}

function nicknameControl() {
    if ($("#nickname")[0].checkValidity()) {
        $.ajax({
            data: {"nickname": $("#nickname").val()},
            url: "/formCheck/nickname",
            method: 'get'
        }).done(function (ans) {
            $("#nicknameBanner").remove();
            if (ans) {
                $("#nickname").after('<div class="alert alert-success mt-1" id="nicknameBanner">Este apodo se puede utilizar</div>');
                return true;
            } else {
                $("#nickname").after('<div class="alert alert-danger mt-1" id="nicknameBanner">Este apodo no se puede utilizar</div>');
            }
        });
    } else {
        $("#nicknameBanner").remove();
    }
    return false;
}

function dniControl() {
    if ($("#dni")[0].checkValidity()) {
        $.ajax({
            data: {
                "dni": $("#dni").val(),
                "role": $("#dni").data("role")
            },
            url: "/formCheck/dni",
            method: 'get'
        }).done(function (ans) {
            $("#dniBanner").remove();
            if (ans) {
                $("#dni").after('<div class="alert alert-success mt-1" id="dniBanner">Este DNI se puede utilizar para este rol</div>');
                return true;
            } else {
                $("#dni").after('<div class="alert alert-danger mt-1" id="dniBanner">Este DNI no se puede utilizar para este rol</div>');
            }
        });
    } else {
        $("#dniBanner").remove();
    }
}

function weightControl() {
    if ($("#maxWeight").length > 0) {
        if ($("#maxWeight")[0].checkValidity()) {
            $("#maxWeightBanner").remove();
            if ($("#maxWeight").val() <= $("#maxWeight").parent().siblings(".minWeight").children("#minWeight").val()) {
                $("#maxWeight").after('<div class="alert alert-danger mt-1" id="maxWeightBanner">No se puede introducir un peso máximo menor que el peso mínimo</div>');
            } else {
                return true;
            }
        } else {
            $("#maxWeightBanner").remove();
        }
    }
    return false;
}

function dateControl() {
    if ($("#endDate").length > 0) {
        if ($("#endDate")[0].checkValidity()) {
            $("#endDateBanner").remove();
            if ($("#endDate").val() <= $("#endDate").parent().siblings(".startDate").children("#startDate").val()) {
                $("#endDate").after('<div class="alert alert-danger mt-1" id="endDateBanner">No se puede introducir una fecha de fin anterior a la fecha de inicio</div>');
            } else {
                return true;
            }
        } else {
            $("#endDateBanner").remove();
        }
    }
    return false;
}
$(function () {
    /**
     * Function for showing datepicker in registration pages
     */
    if ($(".dateInput").length) {
        $.datetimepicker.setLocale('es');
        $('.dateInput').each(function(){
            if ($(this).data("time") === true){
                $('.dateInput').datetimepicker({
                    timepicker: true,
                    format: 'd/m/Y H:i',
                    dayOfWeekStart: 1
                });
            } else {
                $('.dateInput').datetimepicker({
                    timepicker: false,
                    format: 'd/m/Y',
                    maxDate: 0,
                    dayOfWeekStart: 1
                });
            }
        })
    }

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

    // $("#licenseId").focusout(licenseIdControl());
    // $("#nickname").focusout(nicknameControl());
    // $("#dni").focusout(dniControl());
    // $("#maxWeight").focusout(weightControl());
    // $("#endDate").focusout(dateControl());

    /* $(".juding-form-user").on("submit", function () {
        if (!licenseIdControl() && !nicknameControl() && !dniControl())
            if ($(this)[0].checkValidity()) {
                $(this)[0].off("submit").submit();
            }
    });

    $(".juding-form-competition").on("submit", function () {
        if (weightControl() && dateControl())
            if ($(this)[0].checkValidity()) {
                $(this)[0].off("submit").submit();
            }
    }); */
});