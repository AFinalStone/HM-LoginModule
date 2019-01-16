package com.hm.iou.loginmodule.business.register.view;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Outline;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hm.iou.loginmodule.LoginModuleConstants;
import com.hm.iou.loginmodule.R;
import com.hm.iou.router.Router;


/**
 * Created by hjy on 2018/6/5.
 */
public class UserAgreementDialog extends Dialog implements View.OnClickListener {

    public interface OnUserAgreementListener {
        void onAgree();
        void onDisagree();
    }

    private OnUserAgreementListener mListener;
    private Context mContext;

    public UserAgreementDialog(@NonNull final Context context) {
        super(context, R.style.UikitAlertDialogStyle);
        setContentView(R.layout.loginmodule_dialog_user_agreement);
        mContext = context;
        setBgRadius();

        final Window dialogWindow = getWindow();
        if (dialogWindow != null) {
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.8);
            dialogWindow.setAttributes(lp);
        }

        final TextView tvMsg = findViewById(R.id.txt_msg);
        tvMsg.setMovementMethod(ScrollingMovementMethod.getInstance());

        String str = tvMsg.getText().toString();
        SpannableString spanStr = new SpannableString(str);
        String a = "《条管家用户注册协议》";
        String b = "《条管家用户隐私协议》";
        String c = "《条管家用户支付协议》";
        String d = "《CFCA数字证书服务协议》";

        int start = str.indexOf(a);
        int highlightColor = mContext.getResources().getColor(R.color.uikit_function_remind);
        spanStr.setSpan(new ForegroundColorSpan(highlightColor), start, start + a.length(), 0);
        spanStr.setSpan(new UserAgreementClickableSpan(0), start, start + a.length(), 0);
        spanStr.setSpan(new UnderlineSpan(), start, start + a.length(), 0);
        start = str.indexOf(b);
        spanStr.setSpan(new ForegroundColorSpan(highlightColor), start, start + b.length(), 0);
        spanStr.setSpan(new UserAgreementClickableSpan(1), start, start + b.length(), 0);
        spanStr.setSpan(new UnderlineSpan(), start, start + b.length(), 0);
        start = str.indexOf(c);
        spanStr.setSpan(new ForegroundColorSpan(highlightColor), start, start + c.length(), 0);
        spanStr.setSpan(new UserAgreementClickableSpan(2), start, start + c.length(), 0);
        spanStr.setSpan(new UnderlineSpan(), start, start + c.length(), 0);
        start = str.indexOf(d);
        spanStr.setSpan(new ForegroundColorSpan(highlightColor), start, start + d.length(), 0);
        spanStr.setSpan(new UserAgreementClickableSpan(3), start, start + d.length(), 0);
        spanStr.setSpan(new UnderlineSpan(), start, start + d.length(), 0);

        tvMsg.setText(spanStr);
        tvMsg.setHighlightColor(context.getResources().getColor(android.R.color.transparent));

        findViewById(R.id.btn_pos).setOnClickListener(this);
        findViewById(R.id.btn_neg).setOnClickListener(this);

        tvMsg.setOnTouchListener(new View.OnTouchListener() {

            float downX;
            float downY;
            float moveX;
            float moveY;
            long t;
            ClickableSpan tmpSpan;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    x -= tvMsg.getTotalPaddingLeft();
                    y -= tvMsg.getTotalPaddingTop();

                    x += tvMsg.getScrollX();
                    y += tvMsg.getScrollY();

                    Layout layout = tvMsg.getLayout();
                    int line = layout.getLineForVertical(y);
                    int off = layout.getOffsetForHorizontal(line, x);

                    Spannable buffer = (Spannable) tvMsg.getText();
                    ClickableSpan[] links = buffer.getSpans(off, off, ClickableSpan.class);
                    downX = event.getX();
                    downY = event.getY();
                    moveX = 0;
                    moveY = 0;
                    if (links != null && links.length != 0) {
                        t = System.currentTimeMillis();
                        tmpSpan = links[0];
                        return false;
                    }
                } else if (action == MotionEvent.ACTION_UP) {
                    moveX += Math.abs(event.getX() - downX);
                    moveY += Math.abs(event.getY() - downY);
                    long moveTime = System.currentTimeMillis() - t;
                    int touchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
                    if (tmpSpan != null && moveTime < 500 && moveX < touchSlop && moveY < touchSlop) {
                        tmpSpan.onClick(tvMsg);
                        return true;
                    }
                } else if (action == MotionEvent.ACTION_MOVE) {
                    moveX += Math.abs(event.getX() - downX);
                    moveY += Math.abs(event.getY() - downY);
                    downX = event.getX();
                    downY = event.getY();
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_pos) {
            dismiss();
            if (mListener != null) {
                mListener.onAgree();
            }
        } else if (v.getId() == R.id.btn_neg) {
            dismiss();
            if (mListener != null) {
                mListener.onDisagree();
            }
        }
    }

    /**
     * 设置dialog圆角
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setBgRadius() {
        LinearLayout layoutContent = findViewById(com.hm.iou.uikit.R.id.ll_dialog_content);
        final int bgRadius = (int) (getContext().getResources().getDisplayMetrics().density * 4);
        layoutContent.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), bgRadius);
            }
        });
        layoutContent.setClipToOutline(true);
    }

    public void setOnUserAgreementListener(OnUserAgreementListener listener) {
        mListener = listener;
    }

    class UserAgreementClickableSpan extends ClickableSpan {

        int type;

        public UserAgreementClickableSpan(int type) {
            this.type = type;
        }

        @Override
        public void onClick(View widget) {
            String url = "";
            if (type == 0) {
                url = LoginModuleConstants.H5_URL_1;
            } else if (type == 1) {
                url = LoginModuleConstants.H5_URL_2;
            } else if (type == 2) {
                url = LoginModuleConstants.H5_URL_3;
            } else if (type == 3) {
                url = LoginModuleConstants.H5_URL_4;
            }

            Router.getInstance()
                    .buildWithUrl("hmiou://m.54jietiao.com/webview/index")
                    .withString("url", url)
                    .navigation(getContext());
        }

        @Override
        public void updateDrawState(TextPaint ds) {
        }
    }

}
