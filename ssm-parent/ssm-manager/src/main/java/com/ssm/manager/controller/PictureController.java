package com.ssm.manager.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ssm.manager.service.PictureService;

@Controller
public class PictureController {
	@Resource
	private PictureService pictureServiceImpl;
		
	@RequestMapping("/pic/upload")
	@ResponseBody
	public Map<String , Object> picUpload(MultipartFile uploadFile) {
		try {
			return pictureServiceImpl.upload(uploadFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return null;
	}
		
}
