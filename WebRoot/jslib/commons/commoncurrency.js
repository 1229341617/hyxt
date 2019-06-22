//´óÐ¡Ð´
DX = function (num) {  
	  var strOutput = "";  
	  var strUnit = 'Çª°ÛÊ°ÒÚÇª°ÛÊ°ÍòÇª°ÛÊ°Ôª½Ç·Ö';  
	  num += "00";  
	  var intPos = num.indexOf('.');  
	  if (intPos >= 0)  
	    num = num.substring(0, intPos) + num.substr(intPos + 1, 2);  
	  strUnit = strUnit.substr(strUnit.length - num.length);  
	  for (var i=0; i < num.length; i++)  
	    strOutput += 'ÁãÒ¼·¡ÈþËÁÎéÂ½Æâ°Æ¾Á'.substr(num.substr(i,1),1) + strUnit.substr(i,1);  
	    return strOutput.replace(/Áã½ÇÁã·Ö$/, 'Õû').replace(/Áã[Çª°ÛÊ°]/g, 'Áã').replace(/Áã{2,}/g, 'Áã').replace(/Áã([ÒÚ|Íò])/g, '$1').replace(/Áã+Ôª/, 'Ôª').replace(/ÒÚÁã{0,3}Íò/, 'ÒÚ').replace(/^Ôª/, "ÁãÔª");  
	}; 	
	

