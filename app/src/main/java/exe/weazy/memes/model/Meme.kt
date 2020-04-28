package exe.weazy.memes.model

import exe.weazy.memes.data.db.entities.MemeEntity
import java.util.*

data class Meme(
    val id: Long,
    val title: String,
    val description: String?,
    val isFavorite: Boolean,
    val createDate: Date,
    val photoUrl: String
) {
    fun toEntity() = MemeEntity(
        id = id,
        title = title,
        description = description,
        isFavorite = isFavorite,
        createDate = createDate.time,
        photoUrl = photoUrl
    )
}