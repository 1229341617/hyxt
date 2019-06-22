package light.mvc.service.sys;

import java.util.List;

import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.base.SessionInfo;
import light.mvc.pageModel.base.Tree;
import light.mvc.pageModel.sys.User;

public interface UserServiceI {
	public List<User> dataGrid(User user, PageFilter ph);

	public Long count(User user, PageFilter ph);
	public List<User> dataGrid_ys(User user, PageFilter ph,String role);
	public Long count_ys(User user, PageFilter ph,String role);
	public void add(User user);

	public void delete(Long id);

	public void edit(User user);

	public User get(Long id);
	public User getU(Long id);
	public User login(User user);
	public List<String> listResource(Long id);
	
	public List<String> listTabsResource(Long id);

	public boolean editUserPwd(SessionInfo sessionInfo, String oldPwd, String pwd);

	public User getByLoginName(String loginname);

	public List<User> getUserListByUserType();

	public String[] getUserListNameByUserType();
	
	public List<Tree> getvloginnametree();
	
	public Boolean	IsQuerybillright(String userid,String userid2);
	
	public Boolean	IsQuerybillright(Integer userid,Integer userid2);
}
