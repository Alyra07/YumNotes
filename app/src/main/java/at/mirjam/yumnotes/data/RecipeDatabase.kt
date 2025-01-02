package at.mirjam.yumnotes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Recipe::class], version = 3, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null
        // Get the room database instance
        fun getDatabase(context: Context): RecipeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
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

    // Callback to insert sample recipes when the database is created
    // I did it this way to avoid spamming the database with sample data :)
    private class RecipeDatabaseCallback(
        private val context: Context
    ) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            // Insert sample recipes
            CoroutineScope(Dispatchers.IO).launch {
                val sampleRecipes = listOf(
                    Recipe(
                        name = "Spaghetti Bolognese",
                        ingredients = "Spaghetti, ground beef, tomato sauce, onion, garlic",
                        instructions = "Cook spaghetti. Prepare sauce with ground beef and tomato sauce.",
                        collectionTags = "Dinner",
                        selectedTags = "Italian, Quick",
                        imageUri = null
                    ),
                    Recipe(
                        name = "Pancakes",
                        ingredients = "Flour, milk, eggs, sugar, baking powder",
                        instructions = "Mix ingredients. Cook on a hot pan until golden brown.",
                        collectionTags = "Breakfast, Dessert",
                        selectedTags = "Sweet, Quick",
                        imageUri = null
                    )
                )

                val database = getDatabase(context)
                sampleRecipes.forEach {
                    database.recipeDao().insertRecipe(it)
                }
            }
        }
    }
}