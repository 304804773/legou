package com.legou.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.legou.common.utils.FastDFSClient;
import com.legou.common.utils.JsonUtils;

@Controller
public class PicUploadController {
//	
//	@RequestMapping("/pic/upload")
//	@ResponseBody
	//上传文件需要MultipartFile
//	public String uploadFile(MultipartFile uploadFile) {
		
//		try {
//			//创建工具类,设置加载文件的相对路径,创建Trackerserver和Storageserver
//			FastDFSClient fastDFSClient=new FastDFSClient("classpath:config/fastdfs.properties");
//			//得到原文件名，知道从后数第一个点在文件名中的下标+1
//			int i=uploadFile.getOriginalFilename().lastIndexOf(".")+1;
//			//截取字符串,得到文件后缀名
//			String extName=uploadFile.getOriginalFilename().substring(i);
//			//上传文件到服务器,得到url
//			String url=fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
//			//ip地址
//			String ipAdress="http://192.168.25.133/";
//			//拼接字符串
//			url=ipAdress+url;
//			System.out.println(url);
//			//设置上传成功返回的参数
//			Map map=new HashMap(); 
//			map.put("erroy", 0);
//			map.put("url", url);
//			//将map集合转化为json字符串
//			return JsonUtils.objectToJson(map);
//		} catch (Exception e) {
//			//设置上传失败返回的参数
//			Map map=new HashMap(); 
//			map.put("erroy", 1);
//			map.put("message", "上传图片失败");
//			return JsonUtils.objectToJson(map);
//		}
//		
//				
//	}
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String uploadFile(MultipartFile uploadFile) {

		try {

			FastDFSClient fastDFSClient = new FastDFSClient("classpath:config/fastdfs.properties");

			// asdfasdf.png

	

			int i = uploadFile.getOriginalFilename().lastIndexOf(".") + 1;

			String extName = uploadFile.getOriginalFilename().substring(i);

			// group/
//			String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extnameString);
//
//			String hostname = "http://192.168.25.133/";
//
//			url = hostname + url;
			//上传文件到服务器,得到url
			String url=fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
			//ip地址
			String ipAdress="http://192.168.25.133/";
			//拼接字符串
			url=ipAdress+url;
			//System.out.println(url);

	

//			Map map = new HashMap();
//			map.put("error", 0);
//			map.put("url", url);
//
//			return JsonUtils.objectToJson(map);
			Map map=new HashMap(); 
			map.put("error", 0);
			map.put("url", url);
			//将map集合转化为json字符串
			return JsonUtils.objectToJson(map);
		} catch (Exception e) {
			Map map = new HashMap();
			map.put("error", 1);
			map.put("message", "上传图片失败");
			return JsonUtils.objectToJson(map);
		}

	}
	

}
