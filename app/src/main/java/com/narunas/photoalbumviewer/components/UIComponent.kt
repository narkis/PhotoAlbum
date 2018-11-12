package com.narunas.photoalbumviewer.components

import com.narunas.photoalbumviewer.MainActivity
import com.narunas.photoalbumviewer.modules.SingleFragmentUIModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
    SingleFragmentUIModule::class
))

interface UIComponent {

    fun inject(activity: MainActivity)

}