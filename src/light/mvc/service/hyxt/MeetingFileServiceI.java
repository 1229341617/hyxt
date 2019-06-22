package light.mvc.service.hyxt;

import java.util.List;

import light.mvc.model.hyxt.TMeetingFile;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.hyxt.Meeting;
import light.mvc.pageModel.hyxt.MeetingFile;

public interface MeetingFileServiceI {
	public List<MeetingFile> dataGrid(MeetingFile meetingFile, PageFilter ph);
	
	public Long count(MeetingFile meetingFile, PageFilter ph);
	
	public void delete(Long id);

	public List<Meeting> meetinglistDataGrid();

	public String getMeetnameById(Long id);

	public TMeetingFile getMeetingFileByFilename(String filename);

	public void add(MeetingFile meetingFile);

	public MeetingFile get(Long id);

	public void edit(MeetingFile meetingFile);

	public List<MeetingFile> getMeetingFilesById(Long id);

}
