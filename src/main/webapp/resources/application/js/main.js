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

    var setContentType = function(value) {
        $("#contentType").val(value);
    };

    $('#loginBtn').click(function() {
        setContentType("json");
        sendUrl = "/login";
        fillPredefinedInfo('/login/predefined');
    });

    $('#explorerFolderDataBtn').click(function() {
        setContentType("json");
        sendUrl = "/explorerFolderData";
        fillPredefinedInfo('/explorerFolderData/predefined');
    });

    $('#explorerGetFileBtn').click(function() {
        setContentType("json");
        sendUrl = "/explorerGetFile";
        fillPredefinedInfo('/explorerGetFile/predefined');
    });


    $('#getPasswordBtn').click(function() {
        setContentType("json");
        sendUrl = "/getPassword";
        fillPredefinedInfo('/getPassword/predefined');
    });

    $('#forwardUrlBtn').click(function() {
        setContentType("json");
        sendUrl = "/forwardUrl";
        fillPredefinedInfo('/forwardUrl/predefined');
    });

    $('#exchangeLoginBtn').click(function() {
        setContentType("form");
        sendUrl = "/exchangeLogin";
        fillPredefinedInfo('/exchangeLogin/predefined');
    });

    $('#exchangeFoldersBtn').click(function() {
        setContentType("form");
        sendUrl = "/exchangeFolders";
        fillPredefinedInfo('/exchangeFolders/predefined');
    });

    $('#exchangeFolderDataBtn').click(function() {
        setContentType("form");
        sendUrl = "/exchangeFolderData";
        fillPredefinedInfo('/exchangeFolderData/predefined');
    });

    $('#exchangeContactsDataBtn').click(function() {
        setContentType("form");
        sendUrl = "/exchangeContactsData";
        fillPredefinedInfo('/exchangeContactsData/predefined');
    });

    $('#exchangeReadEmailBtn').click(function() {
        setContentType("form");
        sendUrl = "/exchangeReadEmail";
        fillPredefinedInfo('/exchangeReadEmail/predefined');
    });

    $('#exchangeCalendarDataBtn').click(function() {
        setContentType("form");
        sendUrl = "/exchangeCalendarData";
        fillPredefinedInfo('/exchangeCalendarData/predefined');
    });

    $('#exchangeReadAppointmentBtn').click(function() {
        setContentType("form");
        sendUrl = "/exchangeReadAppointment";
        fillPredefinedInfo('/exchangeReadAppointment/predefined');
    });

    $('#exchangeReadMasterAppointmentBtn').click(function() {
        setContentType("form");
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
