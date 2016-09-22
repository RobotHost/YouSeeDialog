package com.sp.youseedialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sp.youseedialoglib.YouSeeDialog;
import com.sp.youseedialoglib.YouSeeDialogListener;

public class MainActivity extends AppCompatActivity {


    private Activity activity = null;
    private Button test_1_btn = null;
    private Button test_2_btn = null;
    private Button test_3_btn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        doBusiness();

    }


    private void doBusiness() {
//        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
//
//        int widthPX = displayMetrics.widthPixels;
//        int heightPX = displayMetrics.heightPixels;
//        float densityF = displayMetrics.density;
//        int densityDpi = displayMetrics.densityDpi;
//
//        float widthDpi = displayMetrics.xdpi;
//        float heightDpi = displayMetrics.ydpi;
//
//        Logger.d(new StringBuffer().append("widthPX=").append(widthPX).append(";").append("heightPX=")
//                .append(heightPX).append(";").append("densityF=").append(densityF).append(";").append("densityDpi=")
//                .append(densityDpi).append(";").append("widthDpi=").append(widthDpi).append(";").append("heightDpi=")
//                .append(heightDpi));

        test_1_btn = (Button) this.findViewById(R.id.test_1_btn);
        test_2_btn = (Button) this.findViewById(R.id.test_2_btn);
        test_3_btn = (Button) this.findViewById(R.id.test_3_btn);

        test_1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new YouSeeDialog(activity)
                        .setTitleText("重要提醒")
                        .setContentText("重要提醒111111")
                        .setCancelBtnText("canncel")
                        .setConfirmBtnText("ok!")
                        .setDialogStyle(YouSeeDialog.D_SIMPLE_BUTTON_TYPE)
                        .setBtnDialogColor("#4A4AFF")
                        .setDialogCanceledOnTouchOutside(true)
                        .setYouSeeDialogListener(new YouSeeDialogListener() {
                            @Override
                            public void onCancelClick(YouSeeDialog youSeeDialog) {
                                youSeeDialog.cancel();
                            }

                            @Override
                            public void onConfirmClick(YouSeeDialog youSeeDialog) {
                                youSeeDialog.dismiss();
                            }
                        })
                        .show();

            }
        });

        test_2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new YouSeeDialog(activity)
                        .setTitleText("重要提醒")
                        .setContentText("重要提醒111111")
                        .setCancelBtnText("canncel")
                        .setConfirmBtnText("ok!")
                        .setDialogStyle(YouSeeDialog.D_SIMPLE_BUTTON_TYPE)
                        .setBtnDialogColor(YouSeeDialog.C_MATCHING_WARN_TYPE)
                        .setYouSeeDialogListener(new YouSeeDialogListener() {
                            @Override
                            public void onCancelClick(YouSeeDialog youSeeDialog) {
                                youSeeDialog.cancel();
                            }

                            @Override
                            public void onConfirmClick(YouSeeDialog youSeeDialog) {
                                youSeeDialog.dismiss();
                            }
                        })
                        .show();

            }
        });

        test_3_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new YouSeeDialog(activity)
                        .setTitleText("重要提醒")
                        .setContentText("重要提醒111111")
                        .setCancelBtnText("canncel")
                        .setConfirmBtnText("ok!")
                        .setYouSeeDialogListener(new YouSeeDialogListener() {
                            @Override
                            public void onCancelClick(YouSeeDialog youSeeDialog) {
                                youSeeDialog.cancel();
                            }

                            @Override
                            public void onConfirmClick(YouSeeDialog youSeeDialog) {
                                youSeeDialog.dismiss();
                            }
                        })
                        .show();

            }
        });
    }
}

