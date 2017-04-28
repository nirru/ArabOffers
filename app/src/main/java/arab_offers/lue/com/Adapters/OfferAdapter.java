package arab_offers.lue.com.Adapters;

/**
 * Created by Fujitsu on 25-11-2016.
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.bridge.Bridge;
import com.afollestad.bridge.Form;
import com.afollestad.bridge.Request;
import com.afollestad.bridge.Response;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.facebook.ads.Ad;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import arab_offers.lue.com.Asyncs.LongOperationE;
import arab_offers.lue.com.Asyncs.LongOperationF;
import arab_offers.lue.com.ItemDetailActivity;
import arab_offers.lue.com.Models.Comment_Model;
import arab_offers.lue.com.Models.ImageModal;
import arab_offers.lue.com.Models.ModelObj;
import arab_offers.lue.com.Models.ObjectModel;
import arab_offers.lue.com.Models.OfferModel;
import arab_offers.lue.com.R;
import arab_offers.lue.com.Utils.DeviceUtils;
import arab_offers.lue.com.Utils.Urls;
import arab_offers.lue.com.Utils.YourPreference;


public class OfferAdapter extends BaseAdapter implements Filterable{

    ArrayList<Object> packageList= new ArrayList<Object>();
    ArrayList<Object> displayedList= new ArrayList<Object>();
    Context context;
    public boolean[] itemChecked;
    boolean[] favorite;
    YourPreference yourPreference;
    HashSet<OfferModel> savedJobs= new HashSet<OfferModel>();
    AdRequest adRequest;
    private NativeAd ad;
    private static final int AD_INDEX = 2;
    int screenWidth,screenHeight;
    int minDetailsWidthInPx,minDetailsWidthInPx1;
    int minDetailsHeightInPx,minDetailsHeightInPx1;
    public OfferAdapter(Context context, ArrayList<Object> packageList) {
        super();
        this.context = context;
        this.packageList = packageList;
        this.displayedList= packageList;
        itemChecked = new boolean[packageList.size()];
        favorite = new boolean[packageList.size()];
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;

        if (DeviceUtils.isTablet(context.getResources())) {
            screenHeight = ((AppCompatActivity)context).getWindow().getAttributes().height;
            screenWidth = ((AppCompatActivity)context).getWindow().getAttributes().width;
        }

        // Make sure the description area below the image is at least 40% of the screen height
         minDetailsHeightInPx = Math.round(screenHeight * 0.30f) ;
         minDetailsHeightInPx1 = Math.round(screenHeight * 0.30f) ;
         minDetailsWidthInPx = Math.round(screenWidth * 0.85f);
         minDetailsWidthInPx1 = Math.round(screenWidth * 0.15f);
        notifyDataSetChanged();
    }



    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        try {
            boolean[] fav = new boolean[this.packageList.size()];
            boolean[] itemch = new boolean[this.packageList.size()];
            for (int i = 0; i < favorite.length; i++) {
                fav[i] = favorite[i];
                itemch[i] = itemChecked[i];
            }
            this.favorite = new boolean[this.packageList.size()];
            this.itemChecked = new boolean[this.packageList.size()];
            favorite = fav;
            itemChecked = itemch;
        }catch (Exception e){e.printStackTrace();}
        MobileAds.initialize(context, context.getResources().getString(R.string.banner_ad_unit_id));
        adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).
                addTestDevice("7E8E3B900656AD9D213508C3D547EACC").build();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                packageList = (ArrayList<Object>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<OfferModel> FilteredArrList = new ArrayList<OfferModel>();

                if (displayedList == null) {
                    displayedList = new ArrayList<>(displayedList.size()); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = displayedList.size();
                    results.values = displayedList;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < displayedList.size(); i++) {
                        String data1 = ((OfferModel)displayedList.get(i)).getName();
                        String data2 = ((OfferModel)displayedList.get(i)).getDescription();
                        if (data1.toLowerCase().contains(constraint.toString())||data2.toLowerCase().contains(constraint.toString())) {
                            FilteredArrList.add(((OfferModel)displayedList.get(i)));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }
    private void getsAllOfferData(String url, List<NameValuePair> params)  {
        try {

            new LongOperationF(context, url, null, new LongOperationF.AsyncResponse() {
                @Override
                public void processFinish(String output) {

                    try {JSONObject jsonObject= new JSONObject(output);
                        Log.i("result", jsonObject.toString());
                        List<Comment_Model> comments= new ArrayList<Comment_Model>();


                        JSONArray jsonArrayimages=jsonObject.getJSONArray("all_images");

                        String[] images= new String[jsonArrayimages.length()];
                        for(int i=0; i<jsonArrayimages.length();i++){
                            images[i]= Urls.IP+jsonArrayimages.getString(i);
                            Log.i("result", images[i]);
                        }
                        try {
                            JSONArray jsonArray = jsonObject.getJSONArray("all_comments");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String user = jsonObject1.getString("user");
                                JSONObject jsonObject2 = new JSONObject(user);

                                comments.add(new Comment_Model(jsonObject1.getString("id"), jsonObject1.getString("text"),
                                        jsonObject1.getString("time"), jsonObject2.getString("id"), jsonObject2.getString("name"),
                                        jsonObject2.getString("mobile"), jsonObject2.getString("city")));

                            }
                        }catch (Exception e){e.printStackTrace();}
                        ObjectModel objectModel= new ObjectModel(""+jsonObject.getInt("id"),jsonObject.getString("name"), jsonObject.getString("publish_date"),
                                jsonObject.getString("start_date"), jsonObject.getString("end_date"), jsonObject.getInt("views")+"",
                                jsonObject.getString("description"), images,""+jsonObject.getInt("likes"),comments,jsonObject.getBoolean("special_offer")

                                ,jsonObject.getBoolean("is_comments_enabled") );
                        yourPreference.saveKeyModel("KeyModel", objectModel);
                        context.startActivity(new Intent(context, ItemDetailActivity.class));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).execute();
        }catch (Exception e){e.printStackTrace();}
    }
    private class ViewHolder {
        AppCompatTextView special, companyName, offerText,  datedUntil, datedFrom, views, likes;
        AppCompatImageView offerImage;
        ImageView img_sec_one,img_sec_two,img_sec_three;
        TextView image_count;
        TextView more_txt;
        WebView webView;
        CardView cards;
        View shadow;
        LinearLayout linearlist_item;
        FrameLayout fm_layout;
        NativeExpressAdView adView;

        LinearLayout  nativeAdContainer;
        LinearLayout  adView1;
    }

    public int getCount() {
        return packageList.size();
    }

    public Object getItem(int position) {
        return packageList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }
    private static final int GENRE_ROW = 0;
    private static final int ARTIST_ROW = 1;
    private static final int FB_ROW = 2;

    @Override
    public int getItemViewType(int position) {
        // Return the row type at the position
        OfferModel item = (OfferModel) packageList.get(position);
        if (item.getId().equals("A")){
            return GENRE_ROW;
        }if (item.getId().equals("B")){
            return FB_ROW;
        }else {
            return ARTIST_ROW;
        }
//        return item.getId().equalsIgnoreCase("") ? GENRE_ROW : ARTIST_ROW;
    }

    @Override
    public int getViewTypeCount() {
        // Return the number of row types there are
        return 3;
    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = null;
        yourPreference= YourPreference.getInstance(context);
//        boolean isGenreRow = getItemViewType(position) == GENRE_ROW;
        int k = getItemViewType(position);
        Log.e("UUUU==","" + k );
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        // Inflate views if this is a new row
        ViewHolder holder = null;
        if (convertView == null) {
            // Get the right type of view depending on the type of data
            if (k==0) {
                row = inflater.inflate(R.layout.adview_item, null);
                holder = new ViewHolder();
                holder.adView= (NativeExpressAdView)row.findViewById(R.id.adView);
            }
             if (k==2){
                 row = inflater.inflate(R.layout.native_ads, null);
                 holder = new ViewHolder();
                 holder.nativeAdContainer = (LinearLayout) row.findViewById(R.id.native_ad_container);
                 holder.adView1 = (LinearLayout) inflater.inflate(R.layout.facebook_ads, holder.nativeAdContainer, false);
                 holder.nativeAdContainer.addView(holder.adView1);
            }
            if (k==1) {
                row = inflater.inflate(R.layout.list_item_layout, null);
                holder = new ViewHolder();
                holder.shadow = (View)row.findViewById(R.id.shadow_view);
                holder.fm_layout = (FrameLayout)row.findViewById(R.id.frame_l1);
                holder.webView = (WebView)row.findViewById(R.id.webView) ;
                holder.more_txt = (TextView)row.findViewById(R.id.more);
                holder.img_sec_one = (ImageView) row.findViewById(R.id.img_Section1);
                holder.img_sec_two = (ImageView) row.findViewById(R.id.img_Section2);
                holder.img_sec_three = (ImageView) row.findViewById(R.id.img_Section3);
                holder.image_count = (TextView)row.findViewById(R.id.more_txt);
                holder.special= (AppCompatTextView) row.findViewById(R.id.special);
                holder.companyName= (AppCompatTextView) row.findViewById(R.id.companyName);
                holder.offerText= (AppCompatTextView) row.findViewById(R.id.offertext);
                holder.offerImage= (AppCompatImageView) row.findViewById(R.id.offerImage);
                holder.datedUntil= (AppCompatTextView) row.findViewById(R.id.dateduntil);
                holder.datedFrom= (AppCompatTextView) row.findViewById(R.id.datedFrom);
                holder.views= (AppCompatTextView) row.findViewById(R.id.views);
                holder.likes= (AppCompatTextView) row.findViewById(R.id.likes);
                holder.cards= (CardView)row.findViewById(R.id.cards);
                holder.linearlist_item=(LinearLayout)row.findViewById(R.id.linearlist_item);
            }
            row.setTag(holder);
        }
        // Otherwise, use the recycled row
        else {
            row = convertView;
            holder = (ViewHolder) row.getTag();
        }

        // Bind data to views, depending on data type
        OfferModel item = (OfferModel) packageList.get(position);
        if (k==0) {
            holder.adView.setVisibility(View.VISIBLE);
            //MobileAds.initialize(context, context.getResources().getString(R.string.banner_ad_unit_id));
            MobileAds.initialize(context, yourPreference.getData("admob"));
            adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).
                    addTestDevice("7E8E3B900656AD9D213508C3D547EACC").build();



            holder.adView.loadAd(adRequest);
            final ViewHolder finalHolder1 = holder;
            holder.adView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    finalHolder1.adView.setVisibility(View.VISIBLE);
                }
            });
        }
        if(k==2){
//            showNativeAd(holder.adView1);
        }
        if(k==1) {
            holder.linearlist_item.setVisibility(View.VISIBLE);
            if(!((OfferModel)packageList.get(position)).isSpecial_offer()){
                holder.special.setVisibility(View.GONE);
            }else{
                holder.special.setVisibility(View.VISIBLE);
            }

            String sHtmlTemplate = "<html><head></head><body><img src=file:///android_asset/fb_loader.gif></body></html>";

//
            if (((OfferModel)packageList.get(position)).getImagearray().length>=4){



                holder.img_sec_one.setVisibility(View.VISIBLE);
                holder.offerImage.getLayoutParams().width=minDetailsWidthInPx;
                holder.offerImage.getLayoutParams().height=minDetailsHeightInPx + context.getResources().getDimensionPixelSize(R.dimen.margin_top_10);

                holder.img_sec_one.getLayoutParams().height = minDetailsHeightInPx/3 ;
                holder.img_sec_one.getLayoutParams().width =  minDetailsWidthInPx1 ;

                holder.img_sec_two.getLayoutParams().height = minDetailsHeightInPx/3 ;
                holder.img_sec_two.getLayoutParams().width =  minDetailsWidthInPx1 ;

                holder.img_sec_three.getLayoutParams().height = minDetailsHeightInPx/3 ;
                holder.img_sec_three.getLayoutParams().width =  minDetailsWidthInPx1 ;

                holder.fm_layout.getLayoutParams().height = minDetailsHeightInPx/3;
                holder.fm_layout.getLayoutParams().width =  minDetailsWidthInPx1 - 15;

//                holder.shadow.getLayoutParams().height = minDetailsHeightInPx/3;
//                holder.shadow.getLayoutParams().width =  minDetailsWidthInPx1 - 15;


                if (((OfferModel)packageList.get(position)).getImagearray().length>4){
                    holder.image_count.setVisibility(View.VISIBLE);
                    int total_image_count = ((OfferModel)packageList.get(position)).getImagearray().length;
                    holder.image_count.setText("+" + (total_image_count - 4)  + "");
                }else{
                    holder.image_count.setVisibility(View.GONE);
                }

                if (!((OfferModel)packageList.get(position)).getImagearray()[0].equals("") && ((OfferModel)packageList.get(position)).getImagearray()[0]!=null) {
//                    holder.webView.loadDataWithBaseURL(null, sHtmlTemplate, "text/html", "utf-8",null);
                    Picasso.with(context)
                            .load(((OfferModel)packageList.get(position)).getImagearray()[0])
                            .config(Bitmap.Config.RGB_565)
                            .placeholder(R.drawable.loading)
                            .error(R.drawable.loading_false)
                            .resize(holder.offerImage.getLayoutParams().width, holder.offerImage.getLayoutParams().height)
                            .centerCrop()
                            .into(holder.offerImage);
                }

                if (!((OfferModel)packageList.get(position)).getImagearray()[1].equals("") && ((OfferModel)packageList.get(position)).getImagearray()[1]!=null)
                    Picasso.with(context)
                            .load(((OfferModel)packageList.get(position)).getImagearray()[1])
                            .config(Bitmap.Config.RGB_565)
                            .placeholder(R.drawable.loading)
                            .error(R.drawable.loading_false)
                            .resize(holder.img_sec_one.getLayoutParams().width, holder.img_sec_one.getLayoutParams().height)
                            .centerCrop()
                            .into(holder.img_sec_one);

                holder.img_sec_two.setVisibility(View.VISIBLE);
                if (!((OfferModel)packageList.get(position)).getImagearray()[2].equals("") && ((OfferModel)packageList.get(position)).getImagearray()[2]!=null)
                    Picasso.with(context)
                            .load(((OfferModel)packageList.get(position)).getImagearray()[2])
                            .config(Bitmap.Config.RGB_565)
                            .placeholder(R.drawable.loading)
                            .error(R.drawable.loading_false)
                            .resize(holder.img_sec_two.getLayoutParams().width, holder.img_sec_two.getLayoutParams().height)
                            .centerCrop()
                            .into(holder.img_sec_two);

                holder.fm_layout.setVisibility(View.VISIBLE);
                holder.img_sec_three.setVisibility(View.VISIBLE);
                if (!((OfferModel)packageList.get(position)).getImagearray()[3].equals("") && ((OfferModel)packageList.get(position)).getImagearray()[3]!=null)
                    Picasso.with(context)
                            .load(((OfferModel)packageList.get(position)).getImagearray()[3])
                            .config(Bitmap.Config.RGB_565)
                            .placeholder(R.drawable.loading)
                            .error(R.drawable.loading_false)
                            .resize(holder.img_sec_three.getLayoutParams().width, holder.img_sec_three.getLayoutParams().height)
                            .centerCrop()
                            .into(holder.img_sec_three);
            }
            if (((OfferModel)packageList.get(position)).getImagearray().length==3){
                if (!((OfferModel)packageList.get(position)).getImagearray()[0].equals("") && ((OfferModel)packageList.get(position)).getImagearray()[0]!=null) {
                    Picasso.with(context)
                            .load(((OfferModel)packageList.get(position)).getImagearray()[0])
                            .config(Bitmap.Config.RGB_565)
                            .placeholder(R.drawable.loading)
                            .error(R.drawable.loading_false)
                            .fit()
                            .centerCrop()
                            .into(holder.offerImage);
                }
                holder.offerImage.getLayoutParams().width=minDetailsWidthInPx;
                holder.offerImage.getLayoutParams().height=minDetailsHeightInPx;

                holder.img_sec_one.getLayoutParams().height = minDetailsHeightInPx/2;
                holder.img_sec_one.getLayoutParams().width =  minDetailsWidthInPx1 - 5;

                holder.img_sec_two.getLayoutParams().height = minDetailsHeightInPx/2;
                holder.img_sec_two.getLayoutParams().width =  minDetailsWidthInPx1 - 5;
                holder.img_sec_one.setVisibility(View.VISIBLE);
                if (!((OfferModel)packageList.get(position)).getImagearray()[1].equals("") && ((OfferModel)packageList.get(position)).getImagearray()[1]!=null)
                    Picasso.with(context)
                            .load(((OfferModel)packageList.get(position)).getImagearray()[1])
                            .config(Bitmap.Config.RGB_565)
                            .placeholder(R.drawable.loading)
                            .error(R.drawable.loading_false)
                            .resize(holder.img_sec_one.getLayoutParams().width, holder.img_sec_one.getLayoutParams().height)
                            .centerCrop()
                            .into(holder.img_sec_one);

                holder.img_sec_two.setVisibility(View.VISIBLE);
                if (!((OfferModel)packageList.get(position)).getImagearray()[2].equals("") && ((OfferModel)packageList.get(position)).getImagearray()[2]!=null)
                    Picasso.with(context)
                            .load(((OfferModel)packageList.get(position)).getImagearray()[2])
                            .config(Bitmap.Config.RGB_565)
                            .placeholder(R.drawable.loading)
                            .error(R.drawable.loading_false)
                            .resize(holder.img_sec_two.getLayoutParams().width, holder.img_sec_two.getLayoutParams().height)
                            .centerCrop()
                            .into(holder.img_sec_two);

                holder.fm_layout.setVisibility(View.GONE);
                holder.img_sec_three.setVisibility(View.GONE);

                holder.image_count.setVisibility(View.GONE);
            }
            if (((OfferModel)packageList.get(position)).getImagearray().length==2){

                holder.img_sec_one.setVisibility(View.VISIBLE);

                holder.offerImage.getLayoutParams().width=Math.round(minDetailsWidthInPx * 0.50f);
                holder.offerImage.getLayoutParams().height=minDetailsHeightInPx;

                holder.img_sec_one.getLayoutParams().height = minDetailsHeightInPx ;
                holder.img_sec_one.getLayoutParams().width =  Math.round(minDetailsWidthInPx * 0.50f) ;

                if (!((OfferModel)packageList.get(position)).getImagearray()[0].equals("") && ((OfferModel)packageList.get(position)).getImagearray()[0]!=null) {
                    Picasso.with(context)
                            .load(((OfferModel)packageList.get(position)).getImagearray()[0])
                            .config(Bitmap.Config.RGB_565)
                            .placeholder(R.drawable.loading)
                            .error(R.drawable.loading_false)
                            .resize(holder.offerImage.getLayoutParams().width, holder.offerImage.getLayoutParams().height)
                            .centerCrop()
                            .into(holder.offerImage);
                }
                if (!((OfferModel)packageList.get(position)).getImagearray()[1].equals("") && ((OfferModel)packageList.get(position)).getImagearray()[1]!=null)
                    Picasso.with(context)
                            .load(((OfferModel)packageList.get(position)).getImagearray()[1])
                            .config(Bitmap.Config.RGB_565)
                            .placeholder(R.drawable.loading)
                            .error(R.drawable.loading_false)
                            .resize(holder.img_sec_one.getLayoutParams().width, holder.img_sec_one.getLayoutParams().height)
                            .centerCrop()
                            .into(holder.img_sec_one);

                holder.fm_layout.setVisibility(View.GONE);
                holder.img_sec_two.setVisibility(View.GONE);
                holder.img_sec_three.setVisibility(View.GONE);

                holder.image_count.setVisibility(View.GONE);
            }


            if (((OfferModel)packageList.get(position)).getImagearray().length==1){
                holder.offerImage.setVisibility(View.VISIBLE);
                holder.offerImage.getLayoutParams().width=minDetailsWidthInPx;
                holder.offerImage.getLayoutParams().height=minDetailsHeightInPx;

                if (!((OfferModel)packageList.get(position)).getImagearray()[0].equals("") && ((OfferModel)packageList.get(position)).getImagearray()[0]!=null) {
                    Picasso.with(context)
                            .load(((OfferModel)packageList.get(position)).getImagearray()[0])
                            .config(Bitmap.Config.RGB_565)
                            .placeholder(R.drawable.loading)
                            .error(R.drawable.loading_false)
                            .resize(holder.offerImage.getLayoutParams().width, holder.offerImage.getLayoutParams().height)
                            .centerCrop()
                            .into(holder.offerImage);
                }

                holder.fm_layout.setVisibility(View.GONE);
                holder.img_sec_one.setVisibility(View.GONE);
                holder.img_sec_two.setVisibility(View.GONE);
                holder.img_sec_three.setVisibility(View.GONE);

                holder.image_count.setVisibility(View.GONE);
            }
//



            holder.companyName.setText(((OfferModel)packageList.get(position)).getName());
            holder.offerText.setText(((OfferModel)packageList.get(position)).getDescription());
            holder.datedUntil.setText(((OfferModel)packageList.get(position)).getEnd_date());
            holder.datedFrom.setText(((OfferModel)packageList.get(position)).getStart_date());
            holder.views.setText(((OfferModel)packageList.get(position)).getViews());
            holder.likes.setText(((OfferModel)packageList.get(position)).getLikes());
            holder.cards.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<NameValuePair> params= new ArrayList<NameValuePair>();
                    Log.i("user_id", yourPreference.getDataInteger("user_id")+"===");
                    params.add(new BasicNameValuePair("user_id", ""+yourPreference.getDataInteger("user_id")));
                    getsAllOfferData(Urls.IP+Urls.getallOffers+((OfferModel)packageList.get(position)).getId()+"/", params);
                }
            });

            Paint paint = new Paint();
            Rect bounds = new Rect();

            int text_width = 0;

            paint.setTypeface(Typeface.DEFAULT);// your preference here
            paint.setTextSize(25);// have this the same as your text size

            String text = holder.offerText.getText().toString();

            paint.getTextBounds(text, 0, text.length(), bounds);

            text_width =  bounds.width();
//            Log.e("WIDTH==", "" + text_width) ;
//            Log.e("MINDETAIL==", "" + minDetailsWidthInPx) ;
            if (text_width>=minDetailsWidthInPx){
                holder.more_txt.setVisibility(View.VISIBLE);
            }else{
                holder.more_txt.setVisibility(View.GONE);
            }
//
        }

        return row;
    }


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
        AdChoicesView adChoicesView = new AdChoicesView(context, nativeAd, true);
        adChoicesContainer.addView(adChoicesView);

        // Register the Title and CTA button to listen for clicks.
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);
        nativeAd.registerViewForInteraction(adView);
    }

    public synchronized void addNativeAd(NativeAd ad) {
        if (ad == null) {
            return;
        }
        if (this.ad != null) {
            // Clean up the old ad before inserting the new one
            this.ad.unregisterView();
            this.packageList.remove(AD_INDEX);
            this.ad = null;
            this.notifyDataSetChanged();
        }
        this.ad = ad;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View adView = inflater.inflate(R.layout.facebook_ads, null);
        inflateAd(ad, adView);
        packageList.add(AD_INDEX, adView);
        this.notifyDataSetChanged();
    }

//    private void showNativeAd(final View adView ) {
//        NativeAd nativeAd = new NativeAd(context, "1906914256219500_1907550236155902");
//        final NativeAd finalNativeAd = nativeAd;
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
//                inflateAd(finalNativeAd, adView);
//
//            }
//
//            @Override
//            public void onAdClicked(Ad ad) {
//
//            }
//        });
////        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
//         AdSettings.addTestDevice("5bad0f46173176b2ff13f59e2ba14feb");
////            AdSettings.addTestDevice("");
//        nativeAd.loadAd();
//    }
}


