package exe.weazy.memes.recycler

import androidx.recyclerview.widget.DiffUtil
import exe.weazy.memes.entity.Meme

class MemesDiffUtils(private val oldMemes: List<Meme>, private val newMemes: List<Meme>)
    : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int)
            = oldMemes[oldItemPosition].id == newMemes[newItemPosition].id

    override fun getOldListSize() = oldMemes.size

    override fun getNewListSize() = newMemes.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int)
            = oldMemes[oldItemPosition] == newMemes[newItemPosition]
}