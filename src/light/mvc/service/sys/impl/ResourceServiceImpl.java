package light.mvc.service.sys.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import light.mvc.dao.BaseDaoI;
import light.mvc.model.sys.Tresource;
import light.mvc.model.sys.Tuser;
import light.mvc.pageModel.base.SessionInfo;
import light.mvc.pageModel.base.Tree;
import light.mvc.pageModel.sys.Resource;
import light.mvc.service.sys.ResourceServiceI;

import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Service;



@Service
public class ResourceServiceImpl implements ResourceServiceI {

	@Autowired
	private BaseDaoI<Tresource> resourceDao;
	@Autowired
	private BaseDaoI<Tuser> userDao;
	@Autowired
	private SessionFactory sessionFactory;
	@Override
	public List<Resource> treeGrid() {
		List<Resource> lr = new ArrayList<Resource>();
		List<Tresource> l = resourceDao
				.find("select distinct t from Tresource t left join fetch t.resource  order by t.seq");
		if ((l != null) && (l.size() > 0)) {
			for (Tresource t : l) {
				Resource r = new Resource();
				BeanUtils.copyProperties(t, r);
				r.setCstate(t.getState());
				if (t.getResource() != null) {
					r.setPid(t.getResource().getId());
				}
				r.setIconCls(t.getIcon());
				lr.add(r);
			}
		}
		return lr;
	}
	
	@Override
	public void add(Resource r) {
		Tresource t = new Tresource();
		t.setCreatedatetime(new Date());
		t.setDescription(r.getDescription());
		t.setIcon(r.getIcon());
		t.setName(r.getName());
		if ((r.getPid() != null) && !"".equals(r.getPid())) {
			t.setResource(resourceDao.get(Tresource.class, r.getPid()));
		}
		t.setResourcetype(r.getResourcetype());
		t.setSeq(r.getSeq());
		t.setState(r.getCstate());
		t.setUrl(r.getUrl());
		resourceDao.save(t);
	}

	@Override
	public void delete(Long id) {
		Tresource t = resourceDao.get(Tresource.class, id);
		del(t);
	}

	private void del(Tresource t) {
		if ((t.getResources() != null) && (t.getResources().size() > 0)) {
			for (Tresource r : t.getResources()) {
				del(r);
			}
		}
		resourceDao.delete(t);
	}

	@Override
	public void edit(Resource r) {
		Tresource t = resourceDao.get(Tresource.class, r.getId());
		t.setDescription(r.getDescription());
		t.setIcon(r.getIcon());
		t.setName(r.getName());
		if ((r.getPid() != null) && !"".equals(r.getPid())) {
			t.setResource(resourceDao.get(Tresource.class, r.getPid()));
		}
		t.setResourcetype(r.getResourcetype());
		t.setSeq(r.getSeq());
		t.setState(r.getCstate());
		t.setUrl(r.getUrl());
		t.setDescription(r.getDescription());
		resourceDao.update(t);
	}

	@Override
	public Resource get(Long id) {
		Tresource t = resourceDao.get(Tresource.class, id);
		Resource r = new Resource();
		BeanUtils.copyProperties(t, r);
		r.setCstate(t.getState());
		if (t.getResource() != null) {
			r.setPid(t.getResource().getId());
			r.setPname(t.getResource().getName());
		}
		return r;
	}

	@Override
	public List<Tree> tree(SessionInfo sessionInfo,Long pId) {
		
		if (sessionInfo == null) {
			return null;
		}
		List<Tree> lt = new ArrayList<Tree>();
		List<Tresource> menuList = new ArrayList();
		List<Tresource> li  = treeMenuList(sessionInfo,menuList,Long.valueOf(pId));
		if ((li != null) && (li.size() > 0)) {
			
			//System.out.println(list8.size());
			for (Tresource r : menuList) {				
				Tree tree = new Tree();
				tree.setId(r.getId().toString());			
				if (r.getResource() != null) {			
					tree.setPid(r.getResource().getId().toString());
					if(r.getResources()!=null&&r.getResources().size()>0){						
						for(Tresource t1 : r.getResources()){						
							if(t1.getResourcetype().toString().equals("0")){
								tree.setState("closed");							
							}
							if(r.getId().toString().equals("2")){	
								tree.setState("open");
							}
							
						}
					}					
				} else {						
					if(r.getId().toString().equals("2")){	
						tree.setState("open");
					}else{						
						tree.setState("closed");	
					}
				}
				if(r.getId().toString().equals("510")){
					
					tree.setText(r.getName());
					//tree.setText(r.getName());
				}else{
					tree.setText(r.getName());
				}
				tree.setIconCls(r.getIcon());
				Map<String, Object> attr = new HashMap<String, Object>();
				attr.put("url", r.getUrl());
				tree.setAttributes(attr);
				lt.add(tree);
			}
		}
		return lt;
	}

	@Override
	public List<Tree> listAllTree(boolean flag) {
		List<Tresource> l = null;
		List<Tree> lt = new ArrayList<Tree>();
		if (flag) {
			l = resourceDao.find("select distinct t from Tresource t left join fetch t.resource  order by t.seq");
		} else {
			l = resourceDao
					.find("select distinct t from Tresource t left join fetch t.resource where t.resourcetype !=1 order by t.seq");
		}
		if ((l != null) && (l.size() > 0)) {
			List<Tresource> newList =null;
			for (Tresource r : l) {
				
			
				Tree tree = new Tree();
				tree.setId(r.getId().toString());
				if (r.getResource() != null) {
					tree.setPid(r.getResource().getId().toString());
					if(r.getResources()!=null&&r.getResources().size()>0){
						//tree.setState("closed");
						for(Tresource t1 : r.getResources()){
							//if(!t1.getId().toString().equals("216")){
							if(t1.getResourcetype().toString().equals("0")&&!(r.getId().toString().equals("217"))){
								tree.setState("closed");
							
							}
						}
					}
				}
				tree.setText(r.getName());
				tree.setIconCls(r.getIcon());
				Map<String, Object> attr = new HashMap<String, Object>();
				attr.put("url", r.getUrl());
				tree.setAttributes(attr);
				//tree.setState("closed");
				lt.add(tree);
			}
		}
		return lt;
	}
	@Override
	public List<Tree> listAllTree1(boolean flag) {
		List<Tresource> l = null;
		List<Tree> lt = new ArrayList<Tree>();
		if (flag) {
			l = resourceDao.find("select distinct t from Tresource t left join fetch t.resource  order by t.seq");
		} else {
			l = resourceDao
					.find("select distinct t from Tresource t left join fetch t.resource where t.resourcetype =0 order by t.seq");
		}
		if ((l != null) && (l.size() > 0)) {
			for (Tresource r : l) {
				Tree tree = new Tree();
				tree.setId(r.getId().toString());
				if (r.getResource() != null) {
					tree.setPid(r.getResource().getId().toString());
					
					if(r.getResources()!=null&&r.getResources().size()>0){
						//tree.setState("closed");
						for(Tresource t1 : r.getResources()){
							//if(!t1.getId().toString().equals("216")){
							if(t1.getResourcetype().toString().equals("0")){
								tree.setState("closed");
							
							}
						}
					}
				}				
				tree.setText(r.getName());
				tree.setIconCls(r.getIcon());
				Map<String, Object> attr = new HashMap<String, Object>();
				attr.put("url", r.getUrl());
				tree.setAttributes(attr);
				//tree.setState("closed");
				lt.add(tree);
			}
		}
		return lt;
	}
	@Override
	public List<String> listAllResource() {
		List<String> resourceList = new ArrayList<String>();
		List<Tresource> l = resourceDao
				.find("select distinct t from Tresource t left join fetch t.resource  order by t.seq");
		for (int i = 0; i < l.size(); i++) {
			resourceList.add(l.get(i).getUrl());
		}
		return resourceList;
	}
	@Override
	public void collection(SessionInfo sessionInfo,Long resource_id){
		Tresource t = resourceDao.get(Tresource.class, resource_id);
		if(t!=null){
			System.out.println(t.getId());
		}
		/*t.setResources(new HashSet<Tresource>(resourceDao.find("select distinct t from Tresource t where t.id in ("
				+ ids + ")")));*/
	    t.setUsers(new HashSet<Tuser>(userDao.find("select t from Tuser t where t.id='"+sessionInfo.getId()+"'")));
	}
	@Override
	public void cancelcollection(SessionInfo sessionInfo,Long resource_id){
		Tresource t = resourceDao.get(Tresource.class, resource_id);
		t.setUsers(new HashSet<Tuser>());
		//resourceDao.update(t);
		
		
	}
	public List<Tree> getTreeNode(String tabsId,String id){
		
		List<Tree> lt = new ArrayList<Tree>();
		 tabsId="577";
		 Long pid = Long.valueOf(tabsId);
		 Map<String, Object> params = new HashMap<String, Object>();
		 params.put("resourcetype", 0);// 菜单类型的资源
		 
		 if(id==null||id.equals("")){
			 params.put("pid",pid);
			 List<Tresource> l = resourceDao.find(
						"select distinct t from Tresource t  where t.resourcetype = :resourcetype and t.resource.id = :pid  order by t.seq",
						params);
			 for (Tresource r : l) {
				 Tree tree = new Tree();
				 tree.setId(r.getId().toString());
				 tree.setIconCls(r.getIcon());
				 tree.setText(r.getName());
				 if(r.getResources()!=null&r.getResources().size()>0){
	                 tree.setState("closed");
				 }
				 lt.add(tree);
			 }
			 return lt;
		 }else{
			 
			 Long cpid = Long.valueOf(id);
			 params.put("pid",cpid);
			 List<Tresource> l = resourceDao.find(
						"select distinct t from Tresource t  where t.resourcetype = :resourcetype and t.resource.id = :pid  order by t.seq",
						params);
			
			 for (Tresource r : l) {
				 Tree tree = new Tree();
				 tree.setId(r.getId().toString());
				 tree.setIconCls(r.getIcon());
				 tree.setText(r.getName());
				 if(r.getResources()!=null&r.getResources().size()>0){
	                 tree.setState("closed");
				 }
				 lt.add(tree);
			 }
			 return lt;
		 }
	}
	
	 //List<Tresource> menuList = new ArrayList();
	 public  List<Tresource> treeMenuList(SessionInfo sessionInfo,List<Tresource> menuList,Long Pid){
		 Map<String, Object> params = new HashMap<String, Object>();
		 params.put("resourcetype", 0);
		 params.put("pid",Pid);
		 List<Tresource> ls =null;
		 if("admin".equals(sessionInfo.getLoginname())){
	     ls = resourceDao.find(
					"select distinct t from Tresource t  where t.resourcetype = :resourcetype and t.resource.id = :pid  order by t.seq",
					params);
		 }else{
			 
			 params.put("userId", Long.valueOf(sessionInfo.getId()));// 自查自己有权限的资源
			ls=resourceDao
						.find("select distinct t from Tresource t join fetch t.roles role join role.users user where"
								+ " t.resourcetype = :resourcetype and  t.resource.id = :pid  and user.id = :userId order by t.seq",
								params);
		 }
		 //Tresource tr = resourceDao.get(Tresource.class, resource_id);
		 if(ls!=null&&ls.size()>0){			 
			 for(Tresource tt: ls){
				 menuList.add(tt);
				  treeMenuList(sessionInfo,menuList,tt.getId()); 
			 }		 
		 }
	    return menuList;  
	    }  
	 
	 
	 @Override
		public List<Tree> newtree(SessionInfo sessionInfo) {
			List<Tresource> l = null;
			List<Tree> lt = new ArrayList<Tree>();

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("resourcetype", 0);// 菜单类型的资源
			params.put("resourcetype1", 2);
			if (sessionInfo != null) {
				if ("admin".equals(sessionInfo.getLoginname())) {
					l = resourceDao.find(
							"select distinct t from Tresource t  where (t.resourcetype = :resourcetype or t.resourcetype = :resourcetype1) order by t.seq",
							params);
				} else {
					params.put("userId", Long.valueOf(sessionInfo.getId()));// 自查自己有权限的资源
					l = resourceDao
							.find("select distinct t from Tresource t join fetch t.roles role join role.users user "
									+ "where (t.resourcetype = :resourcetype or t.resourcetype = :resourcetype1) and user.id = :userId order by t.seq",
									params);
				}
			} else {
				return null;
			}

			if ((l != null) && (l.size() > 0)) {
				for (Tresource r : l) {
					Tree tree = new Tree();
					tree.setId(r.getId().toString());
					
					if (r.getResource() != null) {
						//System.out.println("有父节点的------------------"+r.getId());
						//tree.setState("closed");
						//System.out.println(r.getId()+"++++++++++"+r.getName()+"------本身ID---------------------------");
						//System.out.println(r.getResources().size()+"---------子ID数量-----------------");
						tree.setPid(r.getResource().getId().toString());
						if(r.getResources()!=null&&r.getResources().size()>0){
							//tree.setState("closed");
							for(Tresource t1 : r.getResources()){
								//if(!t1.getId().toString().equals("216")){
								if(t1.getResourcetype().toString().equals("0")&&!(r.getId().toString().equals("217"))){
									tree.setState("closed");
								
								}
							}
						}
						
						
					} else {	
						//System.out.println("没有父节点的------------------"+r.getId());
						if(r.getId().toString().equals("216")){
							//System.out.println(r.getId()+"湖南交通建设打开");
						}else{
							//System.out.println("这个没有父节点的关闭");
							tree.setState("closed");	
						}
					}
					tree.setText(r.getName());
					tree.setIconCls(r.getIcon());
					Map<String, Object> attr = new HashMap<String, Object>();
					attr.put("url", r.getUrl());
					tree.setAttributes(attr);
					lt.add(tree);
				}
			}
			return lt;
		}
	 
	 public void CopyProjectProceDure(String name,int project_code) {
		 Connection conn=null;
		 try {     
		     conn =SessionFactoryUtils.getDataSource(sessionFactory).getConnection();
		     Statement stmt = conn.createStatement(); //创建Statement对象
		     String sql1 = "select max(id) from sys_resource";
		     ResultSet rs1 = stmt.executeQuery(sql1);
		     int id = -1;
		     while (rs1.next()){
		    	 id= rs1.getInt(1);              
             }		     
		     if(id>0){
		    	 id = id+1;
		    	 String sql2 = "{call p_copy_gs(?,?,?)}";
			     CallableStatement cs = conn.prepareCall(sql2);
			     cs.setInt(1, id);
			     cs.setString(2, name);
			     cs.setInt(3, project_code);
			     ResultSet rs = cs.executeQuery();
		     }		    
	         conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	 }
	 public void DeleteProjectProceDure(Long id){
		 
		 Connection conn=null;
		 try {     
		     conn =SessionFactoryUtils.getDataSource(sessionFactory).getConnection();
		     Statement stmt = conn.createStatement(); //创建Statement对象
		   
		    	 String sql2 = "{call p_delete_gs(?)}";
			     CallableStatement cs = conn.prepareCall(sql2);
			     cs.setLong(1, id);			    
			     ResultSet rs = cs.executeQuery();
			     conn.close();
		     }catch(SQLException e){
		    	 
		    	 e.printStackTrace();
		} 
		
	 }
}
