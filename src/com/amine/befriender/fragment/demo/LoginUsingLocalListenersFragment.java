package com.amine.befriender.fragment.demo;

import com.amine.befriender.fragment.base.BaseLoginDemoFragment;
import com.befriender.lib.SocialNetwork;
import com.befriender.lib.listener.OnLoginCompleteListener;

public class LoginUsingLocalListenersFragment extends BaseLoginDemoFragment {
	public static LoginUsingLocalListenersFragment newInstance() {
		return new LoginUsingLocalListenersFragment();
	}

	@Override
	protected void onTwitterAction() {
		if (mSocialNetworkManager.getTwitterSocialNetwork().isConnected()) {
			mSocialNetworkManager.getTwitterSocialNetwork().logout();
		} else {
			showProgress("Authentificating... Twitter");
			mSocialNetworkManager.getTwitterSocialNetwork().requestLogin(
					new DemoOnLoginCompleteListener());
		}
	}

	@Override
	protected void onLinkedInAction() {
		if (mSocialNetworkManager.getLinkedInSocialNetwork().isConnected()) {
			mSocialNetworkManager.getLinkedInSocialNetwork().logout();
		} else {
			showProgress("Authentificating... Linkedin");
			mSocialNetworkManager.getLinkedInSocialNetwork().requestLogin(
					new DemoOnLoginCompleteListener());
		}
	}

	@Override
	protected void onFacebookAction() {
		if (mSocialNetworkManager.getFacebookSocialNetwork().isConnected()) {
			{
				showProgress("Disconnecting... Facebook");
				mSocialNetworkManager.getFacebookSocialNetwork().logout();
				
			}
		} else {
			mSocialNetworkManager.getFacebookSocialNetwork().requestLogin(
					new DemoOnLoginCompleteListener());
		}
	}

	@Override
	protected void onGooglePlusAction() {
		if (mSocialNetworkManager.getGooglePlusSocialNetwork().isConnected()) {
			mSocialNetworkManager.getGooglePlusSocialNetwork().logout();
		} else {
			showProgress("Authentificating... Google plus");
			mSocialNetworkManager.getGooglePlusSocialNetwork().requestLogin(
					new DemoOnLoginCompleteListener());
		}
	}

	private class DemoOnLoginCompleteListener implements
			OnLoginCompleteListener {
		@Override
		public void onLoginSuccess(int socialNetworkID) {
			// let's reset buttons, we need to disable buttons
			onSocialNetworkManagerInitialized();

			hideProgress();
			handleSuccess("onLoginSuccess", "Now you can try other API Demos.");
		}

		@Override
		public void onError(int socialNetworkID, String requestID,
				String errorMessage, Object data) {
			hideProgress();
			handleError(errorMessage);
		}

		

		@Override
		public void onLogOutSuccess(int socialNetworkID) {
			// TODO Auto-generated method stub
			
		}
	}

}
