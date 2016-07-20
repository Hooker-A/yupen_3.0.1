package com.huaop2p.yqs.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.huaop2p.yqs.R;

/**
 * Created by zgq on 2016/5/24.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/24 17:36
 * 功能:  dialog
 */
public class ShowDialog extends Dialog {
    public ShowDialog(Context context) {
        super(context);
    }

    public ShowDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static class Builder {
        private Context context; //上下文对象
        private String title; //对话框标题
        private String message; //对话框内容
        private String confirm_btnText; //按钮名称“确定”
        private String cancel_btnText; //按钮名称“取消”
        private View contentView; //对话框中间加载的其他布局界面
        private int imageView;



        /*按钮坚挺事件*/
        private DialogInterface.OnClickListener confirm_btnClickListener;
        private DialogInterface.OnClickListener cancel_btnClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        /*设置对话框信息*/
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }
        public Builder setImag(int imag){
            this.imageView=imag;
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
            View layout = inflater.inflate(R.layout.customer_dialog_width_ios, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            // set the dialog title
            ((TextView) layout.findViewById(R.id.title)).setText(title);
            ((TextView) layout.findViewById(R.id.title)).getPaint().setFakeBoldText(true);
            // set the confirm button
            if (confirm_btnText != null) {
                ((Button) layout.findViewById(R.id.confirm_btn))
                        .setText(confirm_btnText);
                if (imageView!=0){
                    ((ImageView)layout.findViewById(R.id.img_dig)).setImageResource(imageView);
                }
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
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.confirm_btn).setVisibility(
                        View.GONE);
            }
            // set the cancel button
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
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.cancel_btn).setVisibility(
                        View.GONE);
            }
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

        /**
         * 显示一个按钮的dialog
         *
         * @param
         * @return
         */
        public CustomDialog createOneBtn(boolean isShowInput) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final CustomDialog dialog = new CustomDialog(context, R.style.my_dialog_style);
            View layout = null;
            layout = inflater.inflate(R.layout.customer_dialog_width_ios, null);

            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            // set the dialog title
            ((TextView) layout.findViewById(R.id.title)).setText(title);
            ((TextView) layout.findViewById(R.id.title)).getPaint().setFakeBoldText(true);
            // set the confirm button
            if (confirm_btnText != null) {
                ((Button) layout.findViewById(R.id.confirm_btn))
                        .setText(confirm_btnText);
                if (imageView!=0){
                    ((ImageView)layout.findViewById(R.id.img_dig)).setImageResource(imageView);
                }
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
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.confirm_btn).setVisibility(View.GONE);
            }
            // set the cancel button
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
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.cancel_btn).setVisibility(
                        View.GONE);
            }
            // set the content message
            if (message != null) {
                ((TextView) layout.findViewById(R.id.message)).setText(message);
            } else {
                ((TextView) layout.findViewById(R.id.message)).setVisibility(View.GONE);
            }

            dialog.setContentView(layout);
            return dialog;
        }

        /**
         * 显示一个按钮的彩色的dialog
         *
         * @param
         * @return
         */
        public CustomDialog createOneBtnColor(boolean isShowInput) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final CustomDialog dialog = new CustomDialog(context, R.style.my_dialog_style);
            View layout = null;
            layout = inflater.inflate(R.layout.customer_dialog_1btn_width_color, null);

            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            // set the dialog title
            ((TextView) layout.findViewById(R.id.title)).setText(title);
            ((TextView) layout.findViewById(R.id.title)).getPaint().setFakeBoldText(true);
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
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.confirm_btn).setVisibility(View.GONE);
            }

            // set the content message
            if (message != null) {
                ((TextView) layout.findViewById(R.id.message)).setText(message);
            } else {
                ((TextView) layout.findViewById(R.id.message)).setVisibility(View.GONE);
            }

            dialog.setContentView(layout);
            return dialog;
        }

        /**
         * @param isShowInput 是否显示 输入对话框
         * @return
         */
        public CustomDialog create1(boolean isShowInput) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final CustomDialog dialog = new CustomDialog(context, R.style.my_dialog_style);
            View layout = inflater.inflate(R.layout.customer_dialog_width_ios, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            // set the dialog title
            ((TextView) layout.findViewById(R.id.title)).setText(title);
            ((TextView) layout.findViewById(R.id.title)).getPaint().setFakeBoldText(true);
            if (confirm_btnText != null)

            {
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
            } else

            {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.confirm_btn).setVisibility(
                        View.GONE);
            }
            // set the cancel button
            if (cancel_btnText != null)

            {
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
            } else

            {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.cancel_btn).setVisibility(
                        View.GONE);
            }
            // set the content message
            if (message != null)

            {
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
