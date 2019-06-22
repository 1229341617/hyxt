package light.mvc.service.hyxt.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import light.mvc.dao.BaseDaoI;
import light.mvc.model.hyxt.TMeetingList;
import light.mvc.model.hyxt.TMeetingPerson;
import light.mvc.model.sys.Tuser;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.hyxt.Meeting;
import light.mvc.pageModel.hyxt.MeetingPerson;
import light.mvc.pageModel.sys.User;
import light.mvc.service.hyxt.MeetingPersonServiceI;
import light.mvc.service.hyxt.MeetingServiceI;
import light.mvc.service.sys.UserServiceI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeetingServiceImpl implements MeetingServiceI{

	@Autowired
	BaseDaoI<TMeetingList> meetingDao;
	
	@Autowired
	BaseDaoI<TMeetingPerson> meetingPersonDao ;
 
	@Autowired
	MeetingPersonServiceI meetingPersonServiceI;
	
	@Autowired
	UserServiceI userServiceI ;
	
	@Override
	public List<Meeting> dataGrid(Meeting meeting, PageFilter ph) {
		List<Meeting> ul = new ArrayList<Meeting>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from TMeetingList t ";
		
		List<TMeetingList> l = meetingDao.find(hql + whereHql(meeting, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
		
		for (TMeetingList t : l) {
			Meeting u = new Meeting();
			BeanUtils.copyProperties(t, u);
			Tuser user = t.getUser();
			if(user != null){
				u.setUserid(user.getId());
				u.setUsername(user.getName());
			}
			ul.add(u);
		}
		return ul;
		
	}

	@Override
	public Meeting get(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TMeetingList t = meetingDao.get("from TMeetingList t  where t.id = :id", params);
		Meeting u = new Meeting();
		BeanUtils.copyProperties(t, u);

		Tuser user = t.getUser();
		if(user != null){
			u.setUserid(user.getId());
			u.setUsername(user.getName());
		}
		return u;
	}
	
	private String whereHql(Meeting meeting, Map<String, Object> params) {
		String hql = "";
		if (meeting != null) {
			hql += "where 1 = 1 " ;
			if (meeting.getId() != null) {
				hql += " and t.id = :id";
				params.put("id",  meeting.getMeetid() );
			}
			if (meeting.getMeetid() != null) {
				hql += " and t.meetid like :meetid";
				params.put("meetid", "%%" + meeting.getMeetid() + "%%");
			}
			if (meeting.getMeetname() != null && !meeting.getMeetname().equals("")) {
				hql += " and t.meetname like :meetname";
				params.put("meetname", meeting.getMeetname());
			}
			if (meeting.getMeetingroom() != null && !meeting.getMeetingroom().equals("") ) {
				hql += " and t.meetingroom like :meetingroom";
				params.put("meetingroom", meeting.getMeetingroom());
			}
			if (meeting.getStartdate() != null && !meeting.getStartdate().equals("") ) {
				hql += " and t.startdate >= :startdate" ;
				params.put("startdate", meeting.getStartdate());
			}
			if (meeting.getFinishdate() != null && !meeting.getFinishdate().equals("")) {
				hql += " and t.finishdate <= :finishdate" ;
				params.put("finishdate", meeting.getFinishdate());
			}
			if (meeting.getUserid() != null ) {
				hql += " and t.user.name like :username" ;
				params.put("username", meeting.getUserid());
			}
			if (meeting.getUsername() != null && !meeting.getUsername().equals("") ) {
				hql += " and t.user.name like :username" ;
				params.put("username", meeting.getUsername());
			}
			
			if(meeting.getQueryword() != null && !meeting.getQueryword().equals("")){
				hql += " and ( (t.meetid like :meetid) ";
				params.put("meetid", "%%" + meeting.getQueryword() + "%%");
				
				hql += " or ( t.meetname like :meetname) ";
				params.put("meetname", "%%" + meeting.getQueryword() + "%%");
				
				hql += " or ( t.user.name like :username ) )" ;
				params.put("username","%%" + meeting.getQueryword()+ "%%");
			}
		}
		return hql;
	}

	private String orderHql(PageFilter ph) {
		String orderString = "";
		if ((ph.getSort() != null) && (ph.getOrder() != null)) {
			orderString = " order by t." + ph.getSort() + " " + ph.getOrder();
		}
		return orderString;
	}

 
	@Override
	public Long count(Meeting meeting, PageFilter ph) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from TMeetingList t ";
		return meetingDao.count("select count(*) " + hql + whereHql(meeting, params), params);
	}

 
	@Override
	public void edit(Meeting meeting) {
		// TODO Auto-generated method stub
		TMeetingList meetingList = meetingDao.get(TMeetingList.class, meeting.getId());
		
		meeting.setCreatedate(meetingList.getCreatedate());
		BeanUtils.copyProperties(meeting, meetingList);
		Tuser u = new Tuser();
		u.setId(meeting.getUserid());
		meetingList.setUser(u);
		meetingDao.update(meetingList);
		
		meetingPersonDao.getCurrentSession().flush();
		
		/**
		 * 找出当前会议曾经填写的参会人员，将之从会议人员表中删除
		 */
		MeetingPerson meetingPerson = new MeetingPerson();
		meetingPerson.setId(meeting.getId());
		List<MeetingPerson> meetingPersons = meetingPersonServiceI.dataGrid(meetingPerson);
		for(MeetingPerson p:meetingPersons){
			meetingPersonServiceI.delete(p.getId());
			meetingPersonDao.getCurrentSession().flush();
			meetingPersonDao.getCurrentSession().clear();
		}
		
		Tuser user = new Tuser() ;
		/**
		 * 将与会人员写入表中
		 * s1:将参数中与会人员查询至用户列表
		 * s2:循环用户列表插入至与会人员表中
		 */
		List<User> users = getUsersFromMeetingPerson(meeting.getMeetingperson()) ;
		for(User p:users){
			TMeetingPerson tmeetingPerson = new TMeetingPerson() ;
			tmeetingPerson.setMeetingList(meetingList);
			user.setId(p.getId());
			tmeetingPerson.setUser(user);
			
			meetingDao.delete(meetingList);
			
			meetingPersonDao.save(tmeetingPerson);
			meetingPersonDao.getCurrentSession().clear();		
		}
	}
 
	@Override
	public void delete(Long id) {
		TMeetingList t = meetingDao.get(TMeetingList.class, id);
		meetingDao.delete(t);
	}
	
	@Override
	public void add(Meeting meeting) {
		TMeetingList meetingList = new TMeetingList() ;
		BeanUtils.copyProperties(meeting, meetingList);
		Tuser user = new Tuser();
		user.setId(meeting.getUserid());
		user.setName(meeting.getUsername());
		meetingList.setUser(user);
		meetingDao.save(meetingList) ;
		
		/**
		 * 将与会人员写入表中
		 * s1:将参数中与会人员查询至用户列表
		 * s2:循环用户列表插入至与会人员表中
		 */
		List<User> users = getUsersFromMeetingPerson(meeting.getMeetingperson()) ;
		for(User p:users){
			TMeetingPerson meetingPerson = new TMeetingPerson() ;
			meetingPerson.setMeetingList(meetingList);
			user.setId(p.getId());
			meetingPerson.setUser(user);
			meetingPersonDao.save(meetingPerson);
			meetingPersonDao.getCurrentSession().clear();		
		}
	}
	
	/**
	 * 
	* @Title: getUsersFromMeetingPerson 
	* @Description: 返回参数中逗号(,|，)分割的用户信息
	* @param meetingPerson
	* @return List<User>
	* @throws
	 */
	public List<User> getUsersFromMeetingPerson(String meetingPerson){
		String [] person = null ;
		if(meetingPerson.contains("，"))
			person = meetingPerson.split("，");
		else {
			person = meetingPerson.split(",");
		}
		
		User user = new User();
		PageFilter ph = new PageFilter();
		List<User> users = new LinkedList<>() ;
		for(String p:person){
			user.setName(p);
			users.addAll(userServiceI.dataGrid(user, ph));
		}
		return users ;
	}

}
