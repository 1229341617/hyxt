package light.mvc.pageModel.hyxt;

import java.util.Date;


public class Meeting implements java.io.Serializable{
	
	private Long id;
	private String meetid;
	private String meetname;
	private String meetingroom;
	private Date createdate;
	private Date startdate;
	private Date finishdate;
	
	private Long userid;
	private String username;
	private String meetingperson ;
	private String starttime;
	private String finishtime ;
	private String isView;//0预览，1不可预览
	private String comments;
	
	
	private String queryword;
	
	public Meeting() {
		super();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMeetid() {
		return meetid;
	}
	public void setMeetid(String meetid) {
		this.meetid = meetid;
	}
	public String getMeetname() {
		return meetname;
	}
	public void setMeetname(String meetname) {
		this.meetname = meetname;
	}
	public String getMeetingroom() {
		return meetingroom;
	}
	public void setMeetingroom(String meetingroom) {
		this.meetingroom = meetingroom;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getFinishdate() {
		return finishdate;
	}
	public void setFinishdate(Date finishdate) {
		this.finishdate = finishdate;
	}


	public String getQueryword() {
		return queryword;
	}

	public void setQueryword(String queryword) {
		this.queryword = queryword;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMeetingperson() {
		return meetingperson;
	}

	public void setMeetingperson(String meetingperson) {
		this.meetingperson = meetingperson;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getFinishtime() {
		return finishtime;
	}

	public void setFinishtime(String finishtime) {
		this.finishtime = finishtime;
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
