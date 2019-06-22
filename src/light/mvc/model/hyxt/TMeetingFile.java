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
@Table(name = "hyxt_meetingfile")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TMeetingFile implements java.io.Serializable{
	private static final long serialVersionUID = -4281353343230411537L;
	private Long id;
	private Date uploaddate;
	private String filename;
	private String fileurl;
	private String comments;
	private Tuser user;
	private TMeetingList meetingList;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "uploaddate", length = 19)
	public Date getUploaddate() {
		return uploaddate;
	}
	public void setUploaddate(Date uploaddate) {
		this.uploaddate = uploaddate;
	}
	
	@NotNull
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	@NotNull
	public String getFileurl() {
		return fileurl;
	}
	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meetid")
	public TMeetingList getMeetingList() {
		return meetingList;
	}
	
	public void setMeetingList(TMeetingList meetingList) {
		this.meetingList = meetingList;
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
	@Override
	public String toString() {
		return "TMeetingFile [id=" + id + ", uploaddate=" + uploaddate
				+ ", filename=" + filename + ", fileurl=" + fileurl
				+ ", comments=" + comments + ", user=" + user
				+ ", meetingList=" + meetingList + "]";
	}
	
	
}
