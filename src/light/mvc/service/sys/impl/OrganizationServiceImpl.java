package light.mvc.service.sys.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import light.mvc.dao.BaseDaoI;
import light.mvc.model.sys.Torganization;
import light.mvc.model.sys.Trole;
import light.mvc.model.sys.Tuser;
import light.mvc.pageModel.base.Tree;
import light.mvc.pageModel.sys.Organization;
import light.mvc.service.base.ServiceException;
import light.mvc.service.sys.OrganizationServiceI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationServiceI {
	
	@Autowired
	private BaseDaoI<Tuser> userDao;
	@Autowired
	private BaseDaoI<Trole> roleDao;

	@Autowired
	private BaseDaoI<Torganization> organizationDao;

	@Override
	public List<Organization> treeGrid() {
		List<Organization> lr = new ArrayList<Organization>();
		List<Torganization> l = organizationDao
				.find("from Torganization t left join fetch t.organization  order by t.seq");
		if ((l != null) && (l.size() > 0)) {
			for (Torganization t : l) {
				Organization r = new Organization();
				BeanUtils.copyProperties(t, r);
				if (t.getOrganization() != null) {
					r.setPid(t.getOrganization().getId());
					r.setPname(t.getOrganization().getName());
				}
				r.setIconCls(t.getIcon());
				lr.add(r);
			}
		}
		return lr;
	}

	@Override
	public void add(Organization org) {
		Torganization t = new Torganization();
		BeanUtils.copyProperties(org, t);
		if ((org.getPid() != null) && !"".equals(org.getPid())) {
			t.setOrganization(organizationDao.get(Torganization.class, org.getPid()));
		}
		t.setCreatedatetime(new Date());
		organizationDao.save(t);
	}

	@Override
	public void delete(Long id) {
		Torganization t = organizationDao.get(Torganization.class, id);
		del(t);
	}

	private void del(Torganization t) {
		List<Tuser> list = userDao.find("from Tuser t left join t.organization org where org.id="+t.getId());
		List<Trole> list2 = roleDao.find("from Trole t left join t.organization org where org.id="+t.getId());
		if(list!=null&&list.size()>0){
			throw new ServiceException("该部门已经被用户使用");
		}else if(list2!=null&&list2.size()>0){
			throw new ServiceException("该部门已经被角色使用");
		}{
			if ((t.getOrganizations() != null) && (t.getOrganizations().size() > 0)) {
				for (Torganization r : t.getOrganizations()) {
					del(r);
				}
			}
			organizationDao.delete(t);
		}
	}

	@Override
	public void edit(Organization r) {
		Torganization t = organizationDao.get(Torganization.class, r.getId());
		t.setCode(r.getCode());
		t.setIcon(r.getIcon());
		t.setName(r.getName());
		t.setShortname(r.getShortname());
		t.setSeq(r.getSeq());
		if ((r.getPid() != null) && !"".equals(r.getPid())) {
			t.setOrganization(organizationDao.get(Torganization.class, r.getPid()));
		}
		organizationDao.update(t);
	}

	@Override
	public Organization get(Long id) {
		Torganization t = organizationDao.get(Torganization.class, id);
		Organization r = new Organization();
		BeanUtils.copyProperties(t, r);
		if (t.getOrganization() != null) {
			r.setPid(t.getOrganization().getId());
			r.setPname(t.getOrganization().getName());
			r.setShortname(t.getShortname());
		}
		return r;
	}
	public List<Tree> accounttree(){
		List<Torganization> l = null;
		List<Tree> lt = new ArrayList<Tree>();

		l = organizationDao.find("select distinct t from Torganization t where t.isaccount=1 order by t.seq");

		if ((l != null) && (l.size() > 0)) {
			for (Torganization r : l) {
				Tree tree = new Tree();
				tree.setId(r.getId().toString());
				tree.setText(r.getName());
				tree.setIconCls(r.getIcon());
				lt.add(tree);
			}
		}
		return lt;
	}
	public List<Tree> accounttree_select(Long accountId){//1L代表资产公司，否则代表子公司
		List<Torganization> l = null;
		List<Tree> lt = new ArrayList<Tree>();

		String sql="select distinct t from Torganization t where t.isaccount=1";
		l = organizationDao.find(sql+ " order by t.seq");
		if ((l != null) && (l.size() > 0)) {
			for (Torganization r : l) {

				if(r.getId() == accountId)
				{
						Tree tree = new Tree();
						tree.setId(r.getId().toString());
						tree.setText(r.getName());
						tree.setIconCls(r.getIcon());
						lt.add(tree);
					}
				}
			}
		return lt;
	}
	public List<Tree> accounttjtree(Long accountId){//1L代表资产公司，否则代表子公司
		List<Torganization> l = null;
		List<Tree> lt = new ArrayList<Tree>();
		Boolean canChoose = false;
		if(accountId == 1L){
			Tree tree = new Tree();
			tree.setId("-1");
			tree.setText("所有");
			tree.setIconCls("");
			lt.add(tree);
		}
		String sql="select distinct t from Torganization t where t.isaccount=1";
		l = organizationDao.find(sql+ " order by t.seq");
		if ((l != null) && (l.size() > 0)) {
			for (Torganization r : l) {
				if(accountId == 1L)
					canChoose = true;
				else
					canChoose = false;
				if(r.getId() == accountId)
					canChoose = true;
				if(canChoose){
						Tree tree = new Tree();
						tree.setId(r.getId().toString());
						tree.setText(r.getName());
						tree.setIconCls(r.getIcon());
						lt.add(tree);
					}
				}
			}
		return lt;
	}
	@Override
	public List<Tree> tree() {
		List<Torganization> l = null;
		List<Tree> lt = new ArrayList<Tree>();

		l = organizationDao.find("select distinct t from Torganization t order by t.seq");

		if ((l != null) && (l.size() > 0)) {
			for (Torganization r : l) {
				Tree tree = new Tree();
				tree.setId(r.getId().toString());
				if (r.getOrganization() != null) {
					tree.setPid(r.getOrganization().getId().toString());
				}
				tree.setText(r.getName());
				tree.setIconCls(r.getIcon());
				lt.add(tree);
			}
		}
		return lt;
	}
	public Organization getAccounorgantById(Long id){
		Torganization t = organizationDao.get(Torganization.class, id);
		
		Torganization tp = organizationDao.get(Torganization.class, t.getOrganization().getId());
		Organization r = new Organization();
		if(t.getIsaccount() == 1) {//帐号单位 
			BeanUtils.copyProperties(t, r);
			if (t.getOrganization() != null) {
				r.setPid(t.getOrganization().getId());
				r.setPname(t.getOrganization().getName());
				
			}
		}
		else{
			BeanUtils.copyProperties(tp, r);
			if (tp.getOrganization() != null) {
				r.setPid(tp.getOrganization().getId());
				r.setPname(tp.getOrganization().getName());
				
			}
		}
		
		
		return r;
	}
	
	public Boolean IssameAccount(Long deptid,Long deptid2){
		Long AccountId2 = null,AccountPId,AccountId = null;
		if(getAccounorgantById(deptid) !=null){
			AccountPId =  get(deptid).getPid();//取发起人的核算单位
			if (AccountPId == 1L)//发起人是资产公司
				return true;
			else{
				AccountId =  getAccounorgantById(deptid).getId();
				if(getAccounorgantById(deptid2)!=null)
					AccountId2 =  getAccounorgantById(deptid2).getId();//取帐号2的核算单位
				
				if ((AccountId2 == AccountId)||(AccountId == 1L))//如果是一个子公司
					return true;
				else
					return false;
			}
		}
		return false;
		
	}

	public Boolean IsRightdept(Long deptid,Long deptid2){//deptid发起人部门,deptid2登录部门
		Long AccountId2 = null,AccountId = null;
		if(getAccounorgantById(deptid2) !=null){//取登录部门单位
			AccountId2 = get(deptid2).getPid();
			if (AccountId2 == 1L){//登录部门是资产公司
				return true;
			}else if(getAccounorgantById(deptid)!=null){
				AccountId =  getAccounorgantById(deptid).getId();//取发起人的核算单位
				if (AccountId2 == AccountId)//如果是一个子公司
					return true;
				else
					return false;
			}
		}
		return false;
	}
}
