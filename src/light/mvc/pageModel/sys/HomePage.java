package light.mvc.pageModel.sys;

import java.math.BigDecimal;

public class HomePage implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7343664092211988411L;
	String deptname;
	Long waitcount;
	Long caogaocount;
	Long applycount;
	Long checkcount;
	Long curfinishcount;
	int budgetcount;
	int projectcount;
	int factstartcount;
	int factendcount;
	BigDecimal projecttotal;
	int zgscount;//子公司
	int zccount ;//资产
	int jtcount;//集团
	int yjcount;//应急
	int stopcount;//停工
	int correcttcount;//整改
	public String getdeptname() {
		return deptname;
	}
	public void setdeptname(String deptname) {
		this.deptname = deptname;
	}
	public Long getWaitcount() {
		return waitcount;
	}
	public void setWaitcount(Long waitcount) {
		this.waitcount = waitcount;
	}
	public Long getCaogaocount() {
		return caogaocount;
	}
	public void setCaogaocount(Long caogaocount) {
		this.caogaocount = caogaocount;
	}
	public Long getApplycount() {
		return applycount;
	}
	public void setApplycount(Long applycount) {
		this.applycount = applycount;
	}
	public Long getCheckcount() {
		return checkcount;
	}
	public void setCheckcount(Long checkcount) {
		this.checkcount = checkcount;
	}
	public Long getCurfinishcount() {
		return curfinishcount;
	}
	public void setCurfinishcount(Long curfinishcount) {
		this.curfinishcount = curfinishcount;
	}
	public int getBudgetcount() {
		return budgetcount;
	}
	public void setBudgetcount(int budgetcount) {
		this.budgetcount = budgetcount;
	}
	public int getProjectcount() {
		return projectcount;
	}
	public void setProjectcount(int projectcount) {
		this.projectcount = projectcount;
	}
	public int getFactstartcount() {
		return factstartcount;
	}
	public void setFactstartcount(int factstartcount) {
		this.factstartcount = factstartcount;
	}
	public int getFactendcount() {
		return factendcount;
	}
	public void setFactendcount(int factendcount) {
		this.factendcount = factendcount;
	}
	public BigDecimal getProjecttotal() {
		return projecttotal;
	}
	public void setProjecttotal(BigDecimal projecttotal) {
		this.projecttotal = projecttotal;
	}
	public int getZgscount() {
		return zgscount;
	}
	public void setZgscount(int zgscount) {
		this.zgscount = zgscount;
	}
	public int getZccount() {
		return zccount;
	}
	public void setZccount(int zccount) {
		this.zccount = zccount;
	}
	public int getJtcount() {
		return jtcount;
	}
	public void setJtcount(int jtcount) {
		this.jtcount = jtcount;
	}
	public int getYjcount() {
		return yjcount;
	}
	public void setYjcount(int yjcount) {
		this.yjcount = yjcount;
	}
	public int getStopcount() {
		return stopcount;
	}
	public void setStopcount(int stopcount) {
		this.stopcount = stopcount;
	}
	public int getCorrecttcount() {
		return correcttcount;
	}
	public void setCorrecttcount(int correcttcount) {
		this.correcttcount = correcttcount;
	}
	
}
