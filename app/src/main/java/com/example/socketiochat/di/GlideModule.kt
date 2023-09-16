package com.example.socketiochat.di

import android.content.Context
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy.ALL
import com.bumptech.glide.load.model.StreamEncoder
import com.bumptech.glide.request.RequestOptions
import com.example.socketiochat.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class GlideModule {

    @Singleton
    @Provides
    fun provideGlideRequestOptions(): RequestOptions {
        return RequestOptions
            .placeholderOf(R.drawable.profile_photo_green)
            .diskCacheStrategy(ALL)
            .error(R.drawable.profile_photo_green)
    }

    @Singleton
    @Provides
    fun provideGlide(
        @ApplicationContext context: Context,
        options: RequestOptions
    ): RequestManager {
        return Glide.with(context).setDefaultRequestOptions(options)
    }

}