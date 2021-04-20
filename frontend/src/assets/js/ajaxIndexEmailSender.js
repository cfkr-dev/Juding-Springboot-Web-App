$("#contact-form").on("submit", function (evt) {
    evt.preventDefault();
    $.ajax({
        data: {
            "name": $("#contact-form-name").val(),
            "email": $("#contact-form-email").val(),
            "subject": $("#contact-form-subject").val(),
            "message": $("#contact-form-message").val()
        },
        url: '/api/index-email',
        method: 'get',
        beforeSend: () => {
            $("#loadedAJAXItem").remove();
            $(this).prepend('<div class="alert alert-info" id="loadingAJAXItem"><i class="fas fa-circle-notch fa-spin"></i> Enviando...</div>');
        }
    }).always((ans, textStatus, jqXHR) => {
        $("#loadingAJAXItem").remove();
        if (jqXHR.status === 200 || ans.status === 200)
            $(this).prepend('<div class="alert alert-success" id="loadedAJAXItem">Correo enviado correctamente.</div>');
        else if (jqXHR.status === 400 || ans.status === 400)
            $(this).prepend('<div class="alert alert-danger" id="loadedAJAXItem">No se pudo enviar. Intentelo de nuevo.</div>');
        else
            $(this).prepend('<div class="alert alert-danger" id="loadedAJAXItem">Ha ocurrido un error inesperado.</div>');
    });
})