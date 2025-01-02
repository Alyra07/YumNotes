package at.mirjam.yumnotes.data

import android.content.Context
import at.mirjam.yumnotes.R
import java.io.File
import java.io.FileOutputStream

// Sample Recipe data to populate the database
// for testing the app :)
object SampleData {
    // Helper function to copy image resources to internal storage
    private fun copyResourceToFile(context: Context, resourceId: Int, fileName: String): File? {
        return try {
            val file = File(context.filesDir, fileName)
            context.resources.openRawResource(resourceId).use { inputStream ->
                FileOutputStream(file).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Sample recipes with image resources
    fun getSampleRecipes(context: Context): List<Recipe> {
        // Copy resources to internal storage and get file paths
        val bologneseImage = copyResourceToFile(context, R.drawable.bolognese, "bolognese.jpg")
        val pancakeImage = copyResourceToFile(context, R.drawable.pancakes, "pancakes.jpg")

        return listOf(
            Recipe(
                name = "Spaghetti Bolognese",
                ingredients = "Spaghetti, ground beef, tomato sauce, onion, garlic",
                instructions = "Cook spaghetti. Prepare sauce with ground beef and tomato sauce.",
                collectionTags = "Dinner, Favorites",
                selectedTags = "Italian",
                imageUri = bologneseImage?.name // Save image file name
            ),
            Recipe(
                name = "Pancakes",
                ingredients = "Flour, milk, eggs, sugar, baking powder",
                instructions = "Mix ingredients. Cook on a hot pan until golden brown.",
                collectionTags = "Breakfast, Favorites",
                selectedTags = "Sweet",
                imageUri = pancakeImage?.name
            )
        )
    }
}