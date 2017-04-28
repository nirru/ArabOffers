package arab_offers.lue.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import arab_offers.lue.com.Adapters.FullScreenImageAdapter;
import arab_offers.lue.com.Models.ObjectModel;
import arab_offers.lue.com.Utils.YourPreference;
import arab_offers.lue.com.helper.Utils;


public class FullScreenViewActivity extends Activity {

	private Utils utils;
	private FullScreenImageAdapter adapter;
	private ViewPager viewPager;
    YourPreference yourPreference=null;
	ObjectModel modelObj;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen_view);
        yourPreference=YourPreference.getInstance(FullScreenViewActivity.this);
		viewPager = (ViewPager) findViewById(R.id.pager);

		utils = new Utils(getApplicationContext());
		modelObj = yourPreference.getModelKey("KeyModel");
		Intent i = getIntent();
		int position = i.getIntExtra("position", 0);

		adapter = new FullScreenImageAdapter(FullScreenViewActivity.this,
				modelObj.getAll_images());

		viewPager.setAdapter(adapter);

		// displaying selected image first
		viewPager.setCurrentItem(position);
	}
}
