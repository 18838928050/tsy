package com.ssm.tsy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UpLoadImgUtil {

	/**
	 * MultipartFile转File
	 * 
	 * @param multfile
	 * @return
	 * @throws IOException
	 */
	public static File MultipartFileToFile(MultipartFile multfile) throws Exception {
		CommonsMultipartFile cf = (CommonsMultipartFile) multfile;
		// 这个myfile是MultipartFile的
		DiskFileItem fi = (DiskFileItem) cf.getFileItem();
		File file = fi.getStoreLocation();
		// 手动创建临时文件
		if (file.length() < 1024) {
			File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + file.getName());
			multfile.transferTo(tmpFile);
			return tmpFile;
		} 
		return file;
	}

	/**
	 * 上传图片
	 * 
	 * @param request
	 * @param file_img
	 * @return
	 * @throws IOException
	 */
	public static String UploadImg(HttpServletRequest request, File file_img) throws Exception {
		String f = "upload/";
		String path = request.getSession().getServletContext().getRealPath(f);
		String ne = null;
		if (file_img != null) {
			// 产生随机数开始(产生12为随机数作为图片的new name)
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyMMddHHmmss");
			String n = sdf.format(new java.util.Date());
			// 读取图片换算成字节
			InputStream is = new FileInputStream(file_img);
			byte b[] = new byte[is.available()];
			is.read(b);
			// 保存图片
			OutputStream out;
			// 图片保存路径(电脑相对路径),路径取到该项目的根目录
			out = new FileOutputStream(path + "/" + n + ".jpg");
			out.write(b);
			out.close();
			is.close();
			// 产生随机数结束
			ne = f + n + ".jpg";
		}
		return ne;
	}

	/**
	 * 删除图片
	 * 
	 * @param request
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static boolean DeleteImg(HttpServletRequest request, String path_img) throws Exception {
		String path = request.getSession().getServletContext().getRealPath("/") + path_img;
		if (new File(path).delete()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 上传Excel表
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static boolean UploadExcel(HttpServletRequest request) throws Exception {
		MultipartFile file = (MultipartFile) ((MultipartHttpServletRequest) request).getFile("file");
		String f = "upload/";
		// 产生随机数开始(产生12为随机数作为图片的new name)
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat( "yyMMddHHmmss");
		String n = sdf.format(new java.util.Date());
		String path = request.getSession().getServletContext().getRealPath(f);
		InputStream is = file.getInputStream();
		byte b[] = new byte[is.available()];
		OutputStream out;
		// 图片保存路径(电脑相对路径),路径取到该项目的根目录
		out = new FileOutputStream(path + "/" + n + ".xlsx");
		out.write(b);
		out.close();
		is.close();
		return true;
	}

}
