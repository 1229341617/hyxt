package light.mvc.controller.base;

import java.text.SimpleDateFormat;
import java.util.Date;

import light.mvc.utils.CustomTimestampEditor;
import light.mvc.utils.StringEscapeEditor;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/base")
public class BaseController {

	protected int page = 1;// 当前页
	protected int rows = 10;// 每页显示记录数
	protected String sort;// 排序字段
	protected String order = "asc";// asc/desc

	protected String ids;// 主键集合，逗号分割

	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) 
	{
		/**
		 * 自动转换日期类型的字段格式
		 */
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		
		SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		datetimeFormat.setLenient(false);
		//转换时间
		binder.registerCustomEditor(java.sql.Timestamp.class,new CustomTimestampEditor(datetimeFormat, true));
		/**
		 * 防止XSS攻击
		 */
		binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false));	}
	/**
	 * 用户跳转JSP页面
	 * 
	 * 此方法不考虑权限控制
	 * 
	 * @param folder
	 *            路径
	 * @param jspName
	 *            JSP名称(不加后缀)
	 * @return 指定JSP页面
	 */
	@RequestMapping("/{folder}/{jspName}")
	public String redirectJsp(@PathVariable String folder, @PathVariable String jspName) {
		return "/" + folder + "/" + jspName;
	}
	
	
	private class IntEditor extends CustomNumberEditor
	{  
		public IntEditor()
		{  
			super(Integer.class,true);  
		}  
		@Override  
		public void setValue(Object value) 
		{  
			if(value == null)
			{  
				super.setValue(0);  
			}
			else
			{  
				super.setValue(value);  
			}  
		} 
		@Override
		public void setAsText(String text) throws IllegalArgumentException 
		{   
			if (text==null || text.trim().equals("")) 
			{   
			    setValue(0);   
			} 
			else 
			{   
			    setValue(Integer.parseInt(text));   
			}   
		}  
	} 
}
