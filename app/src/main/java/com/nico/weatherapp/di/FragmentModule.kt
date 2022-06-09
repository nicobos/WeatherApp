package com.nico.weatherapp.di

import androidx.fragment.app.Fragment
import com.nico.weatherapp.ui.dialogs.DialogManager
import com.nico.weatherapp.ui.dialogs.IDialogManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class FragmentModule {

    @Provides
    fun dialogManager(fragment: Fragment): IDialogManager = DialogManager(
        context = fragment.requireContext()
    )

}