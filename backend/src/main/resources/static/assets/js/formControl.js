$(function () {
    /**
     * Function for showing datepicker in registration pages
     */
    if ($(".dateInput").length) {
        $.datetimepicker.setLocale('es');
        $('.dateInput').each(function () {
            if ($(this).data("time") === true) {
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
     * CONTROLLING SUBMISSION
     */

    $(".juding-form-competition-update").on("submit", function (evt) {
        evt.preventDefault();
        if ($(this)[0].checkValidity()) {
            $.ajax({
                data: {
                    "startDate": $("#startDate").val(),
                    "endDate": $("#endDate").val(),
                    "minWeight": $("#minWeight").val(),
                    "maxWeight": $("#maxWeight").val()
                },
                url: "/api/formValidation/competitions/alteration",
                method: "get"
            }).done((ans) => {
                if (ans === 3) {
                    $(this).unbind().submit();
                } else {
                    evt.preventDefault();
                    evt.stopPropagation();
                    if (ans === 0) {
                        $("#dateFeedback").html("<div class=\"alert alert-danger\">La fecha del inicio de la competición debe ser anterior a la fecha del final de la competición</div>");
                        $("#weightFeedback").html("<div class=\"alert alert-danger\">El peso mínimo debe ser menor que el peso máximo de la competición</div>");
                    } else if (ans === 1) {
                        $("#dateFeedback").html("");
                        $("#weightFeedback").html("<div class=\"alert alert-danger\">El peso mínimo debe ser menor que el peso máximo de la competición</div>");
                    } else if (ans === 2) {
                        $("#dateFeedback").html("<div class=\"alert alert-danger\">La fecha del inicio de la competición debe ser anterior a la fecha del final de la competición</div>");
                        $("#weightFeedback").html("");
                    }
                }
                $(this).addClass('was-validated');
            });
        } else {
            evt.preventDefault();
            evt.stopPropagation();
            $("#dateFeedback").html("");
            $("#weightFeedback").html("");
        }
        $(this).addClass('was-validated');
    });

    $(".juding-form-competition-new").on("submit", function (evt) {
        evt.preventDefault();
        if ($(this)[0].checkValidity()) {
            $.ajax({
                data: {
                    "startDate": $("#startDate").val(),
                    "endDate": $("#endDate").val(),
                    "minWeight": $("#minWeight").val(),
                    "maxWeight": $("#maxWeight").val()
                },
                url: "/api/formValidation/competitions/new",
                method: "get"
            }).done((ans) => {
                if (ans === 3) {
                    $(this).unbind().submit();
                } else {
                    evt.preventDefault();
                    evt.stopPropagation();
                    if (ans === 0) {
                        $("#dateFeedback").html("<div class=\"alert alert-danger\">La fecha del inicio de la competición debe ser anterior a la fecha del final de la competición, además de ser posterior a la fecha actual</div>");
                        $("#weightFeedback").html("<div class=\"alert alert-danger\">El peso mínimo debe ser menor que el peso máximo de la competición</div>");
                    } else if (ans === 1) {
                        $("#dateFeedback").html("");
                        $("#weightFeedback").html("<div class=\"alert alert-danger\">El peso mínimo debe ser menor que el peso máximo de la competición</div>");
                    } else if (ans === 2) {
                        $("#dateFeedback").html("<div class=\"alert alert-danger\">La fecha del inicio de la competición debe ser anterior a la fecha del final de la competición, además de ser posterior a la fecha actual</div>");
                        $("#weightFeedback").html("");
                    }
                }
                $(this).addClass('was-validated');
            });
        } else {
            evt.preventDefault();
            evt.stopPropagation();
            $("#dateFeedback").html("");
            $("#weightFeedback").html("");
        }
        $(this).addClass('was-validated');
    });

    $(".juding-form-user-update").on("submit", function (evt) {
        evt.preventDefault();
        if ($(this)[0].checkValidity()) {
            $.ajax({
                data: {
                    "licenseId": $("#licenseId").val(),
                    "nickname": $("#nickname").val()
                },
                url: "/api/formValidation/users/alteration",
                method: "get"
            }).done((ans) => {
                if (ans) {
                    evt.preventDefault();
                    evt.stopPropagation();
                    $("#nicknameFeedback").html("<div class=\"alert alert-danger\">Este apodo ya está registrado.</div>");
                } else {
                    $(this).unbind().submit();
                }
                $(this).addClass('was-validated');
            });
        } else {
            evt.preventDefault();
            evt.stopPropagation();
            $("#nicknameFeedback").html("");
        }
        $(this).addClass('was-validated');
    });

    $(".juding-form-signup").on("submit", function (evt) {
        evt.preventDefault();
        if ($(this)[0].checkValidity()) {
            $.ajax({
                data: {
                    "licenseId": $("#licenseId").val(),
                    "nickname": $("#nickname").val()
                },
                url: "/api/formValidation/users/new",
                method: "get"
            }).done((ans) => {
                if (ans === 3) {
                    $(this).unbind().submit();
                } else {
                    evt.preventDefault();
                    evt.stopPropagation();
                    if (ans === 0) {
                        $("#licenseIdFeedback").html("<div class=\"alert alert-danger\">Este número de licencia ya está registrado.</div>");
                        $("#nicknameIdFeedback").html("<div class=\"alert alert-danger\">Este apodo ya está registrado.</div>");
                    } else if (ans === 1) {
                        $("#licenseIdFeedback").html("<div class=\"alert alert-danger\">Este número de licencia ya está registrado.</div>");
                        $("#nicknameIdFeedback").html("");
                    } else if (ans === 2) {
                        $("#licenseIdFeedback").html("");
                        $("#nicknameIdFeedback").html("<div class=\"alert alert-danger\">Este apodo ya está registrado.</div>");
                    }
                }
                $(this).addClass('was-validated');
            });
        } else {
            evt.preventDefault();
            evt.stopPropagation();
            $("#licenseIdFeedback").html("");
            $("#nicknameIdFeedback").html("");
        }
        $(this).addClass('was-validated');
    });


});