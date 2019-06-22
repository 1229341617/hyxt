package light.mvc.controller.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import light.mvc.controller.base.BaseController;
import light.mvc.pageModel.base.Grid;
import light.mvc.pageModel.base.Json;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.base.Tree;
import light.mvc.pageModel.sys.Role;
import light.mvc.service.sys.RoleServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

	@Autowired
	private RoleServiceI roleService;
	
	@RequestMapping("/manager")
	public String manager() {
		return "/admin/role";
	}

	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(Role role, PageFilter ph) {
		Grid grid = new Grid();
		grid.setRows(roleService.dataGrid(role, ph));
		grid.setTotal(roleService.count(role, ph));
		return grid;
	}
	
	@RequestMapping("/tree")
	@ResponseBody
	public List<Tree> tree() {
		return roleService.tree();
	}
	
	@RequestMapping("/treeRoleid")
	@ResponseBody
	public List<Tree> treeRoleid() {
		return roleService.treeRoleid();
	}
	
	@RequestMapping("/addPage")
	public String addPage() {
		return "/admin/roleAdd";
	}

	@RequestMapping("/add")
	@Transactional(readOnly = false, rollbackFor = DataAccessException.class)
	@ResponseBody
	public Json add(Role role) {
		Json j = new Json();
		Role r = roleService.getRoleId(role);
		try {
			if(r != null){
				roleService.add(role,false);
			}else{
				roleService.add(role,true);
			}
			j.setSuccess(true);
			j.setMsg("添加成功！");
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
			roleService.delete(id);
			j.setMsg("删除成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	@RequestMapping("/get")
	@ResponseBody
	public Role get(Long id)  {
		return roleService.get(id);
	}
	
	@RequestMapping("/getList")
	@ResponseBody
	public Role getList(Long userId)  {
		//SessionInfo sessionInfo = (SessionInfo) session.getAttribute(GlobalConstant.SESSION_INFO);
		//Long userId = sessionInfo.getId();	
		return roleService.getList(userId);
	}
	
	
	
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		Role r = roleService.get(id);
		
		request.setAttribute("role", r);
		return "/admin/roleEdit";
	}

	@RequestMapping("/edit")
	@Transactional(readOnly = false, rollbackFor = DataAccessException.class)
	@ResponseBody
	public Json edit(Role role) {
		Json j = new Json();
		try {
			roleService.edit(role);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	@RequestMapping("/grantPage")
	public String grantPage(HttpServletRequest request, Long id) {
		Role r = roleService.get(id);
		request.setAttribute("role", r);
		return "/admin/roleGrant";
	}

	@RequestMapping("/grant")
	@ResponseBody
	public Json grant(Role role) {
		Json j = new Json();
		try {
			roleService.grant(role);
			j.setMsg("授权成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

}
