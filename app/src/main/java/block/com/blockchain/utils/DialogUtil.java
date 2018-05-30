package block.com.blockchain.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import block.com.blockchain.R;
import block.com.blockchain.app.MyApp;
import block.com.blockchain.callback.SelectListener;


public class DialogUtil {
    public static int state = 1;
    private static int timegap = -1;


    public static void showDialog(Activity context, String title, String msg, String confirmMsg, String cancelMsg,
                                  final SelectListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle(TextUtils.isEmpty(title) ? "提示" : title);
        builder.setMessage(TextUtils.isEmpty(msg) ? "" : msg);
        String confirm = TextUtils.isEmpty(confirmMsg) ? "确定" : confirmMsg;
//		 String cancel = TextUtils.isEmpty(cancelMsg) ? "取消" : cancelMsg;
        // 相当于确定
        builder.setPositiveButton(confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null)
                    listener.confirm();
            }
        });
        if (!TextUtils.isEmpty(cancelMsg)) {
            // 相当于取消
            builder.setNegativeButton(cancelMsg, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (listener != null)
                        listener.cancel();
                }
            });
        }
        builder.create().show();
    }

    public static void showPopupWindow(Activity activity, int resId, OnEventListener eventListener) {
        showPopupWindow(activity, resId, eventListener, R.style.AnimBottom);
    }

    public static void showPopupWindow(Activity activity, int resId, OnEventListener eventListener, int styleId) {
        // token null is not valid; is your activity running?
        try {
            if (activity == null) {
                return;
            }
            if (activity.getParent() != null) {
                activity = activity.getParent();
            }
            View popView = LayoutInflater.from(MyApp.mContext).inflate(resId, null);
            PopupWindow popupWindow = new PopupWindow(activity);
            popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.setContentView(popView);
            popupWindow.setAnimationStyle(styleId);
            if (eventListener != null)
                eventListener.eventListener(popView, popupWindow);
            popupWindow.showAtLocation(popView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        } catch (Exception e) {
        }
    }

    public static void showAlertDialog(Activity activity, int resId, OnEventListener eventListener) {
        Dialog dialog = new Dialog(activity);
        dialog.show();
        View view = LayoutInflater.from(MyApp.mContext).inflate(resId, null);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        if (eventListener != null)
            eventListener.eventListener(view, dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
    }

    public static void showPromptDialog(Context activity, String title, String content, String leftMenu,
                                        String centerMenu, String rightMenu, final OnMenuClick onMenuClick) {
        showPromptDialog(activity, title, content, leftMenu, centerMenu, rightMenu, onMenuClick, null);
    }

    public static void showCancelAblePromptDialog(Context activity, String title, String content, String leftMenu,
                                                  String centerMenu, String rightMenu, final OnMenuClick onMenuClick,
                                                  boolean cancelable) {
        showPromptDialog(null, activity, title, content, leftMenu, centerMenu, rightMenu, onMenuClick, null,
                cancelable);
    }

    public static void showPromptDialog(Dialog diag, Context activity, String title, String content, String leftMenu,
                                        String centerMenu, String rightMenu, final OnMenuClick onMenuClick, String
                                                content2, boolean cancelable) {
        try {
            content = TextUtils.isEmpty(content) ? "系统提示" : content;
            if (diag == null) {
                diag = new Dialog(activity);
            }

            final Dialog dialog = diag;
            //去掉自定义dialog 在部分机型上出现的蓝色横线
            Context context = dialog.getContext();
            int divierId = context.getResources().getIdentifier("o2o:id/titleDivider", null, null);
            View divider = dialog.findViewById(divierId);
            if (divider != null) {
                divider.setBackgroundColor(Color.TRANSPARENT);
            }


            dialog.show();

            View view = LayoutInflater.from(MyApp.mContext).inflate(R.layout.common_prompt_dialog, null);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            TextView textTitle = (TextView) view.findViewById(R.id.tv_common_prompt_title);
            TextView textContent = (TextView) view.findViewById(R.id.tv_common_prompt_content);

            TextView textContent2 = (TextView) view.findViewById(R.id.tv_common_prompt_content2);

            if (TextUtils.isEmpty(title)) {
                textTitle.setVisibility(View.GONE);
            } else {
                textTitle.setText(title);
                textTitle.setVisibility(View.VISIBLE);
            }
            if (TextUtils.isEmpty(content)) {
                textContent.setVisibility(View.GONE);
            } else {
                textContent.setText(content);
                textContent.setVisibility(View.VISIBLE);
            }

            if (TextUtils.isEmpty(content2)) {
                textContent2.setVisibility(View.GONE);
            } else {
                textContent2.setVisibility(View.VISIBLE);
                textContent2.setText(content2);
            }
            TextView textLeft = (TextView) view.findViewById(R.id.tv_common_prompt_left);
            TextView textCenter = (TextView) view.findViewById(R.id.tv_common_prompt_center);
            TextView textRight = (TextView) view.findViewById(R.id.tv_common_prompt_right);

            View viewOne = view.findViewById(R.id.view_one);
            View viewTwo = view.findViewById(R.id.view_two);

            if (!TextUtils.isEmpty(leftMenu)) {// 左菜单不为空
                textLeft.setVisibility(View.VISIBLE);
                textLeft.setText(leftMenu);
            } else {
                textLeft.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(centerMenu)) {
                textCenter.setVisibility(View.VISIBLE);
                textCenter.setText(centerMenu);
            } else {
                textCenter.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(rightMenu)) {
                textRight.setVisibility(View.VISIBLE);
                textRight.setText(rightMenu);
            } else {
                textRight.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(leftMenu) && !TextUtils.isEmpty(centerMenu) && !TextUtils.isEmpty(rightMenu)) {//
                // 全部都有
                viewOne.setVisibility(View.VISIBLE);
                viewTwo.setVisibility(View.VISIBLE);
            } else if (!TextUtils.isEmpty(leftMenu) && TextUtils.isEmpty(centerMenu) && !TextUtils.isEmpty(rightMenu)
                    ) {// 中间为空
                viewOne.setVisibility(View.VISIBLE);
                viewTwo.setVisibility(View.GONE);
            } else if (TextUtils.isEmpty(leftMenu) && !TextUtils.isEmpty(centerMenu) && !TextUtils.isEmpty(rightMenu)
                    ) {// 左边为空
                viewOne.setVisibility(View.GONE);
                viewTwo.setVisibility(View.VISIBLE);
            } else if (TextUtils.isEmpty(leftMenu) && !TextUtils.isEmpty(centerMenu) && !TextUtils.isEmpty(rightMenu)
                    ) {// 右边为空
                viewOne.setVisibility(View.VISIBLE);
                viewTwo.setVisibility(View.GONE);
            } else {// 只有一个
                viewOne.setVisibility(View.GONE);
                viewTwo.setVisibility(View.GONE);
            }

            textLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (onMenuClick != null)
                        onMenuClick.onLeftMenuClick();

                }
            });
            textCenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (onMenuClick != null)
                        onMenuClick.onCenterMenuClick();

                }
            });
            textRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (onMenuClick != null)
                        onMenuClick.onRightMenuClick();

                }
            });
            dialog.setCanceledOnTouchOutside(false);

            dialog.setContentView(view);
            dialog.setCancelable(cancelable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showEdPromptDialog(Activity activity, String title, String content, String leftMenu,
                                          String centerMenu, String rightMenu, final OnEditMenuClick onMenuClick) {
        showPromptEditDialog(activity, title, content, leftMenu, centerMenu, rightMenu, onMenuClick, null);
    }

    public static void showPromptDialog(Dialog diag, Context activity, String title, String content, String leftMenu,
                                        String centerMenu, String rightMenu, final OnMenuClick onMenuClick, String
                                                content2) {
        try {
            content = TextUtils.isEmpty(content) ? "系统提示" : content;
            if (diag == null) {
                diag = new Dialog(activity);
            }

            final Dialog dialog = diag;
            //去掉自定义dialog 在部分机型上出现的蓝色横线
            Context context = dialog.getContext();
            int divierId = context.getResources().getIdentifier("o2o:id/titleDivider", null, null);
            View divider = dialog.findViewById(divierId);
            if (divider != null) {
                divider.setBackgroundColor(Color.TRANSPARENT);
            }


            dialog.show();

            View view = LayoutInflater.from(MyApp.mContext).inflate(R.layout.common_prompt_dialog, null);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            TextView textTitle = (TextView) view.findViewById(R.id.tv_common_prompt_title);
            TextView textContent = (TextView) view.findViewById(R.id.tv_common_prompt_content);
            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.parent_layout);

            TextView textContent2 = (TextView) view.findViewById(R.id.tv_common_prompt_content2);

            if (TextUtils.isEmpty(title)) {
                textTitle.setVisibility(View.GONE);
            } else {
                textTitle.setText(title);
                textTitle.setVisibility(View.VISIBLE);
                if (title.equals("温馨提示")) {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                    params.width = ScreenUtils.getScreenDispaly(activity)[0] * 2 / 3;
                    linearLayout.setLayoutParams(params);
                }
            }
            if (TextUtils.isEmpty(content)) {
                textContent.setVisibility(View.GONE);
            } else {
                textContent.setText(content);
                textContent.setVisibility(View.VISIBLE);
            }

            if (TextUtils.isEmpty(content2)) {
                textContent2.setVisibility(View.GONE);
            } else {
                textContent2.setVisibility(View.VISIBLE);
                textContent2.setText(content2);
            }
            TextView textLeft = (TextView) view.findViewById(R.id.tv_common_prompt_left);
            TextView textCenter = (TextView) view.findViewById(R.id.tv_common_prompt_center);
            TextView textRight = (TextView) view.findViewById(R.id.tv_common_prompt_right);

            View viewOne = view.findViewById(R.id.view_one);
            View viewTwo = view.findViewById(R.id.view_two);

            if (!TextUtils.isEmpty(leftMenu)) {// 左菜单不为空
                textLeft.setVisibility(View.VISIBLE);
                textLeft.setText(leftMenu);
            } else {
                textLeft.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(centerMenu)) {
                textCenter.setVisibility(View.VISIBLE);
                textCenter.setText(centerMenu);
            } else {
                textCenter.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(rightMenu)) {
                textRight.setVisibility(View.VISIBLE);
                textRight.setText(rightMenu);
            } else {
                textRight.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(leftMenu) && !TextUtils.isEmpty(centerMenu) && !TextUtils.isEmpty(rightMenu)) {//
                // 全部都有
                viewOne.setVisibility(View.VISIBLE);
                viewTwo.setVisibility(View.VISIBLE);
            } else if (!TextUtils.isEmpty(leftMenu) && TextUtils.isEmpty(centerMenu) && !TextUtils.isEmpty(rightMenu)
                    ) {// 中间为空
                viewOne.setVisibility(View.VISIBLE);
                viewTwo.setVisibility(View.GONE);
            } else if (TextUtils.isEmpty(leftMenu) && !TextUtils.isEmpty(centerMenu) && !TextUtils.isEmpty(rightMenu)
                    ) {// 左边为空
                viewOne.setVisibility(View.GONE);
                viewTwo.setVisibility(View.VISIBLE);
            } else if (TextUtils.isEmpty(leftMenu) && !TextUtils.isEmpty(centerMenu) && !TextUtils.isEmpty(rightMenu)
                    ) {// 右边为空
                viewOne.setVisibility(View.VISIBLE);
                viewTwo.setVisibility(View.GONE);
            } else {// 只有一个
                viewOne.setVisibility(View.GONE);
                viewTwo.setVisibility(View.GONE);
            }

            textLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (onMenuClick != null)
                        onMenuClick.onLeftMenuClick();

                }
            });
            textCenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (onMenuClick != null)
                        onMenuClick.onCenterMenuClick();

                }
            });
            textRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (onMenuClick != null)
                        onMenuClick.onRightMenuClick();

                }
            });
            dialog.setCanceledOnTouchOutside(false);

            dialog.setContentView(view);
            // dialog.setCancelable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static  void showQRCodeDialog(String url, Context context) {

        Dialog diag = new Dialog(context);

        final Dialog dialog = diag;
        //去掉自定义dialog 在部分机型上出现的蓝色横线
        int divierId = context.getResources().getIdentifier("o2o:id/titleDivider", null, null);
        View divider = dialog.findViewById(divierId);
        if (divider != null) {
            divider.setBackgroundColor(Color.TRANSPARENT);
        }
        dialog.show();
        View view = LayoutInflater.from(MyApp.mContext).inflate(R.layout.dialog_qr, null);
        ImageView imageView = view.findViewById(R.id.qr_img);

        imageView.setImageBitmap(QRUtils.createQRImage(url, DensityUtil.dip2px(context, 290), DensityUtil.dip2px
                (context, 290)));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCanceledOnTouchOutside(true);

        dialog.setContentView(view);
    }

    public static void showPromptDialog(Context activity, String title, String content, String leftMenu,
                                        String centerMenu, String rightMenu, final OnMenuClick onMenuClick, String
                                                content2) {
        showPromptDialog(null, activity, title, content, leftMenu, centerMenu, rightMenu, onMenuClick, content2);

    }

    public static void showPromptDialogs(Context activity, String title, String content, String leftMenu,
                                         String centerMenu, String rightMenu, final OnMenuClick onMenuClick, String
                                                 content2) {
        try {
            content = TextUtils.isEmpty(content) ? "系统提示" : content;
            final Dialog dialog = new Dialog(activity);
            dialog.show();
            View view = LayoutInflater.from(MyApp.mContext).inflate(R.layout.common_prompt_dialog, null);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            TextView textTitle = (TextView) view.findViewById(R.id.tv_common_prompt_title);
            TextView textContent = (TextView) view.findViewById(R.id.tv_common_prompt_content);

            TextView textContent2 = (TextView) view.findViewById(R.id.tv_common_prompt_content2);

            if (TextUtils.isEmpty(title)) {
                textTitle.setVisibility(View.GONE);
            } else {
                textTitle.setText(title);
                textTitle.setVisibility(View.VISIBLE);
            }
            if (TextUtils.isEmpty(content)) {
                textContent.setVisibility(View.GONE);
            } else {
                textContent.setText(content);
                textContent.setVisibility(View.VISIBLE);
            }

            if (TextUtils.isEmpty(content2)) {
                textContent2.setVisibility(View.GONE);
            } else {
                textContent2.setVisibility(View.VISIBLE);
                textContent2.setText(content2);
            }
            TextView textLeft = (TextView) view.findViewById(R.id.tv_common_prompt_left);
            TextView textCenter = (TextView) view.findViewById(R.id.tv_common_prompt_center);
            TextView textRight = (TextView) view.findViewById(R.id.tv_common_prompt_right);

            View viewOne = view.findViewById(R.id.view_one);
            View viewTwo = view.findViewById(R.id.view_two);

            if (!TextUtils.isEmpty(leftMenu)) {// 左菜单不为空
                textLeft.setVisibility(View.VISIBLE);
                textLeft.setText(leftMenu);
            } else {
                textLeft.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(centerMenu)) {
                textCenter.setVisibility(View.VISIBLE);
                textCenter.setText(centerMenu);
            } else {
                textCenter.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(rightMenu)) {
                textRight.setVisibility(View.VISIBLE);
                textRight.setText(rightMenu);
            } else {
                textRight.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(leftMenu) && !TextUtils.isEmpty(centerMenu) && !TextUtils.isEmpty(rightMenu)) {//
                // 全部都有
                viewOne.setVisibility(View.VISIBLE);
                viewTwo.setVisibility(View.VISIBLE);
            } else if (!TextUtils.isEmpty(leftMenu) && TextUtils.isEmpty(centerMenu) && !TextUtils.isEmpty(rightMenu)
                    ) {// 中间为空
                viewOne.setVisibility(View.VISIBLE);
                viewTwo.setVisibility(View.GONE);
            } else if (TextUtils.isEmpty(leftMenu) && !TextUtils.isEmpty(centerMenu) && !TextUtils.isEmpty(rightMenu)
                    ) {// 左边为空
                viewOne.setVisibility(View.GONE);
                viewTwo.setVisibility(View.VISIBLE);
            } else if (TextUtils.isEmpty(leftMenu) && !TextUtils.isEmpty(centerMenu) && !TextUtils.isEmpty(rightMenu)
                    ) {// 右边为空
                viewOne.setVisibility(View.VISIBLE);
                viewTwo.setVisibility(View.GONE);
            } else {// 只有一个
                viewOne.setVisibility(View.GONE);
                viewTwo.setVisibility(View.GONE);
            }

            textLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (onMenuClick != null)
                        onMenuClick.onLeftMenuClick();

                }
            });
            textCenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (onMenuClick != null)
                        onMenuClick.onCenterMenuClick();

                }
            });
            textRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (onMenuClick != null)
                        onMenuClick.onRightMenuClick();

                }
            });
            // dialog.setCanceledOnTouchOutside(false);
            // 设置返回键失效
            dialog.setContentView(view);
            dialog.setCancelable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showPromptEditDialog(final Activity activity, String title, String content, String leftMenu,
                                            String centerMenu, String rightMenu, final OnEditMenuClick
                                                    onEditMenuClick, String content2) {
        try {
            content = TextUtils.isEmpty(content) ? "" : content;
            final Dialog dialog = new Dialog(activity);
            dialog.show();
            View view = LayoutInflater.from(MyApp.mContext).inflate(R.layout.common_prompt_edit_dialog,
                    null);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            TextView textTitle = (TextView) view.findViewById(R.id.tv_common_prompt_title);
            final EditText textContent = (EditText) view.findViewById(R.id.tv_common_prompt_content);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    onEditMenuClick.onCancel();
                }
            });
            TextView textContent2 = (TextView) view.findViewById(R.id.tv_common_prompt_content2);

            if (TextUtils.isEmpty(title)) {
                textTitle.setVisibility(View.GONE);
            } else {
                textTitle.setText(title);
                textTitle.setVisibility(View.VISIBLE);
            }
            // if (TextUtils.isEmpty(content)) {
            // textContent.setVisibility(View.GONE);
            // } else {
            textContent.setText(content);
            Editable etext = textContent.getText();
            Selection.setSelection(etext, etext.length());
            // textContent.setVisibility(View.VISIBLE);
            // }

            if (TextUtils.isEmpty(content2)) {
                textContent2.setVisibility(View.GONE);
            } else {
                textContent2.setVisibility(View.VISIBLE);
                textContent2.setText(content2);
            }
            TextView textLeft = (TextView) view.findViewById(R.id.tv_common_prompt_left);
            TextView textCenter = (TextView) view.findViewById(R.id.tv_common_prompt_center);
            TextView textRight = (TextView) view.findViewById(R.id.tv_common_prompt_right);

            View viewOne = view.findViewById(R.id.view_one);
            View viewTwo = view.findViewById(R.id.view_two);

            if (!TextUtils.isEmpty(leftMenu)) {// 左菜单不为空
                textLeft.setVisibility(View.VISIBLE);
                textLeft.setText(leftMenu);
            } else {
                textLeft.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(centerMenu)) {
                textCenter.setVisibility(View.VISIBLE);
                textCenter.setText(centerMenu);
            } else {
                textCenter.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(rightMenu)) {
                textRight.setVisibility(View.VISIBLE);
                textRight.setText(rightMenu);
            } else {
                textRight.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(leftMenu) && !TextUtils.isEmpty(centerMenu) && !TextUtils.isEmpty(rightMenu)) {//
                // 全部都有
                viewOne.setVisibility(View.VISIBLE);
                viewTwo.setVisibility(View.VISIBLE);
            } else if (!TextUtils.isEmpty(leftMenu) && TextUtils.isEmpty(centerMenu) && !TextUtils.isEmpty(rightMenu)
                    ) {// 中间为空
                viewOne.setVisibility(View.VISIBLE);
                viewTwo.setVisibility(View.GONE);
            } else if (TextUtils.isEmpty(leftMenu) && !TextUtils.isEmpty(centerMenu) && !TextUtils.isEmpty(rightMenu)
                    ) {// 左边为空
                viewOne.setVisibility(View.GONE);
                viewTwo.setVisibility(View.VISIBLE);
            } else if (TextUtils.isEmpty(leftMenu) && !TextUtils.isEmpty(centerMenu) && !TextUtils.isEmpty(rightMenu)
                    ) {// 右边为空
                viewOne.setVisibility(View.VISIBLE);
                viewTwo.setVisibility(View.GONE);
            } else {// 只有一个
                viewOne.setVisibility(View.GONE);
                viewTwo.setVisibility(View.GONE);
            }

            textLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (onEditMenuClick != null)
                        onEditMenuClick.onLeftEditMenuClick(textContent.getText().toString());

                }
            });
            textCenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (onEditMenuClick != null)
                        onEditMenuClick.onCenterEditMenuClick();

                }
            });
            textRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (onEditMenuClick != null)
                        onEditMenuClick.onRightEditMenuClick();

                }
            });
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Dialog showPromptDialogIcon(Activity activity, String title, String content, String leftMenu,
                                              String centerMenu, String rightMenu, final OnMenuClick onMenuClick,
                                              String content2, int resid) {
        return showPromptDialogIcon(null, activity, title, content, leftMenu, centerMenu, rightMenu, onMenuClick,
                content2, resid);
    }

    public static Dialog showPromptDialogIcon(Dialog bdialog, Context activity, String title, String content,
                                              String leftMenu, String centerMenu, String rightMenu, final OnMenuClick
                                                      onMenuClick, String content2,
                                              int resid) {
        try {
            content = TextUtils.isEmpty(content) ? "系统提示" : content;
            final Dialog dialog;
            if (bdialog == null) {
                dialog = new Dialog(activity);
            } else {
                dialog = bdialog;
            }
            dialog.show();
            View view = LayoutInflater.from(MyApp.mContext).inflate(R.layout.common_prompt_dialog, null);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            TextView textTitle = (TextView) view.findViewById(R.id.tv_common_prompt_title);
            TextView textContent = (TextView) view.findViewById(R.id.tv_common_prompt_content);

            ImageView imgIcon = (ImageView) view.findViewById(R.id.tv_common_prompt_icon);
            TextView textContent2 = (TextView) view.findViewById(R.id.tv_common_prompt_content2);

            if (TextUtils.isEmpty(title)) {
                textTitle.setVisibility(View.GONE);
            } else {
                textTitle.setText(title);
                textTitle.setVisibility(View.VISIBLE);
            }
            if (TextUtils.isEmpty(content)) {
                textContent.setVisibility(View.GONE);
            } else {
                textContent.setText(content);
                textContent.setVisibility(View.VISIBLE);
            }

            if (TextUtils.isEmpty(content2)) {
                textContent2.setVisibility(View.GONE);
            } else {
                textContent2.setVisibility(View.VISIBLE);
                textContent2.setText(content2);
            }
            TextView textLeft = (TextView) view.findViewById(R.id.tv_common_prompt_left);
            TextView textCenter = (TextView) view.findViewById(R.id.tv_common_prompt_center);
            TextView textRight = (TextView) view.findViewById(R.id.tv_common_prompt_right);

            View viewOne = view.findViewById(R.id.view_one);
            View viewTwo = view.findViewById(R.id.view_two);

            if (!TextUtils.isEmpty(leftMenu)) {// 左菜单不为空
                textLeft.setVisibility(View.VISIBLE);
                textLeft.setText(leftMenu);
            } else {
                textLeft.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(centerMenu)) {
                textCenter.setVisibility(View.VISIBLE);
                textCenter.setText(centerMenu);
            } else {
                textCenter.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(rightMenu)) {
                textRight.setVisibility(View.VISIBLE);
                textRight.setText(rightMenu);
            } else {
                textRight.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(leftMenu) && !TextUtils.isEmpty(centerMenu) && !TextUtils.isEmpty(rightMenu)) {//
                // 全部都有
                viewOne.setVisibility(View.VISIBLE);
                viewTwo.setVisibility(View.VISIBLE);
            } else if (!TextUtils.isEmpty(leftMenu) && TextUtils.isEmpty(centerMenu) && !TextUtils.isEmpty(rightMenu)
                    ) {// 中间为空
                viewOne.setVisibility(View.VISIBLE);
                viewTwo.setVisibility(View.GONE);
            } else if (TextUtils.isEmpty(leftMenu) && !TextUtils.isEmpty(centerMenu) && !TextUtils.isEmpty(rightMenu)
                    ) {// 左边为空
                viewOne.setVisibility(View.GONE);
                viewTwo.setVisibility(View.VISIBLE);
            } else if (TextUtils.isEmpty(leftMenu) && !TextUtils.isEmpty(centerMenu) && !TextUtils.isEmpty(rightMenu)
                    ) {// 右边为空
                viewOne.setVisibility(View.VISIBLE);
                viewTwo.setVisibility(View.GONE);
            } else {// 只有一个
                viewOne.setVisibility(View.GONE);
                viewTwo.setVisibility(View.GONE);
            }

            if (resid != -1) {
                imgIcon.setVisibility(View.VISIBLE);
                RotateAnimation rotateAnimation = new RotateAnimation(0, 359, Animation.RELATIVE_TO_SELF, .5f,
                        Animation.RELATIVE_TO_SELF, .5f);
                rotateAnimation.setDuration(1000);
                rotateAnimation.setInterpolator(new LinearInterpolator());
                rotateAnimation.setRepeatCount(Animation.INFINITE);
                imgIcon.startAnimation(rotateAnimation);
            } else {
                imgIcon.setVisibility(View.GONE);
            }

            textLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (onMenuClick != null)
                        onMenuClick.onLeftMenuClick();
                }
            });
            textCenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (onMenuClick != null)
                        onMenuClick.onCenterMenuClick();
                }
            });
            textRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (onMenuClick != null)
                        onMenuClick.onRightMenuClick();
                }
            });
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(view);
            return dialog;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public interface OnMenuClick {
        public void onLeftMenuClick();

        public void onCenterMenuClick();

        public void onRightMenuClick();
    }

    public interface CommonClick {
        public void onEnsureClick(String msg);

        public void onCancelClick();

    }

    public interface OnEditMenuClick {
        public void onLeftEditMenuClick(String content);

        public void onCenterEditMenuClick();

        public void onRightEditMenuClick();

        public void onCancel();
    }

    public interface OnDiffMenuClick {
        public void onLeftMenuClick();

        public void onCheckMenuClick(boolean isCheck);

        public void onRightMenuClick();
    }

    public interface OnEventListener<T> {
        public void eventListener(View parentView, T window);
    }


    public interface MyClick {
        void onclikme();
    }

    /**
     * 显示密码输入框
     *
     * @param dialog
     * @param context
     * @param title
     * @param sure
     * @param back
     * @return
     */

}
