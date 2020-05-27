//时间转换函数
function showTime(tempDate) {

    var d = new Date(tempDate);
    var year = d.getFullYear();
    var month = d.getMonth();
    month++;
    var day = d.getDate();
    var hours = d.getHours();

    var minutes = d.getMinutes();
    var seconds = d.getSeconds();
    month = month < 10 ? "0" + month : month;
    day = day < 10 ? "0" + day : day;
    hours = hours < 10 ? "0" + hours : hours;
    minutes = minutes < 10 ? "0" + minutes : minutes;
    seconds = seconds < 10 ? "0" + seconds : seconds;
    var time = year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
    return time;

}
