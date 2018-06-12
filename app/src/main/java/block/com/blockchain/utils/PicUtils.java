package block.com.blockchain.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/6/12.
 */

public class PicUtils  {
    public static String getQualityPic(String srcPath, Context context) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
//开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
//现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = ScreenUtils.getScreenDispaly(context)[1];//这里设置高度为800f
        float ww = ScreenUtils.getScreenDispaly(context)[0];//这里设置宽度为480f
//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放

            be = (int) (newOpts.outWidth / ww);
            if ((float) (newOpts.outWidth / ww) > be) {
                be++;
            }
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
            if ((float) (newOpts.outHeight / hh) > be) {
                be++;
            }
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        if (bitmap == null)
            return srcPath;
        Bitmap bitmapQulity = compressImage(bitmap, 0.3f);//压缩好比例大小后再进行质量压缩
        if (bitmapQulity == null)
            return srcPath;
        String srcp = FileUtils.savePic(bitmapQulity, "temppic.jpg");
        if (srcp == null)
            return srcPath;
        return srcp;
    }
    /**
     * 压缩图片小于size M
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image, float size) {
        if (image == null) {
            return image;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int bytes = baos.toByteArray().length / 1024;
        Log.i("图片大小信息 原图=", baos.toByteArray().length + "byte");
        int KBsize = (int) (size * 1024);
        int options = KBsize * 100 / bytes; // 运算出需要的压缩比例。
        if (options < 100) { // 说明许需要压缩
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
        } else {
            Log.i("图片大小信息 原图=", "不需要压缩");
            return null;
        }
        Log.i("图片大小信息 压缩=", baos.toByteArray().length + "byte");
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        if (baos != null) {
            try {
                baos.close();
                isBm.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (image != null)
            image.recycle();
        return bitmap;
    }

}
