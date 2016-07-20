package com.huaop2p.yqs.module.one_home.entity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.view.ClearEditText;


/**
 * 仿    ios 对话框
 * Created by yindongli on 2015/3/31.
 */
public class CustomToastDialog extends Dialog {

    public CustomToastDialog(Context context) {
        super(context);
    }

    public CustomToastDialog(Context context, int theme) {
        super(context, theme);
    }


    public static class Builder {
        public Context context; //上下文对象
        public String title; //对话框标题
        public String message; //对话框内容
        public String confirm_btnText; //按钮名称“确定”
        public String cancel_btnText; //按钮名称“取消”
        public View contentView; //对话框中间加载的其他布局界面
        public ClearEditText inputText;

        /*按钮坚挺事件*/
        public OnClickListener confirm_btnClickListener;
        public OnClickListener cancel_btnClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        /*设置对话框信息*/
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * 设置对话框界面
         *
         * @param v View
         * @return
         */
        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public Builder setPositiveButton(int confirm_btnText,
                                         OnClickListener listener) {
            this.confirm_btnText = (String) context
                    .getText(confirm_btnText);
            this.confirm_btnClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String confirm_btnText, OnClickListener listener) {
            this.confirm_btnText = confirm_btnText;
            this.confirm_btnClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int cancel_btnText,
                                         OnClickListener listener) {
            this.cancel_btnText = (String) context
                    .getText(cancel_btnText);
            this.cancel_btnClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String cancel_btnText,
                                         OnClickListener listener) {
            this.cancel_btnText = cancel_btnText;
            this.cancel_btnClickListener = listener;
            return this;
        }

        public CustomDialog create(boolean isShowInput) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final CustomDialog dialog = new CustomDialog(context, R.style.my_dialog_style);
            View layout = inflater.inflate(R.layout.custom_toast_dialog, null);
            dialog.addContentView(layout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            // set the dialog title
            ((TextView) layout.findViewById(R.id.title)).setText(title);
            ((TextView) layout.findViewById(R.id.title)).getPaint().setFakeBoldText(true);
            inputText = (ClearEditText) layout.findViewById(R.id.edittext);
            if (isShowInput)
                inputText.setVisibility(View.VISIBLE);
            // set the confirm button
            if (confirm_btnText != null) {
                ((Button) layout.findViewById(R.id.confirm_btn))
                        .setText(confirm_btnText);
                if (confirm_btnClickListener != null) {
                    ((Button) layout.findViewById(R.id.confirm_btn))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    confirm_btnClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            }
            // set the cancel button
            // set the content message
            if (message != null) {
                ((TextView) layout.findViewById(R.id.message)).setText(message);
            }
//            else if (contentView != null) {
//                ((LinearLayout) layout.findViewById(R.id.message))
//                        .removeAllViews();
//                ((LinearLayout) layout.findViewById(R.id.message)).addView(
//                        contentView, new ViewGroup.LayoutParams(
//                                LayoutParams.WRAP_CONTENT,
//                                LayoutParams.WRAP_CONTENT));
//            }
            dialog.setContentView(layout);
            return dialog;
        }

    }
}
