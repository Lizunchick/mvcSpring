var getList=[];
var lastid=0;
var pointNumber = 0;
var checkEditor = false;
ymaps.ready(['ext.paintOnMap']).then(init);

function coordsToDots(coordinates) {
    var Jsoncoords = [];
    for (var i = 0; i < coordinates.length; i++) {
        Jsoncoords[i] = {
            xcoord: coordinates[i][0],
            ycoord: coordinates[i][1]

        }
    }
    return Jsoncoords;
}

function init() {
    var myMap = new ymaps.Map("map", {
        center: [51.66, 39.21],
        zoom: 12
    });

    //получение данных
    $.ajax({
        type:'GET',
        dataType:'json',
        contentType: 'application/json',
        url:'coords',
        success:function(data) {
            console.log("Getted data", data);
            for (var i = 0; i < data.length ; i++){
                getList[lastid]=data[i]['id'];
                lastid++;
                switch (data[i]['type']['id']) {
                    case 1:{
                        pointNumber++;
                        myGeoObject = new ymaps.GeoObject({
                            geometry: {
                                type: "Point",
                                coordinates: [data[i]['coords'][0]['xcoord'], data[i]['coords'][0]['ycoord']]
                            },
                            properties: {
                                iconContent: pointNumber// Контент метки.
                            }
                        }, {
                            draggable: true// Опции// Метку можно перемещать.
                        });
                        myMap.geoObjects.add(myGeoObject);
                        myGeoObject.properties.set('id', data[i]['id']);
                        break;
                    }
                    case 2:{
                        var coordinates=[];
                        for(j=0;j<data[i]['coords'].length-1;j++)
                        {
                            var jcoord=[data[i]['coords'][j]['xcoord'],data[i]['coords'][j]['ycoord']]
                            coordinates[j]=jcoord;}
                        if (currentIndex == styles.length - 1) {
                            currentIndex = 0;
                        } else {
                            currentIndex += 1;
                        }
                        var geoObject=new ymaps.Polyline(coordinates, {}, styles[currentIndex])
                        myMap.geoObjects.add(geoObject);
                        geoObject.properties.set('id', data[i]['id']);
                        break;
                    }
                    case 3:{
                        var coordinates=[];
                        for(j=0;j<data[i]['coords'].length;j++)
                        {
                            var jcoord=[data[i]['coords'][j]['xcoord'],data[i]['coords'][j]['ycoord']]
                            coordinates[j]=jcoord;}
                        if (currentIndex == styles.length - 1) {
                            currentIndex = 0;
                        } else {
                            currentIndex += 1;
                        }
                        var geoObject=new ymaps.Polygon([coordinates], {}, styles[currentIndex]);
                        myMap.geoObjects.add(geoObject);
                        geoObject.properties.set('id', data[i]['id']);
                        break;
                    }

                }

            }
            console.log(getList);

        }

    });



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
            console.log(coordinates);
            paintProcess = null;
            var type;
            // В зависимости от состояния кнопки добавляем на карту многоугольник или линию с полученными координатами.
            if(button.isSelected()){
                var geoObject =new ymaps.Polyline(coordinates, {}, styles[currentIndex]) ;
                type={
                    id:2,
                    name:'POLYLINE'
                }
            }
            else{
                var geoObject = new ymaps.Polygon([coordinates], {}, styles[currentIndex]);
                type={
                    id:3,
                    name:'POLYLGON'
                }
            }
            var Jsoncoords = coordsToDots(coordinates);
            myMap.geoObjects.add(geoObject);
            var SendData =
                {
                    coords: Jsoncoords,
                    type: type
                }

            console.log("ObjectData", SendData);
            var JsonData =  JSON.stringify(SendData);
            $.ajax({
                method:'POST',
                dataType:'JSON',
                contentType: 'application/json',
                url:'save',
                data: JsonData,
                success:function(data) {
                    console.log("success");
                    console.log("Getted id", data);
                    geoObject.properties.set('id', data);
                    getList[lastid]=data;
                    lastid++;

                },
                error:function (e) {
                    console.log(e);

                }
            })
        }

    })


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

            type={
                id:1,
                name:'POINT'
            }

            var Jsoncoords=[];

            Jsoncoords[0]= {
                xcoord: event.get('coords')[0],
                ycoord: event.get('coords')[1]
            }

            var SendData =
                {
                    coords: Jsoncoords,
                    type: type
                }

            console.log("ObjectData", SendData);
            var JsonData =  JSON.stringify(SendData);
            $.ajax({
                method:'POST',
                dataType:'json',
                contentType: 'application/json',
            url:'save',
                data:  JsonData,
                success:function(data) {
                    console.log("success");
                    console.log("Getted id ", data);
                    myGeoObject.properties.set('id', data);
                    getList[lastid]=data;
                    lastid++;

                }
            })
        }



    });
// add metkq on geooobject(polygone or polyline)
    myMap.geoObjects.events.add('click',function (e) {
        if (e.get('shiftKey')) {
            pointNumber++;
            myGeoObject = new ymaps.GeoObject({
                // Описание геометрии.
                geometry: {
                    type: "Point",
                    coordinates: [e.get('coords')[0], e.get('coords')[1]]
                },
                properties: {
                    iconContent: pointNumber// Контент метки.
                }
            }, {
                draggable: true // Опции// Метку можно перемещать.
            });

            myMap.geoObjects.add(myGeoObject);
            type = {
                id: 1,
                name: 'POINT'
            }

            var Jsoncoords = [];

            Jsoncoords[0] = {
                xcoord: e.get('coords')[0],
                ycoord: e.get('coords')[1]
            }

            var SendData =
                {
                    coords: Jsoncoords,
                    type: type
                }

            console.log("ObjectData", SendData);
            var JsonData = JSON.stringify(SendData);
            $.ajax({
                method: 'POST',
                dataType: 'json',
                contentType: 'application/json',
                url: 'save',
                data: JsonData,
                success: function (data) {
                    console.log("success");
                    console.log("Getted id", data);
                    myGeoObject.properties.set('id', data);
                    getList[lastid] = data;
                    lastid++;

                }
            })
        } else {
            var target = e.get('target');
            if (e.get('ctrlKey') && !checkEditor) {
                target.editor.startEditing();
                checkEditor = true;

                /* target.events.add(['dragstart','dragend'], function (e) {
                    // var target = e.get('target');
                     target.options.set("iconColor", '#0000ff');
                     newCoords = target.geometry._coordinates;
                     let jsonCoords = coordsToDots([newCoords]);
                     console.log(newCoords);
                     console.log(jsonCoords);
                     let id = target.properties.get('id');
                     console.log(id)
                     let data = {
                         id: id,
                         coords: jsonCoords
                     };
                     $.ajax({
                         type: 'PUT',
                         data: JSON.stringify(data),
                         dataType: 'json',
                         url: 'update',
                         contentType: 'application/json',
                         success: function () {
                             console.log('update')
                         }

                     })
                     target.options.set("iconColor", '#00ffff');
                 });
             }*/

            } else {
                if (!checkEditor) {
                    return;
                }
                target.editor.stopEditing();
                newCoords = target.geometry._coordPath._coordinates[0];
                let jsonCoords = coordsToDots(newCoords);
                let id = target.properties.get('id');
                let data = {
                    id: id,
                    coords: jsonCoords
                };

                $.ajax({
                    type: 'PUT',
                    data: JSON.stringify(data),
                    dataType: 'json',
                    url: 'update',
                    contentType: 'application/json',
                    success: function () {
                        console.log('update')
                    }

                })
                checkEditor = false;


            }

        }



    });

    myMap.geoObjects.events.add(['dragstart','dragend'], function (e) {
        var target = e.get('target');
        target.options.set("iconColor", '#0000ff');
        newCoords = target.geometry._coordinates;
        let jsonCoords = coordsToDots([newCoords]);
        console.log(newCoords);
        console.log(jsonCoords);
        let id = target.properties.get('id');
        console.log(id)
        let data = {
            id: id,
            coords: jsonCoords
        };
        $.ajax({
            type: 'PUT',
            data: JSON.stringify(data),
            dataType: 'json',
            url: 'update',
            contentType: 'application/json',
            success: function () {
                console.log('update')
            }

        })
        target.options.set("iconColor", '#00ffff');
    });

    myMap.geoObjects.events.add('contextmenu', function (e) {
        e.preventDefault();
        var target = e.get('target');
        var deleteId=target.properties.get('id');
        console.log('delete '+deleteId);

        $.ajax({
            type:'POST',
            url:'delete/id/'+deleteId,
            contentType:'application/json',
            success:function() {
                console.log("success deleted");
                myMap.geoObjects.remove(target);

            },
            error:function (e) {
                console.log(e);

            }

        })


    });


}
