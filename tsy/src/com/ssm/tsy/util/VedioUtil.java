package com.ssm.tsy.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;

public class VedioUtil {
	
	/**
	 * 删除文件
	 * @param inputObject
	 * @param outputObject
	 * @return
	 * @throws Exception
	 */
	public static Map<String , Object> vedioDelete(InputObject inputObject,OutputObject outputObject) throws Exception{
		
		return null;
	}
	
	/**
	 * 上传附件
	 * @param inputObject
	 * @param outputObject
	 * @param files
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public static Map<String , Object> vedioUpload(InputObject inputObject,OutputObject outputObject,CommonsMultipartFile files) throws Exception{
		String fileRealResistPath = "upload\\vedio\\main\\";// 文件存放真实相对路径
		String ffmpegRealResistPath = "upload\\vedio\\ffmpeg\\";// 文件截屏存放真实相对路径
		String ffmpeg = "upload\\util\\ffmpeg.exe";
		String Gfile = inputObject.getRequest().getSession().getServletContext().getRealPath("/");
		String filePath = Gfile + fileRealResistPath;
		String ffmpegPath = Gfile + ffmpegRealResistPath;//截屏保存路径
		String ffmpegGPath = Gfile + ffmpeg;//工具路径
		
		String fileName = files.getOriginalFilename();// 文件名称
		
		// 为了避免文件名重复，在文件名前加UUID加密字符串
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String uuidFileName = uuid + fileName.substring(fileName.lastIndexOf("."));
		String uuidffmpegPath = uuid + ".jpg";
		// 将文件保存到服务器
		upFile(files.getInputStream(), uuidFileName, filePath);
		
		Map<String,Object> map = new HashMap<String, Object>();
		if(fileName.substring(fileName.indexOf(".")+1,fileName.length()).equalsIgnoreCase("mp4")
				||fileName.substring(fileName.indexOf(".")+1,fileName.length()).equalsIgnoreCase("rm")
				||fileName.substring(fileName.indexOf(".")+1,fileName.length()).equalsIgnoreCase("rmvb")
				||fileName.substring(fileName.indexOf(".")+1,fileName.length()).equalsIgnoreCase("wmv")
				||fileName.substring(fileName.indexOf(".")+1,fileName.length()).equalsIgnoreCase("avi")
				||fileName.substring(fileName.indexOf(".")+1,fileName.length()).equalsIgnoreCase("3gp")
				||fileName.substring(fileName.indexOf(".")+1,fileName.length()).equalsIgnoreCase("mkv")){
			if(take(filePath+uuidFileName,ffmpegPath+uuidffmpegPath,ffmpegGPath)){
				map.put("optionPicPath", ffmpegRealResistPath + uuidffmpegPath);//附件截屏路径
			}else{
				map.put("optionPicPath", "");//附件截屏路径
			}
		}else{
			map.put("optionPicPath", fileRealResistPath+uuidFileName);
		}
		map.put("optionPath", fileRealResistPath+uuidFileName);//附件路径
		map.put("optionName", fileName.substring(0,fileName.indexOf(".")));//附件原始名称
		map.put("optionType", fileName.substring(fileName.indexOf(".")+1,fileName.length()));//附件类型
		map.put("optionSize", TransUtil.FormetFileSize(files.getSize()));//附件大小
		map.put("optionSizeUnit", TransUtil.FormetFileUnit(files.getSize()));//附件大小单位B，KB。。。
		return map;
	}
	
	/**
	 * 获取视频截图
	 * @param videoLocation
	 * @param imageLocation
	 * @return
	 */
	public static boolean take(String videoLocation, String imageLocation, String ffmpegGPath){
		// 低精度
		List<String> commend = new ArrayList<String>();
		commend.add(ffmpegGPath);//视频提取工具的位置
		commend.add("-i");
		commend.add(videoLocation);
		commend.add("-y");
		commend.add("-f");
		commend.add("image2");
		commend.add("-ss");
		commend.add("08.010");
		commend.add("-t");
		commend.add("0.001");
		commend.add("-s");
		commend.add("352x240");
		commend.add(imageLocation);
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commend);
			builder.start();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 单个文件上传
	 * @param is
	 * @param fileName
	 * @param filePath
	 */
	public static void upFile(InputStream is, String fileName, String filePath) {
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		File f = new File(filePath + "/" + fileName);
		try {
			bis = new BufferedInputStream(is);
			fos = new FileOutputStream(f);
			bos = new BufferedOutputStream(fos);
			byte[] bt = new byte[4096];
			int len;
			while ((len = bis.read(bt)) > 0) {
				bos.write(bt, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != bos) {
					bos.close();
					bos = null;
				}
				if (null != fos) {
					fos.close();
					fos = null;
				}
				if (null != is) {
					is.close();
					is = null;
				}
				if (null != bis) {
					bis.close();
					bis = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
