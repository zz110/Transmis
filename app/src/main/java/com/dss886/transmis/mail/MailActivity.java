package com.dss886.transmis.mail;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.dss886.transmis.R;
import com.dss886.transmis.base.App;
import com.dss886.transmis.base.BaseActivity;
import com.dss886.transmis.utils.DialogBuilder;
import com.dss886.transmis.utils.Tags;
import com.dss886.transmis.view.SectionItem;
import com.dss886.transmis.view.TextItem;

public class MailActivity extends BaseActivity {

    private TextItem mHostItem;
    private TextItem mPortItem;
    private TextItem mSendMailItem;
    private TextItem mSendPasswordItem;
    private TextItem mSendNameItem;
    private TextItem mReceiveItem;

    @Override
    protected int getToolbarTitle() {
        return R.string.mail_title;
    }

    @Override
    protected boolean showToolbarBackIcon() {
        return true;
    }

    @Override
    protected void addViews() {
        mHostItem = new TextItem(this, "SMTP服务器").setCallback(sp -> {
            String value = sp.getString(Tags.SP_MAIL_HOST, null);
            return TextUtils.isEmpty(value) ? "未设置" : value;
        });
        mPortItem = new TextItem(this, "端口号").setCallback(sp -> {
            String value = sp.getString(Tags.SP_MAIL_PORT, null);
            return TextUtils.isEmpty(value) ? "未设置" : value;
        });
        mSendNameItem = new TextItem(this, "发件人昵称").setCallback(sp -> {
            String value = sp.getString(Tags.SP_MAIL_SEND_NAME, "默认");
            return TextUtils.isEmpty(value) ? "未设置" : value;
        });
        mSendMailItem = new TextItem(this, "发件人邮箱").setCallback(sp -> {
            String value = sp.getString(Tags.SP_MAIL_SEND_MAIL, null);
            return TextUtils.isEmpty(value) ? "未设置" : value;
        });
        mSendPasswordItem = new TextItem(this, "发件人密码/授权码").asPassword().setCallback(sp -> {
            String value = sp.getString(Tags.SP_MAIL_SEND_PASSWORD, null);
            return TextUtils.isEmpty(value) ? "未设置" : value;
        });
        mReceiveItem = new TextItem(this, "收件人邮箱").setCallback(sp -> {
            String value = sp.getString(Tags.SP_MAIL_RECEIVE_MAIL, null);
            return TextUtils.isEmpty(value) ? "未设置" : value;
        });

        addView(new SectionItem(this, "服务器设置"));
        addView(mHostItem);
        addView(mPortItem);
        addView(new SectionItem(this, "发件人设置"));
        addView(mSendNameItem);
        addView(mSendMailItem);
        addView(mSendPasswordItem);
        addView(new SectionItem(this, "收件人设置"));
        addView(mReceiveItem);
    }

    @Override
    protected void setListeners() {
        setTextItemListener(mHostItem, Tags.SP_MAIL_HOST, "设置服务器", false);
        setTextItemListener(mPortItem, Tags.SP_MAIL_PORT, "设置端口号", false);
        setTextItemListener(mSendNameItem, Tags.SP_MAIL_SEND_NAME, "设置发件人昵称", false);
        setTextItemListener(mSendMailItem, Tags.SP_MAIL_SEND_MAIL, "设置发件人邮箱", false);
        setTextItemListener(mSendPasswordItem, Tags.SP_MAIL_SEND_PASSWORD, "设置发件人密码/授权码", true);
        setTextItemListener(mReceiveItem, Tags.SP_MAIL_RECEIVE_MAIL, "设置收件人邮箱", false);
    }

    private void setTextItemListener(TextItem item, String key, String showTitle, boolean isPassword) {
        item.setOnClickListener(v -> {
            String value = App.sp.getString(key, null);
            DialogBuilder.showEditTextDialog(this, showTitle, value, isPassword, content -> {
                SharedPreferences.Editor editor = App.sp.edit();
                if (TextUtils.isEmpty(content)) {
                    editor.remove(key);
                } else {
                    editor.putString(key, content);
                }
                editor.apply();
                item.onResume();
            });
        });
    }

}
