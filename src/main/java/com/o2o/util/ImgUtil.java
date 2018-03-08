package com.o2o.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.o2o.dto.ImageHolder;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImgUtil {
	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random r = new Random();
	private static Logger logger = LoggerFactory.getLogger(ImgUtil.class);
	private static String seperator = System.getProperty("file.separator");
	// 将文件流转化为文件对象
	public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile) {
		// 创建文件对象
		File newFile = new File(cFile.getOriginalFilename());
		// 将流写入文件
		try {
			cFile.transferTo(newFile);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			e.printStackTrace();
		}
		return newFile;
	}

	// 处理缩略图并返回相对值路径
	public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
		// 图片文件名
		String realFileName = getRandomFileName();
		// 图片文件名后缀
		String extension = getFileExtension(thumbnail.getImageName());
		// 路径不存在就创造路径
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		logger.debug("current relativeAddr is:" + relativeAddr);
		File dest = new File(PathUtil.getBasePath() + relativeAddr);
		logger.debug("current complete addr is:" + dest);
		try {
			// 给图片添加水印并保存
			System.out.println("basePath:"+basePath.replace("/", seperator)+ "watermark.png");
			Thumbnails.of(thumbnail.getImage()).size(200, 200)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "watermark.png")), 0.5f)
					.outputQuality(0.8f).toFile(dest);
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return relativeAddr;
	}

	// 创建目标路径所涉及到的目录
	private static void makeDirPath(String targetAddr) {
		// TODO Auto-generated method stub
		String realFileParentPath = PathUtil.getBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}

	/*
	 * 获取输入文件流的扩展名
	 */
	private static String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	/*
	 * 生成随机文件名
	 */
	private static String getRandomFileName() {
		// 获取随机的五位数
		int rannum = r.nextInt(89999) + 10000;
		String nowTimeStr = sDateFormat.format(new Date());
		return nowTimeStr + rannum;
	}
	/*
	 * storeOrPath是文件路径还是目录路径
	 * 如果是文件路径则删除该文件
	 * 如果是目录路径则删除该目录下所有文件
	 */
	public static void deleteFileOrPath(String storePath){
		File fileOrPath=new File(PathUtil.getBasePath()+storePath);
		if(fileOrPath.exists()){
		if(fileOrPath.isDirectory()){
			File[] files=fileOrPath.listFiles();
			for(int i=0;i<files.length;i++){
				files[i].delete();
			}
		}
		if(fileOrPath.isFile()){
//			System.out.println("fileName"+fileOrPath);
		fileOrPath.delete();
		}
	}
	}
	/*
	 * 处理详情图片，并返回新生成图片的相对值路径
	 */

	public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
		// 图片文件名
				String realFileName = getRandomFileName();
				// 图片文件名后缀
				String extension = getFileExtension(thumbnail.getImageName());
				// 路径不存在就创造路径
				makeDirPath(targetAddr);
				String relativeAddr = targetAddr + realFileName + extension;
				logger.debug("current relativeAddr is:" + relativeAddr);
				File dest = new File(PathUtil.getBasePath() + relativeAddr);
				logger.debug("current complete addr is:" + dest);
				try {
					// 给图片添加水印并保存
					System.out.println("basePath:"+basePath.replace("/", seperator)+ "watermark.png");
					Thumbnails.of(thumbnail.getImage()).size(337, 640)
							.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "watermark.png")), 0.5f)
							.outputQuality(0.9f).toFile(dest);
				} catch (Exception e) {
					logger.error(e.toString());
					e.printStackTrace();
				}
				return relativeAddr;
	}
	
}
