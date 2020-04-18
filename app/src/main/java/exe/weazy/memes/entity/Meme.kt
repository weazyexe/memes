package exe.weazy.memes.entity

import java.util.*

data class Meme(
    val id: Long,
    val title: String,
    val description: String?,
    val isFavorite: Boolean,
    val createDate: Date,
    val photoUrl: String
)