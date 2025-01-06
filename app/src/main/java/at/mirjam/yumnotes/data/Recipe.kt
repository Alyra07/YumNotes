package at.mirjam.yumnotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// General data structure for a Recipe
@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val ingredients: String,
    val instructions: String,
    val collectionTags: String, // Comma-separated custom tags
    val selectedTags: String,  // Comma-separated predefined tags
    val imageUri: String? = null, // Store the image URI (FileUtil)
    val originalImageUri: String? = null // Store the original image URI (for update when editing)
)