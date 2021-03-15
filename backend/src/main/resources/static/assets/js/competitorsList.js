$("#modalProfile").on("show.bs.modal", function (evt) {
    console.log(evt.relatedTarget);
    $("#modalProfile #modalOpenedTitle").html($(evt.relatedTarget).parent().siblings(".nameModal").html());
    $("#modalProfile #modalOpenedBody").html($(evt.relatedTarget).parent().siblings(".viewMore").html());
});