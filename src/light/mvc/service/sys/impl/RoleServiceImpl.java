package light.mvc.service.sys.impl;

import java.util.ArrayList;
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
import light.mvc.pageModel.base.Tree;
import light.mvc.pageModel.sys.Role;
import light.mvc.service.sys.RoleServiceI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleServiceI {
	

	@Autowired
	private BaseDaoI<Trole> roleDao;
	
	@Autowired
	private BaseDaoI<Tuser> userDao;
	
	@Autowired
	private BaseDaoI<Tresource> resourceDao;
	
	@Autowired
	private BaseDaoI<Torganization> organizationDao;
	
	@Override
	public void add(Role r,boolean flag) {
		Trole t = new Trole();
		//获取organizationId   setOrganization01在Trole类中设置了对应的 数据库表名以及期字段
		t.setOrganization(organizationDao.get(Torganization.class, r.getOrganizationId()));
		
		t.setIsdefault(GlobalConstant.NOT_DEFAULT);
		t.setName(r.getName());
		t.setRoleId(r.getRoleId());
		t.setSeq(r.getSeq());
		t.setDescription(r.getDescription());
		roleDao.save(t);
	}
	
	@Override
	public void delete(Long id) {
		Trole t = roleDao.get(Trole.class, id);
		roleDao.delete(t);
	}
	
	@Override
	public void edit(Role r) {
		Trole t = roleDao.get(Trole.class, r.getId());
		t.setDescription(r.getDescription());
		t.setName(r.getName());
		t.setRoleId(r.getRoleId());
		t.setSeq(r.getSeq());
		t.setOrganization(organizationDao.get(Torganization.class, r.getOrganizationId()));
		roleDao.update(t);
	}
	
	@Override
	//用户获得的资源权限
	public Role getList(Long  userId) {
		Role r = new Role();
		Tuser t = userDao.get(Tuser.class, userId);
		Set<Trole> roleList = t.getRoles();
		String ids = "";
		String names = "";
		for(Trole role:roleList){
			
			Set<Tresource> s = role.getResources();
			if ((s != null) && !s.isEmpty()) {
				boolean b = false;
				for (Tresource tr : s) {
					if (b) {
						ids += ",";
						names += ",";
					} else {
						b = true;
					}
					ids += tr.getId();
					names += tr.getName();
				}
			
			}
		}
		r.setResourceIds(ids);
		r.setResourceNames(names);
		return r;
	}
	
	@Override
	public Role get(Long id) {
		
		Trole t = roleDao.get(Trole.class, id);
		Role r = new Role();
		r.setDescription(t.getDescription());
		r.setId(t.getId());
		r.setIsdefault(t.getIsdefault());
		r.setName(t.getName());
		r.setRoleId(t.getRoleId());
		r.setSeq(t.getSeq());
		
		//获取organization的Id 与 name 值
		if (t.getOrganization() != null) {
			r.setOrganizationId(t.getOrganization().getId());
			r.setOrganizationName(t.getOrganization().getName());
		}
		
		Set<Tresource> s = t.getResources();
		
		
		if ((s != null) && !s.isEmpty()) {
			boolean b = false;
			String ids = "";
			String names = "";
			for (Tresource tr : s) {
				if (b) {
					ids += ",";
					names += ",";
				} else {
					b = true;
				}
				ids += tr.getId();
				names += tr.getName();
			}
			r.setResourceIds(ids);
			r.setResourceNames(names);
		}
		return r;
	}

	@Override
	public List<Role> dataGrid(Role role, PageFilter ph) {
		List<Role> ul = new ArrayList<Role>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Trole t ";
		List<Trole> l = roleDao.find(hql + whereHql(role, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
		for (Trole t : l) {
			Role u = new Role();
			BeanUtils.copyProperties(t, u);
			
			//添加organization的Id 与 name 值
			if (t.getOrganization() != null) {
				
				u.setOrganizationId(t.getOrganization().getId());
				u.setOrganizationName(t.getOrganization().getName());
			}

			ul.add(u);
		}
		return ul;
	}

	@Override
	public Long count(Role role, PageFilter ph) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Trole t ";
		return roleDao.count("select count(*) " + hql + whereHql(role, params), params);
	}

	private String whereHql(Role role, Map<String, Object> params) {
		String hql = "";
		if (role != null) {
			hql += " where 1=1 ";
			if (role.getName() != null) {
				hql += " and t.name like :name";
				params.put("name", "%%" + role.getName() + "%%");
			}
			if (role.getOrganizationId() != null) {
				hql += " and t.organization.id =" + role.getOrganizationId();
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
	public void grant(Role role) {
		Trole t = roleDao.get(Trole.class, role.getId());
		if ((role.getResourceIds() != null) && !role.getResourceIds().equalsIgnoreCase("")) {
			String ids = "";
			boolean b = false;
			for (String id : role.getResourceIds().split(",")) {
				if (b) {
					ids += ",";
				} else {
					b = true;
				}
				ids += id;
			}
			HashSet<Tresource> rs = new HashSet<Tresource>(resourceDao.find("select distinct t from Tresource t where t.id in ("
					+ ids + ")"));
			t.setResources(rs);
		} else {
			t.setResources(null);
		}
	}
	
	@Override
	public List<Tree> tree() {
		List<Trole> l = null;
		List<Tree> lt = new ArrayList<Tree>();

		l = roleDao.find("select distinct t from Trole t order by t.seq");

		if ((l != null) && (l.size() > 0)) {
			for (Trole r : l) {
				Tree tree = new Tree();
				tree.setId(r.getId().toString());
				tree.setText(r.getName());
				lt.add(tree);
			}
		}
		return lt;
	}
	
	public List<Tree> treeRoleid() {	//取角色roleID,用于activiti同步
		List<Trole> l = null;
		List<Tree> lt = new ArrayList<Tree>();

		l = roleDao.find("select distinct t from Trole t order by t.seq");

		if ((l != null) && (l.size() > 0)) {
			for (Trole r : l) {
				Tree tree = new Tree();
				tree.setId(r.getRoleId().toString());//htu7 20170410
				tree.setText(r.getName());
				lt.add(tree);
			}
		}
		return lt;
	}
				
	@Override
	public Role getRoleId(Role role) {
		Trole t = roleDao.get("from Trole t  where t.roleId = '" + role.getRoleId() + "'");
		Role r = new Role();
		if (t != null) {
			BeanUtils.copyProperties(t, r);
		} else {
			return null;
		}
		return r;
	}
	
	
}
