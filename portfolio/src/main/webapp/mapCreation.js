function createMap() {

  var visited=[alabama];

  var unvisited=[alaska];

  const map = new google.maps.Map(
      document.getElementById('map'),
      {center: {lat: 37.422, lng: -122.084}, zoom: 16});
    
    var i;
    for(i=0;i<visited.length;i++){
        state=visited[i];
        var statePolygon = new google.maps.Polygon({
            paths: alabama,
            strokeColor: 'green',
            strokeOpacity : 1,
            strokeWeight: 3,
            fillColor: 'green',
            fillOpacity: 0.35
        });
        statePolygon.setMap(map);
    }
    


    for(i=0;i<alaska.length;i++){
        var alaskaPolygon = new google.maps.Polygon({
            paths: alaska[i],
            strokeColor: 'red',
            strokeOpacity : 1,
            strokeWeight: 3,
            fillColor: 'red',
            fillOpacity: 0.35
        });

        alaskaPolygon.setMap(map);
    }
    
}
