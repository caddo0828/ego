package com.ego.manager.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.ego.common.utils.FtpUtil;
import com.ego.common.utils.IDUtils;
import com.ego.manager.service.PictureService;

@Service
public class PictureServiceImpl implements PictureService{
	//从ftp属性文件中读取数据，要将属性文件交给spring进行管理
	@Value("${ftpclient.host}")
	private String host;
	@Value("${ftpclient.port}")
	private int port;
	@Value("${ftpclient.username}")
	private String username;
	@Value("${ftpclient.password}")
	private String password;
	@Value("${ftpclient.basePath}")
	private String basePath;
	@Value("${ftpclient.filePath}")
	private String filePath;
	
	//业务实现类中如果交由Spring管理，并且声明了事务就抛异常，
	@Override
	public Map<String, Object> upload(MultipartFile uploadFile) throws IOException {
		String fileName = IDUtils.getImageName()+uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf("."));
		boolean flag = FtpUtil.uploadFile(host,21, username, password, basePath, filePath, fileName, uploadFile.getInputStream());
		Map<String, Object> map = new HashMap<>();
		if(flag) {
			//上传成功
			map.put("error", 0);
			//相当于http://192.168.178.130:80/文件存放目录/文件名
			map.put("url", "http://"+host+":80"+filePath+fileName);
		}else {
			map.put("error", 1);
			map.put("message", "图片上传失败");
		}
		return map;
	}
	
}
