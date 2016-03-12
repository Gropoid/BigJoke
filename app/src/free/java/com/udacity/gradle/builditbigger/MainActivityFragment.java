package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.gropoid.jokelibrary.JokeDisplayActivity;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String TAG = MainActivityFragment.class.getSimpleName();

    InterstitialAd interstitialAd;

    @Bind(R.id.instructions_text_view)
    TextView instructionsView;

    @Bind(R.id.joke_button)
    Button jokeButton;

    @Bind(R.id.adView)
    AdView adView;

    @Bind(R.id.progressSpinner)
    ProgressBar progressIndicator;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, root);
        loadAds(root);
        return root;
    }

    private void loadAds(View root) {
        interstitialAd = new InterstitialAd(getContext());
        interstitialAd.setAdUnitId(getString(R.string.inter_ad_unit_id));
        AdView adView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView.loadAd(adRequest);
        interstitialAd.loadAd(adRequest);
    }

    private void showInterstitialAdThenJoke(final String jokeString) {
        if (interstitialAd.isLoaded()) {
            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    requestNewInterstitial();
                    setLoadingState(false);
                    JokeDisplayActivity.startWithJoke(getContext(), jokeString);
                }
            });
            interstitialAd.show();
        } else {
            setLoadingState(false);
            JokeDisplayActivity.startWithJoke(getContext(), jokeString);
        }
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        interstitialAd.loadAd(adRequest);
    }

    public void tellJoke(View view){
        FetchJokeTask task = new FetchJokeTask() {
            @Override
            protected void onPostExecute(String jokeString) {
                if (jokeString != null) {
                    showInterstitialAdThenJoke(jokeString);
                } else {
                    Toast.makeText(getContext(), "Could not retrieve joke :o(", Toast.LENGTH_SHORT).show();
                    setLoadingState(false);
                }
            }
        };
        setLoadingState(true);
        task.execute(getContext());
    }

    private void setLoadingState(boolean loading) {
        if (loading) {
            instructionsView.setVisibility(View.GONE);
            jokeButton.setVisibility(View.GONE);
            adView.setVisibility(View.GONE);
            progressIndicator.setVisibility(View.VISIBLE);
        } else {
            instructionsView.setVisibility(View.VISIBLE);
            jokeButton.setVisibility(View.VISIBLE);
            adView.setVisibility(View.VISIBLE);
            progressIndicator.setVisibility(View.GONE);
        }
    }
}
