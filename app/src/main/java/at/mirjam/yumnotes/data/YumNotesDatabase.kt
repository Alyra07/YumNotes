package at.mirjam.yumnotes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Room Database for storing Recipe & Profile data
@Database(entities = [Recipe::class, Profile::class], version = 4, exportSchema = false)
abstract class YumNotesDatabase : RoomDatabase() {
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
                    .addCallback(RecipeDatabaseCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    // Callback to prepopulate the database with SampleData
    private class RecipeDatabaseCallback(
        private val context: Context
    ) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                val database = getDatabase(context)
                val sampleRecipes = SampleData.getSampleRecipes(context)
                sampleRecipes.forEach { recipe ->
                    database.recipeDao().insertRecipe(recipe)
                }
            }
        }
    }
}