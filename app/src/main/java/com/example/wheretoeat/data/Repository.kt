package com.example.wheretoeat.data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

// the instance survives a configuration change (ex. rotating the screen)
@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {
    val remote = remoteDataSource
    val local = localDataSource
}