package com.aksharadeeptutor.ui.syllabus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aksharadeeptutor.R
import com.aksharadeeptutor.data.model.Chapter
import com.aksharadeeptutor.data.model.Subject
import com.aksharadeeptutor.databinding.ItemSubjectBinding

data class SubjectUiModel(
    val subject: Subject,
    val chapters: List<Chapter>,
    val isExpanded: Boolean
)

class SubjectAdapter(
    private val onSubjectClick: (Subject) -> Unit,
    private val onChapterQuiz: (Chapter) -> Unit,
    private val onMarkComplete: (Chapter) -> Unit
) : ListAdapter<SubjectUiModel, SubjectAdapter.SubjectViewHolder>(SubjectUiDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val binding = ItemSubjectBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SubjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SubjectViewHolder(
        private val binding: ItemSubjectBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private var chapterAdapter: ChapterAdapter? = null

        init {
            binding.cardSubjectHeader.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onSubjectClick(getItem(position).subject)
                }
            }
        }

        fun bind(uiModel: SubjectUiModel) {
            val subject = uiModel.subject
            val completedCount = uiModel.chapters.count { it.progress >= 100 }
            binding.textViewSubjectName.text = subject.name
            binding.textViewChapterCount.text = "$completedCount/${uiModel.chapters.size} chapters completed"
            binding.progressBarSubject.progress = subject.progress
            binding.progressBarSubject.max = 100

            val iconRes = when (subject.id) {
                1 -> R.drawable.ic_science
                2 -> R.drawable.ic_math
                3 -> R.drawable.ic_social
                else -> R.drawable.ic_science
            }
            binding.imageViewSubjectIcon.setImageResource(iconRes)

            binding.recyclerViewChapters.visibility = if (uiModel.isExpanded) View.VISIBLE else View.GONE
            binding.imageExpandIndicator.rotation = if (uiModel.isExpanded) 90f else 0f

            if (uiModel.isExpanded) {
                if (chapterAdapter == null) {
                    chapterAdapter = ChapterAdapter(onChapterQuiz, onMarkComplete)
                    binding.recyclerViewChapters.layoutManager = LinearLayoutManager(binding.root.context)
                    binding.recyclerViewChapters.adapter = chapterAdapter
                }
                chapterAdapter?.submitList(uiModel.chapters)
            }
        }
    }

    class SubjectUiDiffCallback : DiffUtil.ItemCallback<SubjectUiModel>() {
        override fun areItemsTheSame(oldItem: SubjectUiModel, newItem: SubjectUiModel): Boolean {
            return oldItem.subject.id == newItem.subject.id
        }

        override fun areContentsTheSame(oldItem: SubjectUiModel, newItem: SubjectUiModel): Boolean {
            return oldItem == newItem
        }
    }
}
