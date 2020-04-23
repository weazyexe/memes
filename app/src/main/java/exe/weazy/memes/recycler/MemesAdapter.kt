package exe.weazy.memes.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import exe.weazy.memes.R
import exe.weazy.memes.entity.Meme

class MemesAdapter(
    var memes: List<Meme>,
    private val onFavoriteClick: View.OnClickListener,
    private val onShareClick: View.OnClickListener,
    private val onItemClick: View.OnClickListener
) : RecyclerView.Adapter<MemesAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_meme, parent, false)

        val favoriteButton = view.findViewById<ImageButton>(R.id.favoriteButton)
        val shareButton = view.findViewById<ImageButton>(R.id.shareButton)

        view.setOnClickListener(onItemClick)
        favoriteButton.setOnClickListener(onFavoriteClick)
        shareButton.setOnClickListener(onShareClick)

        return Holder(view)
    }

    override fun getItemCount() = memes.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(meme = memes[position])
    }

    fun updateMemes(memes: List<Meme>) {
        this.memes = memes.toList()
        notifyDataSetChanged()
    }

    inner class Holder(view: View): RecyclerView.ViewHolder(view) {

        private var memeImageView = view.findViewById<ImageView>(R.id.memeImageView)
        private var memeTitleTextView = view.findViewById<TextView>(R.id.memeTitleTextView)
        private var favoriteButton = view.findViewById<ImageButton>(R.id.favoriteButton)

        fun bind(meme: Meme) {
            Glide
                .with(memeImageView)
                .load(meme.photoUrl)
                .into(memeImageView)

            memeTitleTextView.text = meme.title

            val favoriteDrawable = if (meme.isFavorite) {
                R.drawable.ic_favorite_filled_blue_24dp
            } else {
                R.drawable.ic_favorite_border_white_24dp
            }

            favoriteButton.setImageDrawable(
                ContextCompat.getDrawable(favoriteButton.context, favoriteDrawable)
            )
        }
    }
}