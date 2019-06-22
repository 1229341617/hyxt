package light.mvc.controller.hyxt;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import light.mvc.controller.base.BaseController;
import light.mvc.pageModel.base.Grid;
import light.mvc.pageModel.base.Json;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.hyxt.Meeting;
import light.mvc.pageModel.hyxt.MeetingPerson;
import light.mvc.pageModel.sys.User;
import light.mvc.service.base.ServiceException;
import light.mvc.service.hyxt.MeetingPersonServiceI;
import light.mvc.service.hyxt.MeetingServiceI;
import light.mvc.service.sys.UserServiceI;
import light.mvc.utils.MyTimeUtil;


@Controller
@RequestMapping("/meeting")
public class MeetingController extends BaseController {
	@Autowired
	private MeetingServiceI meetingServiceI;

	@Autowired
	private UserServiceI userServiceI;
	@Autowired
	private MeetingPersonServiceI meetingPersonServiceI;

	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		
		return "/meeting/meeting";
	}
	
	@RequestMapping("/addPage")
	public String add(Model model, PageFilter ph, HttpServletRequest request) {
		List<User> users = userServiceI.dataGrid(null, ph);
		model.addAttribute("users", users);
		
		
		String meetingid = "" ;
		Meeting meeting = new Meeting() ;
		 
		//查询当天是否已存在会议，并生成当前会议编号
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		meetingid = dateFormat.format(new Date());
		meeting.setMeetid(meetingid);
		
		ph.setSort("createdate");
		ph.setOrder("desc");
		List<Meeting> meetings = meetingServiceI.dataGrid(meeting, ph);
		if(meetings != null && meetings.size() >= 1){
			String dayMaxIndex = meetings.get(0).getMeetid().substring(8);
			Integer curIndex = Integer.valueOf(dayMaxIndex) + 1 ;
			meetingid = meetingid + String.format("%03d", curIndex);
		}
		else{
			meetingid = meetingid + "001" ;
		}
		model.addAttribute("meetid", meetingid);
		
		return "/meeting/meetingAdd";
	}

	@RequestMapping("/editPage")
	public String editPage(Model model, PageFilter ph, Meeting meeting, HttpServletRequest request) {
		List<User> users = userServiceI.dataGrid(null, ph);
		model.addAttribute("users", users);
		
		Meeting meetings = meetingServiceI.get(meeting.getId());
		if(meetings != null ){
			model.addAttribute("meeting", meetings );
		}
		
		MeetingPerson meetingPerson = new MeetingPerson();
		meetingPerson.setId(meetings.getId());
		List<MeetingPerson> meetingPersons = meetingPersonServiceI.dataGrid(meetingPerson);
		
		if(meetingPersons != null){
			String usernames = "" ;
			for(MeetingPerson person :meetingPersons){
				usernames = (usernames=="")? (person.getUsername()) : (usernames + ","+ person.getUsername());
			}
			model.addAttribute("usernames",usernames);
		}
		
		return "/meeting/meetingEdit";
	}
	
	
	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid datagrid(Meeting meetingList, PageFilter ph) {
		Grid grid = new Grid();
		grid.setRows(meetingServiceI.dataGrid(meetingList, ph));
		grid.setTotal(meetingServiceI.count(meetingList, ph));
		return grid;
	}
	
	@RequestMapping("/add")
	@Transactional(readOnly = false, rollbackFor = DataAccessException.class)
	@ResponseBody
	public Json add(Meeting meeting) {
		Json j = new Json();
		meeting.setCreatedate(new Date());
		meeting.setStartdate(MyTimeUtil.getDate(meeting.getStarttime(), "yyyy-MM-dd HH:mm:ss"));
		meeting.setFinishdate(MyTimeUtil.getDate(meeting.getFinishtime(), "yyyy-MM-dd HH:mm:ss"));
		try {
			meetingServiceI.add(meeting);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	/**
	 * 
	* @Title: timeToDate 
	* @Description: 将String类型时间(hh:mm)转换为当前日期的Date类型数据
	* @param time
	* @return Date
	* @throws
	 */
	private Date timeToDate(String time){
		Calendar calendar = Calendar.getInstance() ;
		if(time != "null" && !time.equals("")){
			String[] timeArr = time.split(":");
			int hour = Integer.valueOf(timeArr[0]) ;
			int minute = Integer.valueOf(timeArr[1]) ;
			
			calendar.set(Calendar.HOUR_OF_DAY, hour);
			calendar.set(Calendar.MINUTE,minute);
		}
		return calendar.getTime();
	}
	

	@RequestMapping("/edit")
	@Transactional(readOnly = false, rollbackFor = DataAccessException.class)
	@ResponseBody
	public Json edit(Meeting meeting) {
		Json j = new Json();
		meeting.setStartdate(MyTimeUtil.getDate(meeting.getStarttime(), "yyyy-MM-dd HH:mm:ss"));
		meeting.setFinishdate(MyTimeUtil.getDate(meeting.getFinishtime(), "yyyy-MM-dd HH:mm:ss"));
		try {
			meetingServiceI.edit(meeting);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (ServiceException e) {
			j.setMsg(e.getMessage());
		}
		return j;
	} 
	
	@RequestMapping("/delete")
	@Transactional(readOnly = false, rollbackFor = DataAccessException.class)
	@ResponseBody
	public Json delete(Long id) {
		Json j = new Json();
		try {
			meetingServiceI.delete(id);
			j.setMsg("删除成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	} 
	
	@RequestMapping("/viewfile")
	@ResponseBody
	public String viewfile(HttpServletRequest request) {
		
		return "";
	}
	
	@RequestMapping("/viewPage")
	public String viewPage(Model model, PageFilter ph, Meeting meeting, HttpServletRequest request) {
		List<User> users = userServiceI.dataGrid(null, ph);
		model.addAttribute("users", users);
		
		Meeting meetings = meetingServiceI.get(meeting.getId());
		if(meetings != null ){
			model.addAttribute("meeting", meetings );
		}
		
		MeetingPerson meetingPerson = new MeetingPerson();
		meetingPerson.setId(meetings.getId());
		List<MeetingPerson> meetingPersons = meetingPersonServiceI.dataGrid(meetingPerson);
		
		if(meetingPersons != null){
			String usernames = "" ;
			for(MeetingPerson person :meetingPersons){
				usernames = (usernames=="")? (person.getUsername()) : (usernames + ","+ person.getUsername());
			}
			model.addAttribute("usernames",usernames);
		}
		
		return "/meeting/meetingView";
	}
}
