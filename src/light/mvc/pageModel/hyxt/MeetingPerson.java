package light.mvc.pageModel.hyxt;


public class MeetingPerson implements java.io.Serializable{
	//这个meetid
	private Long id;
	private Long userid;
	private String username;
	private Long meetingid;
	
	
	
	public Long getMeetingid() {
		return meetingid;
	}
	public void setMeetingid(Long meetingid) {
		this.meetingid = meetingid;
	}


	public MeetingPerson() {
		super();
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


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}
	
}
