$(function() {
    $('#loginButton').click(function() {
        $.ajax({
            type: 'post',
            url: '/login',
            dataType: 'application/json',
            data: $('#parametersArea').val()
        }).done(function(result) {
            $('#responseBodyArea').val(result.body);
            $('#responseMetaArea').val(result.meta);
        });
    });
});
