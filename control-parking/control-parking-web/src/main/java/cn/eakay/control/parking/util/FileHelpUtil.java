package cn.eakay.control.parking.util;

import java.io.File;

public class FileHelpUtil {
	public static String getFileName(String path) {
		File file = new File(path);
		File[] array = file.listFiles();
		for (int i = 0; i < array.length; i++) {
			if (array[i].isFile()) {
				String name = array[i].getName();
				String filename = name.substring(0, name.lastIndexOf("."));
				String a[] = filename.split("_");
				return a[1];
			}
		}
		return null;
	}
	public static String getFileWholeName(String path) {
		File file = new File(path);
		File[] array = file.listFiles();
		for (int i = 0; i < array.length; i++) {
			if (array[i].isFile()) {
				return array[i].getName();
			}
		}
		return null;
	}
}
