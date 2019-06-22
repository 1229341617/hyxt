package light.mvc.pageModel.base;

import java.util.Date;
import java.util.List;
public class SessionInfo implements java.io.Serializable {

	private Long id;// 用户ID
	private String loginname;// 登录名
	private String name;// 姓名
	private String ip;// 用户IP
	private String organizationname;// 部门名
	private Long OrganizationId;//部门编号
	private String shortname;//核算部门简称
	private Long accountId;//核算部门编号
	private Date systime;
	private String uploadPath;//上传文件路径"activitifile.properties"定义
	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	private List<String> resourceList;// 用户可以访问的资源地址列表
	
	private List<String> resourceAllList;
	
	private List<String> resourceTabsList;

	public Date getSystime() {
		return systime;
	}

	public void setSystime(Date systime) {
		this.systime = systime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOrganizationname() {
		return organizationname;
	}

	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}

	public Long getOrganizationId() {
		return OrganizationId;
	}

	public void setOrganizationId(Long organizationId) {
		OrganizationId = organizationId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public List<String> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<String> resourceList) {
		this.resourceList = resourceList;
	}

	public List<String> getResourceAllList() {
		return resourceAllList;
	}

	public void setResourceAllList(List<String> resourceAllList) {
		this.resourceAllList = resourceAllList;
	}

	public List<String> getResourceTabsList() {
		return resourceTabsList;
	}

	public void setResourceTabsList(List<String> resourceTabsList) {
		this.resourceTabsList = resourceTabsList;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	@Override
	public String toString() {
		return this.name;
	}

}
