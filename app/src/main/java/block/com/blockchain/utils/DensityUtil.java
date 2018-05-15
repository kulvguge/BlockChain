package block.com.blockchain.utils;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;

public class DensityUtil {  
  
    /** 
     */  
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }
    /** 
     */  
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
    public static int[] getViewSize(View view) {
    	 int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
         int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
         view.measure(w, h);  
         int height =view.getMeasuredHeight();  
         int width =view.getMeasuredWidth();  
         return new int[]{width,height};
    }
}
