<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache,must-revalidate">
    <title>注册-个人用户</title>
    <link type="text/css" rel="stylesheet" href="/css/regist.personal.css"/>
    <link type="text/css" rel="stylesheet" href="/css/passport.base.css"/>
    <script type="text/javascript" src="/js/jquery-1.6.4.js"></script>
</head>
<body>
<div class="w" id="logo">
    <div>
    	<a href="http://localhost:8082">
    		<img src="/images/ego-logo.gif" alt="易购商城" width="170" height="60"/>
    	</a> <b></b>
    </div>
</div>

<div class="w" id="regist">
    <div class="mt">
        <ul class="tab">
            <li class="curr">个人用户</li>
        </ul>
        <div class="extra">
        <span>我已经注册，现在就&nbsp;
        	<a href="/user/showLogin" class="flk13">登录</a>
        </span>
        </div>
    </div>
    <div class="mc">
        <form id="personRegForm" method="post" onsubmit="return false;">
            <div class="form" onselectstart="return false;">
                <div class="item" id="select-regName">
                    <span class="label"><b class="ftx04">*</b>用户名：</span>

                    <div class="fl item-ifo">
                        <div class="o-intelligent-regName">
                            <input type="text" id="regName" name="username" class="text" tabindex="1" autoComplete="off"
                                   onpaste="return false;"
                                   value=""
                                   onfocus="if(this.value=='') this.value='';this.style.color='#333'"
                                   onblur="if(this.value=='') {this.value='';this.style.color='#999999'}"/>
                            <i class="i-name"></i>
                            <ul id="intelligent-regName" class="hide"></ul>
                            <label id="regName_succeed" class="blank"></label>
                            <label id="regName_error" class="hide"></label>
                        </div>
                    </div>
                </div>
                <div id="o-password">
                    <div class="item">
                        <span class="label"><b class="ftx04">*</b>请设置密码：</span>

                        <div class="fl item-ifo">
                            <input type="password" id="pwd" name="password" class="text" tabindex="2"
                                   style="ime-mode:disabled;"
                                   onpaste="return  false" autocomplete="off"/>
                            <i class="i-pass"></i>
                            <label id="pwd_succeed" class="blank"></label>
                            <label id="pwd_error"></label>
                            <span class="clr"></span>
                        </div>
                    </div>

                    <div class="item">
                        <span class="label"><b class="ftx04">*</b>请确认密码：</span>

                        <div class="fl item-ifo">
                            <input type="password" id="pwdRepeat" name="pwdRepeat" class="text" tabindex="3"
                                   onpaste="return  false" autocomplete="off"/>
                            <i class="i-pass"></i>
                            <label id="pwdRepeat_succeed" class="blank"></label>
                            <label id="pwdRepeat_error"></label>
                        </div>
                    </div>
					<div class="item" id="dphone">
						<span class="label"><b class="ftx04">*</b>验证手机：</span>

						<div class="fl item-ifo">
							<input type="text" id="phone" maxlength="11" name="phone"
								class="text" tabindex="4"
								autocomplete="off" /> <i class="i-phone"></i> <label
								id="phone_succeed" class="blank"></label> <label
								id="phone_error"></label>
						</div>
					</div>
					</div>
                <div class="item item-new">
                    <span class="label">&nbsp;</span>

                    <div class="fl item-ifo">
                        <input type="checkbox" class="checkbox" checked="checked" id="readme"
                               onclick="agreeonProtocol();">
                        <label for="protocol">我已阅读并同意<a href="#" class="blue" id="protocol">《易购用户注册协议》</a></label>
                        <span class="clr"></span>
                        <label id="protocol_error" class="error hide">请接受服务条款</label>
                    </div>
                </div>
                <div class="item">
                    <span class="label">&nbsp;</span>
                    <input type="button" class="btn-img btn-regist" id="registsubmit" value="立即注册" tabindex="8"
                           clstag="regist|keycount|personalreg|07"
                           onclick="REGISTER.reg();"/>
                </div>
            </div>
            <div class="phone">
                <img width="180" height="180" src="/images/phone-bg.jpg">
            </div>
            <span class="clr"></span>
        </form>
    </div>
<script type="text/javascript">
	var REGISTER={
		param:{
			//单点登录系统的url
			surl:""
		},
		inputcheck:function(){
				//不能为空检查，输入框验证
				//支持中文，数字，字母 4-20
				if ($("#regName").val() == "") {
					alert("用户名不能为空");
					$("#regName").focus();  //当鼠标移动到该input框时，获取到焦点
					return false;
				}else if( $("#regName").val().length<4 || $("#regName").val().length>20 ) {
					alert("用户注册名由4-20位组成,请重新输入!")
					$("#regName").focus();
					return false;
				}
				
				//字母、数字6-20 [A-Za-z0-9_]
				var reg_pwd = /^\w{6,20}$/ ; 
				if ($("#pwd").val() == "") {
					alert("密码不能为空");
					$("#pwd").focus();
					return false;
				}else if(!reg_pwd.test( $("#pwd").val() )) {
					alert("密码格式为由字母，数字，下划线组成的6-20位");
					$("#pwd").focus();
					return false;
				}
				
				//密码检查
				if ($("#pwd").val() != $("#pwdRepeat").val()) {
					alert("确认密码和密码不一致，请重新输入！");
					$("#pwdRepeat").select();
					$("#pwdRepeat").focus();
					return false;
				}
				
				//验证注册手机号
				var reg_phone =/^1(3|4|5|6|7|8|9)\d{9}$/ ;  
				
				//1(3|4|5|6|7|8|9)\d{9}
				if ($("#phone").val() == "") {
					alert("手机号不能为空");
					$("#phone").focus();
					return false;
				}else if( !reg_phone.test($("#phone").val()) ) {
					// test() 函数进行正则表达式校验
					// match() 函数进行
					alert("请输入正确的手机号！");
					$("#phone").focus();
					return false;
				}
				
				return true;
		},
		beforeSubmit:function() {
				//检查用户是否已经被占用
				$.ajax({
					//escape() 函数可对字符串进行编码，这样就可以在所有的计算机上读取该字符串。
					//decodeURI() 函数可以实现对字符串进行解码操作
	                //采用Math.random()为了防止浏览器默认为相同而缓存，包证每次url都不一样，认为相同的缓存而造成ajax请求失效
	            	url : REGISTER.param.surl + "/user/check/"+decodeURI($("#regName").val())+"/1?r=" + Math.random(),
	            	success : function(data) {
	            		//返回一个Result对象，包含data属性，data属性为布尔类型
	            		if (data.data) {
	            			//检查手机号是否存在
	            			$.ajax({
	            				url : REGISTER.param.surl + "/user/check/"+$("#phone").val()+"/2?r=" + Math.random(),
				            	success : function(data) {
				            		if (data.data) {
				            			//注册
					            		REGISTER.doSubmit();
				            		} else {
				            			alert("此手机号已经被注册！");
				            			$("#phone").select();
				            		}
				            	}
	            			});
	            		} else {
	            			alert("此用户名已经被占用，请选择其他用户名");
	            			$("#regName").select();
	            		}	
	            	}
				});
	            	
		},
		doSubmit:function() {
			$.post("/user/register",$("#personRegForm").serialize(), function(data){
				// 要求返回一个Result对象，包含注册成功的状态码
				if(data.status == 200){
					alert('用户注册成功，请登录！');
					//用户注册成功后，点击确认，跳转到登录页面
					REGISTER.login();
				} else {
					alert("注册失败！");
				}
			});
		},
		login:function() {
			//显示登录页面
			 location.href = "/user/showLogin";
			 return false;
		},
		reg:function() {
			if (this.inputcheck()) {
				this.beforeSubmit();
			}
		}
	};
</script>
</body>
</html>
