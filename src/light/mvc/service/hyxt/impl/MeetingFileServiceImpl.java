package light.mvc.service.hyxt.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import light.mvc.dao.BaseDaoI;
import light.mvc.model.hyxt.TMeetingFile;
import light.mvc.model.hyxt.TMeetingList;
import light.mvc.model.sys.Tuser;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.hyxt.Meeting;
import light.mvc.pageModel.hyxt.MeetingFile;
import light.mvc.service.hyxt.MeetingFileServiceI;
import light.mvc.utils.MyTimeUtil;
import light.mvc.utils.PropertiesLoader;
import light.mvc.utils.StrUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeetingFileServiceImpl implements MeetingFileServiceI{
	@Autowired
	private BaseDaoI<TMeetingFile> meetingFileDao;
	@Autowired
	private BaseDaoI<TMeetingList> meetingDao;
	@Autowired
	private BaseDaoI<Tuser> userDao;
	
	
	
	
	private String whereHql(MeetingFile meetingFile, Map<String, Object> params) {
		String hql = "";
		if (meetingFile != null) {
			hql += " where 1=1 ";
			
			System.out.println("meetingFile:" + meetingFile);
			
			if (StrUtil.hasLength(meetingFile.getMeetid())) {
				hql += " and t.meetingList.meetid like :meetid ";
				params.put("meetid", "%%" + meetingFile.getMeetid() + "%%");
			}
			if (StrUtil.hasLength(meetingFile.getMeetingname())) {
				hql += " and t.meetingList.meetname like :meetname ";
				params.put("meetname", "%%" + meetingFile.getMeetingname() + "%%");
			}
			if (StrUtil.hasLength(meetingFile.getFilename())) {
				hql += " and t.filename like :filename ";
				params.put("filename", "%%" + meetingFile.getFilename() + "%%");
			}
			if (StrUtil.hasLength(meetingFile.getUploaduser())) {
				hql += " and t.user.name like :uploaduser ";
				params.put("uploaduser", "%%" + meetingFile.getUploaduser() + "%%");
			}
			if (StrUtil.hasLength(meetingFile.getUploaddate())) {
				hql += " and t.uploaddate >= :uploaddatestart";
				params.put("uploaddatestart", MyTimeUtil.getDate(meetingFile.getUploaddate(), "yyyy-MM-dd HH:mm:ss") );
			}
			if (StrUtil.hasLength(meetingFile.getUploaddateend())) {
				hql += " and t.uploaddate <= :uploaddateend";
				params.put("uploaddateend", MyTimeUtil.getDate(meetingFile.getUploaddateend(), "yyyy-MM-dd HH:mm:ss"));
			}
		}
		return hql;
	}
	
	
	private String orderHql(PageFilter ph) {
		String orderString = "";
		if ((ph.getSort() != null) && (ph.getOrder() != null)) {
			orderString = " order by t." + ph.getSort() + " " + ph.getOrder();
		}
		return orderString;
	}
	
	
	@Override
	public List<MeetingFile> dataGrid(MeetingFile meetingFile, PageFilter ph) {
		List<MeetingFile> ml = new ArrayList<MeetingFile>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from TMeetingFile t ";
		
		System.out.println("hql:" + hql + whereHql(meetingFile, params) + orderHql(ph));
		System.out.println("params:" + params);
		
		List<TMeetingFile> l = meetingFileDao.find(hql + whereHql(meetingFile, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
		for (TMeetingFile t : l) {
			MeetingFile m = new MeetingFile();
			BeanUtils.copyProperties(t, m);
			
			m.setUploaddate(MyTimeUtil.getDateString(t.getUploaddate(), "yyyy-MM-dd HH:mm:ss"));
			m.setMeetid(t.getMeetingList().getMeetid());
			m.setMeetingname(t.getMeetingList().getMeetname());
			m.setUploaduser(t.getUser().getName());
			ml.add(m);
			
//			BeanUtils.copyProperties(t, m);
		}
		return ml;
	}
	
	@Override
	public Long count(MeetingFile meetingFile, PageFilter ph) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from TMeetingFile t ";
		return meetingFileDao.count("select count(*) " + hql + whereHql(meetingFile, params), params);
	}

	
	
	@Override
	public void delete(Long id) {
		TMeetingFile t = meetingFileDao.get(TMeetingFile.class, id);
		
		PropertiesLoader p = new PropertiesLoader("hyxt.properties");
     	String filePaths = p.getValue("uploadPath");
		File deleteFile = new File(filePaths + "/" + t.getFilename());
		if(deleteFile.exists()){
			deleteFile.delete();
		}
		del(t);
	}

	private void del(TMeetingFile t) {
		meetingFileDao.delete(t);
	}
	
	@Override
	public List<Meeting> meetinglistDataGrid() {
		List<Meeting> ms = new ArrayList<Meeting>();
		List<TMeetingList> meetings = meetingDao.find("from TMeetingList");
		for (TMeetingList t : meetings) {
			Meeting m = new Meeting();
			m.setId(t.getId());
			m.setMeetid(t.getMeetid());
			ms.add(m);
		}
		return ms;
	}

	@Override
	public String getMeetnameById(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		List<TMeetingList> m = meetingDao.find("from TMeetingList t where t.id = :id", params);
		
		return m != null ? m.get(0).getMeetname() : "";
	}
	
	
	@Override
	public TMeetingFile getMeetingFileByFilename(String filename) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("filename", filename);
		List<TMeetingFile> ts = meetingFileDao.find("from TMeetingFile t where t.filename = :filename", params);
		return ts != null && ts.size() > 0 ? ts.get(0) : null;
	}
	
	@Override
	public void add(MeetingFile meetingFile) {
		TMeetingFile tm = new TMeetingFile();
		BeanUtils.copyProperties(meetingFile, tm);
		tm.setUploaddate(MyTimeUtil.getDate(meetingFile.getUploaddate(), "yyyy-MM-dd HH:mm:ss"));
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", Long.valueOf(meetingFile.getMeetid()));
		List<TMeetingList> tmeetingLists = meetingDao.find("from TMeetingList t where t.id = :id" , params);
		if(tmeetingLists != null && tmeetingLists.size() > 0){
			tm.setMeetingList(tmeetingLists.get(0));
		}
		params.clear();
		params.put("name", meetingFile.getUploaduser());
		List<Tuser> tusers = userDao.find("from Tuser t where t.name = :name" ,params);
		if(tusers != null && tusers.size() > 0){
			tm.setUser(tusers.get(0));
		}
		
		meetingFileDao.save(tm);
	}
	
	@Override
	public MeetingFile get(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TMeetingFile t = meetingFileDao.get("from TMeetingFile t where t.id = :id", params);
		MeetingFile m = new MeetingFile();
		BeanUtils.copyProperties(t, m);
		m.setUploaddate(MyTimeUtil.getDateString(t.getUploaddate(), "yyyy-MM-dd HH:mm:ss"));
		m.setMeetid(t.getMeetingList().getId().toString());
		m.setMeetingname(t.getMeetingList().getMeetname());
		m.setUploaduser(t.getUser().getName());
		
		return m;
	}
	
	@Override
	public void edit(MeetingFile meetingFile) {
		TMeetingFile tm = new TMeetingFile();
		BeanUtils.copyProperties(meetingFile, tm);
		tm.setUploaddate(MyTimeUtil.getDate(meetingFile.getUploaddate(), "yyyy-MM-dd HH:mm:ss"));
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", Long.valueOf(meetingFile.getMeetid()));
		List<TMeetingList> tmeetingLists = meetingDao.find("from TMeetingList t where t.id = :id" , params);
		if(tmeetingLists != null && tmeetingLists.size() > 0){
			tm.setMeetingList(tmeetingLists.get(0));
		}
		params.clear();
		params.put("name", meetingFile.getUploaduser());
		List<Tuser> tusers = userDao.find("from Tuser t where t.name = :name" ,params);
		if(tusers != null && tusers.size() > 0){
			tm.setUser(tusers.get(0));
		}
		
		System.out.println("tm:" + tm);
		
		meetingFileDao.update(tm);
	}
	
	@Override
	public List<MeetingFile> getMeetingFilesById(Long id) {
		List<MeetingFile> ml = new ArrayList<MeetingFile>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		List<TMeetingFile> ls = meetingFileDao.find("from TMeetingFile t where t.meetingList.id = :id", params);
		System.out.println(ls);
		
		for (TMeetingFile t : ls) {
			MeetingFile m = new MeetingFile();
			BeanUtils.copyProperties(t, m);
			
			m.setUploaddate(MyTimeUtil.getDateString(t.getUploaddate(), "yyyy-MM-dd HH:mm:ss"));
			m.setMeetid(t.getMeetingList().getMeetid());
			m.setMeetingname(t.getMeetingList().getMeetname());
			m.setUploaduser(t.getUser().getName());
			ml.add(m);
			
//			BeanUtils.copyProperties(t, m);
		}
		return ml;
	}
	
}
