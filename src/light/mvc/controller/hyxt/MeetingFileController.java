package light.mvc.controller.hyxt;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import light.mvc.model.hyxt.TMeetingFile;
import light.mvc.pageModel.base.Grid;
import light.mvc.pageModel.base.Json;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.hyxt.Meeting;
import light.mvc.pageModel.hyxt.MeetingFile;
import light.mvc.service.hyxt.MeetingFileServiceI;
import light.mvc.utils.PropertiesLoader;
import light.mvc.utils.StrUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


@Controller
@RequestMapping("/meetingfile")
public class MeetingFileController {
	@Autowired
	private MeetingFileServiceI meetingFileService;

	@RequestMapping("/manager")
	public String manager() {
		return "/meetingfile/meetingfile";
	}
	
	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(MeetingFile meetingFile, PageFilter ph) {
		Grid grid = new Grid();
		grid.setRows(meetingFileService.dataGrid(meetingFile, ph));
		grid.setTotal(meetingFileService.count(meetingFile, ph));
		return grid;
	}
	
	@RequestMapping("/meetinglistDataGrid")
	@ResponseBody
	public List<Meeting> meetinglistDataGrid() {
		return meetingFileService.meetinglistDataGrid();
	}
	
	@RequestMapping("/getMeetnameById")
	@ResponseBody
	public String getMeetnameById(Long id) {
		return meetingFileService.getMeetnameById(id);
	}
	
	
	@RequestMapping("/addPage")
	public String addPage() {
		return "/meetingfile/meetingfileAdd";
	}
	
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		MeetingFile m = meetingFileService.get(id);
		request.setAttribute("meetingfile", m);
		
		return "/meetingfile/meetingfileEdit";
	}

	@RequestMapping("/edit")
	@Transactional(readOnly = false, rollbackFor = DataAccessException.class)
	@ResponseBody
	public Json edit(HttpServletRequest request) {
		Json j = new Json();
		
		PropertiesLoader p = new PropertiesLoader("hyxt.properties");
     	String filePaths = p.getValue("uploadPath");
		String deleteFilename = request.getParameter("beforeFilename");
		File deleteFile = new File(filePaths + "/" + deleteFilename);
		if(deleteFile.exists()){
			deleteFile.delete();
		}
		
		MeetingFile meetingFile = new MeetingFile();
		setMeetingfile(request, meetingFile);
		meetingFile.setId(Long.valueOf(request.getParameter("meetfileId")));
		
		String fileName = null;
		String  path = null;
		File filep = null;
		try {
			//将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
	        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
	                request.getSession().getServletContext());
	        //检查form中是否有enctype="multipart/form-data"  判断是否有文件上传
	        if(multipartResolver.isMultipart(request))
	        {
	            //将request变成多部分request
	            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
	           //获取multiRequest 中所有的文件名
	            Iterator iter=multiRequest.getFileNames();
	            while(iter.hasNext())
	            {
	                //一次遍历所有文件
	                MultipartFile file=multiRequest.getFile(iter.next().toString());
	            	fileName = file.getOriginalFilename();
	            	if(StrUtil.hasLength(fileName)){
	            		System.out.println("fileName_edit:" + fileName);
	            		meetingFile.setFilename(fileName);
	            		
	            		File file1 = new File(filePaths);
	            		//判断文件夹是否存在
	            		if(!file1.exists() && !file1.isDirectory())
	            		{
	            			file1.mkdir();
	            		}
	            		path = filePaths +"/"+ fileName;
	            		
	            		System.out.println("path_edit:" + path);
	            		
	            		filep = new File(path);
	            		if(filep.exists())
	            		{
	            			filep.delete();
	            		}
	            		//上传
	            		file.transferTo(new File(path));
	            	}
	            }
	         }
	        
			try {
				meetingFileService.edit(meetingFile);
				j.setSuccess(true);
				j.setMsg("编辑成功！");
			} catch (Exception e) {
				j.setMsg(e.getMessage());
			}
        } catch (Exception e) {
			e.printStackTrace();
		}
		
		return j;
	}
	
	
	public String getValueFromRequest(HttpServletRequest request, String key){
		return request.getParameter(key);
	}
	
	@RequestMapping("/add")
	@Transactional(readOnly = false, rollbackFor = DataAccessException.class)
	@ResponseBody
	public Json add(HttpServletRequest request) {
		Json j = new Json();
		MeetingFile meetingFile = new MeetingFile();
		setMeetingfile(request, meetingFile);
		
		String fileName = null;
		String  path = null;
		File filep = null;
		try {
			//将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
	        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
	                request.getSession().getServletContext());
	        //检查form中是否有enctype="multipart/form-data"  判断是否有文件上传
	        if(multipartResolver.isMultipart(request))
	        {
	            //将request变成多部分request
	            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
	           //获取multiRequest 中所有的文件名
	            Iterator iter=multiRequest.getFileNames();
	            while(iter.hasNext())
	            {
	                //一次遍历所有文件
	                MultipartFile file=multiRequest.getFile(iter.next().toString());
	                PropertiesLoader p = new PropertiesLoader("hyxt.properties");
	            	String filePaths =p.getValue("uploadPath");
	            	fileName = file.getOriginalFilename();
	            	if(StrUtil.hasLength(fileName)){
	            		System.out.println("fileName:" + fileName);
	            		meetingFile.setFilename(fileName);
	            		
	            		File file1 = new File(filePaths);
	            		//判断文件夹是否存在
	            		if(!file1.exists() && !file1.isDirectory())
	            		{
	            			file1.mkdir();
	            		}
	            		path = filePaths +"/"+ fileName;
	            		
	            		System.out.println("path:" + path);
	            		
	            		filep = new File(path);
	            		if(filep.exists())
	            		{
	            			filep.delete();
	            		}
	            		//上传
	            		file.transferTo(new File(path));
	            	}
	            }
	        }
			TMeetingFile m = meetingFileService.getMeetingFileByFilename(meetingFile.getFilename());
			if (m != null) {
				j.setMsg("会议文件已存在!");
			} else {
				try {
					meetingFileService.add(meetingFile);
					j.setSuccess(true);
					j.setMsg("添加成功！");
				} catch (Exception e) {
					j.setMsg(e.getMessage());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return j;
	}

	private void setMeetingfile(HttpServletRequest request,
			MeetingFile meetingFile) {
		meetingFile.setComments(getValueFromRequest(request, "comments"));
		meetingFile.setFilename(getValueFromRequest(request, "filename"));
		meetingFile.setFileurl(getValueFromRequest(request, "fileurl"));
		meetingFile.setMeetid(getValueFromRequest(request, "meetid"));
		meetingFile.setMeetingname(getValueFromRequest(request, "meetingname"));
		meetingFile.setUploaddate(getValueFromRequest(request, "uploaddate"));
		meetingFile.setUploaduser(getValueFromRequest(request, "uploaduser"));
	}

	
	
	@RequestMapping("/delete")
	@Transactional(readOnly = false, rollbackFor = DataAccessException.class)
	@ResponseBody
	public Json delete(Long id) {
		Json j = new Json();
		try {
			meetingFileService.delete(id);
			j.setMsg("删除成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	
	@RequestMapping("/downloadMeetingfile")
	public void downloadMeetingfile(HttpServletRequest req, HttpServletResponse resp){
		FileInputStream fis = null;
		try {
			PropertiesLoader p = new PropertiesLoader("hyxt.properties");
	     	String filePaths =p.getValue("uploadPath");
	     	File file1 = new File(filePaths);
			if(file1.exists() || file1.isDirectory())
			{
				String dowloadFilename = req.getParameter("fileName");
				String dFilePath = filePaths + "/" + dowloadFilename;
			    File file = new File(dFilePath);
			    
			    
			    String userAgent = req.getHeader("user-agent").toLowerCase();  
			    if (userAgent.contains("msie") || userAgent.contains("like gecko")) {  
			    	dowloadFilename = URLEncoder.encode(dowloadFilename, "UTF-8");  
				} else {  
					dowloadFilename = new String(dowloadFilename.getBytes("UTF-8"), "ISO8859-1");
				}  
			    resp.setHeader("Content-Disposition","attachment;filename=" + dowloadFilename);
			    resp.setContentType("application/octet-stream");
			    resp.setContentLength((int) file.length());
		       
			    fis = new FileInputStream(file);
		        byte[] buffer = new byte[128];
		        int count = 0;
		        while ((count = fis.read(buffer)) > 0) {
		            resp.getOutputStream().write(buffer, 0, count);
		        }
			}
	     	
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				resp.getOutputStream().flush();
				resp.getOutputStream().close();
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping("/viewfiles")
	public String viewfiles(Long id, HttpServletRequest req) {
		List<MeetingFile> ls = meetingFileService.getMeetingFilesById(id);
		req.setAttribute("ls", ls);
		
		return "/meetingfile/meetingfileViewfiles";
	}
		
}

	
	
	
	
	
	
	
