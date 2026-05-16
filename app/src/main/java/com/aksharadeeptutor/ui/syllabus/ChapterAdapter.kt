package com.aksharadeeptutor.ui.syllabus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aksharadeeptutor.data.model.Chapter
import com.aksharadeeptutor.data.model.ChapterStatus
import com.aksharadeeptutor.databinding.ItemChapterBinding

class ChapterAdapter(
    private val onChapterClick: (Chapter) -> Unit,
    private val onStatusChange: (Chapter, ChapterStatus) -> Unit
) : ListAdapter<Chapter, ChapterAdapter.ChapterViewHolder>(ChapterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        val binding = ItemChapterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ChapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ChapterViewHolder(
        private val binding: ItemChapterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onChapterClick(getItem(position))
                }
            }

            binding.buttonMarkComplete.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val chapter = getItem(position)
                    val newStatus = if (chapter.status == ChapterStatus.COMPLETED) {
                        ChapterStatus.NOT_STARTED
                    } else {
                        ChapterStatus.COMPLETED
                    }
                    onStatusChange(chapter, newStatus)
                }
            }
        }

        fun bind(chapter: Chapter) {
            binding.textViewChapterName.text = chapter.name
            binding.textViewChapterStatus.text = when (chapter.status) {
                ChapterStatus.NOT_STARTED -> "Not Started"
                ChapterStatus.IN_PROGRESS -> "In Progress"
                ChapterStatus.COMPLETED -> "Completed"
            }

            binding.buttonMarkComplete.text = when (chapter.status) {
                ChapterStatus.COMPLETED -> "Undo"
                else -> "Mark Complete"
            }

            binding.progressBarChapter.progress = chapter.progress
        }
    }

    class ChapterDiffCallback : DiffUtil.ItemCallback<Chapter>() {
        override fun areItemsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
            return oldItem == newItem
        }
    }
}
