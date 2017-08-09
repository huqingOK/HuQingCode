package com.hq.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hq.base.demo.BannerActivity;
import com.hq.base.demo.GuideActivity;
import com.hq.base.demo.NetActivity;
import com.hq.base.view.CustomTitlebar;
import com.hq.base.view.loadingtip.OnAdClickListener;
import com.hq.base.view.loadingtip.PromptButton;
import com.hq.base.view.loadingtip.PromptButtonListener;
import com.hq.base.view.loadingtip.PromptDialog;
import com.jkt.tdialog.TDialog;

/**
 * 1、标题栏操作
 */
public class DialogActivity extends BaseActivity implements TDialog.onItemClickListener, TDialog.onDismissListener {

        TDialog mTDialog;

    private CustomTitlebar titleBar;

    private com.hq.base.view.loadingtip.PromptDialog promptDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleBar = (CustomTitlebar) findViewById(R.id.titleBar);
        initTitle();
        initDialog();
        initLoadingTipDialog();
        initJump();
    }

    private void initJump()
    {
        findViewById(R.id.bnNet).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(mContext, NetActivity.class));
            }
        });
        findViewById(R.id.bnGuide).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(mContext, GuideActivity.class));
            }
        });
        findViewById(R.id.bnBanner).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(mContext, BannerActivity.class));
            }
        });
    }


    private void initLoadingTipDialog()
    {
        //创建对象
        promptDialog = new PromptDialog(this);
        //设置自定义属性
        promptDialog.getDefaultBuilder().touchAble(true).round(3).loadingDuration(3000);

        findViewById(R.id.main_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptDialog.showWarn("注意");
            }
        });

        findViewById(R.id.main_loading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                main_failview.setMode(FailView.MODE_REFRESH);
                promptDialog.showLoading("正在登录");
            }
        });
        findViewById(R.id.main_success).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptDialog.showSuccess("登陆成功");
            }
        });
        findViewById(R.id.main_fail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptDialog.showError("登录失败");
            }
        });

        //按钮的定义，创建一个按钮的对象
        final PromptButton confirm = new PromptButton("确定", new PromptButtonListener() {
            @Override
            public void onClick(PromptButton button) {
                Toast.makeText(DialogActivity.this, button.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        confirm.setTextColor(Color.parseColor("#DAA520"));
        confirm.setFocusBacColor(Color.parseColor("#FAFAD2"));
        confirm.setDelyClick(true);//点击后，是否再对话框消失后响应按钮的监听事件
        findViewById(R.id.main_warn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptDialog.showWarnAlert("你确定要退出登录？", new PromptButton("取消", new PromptButtonListener() {
                    @Override
                    public void onClick(PromptButton button) {
                        Toast.makeText(DialogActivity.this, button.getText(), Toast.LENGTH_SHORT).show();
                    }
                }), confirm);
            }
        });

        findViewById(R.id.main_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptDialog.showInfo("成功了");
            }
        });

        findViewById(R.id.main_system).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //可创建android效果的底部Sheet选择，默认IOS效果，sheetCellPad=0为Android效果的Sheet
//                promptDialog.getAlertDefaultBuilder().sheetCellPad(0).round(0);
                //设置按钮的特点，颜色大小什么的，具体看PromptButton的成员变量
                PromptButton cancle = new PromptButton("取消", null);
                cancle.setTextColor(Color.parseColor("#0076ff"));
                //设置显示的文字大小及颜色
//                promptDialog.getAlertDefaultBuilder().textSize(12).textColor(Color.GRAY);
                //默认两个按钮为Alert对话框，大于三个按钮的为底部SHeet形式展现
                promptDialog.showAlertSheet("", true, cancle,
                        new PromptButton("选项1", null), new PromptButton("选项2", null),
                        new PromptButton("选项3", null), new PromptButton("选项4", null));

            }
        });
        findViewById(R.id.main_customer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptDialog.showCustom(R.mipmap.ic_launcher, "自定义图标的");
            }
        });
//        u0_a280   8011  2923  1474704 209280 SyS_epoll_ 0000000000 S me.leefeng.beida

        findViewById(R.id.main_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptDialog.getDefaultBuilder().backAlpha(150);
                Glide.with(DialogActivity.this).load("https://timgsa.baidu.com/timg?image&quality=80&" +
                        "size=b9999_10000&sec=1495518782659&di=25b120262114749ae8543652d2de5715&" +
                        "imgtype=0&src=http%3A%2F%2Fimg.tupianzj.com%2Fuploads%2Fallimg%2F160316%2F9-160316152R5.jpg")
//                        .placeholder(getResources().getDrawable(R.drawable.ic_prompt_holder))
                        .into(promptDialog.showAd(true, new OnAdClickListener() {
                            @Override
                            public void onAdClick() {
                                Toast.makeText(DialogActivity.this,"点击了广告",Toast.LENGTH_SHORT).show();
                            }
                        }));
            }
        });

    }

    /**
     * 设置对话框
     */
    private void initDialog()
    {
    }

    /**
     * 设置标题
     */
    private void initTitle()
    {
        titleBar.setAction(new CustomTitlebar.ILeftClick()
        {
            @Override
            public void doLeft()
            {
                finish();
            }
        });
    }


    public void onClick(View view) {
        //当条目为2时候中间弹出方式改为横向两个条目
        //中间弹出方式默认为点击外部区域可取消,底部弹出方式默认为不可取消
        //各种字体颜色大小和对应的布局margin都可以设置.每一个item也可以分别设置
        //最后动画可以自定义,如果想取消掉自己设置的动画,将setAnim再次调用,参数传空即可.
        String[] contentArray = {"111", "22", "33"};
        switch (view.getId()) {
            case R.id.main_btn:
                String[] array = {"取消", "确认"};
                mTDialog = new TDialog(DialogActivity.this, TDialog.Style.Center, array,
                        "中间对话框", "点击外部区域不可取消", this);
                mTDialog.setCancelable(false);
                mTDialog.show();
                break;
            case R.id.main_btn1:
                mTDialog = new TDialog(DialogActivity.this, TDialog.Style.DownSheet, contentArray,
                        "底部对话框", "点击外部区域不可取消", this);
                mTDialog.show();
                break;
            case R.id.main_btn2:
                mTDialog = new TDialog(DialogActivity.this, TDialog.Style.Center, contentArray,
                        "中间对话框", "点击外部区域可取消", this);
                mTDialog.show();
                break;
            case R.id.main_btn3:
                mTDialog = new TDialog(DialogActivity.this, TDialog.Style.DownSheet, contentArray,
                        "底部对话框", "点击外部区域可取消", this);
                mTDialog.setCancelable(true);
                mTDialog.show();
                break;
            case R.id.main_btn4:
                String[] array4 = {"取消", "确认"};
                mTDialog = new TDialog(DialogActivity.this, TDialog.Style.Center, array4,
                        "自定义样式,中间弹出", "点击外部区域可取消", this);
                mTDialog.setItemTextColor(getResources().getColor(R.color.bgColor_overlay));
                mTDialog.setMsgTextColor(getResources().getColor(R.color.colorAccent));
                mTDialog.setItemTextColorAt(1, getResources().getColor(R.color.colorPrimary));
                mTDialog.setItemTextColorAt(2, getResources().getColor(R.color.colorAccent));
                mTDialog.setItemTextColorAt(10, getResources().getColor(R.color.colorAccent));
                mTDialog.show();
                break;
            case R.id.main_btn5:
                mTDialog = new TDialog(DialogActivity.this, TDialog.Style.DownSheet, contentArray,
                        "自定义样式,底部弹出", "点击外部区域不可取消", this);
                mTDialog.setTitleTextColor(getResources().getColor(R.color.colorAccent));
                mTDialog.setItemTextColor(getResources().getColor(R.color.colorAccent));
                mTDialog.setMsgTextColor(getResources().getColor(R.color.bgColor_overlay));
                mTDialog.show();
                break;
            case R.id.main_btn6:
                mTDialog = new TDialog(DialogActivity.this, TDialog.Style.Center, contentArray,
                        "消失监听", "点击外部区域可取消并监听到消失事件", this);
                mTDialog.setDismissListener(this);
                mTDialog.show();
                break;
            case R.id.main_btn7:
                mTDialog = new TDialog(DialogActivity.this, TDialog.Style.Center, contentArray,
                        "更改边距", "通过外边距更改宽度", this);
                mTDialog.setMargin(
                        0, 0, 0, 50);
                mTDialog.show();
                break;
            case R.id.main_btn8:
                mTDialog = new TDialog(DialogActivity.this, TDialog.Style.DownSheet, contentArray,
                        "更改动画", "自定义动画,进行设置", this);
                mTDialog.setInAnim(AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom1));
                mTDialog.setOutAnim(AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom1));
                mTDialog.show();
                break;
            case R.id.main_btn9:
                String[] array9 = {"取消", "确认"};
                View inflate = LayoutInflater.from(this).inflate(R.layout.alertext_from, null);
                final EditText et = (EditText) inflate.findViewById(R.id.from_et);
                mTDialog = new TDialog(this, TDialog.Style.Center, array9, "添加View", "一行代码搞定"
                        , new TDialog.onItemClickListener() {
                    @Override
                    public void onItemClick(Object object, int position) {
                        closeSoftInput(et);
                    }
                });
                mTDialog.setItemTextColorAt(0, getResources().getColor(R.color.bgColor_overlay));
                mTDialog.addView(inflate);
                mTDialog.show();
                break;

        }
    }

    @Override
    public void onItemClick(Object object, int position) {
        if (object == mTDialog) {
            Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
            if (position == 0) {
                //立即消失对话框（dismiss在动画执行完毕后消失）
                Toast.makeText(DialogActivity.this,"0",Toast.LENGTH_LONG).show();
                mTDialog.dismissImmediately();
            }else if (position==1){
                Toast.makeText(DialogActivity.this,"1",Toast.LENGTH_LONG).show();
            }else if (position==2){
                Toast.makeText(DialogActivity.this,"2",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onDismissClick(Object object) {
        if (object == mTDialog) {
            Toast.makeText(this, "消失", Toast.LENGTH_SHORT).show();
        }
    }

    private void closeSoftInput(EditText editText) {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager.isActive()) {
            manager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }



    /**
     * 根据需要处理返回键，这里主要针对Alert和Sheet的对话框的返回处理
     */
    @Override
    public void onBackPressed() {
        if (promptDialog.onBackPressed()){
            promptDialog.dismiss();
        }else {
            super.onBackPressed();
        }

    }
}
