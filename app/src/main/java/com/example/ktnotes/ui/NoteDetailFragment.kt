package com.example.ktnotes.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ktnotes.BaseFragment
import com.example.ktnotes.R
import com.example.ktnotes.databinding.FragmentNoteDetailBinding
import com.google.android.material.snackbar.Snackbar

class NoteDetailFragment : BaseFragment() {

    companion object {
        fun newInstance() = NoteDetailFragment()
    }
    val navArgs: NoteDetailFragmentArgs by navArgs()
    val noteId: String by lazy { navArgs.id }
    private val viewModel: NoteDetailViewModel by viewModels()
    lateinit var binding: FragmentNoteDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        binding.fragment = this
        viewModel.data.observe(viewLifecycleOwner, { note ->
            binding.note = note
        })
        viewModel.loadNote(noteId)
        return binding.root
    }

    fun onBackBtnClicked(v: View) {
        findNavController().navigateUp()
    }

    override fun setupBottomBar() {
        (requireActivity() as? FullscreenActivity)?.apply {
            //FAB Button will do the job of saving the note
            this.fabOnClickListener = { view ->
                viewModel.saveNote()
                Toast.makeText(view.context, "Note Saved",Toast.LENGTH_SHORT).show()
            }
            this.binding.fab.setImageResource(R.drawable.ic_baseline_save_24)
        }
    }




}