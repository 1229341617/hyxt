package light.mvc.service.sys;

import java.util.List;

import light.mvc.pageModel.base.SessionInfo;
import light.mvc.pageModel.base.Tree;
import light.mvc.pageModel.sys.Resource;

public interface ResourceServiceI {

	public List<Resource> treeGrid();

	public void add(Resource resource);

	public void delete(Long id);

	public void edit(Resource resource);

	public Resource get(Long id);

	public List<Tree> newtree(SessionInfo sessionInfo);
	
	public List<Tree> tree(SessionInfo sessionInfo,Long pId);

	public List<Tree> listAllTree(boolean flag);

	public List<Tree> listAllTree1(boolean flag);
	
	public List<String> listAllResource();
	
	public void collection(SessionInfo sessionInfo,Long resource_id);
	public void cancelcollection(SessionInfo sessionInfo,Long resource_id);
	
	
	public List<Tree> getTreeNode(String tabsId,String id);
	
	public void CopyProjectProceDure(String name,int project_code);
	
	public void DeleteProjectProceDure(Long id);
}
