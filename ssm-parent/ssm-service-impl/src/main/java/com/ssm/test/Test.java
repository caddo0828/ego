package com.ssm.test;

import com.alibaba.dubbo.container.Main;

public class Test {
	public static void main(String[] args) {
		//让zookeeper注册中心启动，将服务进行挂载
		//这种情况下默认扫描的是META-INF/spring/applicationContext-*.xm;
		Main.main(args);
	}
}
