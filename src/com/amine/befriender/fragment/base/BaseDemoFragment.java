package com.amine.befriender.fragment.base;

import static com.amine.befriender.APIDemosApplication.TAG;
import lt.lemonlabs.android.expandablebuttonmenu.ExpandableButtonMenu;
import lt.lemonlabs.android.expandablebuttonmenu.ExpandableMenuOverlay;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.amine.befriender.activity.MainActivity;
import com.amine.befriender.fragment.demo.AddFriendFragment;
import com.amine.befriender.fragment.dialog.AlertDialogFragment;
import com.amine.befriender.fragment.dialog.ProgressDialogFragment;
import com.befriender.apidemos.R;
import com.befriender.lib.SocialNetwork;
import com.befriender.lib.SocialNetworkManager;

public abstract class BaseDemoFragment extends Fragment implements
		SocialNetworkManager.OnInitializationCompleteListener,
		View.OnClickListener {

	public static final String SOCIAL_NETWORK_TAG = "BaseLoginDemoFragment.SOCIAL_NETWORK_TAG";
	private static final String PROGRESS_DIALOG_TAG = "BaseDemoFragment.PROGRESS_DIALOG_TAG";
	protected SocialNetworkManager mSocialNetworkManager;
	protected boolean mSocialNetworkManagerInitialized = false;

	protected Button mTwitterButton;
	protected Button mLinkedInButton;
	protected Button mFacebookButton;
	protected Button mGooglePlusButton;
	protected Button mMenuButton;
	protected ExpandableMenuOverlay menuOverlay;

	protected abstract void onTwitterAction();

	protected abstract void onLinkedInAction();

	protected abstract void onFacebookAction();

	protected abstract void onGooglePlusAction();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_base_buttons, container,
				false);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);

		mSocialNetworkManager = (SocialNetworkManager) getFragmentManager()
				.findFragmentByTag(SOCIAL_NETWORK_TAG);

		if (mSocialNetworkManager == null) {
			mSocialNetworkManager = SocialNetworkManager.Builder
					.from(getActivity())
					.twitter("NmygyYATAy486rBO14OgNTPhy",
							"9EPuGMJq1yDMcYF4c6Ce7Un4Huoh9nLXARVZDUoYmVzHO9kD7m")
					.linkedIn("77ql96y7bh86qq", "dMlftOTAh7fanUhx",
							"r_basicprofile+rw_nus+r_network+w_messages")
					.facebook().googlePlus().build();
			getFragmentManager().beginTransaction()
					.add(mSocialNetworkManager, SOCIAL_NETWORK_TAG).commit();

			mSocialNetworkManager.setOnInitializationCompleteListener(this);
		} else {
			// we need to setup buttons correctly, mSocialNetworkManager isn't
			// null, so
			// we are sure that it was initialized
			mSocialNetworkManagerInitialized = true;
		}

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mTwitterButton = (Button) view.findViewById(R.id.twitter_button);
		mLinkedInButton = (Button) view.findViewById(R.id.linkedin_button);
		mFacebookButton = (Button) view.findViewById(R.id.facebook_button);
		mGooglePlusButton = (Button) view.findViewById(R.id.google_plus_button);

		mTwitterButton.setOnClickListener(this);
		mLinkedInButton.setOnClickListener(this);
		mFacebookButton.setOnClickListener(this);
		mGooglePlusButton.setOnClickListener(this);

		if (mSocialNetworkManagerInitialized) {
			onSocialNetworkManagerInitialized();
		}

		menuOverlay = (ExpandableMenuOverlay) view
				.findViewById(R.id.button_menu);
		menuOverlay
				.setOnMenuButtonClickListener(new ExpandableButtonMenu.OnMenuButtonClick() {
					@Override
					public void onClick(ExpandableButtonMenu.MenuButton action) {
						switch (action) {
						case MID:
							Toast.makeText(getActivity(),
									"Mid pressed and dismissing...",
									Toast.LENGTH_SHORT).show();
							menuOverlay.getButtonMenu().toggle();
							break;
						case LEFT:

							getMainActivity().getSupportActionBar().setTitle(
									"beFriend");
							getMainActivity().getSupportActionBar()
									.setSubtitle("through...");

							getMainActivity()
									.getSupportFragmentManager()
									.beginTransaction()
									.replace(R.id.root_container,
											AddFriendFragment.newInstance())
									.addToBackStack(null).commit();
							getMainActivity().getSupportActionBar()
									.setDisplayHomeAsUpEnabled(true);

							Toast.makeText(getActivity(), "Left pressed",
									Toast.LENGTH_SHORT).show();
							menuOverlay.getButtonMenu().toggle();

							break;
						case RIGHT:
							Toast.makeText(getActivity(), "Right pressed",
									Toast.LENGTH_SHORT).show();
							menuOverlay.getButtonMenu().toggle();

							break;
						}
					}
				});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		onRequestCancel();
	}

	protected void showProgress(String text) {
		ProgressDialogFragment progressDialogFragment = ProgressDialogFragment
				.newInstance(text);
		progressDialogFragment.setTargetFragment(this, 0);
		progressDialogFragment.show(getFragmentManager(), PROGRESS_DIALOG_TAG);
	}

	protected void hideProgress() {
		Fragment fragment = getFragmentManager().findFragmentByTag(
				PROGRESS_DIALOG_TAG);

		if (fragment != null) {
			getFragmentManager().beginTransaction().remove(fragment).commit();
		}
	}

	protected void handleError(String text) {
		AlertDialogFragment.newInstance("Error", text).show(
				getFragmentManager(), null);
	}

	protected void handleSuccess(String title, String message) {
		AlertDialogFragment.newInstance(title, message).show(
				getFragmentManager(), null);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.twitter_button:
			onTwitterAction();
			break;
		case R.id.linkedin_button:
			onLinkedInAction();
			break;
		case R.id.facebook_button:
			onFacebookAction();
			break;
		case R.id.google_plus_button:
			onGooglePlusAction();
			break;
		default:
			throw new IllegalArgumentException("Can't find click handler for: "
					+ v);
		}
	}

	@Override
	public void onSocialNetworkManagerInitialized() {

	}

	public boolean checkIsLoginned(int socialNetworkID) {
		if (mSocialNetworkManager.getSocialNetwork(socialNetworkID)
				.isConnected()) {
			return true;
		}

		AlertDialogFragment.newInstance("Request Login",
				"Please login to your account.").show(getFragmentManager(),
				null);

		return false;
	}

	public boolean beFriendLoginned(int socialNetworkID) {
		if (mSocialNetworkManager.getSocialNetwork(socialNetworkID)
				.isConnected()) {
			return true;
		}

		return false;
	}

	public void onRequestCancel() {
		Log.d(TAG, "BaseDemoFragment.onRequestCancel");

		for (SocialNetwork socialNetwork : mSocialNetworkManager
				.getInitializedSocialNetworks()) {
			socialNetwork.cancelAll();
		}
	}

	public MainActivity getMainActivity() {
		return (MainActivity) getActivity();
	}
}
