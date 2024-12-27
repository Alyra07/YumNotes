package at.mirjam.yumnotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val ingredients: String,
    val instructions: String,
    val collectionTags: String // Comma-separated tags, e.g., "Dessert,Quick,Budget"
)