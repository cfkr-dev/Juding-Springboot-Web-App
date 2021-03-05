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

    /**
     * Function for showing dan selector when it's necessary
     */
    if ($("#beltSelector").length) {
        $("#beltSelector").change(function () {
            $(this).siblings("#danField").css("display", $(this).val() === "N" ? "inherit" : "none");
            console.log($(this).val());
        });
    }
});