package com.example.tp3.database

import androidx.room.*
import com.example.tp3.model.Planning
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanningDao {
    @Insert
    suspend fun insertPlanning(planning: Planning): Long

    @Query("SELECT * FROM plannings WHERE login = :login ORDER BY id DESC LIMIT 1")
    suspend fun getLastPlanningForUser(login: String): Planning?

    @Query("SELECT * FROM plannings WHERE login = :login AND date = :date LIMIT 1")
    suspend fun getPlanningByLoginAndDate(login: String, date: String): Planning?

    @Update
    suspend fun updatePlanning(planning: Planning)


}



