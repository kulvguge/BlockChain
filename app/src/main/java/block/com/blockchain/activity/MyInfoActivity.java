package block.com.blockchain.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.io.File;

import block.com.blockchain.R;
import block.com.blockchain.bean.MotifyUserBean;
import block.com.blockchain.bean.ResultInfo;
import block.com.blockchain.bean.UserBean;
import block.com.blockchain.customview.BasicEditInfoView;
import block.com.blockchain.customview.BasicInfoView;
import block.com.blockchain.request.HttpSendClass;
import block.com.blockchain.request.SenUrlClass;
import block.com.blockchain.utils.DialogUtil;
import block.com.blockchain.utils.FileUtils;
import block.com.blockchain.utils.PhoneAdapterUtils;
import block.com.blockchain.utils.SDCardUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ts on 2018/5/14.
 */

public class MyInfoActivity extends BaseActivity {

    @BindView(R.id.small_img)
    ImageView smallImg;
    @BindView(R.id.person_nick_name)
    TextView personNickName;
    @BindView(R.id.person_name)
    BasicEditInfoView personName;
    @BindView(R.id.person_phone)
    BasicEditInfoView personPhone;
    @BindView(R.id.person_sex)
    BasicInfoView personSex;
    @BindView(R.id.person_birthday)
    BasicInfoView personBirthday;
    @BindView(R.id.person_work)
    BasicEditInfoView personWork;
    @BindView(R.id.person_signature)
    BasicEditInfoView personSignature;
    @BindView(R.id.person_title)
    private Toolbar personTitle;
    private File picFile;
    private String upLoadPath = "";
    private PopupWindow popupWindow;//性别选择弹框
    protected static final int IMAGE_ALBUM = 155;// 相册
    protected static final int IMAGE_TAK = 154; // 拍照
    private MotifyUserBean oldUserBean = null;

    @Override
    public void init() {
        setContentView(R.layout.activity_my_info);
        ButterKnife.bind(this);
        getUserInfo();
        smallImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgChoose();
            }
        });
    }

    /**
     * 获取资料
     */
    private void getUserInfo() {
        AjaxParams params = new AjaxParams();
        params.put("type", 1 + "");
        HttpSendClass.getInstance().getWithToken(params, SenUrlClass.TOKEN, new
                AjaxCallBack<ResultInfo<MotifyUserBean>>() {
                    @Override
                    public void onSuccess(ResultInfo<MotifyUserBean> resultInfo) {
                        super.onSuccess(resultInfo);
                        if (resultInfo.status.equals("success")) {
                            oldUserBean = resultInfo.data;
                            dataSet(oldUserBean);
                        } else {
                            Toast.makeText(MyInfoActivity.this, resultInfo.message, Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Throwable t, String strMsg) {
                        super.onFailure(t, strMsg);
                        Toast.makeText(MyInfoActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 修改资料
     */
    private void motify() {
        AjaxParams params = new AjaxParams();
        params.put("type", 1 + "");
        HttpSendClass.getInstance().getWithToken(params, SenUrlClass.TOKEN, new
                AjaxCallBack<ResultInfo<UserBean>>() {
                    @Override
                    public void onSuccess(ResultInfo<UserBean> resultInfo) {
                        super.onSuccess(resultInfo);
                        if (resultInfo.status.equals("success")) {
                            dataSet(resultInfo.data);
                        } else {
                            Toast.makeText(MyInfoActivity.this, resultInfo.message, Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Throwable t, String strMsg) {
                        super.onFailure(t, strMsg);
                        Toast.makeText(MyInfoActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 数据组装
     *
     * @param userBean
     */
    private void dataSet(UserBean userBean) {
        personNickName.setText(userBean.getNickname());
        personName.setRightMsg(userBean.getReal_name());
        if (userBean.getSex() == 1) {
            personSex.setRightMsg(R.string.person_sex_man);
        } else if (userBean.getSex() == 2) {
            personSex.setRightMsg(R.string.person_sex_woman);
        }
        personPhone.setRightMsg(userBean.getMobile());
        personBirthday.setRightMsg(userBean.getBirthday());
        personWork.setRightMsg(userBean.getEnterprise());
        personSignature.setRightMsg(userBean.getSelf_sign());
        Glide.with(this).load(userBean.getPic_url()).into(smallImg);
    }

    /**
     * 图片选择弹出框
     */
    private void imgChoose() {
        showPopwindow("拍照", "相册", "取消", new OnPopWindowClickLisenter() {
            @Override
            public void onButtonOne() {
                if (Environment.getExternalStorageState() != null) {
                    String filePath = SDCardUtils.getCacheDir(MyInfoActivity.this) + "/" + System.currentTimeMillis
                            () + ".jpg";
                    picFile = FileUtils.createFile(filePath);
                }
                if (Build.VERSION.SDK_INT >= 23) {

                    if (PackageManager.PERMISSION_GRANTED == ContextCompat
                            .checkSelfPermission(MyInfoActivity.this,
                                    Manifest.permission.CAMERA)) {
                        // 启动拍照,并保存到临时文件
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picFile));
                        startActivityForResult(intent, IMAGE_TAK);
                    } else {
                        ActivityCompat.requestPermissions(MyInfoActivity.this, new String[]{Manifest.permission
                                .CAMERA}, 1);
                    }
                } else {
                    // 启动拍照,并保存到临时文件
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picFile));
                    startActivityForResult(intent, IMAGE_TAK);

                }
            }

            @Override
            public void onButtonTwo() {
                if (Environment.getExternalStorageState() != null) {
                    String filePath = SDCardUtils.getCacheDir(MyInfoActivity.this) + "/" + System.currentTimeMillis
                            () + ".jpg";
                    picFile = FileUtils.createFile(filePath);
                }
                if (Build.VERSION.SDK_INT >= 23) {

                    if (PackageManager.PERMISSION_GRANTED != ContextCompat
                            .checkSelfPermission(MyInfoActivity.this,
                                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        ActivityCompat.requestPermissions(MyInfoActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                2);
                    } else {
                        Intent intent2 = new Intent(Intent.ACTION_PICK, null);
                        intent2.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent2, IMAGE_ALBUM);
                    }
                } else {
                    Intent intent2 = new Intent(Intent.ACTION_PICK, null);
                    intent2.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent2, IMAGE_ALBUM);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 启动拍照,并保存到临时文件
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picFile));
                startActivityForResult(intent, IMAGE_TAK);

            } else {
                Toast.makeText(MyInfoActivity.this, this.getResources().getString(R.string.app_name), Toast
                        .LENGTH_SHORT).show();
            }
        } else if (requestCode == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent2 = new Intent(Intent.ACTION_PICK, null);
                intent2.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent2, IMAGE_ALBUM);
            } else {
                Toast.makeText(MyInfoActivity.this, this.getResources().getString(R.string.no_permission), Toast
                        .LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMAGE_ALBUM:
            case IMAGE_TAK:
                if (resultCode == RESULT_OK) {
                    upLoadPath = getAbsPath(data);
                }
                break;
        }
    }

    /**
     * 获取相册中的绝对路径
     *
     * @param data
     * @return
     */
    private String getAbsPath(Intent data) {
        if (data == null || data.getData() == null) {
            return picFile.getAbsolutePath();
        }

        Uri uri = PhoneAdapterUtils.geturi(data, this);
        String[] proj = {MediaStore.Images.Media.DATA};

        // 好像是android多媒体数据库的封装接口，具体的看Android文档
        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
        // 按我个人理解 这个是获得用户选择的图片的索引值
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        // 将光标移至开头 ，这个很重要，不小心很容易引起越界
        cursor.moveToFirst();
        // 最后根据索引值获取图片路径
        String path = cursor.getString(column_index);

        return path;
    }

    /**
     * 性别选择弹出框
     */
    protected void showPopwindow(String text1, String text2, String text3, final OnPopWindowClickLisenter
            onPopWindowClickLisenter) {
        final String textStr1 = TextUtils.isEmpty(text1) == true ? "" : text1;
        final String textStr2 = TextUtils.isEmpty(text1) == true ? "" : text2;
        final String textStr3 = TextUtils.isEmpty(text1) == true ? "" : text3;
        DialogUtil.showPopupWindow(this, R.layout.layout_photo_camera, new DialogUtil.OnEventListener() {
            @Override
            public void eventListener(View parentView, Object window) {
                popupWindow = (PopupWindow) window;
                TextView view1 = (TextView) parentView.findViewById(R.id.tv_from_camera);
                TextView view2 = (TextView) parentView.findViewById(R.id.tv_from_photos);
                TextView view3 = (TextView) parentView.findViewById(R.id.tv_cancel);
                view1.setText(textStr1);
                view2.setText(textStr2);
                view3.setText(textStr3);
                view1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        if (onPopWindowClickLisenter != null)
                            onPopWindowClickLisenter.onButtonOne();

                    }
                });
                view2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        if (onPopWindowClickLisenter != null)
                            onPopWindowClickLisenter.onButtonTwo();

                    }
                });
                view3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    }
                });
            }
        });
    }

    public interface OnPopWindowClickLisenter {
        void onButtonOne();

        void onButtonTwo();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showView("修改资料", "资料已经发生改变\n是否保存修改", "确定", "取消");
        }
        return super.onKeyUp(keyCode, event);
    }

    // 返回弹框
    private void showView(String title, String count, String left, String right) {

        DialogUtil.showPromptDialog(MyInfoActivity.this, title, count, left, null, right,
                new DialogUtil.OnMenuClick() {

                    @Override
                    public void onRightMenuClick() {
                        finish();
                    }

                    @Override
                    public void onLeftMenuClick() {

                    }

                    @Override
                    public void onCenterMenuClick() {


                    }
                }, "");
    }
}
