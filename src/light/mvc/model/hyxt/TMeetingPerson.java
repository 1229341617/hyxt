package light.mvc.model.hyxt;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import light.mvc.model.sys.Tuser;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "hyxt_meetingperson")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TMeetingPerson implements java.io.Serializable{
	private static final long serialVersionUID = 7258841679755556284L;
	private Long id;
	private TMeetingList meetingList;
	private Tuser user;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
}
