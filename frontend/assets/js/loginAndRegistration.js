function showPassword(){
    var cambio = document.getElementById("txtPassword");
    if(cambio.type === "password"){
        cambio.type = "text";
        $('i').removeClass('fa fa-eye-slash').addClass('fa fa-eye');
    }else{
        cambio.type = "password";
        $('i').removeClass('fa fa-eye').addClass('fa fa-eye-slash');
    }
}

$(document).ready(function () {
    //CheckBox mostrar contraseña
    $('#ShowPassword').click(function () {
        $('#Password').attr('type', $(this).is(':checked') ? 'text' : 'password');
    });
});

$("#beltSelector").change(function(){
    if ($(this).val() === "N"){
        console.log("N");
        $(this).siblings("#danField").css("display", "inherit");
    } else {
        console.log("No N");
        $(this).siblings("#danField").css("display", "none");
    }
})