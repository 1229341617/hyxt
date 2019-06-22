package light.mvc.service.sys.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import light.mvc.dao.BaseDaoI;
import light.mvc.framework.constant.GlobalConstant;
import light.mvc.model.sys.Torganization;
import light.mvc.model.sys.Tresource;
import light.mvc.model.sys.Trole;
import light.mvc.model.sys.Tuser;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.base.SessionInfo;
import light.mvc.pageModel.base.Tree;
import light.mvc.pageModel.sys.User;
import light.mvc.service.sys.OrganizationServiceI;
import light.mvc.service.sys.UserServiceI;
import light.mvc.utils.MD5Util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserServiceI {
	@Autowired
	private BaseDaoI<Tuser> userDao;

	@Autowired
	private BaseDaoI<Trole> roleDao;

	@Autowired
	private BaseDaoI<Torganization> organizationDao;
	
	@Autowired
	private OrganizationServiceI OrganizationService;
	
	
	@Override
	public void add(User u) {
		Tuser t = new Tuser();
		BeanUtils.copyProperties(u, t);
		t.setOrganization(organizationDao.get(Torganization.class, u.getOrganizationId()));

		List<Trole> roles = new ArrayList<Trole>();
		if (u.getRoleIds() != null) {
			
			for (String roleId : u.getRoleIds().split(",")) {
				Trole tr =roleDao.get(Trole.class, Long.valueOf(roleId));
				roles.add(tr);
			}
		}
		
		t.setRoles(new HashSet<Trole>(roles));

		t.setPassword(MD5Util.md5(u.getPassword()));
		t.setState(GlobalConstant.ENABLE);
		t.setIsdefault(GlobalConstant.DEFAULT);
		t.setCreatedatetime(new Date());
		userDao.save(t);
	}
	
	

	@Override
	public void delete(Long id) {
		Tuser t = userDao.get(Tuser.class, id);
		del(t);
	}

	private void del(Tuser t) {
		userDao.delete(t);
	}

	@Override
	public void edit(User user) {
		Tuser t = userDao.get(Tuser.class, user.getId());
		t.setAge(user.getAge());
		t.setLoginname(user.getLoginname());
		t.setName(user.getName());
		t.setOrganization(organizationDao.get(Torganization.class, user.getOrganizationId()));
		List<Trole> roles = new ArrayList<Trole>();
		if (user.getRoleIds() != null) {
			for (String roleId : user.getRoleIds().split(",")) {
				roles.add(roleDao.get(Trole.class, Long.valueOf(roleId)));
			}
		}
		t.setRoles(new HashSet<Trole>(roles));
		t.setSex(user.getSex());
		t.setUsertype(user.getUsertype());
		t.setPhone(user.getPhone());
		t.setState(user.getState());
		if ((user.getPassword() != null) && !"".equals(user.getPassword())) {
			t.setPassword(MD5Util.md5(user.getPassword()));
		}
		userDao.update(t);
	}
	@Override
	public User getU(Long id){//pxh
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Tuser t = userDao.get("from Tuser t  left join fetch t.organization where t.id = :id", params);
		User u = new User();
		BeanUtils.copyProperties(t, u);

		if (t.getOrganization() != null) {
			u.setOrganizationId(t.getOrganization().getId());
			u.setOrganizationName(t.getOrganization().getName());
		}
		return u;
	}

	@Override
	public User get(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Tuser t = userDao.get("from Tuser t  left join fetch t.roles role where t.id = :id", params);
		User u = new User();
		BeanUtils.copyProperties(t, u);

		if (t.getOrganization() != null) {
			u.setOrganizationId(t.getOrganization().getId());
			u.setOrganizationName(t.getOrganization().getName());
		}

		if ((t.getRoles() != null) && !t.getRoles().isEmpty()) {
			String roleIds = "";
			String roleNames = "";
			boolean b = false;
			for (Trole role : t.getRoles()) {
				if (b) {
					roleIds += ",";
					roleNames += ",";
				} else {
					b = true;
				}
				roleIds += role.getId();
				roleNames += role.getName();
			}
			u.setRoleIds(roleIds);
			u.setRoleNames(roleNames);
		}
		return u;
	}

	@Override
	public User login(User user) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginname", user.getLoginname());
		params.put("password", MD5Util.md5(user.getPassword()));
		Tuser t = userDao.get("from Tuser t where t.loginname = :loginname and t.password = :password", params);
		if (t != null) {
			User u = new User();
			BeanUtils.copyProperties(t, u);
			return u;
		}
		return null;
	}

	@Override
	public List<String> listResource(Long id) {
		List<String> resourceList = new ArrayList<String>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Tuser t = userDao.get(
				"from Tuser t join fetch t.roles role join fetch role.resources resource where t.id = :id", params);
		if (t != null) {
			Set<Trole> roles = t.getRoles();
			if ((roles != null) && !roles.isEmpty()) {
				for (Trole role : roles) {
					Set<Tresource> resources = role.getResources();
					if ((resources != null) && !resources.isEmpty()) {
						for (Tresource resource : resources) {
							if ((resource != null) && (resource.getUrl() != null)) {
								resourceList.add(resource.getUrl());
							}
						}
					}
				}
			}
		}
		return resourceList;
	}
	public List<String> listTabsResource(Long id){
		List<String> resourceList = new ArrayList<String>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Tuser t = userDao.get(
				"from Tuser t join fetch t.roles role join fetch role.resources resource where t.id = :id", params);
		if (t != null) {
			Set<Trole> roles = t.getRoles();
			if ((roles != null) && !roles.isEmpty()) {
				for (Trole role : roles) {
					Set<Tresource> resources = role.getResources();
					if ((resources != null) && !resources.isEmpty()) {
						for (Tresource resource : resources) {
							//System.out.println(resource.getResourcetype());
							if(resource.getResourcetype()==2){
								
								resourceList.add(resource.getName());
							}
							/*if ((resource != null) && (resource.getUrl() != null)) {
								resourceList.add(resource.getUrl());
							}*/
						}
					}
				}
			}
		}
		return resourceList;
	}
	@Override
	public List<User> dataGrid_ys(User user, PageFilter ph,String roleid) {
		List<User> ul = new ArrayList<User>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleid", roleid);
		List<Trole> l = roleDao.find("from Trole t  where t.roleId = :roleid ", params);
		for (Trole t : l) {
			Set<Tuser> users = t.getUsers();
			if((users != null)&&(!users.isEmpty())){
				for (Tuser tu : users) {
					User u = new User();
					BeanUtils.copyProperties(tu, u);
					ul.add(u);
				}
			}
		}
		return ul;
	}
	@Override
	public Long count_ys(User user, PageFilter ph,String roleid) {
		Long count=0L;
		List<User> ul = new ArrayList<User>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleid", roleid);
		List<Trole> l = roleDao.find("from Trole t  where t.roleId = :roleid ", params);
		for (Trole t : l) {
			Set<Tuser> users = t.getUsers();
			if((users != null)&&(!users.isEmpty())){
				for (Tuser tu : users) {
					count++;
				}
			}
		}
		return count;
	}
	@Override
	public List<User> dataGrid(User user, PageFilter ph) {
		List<User> ul = new ArrayList<User>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tuser t ";
		List<Tuser> l = userDao.find(hql + whereHql(user, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
		for (Tuser t : l) {
			User u = new User();
			BeanUtils.copyProperties(t, u);
			Set<Trole> roles = t.getRoles();
			if ((roles != null) && !roles.isEmpty()) {
				String roleIds = "";
				String roleNames = "";
				boolean b = false;
				for (Trole tr : roles) {
					if (b) {
						roleIds += ",";
						roleNames += ",";
					} else {
						b = true;
					}
					roleIds += tr.getId();
					roleNames += tr.getName();
				}
				u.setRoleIds(roleIds);
				u.setRoleNames(roleNames);
			}
			if (t.getOrganization() != null) {
				u.setOrganizationId(t.getOrganization().getId());
				u.setOrganizationName(t.getOrganization().getName());
			}
			ul.add(u);
		}
		return ul;
	}

	@Override
	public Long count(User user, PageFilter ph) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tuser t ";
		return userDao.count("select count(*) " + hql + whereHql(user, params), params);
	}

	private String whereHql(User user, Map<String, Object> params) {
		String hql = "";
		if (user != null) {
			hql += " where 1=1 ";
			if (user.getName() != null) {
				hql += " and t.name like :name";
				params.put("name", "%%" + user.getName() + "%%");
			}
			if (user.getCreatedatetimeStart() != null) {
				hql += " and t.createdatetime >= :createdatetimeStart";
				params.put("createdatetimeStart", user.getCreatedatetimeStart());
			}
			if (user.getCreatedatetimeEnd() != null) {
				hql += " and t.createdatetime <= :createdatetimeEnd";
				params.put("createdatetimeEnd", user.getCreatedatetimeEnd());
			}
			if (user.getOrganizationId() != null) {
				hql += " and t.organization.id =" + user.getOrganizationId();
			}

			if (user.getPhone() != null) {
				hql += " and t.phone like :phone";
				params.put("phone","%%" + user.getPhone() + "%%");
			}
		}
		return hql;
	}

	private String orderHql(PageFilter ph) {
		String orderString = "";
		if ((ph.getSort() != null) && (ph.getOrder() != null)) {
			orderString = " order by t." + ph.getSort() + " " + ph.getOrder();
		}
		return orderString;
	}

	@Override
	public boolean editUserPwd(SessionInfo sessionInfo, String oldPwd, String pwd) {
		Tuser u = userDao.get(Tuser.class, sessionInfo.getId());
		if (u.getPassword().equalsIgnoreCase(MD5Util.md5(oldPwd))) {// 说明原密码输入正确
			u.setPassword(MD5Util.md5(pwd));
			return true;
		}
		return false;
	}

	@Override
	public User getByLoginName(String loginname) {
		Tuser t = userDao.get("from Tuser t  where t.loginname = '" + loginname + "'");
		User u = new User();
		if (t != null) {
			BeanUtils.copyProperties(t, u);
		} else {
			return null;
		}
		return u;
	}
	
	@Override
	public List<User> getUserListByUserType() {
		List<Tuser> list = userDao.find("from Tuser t where t.usertype=1 and t.state=0");
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < list.size(); i++) {
			User u = new User();
			BeanUtils.copyProperties(list.get(i), u);
			users.add(u);
		}
		return users;
	}

	@Override
	public String[] getUserListNameByUserType() {
		List<Tuser> list = userDao.find("from Tuser t where t.usertype=1 and t.state=0");
		String[] users = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			users[i] = list.get(i).getName();
		}
		return users;
	}

	@Override
	public List<Tree> getvloginnametree() {
		List<Tuser> l = userDao.find("from Tuser t where t.usertype=2 and t.state=0");
		List<Tree> lt = new ArrayList<Tree>();

		if ((l != null) && (l.size() > 0)) {
			for (Tuser r : l) {
				Tree tree = new Tree();
				tree.setId(r.getLoginname().toString());
				tree.setText(r.getName());
				lt.add(tree);
			}
		}
		return lt;
	}
/*	
 * 单据userlogin发起人用户登陆名,userlogin2登陆人用户登陆名
 * 适用范围:单位层级为两级
 	//userlogin2如果是资产公司,返回true,
	//如果userlogin2与发起人是相同单位返回true
	//如果发起人是自己,也返回true
	//如果用户是admin,返回false
*/	
	public Boolean	IsQuerybillright(String userlogin,String userlogin2){
		Boolean isright=false;
		if (userlogin2.equalsIgnoreCase("admin")){//如果是管理员账号
			isright=false;
		}else if (userlogin.equals(userlogin2))//如果是自己
			isright=true;
			else {//如果是资产公司或是相同子公司
				Tuser t = userDao.get("from Tuser t  where t.loginname = '" + userlogin + "'");
				Long deptid = 0L,deptid2 = 0L;
				if (t != null) {
					deptid = t.getOrganization().getId();//发起人部门
				}
				Tuser t2 = userDao.get("from Tuser t  where t.loginname = '" + userlogin2 + "'");
				if (t2 != null) {
					deptid2 = t2.getOrganization().getId();//登陆部门
				}
				isright=OrganizationService.IsRightdept(deptid, deptid2);
			}
		return isright;
	}
	/*	
	 * 单据userid发起人用户id,userid2登陆人用户id
	 * 适用范围:单位层级为两级
	 	//userid2如果是资产公司,返回true,
		//如果userid2与发起人是相同单位返回true
		//如果发起人是自己,也返回true
		//如果用户是1(admin),返回false
	*/	
		public Boolean	IsQuerybillright(Integer userid,Integer userid2){
			Boolean isright=false;
			if (userid2==1){//如果是管理员账号
				isright=false;
			}else if (userid==userid2)//如果是自己
				isright=true;
				else {//如果是资产公司或是相同子公司
					Tuser t = userDao.get("from Tuser t  where t.id = " + userid.toString());
					Long deptid = 0L,deptid2 = 0L;
					if (t != null) {
						deptid = t.getOrganization().getId();//发起人部门
					}
					Tuser t2 = userDao.get("from Tuser t  where t.id = " + userid2.toString());
					if (t2 != null) {
						deptid2 = t2.getOrganization().getId();//登陆部门
					}
					isright=OrganizationService.IsRightdept(deptid, deptid2);
				}
			return isright;
		}

		
		
		
		
		
		
		
		
		
		
		
		

}
