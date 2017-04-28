package arab_offers.lue.com.Adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;

import arab_offers.lue.com.R;

/**
 * Created by Fujitsu on 07-01-2017.
 */
public class FullScreenView extends Activity {

    WebView webView;
    AppCompatImageView  cancel_toback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_view);
        webView=(WebView) findViewById(R.id.webView);
        webView.setBackground(getResources().getDrawable(R.drawable.drawable_progress));
        cancel_toback=(AppCompatImageView)findViewById(R.id.cancel_to_back);

        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);


        String imgSrcHtml = "<html><img src='" + getIntent().getStringExtra("imageUrl") + "' /></html>";
        webView.loadData(imgSrcHtml, "text/html", "UTF-8");
        cancel_toback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
