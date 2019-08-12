package com.materiotech.atc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.materiotech.atc.R;
import com.materiotech.atc.fragment.YouTubeVideoFragment;

public class HomeActivity extends AppCompatActivity {

    public ActionBar actionBar;
    private BottomNavigationView bottomNavigationView;
    private static final int REQUEST_INVITE = 101;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_botton_bar);

        actionBar = getSupportActionBar();
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        actionBar.setTitle("Atlanta Tamil Church");

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.action_share:
                    actionBar.setTitle("Share With Friends");
                    inviteToFriends();
                    return true;
                case R.id.action_game:
                    actionBar.setTitle("ATC Videos");
                    fragment = new YouTubeVideoFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.action_account:
                    actionBar.setTitle("My Account");
                    return true;

            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void inviteToFriends(){
       Intent intent = new AppInviteInvitation
               .IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                /*.setDeepLink(Uri.parse(getString(R.string.invitation_deep_link)))
                .setCustomImage(Uri.parse(getString(R.string.invitation_custom_image)))*/
                .setCallToActionText(getString(R.string.invitation_cta))
                .build();
        startActivityForResult(intent, REQUEST_INVITE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {

            }
        }
    }
}
