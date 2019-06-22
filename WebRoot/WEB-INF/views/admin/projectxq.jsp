<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
		<form id="" method="post">
			<table class="grid">
				<tr>
					<td>路线编号</td>
					<td><input name="qdwz" type="text" class="easyui-validatebox" value="${xqProject.xmbh}"></td>
					<td>名称</td>
					<td><input name="zdwz" type="text" class="easyui-validatebox" value="${xqProject.name}"></td>
				</tr>
				<tr>
					<td>起点位置</td>
					<td><input name="qdwz" type="text" class="easyui-validatebox" value="${xqProject.qdwz}"></td>
					<td>终点位置</td>
					<td><input name="zdwz" type="text" class="easyui-validatebox" value="${xqProject.zdwz}"></td>
				</tr>
				<tr>
					<td>起点桩号</td>
					<td><input name="qdzh" type="text" class="easyui-validatebox" value="${xqProject.qdzh}"></td>
					<td>终点桩号</td>
					<td><input name="zdzh" type="text" class="easyui-validatebox" value="${xqProject.zdzh}"></td>
				</tr>
				<tr>
					<td>路线名称</td>
					<td><input name="lxcd" type="text" class="easyui-validatebox" value="${xqProject.lxcd}"></td>
					<td>公路等级</td>
					<td><input name="gldj" type="text" class="easyui-validatebox" value="${xqProject.gldj}"></td>
				</tr>
				<tr>
					<td>设计交通量</td>
					<td><input name="sjjtl" type="text" class="easyui-validatebox" value="${xqProject.sjjtl}"></td>
					<td>设计车道数</td>
					<td><input name="sjcds" type="text" class="easyui-validatebox" value="${xqProject.sjcds}"></td>
				</tr>
				<tr>
					<td>设计速度</td>
					<td><input name="sjsd" type="text" class="easyui-validatebox" value="${xqProject.sjsd}"></td>
					<td>设计荷载</td>
					<td><input name="sjhz" type="text" class="easyui-validatebox" value="${xqProject.sjhz}"></td>
				</tr>
				<tr>
					<td>概算</td>
					<td><input name="gs" type="text" class="easyui-validatebox" value="${xqProject.gs}"></td>
					<td>预算</td>
					<td><input name="ys" type="text" class="easyui-validatebox" value="${xqProject.ys}"></td>
				</tr>
				<tr>
					<td>决算</td>
					<td><input name="js" type="text" class="easyui-validatebox" value="${xqProject.js}"></td>
					<td>工程概况说明</td>
					<td><input name="gcgksm" type="text" class="easyui-validatebox" value="${xqProject.gcgksm}"></td>
				</tr>
				<tr>
					<td>建设单位</td>
					<td><input name="jsdw" type="text" class="easyui-validatebox" value="${xqProject.jsdw}"></td>
					<td>建设工期(月)</td>
					<td><input name="jsgq" type="text" class="easyui-validatebox" value="${xqProject.jsgq}"></td>
				</tr>
			</table>
		</form>
	</div>
</div>