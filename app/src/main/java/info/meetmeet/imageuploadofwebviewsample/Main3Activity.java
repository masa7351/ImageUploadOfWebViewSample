package info.meetmeet.imageuploadofwebviewsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.xwalk.core.XWalkView;

public class Main3Activity extends AppCompatActivity {

    private static final String TAG = Main3Activity.class.getSimpleName();

    private XWalkView mXWalkWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

//        XWalkView view = new XWalkView(this, this);
        mXWalkWebView = (XWalkView)findViewById(R.id.webView);
        mXWalkWebView.load("http://49.212.135.31/image_upload/edit_profile.html", null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mXWalkWebView != null) {
            mXWalkWebView.pauseTimers();
            mXWalkWebView.onHide();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mXWalkWebView != null) {
            mXWalkWebView.resumeTimers();
            mXWalkWebView.onShow();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mXWalkWebView != null) {
            mXWalkWebView.onDestroy();
        }
    }

}
