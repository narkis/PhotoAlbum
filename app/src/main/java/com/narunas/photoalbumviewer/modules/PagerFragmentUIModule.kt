package com.narunas.photoalbumviewer.modules

import com.narunas.photoalbumviewer.ui.PagerUIFragment
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class PagerFragmentUIModule {

    @Provides
    @Singleton

    fun providePagerUIFragment() : PagerUIFragment{
        return PagerUIFragment.getInstance()
    }
}