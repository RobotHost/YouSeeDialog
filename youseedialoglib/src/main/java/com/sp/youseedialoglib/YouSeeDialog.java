package com.sp.youseedialoglib;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
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

    private boolean isTnterrupterBack = false;


    private AnimationSet mDialogInAnim = null;
    private AnimationSet mDialogOutAnim = null;

    private View dialog_view = null;
    private LinearLayout simple_view_ll = null;
    private TextView title_tv = null;
    private TextView content_tv = null;
    private Button cancel_btn = null;
    private Button confirm_btn = null;

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

    public YouSeeDialog(Context context, int styleType, String colorMatchingType) {
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
                dialog_view.setVisibility(View.GONE);
                dialog_view.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mIsCancel) {
                            YouSeeDialog.super.cancel();
                        } else {
                            YouSeeDialog.super.dismiss();
                        }
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog_view = LayoutInflater.from(getContext()).inflate(R.layout.you_see_dialog, null);
        setContentView(dialog_view);
        simple_view_ll = (LinearLayout) dialog_view.findViewById(R.id.simple_view_ll);
        title_tv = (TextView) dialog_view.findViewById(R.id.title_tv);
        content_tv = (TextView) dialog_view.findViewById(R.id.content_tv);
        cancel_btn = (Button) dialog_view.findViewById(R.id.cancel_btn);
        confirm_btn = (Button) dialog_view.findViewById(R.id.confirm_btn);


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
        dialog_view.startAnimation(mDialogInAnim);

    }

    @Override
    public void cancel() {
        //动画完成再super
        outAnimationStart(true);
    }

    @Override
    public void dismiss() {
        //动画完成再super
        outAnimationStart(false);
    }

    private void outAnimationStart(boolean isCancel) {
        mIsCancel = isCancel;
        dialog_view.startAnimation(mDialogOutAnim);
    }

    public YouSeeDialog setTitleVisibility(boolean visible) {
        mTitleVisible = visible;
        if (title_tv != null) {
            if (visible) {
                title_tv.setVisibility(View.VISIBLE);
            } else {
                title_tv.setVisibility(View.GONE);
            }
        }
        return this;
    }

    public YouSeeDialog setTitleText(String str) {
        mTitleText = str;
        if (title_tv != null && !TextUtils.isEmpty(str)) {
            title_tv.setText(str);
            setTitleVisibility(true);
        }
        return this;
    }

    public YouSeeDialog setContentVisibility(boolean visible) {
        mContentVisible = visible;
        if (content_tv != null) {
            if (visible) {
                content_tv.setVisibility(View.VISIBLE);
            } else {
                content_tv.setVisibility(View.GONE);
            }
        }

        return this;
    }

    public YouSeeDialog setContentText(String str) {
        mContentText = str;
        if (content_tv != null && !TextUtils.isEmpty(str)) {
            content_tv.setText(str);
            setContentVisibility(true);
        }
        return this;
    }


    public YouSeeDialog setConfirmBtnVisibility(boolean visible) {
        mConfirmBtnVisible = visible;
        if (confirm_btn != null) {
            if (visible) {
                confirm_btn.setVisibility(View.VISIBLE);
            } else {
                confirm_btn.setVisibility(View.GONE);
            }
        }
        return this;
    }

    public YouSeeDialog setConfirmBtnText(String str) {
        mConfirmBtnText = str;
        if (confirm_btn != null && !TextUtils.isEmpty(str)) {
            confirm_btn.setText(str);
            setConfirmBtnVisibility(true);
        }
        return this;
    }

    public YouSeeDialog setCancelBtnVisibility(boolean visible) {
        mCancelBtnVisible = visible;
        if (cancel_btn != null) {
            if (visible) {
                cancel_btn.setVisibility(View.VISIBLE);
            } else {
                cancel_btn.setVisibility(View.GONE);
            }
        }
        return this;
    }

    public YouSeeDialog setCancelBtnText(String str) {
        mCancelBtnText = str;
        if (cancel_btn != null && !TextUtils.isEmpty(str)) {
            cancel_btn.setText(str);
            setCancelBtnVisibility(true);
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


    public interface YouSeeDialogListener {
        public void onCancelClick(YouSeeDialog youSeeDialog);

        public void onConfirmClick(YouSeeDialog youSeeDialog);
    }
}
