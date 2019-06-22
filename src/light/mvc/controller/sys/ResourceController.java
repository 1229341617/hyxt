package light.mvc.controller.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import light.mvc.controller.base.BaseController;
import light.mvc.framework.constant.GlobalConstant;
import light.mvc.pageModel.base.Json;
import light.mvc.pageModel.base.SessionInfo;
import light.mvc.pageModel.base.Tree;
import light.mvc.pageModel.sys.Resource;
import light.mvc.service.sys.ResourceServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/resource")
public class ResourceController extends BaseController {

	@Autowired
	private ResourceServiceI resourceService;

	@RequestMapping("/manager")
	public String manager() {
		return "/admin/resource";
	}

	@RequestMapping("/newtree")
	@ResponseBody
	public List<Tree> newtree(HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(GlobalConstant.SESSION_INFO);
		return resourceService.newtree(sessionInfo);
	}
	@RequestMapping("/tree")
	@ResponseBody
	public List<Tree> tree(HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(GlobalConstant.SESSION_INFO);
		return resourceService.tree(sessionInfo,Long.valueOf("1"));
	}
	@RequestMapping("/tree1")
	@ResponseBody
	public List<Tree> tree1(HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(GlobalConstant.SESSION_INFO);
		return resourceService.tree(sessionInfo,Long.valueOf("587"));
	}
	@RequestMapping("/tree2")
	@ResponseBody
	public List<Tree> tree2(HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(GlobalConstant.SESSION_INFO);
		return resourceService.tree(sessionInfo,Long.valueOf("585"));
	}
	@RequestMapping("/tree3")
	@ResponseBody
	public List<Tree> tree3(HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(GlobalConstant.SESSION_INFO);
		return resourceService.tree(sessionInfo,Long.valueOf("590"));
	}
	@RequestMapping("/tree4")
	@ResponseBody
	public List<Tree> tree4(HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(GlobalConstant.SESSION_INFO);
		return resourceService.tree(sessionInfo,Long.valueOf("589"));
	}
	@RequestMapping("/allTree")
	@ResponseBody
	public List<Tree> allTree(boolean flag) {// true获取全部资源,false只获取菜单资源
		return resourceService.listAllTree(flag);
	}

	@RequestMapping("/allTree1")
	@ResponseBody
	public List<Tree> allTree1(boolean flag) {// true获取全部资源,false只获取菜单资源
		return resourceService.listAllTree1(flag);
	}

	@RequestMapping("/treeGrid")
	@ResponseBody
	public List<Resource> treeGrid() {
		return resourceService.treeGrid();
	}

	@RequestMapping("/get")
	@ResponseBody
	public Resource get(Long id) {
		return resourceService.get(id);
	}

	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		Resource r = resourceService.get(id);
		request.setAttribute("resource", r);
		return "/admin/resourceEdit";
	}

	@RequestMapping("/edit")
	@Transactional(readOnly = false, rollbackFor = DataAccessException.class)
	@ResponseBody
	public Json edit(Resource resource) throws InterruptedException {
		Json j = new Json();
		try {
			resourceService.edit(resource);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (Exception e) {
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
			resourceService.delete(id);
			j.setMsg("删除成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	@RequestMapping("/addPage")
	public String addPage() {
		return "/admin/resourceAdd";
	}

	@RequestMapping("/add")
	@Transactional(readOnly = false, rollbackFor = DataAccessException.class)
	@ResponseBody
	public Json add(Resource resource) {
		Json j = new Json();
		try {
			resourceService.add(resource);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	@RequestMapping("/collection")
	@ResponseBody
	public Json collection(HttpServletRequest request,Long resource_id) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		
		Json j = new Json();
		try {
			resourceService.collection(sessionInfo, resource_id);
			//roleService.grant(role);
			j.setMsg("收藏成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	@RequestMapping("/cancelcollection")
	@ResponseBody
	public Json cancelcollection(HttpServletRequest request,Long resource_id) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		Json j = new Json();
		try {
			resourceService.cancelcollection(sessionInfo, resource_id);
			j.setMsg("取消收藏成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	
	@RequestMapping("/manager1")
	public String manager1() {
		return "/workflow/Activiti/process/test2";
	}
	@RequestMapping("/getTreeNode")
	@ResponseBody
	public List<Tree> getTreeNode(String tabsId,String id) {// true获取全部资源,false只获取菜单资源
		//return resourceService.listAllTree1(flag);
		
		List<Tree> list = resourceService.getTreeNode(tabsId, id);
		
		return list;
	}
	@RequestMapping("/test2")
	public void test2() {
		//return "/workflow/Activiti/process/test2";
		//this.resourceService.CopyProjectProceDure("name");
		this.resourceService.DeleteProjectProceDure(9995L);
	}
	
	
}
