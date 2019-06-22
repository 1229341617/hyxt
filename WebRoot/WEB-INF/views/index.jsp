<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="inc.jsp"></jsp:include>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>工程信息管理系统</title>
    <script type="text/javascript">
        var index_layout;
        var index_tabs;
        var index_tabsMenu;
        var layout_west_tree;
        var layout_west_tree1;
        var layout_west_tree2;
        var layout_west_tree3;
        var layout_west_tree_url = '';
        var layout_west_tree_url1 = '';
        var layout_west_tree_url2 = '';
        var layout_west_tree_url3 = '';

        var sessionInfo_userId = '${sessionInfo.id}';
        if (sessionInfo_userId) { //如果没有登录,直接跳转到登录页面
            layout_west_tree_url = '${ctx}/resource/tree';
            layout_west_tree_url1 = '${ctx}/resource/tree1';
            layout_west_tree_url2 = '${ctx}/resource/tree2';
            //layout_west_tree_url3 = '${ctx}/resource/tree3';
            layout_west_tree_url3 = '${ctx}/organization/tree';

			} else {
            window.location.href = '${ctx}/admin/index';
        }
	function createFrame(url) {
      return '<iframe src="' + url + '" frameborder="0" style="height:100%;width:100%;"></iframe>';
    };
    
    $(function () {
 
          index_layout = $('#index_layout').layout({
                fit: true
          });
 		layout_west_tree = $('#layout_west_tree').tree({
                url: layout_west_tree_url,
                parentField: 'pid',
                lines: true,
                onClick: function (node) {
                    if (node.attributes && node.attributes.url) {
                        console.log('layout_west_tree' + node.text);
                        var url = '${ctx}' + node.attributes.url;
                        addTab({
                            id: node.id,
                            url: url,
                            title: node.text,
                            iconCls: node.iconCls
                        });
                    }
                }
            });
          index_tabs = $('#index_tabs').tabs({
                fit: true,
                border: false,
                tools: [
                    {
                        iconCls: 'icon-home',
                        handler: function () {
                            index_tabs.tabs('select', 0);
                        }
                    }, {
                        iconCls: 'icon-reload',
                        handler: function () {
       						var currTab = index_tabs.tabs('getSelected');
					        if (currTab.find('iframe').length > 0){
					        var url = $(currTab.panel('options').content).attr('src');
					        index_tabs.tabs('update', {
					            tab: currTab,
					            options: {
					               content:  createFrame(url), closable: true //创建Frame标签
					            }
					        });	
					        }	
					        else {
								//var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
                				//index_tabs.tabs('getTab', index).panel('open').panel('refresh');
                				location.reload(true);    
					        }
					     }
                    }, {
                        iconCls: 'icon-del',
                        handler: function () {
                            var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
                            var tab = index_tabs.tabs('getTab', index);
                            if (tab.panel('options').closable) {
                                index_tabs.tabs('close', index);
                            }
                        }
                    }
                ]
            });

           
            layout_west_tree1 = $('#layout_west_tree1').tree({
                url: layout_west_tree_url1,
                parentField: 'pid',
                lines: true,
                onClick: function (node) {
                    if (node.attributes && node.attributes.url) {
                        var url = '${ctx}' + node.attributes.url;
                        addTab({
                            id: node.id,
                            url: url,
                            title: node.text,
                            iconCls: node.iconCls
                        });
                    }
                }
            });
            layout_west_tree2 = $('#layout_west_tree2').tree({
                url: layout_west_tree_url2,
                parentField: 'pid',
                lines: true,
                onClick: function (node) {
                    if (node.attributes && node.attributes.url) {
                        console.log('layout_west_tree2' + node.text);
                        var url = '${ctx}' + node.attributes.url;
                        addTab({
                            id: node.id,
                            url: url,
                            title: node.text,
                            iconCls: node.iconCls
                        });
                    }
                }
            });
            layout_west_tree3 = $('#layout_west_tree3').tree({
                url: layout_west_tree_url3,
                parentField: 'pid',
                lines: true,
                onClick: function (node) {
                    //如果点击根节点（即 id=1 时）加载所有数据。 否则加载对应部门id的数据
                    if (node.id == 1) {
                        //判断组织机构的页面是否存在，存在就选中该页面并加载全部数据。   不存在则添加该页面
                        if ($('#index_tabs').tabs('exists', "组织机构")) {
                            $('#index_tabs').tabs('select', "组织机构");
                            $('#dataGridZzjg').datagrid('load', {});
                        } else {
                            addTab({
                                id: '',
                                url: '${ctx}/user/zzjg',
                                title: '组织机构',
                                iconCls: 'icon-ok'
                            });
                        }

                    } else {
                        $('#dataGridZzjg').datagrid('load', {
                            organizationId: node.id
                        });
                    }
                }
            });
            $("#index_tabs").tabs({
                onContextMenu: function (e, title) {
                    e.preventDefault();
                    //console.log(e);
                    $('#tabsMenu').menu('show', {
                        left: e.pageX,
                        top: e.pageY
                    }).data("tabTitle", title);
                }
            });
            //实例化menu的onClick事件
            $("#tabsMenu").menu({
                onClick: function (item) {
                    //alert(item.name);
                    TabEvent(this, item.name);
                }
            });

            //几个事件的实现
            function TabEvent(menu, type) {
                var curTabTitle = $(menu).data("tabTitle");
                var tabs = $("#index_tabs");
                //console.log(tabs.);
                if (type === "close") {
                    tabs.tabs("close", curTabTitle);
                    return;
                }

                if (type === "sc") {
                    // alert("收藏");
                    //alert(curTabTitle);
                    var pp = $('#index_tabs').tabs('getSelected');
                    var opt = pp.panel('options');
                    var resource_id = opt.id;
                    if (resource_id == null) {
                        alert('请先选择需要收藏的页面');
                    } else {
                        //alert(opt.id);
                        //console.log(tab);
                        $.ajax({
                            type: 'POST',
                            url: '${ctx}/resource/collection',
                            data: { resource_id: opt.id },
                            success: function (result) {
                                alert("收藏成功");
                                layout_west_tree.tree('reload');
                                //parent.layout_west_tree.tree('reload');
                            }
                            //dataType: dataType
                        });
                    }
                }

                if (type === "qxsc") {
                    var pp = $('#index_tabs').tabs('getSelected');
                    var opt = pp.panel('options');
                    var resource_id = opt.id;
                    if (resource_id == null) {
                        alert('请先选择需要取消收藏的页面');
                    } else {
                        $.ajax({
                            type: 'POST',
                            url: '${ctx}/resource/cancelcollection',
                            data: { resource_id: opt.id },
                            success: function (result) {
                                alert("取消成功");
                                layout_west_tree.tree('reload');
                                //parent.layout_west_tree.tree('reload');
                            }
                            //dataType: dataType
                        });
                    }
                }

                var allTabs = tabs.tabs("tabs");
                var closeTabsTitle = [];

                $.each(allTabs, function () {
                    var opt = $(this).panel("options");
                    if (opt.closable && opt.title != curTabTitle && type === "Other") {
                        closeTabsTitle.push(opt.title);
                    } else if (opt.closable && type === "All") {
                        closeTabsTitle.push(opt.title);
                    }
                });

                for (var i = 0; i < closeTabsTitle.length; i++) {
                    tabs.tabs("close", closeTabsTitle[i]);
                }
            }

            //菜单导航选中事件
            $('#Menu_Tabs').tabs({
                border: false,
                onSelect: function (title) {
                    if (title == '组织机构') {
                        var node = $('#layout_west_tree3').tree('find', 1); //找到id为”tt“这个树的节点id为”1“的对象
                        $('#layout_west_tree3').tree('select', node.target); //设置选中该节点

                        addTab({
                            id: '',
                            url: '${ctx}/user/zzjg',
                            title: '组织机构',
                            iconCls: 'icon-ok'
                        });
                    }
                }
            });

        });
   
        function addTab(params) {
            var t = $('#index_tabs');
            var opts = {
                title: params.title,
                id: params.id,
                iconCls: params.iconCls,
                content : createFrame(params.url), //创建Frame标签
                //     href: params.url,
                border: false,
                fit: true,
                closable: true
            };
            if (params.close==1){
	            if (t.tabs('exists', opts.title)) {
	                t.tabs('close', opts.title);
	            }          	
           	}
            if (t.tabs('exists', opts.title)) {
                //alert('select');
                t.tabs('select', opts.title);//存在选择
            } else {
                //alert('add');
                t.tabs('add', opts);
            }
         }
	
	function closeTab(title) {
		if ($('#index_tabs').tabs('exists', title)) {
			     $('#index_tabs').tabs('close', title);
		}          	
	}

        function logout() {
            $.messager.confirm('提示', '确定要退出?', function (r) {
                if (r) {
                    progressLoad();
                    $.post('${ctx}/admin/logout', function (result) {
                        if (result.success) {
                            progressClose();
                            window.location.href = '${ctx}/admin/index';
                        }
                    }, 'json');
                }
            });
        }


        function editUserPwd() {
            parent.$.modalDialog({
                title: '修改密码',
                width: 300,
                height: 250,
                href: '${ctx}/user/editPwdPage',
                buttons: [
                    {
                        text: '修改',
                        handler: function () {
                            var f = parent.$.modalDialog.handler.find('#editUserPwdForm');
                            f.submit();
                        }
                    }
                ]
            });
        }
    </script>
    <style>
        * {
            margin: 0px;
            padding: 0px;
        }

        .dbyw {
            float: right;
            border-radius: 3px;
            margin-top: 46px;
            font-size: 14px;
            background: #DDDDDD;
            font-family: '微软雅黑';
            color: #000;
        }

        .Htitle {
            display: inline;
            float: left;
            margin: 5px 0 0 30px;
            font-size: 32px;
            font-family: '楷体';
            color: #FFF;
        }
    </style>
</head>

<body>
    <div id="loading" style="position: fixed;top: -50%;left: -50%;width: 200%;height: 200%;background: #fff;z-index: 100;overflow: hidden;">
        <img src="${ctx}/style/images/ajax-loader.gif" style="position: absolute;top: 0;left: 0;right: 0;bottom: 0;margin: auto;" />
    </div>
    <div id="index_layout">
        <div data-options="region:'north'" style="height:68px;overflow: hidden;background:url(${ctx}/style/images/dz.png) no-repeat;background-color:rgb(0,130,197);">
            <%-- <div style="width:70%;margin:0 auto;overflow:hidden;background:url(${ctx}/style/images/dz2.png) no-repeat">
                --%>
                <div id="header">
                    <div class="Htitle">
			<img src="${ctx}/style/images/goog-earth.png" style="width:60px;vertical-align: middle;margin-right: 10px;" />
			</div>
                    <div style="margin-right:10px;">
                        <span id="" style="float: right;text-align:center;margin-top:10px;font-size:14px;font-family:'微软雅黑'; padding-right: 30px;">
                            <span style="color:#fff;">欢迎 <b>${sessionInfo.name}</b>&nbsp;&nbsp;</span> <a style="color:#fff;text-decoration:none;" href="javascript:void(0)" onclick="editUserPwd()">修改密码</a>&nbsp;&nbsp;<a style="color:#fff;text-decoration:none;" href="javascript:void(0)" onclick="logout()">安全退出</a>
                            
                            &nbsp;&nbsp;&nbsp;&nbsp;
                        </span>
                        <br>
                        <span id="" style="float: right;text-align:center;margin-top:10px;font-size:14px;font-family:'微软雅黑'; padding-right: 30px;">
                        <a style="color:#fff;text-decoration:none;" href="http://10.13.1.188/wv/wordviewerframe.aspx?WOPISrc=http%3a%2f%2f10.13.1.188%2fapi%2fwopi%2ffiles%2fusermanual.doc&access_token=xLI2rWWTlgE%3dG1rHSSaQ9TMfoHpxU5sURlBqAGBWXnOhnmX0O%2bQBSwY%3d" target="_blank">查看用户手册</a>
                        </span>
                    </div>
                </div>
                <!-- <span class="header"></span> -->
                <!-- </div> -->
            </div>
            <div class="easyui-layout" data-options="region:'west',split:true" title="菜单导航" style="width: 220px;height:900px; background-color:#E0ECEC">
                <div id="Menu_Tabs" class="easyui-tabs" style="width:100%;height:100%;">
                   
                        <div title="业务导航" style="padding:10px;background:#f0f8ff;">
                            <ul id="layout_west_tree"></ul>
                        </div>
                  

                    <c:if test="${fn:contains(sessionInfo.resourceTabsList, '流程审批')}">
                        <div title="流程审批" data-options="" style="padding:10px;background:#f0f8ff;height:100%;width:50%;">
                            <ul id="layout_west_tree2"></ul>
                        </div>
                    </c:if>

                </div>
            </div>
            <!-- 首页 -->
            <div data-options="region:'center'" style="overflow:auto;width:1600px;background-color:#F0F8FF;">
                <div id="index_tabs" style="overflow:auto;">
                    <!-- <div title="首页" data-options="" style="overflow:auto;">
                    </div> -->
                </div>

            </div>
          
        </div>

        <!--[if lte IE 7]>
        <div id="ie6-warning"><p>您正在使用 低版本浏览器，在本页面可能会导致部分功能无法使用。建议您升级到 <a href="http://www.microsoft.com/china/windows/internet-explorer/" target="_blank">Internet Explorer 9</a> 或以下浏览器：
        <a href="http://www.mozillaonline.com/" target="_blank">Firefox</a> / <a href="http://www.google.com/chrome/?hl=zh-CN" target="_blank">Chrome</a> / <a href="http://www.apple.com.cn/safari/" target="_blank">Safari</a> / <a href="http://www.operachina.com/" target="_blank">Opera</a></p></div>
        <![endif]-->

        <style>
            /*ie6提示*/
            #ie6-warning {
                width: 100%;
                position: absolute;
                top: 0;
                left: 0;
                background: #fae692;
                padding: 5px 0;
                font-size: 12px;
            }

                #ie6-warning p {
                    width: 960px;
                    margin: 0 auto;
                }
        </style>
        <div id="tabsMenu" class="easyui-menu" style="width: 120px;">
            <div name="close">关闭</div>
            <div name="Other">关闭其他</div>
            <div name="All">关闭所有</div>
            <div name="sc">收藏</div>
            <div name="qxsc">取消收藏</div>
        </div>
</body>

</html>

