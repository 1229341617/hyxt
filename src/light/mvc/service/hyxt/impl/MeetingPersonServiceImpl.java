package light.mvc.service.hyxt.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import light.mvc.dao.BaseDaoI;
import light.mvc.model.hyxt.TMeetingPerson;
import light.mvc.model.sys.Tuser;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.hyxt.MeetingPerson;
import light.mvc.pageModel.sys.User;
import light.mvc.service.hyxt.MeetingPersonServiceI;
import light.mvc.service.sys.UserServiceI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeetingPersonServiceImpl implements MeetingPersonServiceI{
	
	@Autowired
	BaseDaoI<TMeetingPerson> meetingPersonDao ;
 
	@Autowired
	UserServiceI userServiceI ;
	
	@Override
	public List<MeetingPerson> dataGrid(MeetingPerson meetingPerson) {
		List<MeetingPerson> ul = new ArrayList<MeetingPerson>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from TMeetingPerson t ";
		
		List<TMeetingPerson> l = meetingPersonDao.find(hql + whereHql(meetingPerson, params) , params);
		
		for (TMeetingPerson t : l) {
			MeetingPerson u = new MeetingPerson();
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
	public MeetingPerson get(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TMeetingPerson t = meetingPersonDao.get("from TMeetingPerson t  where t.id = :id", params);
		MeetingPerson u = new MeetingPerson();
		BeanUtils.copyProperties(t, u);
		Tuser user = t.getUser();
		if(user != null){
			u.setUserid(user.getId());
			u.setUsername(user.getName());
		}
		return u;
	}
	
	private String whereHql(MeetingPerson meetingPerson, Map<String, Object> params) {
		String hql = "";
		if (meetingPerson != null) {
			hql += "where 1 = 1 " ;
			if (meetingPerson.getId() != null) {
				hql += " and t.meetingList.id = :id";
				params.put("id",  meetingPerson.getId() );
			}
			if (meetingPerson.getUserid() != null ) {
				hql += " and t.user.name like :username" ;
				params.put("username", meetingPerson.getUserid());
			}
			if (meetingPerson.getUsername() != null && !meetingPerson.getUsername().equals("") ) {
				hql += " and t.user.name like :username" ;
				params.put("username", meetingPerson.getUsername());
			}
		}
		return hql;
	}

	@Override
	public void delete(Long id) {
		TMeetingPerson t = meetingPersonDao.get(TMeetingPerson.class, id);
		meetingPersonDao.delete(t);
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
	
	@Override
	public List<MeetingPerson> findMeetingsByUserId(Long id) {
		List<MeetingPerson> ml = new ArrayList<>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		List<TMeetingPerson> ts = meetingPersonDao.find("from TMeetingPerson t  where t.user.id = :id", params);
		for (TMeetingPerson t : ts) {
			MeetingPerson m = new MeetingPerson();
			m.setMeetingid(t.getMeetingList().getId());
			ml.add(m);
		}
		return ml;
	}

}
