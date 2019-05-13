package com.ego.manager.service;

import java.io.IOException;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface PictureService {
	/**
	 * 对图片进行上传操作，富文本编辑器要求数据回显的格式为
	 * 成功时
	 *   error : 0 
	 *   url : "图片url路径"
	 *  
	 **********
	 * 失败时
	 *   error: 1;
	 *   message : "图片上传失败"
	 *  根据要求响应的数据格式为json，将响应数据封装成为一个map对象
	 * @param uploadFile
	 * @return
	 */
	Map<String, Object> upload(MultipartFile uploadFile)throws IOException;
}
