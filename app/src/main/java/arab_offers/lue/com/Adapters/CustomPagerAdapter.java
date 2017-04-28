package arab_offers.lue.com.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import arab_offers.lue.com.R;

public  class CustomPagerAdapter extends PagerAdapter {
 
    Context mContext;
    LayoutInflater mLayoutInflater;
    String[] mResources;
    public CustomPagerAdapter(Context context, String[] urls) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResources= urls;
    }
 
    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }
 
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
 
        SliderLayout imageView = (SliderLayout) itemView.findViewById(R.id.imageView);
        TextSliderView textSliderView = new TextSliderView(mContext);
        // initialize a SliderLayout

        textSliderView
                .description("")
                .image(mResources[position])
                .setScaleType(BaseSliderView.ScaleType.Fit)
                ;

        //add your extra information
        textSliderView.bundle(new Bundle());
        imageView.setPresetTransformer(SliderLayout.Transformer.Accordion);
        imageView.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        imageView.setCustomAnimation(new DescriptionAnimation());
        imageView.setDuration(4000);
        imageView.addSlider(textSliderView);
        imageView.setSliderTransformDuration(4000, new FastOutLinearInInterpolator());
        container.addView(itemView);

        return itemView;
    }
 
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}