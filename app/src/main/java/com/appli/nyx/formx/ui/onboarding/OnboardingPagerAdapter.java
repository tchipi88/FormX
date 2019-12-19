package com.appli.nyx.formx.ui.onboarding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appli.nyx.formx.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class OnboardingPagerAdapter extends PagerAdapter {

	Context mContext;
	List<OnboardingItem> mListScreen;

	public OnboardingPagerAdapter(Context mContext, List<OnboardingItem> mListScreen) {
		this.mContext = mContext;
		this.mListScreen = mListScreen;
	}

	@NonNull
	@Override
	public Object instantiateItem(@NonNull ViewGroup container, int position) {

		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layoutScreen = inflater.inflate(R.layout.fragment_onboarding_slide, null);

		ImageView imgSlide = layoutScreen.findViewById(R.id.image);
		TextView description = layoutScreen.findViewById(R.id.description);

		description.setText(mListScreen.get(position).getDescription());
		imgSlide.setImageResource(mListScreen.get(position).getScreenImg());

		container.addView(layoutScreen);

		return layoutScreen;

	}

	@Override
	public int getCount() {
		return mListScreen.size();
	}

	@Override
	public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
		return view == o;
	}

	@Override
	public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

		container.removeView((View) object);

	}
}
