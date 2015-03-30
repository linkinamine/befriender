package com.amine.befriender.fragment.base;

import android.graphics.Color;
import android.view.View;

public abstract class BaseLoginDemoFragment extends BaseDemoFragment implements
		View.OnClickListener {

	@Override
	public void onSocialNetworkManagerInitialized() {
		if (mSocialNetworkManager.getTwitterSocialNetwork().isConnected()) {
			mTwitterButton.setText("Disconnect Twitter");
			mTwitterButton.setBackgroundColor(Color.LTGRAY);
			// mTwitterButton.setOnClickListener(null);
		} 
		else {
			mTwitterButton.setText("Connect Twitter");
			mTwitterButton.setBackgroundColor(Color.parseColor("#55acee"));
		}

		if (mSocialNetworkManager.getLinkedInSocialNetwork().isConnected()) {
			mLinkedInButton.setText("Disconnect Linkedin");
			mLinkedInButton.setBackgroundColor(Color.LTGRAY);
			// mLinkedInButton.setOnClickListener(null);
		} 
		else {
			mLinkedInButton.setText("Connect Linkedin");
			mLinkedInButton.setBackgroundColor(Color.parseColor("#0e7cb7"));

		}

		if (mSocialNetworkManager.getFacebookSocialNetwork().isConnected()) {
			mFacebookButton.setText("Disconnect Facebook");
			mFacebookButton.setBackgroundColor(Color.LTGRAY);
			// mFacebookButton.setOnClickListener(null);
		} 
		else {
			mFacebookButton.setText("Connect Facebook");
			mFacebookButton.setBackgroundColor(Color.parseColor("#3b5998"));

		}
		if (mSocialNetworkManager.getGooglePlusSocialNetwork().isConnected()) {
			mGooglePlusButton.setText("Disconnect Goolge plus");
			mGooglePlusButton.setBackgroundColor(Color.LTGRAY);
			// mGooglePlusButton.setOnClickListener(null);
		} 
 else {
			mGooglePlusButton.setText("Connect Google plus");
			mGooglePlusButton.setBackgroundColor(Color.parseColor("#c83929"));

		}
	}

}
