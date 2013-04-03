/*global window */
function popup(url, windowname, x, y) {
    "use strict";
    window.open(url, windowname, "resizable=no,toolbar=no,scrollbars=no,menubar=no,status=no,directories=no,width=350,height=400,left=" + x + ", top=" + y + "");
}