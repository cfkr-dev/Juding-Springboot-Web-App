$("#modalProfile").on("show.bs.modal", function (evt) {
    console.log(evt.relatedTarget);
    $("#modalProfile #modalOpenedTitle").html($(evt.relatedTarget).parent().siblings(".nameModal").html());
    $("#modalProfile #modalOpenedBody").html($(evt.relatedTarget).parent().siblings(".viewMore").html());
});
$(document).ready(function () {
    $(".rtRow:even").css("background-color", "rgba(255, 204, 204, 0.5)"); // filas impares
});