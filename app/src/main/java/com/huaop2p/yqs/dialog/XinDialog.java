package com.huaop2p.yqs.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.huaop2p.yqs.R;

/**
 * Created by zgq on 2016/5/31.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/31 17:24
 * 功能: 显示信息的diglog
 */
public class XinDialog extends Dialog {
    public XinDialog(Context context) {
        super(context);
    }

    public XinDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context; //上下文对象
        private String message; //对话框内容
        private String message2; //对话框内容
        private String message3; //对话框内容
        private String message4; //对话框内容
        private String message5; //对话框内容
        private String message6; //对话框内容
        private String confirm_btnText; //按钮名称“确定”
        private String cancel_btnText; //按钮名称“取消”
        private View contentView; //对话框中间加载的其他布局界面

        /*按钮坚挺事件*/
        private DialogInterface.OnClickListener confirm_btnClickListener;
        private DialogInterface.OnClickListener cancel_btnClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        /*设置对话框信息*/
        public Builder setMessage2(String message) {
            this.message2 = message;
            return this;
        }


        public Builder setMessage2(int message) {
            this.message2 = (String) context.getText(message);
            return this;
        }

        public Builder setMessage3(String message) {
            this.message3 = message;
            return this;
        }


        public Builder setMessage3(int message) {
            this.message3 = (String) context.getText(message);
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }


        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }
        public Builder setMessage4(String message) {
            this.message4 = message;
            return this;
        }


        public Builder setMessage4(int message) {
            this.message4 = (String) context.getText(message);
            return this;
        }
        public Builder setMessage5(String message) {
            this.message5 = message;
            return this;
        }


        public Builder setMessage5(int message) {
            this.message5 = (String) context.getText(message);
            return this;
        }
        public Builder setMessage6(String message) {
            this.message6 = message;
            return this;
        }


        public Builder setMessage6(int message) {
            this.message6 = (String) context.getText(message);
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
                                         DialogInterface.OnClickListener listener) {
            this.confirm_btnText = (String) context
                    .getText(confirm_btnText);
            this.confirm_btnClickListener = listener;
            return this;
        }


        public Builder setPositiveButton(String confirm_btnText,
                                         DialogInterface.OnClickListener listener) {
            this.confirm_btnText = confirm_btnText;
            this.confirm_btnClickListener = listener;
            return this;

        }

        public Builder setNegativeButton(int cancel_btnText,
                                         DialogInterface.OnClickListener listener) {
            this.cancel_btnText = (String) context
                    .getText(cancel_btnText);
            this.cancel_btnClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String cancel_btnText,
                                         DialogInterface.OnClickListener listener) {
            this.cancel_btnText = cancel_btnText;
            this.cancel_btnClickListener = listener;
            return this;
        }

        /**
         * @param isShowInput 是否显示 输入对话框
         * @return
         */
        public CustomDialog create(boolean isShowInput) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final CustomDialog dialog = new CustomDialog(context, R.style.my_dialog_style);
            View layout = inflater.inflate(R.layout.customer_dialog, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            // set the dialog title
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
            } else {
                layout.findViewById(R.id.confirm_btn).setVisibility(
                        View.GONE);
            }
            if (cancel_btnText != null) {
                ((Button) layout.findViewById(R.id.cancel_btn))
                        .setText(cancel_btnText);
                if (cancel_btnClickListener != null) {
                    ((Button) layout.findViewById(R.id.cancel_btn))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    cancel_btnClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                layout.findViewById(R.id.cancel_btn).setVisibility(
                        View.GONE);
            }
            if (message != null || message2 != null || message3 != null|| message4 != null || message5 != null || message6 != null) {
                ((TextView) layout.findViewById(R.id.title1)).setText(message);
                ((TextView) layout.findViewById(R.id.title2)).setText(message2);
                ((TextView) layout.findViewById(R.id.title3)).setText(message3);
                ((TextView) layout.findViewById(R.id.t_name)).setText(message4);
                ((TextView) layout.findViewById(R.id.t_type)).setText(message5);
                ((TextView) layout.findViewById(R.id.t_code)).setText(message6);
            }
            dialog.setContentView(layout);
            return dialog;
        }


    }

}
