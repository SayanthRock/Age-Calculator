package com.example

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "calculation_history")
data class AgeCalculationHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dob: Long,
    val today: Long,
    val timestamp: Long = System.currentTimeMillis()
)

@Dao
interface AgeCalculationDao {
    @Query("SELECT * FROM calculation_history ORDER BY timestamp DESC LIMIT 5")
    fun getRecentCalculations(): Flow<List<AgeCalculationHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCalculation(history: AgeCalculationHistory)
}

@Database(entities = [AgeCalculationHistory::class], version = 1, exportSchema = false)
abstract class AgeDatabase : RoomDatabase() {
    abstract fun ageDao(): AgeCalculationDao

    companion object {
        @Volatile
        private var INSTANCE: AgeDatabase? = null

        fun getDatabase(context: Context): AgeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AgeDatabase::class.java,
                    "age_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
