package light.mvc.service.hyxt;

import java.util.List;

import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.hyxt.Meeting;

public interface MeetingServiceI {
	public List<Meeting> dataGrid(Meeting meeting, PageFilter ph);

	public Long count(Meeting meeting, PageFilter ph);

	public void edit(Meeting meeting);

 
	public void delete(Long id);

	void add(Meeting meeting);

	/** 
	* @Title: get 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param id
	* @return Meeting
	* @throws 
	*/
	Meeting get(Long id);

}
