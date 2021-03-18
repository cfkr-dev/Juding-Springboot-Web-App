$("#modalProfile").on("show.bs.modal", function (evt) {

    $("#modalProfile #modalOpenedTitle").html($(evt.relatedTarget).parent().siblings(".nameModal").html());
    $("#modalProfile #modalOpenedBody").html($(evt.relatedTarget).parent().siblings(".viewMore").html());
});

$("#modalDelete").on("show.bs.modal", function (evt) {
    $("#modalDeleteLink").attr("href", "/admin/" + $(evt.relatedTarget).data("type") + "/delete/" + $(evt.relatedTarget).data("id"));
});

$(function(){
    tinymce.init({
        selector: 'textarea',
        language: 'es',
        plugins: ['fullscreen', 'lists', 'link', 'table'],
        toolbar: 'undo redo | styleselect | bold italic underline fontsizeselect | alignleft aligncenter alignright alignjustify | bullist numlist | link unlink | table | fullscreen'
    });
})
