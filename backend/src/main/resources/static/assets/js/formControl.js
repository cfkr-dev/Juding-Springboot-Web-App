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