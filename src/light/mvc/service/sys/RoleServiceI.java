package light.mvc.service.sys;

import java.util.List;

import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.base.Tree;
import light.mvc.pageModel.sys.Role;

public interface RoleServiceI {

	public List<Role> dataGrid(Role role, PageFilter ph);

	public Long count(Role role, PageFilter ph);

	public void add(Role role,boolean flag);

	public void delete(Long id);

	public void edit(Role role);

	public Role get(Long id);
	
	public Role getList(Long  userId);
	
	public void grant(Role role);

	public List<Tree> tree();
	
	public List<Tree> treeRoleid();
	
	public Role getRoleId(Role role);
	

}
