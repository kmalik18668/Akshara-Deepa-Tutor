package com.aksharadeeptutor.ui.syllabus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aksharadeeptutor.R
import com.aksharadeeptutor.data.model.Chapter
import com.aksharadeeptutor.data.model.ChapterStatus
import com.aksharadeeptutor.databinding.ItemChapterBinding

class ChapterAdapter(
    private val onStartQuiz: (Chapter) -> Unit,
    private val onMarkComplete: (Chapter) -> Unit
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

        fun bind(chapter: Chapter) {
            binding.textViewChapterName.text = chapter.name
            binding.progressBarChapter.progress = chapter.progress
            binding.progressBarChapter.max = 100

            // Status text + color + dot
            when (chapter.status) {
                ChapterStatus.COMPLETED -> {
                    binding.textViewChapterStatus.text = "✓ Completed"
                    binding.textViewChapterStatus.setTextColor(
                        ContextCompat.getColor(binding.root.context, R.color.tertiary)
                    )
                    binding.viewStatusDot.setBackgroundColor(
                        ContextCompat.getColor(binding.root.context, R.color.tertiary)
                    )
                    binding.buttonMarkComplete.setImageResource(R.drawable.ic_check_circle_filled)
                    binding.buttonMarkComplete.imageTintList = null
                }
                ChapterStatus.IN_PROGRESS -> {
                    binding.textViewChapterStatus.text = "⏳ In Progress"
                    binding.textViewChapterStatus.setTextColor(
                        ContextCompat.getColor(binding.root.context, R.color.warning)
                    )
                    binding.viewStatusDot.setBackgroundColor(
                        ContextCompat.getColor(binding.root.context, R.color.warning)
                    )
                    binding.buttonMarkComplete.setImageResource(R.drawable.ic_check_circle_outline)
                    binding.buttonMarkComplete.imageTintList =
                        ContextCompat.getColorStateList(binding.root.context, R.color.onSurfaceVariant)
                }
                ChapterStatus.NOT_STARTED -> {
                    binding.textViewChapterStatus.text = "Not Started"
                    binding.textViewChapterStatus.setTextColor(
                        ContextCompat.getColor(binding.root.context, R.color.onSurfaceVariant)
                    )
                    binding.viewStatusDot.setBackgroundColor(
                        ContextCompat.getColor(binding.root.context, R.color.outlineVariant)
                    )
                    binding.buttonMarkComplete.setImageResource(R.drawable.ic_check_circle_outline)
                    binding.buttonMarkComplete.imageTintList =
                        ContextCompat.getColorStateList(binding.root.context, R.color.onSurfaceVariant)
                }
            }

            binding.buttonStartQuiz.setOnClickListener { onStartQuiz(chapter) }

            // Toggle: completed -> not started; not started/in-progress -> completed
            binding.buttonMarkComplete.setOnClickListener {
                onMarkComplete(chapter)
            }
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
