/*(function () {
    $("#modalPost").on("show.bs.modal", function () {
        var modalId = $(this).parent(".post-box").dataset.id
        console.log(modalId)
        const nextURL = './frontend/index/post-'+modalId+'.html';
        const nextTitle = 'Post '+modalId;
        const nextState = { additionalInformation: 'Updated the URL' };
        window.history.pushState(nextState, nextTitle, nextURL);
        window.history.replaceState(nextState, nextTitle, nextURL);
    })
})();*/
