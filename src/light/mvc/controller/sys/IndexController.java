package light.mvc.controller.sys;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import light.mvc.controller.base.BaseController;
import light.mvc.framework.constant.GlobalConstant;
import light.mvc.pageModel.base.Json;
import light.mvc.pageModel.base.SessionInfo;
import light.mvc.pageModel.hyxt.Meeting;
import light.mvc.pageModel.hyxt.MeetingFile;
import light.mvc.pageModel.hyxt.MeetingPerson;
import light.mvc.pageModel.sys.User;
import light.mvc.service.hyxt.MeetingFileServiceI;
import light.mvc.service.hyxt.MeetingPersonServiceI;
import light.mvc.service.hyxt.MeetingServiceI;
import light.mvc.service.sys.OrganizationServiceI;
import light.mvc.service.sys.ResourceServiceI;
import light.mvc.service.sys.UserServiceI;
import light.mvc.utils.MD5Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class IndexController extends BaseController {

	@Autowired
	private UserServiceI userService;
	@Autowired
	private ResourceServiceI resourceService;
	@Autowired
	private OrganizationServiceI OrganizationService;
	@Autowired
	private MeetingPersonServiceI meetingPersonService;
	@Autowired
	private MeetingServiceI meetingService;
	@Autowired
	private MeetingFileServiceI meetingFileService;

	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		if ((sessionInfo != null) && (sessionInfo.getId() != null) ) {
			if(sessionInfo.getLoginname().equals("admin")){
				return "/index";
			}
			List<MeetingPerson> meetings = meetingPersonService.findMeetingsByUserId(sessionInfo.getId());
			List<List<MeetingFile>> mfs = new ArrayList<List<MeetingFile>>();
			for (MeetingPerson m : meetings) {
				Meeting meeting = meetingService.get(m.getMeetingid());
				List<MeetingFile> meetingFiles = new ArrayList<MeetingFile>();
				List<MeetingFile> mFiles = meetingFileService.getMeetingFilesById(meeting.getId());
				meetingFiles.addAll(mFiles);
				if(meetingFiles.size() > 0){
					mfs.add(meetingFiles);
				}
			}
			request.setAttribute("mfs", mfs);
			
			return "/meetingfile/meetingfileViewByUser";
		}
		return "/login";
	}

	@RequestMapping("/index1")
	public String index1(HttpServletRequest request, HttpSession session,String userId) {
		
		User sysuser = userService.get(Long.valueOf(userId));
		SessionInfo sessionInfo = new SessionInfo();
		sessionInfo.setId(sysuser.getId());
		sessionInfo.setLoginname(sysuser.getLoginname());
		sessionInfo.setName(sysuser.getName());
		sessionInfo.setOrganizationId(sysuser.getOrganizationId());
		sessionInfo.setResourceList(userService.listResource(sysuser.getId()));
		sessionInfo.setResourceTabsList(userService.listTabsResource(sysuser.getId()));
		sessionInfo.setResourceAllList(resourceService.listAllResource());	
		session.setAttribute(GlobalConstant.SESSION_INFO, null);
		session.setAttribute(GlobalConstant.SESSION_INFO, sessionInfo);
		return "/index";
	}
	
	public static void main(String[] args) {
		String a = MD5Util.md5("1");
		System.out.println(a);
	}
	
	
	
	
	@ResponseBody
	@RequestMapping("/login")
	public Json login(User user, HttpSession session,HttpServletRequest request) {
		Json j = new Json();
		String Message="";
		User sysuser = userService.login(user);
		User user2;
		Long deptid;
		String deptName;
		if (sysuser != null) {
			user2=userService.getU(sysuser.getId());
			deptid = user2.getOrganizationId();
			deptName = user2.getOrganizationName();
			j.setSuccess(true);
			Message+="登陆成功！";

			SessionInfo sessionInfo = new SessionInfo();
			
			sessionInfo.setId(sysuser.getId());
			sessionInfo.setLoginname(sysuser.getLoginname());

			sessionInfo.setName(sysuser.getName());

			sessionInfo.setOrganizationId(deptid);
			Long accountId;
			if(OrganizationService.get(deptid).getIsaccount()!=1){
				accountId = OrganizationService.getAccounorgantById(deptid).getId();
			}
			else{
				accountId = deptid;
				
			}
			sessionInfo.setShortname(OrganizationService.get(accountId).getShortname());
			sessionInfo.setSystime(new Date());
			sessionInfo.setOrganizationname(deptName);
			sessionInfo.setAccountId(accountId);
			// user.setIp(IpUtil.getIpAddr(getRequest()));
			sessionInfo.setResourceList(userService.listResource(sysuser.getId()));
			sessionInfo.setResourceTabsList(userService.listTabsResource(sysuser.getId()));
			sessionInfo.setResourceAllList(resourceService.listAllResource());		
			session.setAttribute(GlobalConstant.SESSION_INFO, sessionInfo);

		} else {
			j.setMsg("用户名或密码错误！");
			return j;
		}
		j.setMsg(Message);
		return j;
	}

	@ResponseBody
	@RequestMapping("/logout")
	public Json logout(HttpSession session) {
		Json j = new Json();
		if (session != null) {
			session.invalidate();
		}
		j.setSuccess(true);
		j.setMsg("注销成功！");
		return j;
	}

}
