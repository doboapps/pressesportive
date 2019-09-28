package doboapps.com.pressesportive;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NewExternalTab extends AppCompatActivity implements View.OnClickListener {

    WebView webView;
    String urlNewTab = "http://doboapps.com";
    String currentUrl = "";
    LinearLayout layoutLoading, layoutBar, layoutBigSpinner;
    TextView percentage, titleBar;
    ImageView returnArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_external_tab);

        webView = findViewById(R.id.WebViewLayout);
        percentage = findViewById(R.id.percentage);
        titleBar = findViewById(R.id.title_bar);
        layoutBigSpinner = findViewById(R.id.loading_spinner_layout);
        layoutLoading = findViewById(R.id.loading_layout);
        layoutBar = findViewById(R.id.bar_layout);
        returnArrow = findViewById(R.id.return_arrow);
        returnArrow.setOnClickListener(this);

        WebSettings settings = webView.getSettings();

        webView.setWebViewClient(new WebViewClient());

        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        // Improve acceleration in old versions.
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration.
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration.
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        } else {
            CookieManager.getInstance().setAcceptCookie(true);
        }

        layoutBar.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            urlNewTab = extras.getString("urlNewTab");
        }

        webView.loadUrl(urlNewTab);

        // Detecting new tab.
        webView.setWebChromeClient(new WebChromeClient() {

            // Avoid video error in old versions.
            @Override
            public Bitmap getDefaultVideoPoster() {
                return Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
            }

            public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture, android.os.Message resultMsg) {
                WebView.HitTestResult result = view.getHitTestResult();
                String url = result.getExtra();
                Boolean isGooglePlayLink = false;

                try {
                    isGooglePlayLink = url.indexOf("play.google") != -1;
                }catch (Exception e) {
                    Log.e("Error checking link: ", e.getMessage());
                }

                // Open browser.
                if (isGooglePlayLink) {
                    Context context = view.getContext();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(intent);
                    startActivity(intent);
                }
                return false;
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

                if (title.length() > 25) {
                    title = title.substring(0, 25) + "...";
                }
                titleBar.setText(title);
            }

            @Override
            public void onProgressChanged(WebView view, int progress) {
                NewExternalTab.this.setProgress(progress * 1000);
                String percentage_txt = Integer.toString(progress);
                percentage.setText(' ' + percentage_txt + '%');

                if (progress <= 40) {
                    layoutBigSpinner.setVisibility(View.VISIBLE);
                }
                if (progress > 40) {
                    layoutBigSpinner.setVisibility(View.GONE);
                    layoutBar.setVisibility(View.VISIBLE);
                }
                if (progress > 90){
                    layoutLoading.setVisibility(View.GONE);
                }
            }
        });

        // Show message if internet connection failed.
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewExternalTab.this);
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
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                currentUrl = url;
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.return_arrow) {
            finish();
        }
    }

    // Back button.
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        webView = (WebView) findViewById(R.id.WebViewLayout);
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    finish();
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}