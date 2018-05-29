package block.com.blockchain.utils;

import android.content.Context;
import android.graphics.Bitmap;

import android.os.Environment;
import android.text.TextUtils;



import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ClassName: FileUtils
 * @Description: TODO
 * @author: Administrator
 * @date: 2015年4月2日 下午1:48:33
 */
public class SDCardUtils {
	public final static String CACHE = "/Android/data/blockChain";
	public static String filepath = "";
	public final static String CHATCACHE = "ChatDocument/";

	private SDCardUtils() {
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 判断SDCard是否可用
	 * 
	 * @return
	 */
	public static boolean isSDCardEnable() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

	}

	/**
	 * 获取Sdcard卡路径
	 * 
	 * @return SDPath
	 */
	public static String getSDPath(Context context) {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();
		} else {
			sdDir = context.getCacheDir();
		}
		return sdDir.toString();
	}



	/**
	 * 获取img存放路径
	 * 
	 * @return SDPath SDCard--/Android/data/jinvovo
	 */
	public static String getCacheDir(Context context) {

		String imgDir = getSDPath(context) + CACHE;
		File fileImgDir = new File(imgDir);
		if (!fileImgDir.exists()) {
			fileImgDir.mkdirs();
		}
		return fileImgDir.getAbsolutePath();
	}

	public static File getCacheDirFile(Context context) {
		String path = getCacheDir(context);
		File fileDir = new File(path);
		return fileDir;
	}



	public static void saveBitmapToSdcard(File file, Bitmap bitmap) {
		// 文件不存在。报错
		if (file == null || !file.exists()) {
			return;
		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 获取系统存储路径
	 * 
	 * @return
	 */
	public static String getRootDirectoryPath() {
		return Environment.getRootDirectory().getAbsolutePath();
	}

	/**
	 * 删除聊天缓存
	 * 
	 * @param context
	 * @param userId
	 * @param friendId
	 * @return
	 */
	public static boolean deleteFolder(Context context, String userId, String friendId) {
		String imgDir = getSDPath(context) + "/ChatDocument" + "/ChatFile_" + userId + "/FriendFile_" + friendId;
		File file = new File(imgDir);
		if (!file.exists()) {
			return false;
		} else {
			if (file.isFile()) {
				// 为文件时调用删除文件方法
				return deleteFile(imgDir);
			} else {
				// 为目录时调用删除目录方法
				return deleteDirectory(imgDir);
			}
		}
	}

	/**
	 * 删除发帖时临时创建的压缩文件
	 * 
	 * @param context
	 * @return
	 */
	public static boolean deleteCompressFolder(Context context) {
		String imgDir = getSDPath(context) + "/ChatDocument/Publish";
		File file = new File(imgDir);
		if (!file.exists()) {
			return false;
		} else {
			if (file.isFile()) {
				// 为文件时调用删除文件方法
				return deleteFile(imgDir);
			} else {
				// 为目录时调用删除目录方法
				return deleteDirectory(imgDir);
			}
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param filePath
	 *            被删除文件的文件名
	 * @return 文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String filePath) {
		if (TextUtils.isEmpty(filePath))
			return false;
		File file = new File(filePath);
		if (file.isFile() && file.exists()) {
			return file.delete();
		}
		return false;
	}

	/**
	 * 删除文件夹以及目录下的文件
	 * 
	 * @param filePath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String filePath) {
		boolean flag = false;
		// 如果filePath不以文件分隔符结尾，自动添加文件分隔符
		if (!filePath.endsWith(File.separator)) {
			filePath = filePath + File.separator;
		}
		File dirFile = new File(filePath);
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		flag = true;
		File[] files = dirFile.listFiles();
		// 遍历删除文件夹下的所有文件(包括子目录)
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				// 删除子文件
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} else {
				// 删除子目录
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前空目录
		return dirFile.delete();
	}
}
