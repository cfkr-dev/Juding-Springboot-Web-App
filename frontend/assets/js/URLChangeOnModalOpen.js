$("#modalPost").on("show.bs.modal", function(evt){
    let cnt = false;
    for (let item of window.location.search.split('?')) {
        if (item.substring(0, 6) === "postId") {
            cnt = true;
        }
    }
    if (evt.relatedTarget === undefined){
        if (cnt)
            console.log(window.location.href.substring(window.location.href.indexOf("=")) + $(evt.relatedTarget).siblings(".post-info").data("id"));
        else
            window.history.pushState({}, "", window.location + "?postId=" + $(evt.relatedTarget).siblings(".post-info").data("id"));
    }
});

$(function(){
    for (let item of window.location.search.split('?')){
        if (item.substring(0, 6) === "postId"){
            let variable = item.substring(7);
            if (variable >= 1){
                let modal = new bootstrap.Modal(document.getElementById('modalPost'));
                let postInfoSelector = $(".post-info[data-id="+variable+"]");
                $("#modalPost #modalOpenedTitle").html(postInfoSelector.siblings(".post-title").html());
                $("#modalPost #modalOpenedBody").html(postInfoSelector.html());
                modal.show();
            }
        }
    }
});