/**
 * Created by franc on 2/5/2017.
 */

var ctr = 0;
var pos = [
    [45.504506, -73.615511],
    [45.504141, -73.615852],
    [45.504133, -73.615868],
    [45.503841, -73.616046],
    [45.503494, -73.616216],
    [45.503223, -73.616361],
    [45.502665, -73.616669],
    [45.502140, -73.616937],
    [45.501558, -73.616850],
    [45.500943, -73.616643],
    [45.501295, -73.615920],
    [45.501793, -73.614996],
    [45.502130, -73.614822],
    [45.502412, -73.614065],
    [45.502909, -73.614065],
    [45.503580, -73.614320],
    [45.504129, -73.614768],
    [45.504570, -73.615397]
];

setInterval(function(){
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function() {
        //if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
        //callback(xmlHttp.responseText);
    }
    xmlHttp.open("GET", "http://fussybus.azurewebsites.net/api/values/setBusPosition?id=2&lat=" + pos[ctr][0] + "&lon=" + pos[ctr][1], true); // true for asynchronous
    xmlHttp.send(null);

    if (ctr + 1 < pos.length) {
        ctr++;
    } else ctr = 0;
}, 4000);
