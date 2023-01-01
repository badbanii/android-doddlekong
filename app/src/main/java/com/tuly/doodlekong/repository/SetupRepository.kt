package com.tuly.doodlekong.repository

import com.tuly.doodlekong.data.remote.ws.Room
import com.tuly.doodlekong.util.Resource

interface SetupRepository {
    suspend fun createRoom(room:Room):Resource<Unit>
    suspend fun getRooms(searchQuery:String):Resource<List<Room>>
    suspend fun joinRoom(username:String,roomName:String):Resource<Unit>

}