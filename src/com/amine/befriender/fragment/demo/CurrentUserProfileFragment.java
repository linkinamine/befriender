package com.amine.befriender.fragment.demo;

import android.os.Bundle;
import android.view.View;

import com.amine.befriender.fragment.base.BaseDemoFragment;
import com.befriender.apidemos.R;
import com.befriender.lib.SocialPerson;
import com.befriender.lib.impl.FacebookSocialNetwork;
import com.befriender.lib.impl.GooglePlusSocialNetwork;
import com.befriender.lib.impl.LinkedInSocialNetwork;
import com.befriender.lib.impl.TwitterSocialNetwork;
import com.befriender.lib.listener.OnRequestSocialPersonCompleteListener;

public class CurrentUserProfileFragment extends BaseDemoFragment implements View.OnClickListener {

    public static CurrentUserProfileFragment newInstance() {
        return new CurrentUserProfileFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTwitterButton.setText("Load Twitter Profile");
        mLinkedInButton.setText("Load LinkedIn Profile");
        mFacebookButton.setText("Load Facebook Profile");
        mGooglePlusButton.setText("Load Google Plus Profile");
    }

    @Override
    protected void onTwitterAction() {
        if (!checkIsLoginned(TwitterSocialNetwork.ID)) return;

        showProgress("Loading profile");
        mSocialNetworkManager.getTwitterSocialNetwork()
                .requestCurrentPerson(new DemoOnRequestSocialPersonCompleteListener());

    }

    @Override
    protected void onLinkedInAction() {
        if (!checkIsLoginned(LinkedInSocialNetwork.ID)) return;

        showProgress("Loading profile");
        mSocialNetworkManager.getLinkedInSocialNetwork()
                .requestCurrentPerson(new DemoOnRequestSocialPersonCompleteListener());
    }

    @Override
    protected void onFacebookAction() {
        if (!checkIsLoginned(FacebookSocialNetwork.ID)) return;

        showProgress("Loading profile");
        mSocialNetworkManager.getFacebookSocialNetwork()
                .requestCurrentPerson(new DemoOnRequestSocialPersonCompleteListener());
    }

    @Override
    protected void onGooglePlusAction() {
        if (!checkIsLoginned(GooglePlusSocialNetwork.ID)) return;

        mSocialNetworkManager.getGooglePlusSocialNetwork()
                .requestCurrentPerson(new DemoOnRequestSocialPersonCompleteListener());
    }

    private class DemoOnRequestSocialPersonCompleteListener implements OnRequestSocialPersonCompleteListener {
        @Override
        public void onRequestSocialPersonSuccess(int socialNetworkID, SocialPerson socialPerson) {
            hideProgress();

            getFragmentManager().beginTransaction()
                    .replace(R.id.root_container, ShowProfileFragment.newInstance(socialPerson))
                    .addToBackStack(null)
                    .commit();
        }

        @Override
        public void onError(int socialNetworkID, String requestID, String errorMessage, Object data) {
            hideProgress();
            handleError(errorMessage);
        }
    }
}
