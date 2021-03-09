$("#contact-form").on("submit", function (evt) {
    evt.preventDefault();
    $.ajax({
        data: {
            "name": $("#contact-form-name").val(),
            "email": $("#contact-form-email").val(),
            "subject": $("#contact-form-subject").val(),
            "message": $("#contact-form-message").val()
        },
        url: '/index-email',
        method: 'post',
        beforeSend: (ans) => {
            $(this).prepend('<i class="fas fa-circle-notch fa-spin" id="loading"></i>')
        },
        success: (ans) => {
            $("#loading").remove();
            if (ans)
                $(this).prepend('<div class="alert alert-success">Correo enviado correctamente.</div>');
            else
                $(this).prepend('<div class="alert alert-danger">No se pudo enviar. Intentelo de nuevo.</div>');
        }
    })
})