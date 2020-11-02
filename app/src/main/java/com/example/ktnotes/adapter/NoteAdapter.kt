package com.example.ktnotes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.example.ktnotes.databinding.CellNoteItemBinding
import com.example.ktnotes.model.NoteCell

/**
 * A Simple List Adapter for Recycler View. To display Note List on Fragment.
 */
class NoteListAdapter(val listener: NoteCellListener): ListAdapter<NoteCell, NoteViewHolder>(NoteCell.DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = CellNoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

}


class NoteViewHolder(val binding: CellNoteItemBinding
                     , val listener: NoteCellListener): RecyclerView.ViewHolder(binding.root) {
    init {
        //Assign Listener to binding
        binding.listener = listener
        binding.starImgBtn.setOnClickListener { view ->
            listener.onStarClicked(view, binding.note!!)
        }
    }


    /**
     * Assigning data to viewHolder, execute when onBindViewHolder
     */
    fun bindData(noteCell: NoteCell) {
        binding.note = noteCell
        binding.executePendingBindings()
    }
}




/**
 * Customized Listener for Note Cell in Recycler View
 */
interface NoteCellListener {
    fun onNoteClicked(v: View, noteCell: NoteCell)
    fun onStarClicked(v: View, noteCell: NoteCell)
}