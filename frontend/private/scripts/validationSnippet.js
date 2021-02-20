$(function(){
    // EXAMPLE OF SNIPPET for password validation
    // It fails when password is shorter than 8 chars and does not contain any number or a uppercase letter
    // TRIGGER DILEMMA: when out of field or when sending form?
    // This option reports mistakes when out of field
    $("input[name=pass]").blur(() => {
        let valor = $(this).val();
        console.log(valor);
    });
})