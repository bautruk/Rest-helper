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
        $("#contentType").val("json");
        sendUrl = "/login";
        fillPredefinedInfo('/login/predefined');
    });

    $('#explorerFolderDataBtn').click(function() {
        $("#contentType").val("json");
        sendUrl = "/explorerFolderData";
        fillPredefinedInfo('/explorerFolderData/predefined');
    });

    $('#explorerGetFileBtn').click(function() {
        $("#contentType").val("json");
        sendUrl = "/explorerGetFile";
        fillPredefinedInfo('/explorerGetFile/predefined');
    });


    $('#getPasswordBtn').click(function() {
        $("#contentType").val("json");
        sendUrl = "/getPassword";
        fillPredefinedInfo('/getPassword/predefined');
    });

    $('#forwardUrlBtn').click(function() {
        $("#contentType").val("json");
        sendUrl = "/forwardUrl";
        fillPredefinedInfo('/forwardUrl/predefined');
    });

    $('#exchangeLoginBtn').click(function() {
        $("#contentType").val("form");
        sendUrl = "/exchangeLogin";
        fillPredefinedInfo('/exchangeLogin/predefined');
    });

    $('#exchangeFoldersBtn').click(function() {
        $("#contentType").val("form");
        sendUrl = "/exchangeFolders";
        fillPredefinedInfo('/exchangeFolders/predefined');
    });

    $('#exchangeFolderDataBtn').click(function() {
        $("#contentType").val("form");
        sendUrl = "/exchangeFolderData";
        fillPredefinedInfo('/exchangeFolderData/predefined');
    });

    $('#exchangeContactsDataBtn').click(function() {
        $("#contentType").val("form");
        sendUrl = "/exchangeContactsData";
        fillPredefinedInfo('/exchangeContactsData/predefined');
    });

    $('#exchangeReadEmailBtn').click(function() {
        $("#contentType").val("form");
        sendUrl = "/exchangeReadEmail";
        fillPredefinedInfo('/exchangeReadEmail/predefined');
    });

    $('#exchangeCalendarDataBtn').click(function() {
        $("#contentType").val("form");
        sendUrl = "/exchangeCalendarData";
        fillPredefinedInfo('/exchangeCalendarData/predefined');
    });

    $('#exchangeReadAppointmentBtn').click(function() {
        $("#contentType").val("form");
        sendUrl = "/exchangeReadAppointment";
        fillPredefinedInfo('/exchangeReadAppointment/predefined');
    });

    $('#exchangeReadMasterAppointmentBtn').click(function() {
        $("#contentType").val("form");
        sendUrl = "/exchangeReadMasterAppointment";
        fillPredefinedInfo('/exchangeReadMasterAppointment/predefined');
    });

    $('#sendButton').click(function() {
        $.ajax({
            type: 'post',
            url: sendUrl,
            dataType: 'json',
            data: JSON.stringify({
                'parameters': $('#parametersArea').val(),
                'headers': $('#headersArea').val(),
                'type': $('#contentType').val(),
                'baseUrl': $('#baseUrl').val()
            }),
            contentType: 'application/json'
        }).done(function(result) {
            $('#responseBodyArea').val(result.body.split('\\n').join('\n'));
            $('#responseMetaArea').val(result.meta.split('\\n').join('\n'));
        });
    });
});
