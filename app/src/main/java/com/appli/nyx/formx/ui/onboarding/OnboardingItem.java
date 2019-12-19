package com.appli.nyx.formx.ui.onboarding;

public class OnboardingItem {

	int Description, ScreenImg;

	public OnboardingItem(int description, int screenImg) {
		Description = description;
		ScreenImg = screenImg;
	}

	public int getDescription() {
		return Description;
	}

	public void setDescription(int description) {
		Description = description;
	}

	public int getScreenImg() {
		return ScreenImg;
	}

	public void setScreenImg(int screenImg) {
		ScreenImg = screenImg;
	}
}
