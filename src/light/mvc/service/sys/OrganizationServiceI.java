package light.mvc.service.sys;

import java.util.List;

import light.mvc.pageModel.base.Tree;
import light.mvc.pageModel.sys.Organization;

public interface OrganizationServiceI {

	public List<Organization> treeGrid();

	public void add(Organization organization);
	
	public Boolean IssameAccount(Long deptid,Long deptid2);
	
	public Boolean IsRightdept(Long deptid,Long deptid2);

	public void delete(Long id);

	public void edit(Organization organization);

	public Organization get(Long id);

	public List<Tree> tree();
	
	
	public List<Tree> accounttree();

	public Organization getAccounorgantById(Long id);
	
	public List<Tree> accounttjtree(Long accountId);
	
	public List<Tree> accounttree_select(Long accountId);
}
