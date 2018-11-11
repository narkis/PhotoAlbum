package com.narunas.photoalbumviewer.modules

import com.narunas.photoalbumviewer.ui.SingleContainerFragment
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SingleFragmentUIModule {

    @Provides
    @Singleton

    fun provideSingleContainerFragment() : SingleContainerFragment {
        return SingleContainerFragment.getInstance()
    }
}