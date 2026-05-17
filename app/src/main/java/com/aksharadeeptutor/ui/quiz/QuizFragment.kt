package com.aksharadeeptutor.ui.quiz

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aksharadeeptutor.MainActivity
import com.aksharadeeptutor.R
import com.aksharadeeptutor.data.model.Question
import com.aksharadeeptutor.databinding.FragmentQuizBinding
import com.aksharadeeptutor.viewmodel.TutorViewModel
import com.aksharadeeptutor.viewmodel.TutorViewModelFactory
import kotlinx.coroutines.launch

class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TutorViewModel by viewModels {
        TutorViewModelFactory((requireActivity() as MainActivity).database)
    }

    private val args: QuizFragmentArgs by navArgs()

    private var questions: List<Question> = emptyList()
    private var currentQuestionIndex = 0
    private var score = 0
    private var selectedAnswers = mutableMapOf<Int, String>()
    private var quizTimer: CountDownTimer? = null
    private var timeRemaining: Long = 300000
    private val quizDuration: Long = 300000
    private var isReviewMode = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textViewChapterName.text = args.chapterName
        loadQuestions()
        setupClickListeners()
    }

    private fun loadQuestions() {
        viewLifecycleOwner.lifecycleScope.launch {
            questions = viewModel.getQuizQuestions(args.chapterId)
            if (questions.isEmpty()) {
                Toast.makeText(requireContext(), "No questions available for this chapter", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
                return@launch
            }
            currentQuestionIndex = 0
            score = 0
            selectedAnswers.clear()
            timeRemaining = quizDuration
            isReviewMode = false
            setupQuestionDots()
            displayQuestion()
            startTimer()
        }
    }

    private fun setupQuestionDots() {
        binding.questionDotsContainer.removeAllViews()
        questions.forEachIndexed { index, _ ->
            val dot = TextView(requireContext()).apply {
                text = "${index + 1}"
                textSize = 11f
                setPadding(6, 4, 6, 4)
                gravity = android.view.Gravity.CENTER
                minWidth = 24
                minHeight = 24
                setBackgroundResource(R.drawable.dot_unanswered)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.onSurfaceVariant))
            }
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { marginEnd = 4 }
            dot.layoutParams = params
            binding.questionDotsContainer.addView(dot)
        }
        updateQuestionDots()
    }

    private fun updateQuestionDots() {
        for (i in 0 until binding.questionDotsContainer.childCount) {
            val dot = binding.questionDotsContainer.getChildAt(i) as TextView
            val questionIndex = i
            val isAnswered = selectedAnswers.containsKey(questionIndex)
            val isCurrent = questionIndex == currentQuestionIndex

            val bgRes = when {
                isCurrent -> R.drawable.dot_current
                isAnswered -> R.drawable.dot_answered
                else -> R.drawable.dot_unanswered
            }
            dot.setBackgroundResource(bgRes)
            dot.setTextColor(ContextCompat.getColor(requireContext(), if (isCurrent) R.color.white else R.color.onSurfaceVariant))
        }
    }

    private fun displayQuestion() {
        val question = questions[currentQuestionIndex]
        binding.textViewQuestionNumber.text = "Question ${currentQuestionIndex + 1} of ${questions.size}"
        binding.textViewQuestionText.text = question.questionText
        binding.buttonOptionA.text = question.optionA
        binding.buttonOptionB.text = question.optionB
        binding.buttonOptionC.text = question.optionC
        binding.buttonOptionD.text = question.optionD

        binding.progressIndicator.progress = ((currentQuestionIndex + 1) * 100) / questions.size

        clearSelections()

        if (isReviewMode) {
            showReviewState(question)
            binding.buttonOptionA.isClickable = false
            binding.buttonOptionB.isClickable = false
            binding.buttonOptionC.isClickable = false
            binding.buttonOptionD.isClickable = false
            // Show explanation card in review mode
            binding.cardExplanation.visibility = android.view.View.VISIBLE
            binding.textViewExplanation.text = question.explanation
        } else {
            // Hide explanation card during active quiz
            binding.cardExplanation.visibility = android.view.View.GONE
            selectedAnswers[currentQuestionIndex]?.let { selectedOption ->
                when (selectedOption) {
                    question.optionA -> selectButton(binding.buttonOptionA)
                    question.optionB -> selectButton(binding.buttonOptionB)
                    question.optionC -> selectButton(binding.buttonOptionC)
                    question.optionD -> selectButton(binding.buttonOptionD)
                }
            }
            binding.buttonOptionA.isClickable = true
            binding.buttonOptionB.isClickable = true
            binding.buttonOptionC.isClickable = true
            binding.buttonOptionD.isClickable = true
        }

        binding.buttonPrevious.visibility = if (currentQuestionIndex > 0) View.VISIBLE else View.GONE
        binding.buttonNext.text = if (currentQuestionIndex == questions.size - 1) {
            if (isReviewMode) "Done" else "Submit"
        } else "Next"

        updateQuestionDots()
    }

    private fun showReviewState(question: Question) {
        val userAnswer = selectedAnswers[currentQuestionIndex]
        val options = mapOf(
            binding.buttonOptionA to question.optionA,
            binding.buttonOptionB to question.optionB,
            binding.buttonOptionC to question.optionC,
            binding.buttonOptionD to question.optionD
        )

        options.forEach { (button, optionText) ->
            when {
                optionText == question.correctAnswer -> {
                    button.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.success)
                    button.strokeColor = ContextCompat.getColorStateList(requireContext(), R.color.success)
                    button.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }
                optionText == userAnswer && userAnswer != question.correctAnswer -> {
                    button.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.error)
                    button.strokeColor = ContextCompat.getColorStateList(requireContext(), R.color.error)
                    button.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }
                else -> {
                    resetButton(button)
                }
            }
        }
    }

    private fun clearSelections() {
        resetButton(binding.buttonOptionA)
        resetButton(binding.buttonOptionB)
        resetButton(binding.buttonOptionC)
        resetButton(binding.buttonOptionD)
    }

    private fun selectButton(button: com.google.android.material.button.MaterialButton) {
        button.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.primaryContainer)
        button.strokeColor = ContextCompat.getColorStateList(requireContext(), R.color.primary)
        button.setTextColor(ContextCompat.getColor(requireContext(), R.color.onPrimaryContainer))
    }

    private fun resetButton(button: com.google.android.material.button.MaterialButton) {
        button.backgroundTintList = ContextCompat.getColorStateList(requireContext(), android.R.color.transparent)
        button.strokeColor = ContextCompat.getColorStateList(requireContext(), R.color.outline)
        button.setTextColor(ContextCompat.getColor(requireContext(), R.color.onSurface))
    }

    private fun setupClickListeners() {
        binding.buttonOptionA.setOnClickListener { selectOption(questions[currentQuestionIndex].optionA) }
        binding.buttonOptionB.setOnClickListener { selectOption(questions[currentQuestionIndex].optionB) }
        binding.buttonOptionC.setOnClickListener { selectOption(questions[currentQuestionIndex].optionC) }
        binding.buttonOptionD.setOnClickListener { selectOption(questions[currentQuestionIndex].optionD) }

        binding.buttonNext.setOnClickListener {
            if (isReviewMode) {
                if (currentQuestionIndex < questions.size - 1) {
                    currentQuestionIndex++
                    displayQuestion()
                } else {
                    findNavController().popBackStack()
                }
            } else if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
                displayQuestion()
            } else {
                showSubmitConfirmation()
            }
        }

        binding.buttonPrevious.setOnClickListener {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--
                displayQuestion()
            }
        }
    }

    private fun selectOption(option: String) {
        selectedAnswers[currentQuestionIndex] = option
        clearSelections()
        val question = questions[currentQuestionIndex]
        when (option) {
            question.optionA -> selectButton(binding.buttonOptionA)
            question.optionB -> selectButton(binding.buttonOptionB)
            question.optionC -> selectButton(binding.buttonOptionC)
            question.optionD -> selectButton(binding.buttonOptionD)
        }
        updateQuestionDots()
    }

    private fun showSubmitConfirmation() {
        val unanswered = questions.size - selectedAnswers.size
        val message = if (unanswered > 0) {
            "You have $unanswered unanswered question${if (unanswered > 1) "s" else ""}. Submit anyway?"
        } else {
            "Submit your quiz?"
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Submit Quiz")
            .setMessage(message)
            .setPositiveButton("Submit") { _, _ -> submitQuiz() }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun startTimer() {
        quizTimer?.cancel()
        quizTimer = object : CountDownTimer(timeRemaining, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = millisUntilFinished
                val minutes = millisUntilFinished / 60000
                val seconds = (millisUntilFinished % 60000) / 1000
                binding.textViewTimer.text = String.format("%02d:%02d", minutes, seconds)

                if (millisUntilFinished < 60000) {
                    binding.cardTimer.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.error))
                }
            }

            override fun onFinish() {
                binding.textViewTimer.text = "00:00"
                Toast.makeText(requireContext(), "Time's up!", Toast.LENGTH_SHORT).show()
                submitQuiz()
            }
        }.start()
    }

    private fun submitQuiz() {
        quizTimer?.cancel()
        score = 0
        questions.forEachIndexed { index, question ->
            if (selectedAnswers[index] == question.correctAnswer) {
                score++
            }
        }

        val answersJson = selectedAnswers.map { (key, value) -> "$key:$value" }.joinToString(",")

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.submitQuiz(args.chapterId, score, questions.size, answersJson)
            showResults()
        }
    }

    private fun showResults() {
        val percentage = (score * 100) / questions.size
        val passed = percentage >= 60
        val title = if (passed) "Chapter Completed!" else "Needs Practice"
        val message = "Score: $score/${questions.size} ($percentage%)\n\n" +
                "Time: ${formatTime(quizDuration - timeRemaining)}\n" +
                "Correct: $score\n" +
                "Incorrect: ${questions.size - score}"

        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Review Answers") { _, _ ->
                isReviewMode = true
                currentQuestionIndex = 0
                binding.buttonNext.text = "Next"
                displayQuestion()
            }
            .setNegativeButton("Done") { _, _ ->
                findNavController().popBackStack()
            }
            .setCancelable(false)
            .show()
    }

    private fun formatTime(millis: Long): String {
        val minutes = millis / 60000
        val seconds = (millis % 60000) / 1000
        return String.format("%02d:%02d", minutes, seconds)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        quizTimer?.cancel()
        _binding = null
    }
}
