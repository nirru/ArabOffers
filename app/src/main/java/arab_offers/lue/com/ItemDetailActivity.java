package arab_offers.lue.com;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderAdapter;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Transformers.DefaultTransformer;
import com.daimajia.slider.library.Tricks.InfinitePagerAdapter;
import com.daimajia.slider.library.Tricks.InfiniteViewPager;


import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.facebook.ads.AbstractAdListener;
import com.facebook.ads.Ad;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;
import com.matthewtamlin.sliding_intro_screen_library.indicators.DotIndicator;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import arab_offers.lue.com.Adapters.CustomPagerAdapter;
import arab_offers.lue.com.Adapters.FilterListAdapter;
import arab_offers.lue.com.Adapters.FullScreenView;
import arab_offers.lue.com.Adapters.OfferAdapter;
import arab_offers.lue.com.Asyncs.LongOperationE;
import arab_offers.lue.com.Models.Comment_Model;
import arab_offers.lue.com.Models.ImageModal;
import arab_offers.lue.com.Models.ModelObj;
import arab_offers.lue.com.Models.ObjectModel;
import arab_offers.lue.com.Models.OfferModel;
import arab_offers.lue.com.Utils.Constants;
import arab_offers.lue.com.Utils.DeviceUtils;
import arab_offers.lue.com.Utils.Urls;
import arab_offers.lue.com.Utils.YourPreference;

/**
 * Created by Fujitsu on 03-01-2017.
 */
public class ItemDetailActivity extends AppCompatActivity  {
    YourPreference yourPreference;
    Toolbar toolbar;
    ObjectModel modelObj;
    LinearLayout linearimage;
    AppCompatTextView  companyName, offertext, dateduntil, datedFrom, likes, toolbar_title;
//    SliderLayout viewpager;
    PagerIndicator pagerIndicator;
    CustomPagerAdapter mCustomPagerAdapter;

    AppCompatEditText addcomment;
    AppCompatImageView likenow, shareNow;
    static  int counts=0;
  LinearLayout comments;
   AppCompatButton buttonSend;
    CoordinatorLayout coordinatorLayout;
    ArrayList<String> likes_happen= new ArrayList<String>();
    ArrayList<ImageModal> imageModalArrayList = new ArrayList<>();
    FilterListAdapter filterListAdapter ;
    RecyclerView recyclerView;

    LinearLayout  nativeAdContainer;
    LinearLayout  adView1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail_main);
        toolbar = (Toolbar) findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final ScrollView scrollView = (ScrollView)findViewById(R.id.scroll) ;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        }, 500);


        recyclerView = (RecyclerView)findViewById(R.id.recyle_view);
       LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ItemDetailActivity.this,LinearLayoutManager.VERTICAL,false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        recyclerView.setLayoutManager(linearLayoutManager);
        filterListAdapter = new FilterListAdapter(imageModalArrayList,ItemDetailActivity.this);
        recyclerView.setAdapter(filterListAdapter);
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                calculateSwipeRefreshFullHeight(recyclerView);
            }
        });
        initUI();
        yourPreference=YourPreference.getInstance(ItemDetailActivity.this);
        executeUI();
        loadAds();
//        showNativeAd();
//        likes_happen= new ArrayList<String>();
        try{
            likes_happen= yourPreference.getDataArrayListString("likes_happen");
            if(likes_happen.contains(modelObj.getId())){
                likenow.setBackgroundDrawable(getResources().getDrawable(R.drawable.thumbs_new));
                Log.i("result", likes_happen.toString()+ "==="+modelObj.getId());
            }
        }catch (Exception e){
            e.printStackTrace();
        likes_happen= new ArrayList<String>();
        }



    }

    @SuppressLint("NewApi")
    private void executeUI() {
        try {

            modelObj = yourPreference.getModelKey("KeyModel");
             if(!modelObj.is_comment_enabled()){
                 addcomment.setHint(getResources().getString(R.string.commentsdisabled));
                 addcomment.setFocusable(false);
                 addcomment.setClickable(false);
             }
            toolbar_title.setText(modelObj.getName());
            mCustomPagerAdapter = new CustomPagerAdapter(this, modelObj.getAll_images());
            SliderAdapter sliderAdapter= new SliderAdapter(ItemDetailActivity.this);


            InfinitePagerAdapter infinitePagerAdapter= new InfinitePagerAdapter(sliderAdapter);
            SliderAdapter mSliderAdapter = new SliderAdapter(ItemDetailActivity.this);
            final PagerAdapter wrappedAdapter = new InfinitePagerAdapter(mSliderAdapter);

            int size = modelObj.getAll_images().length;
            for( int i=0; i<modelObj.getAll_images().length;i++){
                final int z=i;
//                TextSliderView textSliderView = new TextSliderView(ItemDetailActivity.this);
//                // initialize a SliderLayout
//                textSliderView
//                        .description("")
//                        .image(modelObj.getAll_images()[i])
//                        .setScaleType(BaseSliderView.ScaleType.Fit);
//                textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
//                    @Override
//                    public void onSliderClick(BaseSliderView slider) {
//                        Log.v("clicked", "clicked");
//                        Intent intent = new Intent(ItemDetailActivity.this, FullScreenViewActivity.class);
//                        intent.putExtra("imageUrl", modelObj.getAll_images()[z]);
//                        intent.putExtra("position",z);
//                        startActivity(intent);
//                    }
//                });
//                mSliderAdapter.addSlider(textSliderView);

                ImageModal imageModal = new ImageModal();
                imageModal.setImage_url(modelObj.getAll_images()[i]);
                filterListAdapter.addItem(imageModal);
            }
//            final InfiniteViewPager mViewPager = (InfiniteViewPager)findViewById(R.id.daimajia_slider_viewpager);
//            mViewPager.setAdapter(wrappedAdapter);

            companyName.setText(modelObj.getName());
            offertext.setText(modelObj.getDescription());
            String end_date= modelObj.getEnd_date().substring(0,
                    modelObj.getEnd_date().indexOf("T")==-1?modelObj.getEnd_date().length():modelObj.getEnd_date().indexOf("T"));
            String start_date= modelObj.getStart_date().substring(0,
                    modelObj.getStart_date().indexOf("T")==-1?modelObj.getStart_date().length():modelObj.getStart_date().indexOf("T"));
            dateduntil.setText(end_date);
            datedFrom.setText(start_date);
            likes.setText(modelObj.getLikes());


            final Handler handler= new Handler();
            new Thread(new Runnable() {
                @Override
                public void run() {

                    if(addcomment.getText().toString().length()>1){
                        buttonSend.setVisibility(View.VISIBLE);
                    }else{
                        buttonSend.setVisibility(View.GONE);
                    }
                    handler.postDelayed(this, 1000);
                }
            }).start();

//            TextSliderView textSliderView = new TextSliderView(this);
//            for(int i=0; i<modelObj.getAll_images().length;i++) {
//                textSliderView
//                        .description("")
//                        .image(modelObj.getAll_images()[i]);
//
//                viewpager.addSlider(textSliderView);
//            }
//            viewpager.setPresetTransformer(SliderLayout.Transformer.Accordion);
//            viewpager.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
//            viewpager.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
//            viewpager.setSliderTransformDuration(4000, null );
//            viewpager.startAutoCycle();
//            viewpager.setDuration(4000);
//
//            viewpager.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });
            Log.i("resultIdOffer",modelObj.getId() );
            Log.i("resultIdOffer",yourPreference.getDataInteger("user_id")+" jiug" );

        }catch (Exception e){e.printStackTrace();}

        likenow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if(likes_happen!=null && likes_happen.contains(modelObj.getId())){

                    }else {
                        List<NameValuePair> prams = new ArrayList<NameValuePair>();
                        prams.add(new BasicNameValuePair("user_id", "" + yourPreference.getDataInteger("user_id")));
                        prams.add(new BasicNameValuePair("offer_id", "" + modelObj.getId()));
                        getsAllOfferData(Urls.likes, prams);

                    }
                }catch (Exception e) {
                    e.printStackTrace();
                    try{
                        List<NameValuePair> prams = new ArrayList<NameValuePair>();
                        prams.add(new BasicNameValuePair("user_id", "" + yourPreference.getDataInteger("user_id")));
                        prams.add(new BasicNameValuePair("offer_id", "" + modelObj.getId()));
                        getsAllOfferData(Urls.likes, prams);
                    }catch (Exception s){s.printStackTrace();}
                }
            }
        });
        List<Comment_Model> comment_models= new ArrayList<Comment_Model>();
        comment_models= modelObj.getComment_models();
        for(int i=0; i<comment_models.size(); i++){
            AppCompatTextView appCompatTextView= new AppCompatTextView(ItemDetailActivity.this);
            LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(10,10,10,10);
            appCompatTextView.setLayoutParams(layoutParams);
            appCompatTextView.setPadding(10,10,10,10);
            if(comment_models.get(i).getUser_id().equalsIgnoreCase(""+yourPreference.getDataInteger("user_id"))){
                appCompatTextView.setTextColor(Color.BLACK);
            }else{
                appCompatTextView.setTextColor(Color.BLACK);
            }
            appCompatTextView.setText((comment_models.get(i).getUser_name())+ " : "+ comment_models.get(i).getText());
            Log.i("comments", comment_models.size()+"==="+comment_models.get(i).getText()+"=="+comment_models.get(i).getComment_id());

            comments.addView(appCompatTextView);
            TextView textView= new TextView(ItemDetailActivity.this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
            textView.setBackgroundColor(getResources().getColor(R.color.gray_2));
            comments.addView(textView);
        }
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if(yourPreference.getData("user_name").equalsIgnoreCase("")){
                        Urls.ntifyregisternow(ItemDetailActivity.this);
                    }else {

                        if (addcomment.getText().toString().equalsIgnoreCase(""))
                            Toast.makeText(ItemDetailActivity.this, "write somthing to send", Toast.LENGTH_LONG).show();
                        else {
                            List<NameValuePair> prams = new ArrayList<NameValuePair>();
                            prams.add(new BasicNameValuePair("user", "" + yourPreference.getDataInteger("user_id")));
                            prams.add(new BasicNameValuePair("text", addcomment.getText().toString()));
                            prams.add(new BasicNameValuePair("offer", "" + modelObj.getId()));
                            getsAllOfferData(Urls.comments, prams);
                        }
                    }
                }catch (Exception e){e.printStackTrace();
                    Urls.ntifyregisternow(ItemDetailActivity.this);
                }
            }
        });
        shareNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shareBody = yourPreference.getData("share_message");
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
            }
        });


    }
    private void getsAllOfferData(final String url, List<NameValuePair> params)  {
        try {
            new LongOperationE(ItemDetailActivity.this, url, params, new LongOperationE.AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    try {
                       Log.i("result", output);
                        if(url.toString().equalsIgnoreCase(Urls.likes)){
                            JSONObject jsonObject= new JSONObject(output);
                            likenow.setBackgroundDrawable(getResources().getDrawable(R.drawable.thumbs_new));
                            if(likes_happen==null){
                                likes_happen= new ArrayList<String>();
                            }
                            likes_happen.add(modelObj.getId());
                            likes.setText(Integer.parseInt(likes.getText().toString())+1+"");
                            yourPreference.saveDataArrayString("likes_happen", likes_happen);
                            if(jsonObject.getInt("success")==1){
                               // Urls.notifystatenice(ItemDetailActivity.this, coordinatorLayout,"Status changed for like." );

                            }else{
                              //  Urls.notifystate(ItemDetailActivity.this, coordinatorLayout,"Error! Try Again");
                            }
                        }else if (url.equalsIgnoreCase(Urls.comments)){
                            addcomment.setText("");
                         JSONObject jsonObject= new JSONObject(output);
                            if(!jsonObject.getString("text").equalsIgnoreCase("")){
                                AppCompatTextView appCompatTextView= new AppCompatTextView(ItemDetailActivity.this);
                                LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                layoutParams.setMargins(10,10,10,10);
                                appCompatTextView.setLayoutParams(layoutParams);
                                appCompatTextView.setPadding(10,10,10,10);
                                    appCompatTextView.setTextColor(Color.BLACK);

                                appCompatTextView.setText(yourPreference.getData("user_name")+" : "+jsonObject.getString("text"));


                                comments.addView(appCompatTextView);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).execute();
        }catch (Exception e){e.printStackTrace();}


    }
    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
//        viewpager.stopAutoCycle();
        super.onStop();
    }
    private void initUI(){
        companyName= (AppCompatTextView)findViewById(R.id.companyName);
        offertext= (AppCompatTextView)findViewById(R.id.offertext);
        dateduntil= (AppCompatTextView)findViewById(R.id.dateduntil);
        datedFrom= (AppCompatTextView)findViewById(R.id.datedFrom);
        likes= (AppCompatTextView)findViewById(R.id.likes);
//        viewpager=(SliderLayout) findViewById(R.id.infiniteViewpager);
        pagerIndicator=(PagerIndicator)findViewById(R.id.custom_indicator);
        toolbar_title=(AppCompatTextView)toolbar.findViewById(R.id.toolbar_title);
        buttonSend=(AppCompatButton)findViewById(R.id.buttonSend);
        addcomment=(AppCompatEditText)findViewById(R.id.addcomment);
        linearimage=(LinearLayout)findViewById(R.id.linearimage);
        likenow=(AppCompatImageView)findViewById(R.id.likenow);
        shareNow=(AppCompatImageView)findViewById(R.id.shareNow);
        comments=(LinearLayout) findViewById(R.id.comments);
        buttonSend=(AppCompatButton)findViewById(R.id.buttonSend);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinate);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {


        super.onBackPressed();

    }

    protected void calculateSwipeRefreshFullHeight(RecyclerView recyclerView) {
        WindowManager windowManager = (WindowManager) ItemDetailActivity.this.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;

        if (DeviceUtils.isTablet(getResources())) {
            screenHeight = getWindow().getAttributes().height;
            screenWidth = getWindow().getAttributes().width;
        }

        // Make sure the description area below the image is at least 40% of the screen height
        int minDetailsHeightInPx = Math.round(screenHeight * 0.25f) ;
        int minDetailsWidthInPx = Math.round(screenHeight * 0.4f);

        int maxHeightInPx = screenHeight - minDetailsHeightInPx ;
        int maxWidthInPx = screenWidth - minDetailsWidthInPx;

        final int imageAreaHeight = Math.min(screenHeight, maxHeightInPx);
        int height = 0;
        for (int idx = 0; idx < recyclerView.getChildCount(); idx++ ) {
            View v = recyclerView.getChildAt(idx);
            PhotoView imgDisplay = (PhotoView) v.findViewById(R.id.imgDisplay);
            AppCompatTextView counts = (AppCompatTextView) v.findViewById(R.id.counts);
            imgDisplay.getLayoutParams().height =imageAreaHeight;
//            counts.getLayoutParams().height = Math.round(screenHeight * 0.2f) - 20;
            v.getLayoutParams().height = imageAreaHeight + 50;
            height += v.getLayoutParams().height;
        }
        recyclerView.getLayoutParams().height = height ;
    }

    private void loadAds(){
        final NativeExpressAdView adView = (NativeExpressAdView)findViewById(R.id.adView);
//        MobileAds.initialize(ItemDetailActivity.this, yourPreference.getData("admob"));
        MobileAds.initialize(ItemDetailActivity.this, getResources().getString(R.string.banner_ad_unit_id));
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).
                addTestDevice("7E8E3B900656AD9D213508C3D547EACC").build();



        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adView.setVisibility(View.VISIBLE);
            }
        });
    }

//    private void showNativeAd() {
//        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
//        nativeAdContainer = (LinearLayout) findViewById(R.id.native_ad_container);
//        adView1 = (LinearLayout) inflater.inflate(R.layout.facebook_ads, nativeAdContainer, false);
//        nativeAdContainer.addView(adView1);
//        NativeAd nativeAd = new NativeAd(ItemDetailActivity.this, "1906914256219500_1907550236155902");
//        final NativeAd finalNativeAd = nativeAd;
//
//        nativeAd.setAdListener(new com.facebook.ads.AdListener() {
//            @Override
//            public void onError(Ad ad, AdError adError) {
//                Log.e("ERROR MESSAGE", "" + adError.getErrorMessage());
//            }
//
//            @Override
//            public void onAdLoaded(Ad ad) {
//                if (ad != finalNativeAd) {
//                    return;
//                }
//
//
//                inflateAd(finalNativeAd, adView1);
//
//            }
//
//            @Override
//            public void onAdClicked(Ad ad) {
//
//            }
//
//
//        });
//
//
////        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
//        AdSettings.addTestDevice("5bad0f46173176b2ff13f59e2ba14feb");
////            AdSettings.addTestDevice("");
//        nativeAd.loadAd();
//    }

    private void inflateAd(NativeAd nativeAd, View adView) {
        // Create native UI using the ad metadata.
        ImageView nativeAdIcon = (ImageView) adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = (TextView) adView.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = (MediaView) adView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = (TextView) adView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = (TextView) adView.findViewById(R.id.native_ad_body);
        Button nativeAdCallToAction = (Button) adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdTitle.setText(nativeAd.getAdTitle());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdBody.setText(nativeAd.getAdBody());
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());

        // Download and display the ad icon.
        NativeAd.Image adIcon = nativeAd.getAdIcon();
        NativeAd.downloadAndDisplayImage(adIcon, nativeAdIcon);

        // Download and display the cover image.
        nativeAdMedia.setNativeAd(nativeAd);

        // Add the AdChoices icon
        LinearLayout adChoicesContainer = (LinearLayout) adView.findViewById(R.id.ad_choices_container);
        AdChoicesView adChoicesView = new AdChoicesView(ItemDetailActivity.this, nativeAd, true);
        adChoicesContainer.addView(adChoicesView);

        // Register the Title and CTA button to listen for clicks.
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);
        nativeAd.registerViewForInteraction(adView);
    }


}
