$("#modalProfile").on("show.bs.modal", function (evt) {

    $("#modalProfile #modalOpenedTitle").html($(evt.relatedTarget).parent().siblings(".nameModal").html());
    $("#modalProfile #modalOpenedBody").html($(evt.relatedTarget).parent().siblings(".viewMore").html());
});

$("#modalDelete").on("show.bs.modal", function(evt){
    $("#modalDeleteLink").attr("href", "/admin/user/delete/" + $(evt.relatedTarget).data("id"));
});