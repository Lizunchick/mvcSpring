ymaps.ready(['ext.paintOnMap']).then(init);
function init() {
    var myMap = new ymaps.Map("map", {
        center: [51.66, 39.21],
        zoom: 12
    });

    //получение данных
    $.getJSON(
        "/getsave",
        function(data) {
            console.log("Getted data", data);
            for (var i = 0; i < data.length - 1; i++){
                var mark = new ymaps.Placemark(
                    [data[i].xcoord, data[i].ycoord],
                    {
                        id: data[i].id
                    },
                    {
                        preset: 'islands#dotIcon',
                        iconColor: '#0000ff'
                    }
                );
                getlist.push(mark);
                map.geoObjects.add(mark);
            }
            lastid = data[data.length - 1].xcoord;
            console.log(lastid);
        }
    );

    var pointNumber = 0;
    var paintProcess;
    // Опции многоугольника или линии.
    var styles = [
        {strokeColor: '#ff00ff', strokeOpacity: 0.7, strokeWidth: 3, fillColor: '#ff00ff', fillOpacity: 0.4},
        {strokeColor: '#ff0000', strokeOpacity: 0.6, strokeWidth: 6, fillColor: '#ff0000', fillOpacity: 0.3},
        {strokeColor: '#00ff00', strokeOpacity: 0.5, strokeWidth: 3, fillColor: '#00ff00', fillOpacity: 0.2},
        {strokeColor: '#0000ff', strokeOpacity: 0.8, strokeWidth: 5, fillColor: '#0000ff', fillOpacity: 0.5},
        {strokeColor: '#000000', strokeOpacity: 0.6, strokeWidth: 8, fillColor: '#000000', fillOpacity: 0.3},
    ];
    var currentIndex = 0;

    // Создадим кнопку для выбора типа рисуемого контура.
    var button = new ymaps.control.Button({data: {content: 'Polygon / Polyline'}, options: {maxWidth: 150}});
    myMap.controls.add(button);

    // Подпишемся на событие нажатия кнопки мыши.
    myMap.events.add('mousedown', function (e) {
        // Если кнопка мыши была нажата с зажатой клавишей "alt", то начинаем рисование контура.
        if (e.get('altKey')) {
            if (currentIndex == styles.length - 1) {
                currentIndex = 0;
            } else {
                currentIndex += 1;
            }
            paintProcess = ymaps.ext.paintOnMap(myMap, e, {style: styles[currentIndex]});
        }


    });

    // Подпишемся на событие отпускания кнопки мыши.
    myMap.events.add('mouseup', function (e) {
        if (paintProcess) {

            // Получаем координаты отрисованного контура.
            var coordinates = paintProcess.finishPaintingAt(e);
            paintProcess = null;
            // В зависимости от состояния кнопки добавляем на карту многоугольник или линию с полученными координатами.
            var geoObject = button.isSelected() ?
                new ymaps.Polyline(coordinates, {}, styles[currentIndex]) :
                new ymaps.Polygon([coordinates], {}, styles[currentIndex]);
            myMap.geoObjects.add(geoObject);
        }

    });

    //shift+click=metka
    myMap.events.add('click', function (event) {
        if (event.get('shiftKey')) {
            pointNumber++;
            myGeoObject = new ymaps.GeoObject({
                // Описание геометрии.
                geometry: {
                    type: "Point",
                    coordinates: [event.get('coords')[0], event.get('coords')[1]]
                },
                properties: {
                    // Контент метки.
                    iconContent: pointNumber
                }
            }, {
                // Опции.
                // Метку можно перемещать.
                draggable: true
            });


            myMap.geoObjects.add(myGeoObject);
        }

    });
// add metkq on geooobject(polygone or polyline)
    myMap.geoObjects.events.add('click',function (e) {
        if(e.get('shiftKey')) {
            pointNumber++;
            myGeoObject = new ymaps.GeoObject({
                // Описание геометрии.
                geometry: {
                    type: "Point",
                    coordinates: [e.get('coords')[0], e.get('coords')[1]]
                },
                properties: {
                    // Контент метки.
                    iconContent: pointNumber
                }
            }, {
                // Опции.
                // Метку можно перемещать.
                draggable: true
            });

            myMap.geoObjects.add(myGeoObject);
        }
        else {
            var checkEditor=false;
            var target=e.get('target');
            if(e.get('ctrlKey') && !checkEditor){
                target.editor.startEditing();
                checkEditor=true;
            }
            else {
                target.editor.stopEditing();
                checkEditor=false;
            }

        }

    });
    myMap.geoObjects.events.add('contextmenu', function (e) {
        e.preventDefault();
        var target = e.get('target');
        myMap.geoObjects.remove(target);

    });


}
