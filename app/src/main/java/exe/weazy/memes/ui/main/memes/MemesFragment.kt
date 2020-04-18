package exe.weazy.memes.ui.main.memes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import exe.weazy.memes.R
import exe.weazy.memes.entity.Meme
import exe.weazy.memes.recycler.MemesAdapter
import exe.weazy.memes.state.MemesState
import exe.weazy.memes.ui.main.MainViewModel
import exe.weazy.memes.util.extensions.showErrorSnackbar
import exe.weazy.memes.util.extensions.useViewModel
import exe.weazy.memes.util.handleToolbarInsets
import kotlinx.android.synthetic.main.fragment_memes.*

class MemesFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MemesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_memes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = useViewModel(requireActivity(), MainViewModel::class.java)
        viewModel.fetchMemes()

        handleToolbarInsets(toolbarLayout)
        initObservers()
        initListeners()
    }

    private fun initListeners() {
        swipeRefreshMemesLayout.setOnRefreshListener {
            viewModel.fetchMemes()
            swipeRefreshMemesLayout.isRefreshing = false
        }
    }

    private fun initObservers() {
        viewModel.memesState.observe(viewLifecycleOwner, Observer {
            setState(it)
        })

        viewModel.memes.observe(viewLifecycleOwner, Observer {  memes ->
            if (::adapter.isInitialized) {
                adapter.updateMemes(memes)
            } else {
                initAdapter(memes)
            }
        })
    }

    private fun initAdapter(memes: List<Meme>) {
        adapter = MemesAdapter(
            memes,
            View.OnClickListener {
                Toast.makeText(requireContext(), "Like", Toast.LENGTH_SHORT).show()
            },
            View.OnClickListener {
                Toast.makeText(requireContext(), "Share", Toast.LENGTH_SHORT).show()
            })

        memesRecyclerView.adapter = adapter
        memesRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

    private fun setState(state: MemesState) {
        when (state) {
            MemesState.LOADING -> {
                if (viewModel.memes.value.isNullOrEmpty()) {
                    noContentProgressBar.isVisible = true
                    contentProgressBar.isVisible = false
                    memesRecyclerView.isVisible = false
                } else {
                    noContentProgressBar.isVisible = false
                    contentProgressBar.isVisible = true
                    memesRecyclerView.isVisible = true
                }

                errorTextView.isVisible = false
                emptyTextView.isVisible = false
            }

            MemesState.ERROR -> {
                noContentProgressBar.isVisible = false
                contentProgressBar.isVisible = false
                emptyTextView.isVisible = false

                if (viewModel.memes.value.isNullOrEmpty()) {
                    errorTextView.isVisible = true
                    memesRecyclerView.isVisible = false
                } else {
                    errorTextView.isVisible = false
                    memesRecyclerView.isVisible = true
                }

                val rootView = activity?.findViewById<ViewGroup>(R.id.rootViewMain)
                if (rootView != null) {
                    activity?.showErrorSnackbar(R.string.memes_error, rootView)
                }

            }

            MemesState.SUCCESS, MemesState.DEFAULT -> {
                noContentProgressBar.isVisible = false
                contentProgressBar.isVisible = false
                memesRecyclerView.isVisible = true
                errorTextView.isVisible = false
                emptyTextView.isVisible = false
            }

            MemesState.EMPTY -> {
                noContentProgressBar.isVisible = false
                contentProgressBar.isVisible = false
                memesRecyclerView.isVisible = false
                errorTextView.isVisible = false
                emptyTextView.isVisible = true
            }
        }
    }
}