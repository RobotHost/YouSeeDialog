package com.sp.youseedialoglib;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;

/**
 * Created by songyuan on 2016/9/9.
 */
public class YouSeeDialog extends Dialog implements View.OnClickListener {
    //样式缺省
    public static final int D_PROGRESS_WHEEL_TYPE = 0;
    public static final int D_SIMPLE_BUTTON_TYPE = 1;
    private int mDialogStyle = D_SIMPLE_BUTTON_TYPE;
    //配色缺省
    public static final String C_MATCHING_SIMPLE_TYPE = "0";
    public static final String C_MATCHING_WARN_TYPE = "1";
    public static final String C_MATCHING_ERROR_TYPE = "2";
    private String mColorMatchType = C_MATCHING_SIMPLE_TYPE;
    private String mProgressCircleColor = null;
    private AnimationSet mDialogInAnim = null;
    private AnimationSet mDialogOutAnim = null;
    private View dialog_view = null;
    private LinearLayout dialog_content_ll = null;
    private LinearLayout simple_view_ll = null;
    private LinearLayout progress_view_ll = null;
    private ProgressBarCircularIndeterminate circular_progress = null;
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
    //    private Timer timer = null;
//    private TimerTask timerTask = null;
//    private static final int CHANGE_COUNT_MIN = 1;
//    private static final int CHANGE_COUNT_MAX = 5;
//    private int changeCount = CHANGE_COUNT_MIN;
    private static int DIALOG_PADDING;//px
    private static int DIALOG_LINE;//px

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

        DIALOG_PADDING = dip2Px(context, 12);
        DIALOG_LINE = dip2Px(context, 1);

    }

    public YouSeeDialog setDialogCancelable(boolean flag) {
        YouSeeDialog.super.setCancelable(flag);
        return this;
    }

    public YouSeeDialog setDialogCanceledOnTouchOutside(boolean flag) {
        YouSeeDialog.super.setCanceledOnTouchOutside(flag);
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        circular_progress = (ProgressBarCircularIndeterminate) findViewById(R.id.circular_progress);
        cancel_btn.setOnClickListener(this);
        confirm_btn.setOnClickListener(this);
        setDialogStyle(mDialogStyle);
        setBtnDialogColor(mColorMatchType);
        setProgressDialogColor(mProgressCircleColor);
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
//        if (mDialogStyle == D_PROGRESS_WHEEL_TYPE) {
//            startProgressColorTimer();
//        }
    }


    @Override
    public void cancel() {
        if (mYouSeeDialogListener != null) {
            mYouSeeDialogListener.onCancelClick(YouSeeDialog.this);
        }
        //动画完成的回调中super
        outAnimationStart(true);
    }

    @Override
    public void dismiss() {
        //动画完成的回调中super
        outAnimationStart(false);
    }

    /**
     * ProgressBar型dialog时，效果色值设置
     *
     * @param colourValue 如#F79347
     */
    public YouSeeDialog setProgressDialogColor(String colourValue) {
        mProgressCircleColor = colourValue;
        if (circular_progress != null) {
            if (!TextUtils.isEmpty(mProgressCircleColor) && colourValue.startsWith("#")) {
                int color = Color.parseColor(mProgressCircleColor);
                circular_progress.setBackgroundColor(color);
            }
        }
        return this;
    }

    /**
     * 按钮型dialog时，效果颜色设置
     *
     * @param colorMatchType C_MATCHING_WARN_TYPE/C_MATCHING_WARN_TYPE/C_MATCHING_SIMPLE_TYPE 或色值如#F79347
     * @return
     */
    public YouSeeDialog setBtnDialogColor(String colorMatchType) {
        if (!TextUtils.isEmpty(colorMatchType)) {
            mColorMatchType = colorMatchType;
            if (dialog_content_ll != null && confirm_btn != null) {
                if (mColorMatchType.equals(C_MATCHING_WARN_TYPE)) {
                    dialog_content_ll.setBackgroundResource(R.drawable.dialog_bg_warn);
                    confirm_btn.setBackgroundResource(R.drawable.btn_bg_warn_ys_d);
                } else if (mColorMatchType.equals(C_MATCHING_ERROR_TYPE)) {
                    dialog_content_ll.setBackgroundResource(R.drawable.dialog_bg_error);
                    confirm_btn.setBackgroundResource(R.drawable.btn_bg_error_ys_d);
                } else if (mColorMatchType.equals(C_MATCHING_SIMPLE_TYPE)) {
                    dialog_content_ll.setBackgroundResource(R.drawable.dialog_bg_simple);
                    confirm_btn.setBackgroundResource(R.drawable.btn_bg_simple_ys_d);
                } else {
                    LayerDrawable layerDrawableDialog = diyColorMatchDialog(mColorMatchType);
                    StateListDrawable stateListDrawable = diyColorMatchBtn(mColorMatchType);
                    if (layerDrawableDialog != null) {
                        dialog_content_ll.setBackgroundDrawable(layerDrawableDialog);
                    }
                    if (stateListDrawable != null) {
                        confirm_btn.setBackgroundDrawable(stateListDrawable);
                    }
                }
            }
        }
        return this;
    }

//    private void startProgressColorTimer() {
//        if (timer != null) {
//            timer.cancel();
//            timer = null;
//        }
//
//        if (timerTask != null) {
//            timerTask.cancel();
//            timerTask = null;
//        }
//
//        timer = new Timer();
//        timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                if (changeCount >= CHANGE_COUNT_MAX) {
//                    changeCount = CHANGE_COUNT_MIN;
//                }
//                Message msg = new Message();
//                msg.arg1 = changeCount;
//                timerHandler.sendMessage(msg);
//                changeCount++;
//            }
//        };
//
//        timer.schedule(timerTask, 0, 1000);
//    }


//    private final Handler timerHandler = new Handler() {
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.arg1) {
//                case 1:
//                    circular_progress.setBackgroundResource(R.color.progress_01_ys_d);
//                    break;
//                case 2:
//                    circular_progress.setBackgroundResource(R.color.progress_02_ys_d);
//                    break;
//                case 3:
//                    circular_progress.setBackgroundResource(R.color.progress_03_ys_d);
//                    break;
//                case 4:
//                    circular_progress.setBackgroundResource(R.color.progress_04_ys_d);
//                    break;
//                case 5:
//                    circular_progress.setBackgroundResource(R.color.progress_05_ys_d);
//                    break;
//            }
//        }
//    };


    /**
     * @param colourValue 色值，如#F79347
     */
    private LayerDrawable diyColorMatchDialog(String colourValue) {
        if (!TextUtils.isEmpty(colourValue) && colourValue.startsWith("#")) {
            int contentColor = Color.parseColor("#FFFFFF");
            int baseColor = Color.parseColor(colourValue);

            float radius0 = 15f;
            float[] outerR = new float[]{radius0, radius0, radius0, radius0, radius0, radius0, radius0, radius0};
            RoundRectShape roundRectShape0 = new RoundRectShape(outerR, null, null);
            ShapeDrawable shapeDrawableBg = new ShapeDrawable();
            shapeDrawableBg.setPadding(0, 0, 0, 0);
            shapeDrawableBg.setShape(roundRectShape0);
            shapeDrawableBg.getPaint().setStyle(Paint.Style.FILL);
            shapeDrawableBg.getPaint().setColor(baseColor);

            float radius1 = 15f;
            float[] outerR1 = new float[]{radius1, radius1, radius1, radius1, radius1, radius1, radius1, radius1};
            RoundRectShape roundRectShape1 = new RoundRectShape(outerR1, null, null);
            ShapeDrawable shapeDrawableFg = new ShapeDrawable();
            shapeDrawableFg.setPadding(DIALOG_PADDING, DIALOG_PADDING, DIALOG_PADDING, DIALOG_PADDING);
            shapeDrawableFg.setShape(roundRectShape1);
            shapeDrawableFg.getPaint().setStyle(Paint.Style.FILL);
            shapeDrawableFg.getPaint().setColor(contentColor);

            Drawable[] layers = {shapeDrawableBg, shapeDrawableFg};
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            layerDrawable.setLayerInset(1, DIALOG_LINE, DIALOG_LINE, DIALOG_LINE, DIALOG_LINE);

            return layerDrawable;
        }
        return null;
    }

    /**
     * @param colourValue 按钮型dialog时，效果色值设置，如#F79347
     */
    private StateListDrawable diyColorMatchBtn(String colourValue) {
        if (!TextUtils.isEmpty(colourValue) && colourValue.startsWith("#")) {
            int baseColor = Color.parseColor(colourValue);
            int redBase = Color.red(baseColor);
            int greenBase = Color.green(baseColor);
            int blueBase = Color.blue(baseColor);
            int redDeep = redBase;
            int greenDeep = greenBase;
            int blueDeep = blueBase;
            if (redDeep >= 35) {
                redDeep = redDeep - 35;
            }
            if (greenDeep >= 35) {
                greenDeep = greenDeep - 35;
            }
            if (blueDeep >= 35) {
                blueDeep = blueDeep - 35;
            }
            int deepColor = Color.rgb(redDeep, greenDeep, blueDeep);

            float radius0 = 10f;
            float[] outerR = new float[]{radius0, radius0, radius0, radius0, radius0, radius0, radius0, radius0};
            RoundRectShape roundRectShape0 = new RoundRectShape(outerR, null, null);
            ShapeDrawable shapeDrawableNormal = new ShapeDrawable();
            shapeDrawableNormal.setPadding(0, 0, 0, 0);
            shapeDrawableNormal.setShape(roundRectShape0);
            shapeDrawableNormal.getPaint().setStyle(Paint.Style.FILL);
            shapeDrawableNormal.getPaint().setColor(baseColor);

            float radius1 = 10f;
            float[] outerR1 = new float[]{radius1, radius1, radius1, radius1, radius1, radius1, radius1, radius1};
            RoundRectShape roundRectShape1 = new RoundRectShape(outerR1, null, null);
            ShapeDrawable shapeDrawablePressed = new ShapeDrawable();
            shapeDrawablePressed.setPadding(0, 0, 0, 0);
            shapeDrawablePressed.setShape(roundRectShape1);
            shapeDrawablePressed.getPaint().setStyle(Paint.Style.FILL);
            shapeDrawablePressed.getPaint().setColor(deepColor);


            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, shapeDrawablePressed);
            stateListDrawable.addState(new int[]{}, shapeDrawableNormal);

            return stateListDrawable;
        }
        return null;
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
            cancel();
        }

        if (v.getId() == R.id.confirm_btn) {
            if (mYouSeeDialogListener != null) {
                mYouSeeDialogListener.onConfirmClick(YouSeeDialog.this);
            }
            dismiss();
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

//    @Override
//    public void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//        if (timerTask != null) {
//            timerTask.cancel();
//            timerTask = null;
//        }
//
//        if (timer != null) {
//            timer.cancel();
//            timer = null;
//        }
//    }


    private int dip2Px(Context context, float dip) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f);
    }
}
