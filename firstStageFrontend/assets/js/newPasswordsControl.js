/**
 * Function for showing password
 */
if ($("#showPassword1").length) {
    $("#showPassword1").click(function () {
        let passwordField = $("#password1");
        if (passwordField.attr("type") === "password") {
            $("span>span>i").removeClass('fa-eye-slash').addClass('fa-eye');
            passwordField.attr("type", "text");
        } else {
            $("span>span>i").removeClass('fa-eye').addClass('fa-eye-slash');
            passwordField.attr("type", "password");
        }
    });
}
if ($("#showPassword2").length) {
    $("#showPassword2").click(function () {
        let passwordField = $("#password2");
        if (passwordField.attr("type") === "password") {
            $('div>span>i').removeClass('fa-eye-slash').addClass('fa-eye');
            passwordField.attr("type", "text");
        } else {
            $('div>span>i').removeClass('fa-eye').addClass('fa-eye-slash');
            passwordField.attr("type", "password");
        }
    });
}