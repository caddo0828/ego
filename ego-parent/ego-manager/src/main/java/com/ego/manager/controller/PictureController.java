package com.ego.manager.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.ego.manager.service.PictureService;

/**
 * 操作图片上传的控制器
 * @author 老腰
 *
 */
@Controller
public class PictureController {
	@Resource
	private PictureService pictureServiceImpl;
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public Map<String, Object> upload(MultipartFile uploadFile) {
		Map<String, Object> map = new HashMap<>();
		try {
			 map = pictureServiceImpl.upload(uploadFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
}
