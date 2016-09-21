package com.sp.youseedialoglib;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by songyuan on 2016/9/9.
 */
public class YouSeeDialog extends Dialog implements View.OnClickListener {


    //样式
    public static final int D_PROGRESS_WHEEL_TYPE = 0;
    public static final int D_SIMPLE_BUTTON_TYPE = 1;

    //配色缺省
    public static final String C_MATCHING_SIMPLE_TYPE = "0";
    public static final String C_MATCHING_WARN_TYPE = "1";
    public static final String C_MATCHING_ERROR_TYPE = "2";
    //TODO 配色自定 如不是以上缺省值，则使用用户传递的色值进行处理(准备这样做)
    private int mDialogStyle = D_SIMPLE_BUTTON_TYPE;
    private String mColorMatchType = C_MATCHING_SIMPLE_TYPE;

    private AnimationSet mDialogInAnim = null;
    private AnimationSet mDialogOutAnim = null;

    private View dialog_view = null;
    private LinearLayout dialog_content_ll = null;
    private LinearLayout simple_view_ll = null;
    private LinearLayout progress_view_ll = null;
    private TextView title_tv = null;
    private TextView content_tv = null;
    private Button cancel_btn = null;
    private Button confirm_btn = null;
    private View btn_center_view = null;

    private YouSeeDialogListener mYouSeeDialogListener = null;
    private boolean mIsCancel = false;

    private String mTitleText = null;
    private String mContentText = null;
    private String mCancelBtnText = null;
    private String mConfirmBtnText = null;
    private boolean mTitleVisible = false;
    private boolean mContentVisible = false;
    private boolean mCancelBtnVisible = false;
    private boolean mConfirmBtnVisible = false;

    public YouSeeDialog(Context context) {
        super(context, R.style.theme_base_style_ys_d);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        mDialogInAnim = (AnimationSet) AnimationUtils.loadAnimation(getContext(), R.anim.dialog_in);
        mDialogOutAnim = (AnimationSet) AnimationUtils.loadAnimation(getContext(), R.anim.dialog_out);
        mDialogOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dialog_content_ll.setVisibility(View.GONE);
                if (mIsCancel) {
                    YouSeeDialog.super.cancel();
                } else {
                    YouSeeDialog.super.dismiss();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
    }

    @Override
    public void setCanceledOnTouchOutside(boolean flag) {
        super.setCanceledOnTouchOutside(flag);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("see-dialog", "onCreate");
        setContentView(R.layout.you_see_dialog);
        dialog_view = getWindow().getDecorView().findViewById(android.R.id.content);
        dialog_content_ll = (LinearLayout) findViewById(R.id.dialog_content_ll);
        simple_view_ll = (LinearLayout) findViewById(R.id.simple_view_ll);
        progress_view_ll = (LinearLayout) findViewById(R.id.progress_view_ll);
        title_tv = (TextView) findViewById(R.id.title_tv);
        content_tv = (TextView) findViewById(R.id.content_tv);
        cancel_btn = (Button) findViewById(R.id.cancel_btn);
        confirm_btn = (Button) findViewById(R.id.confirm_btn);
        btn_center_view = findViewById(R.id.btn_center_view);
        cancel_btn.setOnClickListener(this);
        confirm_btn.setOnClickListener(this);
        setDialogStyle(mDialogStyle);
        setBtnDialogColor(mColorMatchType);
        setTitleText(mTitleText);
        setTitleVisibility(mTitleVisible);
        setContentText(mContentText);
        setContentVisibility(mContentVisible);
        setCancelBtnText(mCancelBtnText);
        setCancelBtnVisibility(mCancelBtnVisible);
        setConfirmBtnText(mConfirmBtnText);
        setConfirmBtnVisibility(mConfirmBtnVisible);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("see-dialog", "onStart");
        dialog_view.startAnimation(mDialogInAnim);

    }

    @Override
    public void cancel() {
        //动画完成的回调中super
        outAnimationStart(true);
    }

    @Override
    public void dismiss() {
        //动画完成的回调中super
        outAnimationStart(false);
    }

    public YouSeeDialog setBtnDialogColor(String colorMatchType) {
        if (!TextUtils.isEmpty(colorMatchType)) {
            mColorMatchType = colorMatchType;
            if (dialog_content_ll != null && confirm_btn != null) {
                if (mColorMatchType.equals(C_MATCHING_SIMPLE_TYPE)) {
                    dialog_content_ll.setBackgroundResource(R.drawable.dialog_bg_simple);
                    confirm_btn.setBackgroundResource(R.drawable.btn_bg_simple_ys_d);
                } else if (mColorMatchType.equals(C_MATCHING_WARN_TYPE)) {
                    dialog_content_ll.setBackgroundResource(R.drawable.dialog_bg_warn);
                    confirm_btn.setBackgroundResource(R.drawable.btn_bg_warn_ys_d);
                } else if (mColorMatchType.equals(C_MATCHING_ERROR_TYPE)) {
                    dialog_content_ll.setBackgroundResource(R.drawable.dialog_bg_error);
                    confirm_btn.setBackgroundResource(R.drawable.btn_bg_error_ys_d);
                } else {
                    diyColorMatch();
                }
            }
        }
        return this;
    }


    private void diyColorMatch(){
        if(!TextUtils.isEmpty(mColorMatchType) && mColorMatchType.startsWith("#")){





        }
    }

    public YouSeeDialog setDialogStyle(int dialogStyle) {
        mDialogStyle = dialogStyle;
        if (simple_view_ll != null && progress_view_ll != null) {
            if (mDialogStyle == D_SIMPLE_BUTTON_TYPE) {
                simple_view_ll.setVisibility(View.VISIBLE);
                progress_view_ll.setVisibility(View.GONE);
            } else if (mDialogStyle == D_PROGRESS_WHEEL_TYPE) {
                progress_view_ll.setVisibility(View.VISIBLE);
                simple_view_ll.setVisibility(View.GONE);
            }
        }
        return this;
    }

    private void outAnimationStart(boolean isCancel) {
        mIsCancel = isCancel;
        dialog_view.startAnimation(mDialogOutAnim);
    }

    public YouSeeDialog setTitleVisibility(boolean visible) {
        mTitleVisible = visible;
        if (title_tv != null) {
            if (mTitleVisible) {
                title_tv.setVisibility(View.VISIBLE);
            } else {
                title_tv.setVisibility(View.GONE);
            }
        }
        return this;
    }

    public YouSeeDialog setTitleText(String str) {
        mTitleText = str;
        if (!TextUtils.isEmpty(mTitleText)) {
            setTitleVisibility(true);
            if (title_tv != null) {
                title_tv.setText(mTitleText);
            }
        }
        return this;
    }

    public YouSeeDialog setContentVisibility(boolean visible) {
        mContentVisible = visible;
        if (content_tv != null) {
            if (mContentVisible) {
                content_tv.setVisibility(View.VISIBLE);
            } else {
                content_tv.setVisibility(View.GONE);
            }
        }

        return this;
    }

    public YouSeeDialog setContentText(String str) {
        mContentText = str;
        if (!TextUtils.isEmpty(mContentText)) {
            setContentVisibility(true);
            if (content_tv != null) {
                content_tv.setText(mContentText);
            }
        }
        return this;
    }


    public YouSeeDialog setConfirmBtnVisibility(boolean visible) {
        mConfirmBtnVisible = visible;
        if (confirm_btn != null) {
            if (mConfirmBtnVisible) {
                confirm_btn.setVisibility(View.VISIBLE);
            } else {
                btn_center_view.setVisibility(View.GONE);
                confirm_btn.setVisibility(View.GONE);
            }
        }
        return this;
    }

    public YouSeeDialog setConfirmBtnText(String str) {
        mConfirmBtnText = str;
        if (!TextUtils.isEmpty(mConfirmBtnText)) {
            setConfirmBtnVisibility(true);
            if (confirm_btn != null) {
                confirm_btn.setText(mConfirmBtnText);
            }

        }
        return this;
    }

    public YouSeeDialog setCancelBtnVisibility(boolean visible) {
        mCancelBtnVisible = visible;
        if (cancel_btn != null) {
            if (mCancelBtnVisible) {
                cancel_btn.setVisibility(View.VISIBLE);
            } else {
                btn_center_view.setVisibility(View.GONE);
                cancel_btn.setVisibility(View.GONE);
            }
        }
        return this;
    }

    public YouSeeDialog setCancelBtnText(String str) {
        mCancelBtnText = str;
        if (!TextUtils.isEmpty(mCancelBtnText)) {
            setCancelBtnVisibility(true);
            if (cancel_btn != null) {
                cancel_btn.setText(mCancelBtnText);
            }

        }
        return this;
    }

    public YouSeeDialog setYouSeeDialogListener(YouSeeDialogListener youSeeDialogListener) {
        mYouSeeDialogListener = youSeeDialogListener;
        return this;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancel_btn) {
            if (mYouSeeDialogListener != null) {
                mYouSeeDialogListener.onCancelClick(YouSeeDialog.this);
            } else {
                dismiss();
            }


        }

        if (v.getId() == R.id.confirm_btn) {
            if (mYouSeeDialogListener != null) {
                mYouSeeDialogListener.onConfirmClick(YouSeeDialog.this);
            } else {
                dismiss();
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            cancel();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

}
