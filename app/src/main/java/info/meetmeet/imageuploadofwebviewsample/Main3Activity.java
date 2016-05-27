package info.meetmeet.imageuploadofwebviewsample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.ValueCallback;

import org.xwalk.core.XWalkUIClient;
import org.xwalk.core.XWalkView;

public class Main3Activity extends AppCompatActivity {

    private static final String TAG = Main3Activity.class.getSimpleName();

    private XWalkView mXWalkWebView;
    private static final String TYPE_IMAGE = "image/*";
    private static final int INPUT_FILE_REQUEST_CODE = 1;
    private ValueCallback<Uri> mUploadMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

//        XWalkView view = new XWalkView(this, this);
        mXWalkWebView = (XWalkView)findViewById(R.id.webView);
        mXWalkWebView.setUIClient(MyWebChromeClient(mXWalkWebView));

        mXWalkWebView.load("http://49.212.135.31/image_upload/edit_profile.html", null);
//        mXWalkWebView.load("http://49.212.135.31/image_upload/sample.html", null);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (mUploadMessage == null) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }

        Uri result = null;

        if (resultCode == RESULT_OK) {
            if (data != null) {
                result = data.getData();
            }
        }

        mUploadMessage.onReceiveValue(result);
        mUploadMessage = null;
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

    // https://crosswalk-project.org/apis/embeddingapidocs_v2/org/xwalk/core/XWalkUIClient.html
    private XWalkUIClient MyWebChromeClient(XWalkView view) {
        return new XWalkUIClient(view) {
            @Override
            public void openFileChooser(XWalkView view, ValueCallback<Uri> uploadFile, String acceptType, String capture) {
                Log.d(TAG, "XWalkUIClient.openFileChooser");

                if(mUploadMessage != null){
                    mUploadMessage.onReceiveValue(null);
                }
                mUploadMessage = uploadFile;

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType(TYPE_IMAGE);

                startActivityForResult(intent, INPUT_FILE_REQUEST_CODE);
            }
        };
    }
}
