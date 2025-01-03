package at.mirjam.yumnotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Profile(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val username: String,
    val profileImageUri: String? = null
)