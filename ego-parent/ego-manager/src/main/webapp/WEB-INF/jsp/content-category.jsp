<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
	 <ul id="contentCategory" class="easyui-tree">
    </ul>
</div>
<div id="contentCategoryMenu" class="easyui-menu" style="width:120px;" data-options="onClick:menuHandler">
    <div data-options="iconCls:'icon-add',name:'add'">添加</div>
    <div data-options="iconCls:'icon-remove',name:'rename'">重命名</div>
    <div class="menu-sep"></div>
    <div data-options="iconCls:'icon-remove',name:'delete'">删除</div>
</div>
<script type="text/javascript">
$(function(){
	var oldMenuName = "";
	//表示当前的tree对象
	$("#contentCategory").tree({
		url : '/content/category/list',
		//是否使用动画的效果展示树形界面
		animate: true,
		method : "GET",
		// 表示右键点击菜单事件
		onContextMenu: function(e,node){
            //阻止浏览器的右键功能操作
			e.preventDefault();
            //当前的树被右键选中时，固定当前的节点对象
            $(this).tree('select',node.target);
           //让修改，重命名，删除的div显示出来（右键菜单主键）
            $('#contentCategoryMenu').menu('show',{
                //在当前节点的那个位置进行点击就显示在哪里，x轴，y轴
            	left: e.pageX,
                top:  e.pageY
            });
        },
        //在进行操作的时候，先将原先的树节点的名称记录，在操作失败的时候直接将原来的节点名称赋值回去
        onBeforeEdit:function(node){
        	oldMenuName=node.text;
        },
        onAfterEdit : function(node){
        	var _tree = $(this);
        	//根据当前节点是否存在id判断（当选择重命名操作的时候）是新增操作还是真正的（重命名操作）
        	if(node.id == 0){
        		// 新增节点，将父节点的id传递过去，表示设置新增的内容分类对应的父节点是谁
        		$.post("/content/category/create",{parentId:node.parentId,name:node.text},function(data){
        			if(data.status == 200){
        				_tree.tree("update",{
            				target : node.target,
            				//返回的结果对象中包含一个data对象，data对象中包含一个id属性，表示给当前的内容分类数据增加
            				//也就是表明是一个新增成功的内容分类，并且把该分类的id赋值
            				id : data.data.id
            			});
        				$.messager.alert('提示','创建'+node.text+' 分类成功!');
        			}else{
        				//提示失败的原因
        				$.messager.alert('提示','创建'+node.text+' 分类失败!<br/>'+data.data);
        				_tree.tree("remove",node.target);
        			}
        		});
        	}else{
        		//真正重命名操作
        		$.post("/content/category/update",{id:node.id,name:node.text},function(data){
        			if(data.status == 200){
        				$.messager.alert('提示','修改'+node.text+' 分类成功!');
        			}else{
        				$.messager.alert('提示','修改'+node.text+' 分类失败!<br/>'+data.data);
        				_tree.tree("update",{
            				target : node.target,
            				//当节点的数据重命名失败的时候，将原来的节点名称进行还原操作
            				text:oldMenuName
            			});
        			}
        		});
        	}
        }
	});
});
function menuHandler(item){
	//当前的tree对象
	var tree = $("#contentCategory");
	//获取到被选中的tree节点
	var node = tree.tree("getSelected");
	//右键对菜单进行点击事件，easyui特性，会把被点击选中的菜单项事件传递过来，也就是item对象
	if(item.name === "add"){
		tree.tree('append', {
			//新增的时候，判断该分类节点是否已经是一个父菜单了，如果是则不修改状态，如果不是则修改父节点状态is_parent为true
            parent: (node?node.target:null),
            data: [{
                text: '新建分类',
                //一开始新增的时候默认新增分类的id为0
                id : 0,
                //对所点击的菜单进行增加，所点击的节点作为新增节点的父节点
                parentId : node.id
            }]
        }); 
		var _node = tree.tree('find',0);
		tree.tree("select",_node.target).tree('beginEdit',_node.target);
	}else if(item.name === "rename"){
		//表示开始编辑状态
		tree.tree('beginEdit',node.target);
	}else if(item.name === "delete"){
		$.messager.confirm('确认','确定删除名为 '+node.text+' 的分类吗？',function(r){
			if(r){
				//在进行内容分类的删除的时候，删除该内容分类，并且将父菜单的id传递过去，将是否是父菜单的状态进行修改
				$.post("/content/category/delete/",{parentId:node.parentId,id:node.id},function(data){
					if(data.status==200){
						$.messager.alert('提示','删除'+node.text+'分类成功!');
						tree.tree("remove",node.target);
					}else{
						$.messager.alert('提示','删除'+node.text+'分类失败!<br/>'+data.data);
					}
				});	
			}
		});
	}
}
</script>