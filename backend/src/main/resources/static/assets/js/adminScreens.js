$(function () {
    /**
     * LIBRARIES setup when loading a document:
     * - TinyMCE: WYSIWYG text editor for textareas.
     */
    tinymce.init({
        selector: 'textarea',
        language: 'es',
        plugins: ['fullscreen', 'lists', 'link', 'table'],
        toolbar: 'undo redo | styleselect | bold italic underline fontsizeselect | alignleft aligncenter alignright alignjustify | bullist numlist | link unlink | table | fullscreen'
    });

    /**
     * LISTENER: charging dynamic information of profile modal when opened for a specific user.
     */
    $("#modalProfile").on("show.bs.modal", function (evt) {
        $("#modalProfile #modalOpenedTitle").html($(evt.relatedTarget).parent().siblings(".nameModal").html());
        $("#modalProfile #modalOpenedBody").html($(evt.relatedTarget).parent().siblings(".viewMore").html());
    });

    /**
     * LISTENER: charging dynamically the ID of the element for confirmating deletion of a element.
     */
    $("#modalDelete").on("show.bs.modal", function (evt) {
        $("#modalDeleteLink").attr("href", "/admin/" + $(evt.relatedTarget).data("type") + "/delete/" + $(evt.relatedTarget).data("id"));
    });

    /**
     * LISTENER: adding a new page of information
     */
    var numPage = 1;
    $("#loadNextPage").on("click", function () {
        $.ajax({
            url: "/admin/" + $(this).data("role") + "/list/" + $(this).data("numpage"),
            method: 'get',
            beforeSend: () => {
                $(this).children("i").addClass("fas fa-circle-notch fa-spin");
            }
        }).done((ans) => {
            $("#table").append(ans);
            $(this).blur();
            $(this).children("i").removeClass("fas fa-circle-notch fa-spin");
            $(this).data("numpage", $(this).data("numpage") + 1);
            if ($(this).data("numpage") === $(this).data("totalpages")) {
                $(this).prop("disabled", true);
                $(this).toggleClass("btn-corporative btn-outline-corporative");
                $(this).html("No hay más ítems");
                $(this).off("click");
            }
        });
    });
});