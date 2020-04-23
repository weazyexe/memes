package exe.weazy.memes.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import exe.weazy.memes.util.values.EMPTY_STRING
import java.util.*

@Entity
data class Meme(
    @PrimaryKey
    val id: Long,
    val title: String,
    val description: String?,
    val isFavorite: Boolean,
    val createDate: Date,
    val photoUrl: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        id = parcel.readLong(),
        title = parcel.readString() ?: EMPTY_STRING,
        description = parcel.readString(),
        isFavorite = parcel.readByte() != 0.toByte(),
        createDate = Date(parcel.readLong()),
        photoUrl = parcel.readString() ?: EMPTY_STRING
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeByte(if (isFavorite) 1 else 0)
        parcel.writeLong(createDate.time)
        parcel.writeString(photoUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Meme> {
        override fun createFromParcel(parcel: Parcel): Meme {
            return Meme(parcel)
        }

        override fun newArray(size: Int): Array<Meme?> {
            return arrayOfNulls(size)
        }
    }
}