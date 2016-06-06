$(function() {
    var sendUrl = "";

    $.ajaxSetup({
        beforeSend:function(){
            $("body").addClass("loading");
        },
        complete:function(){
            $("body").removeClass("loading");
        }
    });

    var acc = document.getElementsByClassName("accordion");
    for (var i = 0; i < acc.length; i++) {
        acc[i].onclick = function(){
            this.getElementsByClassName("arrow")[0].classList.toggle("arrow-up");
            this.nextElementSibling.classList.toggle("show");
        }
    }

    $('#exchangeToggle').click(function() {
        this.classList.toggle("arrow-up-big");
        this.nextElementSibling.classList.toggle("show");
    });
    
    var clearResponseAreas = function() {
        $('#responseMetaArea').val('');
        $('#responseBodyArea').val('');
    };

    var fillPredefinedInfo = function(url) {
        $.ajax({
            type: 'get',
            url: url,
            dataType: 'json',
            data: {'username': $('#username').val()}
        }).done(function(result) {
            clearResponseAreas();

            $('#parametersArea').val(result.requestParams);
            $('#headersArea').val(result.requestHeaders);
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

    $('#rsaCheckerBtn').click(function() {
        setContentType("json");
        sendUrl = "/rsaCheck";
        fillPredefinedInfo('/rsaCheck/predefined');
    });

    $('#exchangeLoginBtn').click(function() {
        setContentType("form");
        $('#exchangePanel').addClass("show");
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

    $('#exchangeReadContactBtn').click(function() {
        setContentType("form");
        sendUrl = "/exchangeReadContact";
        fillPredefinedInfo('/exchangeReadContact/predefined');
    });

    $('#exchangeMoveItemBtn').click(function() {
        setContentType("form");
        sendUrl = "/exchangeMoveItem";
        fillPredefinedInfo('/exchangeMoveItem/predefined');
    });

    $('#exchangeReadEmailBtn').click(function() {
        setContentType("form");
        sendUrl = "/exchangeReadEmail";
        fillPredefinedInfo('/exchangeReadEmail/predefined');
    });

    $('#exchangePrepareEmailBtn').click(function() {
        setContentType("form");
        sendUrl = "/exchangePrepareEmail";
        fillPredefinedInfo('/exchangePrepareEmail/predefined');
    });

    $('#exchangeUpdateDraftEmailBtn').click(function() {
        setContentType("form");
        sendUrl = "/exchangeUpdateDraftEmail";
        fillPredefinedInfo('/exchangeUpdateDraftEmail/predefined');
    });

    $('#exchangeSendDraftEmailBtn').click(function() {
        setContentType("form");
        sendUrl = "/exchangeSendDraftEmail";
        fillPredefinedInfo('/exchangeSendDraftEmail/predefined');
    });

    $('#exchangeForwardEmailBtn').click(function() {
        setContentType("form");
        sendUrl = "/exchangeForwardEmail";
        fillPredefinedInfo('/exchangeForwardEmail/predefined');
    });

    $('#exchangeAddAttachmentToDraft').click(function() {
        setContentType("multipart-form");
        sendUrl = "/exchangeAddAttachment";
        fillPredefinedInfo('/exchangeAddAttachment/predefined');
    });

    $('#exchangeFetchAttachment').click(function() {
        setContentType("get");
        sendUrl = "/exchangeFetchAttachment";
        fillPredefinedInfo('/exchangeFetchAttachment/predefined');
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

    $('#exchangeAddAppointmentBtn').click(function() {
        setContentType("form");
        sendUrl = "/exchangeAddAppointment";
        fillPredefinedInfo('/exchangeAddAppointment/predefined');
    });

    $('#exchangeDeleteAppointmentBtn').click(function() {
        setContentType("form");
        sendUrl = "/exchangeDeleteAppointment";
        fillPredefinedInfo('/exchangeDeleteAppointment/predefined');
    });

    $('#exchangeUpdateAppointmentBtn').click(function() {
        setContentType("form");
        sendUrl = "/exchangeUpdateAppointment";
        fillPredefinedInfo('/exchangeUpdateAppointment/predefined');
    });
    $('#exchangeAddDelegateBtn').click(function() {
        setContentType("form");
        sendUrl = "/exchangeAddDelegate";
        fillPredefinedInfo('/exchangeAddDelegate/predefined');
    });
    $('#exchangeRemoveDelegateBtn').click(function() {
        setContentType("form");
        sendUrl = "/exchangeRemoveDelegate";
        fillPredefinedInfo('/exchangeRemoveDelegate/predefined');
    });
    $('#exchangeGetAppointmentCategoriesBtn').click(function() {
        setContentType("form");
        sendUrl = "/exchangeGetAppointmentCategories";
        fillPredefinedInfo('/exchangeGetAppointmentCategories/predefined');
    });
    $('#exchangeCreateAppointmentCategoryBtn').click(function() {
        setContentType("form");
        sendUrl = "/exchangeCreateAppointmentCategory";
        fillPredefinedInfo('/exchangeCreateAppointmentCategory/predefined');
    });
    $('#exchangeUpdateAppointmentCategoryColorBtn').click(function() {
        setContentType("form");
        sendUrl = "/exchangeUpdateAppointmentCategoryColor";
        fillPredefinedInfo('/exchangeUpdateAppointmentCategoryColor/predefined');
    });
    $('#exchangeDeleteAppointmentCategoryBtn').click(function() {
        setContentType("form");
        sendUrl = "/exchangeDeleteAppointmentCategory";
        fillPredefinedInfo('/exchangeDeleteAppointmentCategory/predefined');
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
            $('#responseBodyArea').val(JSON.stringify(JSON.parse(result.body), null, 2));
            $('#responseMetaArea').val(result.meta);
            $('#headersArea').val(result.headers);
        });
    });
});
