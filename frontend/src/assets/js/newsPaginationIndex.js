/**
 * LISTENER: adding a new page of information
 */
var numPage = 1;
$("#loadNextPage").on("click", function () {
    $.ajax({
        url: "/news/page/" + $(this).data("numpage"),
        method: 'get',
        beforeSend: () => {
            $(this).children("i").addClass("fas fa-circle-notch fa-spin");
        }
    }).done((ans) => {
        $(this).before(ans);
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