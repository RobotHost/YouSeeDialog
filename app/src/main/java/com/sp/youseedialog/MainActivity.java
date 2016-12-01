package com.sp.youseedialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sp.youseedialoglib.YouSeeDialog;
import com.sp.youseedialoglib.YouSeeDialogListener;

public class MainActivity extends AppCompatActivity {
    private Activity activity = null;
    private Button test_1_btn = null;
    private Button test_2_btn = null;
    private Button test_3_btn = null;
    private Button test_progress_bar_btn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        doBusiness();

    }


    private void doBusiness() {
        test_1_btn = (Button) this.findViewById(R.id.test_1_btn);
        test_2_btn = (Button) this.findViewById(R.id.test_2_btn);
        test_3_btn = (Button) this.findViewById(R.id.test_3_btn);
        test_progress_bar_btn = (Button) this.findViewById(R.id.test_progress_bar_btn);

        test_1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new YouSeeDialog(activity)
                        .setTitleText("重要提醒")
                        .setContentText("重要提醒111111")
                        .setCancelBtnText("cancel")
                        .setConfirmBtnText("ok!")
                        .setDialogStyle(YouSeeDialog.D_SIMPLE_BUTTON_TYPE)
                        .setBtnDialogColor("#4A4AFF")
                        .setDialogCanceledOnTouchOutside(true)
                        .setYouSeeDialogListener(new YouSeeDialogListener() {
                            @Override
                            public void onCancelClick(YouSeeDialog youSeeDialog) {
                                Log.i("YouSeeDialog", "onCancelClick");
                            }

                            @Override
                            public void onConfirmClick(YouSeeDialog youSeeDialog) {
                                Log.i("YouSeeDialog", "onConfirmClick");
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
                        .setCancelBtnText("cancel")
                        .setConfirmBtnText("ok!")
                        .setDialogStyle(YouSeeDialog.D_SIMPLE_BUTTON_TYPE)
                        .setBtnDialogColor(YouSeeDialog.C_MATCHING_WARN_TYPE)
                        .setYouSeeDialogListener(new YouSeeDialogListener() {
                            @Override
                            public void onCancelClick(YouSeeDialog youSeeDialog) {
                                Log.i("YouSeeDialog", "onCancelClick");
                            }

                            @Override
                            public void onConfirmClick(YouSeeDialog youSeeDialog) {
                                Log.i("YouSeeDialog", "onConfirmClick");
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
                        .setCancelBtnText("cancel")
                        .setConfirmBtnText("ok!")
                        .setYouSeeDialogListener(new YouSeeDialogListener() {
                            @Override
                            public void onCancelClick(YouSeeDialog youSeeDialog) {
                                Log.i("YouSeeDialog", "onCancelClick");
                            }

                            @Override
                            public void onConfirmClick(YouSeeDialog youSeeDialog) {
                                Log.i("YouSeeDialog", "onConfirmClick");
                            }
                        })
                        .show();
            }
        });

        test_progress_bar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ProgressBar类型，只有一下这些方法可以调用并生效，其它方法不可用。
                new YouSeeDialog(activity)
                        .setDialogStyle(YouSeeDialog.D_PROGRESS_WHEEL_TYPE)
                        .setDialogCancelable(true)
                        .setDialogCanceledOnTouchOutside(true)
                        .setProgressDialogColor("#FAB321")
                        .setYouSeeDialogListener(new YouSeeDialogListener() {
                            @Override
                            public void onCancelClick(YouSeeDialog youSeeDialog) {
                                //progress已取消（任务已完成已代码调用取消 或 用户手动已取消）
                                Log.i("YouSeeDialog", "onCancelClick");
                            }

                            @Override
                            public void onConfirmClick(YouSeeDialog youSeeDialog) {
                                //progress不适用该回调
                            }

                        })
                        .show();
            }
        });
    }
}

