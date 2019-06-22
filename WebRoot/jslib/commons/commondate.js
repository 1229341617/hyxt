//格式化当前日期
formatterDate = function(date) {
	var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
	var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"+ (date.getMonth() + 1);
	var hor = date.getHours() > 9 ? date.getHours(): "0" + date.getHours();
	var min = date.getMinutes() > 9 ? date.getMinutes() : "0" + date.getMinutes();
	var sec = date.getSeconds() > 9 ? date.getSeconds(): "0" + date.getSeconds();
	return date.getFullYear() + '-' + month + '-' + day+" "+hor+":"+min+":"+sec;
}

formatterDate_y = function(date) {
	return date.getFullYear();
}
formatterDate_m = function(date) {
	return date.getMonth()+1;
}
formatterDate_d = function(date) {
	return date.getDate();
}
