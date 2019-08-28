package com.legou.fastdfstest;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class Test {
	public static void main(String[] args) throws Exception{
		//加载配置文件
		ClientGlobal.init("D:\\legou\\code\\legou\\legou-manager-web\\src\\main\\resources\\config\\fastdfs.properties");	
		//创建client连接tracker
		TrackerClient trackerClient = new TrackerClient();
		//连接trackserver
		TrackerServer trackserver = trackerClient.getConnection();
		//设置storageServer为空
		StorageServer storageServer = null; 
		//创建client连接storage
		StorageClient storageClient = new StorageClient(trackserver, storageServer);
		//上传图片
		String[] upload_file = storageClient.upload_file("D:\\s.jpg", "jpg", null);
		//得到集群中的地址
		for (String string : upload_file) {
			System.out.println(string);
	}
	}


}
