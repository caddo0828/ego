package com.ssm.manager.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.ssm.commons.utils.FtpUtil;
import com.ssm.commons.utils.IDUtils;
import com.ssm.manager.service.PictureService;

@Service
public class PictureServiceImpl implements PictureService{	
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
	
	@Override
	public Map<String, Object> upload(MultipartFile uploadFile) throws IOException {
		String fileName = IDUtils.getImageName()+uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf("."));
		boolean flag = FtpUtil.uploadFile(host, port, username, password, basePath, filePath, fileName, uploadFile.getInputStream());
		Map<String, Object> map = new HashMap<>();
		if(flag) {
			//上传成功
			map.put("error", 0);
			//http://192.168.178.132:80/images/fileName  
			map.put("url", "http://"+host+":80"+filePath+fileName);
		}else {
			map.put("error", 1);
			map.put("message", "图片上传失败，请重新操作！");
		}
		return map;
	}

}
