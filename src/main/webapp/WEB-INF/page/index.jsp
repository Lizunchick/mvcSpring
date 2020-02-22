<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Draw&go</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script src="https://api-maps.yandex.ru/2.1/?apikey=f32b5dd3-044a-4e8a-ac9a-9088af4517ea&lang=ru_RU" type="text/javascript">
    </script>
    <script src="https://yandex.st/jquery/2.2.3/jquery.min.js" type="text/javascript"></script>
    <script src="<c:url value="/js/paintOnMap.js"/>"></script>
    <script src="<c:url value="/js/drawMap.js"/>"></script>
    <style type="text/css">
        #map{
            margin-left: auto;
            margin-right: auto;
            display: block;
        }
    </style>
</head>


<body>

<div id="map" style="weight:100vw; height:97vh"></div>

</body>
</html>>
