package com.example.ktnotes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ktnotes.BaseFragment
import com.example.ktnotes.R
import com.example.ktnotes.adapter.NoteCellListener
import com.example.ktnotes.adapter.NoteListAdapter
import com.example.ktnotes.databinding.FragmentNoteListBinding
import com.example.ktnotes.model.NoteCell
import com.google.android.material.button.MaterialButton

class NoteListFragment: BaseFragment(), NoteCellListener {
    private lateinit var binding: FragmentNoteListBinding
    private lateinit var viewModel: NoteListViewModel
    private val adapter = NoteListAdapter(this)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(NoteListViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recView.adapter = adapter
        initObservers()
    }

    private fun initObservers() {
        viewModel.dataSource.observe(viewLifecycleOwner, { adapter.submitList(it) })
    }

    private fun createNewNoteAndNavigate() {
        viewModel.createNote {
            val detailDirections = NoteListFragmentDirections.actionNoteListFragmentToNoteDetail(it.id)
            findNavController().navigate(detailDirections)
        }
    }


    //region NOTE CELL LISTENER DELEGATE METHOD

    override fun onNoteClicked(v: View, noteCell: NoteCell) {
        //Navigate to the detail fragment
        val detailDirections = NoteListFragmentDirections.actionNoteListFragmentToNoteDetail(noteCell.id)
        findNavController().navigate(detailDirections)
    }

    override fun onStarClicked(v: View, noteCell: NoteCell) {
        //Save this note to db.
        viewModel.saveNoteToDB(noteCell)
    }

    //endregion

    //region Abstract Methods
    override fun setupBottomBar() {
        (activity as? FullscreenActivity)?.apply {
            this.fabOnClickListener = { view ->
                createNewNoteAndNavigate()
            }
            this.binding.fab.setImageResource(R.drawable.ic_baseline_edit_24)

            this.binding.bottomBarBtn.setOnClickListener {
                val bottomFrag = BottomMenuFragment.newInstance(R.menu.sort_menu)
                bottomFrag.onItemSelected =  { title ->
                    (it as? MaterialButton)?.text = title
                    //Do Some sorting method on DataSource.
                    viewModel.sort(NoteCell.sortByFromString(title.toString()))
                }
                bottomFrag.show(parentFragmentManager, null)
            }
        }
    }

    //endregion
}