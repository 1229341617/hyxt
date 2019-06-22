package light.mvc.service.hyxt;

import java.util.List;

import light.mvc.pageModel.hyxt.MeetingPerson;

public interface MeetingPersonServiceI {
	public List<MeetingPerson> dataGrid(MeetingPerson meetingPerson);


	MeetingPerson get(Long id);


	/** 
	* @Title: delete 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param id void
	* @throws 
	*/
	void delete(Long id);


	public List<MeetingPerson> findMeetingsByUserId(Long id);

}
