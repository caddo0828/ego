var TTCart = {
	load : function(){ // 加载购物车数据
		
	},
	itemNumChange : function(){
		$(".increment").click(function(){//＋
			var _thisInput = $(this).siblings("input");
			_thisInput.val(eval(_thisInput.val()) + 1);
			$.post("/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val() + ".action",function(data){
				TTCart.refreshTotalPrice();
			});
			TTCart.refreshTotalNum();
		});
		$(".decrement").click(function(){//-
			var _thisInput = $(this).siblings("input");
			if(eval(_thisInput.val()) == 1){
				return ;
			}
			_thisInput.val(eval(_thisInput.val()) - 1);
			$.post("/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val() + ".action",function(data){
				TTCart.refreshTotalPrice();
			});
			TTCart.refreshTotalNum();
		});
		$(".quantity-form .quantity-text").rnumber(1);//限制只能输入数字
		$(".quantity-form .quantity-text").change(function(){
			var _thisInput = $(this);
			$.post("/service/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val(),function(data){
				TTCart.refreshTotalPrice();
			});
			TTCart.refreshTotalNum();
		});
	},
	refreshTotalPrice : function(){ //重新计算总价
		var total = 0;
		$(".quantity-form .quantity-text").each(function(i,e){
			var _this = $(e);
			total += (eval(_this.attr("itemPrice")) * 10000 * eval(_this.val())) / 10000;
		});
		$(".totalSkuPrice").html(new Number(total/100).toFixed(2)).priceFormat({ //价格格式化插件
			 prefix: '￥',
			 thousandsSeparator: ',',
			 centsLimit: 2
		});
	},
	refreshTotalNum: function(){//计算总数量
		var totalNum = 0;
		$(".quantity-form .quantity-text").each(function(i,e){
			var _this = $(e);
			if(_this.parent().parent().siblings(".p-checkbox").children().eq(0)[0].checked){
				totalNum += parseInt(_this.val());
			}
		});
		$("#selectedCount").html(totalNum);
	
	},

	 //获取商品选框中选中的商品id
	 getSelectionsIds: function (){
		var array = document.getElementsByClassName("checkbox");
		var ids = [];
		for(var i=0;i<array.length;i++) {
			if(array[i].checked) {
				ids.push(array[i].value);
			}
		}
		//以，将其进行分隔
		ids = ids.join(",");
		return ids;	
	 }

	
};

$(function(){
	TTCart.load();
	TTCart.itemNumChange();
	TTCart.refreshTotalNum();
	
	//对删除超链接添加点击事情
	$(".mycart_remove").click(function(){
		var $a =$(this);
		var href=$(this).attr("href");
		$.post(href,function(data){
			if(data.status==200){
				//parent()当前标签的父标签
				$a.parent().parent().parent().remove();
				//此处对购物车数据进行重新加载了
				TTCart.refreshTotalPrice();
				TTCart.refreshTotalNum();
			}
		});
		return false;
	});
	//对复选框添加点击事件
	$(".checkbox").click(function(){
		var total = 0;
		$(".quantity-form .quantity-text").each(function(i,e){
			var _this = $(e);
			if(_this.parent().parent().siblings(".p-checkbox").children().eq(0)[0].checked){
				total += (eval(_this.attr("itemPrice")) * 10000 * eval(_this.val())) / 10000;
			}
		});
		$(".totalSkuPrice").html(new Number(total/100).toFixed(2)).priceFormat({ //价格格式化插件
			 prefix: '￥',
			 thousandsSeparator: ',',
			 centsLimit: 2
		});
		
		TTCart.refreshTotalNum();
		
	});
	//"去结算"按钮点击事件
	$("#toSettlement").click(function(){
		//alert($(".checkbox:checked").length);
		//i脚标   n当前循环时对象,对象是一个dom对象
		var param = "";
	   
		//先判断是否有商品被选中，如果当前被选中商品数量为0，阻止进行提交
		//selectedCount
		var itemNum = $("#selectedCount").html();
		if(parseInt(itemNum)==0) {
			alert("当前无被选中的商品，请选中商品后再操作");
			return false;
		}else {
			$.each($(".checkbox:checked"),function(i,n){
				//alert($(n).val());
				param+="id="+$(n).val();
				if(i<$(".checkbox:checked").length-1){
					param+="&";
				}
			});
			//alert(param);
			location.href=$(this).attr("href")+"?"+param;
		}
	
		return false;
		
	});
	

	//发送ajax请求进行购物车商品的删除操作
	$("#remove-batch").click(function() {
		var ids = TTCart.getSelectionsIds();
		if(ids.length == 0){
			alert('未选中商品,请选中商品后再操作!');
			return false;
		}else {
			var params = {"ids":ids};
			var $a =$(this);
			var href=$(this).attr("href");	
		    $.post(href,params, function(data){
		    	if(data.status == 200){
						alert('删除商品成功!',function(){
							location.reload();
						});
					}
			}); 
		    return false;
		}
	});
	
	
	
});