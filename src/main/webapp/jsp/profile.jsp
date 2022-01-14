<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href="static/css/profile.css" rel="stylesheet">

    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
</head>
<body>

<div class="container profile">
    <div class="row" style="height: auto">
        <div class="col">
            <div class="row avatar-block">
                <img src="images/${avatar}" id="avatar">
            </div>

            <div class="row">
                <form method="post" action="/ProfileApp/images" enctype="multipart/form-data">
                    <div class="btn-group" role="group" aria-label="Basic example">
                        <div class="btn btn-primary btn-sm float-left">
                            <input id="files" title="Click to choose file" type="file" accept="image/*" style="width: 100%" name="image" required>
                        </div>
                        <button type="submit" class="send-btn btn btn-primary btn-sm">Send</button>
                    </div>

                </form>
                <div style="text-align: center">
                    <progress value="${usedMemory}" max="${commonMemory}"></progress>
                    <p>${usedMemory} B out of ${commonMemory} B</p>
                </div>
            </div>
        </div>
        <div class="col-md-auto">
            <div class="row">
                <div class="col">
                    <div id="name">
                        <h2>${user.name} ${user.surname}</h2>
                        <h4 id = "email">${user.email}</h4>
                    </div>
                </div>
                <div class="col-md-auto">
                    <form class="md-form" method="get" action="/ProfileApp/logout">
                        <button type="submit">
                            <svg xmlns="http://www.w3.org/2000/svg" x="0px" y="0px"
                                 width="48" height="48"
                                 viewBox="0 0 172 172"
                                 style=" fill:#000000;"><g fill="none" fill-rule="nonzero" stroke="none" stroke-width="1" stroke-linecap="butt" stroke-linejoin="miter" stroke-miterlimit="10" stroke-dasharray="" stroke-dashoffset="0" font-family="none" font-weight="none" font-size="none" text-anchor="none" style="mix-blend-mode: normal"><path d="M0,172v-172h172v172z" fill="none"></path><g><path d="M86,14.33333c-39.58041,0 -71.66667,32.08626 -71.66667,71.66667c0,39.58041 32.08626,71.66667 71.66667,71.66667c39.58041,0 71.66667,-32.08626 71.66667,-71.66667c0,-39.58041 -32.08626,-71.66667 -71.66667,-71.66667z" fill="#ff174d"></path><path d="M118.39333,52.56392l-9.44208,10.793c5.74408,5.82292 9.29875,13.81733 9.29875,22.64308c0,17.81275 -14.43725,32.25 -32.25,32.25c-17.81275,0 -32.25,-14.43725 -32.25,-32.25c0,-8.82933 3.55467,-16.82375 9.30233,-22.64667l-9.44208,-10.793c-8.73975,8.471 -14.19358,20.31033 -14.19358,33.43967c0,25.72833 20.855,46.58333 46.58333,46.58333c25.72833,0 46.58333,-20.855 46.58333,-46.58333c0,-13.12933 -5.45025,-24.96867 -14.19,-33.43608z" fill="#ffffff"></path><path d="M78.83333,39.41667h14.33333v43h-14.33333z" fill="#ffffff"></path></g></g></svg>
                        </button>
                    </form>
                </div>
            </div>

            <h5 style="text-align: center">Last logins</h5>
            <table class="table table-dark">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Date</th>
                    <th>Time</th>
                    <th>IP</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${logins}" var="login" varStatus="status">
                    <tr>
                        <th>${status.count}</th>
                        <td>${login.date}</td>
                        <td>${login.time}</td>
                        <td>${login.ip}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="images">
        <div class="row">
            <div class="col-md-12">
                <h5 style="text-align: center">Files</h5>
                <table class="table table-dark">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>File name</th>
                        <th>Size</th>
                        <th>MIME</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${images}" var="image" varStatus="status">
                        <tr>
                            <th>${status.count}</th>
                            <td><a href="images/${image.uniqueName}">${image.originalName}</a></td>
                            <td>${image.size} bytes</td>
                            <td>${image.mime}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

</body>
</html>
