package light.mvc.pageModel.hyxt;



public class MeetingFile implements java.io.Serializable {
	
	private Long id;
	private String filename;
	private String uploaddate;
	private String uploaddateend;
	private String meetid;
	private String meetingname;
	private String uploaduser;
	private String fileurl;
	private String comments;
	
	
	public String getFilnameurl(){
		return filename + ":" + fileurl;
	}
	
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getMeetid() {
		return meetid;
	}
	public void setMeetid(String meetid) {
		this.meetid = meetid;
	}
	public String getMeetingname() {
		return meetingname;
	}
	public void setMeetingname(String meetingname) {
		this.meetingname = meetingname;
	}
	public String getUploaduser() {
		return uploaduser;
	}
	public void setUploaduser(String uploaduser) {
		this.uploaduser = uploaduser;
	}
	public String getUploaddate() {
		return uploaddate;
	}
	public void setUploaddate(String uploaddate) {
		this.uploaddate = uploaddate;
	}
	public String getUploaddateend() {
		return uploaddateend;
	}
	public void setUploaddateend(String uploaddateend) {
		this.uploaddateend = uploaddateend;
	}
	
	
	@Override
	public String toString() {
		return "MeetingFile [id=" + id + ", filename=" + filename
				+ ", uploaddate=" + uploaddate + ", uploaddateend="
				+ uploaddateend + ", meetid=" + meetid + ", meetingname="
				+ meetingname + ", uploaduser=" + uploaduser + ", fileurl="
				+ fileurl + ", comments=" + comments + "]";
	}
}
