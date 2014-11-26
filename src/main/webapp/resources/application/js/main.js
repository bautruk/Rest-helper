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

    $('#loginBtn').click(function() {
        sendUrl = "/login";
        fillPredefinedInfo('/login/predefined');
    });

    $('#explorerFolderDataBtn').click(function() {
        sendUrl = "/explorerFolderData";
        fillPredefinedInfo('/explorerFolderData/predefined');
    });

    $('#getPasswordBtn').click(function() {
        sendUrl = "/getPassword";
        fillPredefinedInfo('/getPassword/predefined');
    });

    $('#forwardUrlBtn').click(function() {
        sendUrl = "/forwardUrl";
        fillPredefinedInfo('/forwardUrl/predefined');
    });

    $('#exchangeLoginBtn').click(function() {
        sendUrl = "/exchangeLogin";
        fillPredefinedInfo('/exchangeLogin/predefined');
    });

    $('#exchangeFoldersBtn').click(function() {
        sendUrl = "/exchangeFolders";
        fillPredefinedInfo('/exchangeFolders/predefined');
    });

    $('#exchangeFolderDataBtn').click(function() {
        sendUrl = "/exchangeFolderData";
        fillPredefinedInfo('/exchangeFolderData/predefined');
    });

    $('#exchangeContactsDataBtn').click(function() {
        sendUrl = "/exchangeContactsData";
        fillPredefinedInfo('/exchangeContactsData/predefined');
    });

    $('#sendButton').click(function() {
        $.ajax({
            type: 'post',
            url: sendUrl,
            dataType: 'json',
            data: JSON.stringify({
                'parameters': $('#parametersArea').val(),
                'headers': $('#headersArea').val(),
                'type': $('input[name=contentType]:checked').val()
            }),
            contentType: 'application/json'
        }).done(function(result) {
            $('#responseBodyArea').val(result.body.split('\\n').join('\n'));
            $('#responseMetaArea').val(result.meta.split('\\n').join('\n'));
        });
    });
});
