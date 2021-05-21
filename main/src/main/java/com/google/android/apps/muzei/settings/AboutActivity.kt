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

package com.google.android.apps.muzei.settings

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.ViewPropertyAnimator
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.text.HtmlCompat
import androidx.fragment.app.commit
import com.google.android.apps.muzei.render.MuzeiRendererFragment
import com.google.android.apps.muzei.util.AnimatedMuzeiLogoFragment
import com.nice.seeyou.BuildConfig
import com.nice.seeyou.R
import com.nice.seeyou.databinding.AboutActivityBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: AboutActivityBinding
    private var animator: ViewPropertyAnimator? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AboutActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)

        binding.toolbar.setNavigationOnClickListener { onNavigateUp() }

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.demo_view_container,
                        MuzeiRendererFragment.createInstance(true))
            }
        }

        // Build the about body view and append the link to see OSS licenses
        binding.content.appVersion.apply {
            text = getString(R.string.about_version_template, BuildConfig.VERSION_NAME)
        }

        binding.content.body.apply {
            text = HtmlCompat.fromHtml(getString(R.string.about_body), 0)
            movementMethod = LinkMovementMethod()
        }

        binding.content.androidExperimentLink.setOnClickListener {
            val cti = CustomTabsIntent.Builder()
                    .setShowTitle(true)
                    .setDefaultColorSchemeParams(CustomTabColorSchemeParams.Builder()
                            .setToolbarColor(ContextCompat.getColor(this, R.color.theme_primary))
                            .build())
                    .build()
            try {
                cti.launchUrl(this,
                        "https://www.androidexperiments.com/experiment/muzei".toUri())
            } catch (ignored: ActivityNotFoundException) {
            }
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        val demoContainerView = binding.demoViewContainer.apply {
            alpha = 0f
        }
        animator = demoContainerView.animate()
                .alpha(1f)
                .setStartDelay(250)
                .setDuration(1000)
                .withEndAction {
                    val logoFragment = supportFragmentManager.findFragmentById(R.id.animated_logo_fragment)
                            as? AnimatedMuzeiLogoFragment
                    logoFragment?.start()
                }
    }

    override fun onDestroy() {
        animator?.cancel()
        super.onDestroy()
    }
}
