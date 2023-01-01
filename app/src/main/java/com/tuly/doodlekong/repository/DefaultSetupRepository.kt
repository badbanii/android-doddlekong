package com.tuly.doodlekong.repository

import android.content.Context
import com.tuly.doodlekong.data.remote.api.SetupApi
import com.tuly.doodlekong.data.remote.ws.Room
import com.tuly.doodlekong.util.Resource
import com.tuly.doodlekong.util.checkForInternetConnection
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DefaultSetupRepository @Inject constructor(
    private val context: Context,
    private val setupApi: SetupApi,
) : SetupRepository {

    override suspend fun createRoom(room: Room): Resource<Unit> {
        if (!context.checkForInternetConnection()) {
            return Resource.Error("Internet Error")
        }
        val response = try {
            setupApi.createRoom(room)
        } catch (e: HttpException) {
            return Resource.Error("HTTP Error")
        } catch (e: IOException) {
            return Resource.Error("Check internet server!")
        }
        return if (response.isSuccessful && response.body()?.successful == true) {
            Resource.Success(Unit)
        } else if (response.body()?.successful == false) {
            Resource.Error(response.body()!!.message!!)
        } else {
            Resource.Error("Error unknown")
        }
    }

    override suspend fun getRooms(searchQuery: String): Resource<List<Room>> {
        if (!context.checkForInternetConnection()) {
            return Resource.Error("Internet Error")
        }
        val response = try {
            setupApi.getRooms(searchQuery)
        } catch (e: HttpException) {
            return Resource.Error("HTTP Error")
        } catch (e: IOException) {
            return Resource.Error("Check internet server!")
        }
        return if (response.isSuccessful && response.body() != null) {
            Resource.Success(response.body()!!)
        } else {
            Resource.Error("Error unknown")
        }
    }

    override suspend fun joinRoom(username: String, roomName: String): Resource<Unit> {
        if (!context.checkForInternetConnection()) {
            return Resource.Error("Internet Error")
        }
        val response = try {
            setupApi.joinRoom(username, roomName)
        } catch (e: HttpException) {
            return Resource.Error("HTTP Error")
        } catch (e: IOException) {
            return Resource.Error("Check internet server!")
        }
        return if (response.isSuccessful && response.body()?.successful == true) {
            Resource.Success(Unit)
        } else if (response.body()?.successful == false) {
            Resource.Error(response.body()!!.message!!)
        } else {
            Resource.Error("Error unknown")
        }
    }

}