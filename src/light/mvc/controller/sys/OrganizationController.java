package light.mvc.controller.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import light.mvc.controller.base.BaseController;
import light.mvc.framework.constant.GlobalConstant;
import light.mvc.pageModel.base.Json;
import light.mvc.pageModel.base.SessionInfo;
import light.mvc.pageModel.base.Tree;
import light.mvc.pageModel.sys.Organization;
import light.mvc.service.base.ServiceException;
import light.mvc.service.sys.OrganizationServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/organization")
public class OrganizationController extends BaseController {

	@Autowired
	private OrganizationServiceI organizationService;
	@Autowired
	private HttpSession session;
	@RequestMapping("/manager")
	public String manager() {
		return "/admin/organization";
	}
	@RequestMapping("/manager2")
	public String manager2() {
		return "gcgl/base/organizationSelect";
	}
	@RequestMapping("/treeGrid")
	@ResponseBody
	public List<Organization> treeGrid() {
		return organizationService.treeGrid();
	}

	@RequestMapping("/tree")
	@ResponseBody
	public List<Tree> tree() {
		return organizationService.tree();
	}
	@RequestMapping("/accounttree")
	@ResponseBody
	public List<Tree> accounttree() {
		return organizationService.accounttree();
	}

	@RequestMapping("/accounttjtree")
	@ResponseBody
	public List<Tree> accounttjtree() {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(GlobalConstant.SESSION_INFO);
		Long accountId = sessionInfo.getAccountId();
		return organizationService.accounttjtree(accountId);
	}
	
	@RequestMapping("/accounttree_select")
	@ResponseBody
	public List<Tree> accounttree_select() {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(GlobalConstant.SESSION_INFO);
		Long accountId = sessionInfo.getAccountId();
		return organizationService.accounttree_select(accountId);
	}
	@RequestMapping("/addPage")
	public String addPage() {
		return "/admin/organizationAdd";
	}

	@RequestMapping("/add")
	@Transactional(readOnly = false, rollbackFor = DataAccessException.class)
	@ResponseBody
	public Json add(Organization organization) {
		Json j = new Json();
		try {
			organizationService.add(organization);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	@RequestMapping("/get")
	@ResponseBody
	public Organization get(Long id) {
		return organizationService.get(id);
	}

	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		Organization o = organizationService.get(id);
		request.setAttribute("organization", o);
		return "/admin/organizationEdit";
	}

	@RequestMapping("/edit")
	@Transactional(readOnly = false, rollbackFor = DataAccessException.class)
	@ResponseBody
	public Json edit(Organization org) throws InterruptedException {
		Json j = new Json();
		try {
			organizationService.edit(org);
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
			organizationService.delete(id);
			j.setMsg("删除成功！");
			j.setSuccess(true);
		} catch (ServiceException e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
}
