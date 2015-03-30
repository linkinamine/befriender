package com.amine.befriender.fragment.demo;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.amine.befriender.fragment.base.BaseDemoFragment;
import com.amine.befriender.fragment.base.BaseLoginDemoFragment;
import com.befriender.lib.SocialNetwork;
import com.befriender.lib.listener.OnLoginCompleteListener;

public class LoginUsingGlobalListenersFragment extends BaseLoginDemoFragment
		implements OnLoginCompleteListener {

	private static final String TAG = LoginUsingGlobalListenersFragment.class
			.getSimpleName();

	public static LoginUsingGlobalListenersFragment newInstance() {
		return new LoginUsingGlobalListenersFragment();
	}

	@Override
	public void onError(int socialNetworkID, String requestID,
			String errorMessage, Object data) {
		hideProgress();
		handleError(errorMessage);
	}

	@Override
	protected void onTwitterAction() {
		if (mSocialNetworkManager.getTwitterSocialNetwork().isConnected())
			hideProgress();
		else
			showProgress("Authentificating... Twitter");

		mSocialNetworkManager.getTwitterSocialNetwork().requestLogin();

	}

	@Override
	protected void onLinkedInAction() {
		if (mSocialNetworkManager.getLinkedInSocialNetwork().isConnected())
			hideProgress();
		else
			showProgress("Authentificating... LinkedIn");
		mSocialNetworkManager.getLinkedInSocialNetwork().requestLogin();

	}

	@Override
	protected void onFacebookAction() {
		// if (mSocialNetworkManager.getFacebookSocialNetwork().isConnected())
		// mSocialNetworkManager.getFacebookSocialNetwork().logout();

		mSocialNetworkManager.getFacebookSocialNetwork().requestLogin();

	}

	@Override
	protected void onGooglePlusAction() {
		if (mSocialNetworkManager.getGooglePlusSocialNetwork().isConnected())
			hideProgress();
		else {
			showProgress("Authentificating... Google plus");
		}
		mSocialNetworkManager.getGooglePlusSocialNetwork().requestLogin();

	}

	@Override
	public void onLoginSuccess(int socialNetworkID) {
		// let's reset buttons, we need to disable buttons
		onSocialNetworkManagerInitialized();

		hideProgress();
		handleSuccess("onLoginSuccess", "Have Fun and start beFriending .");
	}

	@Override
	public void onSocialNetworkManagerInitialized() {
		super.onSocialNetworkManagerInitialized();

		for (SocialNetwork socialNetwork : mSocialNetworkManager
				.getInitializedSocialNetworks()) {
			socialNetwork.setOnLoginCompleteListener(this);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		/**
		 * This is required only if you are using Google Plus, the issue is that
		 * there SDK require Activity to launch Auth, so library can't receive
		 * onActivityResult in fragment
		 */

		Fragment fragment = getChildFragmentManager().findFragmentByTag(
				BaseDemoFragment.SOCIAL_NETWORK_TAG);
		if (fragment != null) {
			fragment.onActivityResult(requestCode, resultCode, data);
		}

	}

	@Override
	public void onLogOutSuccess(int socialNetworkID) {
		super.onSocialNetworkManagerInitialized();
	}

}
