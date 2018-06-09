package block.com.blockchain.utils;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import block.com.blockchain.bean.ContactsBean;
import block.com.blockchain.bean.UserBean;

/**
 * Created by Administrator on 2018/6/8.
 */

public class PhoneUtils {

    public static  List<UserBean> getContactsInfo(Context context) {
        List<UserBean> list = new ArrayList<>();
        try {
            Uri contactUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            Cursor cursor = context.getContentResolver().query(contactUri,
                    new String[]{"display_name", "sort_key", "contact_id", "data1"},
                    null, null, "sort_key");
            String contactName;
            String contactNumber;
            String contactSortKey;
//            int contactId;
            while (cursor.moveToNext()) {
                contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone
                        .DISPLAY_NAME));
                contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
               // contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                contactSortKey = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone
                        .SORT_KEY_PRIMARY));
                UserBean contactsInfo = new UserBean();
                contactsInfo.setNickname(contactName);
                contactsInfo.setMobile(contactNumber);
                if (contactName != null)
                    list.add(contactsInfo);
            }
            cursor.close();//使用完后一定要将cursor关闭，不然会造成内存泄露等问题

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            context = null;
            return list;
        }
    }
    // 一个添加联系人信息的例子
    public static void addContact(String name, String phoneNumber,Context context) {
        // 创建一个空的ContentValues
        ContentValues values = new ContentValues();

        // 向RawContacts.CONTENT_URI空值插入，
        // 先获取Android系统返回的rawContactId
        // 后面要基于此id插入值
        Uri rawContactUri = context.getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);
        values.clear();

        values.put(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, rawContactId);
        // 内容类型
        values.put(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        // 联系人名字
        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name);
        // 向联系人URI添加联系人名字
        context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
        values.clear();

        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        // 联系人的电话号码
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber);
        // 电话类型
        values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        // 向联系人电话号码URI添加电话号码
        context. getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
        values.clear();
//
//        values.put(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, rawContactId);
//        values.put(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
//        // 联系人的Email地址
//        values.put(ContactsContract.CommonDataKinds.Email.DATA, "zhangphil@xxx.com");
//        // 电子邮件的类型
//        values.put(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK);
//        // 向联系人Email URI添加Email数据
//        context. getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);

    }
    /**
     * 获取字符串首字母
     *
     * @param sortKeyString
     * @return
     */
    public static String getSortkey(String sortKeyString) {
        if (TextUtils.isEmpty(sortKeyString))
            return "#";
        try {
            String key = sortKeyString.substring(0, 1);
            if (key.matches("[A-Z]") || key.matches("[a-z]")) {
                return key.toUpperCase();
            } else {
                return "#";   //获取sort key的首个字符，如果是英文字母就直接返回，否则返回#。
            }
        } catch (Exception ex) {
            return "#";   //获取sort key的首个字符，如果是英文字母就直接返回，否则返回#。
        }
    }
}
