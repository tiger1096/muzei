/*
 * Copyright 2017 Google Inc.
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

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.apps.muzei.browse.BrowseProviderFragment
import com.google.android.apps.muzei.settings.EffectsFragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import net.nurik.roman.muzei.R
import net.nurik.roman.muzei.databinding.MainFragmentBinding

/**
 * Fragment which controls the main view of the Muzei app and handles the bottom navigation
 * between various screens.
 */
class MainFragment : Fragment(R.layout.main_fragment), ChooseProviderFragment.Callbacks {

    private val darkStatusBarColor by lazy {
        ContextCompat.getColor(requireContext(), R.color.theme_primary_dark)
    }
    private lateinit var binding: MainFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Set up the container for the child fragments
        binding = MainFragmentBinding.bind(view)
        val navHostFragment = childFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.main_navigation)
        if (requireActivity().isPreviewMode) {
            // Make the Effects screen the start destination when
            // coming from the Settings button on the Live Wallpaper picker
            navGraph.startDestination = R.id.main_effects
        }
        navController.graph = navGraph

        // Set up the bottom nav
        binding.bottomNav.setupWithNavController(navController)
        // Set up an OnDestinationChangedListener for the status bar color
        navController.addOnDestinationChangedListener { _, _, args ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                requireActivity().window.statusBarColor =
                        if (args?.getBoolean("useDarkStatusBar") == true) {
                            darkStatusBarColor
                        } else {
                            Color.TRANSPARENT
                        }
            }
        }
        // Set up an OnDestinationChangedListener for analytics
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.main_history -> {
                    Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
                        param(FirebaseAnalytics.Param.SCREEN_NAME, "ArtDetail")
                        param(FirebaseAnalytics.Param.SCREEN_CLASS,
                                ArtDetailFragment::class.java.simpleName)
                    }
                }
                R.id.main_choose_provider -> {
                    Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
                        param(FirebaseAnalytics.Param.SCREEN_NAME, "ChooseProvider")
                        param(FirebaseAnalytics.Param.SCREEN_CLASS,
                                ChooseProviderFragment::class.java.simpleName)
                    }
                }
                R.id.browse_provider -> {
                    Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
                        param(FirebaseAnalytics.Param.SCREEN_NAME, "BrowseProvider")
                        param(FirebaseAnalytics.Param.SCREEN_CLASS,
                                BrowseProviderFragment::class.java.simpleName)
                    }
                }
                R.id.main_effects -> {
                    Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
                        param(FirebaseAnalytics.Param.SCREEN_NAME, "Effects")
                        param(FirebaseAnalytics.Param.SCREEN_CLASS,
                                EffectsFragment::class.java.simpleName)
                    }
                }
            }
        }
        binding.bottomNav.setOnNavigationItemReselectedListener { item ->
            if (item.itemId == R.id.main_history) {
                activity?.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            }
        }

        // Send the correct window insets to each view
        ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
            // Ensure the container gets the appropriate insets
            ViewCompat.dispatchApplyWindowInsets(binding.container,
                    WindowInsetsCompat.Builder(insets).setSystemWindowInsets(Insets.of(
                            insets.systemWindowInsetLeft,
                            insets.systemWindowInsetTop,
                            insets.systemWindowInsetRight,
                            0)).build())
            ViewCompat.dispatchApplyWindowInsets(binding.bottomNav,
                    WindowInsetsCompat.Builder(insets).setSystemWindowInsets(Insets.of(
                            insets.systemWindowInsetLeft,
                            0,
                            insets.systemWindowInsetRight,
                            insets.systemWindowInsetBottom)).build())
            insets.consumeSystemWindowInsets().consumeDisplayCutout()
        }

        // Listen for visibility changes to know when to hide our views
        view.setOnSystemUiVisibilityChangeListener { vis ->
            val visible = vis and View.SYSTEM_UI_FLAG_LOW_PROFILE == 0

            binding.bottomNav.visibility = View.VISIBLE
            binding.bottomNav.animate()
                    .alpha(if (visible) 1f else 0f)
                    .setDuration(200)
                    .withEndAction {
                        if (!visible) {
                            binding.bottomNav.visibility = View.GONE
                        }
                        updateNavigationBarColor()
                    }
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        updateNavigationBarColor()
    }

    private fun updateNavigationBarColor() {
        activity?.window?.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val lightNavigationBar = resources.getBoolean(R.bool.light_navigation_bar)
                if (lightNavigationBar) {
                    decorView.systemUiVisibility = decorView.systemUiVisibility or
                            View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                } else {
                    decorView.systemUiVisibility = decorView.systemUiVisibility xor
                            View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                }
            }
        }
    }

    override fun onRequestCloseActivity() {
        binding.bottomNav.selectedItemId = R.id.main_history
    }
}
