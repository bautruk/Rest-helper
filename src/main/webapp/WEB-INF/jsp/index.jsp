<html>
<head>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/jquery/js/jquery-1.11.0.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/application/js/main.js"></script>
</head>
<body>
<div>
    <div>
        <div style="width:200px;float:left;display:inline-block;">
            <input type="button" value="Login" id="loginButton"/>
        </div>
        <div style="margin-left:200px;">
            <label for="parametersArea">Parameters</label>
            <textarea id="parametersArea" rows="10" style="width: 100%"></textarea>
            <br/>
            <label for="headersArea">Headers</label>
            <textarea id="headersArea" rows="10" style="width: 100%"></textarea>
            <br/>
            <br/>
            <hr/>
            <br/>
            <label for="responseMetaArea">Response meta (headers etc.)</label>
            <textarea id="responseMetaArea" rows="10" style="width: 100%"></textarea>
            <br/>
            <label for="responseBodyArea">Response body</label>
            <textarea id="responseBodyArea" rows="10" style="width: 100%"></textarea>
        </div>
    </div>
</div>
</body>
</html>
