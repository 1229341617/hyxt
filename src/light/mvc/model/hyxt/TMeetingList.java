package light.mvc.model.hyxt;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import light.mvc.model.sys.Tuser;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "hyxt_meetinglist")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TMeetingList implements java.io.Serializable{
	private static final long serialVersionUID = 7433988645544181910L;
	private Long id;
	private String meetid;
	private String meetname;
	private String meetingroom;
	private Date createdate;
	private Date startdate;
	private Date finishdate;
	private Tuser user;
	private String isView;//1预览，0不可预览
	private String comments;
	
	
	public TMeetingList(){}
	
	public TMeetingList(Long id, String meetid, String meetname,
			String meetingroom, Date createdate, Date startdate,
			Date finishdate, Tuser user, String isView, String comments) {
		this.id = id;
		this.meetid = meetid;
		this.meetname = meetname;
		this.meetingroom = meetingroom;
		this.createdate = createdate;
		this.startdate = startdate;
		this.finishdate = finishdate;
		this.user = user;
		this.isView = isView;
		this.comments = comments;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@NotNull
	@Column(name = "meetid", length = 11)
	public String getMeetid() {
		return meetid;
	}
	public void setMeetid(String meetid) {
		this.meetid = meetid;
	}
	
	@NotNull
	public String getMeetname() {
		return meetname;
	}
	public void setMeetname(String meetname) {
		this.meetname = meetname;
	}
	
	@NotNull
	public String getMeetingroom() {
		return meetingroom;
	}
	public void setMeetingroom(String meetingroom) {
		this.meetingroom = meetingroom;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdate", length = 19)
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "startdate", length = 19)
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "finishdate", length = 19)
	public Date getFinishdate() {
		return finishdate;
	}
	public void setFinishdate(Date finishdate) {
		this.finishdate = finishdate;
	}
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public Tuser getUser() {
		return user;
	}
	public void setUser(Tuser user) {
		this.user = user;
	}
	public String getIsView() {
		return isView;
	}
	public void setIsView(String isView) {
		this.isView = isView;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
}
