/*
 * Copyright 2014 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.apps.muzei

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.content.edit
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.nice.seeyou.R
import com.nice.seeyou.databinding.TutorialFragmentBinding

class TutorialFragment : Fragment(R.layout.tutorial_fragment) {

    companion object {
        const val PREF_SEEN_TUTORIAL = "seen_tutorial"
    }

    private val runningAnimators = mutableListOf<AnimatorSet>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = TutorialFragmentBinding.bind(view).content
        binding.iconAffordance.setOnClickListener {
            Firebase.analytics.logEvent(FirebaseAnalytics.Event.TUTORIAL_COMPLETE, null)
            PreferenceManager.getDefaultSharedPreferences(context).edit {
                putBoolean(PREF_SEEN_TUTORIAL, true)
            }
        }

        if (savedInstanceState == null) {
            val animateDistance = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100f,
                    resources.displayMetrics)
            val mainTextView = binding.mainText.apply {
                alpha = 0f
                translationY = -animateDistance / 5
            }
            val subTextView = binding.subText.apply {
                alpha = 0f
                translationY = -animateDistance / 5
            }
            val affordanceView = binding.iconAffordance.apply {
                alpha = 0f
                translationY = animateDistance
            }
            val iconTextView = binding.iconText.apply {
                alpha = 0f
                translationY = animateDistance
            }
            runningAnimators.add(AnimatorSet().apply {
                startDelay = 500
                duration = 250
                playTogether(
                        ObjectAnimator.ofFloat(mainTextView, View.ALPHA, 1f),
                        ObjectAnimator.ofFloat(subTextView, View.ALPHA, 1f))
                doOnEnd { runningAnimators.remove(this) }
                start()
            })
            runningAnimators.add(AnimatorSet().apply {
                startDelay = 2000
                duration = 500
                // Bug in older versions where set.setInterpolator didn't work
                val interpolator = OvershootInterpolator()
                val a1 = ObjectAnimator.ofFloat(affordanceView, View.TRANSLATION_Y, 0f)
                val a2 = ObjectAnimator.ofFloat(iconTextView, View.TRANSLATION_Y, 0f)
                val a3 = ObjectAnimator.ofFloat(mainTextView, View.TRANSLATION_Y, 0f)
                val a4 = ObjectAnimator.ofFloat(subTextView, View.TRANSLATION_Y, 0f)
                a1.interpolator = interpolator
                a2.interpolator = interpolator
                a3.interpolator = interpolator
                a4.interpolator = interpolator
                playTogether(
                        ObjectAnimator.ofFloat(affordanceView, View.ALPHA, 1f),
                        ObjectAnimator.ofFloat(iconTextView, View.ALPHA, 1f),
                        a1, a2, a3, a4)
                doOnEnd {
                    if (isAdded && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        val avd = ResourcesCompat.getDrawable(resources,
                                R.drawable.avd_tutorial_icon_emanate,
                                context?.theme) as AnimatedVectorDrawable
                        binding.iconEmanate.setImageDrawable(avd)
                        avd.start()
                    }
                    runningAnimators.remove(this)
                }
                start()
            })
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val avd = ResourcesCompat.getDrawable(resources,
                    R.drawable.avd_tutorial_icon_emanate,
                    context?.theme) as AnimatedVectorDrawable
            binding.iconEmanate.setImageDrawable(avd)
            avd.start()
        }
    }

    override fun onDestroyView() {
        runningAnimators.forEach {
            it.removeAllListeners()
            it.cancel()
        }
        super.onDestroyView()
    }
}
