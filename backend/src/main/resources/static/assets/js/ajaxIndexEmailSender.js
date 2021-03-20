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
        method: 'get',
        beforeSend: (ans) => {
            $("#loadedAJAXItem").remove();
            $(this).prepend('<div class="alert alert-info" id="loadingAJAXItem"><i class="fas fa-circle-notch fa-spin"></i> Enviando...</div>');
        },
        success: (ans) => {
            $("#loadingAJAXItem").remove();
            if (ans)
                $(this).prepend('<div class="alert alert-success" id="loadedAJAXItem">Correo enviado correctamente.</div>');
            else
                $(this).prepend('<div class="alert alert-danger" id="loadedAJAXItem">No se pudo enviar. Intentelo de nuevo.</div>');
        }
    })
})