//��Сд
DX = function (num) {  
	  var strOutput = "";  
	  var strUnit = 'Ǫ��ʰ��Ǫ��ʰ��Ǫ��ʰԪ�Ƿ�';  
	  num += "00";  
	  var intPos = num.indexOf('.');  
	  if (intPos >= 0)  
	    num = num.substring(0, intPos) + num.substr(intPos + 1, 2);  
	  strUnit = strUnit.substr(strUnit.length - num.length);  
	  for (var i=0; i < num.length; i++)  
	    strOutput += '��Ҽ��������½��ƾ�'.substr(num.substr(i,1),1) + strUnit.substr(i,1);  
	    return strOutput.replace(/������$/, '��').replace(/��[Ǫ��ʰ]/g, '��').replace(/��{2,}/g, '��').replace(/��([��|��])/g, '$1').replace(/��+Ԫ/, 'Ԫ').replace(/����{0,3}��/, '��').replace(/^Ԫ/, "��Ԫ");  
	}; 	
	

