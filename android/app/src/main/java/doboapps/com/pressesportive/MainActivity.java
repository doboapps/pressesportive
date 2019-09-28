package doboapps.com.pressesportive;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    WebView webView;
    String mainURL = "http://85.208.22.22/main/france-sports";
    String currentURL = "";
    TextView percentage;
    RelativeLayout layoutBigSpinner;
    boolean WebViewReload = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        webView = findViewById(R.id.WebViewLayout);
        layoutBigSpinner = findViewById(R.id.loading_spinner_layout);
        percentage = findViewById(R.id.percentage);

        WebSettings settings = webView.getSettings();

        webView.setWebViewClient(new WebViewClient());

        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportMultipleWindows(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        settings.setDomStorageEnabled(true);

        getWindow().setFlags( WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                              WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // Chromium, enable hardware acceleration.
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // Older android version, disable hardware acceleration.
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        // Cookies.
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        } else {
            CookieManager.getInstance().setAcceptCookie(true);
        }

        // Loading bar percentage.
        webView.setWebChromeClient(new WebChromeClient() {
            public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture, android.os.Message resultMsg) {
                WebView.HitTestResult result = view.getHitTestResult();
                String data = result.getExtra();
                Intent intent;
                Boolean isDoboAppLink = false;

                try {
                    isDoboAppLink = data.indexOf("dobotab=new") != -1;
                }catch (Exception e) {
                    Log.e("Error checking link: ", e.getMessage());
                }

                // New tab.
                if (isDoboAppLink) {
                    intent = new Intent(MainActivity.this, NewExternalTab.class);
                    intent.putExtra("urlNewTab", data);

                 // Continue.
                }else {
                    Context context = view.getContext();
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(data));
                    context.startActivity(intent);
                }

                startActivity(intent);
                return false;
            }

            @Override
            public void onProgressChanged(WebView view, int progress) {
                // Possible use of cookies.
                //try {
                //cookies = CookieManager.getInstance().getCookie(webView.getUrl()).toLowerCase();
                //}catch (Exception e){
                //cookies = "";
                //}

                MainActivity.this.setProgress(progress * 1000);
                String percentage_txt = Integer.toString(progress);
                percentage.setText(' ' + percentage_txt + '%');

                if (progress > 90) {
                    layoutBigSpinner.setVisibility(View.GONE);
                }

                // Avoid slow loading on old phones.
                if (WebViewReload && progress > 70) {
                    WebViewReload = false;
                    webView.reload();
                }
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            // Show message if internet connection failed.
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Oops! ")
                        .setMessage("Internet connection failed.")
                        .setNegativeButton("Reload", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                webView.reload();
                            }
                        })
                        .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                builder.show();
            }

            @Override
            public void doUpdateVisitedHistory (WebView view, String url, boolean isReload) {
                // Get current URL when URL changes.
                currentURL = url;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        webView.loadUrl(mainURL);
    }

    // Back button.
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        webView = findViewById(R.id.WebViewLayout);
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:

                    // If can go back is possible and current URL is different to main URL.
                    if (webView.canGoBack() && !currentURL.equals(mainURL) ) {
                        webView.goBack();
                        return true;
                    }

                    // Clear cache when App is closed.
                    webView.getSettings().setAppCacheEnabled(false);
                    webView.clearCache(true);

                    finish();
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}