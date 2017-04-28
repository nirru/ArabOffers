package arab_offers.lue.com.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

import arab_offers.lue.com.R;
import arab_offers.lue.com.helper.TouchImageView;


public class FullScreenImageAdapter extends PagerAdapter {

	private Activity _activity;
	private String[] _imagePaths;
	private LayoutInflater inflater;

	// constructor
	public FullScreenImageAdapter(Activity activity,
								 String[]imagePaths) {
		this._activity = activity;
		this._imagePaths = imagePaths;
	}

	@Override
	public int getCount() {
		return this._imagePaths.length;
	}

	@Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }
	
	@Override
    public Object instantiateItem(ViewGroup container, int position) {
		PhotoView imgDisplay;
        ImageButton btnClose;
		AppCompatTextView counts;
 
        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container,
                false);
 
        imgDisplay = (PhotoView) viewLayout.findViewById(R.id.imgDisplay);
        btnClose = (ImageButton) viewLayout.findViewById(R.id.btnClose);
        counts = (AppCompatTextView) viewLayout.findViewById(R.id.counts);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
/*if(packageList.get(position).getImagearray().length<1){
                Drawable drawable2= context.getResources().getDrawable(R.drawable.loading_false);
                Glide.with(context).load(packageList.get(position).getImagearray()[0]).placeholder(drawable2);
            }else{
                Drawable drawable= context.getResources().getDrawable(R.drawable.loading);
                Glide.with(context).load(packageList.get(position).getImagearray()[0]).placeholder(drawable).into(holder.offerImage);
            }

        }catch (Exception e){e.printStackTrace();
            Drawable drawable2= context.getResources().getDrawable(R.drawable.loading_false);
            Glide.with(context).load(packageList.get(position).getImagearray()[0]).placeholder(drawable2);}*/
		try {
			counts.setText(+(position+1)+"/"+_imagePaths.length);


			if(_imagePaths.length<1){
				Drawable drawable2= _activity.getResources().getDrawable(R.drawable.loading_false);
				Glide.with(_activity).load(_imagePaths[position]).placeholder(drawable2).into(imgDisplay);
			}else{
				Drawable drawable= _activity.getResources().getDrawable(R.drawable.loading);
				Glide.with(_activity).load(_imagePaths[position]).placeholder(drawable).into(imgDisplay);
			}

		}catch (Exception e){e.printStackTrace();
			Drawable drawable2= _activity.getResources().getDrawable(R.drawable.loading_false);
			imgDisplay.setImageDrawable(drawable2);}
//
//		Glide.with(_activity).load(_imagePaths[position]).
//				placeholder(_activity.getResources().getDrawable(R.drawable.loading)).into(imgDisplay);
        // close button click event
        btnClose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				_activity.finish();
			}
		}); 

        ((ViewPager) container).addView(viewLayout);
 
        return viewLayout;
	}
	
	@Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);
 
    }

}
