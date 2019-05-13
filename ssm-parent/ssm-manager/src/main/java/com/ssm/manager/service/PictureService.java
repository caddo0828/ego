package com.ssm.manager.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface PictureService {
	/**
	 * 上传图片
	 * @param uploadFile
	 * @return
	 */
	Map<String ,Object> upload(MultipartFile uploadFile)throws IOException ;
	
}
