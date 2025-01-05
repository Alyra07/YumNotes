package at.mirjam.yumnotes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Room Database for storing Recipe & Profile data
@Database(entities = [Recipe::class, Profile::class], version = 2, exportSchema = false)
abstract class YumNotesDatabase : RoomDatabase() {
    // abstract methods to access DAOs for each entity
    abstract fun recipeDao(): RecipeDao
    abstract fun profileDao(): ProfileDao

    companion object {
        @Volatile
        private var INSTANCE: YumNotesDatabase? = null

        fun getDatabase(context: Context): YumNotesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    YumNotesDatabase::class.java,
                    "recipe_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}