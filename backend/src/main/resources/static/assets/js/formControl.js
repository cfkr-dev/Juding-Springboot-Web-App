$(document).ready(function () {
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


    /**
     * Function for showing datepicker in registration pages
     */
    if ($("#birthdate").length) {
        $("#birthdate").datepicker({
            clearBtn: true,
            format: "dd/mm/yyyy",
            language: "es",
            todayHighlight: true,
            weekStart: 1
        });
    }
});