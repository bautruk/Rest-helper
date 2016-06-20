<html>
<head>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/jquery/js/jquery-1.11.0.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/application/js/main.js"></script>
    <link href="${pageContext.request.contextPath}/resources/application/css/main.css" type="text/css" rel="stylesheet" />
</head>
<body style="background-color: #f8f8f8;">
<div>
    <div>
        <%-- FIRST COLUMN (buttons) --%>
        <div style="width:220px;float:left;display:inline-block;">
            <select class="dropdown" id="contentType">
                <option value="get">GET</option>
                <option value="form" selected="selected">Form </option>
                <option value="json">Json </option>
                <option value="text">Text </option>
                <option value="multipart-form">Multipart-form </option>
            </select>
            <input type="button" style="font-size: 14px;width: 30%;height: 3%;background-color: #4CAF50;"
                   class="button" value="Send" id="sendButton"/>
            <select class="dropdown" id="baseUrl">
                <option value="http://192.168.12.60:9512/" selected="selected">192.168.12.60:9512</option>
                <option value="http://128.66.200.101:9512/">Dev-128.66.200.101:9512</option>
                <option value="http://128.66.200.102:9512/">Test-128.66.200.102:9512</option>
                <option value="http://192.168.100.22:9512/">192.168.100.22:9512</option>
                <option value="http://192.168.100.21:9512/">192.168.100.21:9512</option>
                <option value="https://dev.e-dapt.net:4440/">dev.e-dapt.net:4440</option>
                <option value="https://controller.botf03.net:4440/newmsa/">controller.botf03.net:4440/newmsa</option>
                <option value="http://128.66.101.101:9512/">128.66.101.101:9512</option>
                <option value="http://192.168.12.133:9512/">192.168.12.133:9512</option>
                <option value="http://192.168.12.7:9512/">192.168.12.7:9512</option>
            </select>
            <hr/>
            <select class="dropdown" id="username">
                <option value="milshtyu" selected="selected">milshtyu</option>
                <option value="pizito">pizito</option>
                <option value="exadel1">exadel1</option>
                <option value="exadel2">exadel2</option>
                <option value="exadel3">exadel3</option>
                <option value="skoval">skoval</option>
            </select>
            <input type="button" class="button" value="Login" id="loginBtn"/>
            <br/>
            <input type="button" class="button" value="Get Password" id="getPasswordBtn"/>
            <br/>
            <hr/>
            <hr/>
            <input type="button" class="button" value="Explorer Folder data" id="explorerFolderDataBtn"/>
            <br/>
            <input type="button" class="button" value="Explorer Get File" id="explorerGetFileBtn"/>
            <br/>
            <hr/>
            <hr/>
            <input type="button" class="button" value="Forward" id="forwardUrlBtn"/>
            <br/>
            <hr/>
            <hr/>
            <input type="button" class="button" value="RSA checker" id="rsaCheckerBtn"/>
            <hr/>
            <hr/>
            <input type="button" class="button" value="Exchange login" id="exchangeLoginBtn"/>
            <div class="arrow-big" id="exchangeToggle"></div>
            <div class="panel" id="exchangePanel">
                <br/>
                <div class="accordion">
                    <label class="label">Folders</label>
                    <div class="arrow"></div>
                </div>
                <div class="panel">
                    <hr/>
                    <input type="button" class="button" value="Get folders" id="exchangeFoldersBtn"/>
                    <br/>
                    <input type="button" class="button" value="Get folder data" id="exchangeFolderDataBtn"/>
                    <br/>
                    <input type="button" class="button" value="Move item" id="exchangeMoveItemBtn"/>
                </div>
                <hr/>
                <div class="accordion">
                    <label class="label">Emails</label>
                    <div class="arrow"></div>
                </div>
                <div class="panel">
                    <hr/>
                    <input type="button" class="button" value="Read email" id="exchangeReadEmailBtn"/>
                    <br/>
                    <input type="button" class="button" value="Prepare email" id="exchangePrepareEmailBtn"/>
                    <br/>
                    <input type="button" class="button" value="Update draft email" id="exchangeUpdateDraftEmailBtn"/>
                    <br/>
                    <input type="button" class="button" value="Send draft email" id="exchangeSendDraftEmailBtn"/>
                    <br/>
                    <input type="button" class="button" value="Forward email" id="exchangeForwardEmailBtn"/>
                    <br/>
                    <input type="button" class="button" value="Add attachment to draft" id="exchangeAddAttachmentToDraft"/>
                    <br/>
                    <input type="button" class="button" value="Fetch attachment" id="exchangeFetchAttachment"/>
                </div>
                <hr/>
                <div class="accordion">
                    <label class="label">Contacts</label>
                    <div class="arrow"></div>
                </div>
                <div class="panel">
                    <hr/>
                    <input type="button" class="button" value="Get contacts data" id="exchangeContactsDataBtn"/>
                    <br/>
                    <input type="button" class="button" value="Read contact" id="exchangeReadContactBtn"/>
                    <br/>
                    <input type="button" class="button" value="Add contact" id="exchangeAddContactBtn"/>
                    <br/>
                    <input type="button" class="button" value="Edit contact" id="exchangeEditContactBtn"/>
                </div>
                <hr/>
                <div class="accordion">
                    <label class="label">Calendar</label>
                    <div class="arrow"></div>
                </div>
                <div class="panel">
                    <hr/>
                    <input type="button" class="button" value="Get calendar data" id="exchangeCalendarDataBtn"/>
                    <br/>
                    <input type="button" class="button" value="Read appointment" id="exchangeReadAppointmentBtn"/>
                    <br/>
                    <input type="button" class="button" value="Read master appointment" id="exchangeReadMasterAppointmentBtn"/>
                    <br/>
                    <input type="button" class="button" value="Add appointment" id="exchangeAddAppointmentBtn"/>
                    <br/>
                    <input type="button" class="button" value="Delete appointment" id="exchangeDeleteAppointmentBtn"/>
                    <br/>
                    <input type="button" class="button" value="Update appointment" id="exchangeUpdateAppointmentBtn"/>
                    <hr/>
                    <div class="accordion">
                        <label class="label">Categories</label>
                        <div class="arrow"></div>
                    </div>
                    <div class="panel">
                        <hr/>
                        <input type="button" class="button" value="Get appointment categories" id="exchangeGetAppointmentCategoriesBtn"/>
                        <br/>
                        <input type="button" class="button" value="Create appointment category" id="exchangeCreateAppointmentCategoryBtn"/>
                        <br/>
                        <input type="button" class="button" value="Update appointment category color" id="exchangeUpdateAppointmentCategoryColorBtn"/>
                        <br/>
                        <input type="button" class="button" value="Delete appointment category" id="exchangeDeleteAppointmentCategoryBtn"/>
                    </div>
                    <hr/>
                    <div class="accordion">
                        <label class="label">Delegates</label>
                        <div class="arrow"></div>
                    </div>
                    <div class="panel">
                        <hr/>
                        <input type="button" class="button" value="Exchange add delegate" id="exchangeAddDelegateBtn"/>
                        <br/>
                        <input type="button" class="button" value="Exchange remove delegate" id="exchangeRemoveDelegateBtn"/>
                    </div>
                    <hr/>
                </div>
            </div>
        </div>
        <%-- SECOND COLUMN (areas) --%>
        <div style="margin-left:220px; padding-left: 20px">
            <div class="accordion">
                <label for="parametersArea" class="label">Parameters</label>
                <div class="arrow arrow-up"></div>
            </div>
            <div class="panel show">
                <textarea wrap="off" class="textarea" id="parametersArea" rows="10" style="width: 100%; height: 15%"></textarea>
            </div>
            <div class="accordion">
                <label for="headersArea" class="label">Headers</label>
                <div class="arrow"></div>
            </div>
            <div class="panel">
                <textarea wrap="off" class="textarea" id="headersArea" rows="10" style="width: 100%; height: 15%"></textarea>
            </div>
            <hr/>
            <div class="accordion">
                <label for="responseMetaArea" class="label">Response meta (headers etc.)</label>
                <div class="arrow arrow-up"></div>
            </div>
            <div class="panel show">
                <textarea wrap="off" class="textarea" id="responseMetaArea" rows="10" style="width: 100%; height: 13%"></textarea>
            </div>
            <div class="accordion">
                <label for="responseBodyArea" class="label">Response body</label>
                <div class="arrow arrow-up"></div>
            </div>
            <div class="panel show">
                <textarea wrap="off" class="textarea" id="responseBodyArea" rows="10" style="width: 100%; height: 61%" ></textarea>
            </div>
        </div>
    </div>
</div>
<div class="modal"></div>
</body>
</html>
