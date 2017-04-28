package arab_offers.lue.com.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSettings;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdsManager;
import com.github.chrisbanes.photoview.PhotoView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import arab_offers.lue.com.Adapters.OfferAdapter;
import arab_offers.lue.com.Asyncs.LongOperationE;
import arab_offers.lue.com.Customs.PullAndLoadListView;
import arab_offers.lue.com.Customs.PullToRefreshListView;
import arab_offers.lue.com.Interfaces.Search;
import arab_offers.lue.com.ItemDetailActivity;
import arab_offers.lue.com.Models.OfferModel;
import arab_offers.lue.com.R;
import arab_offers.lue.com.Utils.DeviceUtils;
import arab_offers.lue.com.Utils.Urls;
import arab_offers.lue.com.Utils.YourPreference;

/**
 * Created by Belal on 2/3/2016.
 */
 
//Our class extending fragment
public class Tab3 extends Fragment{
    ListView list1;
    YourPreference yourPreference=null;
    OfferAdapter offerAdapter;
    ArrayList<OfferModel> modelObjs= new ArrayList<OfferModel>();
    ArrayList<Object> offerModel= new ArrayList<>();
    //Overriden method onCreateView
     int offset=0;

    private NativeAdsManager listNativeAdsManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3,
                container, false);
        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes


        list1= (PullAndLoadListView)rootView.findViewById(R.id.list_3);

        yourPreference=YourPreference.getInstance(getContext());

        getsAllOfferData(yourPreference.getDataInteger("user_id"),1,0);

        return rootView;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            new PullToRefreshDataTask().execute();
            final Handler handler= new Handler();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    searchable();
                    handler.postDelayed(this, 2000);
                }
            }).start();

        }else{
            // fragment is no longer visible
        }
    }
    private void showAllOfferData() {
        try {
             offerAdapter = new OfferAdapter(getContext(), yourPreference.getOffersList("offerstab1"));
            list1.setAdapter(offerAdapter);


//            listNativeAdsManager = new NativeAdsManager(getActivity(),"1906914256219500_1906915486219377",5);
//            listNativeAdsManager.setListener(new NativeAdsManager.Listener() {
//                @Override
//                public void onAdsLoaded() {
//                    NativeAd ad = listNativeAdsManager.nextNativeAd();
////                ad.setAdListener(this);
//                    offerAdapter.addNativeAd(ad);
//                }
//
//                @Override
//                public void onAdError(AdError adError) {
//                    Log.e("Error Meeage=","" + adError.getErrorCode());
//                }
//            });
//            String android_id = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
//
//            AdSettings.addTestDevice(android_id);
//            listNativeAdsManager.loadAds();
        }catch (Exception e){e.printStackTrace();}
    }

    private void getsAllOfferData(int user_id, int type, int offset)  {
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("user_id", ""+user_id));
            params.add(new BasicNameValuePair("type", ""+type));
            params.add(new BasicNameValuePair("offset", ""+offset));
            new LongOperationE(getContext(), Urls.IP + Urls.getGetallOffers(getContext()), params, new LongOperationE.AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    try {
                        yourPreference = YourPreference.getInstance(getContext());
                        JSONArray jsonArray = new JSONArray(output);
                        offerModel.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonobject = jsonArray.getJSONObject(i);
                            String id = jsonobject.getString("id");
                            String name = jsonobject.getString("name");
                            String publish_date = jsonobject.getString("publish_date");
                            String start_date = jsonobject.getString("start_date");
                            start_date=start_date.substring(0, start_date.indexOf("T")==-1?start_date.length():start_date.indexOf("T"));
                            String end_date = jsonobject.getString("end_date");
                            end_date=end_date.substring(0, end_date.indexOf("T")==-1?end_date.length():end_date.indexOf("T"));
                            Boolean special_offer = jsonobject.getBoolean("special_offer");
                            String views = jsonobject.getString("views");
                            String description = jsonobject.getString("description");
                            String comments = jsonobject.getString("comments");
                            String likes = jsonobject.getString("likes");
                            JSONArray arrJson = jsonobject.getJSONArray("all_images");
                            String[] arr = new String[arrJson.length()];
                            for (int j = 0; j < arrJson.length(); j++) {
                                arr[j] = Urls.IP + arrJson.getString(j);
                                Log.i("result", arr[j]);
                            }
                            offerModel.add(new OfferModel(id, name, publish_date, start_date,
                                    end_date, views, description, comments, likes, special_offer, arr));
                            int p = (i + 1)* 3;
                            int k = p%15;
                            int l;
                            if (k==0){
                                l = p/15;
                                if (l%2==0){
                                    offerModel.add(new OfferModel("B", "", "", "", "", "", "", "", "", false,null));
                                }else{
                                    offerModel.add(new OfferModel("A", "", "", "", "", "", "", "", "", false,null));
                                }
                            }
//                            if(((offerModel.size()*3)/15)%2!=0){
//                                offerModel.add(new OfferModel("A", "", "", "", "", "", "", "", "", false,null));
//                            }
//                            if(((offerModel.size()*6)/15)%2==0){
//                                offerModel.add(new OfferModel("B", "", "", "", "", "", "", "", "", false,null));
//                            }


                        }
                        yourPreference.saveOffersList("offerstab1", offerModel);
                        Log.i("offerModel", "size  " + offerModel.size());
                        showAllOfferData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).execute();
        }catch (Exception e){e.printStackTrace();}
    }
    public void searchable(){
        if(this!=null && this.isVisible()) {
            ((SearchView) getActivity().findViewById(R.id.searchView)).setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {

                    return false;
                }

                @Override
                public boolean onQueryTextChange(final String newText) {
                    Log.i("clicked", "clicked");
                    offerAdapter.getFilter().filter(newText.toString());
                    return false;
                }
            });
        }
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Set a listener to be invoked when the list should be refreshed.
        ((PullAndLoadListView) list1)
                .setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {

                    public void onRefresh() {
                        // Do work to refresh the list here.
                        new PullToRefreshDataTask().execute();
                    }
                });

        // set a listener to be invoked when the list reaches the end
        ((PullAndLoadListView) list1)
                .setOnLoadMoreListener(new PullAndLoadListView.OnLoadMoreListener() {

                    public void onLoadMore() {
                        // Do the work to load more items at the end of list
                        // here
                        new LoadMoreDataTask().execute();
                    }
                });


    }
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(this!=null && this.isVisible()) {
            ((SearchView) getActivity().findViewById(R.id.searchView)).setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {

                    return false;
                }

                @Override
                public boolean onQueryTextChange(final String newText) {
                    Log.i("clicked", "clicked");
                    offerAdapter.getFilter().filter(newText.toString());
                    return false;
                }
            });
        }
    }

    private class LoadMoreDataTask extends AsyncTask<Void, Void, Void> {
        ArrayList<OfferModel> offerNew= new ArrayList<OfferModel>();
        @Override
        protected Void doInBackground(Void... params) {

            if (isCancelled()) {
                return null;
            }
            try {
                // Simulates a background task
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                }

          /*for (int i = 0; i < offerModel.size(); i++)
              offerNew.add(offerModel.get(i));

        //  offerModel.addAll(offerNew);*/
            }catch (Exception e){e.printStackTrace();}
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //offerNew.add("Added after load more");

            // We need notify the adapter that the data have been changed
            ((BaseAdapter) offerAdapter).notifyDataSetChanged();

            // Call onLoadMoreComplete when the LoadMore task, has finished
            ((PullAndLoadListView) list1).onLoadMoreComplete();

            super.onPostExecute(result);
        }

        @Override
        protected void onCancelled() {
            // Notify the loading more operation has finished
            ((PullAndLoadListView) list1).onLoadMoreComplete();
        }
    }
    private class PullToRefreshDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {


            if (isCancelled()) {
                return null;
            }

            // Simulates a background task
            try {
                Thread.sleep(1000);
                offerModel.clear();
                getsAllOfferData(yourPreference.getDataInteger("user_id"), 1,0);
                offerAdapter= new OfferAdapter(getContext(), offerModel);
            } catch (Exception e) {
            }

            /*for (int i = 0; i < mAnimals.length; i++)
                mListItems.addFirst(mAnimals[i]);*/

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //mListItems.addFirst("Added after pull to refresh");

            // We need notify the adapter that the data have been changed
            list1.setAdapter(offerAdapter);
            ((BaseAdapter) offerAdapter).notifyDataSetChanged();

            // Call onLoadMoreComplete when the LoadMore task, has finished
            ((PullAndLoadListView) list1).onRefreshComplete();

            super.onPostExecute(result);
        }

        @Override
        protected void onCancelled() {
            // Notify the loading more operation has finished
            ((PullAndLoadListView) list1).onLoadMoreComplete();
        }
    }

}