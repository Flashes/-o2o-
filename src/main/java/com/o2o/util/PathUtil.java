package com.o2o.util;

public class PathUtil {
	// 获取BasePath
	private static String seperator = System.getProperty("file.separator");

	// 根据执行环境不同提供不同的根路径
	public static String getBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath = "D:/projectdev/image";
		} else {
			basePath = "/zhangchi/image";
		}
		basePath = basePath.replace("/", seperator);
		return basePath;
	}

	// 获取店铺图片存储路径
	public static String getShopImagePath(long shopId) {
		String imagePath = "/upload/item/shop/" + shopId + "/";
		System.out.println(imagePath.replace("/", seperator));
		return imagePath.replace("/", seperator);
	}
}
