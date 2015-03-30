package com.amine.befriender.fragment.demo;

import java.lang.reflect.Constructor;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.amine.befriender.APIDemosApplication;
import com.amine.befriender.fragment.base.BaseDemoFragment;
import com.befriender.apidemos.R;
import com.befriender.lib.impl.FacebookSocialNetwork;
import com.befriender.lib.impl.LinkedInSocialNetwork;
import com.befriender.lib.impl.TwitterSocialNetwork;
import com.befriender.lib.listener.OnCheckIsFriendCompleteListener;
import com.befriender.lib.listener.OnRequestAddFriendCompleteListener;

public class AddFriendFragment extends BaseDemoFragment {

	public static AddFriendFragment newInstance() {
		return new AddFriendFragment();

	}


	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mTwitterButton.setText("Follow ");
		mLinkedInButton.setText("Send invite ");
		mFacebookButton.setText("Not yet supported ");
		mGooglePlusButton.setText("Not yet supported ");
		menuOverlay.setVisibility(View.GONE);

	}

	@Override
	protected void onTwitterAction() {
		if (!checkIsLoginned(TwitterSocialNetwork.ID))
			return;

		showProgress("Following ");
		mSocialNetworkManager.getTwitterSocialNetwork().requestAddFriend(
				APIDemosApplication.USER_ID_TWITTER,
				new DemoTwitterOnRequestAddFriendCompleteListener());
	}

	@Override
	protected void onLinkedInAction() {
		if (!checkIsLoginned(LinkedInSocialNetwork.ID))
			return;

		showProgress("Following ");
		mSocialNetworkManager.getLinkedInSocialNetwork().requestAddFriend(
				APIDemosApplication.USER_ID_LINKED_IN,
				new DemoLinkedInOnRequestAddFriendCompleteListener());
	}

	@Override
	protected void onFacebookAction() {
		if (!checkIsLoginned(FacebookSocialNetwork.ID))
			return;
		sendRequestDialog();

	}

	@Override
	protected void onGooglePlusAction() {

		throw new IllegalStateException("Unsupported");
	}

	private class DemoTwitterOnRequestAddFriendCompleteListener implements
			OnRequestAddFriendCompleteListener {
		@Override
		public void onRequestAddFriendComplete(int socialNetworkID,
				String userID) {
			hideProgress();

			handleSuccess("Add friend", "Successfully following");

		}

		@Override
		public void onError(int socialNetworkID, String requestID,
				String errorMessage, Object data) {
			hideProgress();
			handleError(errorMessage);
		}
	}

	private class DemoLinkedInOnRequestAddFriendCompleteListener implements
			OnRequestAddFriendCompleteListener {
		@Override
		public void onRequestAddFriendComplete(int socialNetworkID,
				String userID) {
			hideProgress();

			handleSuccess("Add friend", "Invite was successfully sent");
		}

		@Override
		public void onError(int socialNetworkID, String requestID,
				String errorMessage, Object data) {
			hideProgress();
			handleError(errorMessage);
		}
	}

	private class DemoTwitterOnCheckIsFriendCompleteListener implements
			OnCheckIsFriendCompleteListener {

		@Override
		public void onCheckIsFriendComplete(int socialNetworkID, String userID,
				boolean isFriend) {
			hideProgress();

			if (isFriend) {
				handleSuccess("Is Friend?", "You follow User X!");
				mTwitterButton.setText("already Following ");
				mTwitterButton.setClickable(false);

			} else {
				handleSuccess("Is Friend?", "You don't follow User X!");
			}

		}

		@Override
		public void onError(int socialNetworkID, String requestID,
				String errorMessage, Object data) {
			hideProgress();
			handleError(errorMessage);
		}
	}

	private void sendRequestDialog() {
		String requestUri = "https://www.facebook.com/dialog/friends/?id="
				+ 1038599438 + "&app_id="
				+ getString(R.string.facebook_application_id)
				+ "&redirect_uri=http://www.facebook.com";
		WebView webView = new WebView(this.getActivity());
		webView.setFocusable(true);
		webView.setFocusableInTouchMode(true);
		webView.requestFocus(View.FOCUS_DOWN);
		webView.setOnTouchListener(new View.OnTouchListener() {
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_UP:
					if (!v.hasFocus()) {
						v.requestFocus();
					}
					break;
				}
				return false;
			}
		});

		webView.getSettings().setUserAgentString(
				getDefaultUserAgentString(getActivity()));
		webView.loadUrl(requestUri);

		AlertDialog.Builder dialog = new AlertDialog.Builder(this.getActivity());
		dialog.setView(webView);
		dialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();
			}
		});
		dialog.show();
	}

	public static String getDefaultUserAgentString(Context context) {
		if (Build.VERSION.SDK_INT >= 17) {
			return NewApiWrapper.getDefaultUserAgent(context);
		}

		try {
			Constructor<WebSettings> constructor = WebSettings.class
					.getDeclaredConstructor(Context.class, WebView.class);
			constructor.setAccessible(true);
			try {
				WebSettings settings = constructor.newInstance(context, null);
				return settings.getUserAgentString();
			} finally {
				constructor.setAccessible(false);
			}
		} catch (Exception e) {
			return new WebView(context).getSettings().getUserAgentString();
		}
	}

	@TargetApi(17)
	static class NewApiWrapper {
		static String getDefaultUserAgent(Context context) {
			return WebSettings.getDefaultUserAgent(context);
		}
	}
}
