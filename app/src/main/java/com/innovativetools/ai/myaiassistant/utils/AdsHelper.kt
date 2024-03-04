package com.innovativetools.ai.myaiassistant.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.MutableState
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.innovativetools.ai.myaiassistant.R


var mInterstitialAd: InterstitialAd? = null
var rewardedAd: RewardedAd? = null
var secondRewardedAd: RewardedAd? = null

fun loadTwoRewardedAds(context: Context) {
    RewardedAd.load(context, Constants.REWARDED_AD_UNIT_ID,
        AdRequest.Builder().build(), object : RewardedAdLoadCallback() {
            override fun onAdLoaded(ad: RewardedAd) {
                Log.d("TAG", "First ad is loaded successfully.")
                rewardedAd = ad
                // Load the second ad
                RewardedAd.load(context, Constants.SECOND_REWARDED_AD_UNIT_ID,
                    AdRequest.Builder().build(), object : RewardedAdLoadCallback() {
                        override fun onAdLoaded(ad: RewardedAd) {
                            Log.d("TAG", "Second ad is loaded successfully.")
                            secondRewardedAd = ad
                        }
                        override fun onAdFailedToLoad(adError: LoadAdError) {
                            adError.toString().let { Log.d("TAG", it) }
                            secondRewardedAd = null
//                            Toast.makeText(context, R.string.second_ad_is_loading, Toast.LENGTH_SHORT).show()
                        }
                    })
            }
            override fun onAdFailedToLoad(adError: LoadAdError) {
                adError.toString().let { Log.d("TAG", it) }
                rewardedAd = null
//                Toast.makeText(context, R.string.ad_is_loading, Toast.LENGTH_SHORT).show()
            }
        })
}

fun showTwoRewardedAds(context: Context, showDialog: MutableState<Boolean>, onTwoAdsWatched: () -> Unit) {
    val activity = context.findActivity()

    if (rewardedAd != null && secondRewardedAd != null && activity != null) {
        rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdFailedToShowFullScreenContent(e: AdError) {
                rewardedAd = null
            }

            override fun onAdDismissedFullScreenContent() {
                rewardedAd = null
                // Show the loading dialog
                showDialog.value = true
                // Delay for a few seconds
                Handler(Looper.getMainLooper()).postDelayed({
                    // Show the second ad
                    secondRewardedAd?.show(activity) {
                        onTwoAdsWatched()
                        // Hide the loading dialog
                        showDialog.value = false
                    }
                }, 700)
            }
        }
        rewardedAd?.show(activity) {
            // Do nothing here, wait for the second ad to finish
        }
    }
}

//fun showTwoRewardedAds(context: Context, onTwoAdsWatched: () -> Unit) {
//    val activity = context.findActivity()
//    if (rewardedAd != null && secondRewardedAd != null && activity != null) {
//        rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
//            override fun onAdFailedToShowFullScreenContent(e: AdError) {
//                rewardedAd = null
//            }
//
//            override fun onAdDismissedFullScreenContent() {
//                rewardedAd = null
//                // Show the second ad
//                secondRewardedAd?.show(activity) {
//                    onTwoAdsWatched()
//                }
//            }
//        }
//        rewardedAd?.show(activity) {
//            // Do nothing here, wait for the second ad to finish
//        }
//    }
//}


fun loadRewarded(context: Context) {
    RewardedAd.load(context, Constants.REWARDED_AD_UNIT_ID,
        AdRequest.Builder().build(), object : RewardedAdLoadCallback() {
            override fun onAdLoaded(ad: RewardedAd) {
                Log.d("TAG", "Ad is loaded successfully.")
                rewardedAd = ad
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                adError.toString().let { Log.d("TAG", it) }
                rewardedAd = null
//                Toast.makeText(context, R.string.ad_is_loading, Toast.LENGTH_SHORT).show()
            }
        })
}
fun showRewarded(context: Context, onRewarded: () -> Unit) {
    val activity = context.findActivity()

    if (rewardedAd != null && activity != null) {
        rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdFailedToShowFullScreenContent(e: AdError) {
                rewardedAd = null
//                loadRewarded(context)
            }

            override fun onAdDismissedFullScreenContent() {
                rewardedAd = null
                loadRewarded(context)
            }
        }
        rewardedAd?.show(activity) {
            onRewarded()
        }
    }
}
fun loadInterstitial(context: Context){
    InterstitialAd.load(
        context,
        Constants.INTERTITIAL_ADUNIT,
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
//                Toast.makeText(context, R.string.ad_is_loading, Toast.LENGTH_SHORT).show()
            }
            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
            }
        }
    )
}
fun showInterstitial(context: Context, onAdDismissed: () -> Unit, onAdFailed: () -> Unit) {
    val activity = context.findActivity()
    if (mInterstitialAd != null && activity != null) {
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdFailedToShowFullScreenContent(e: AdError) {
                mInterstitialAd = null
                onAdFailed()
            }
            override fun onAdDismissedFullScreenContent() {
                mInterstitialAd = null
                loadInterstitial(context)
                onAdDismissed()
            }
        }
        mInterstitialAd?.show(activity)
    }
}
fun removeInterstitial() {
    mInterstitialAd?.fullScreenContentCallback = null
    mInterstitialAd = null
}
