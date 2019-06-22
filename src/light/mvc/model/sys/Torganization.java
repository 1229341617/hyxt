package light.mvc.model.sys;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "sys_organization", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Torganization implements java.io.Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 5923932721345518100L;
	protected Long id;
	private Date createdatetime;
	private String name;
	private String address;
	private String code;
	private String icon;
	private Integer seq;
	private Integer isaccount;
	private String shortname;
	private Torganization organization;
	private Set<Torganization> organizations = new HashSet<Torganization>(0);

	public Torganization() {
		super();
	}

	public Torganization(Date createdatetime, String name, String address, String code, String icon,Integer seq,Integer isaccount,
			String shortname,Torganization organization, Set<Torganization> organizations) {
		super();
		this.createdatetime = createdatetime;
		this.name = name;
		this.address = address;
		this.code = code;
		this.icon = icon;
		this.seq = seq;
		this.isaccount=isaccount;
		this.shortname=shortname;
		this.organization = organization;
		this.organizations = organizations;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pid" )
	public Torganization getOrganization() {
		return organization;
	}

	public void setOrganization(Torganization organization) {
		this.organization = organization;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdatetime", length = 19)
	public Date getCreatedatetime() {
		return createdatetime;
	}

	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
	public Set<Torganization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(Set<Torganization> organizations) {
		this.organizations = organizations;
	}

	public Integer getIsaccount() {
		return isaccount;
	}

	public void setIsaccount(Integer isaccount) {
		this.isaccount = isaccount;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

}
