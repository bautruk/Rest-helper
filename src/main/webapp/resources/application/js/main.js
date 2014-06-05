$(function() {
    var sendUrl = "";

    $.ajaxSetup({
        beforeSend:function(){
            $("#loading").show();
        },
        complete:function(){
            $("#loading").hide();
        }
    });

    var clearResponseAreas = function() {
        $('#responseMetaArea').val('');
        $('#responseBodyArea').val('');
    };

    var fillPredefinedInfo = function(url) {
        $.ajax({
            type: 'get',
            url: url,
            dataType: 'json'
        }).done(function(result) {
            clearResponseAreas();

            $('#parametersArea').val(result.requestParams.split('\\n').join('\n'));
            $('#headersArea').val(result.requestHeaders.split('\\n').join('\n'));
        });
    };

    $('#loginButton').click(function() {
        sendUrl = "/login";
        fillPredefinedInfo('/login/predefined');
    });

    $('#forwardUrlButton').click(function() {
        sendUrl = "/forwardUrl";
        fillPredefinedInfo('/forwardUrl/predefined');
    });

    $('#exchangeButton').click(function() {
        sendUrl = "/exchange";
        fillPredefinedInfo('/exchange/predefined');
    });

    $('#foldersButton').click(function() {
        sendUrl = "/folders";
        fillPredefinedInfo('/folders/predefined');
    });

    $('#sendButton').click(function() {
        $.ajax({
            type: 'post',
            url: sendUrl,
            dataType: 'json',
            data: JSON.stringify({
                'parameters': $('#parametersArea').val(),
                'headers': $('#headersArea').val()
            }),
            contentType: 'application/json'
        }).done(function(result) {
            $('#responseBodyArea').val(result.body.split('\\n').join('\n'));
            $('#responseMetaArea').val(result.meta.split('\\n').join('\n'));
        });
    });
});
