package com.aksharadeeptutor.ui.syllabus

import android.os.Bundle
import androidx.navigation.NavDirections
import com.aksharadeeptutor.R
import kotlin.Int
import kotlin.String

public class SyllabusFragmentDirections private constructor() {
  private data class ActionSyllabusToQuiz(
    public val chapterId: Int = 0,
    public val chapterName: String = "",
  ) : NavDirections {
    public override val actionId: Int = R.id.action_syllabus_to_quiz

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putInt("chapterId", this.chapterId)
        result.putString("chapterName", this.chapterName)
        return result
      }
  }

  public companion object {
    public fun actionSyllabusToQuiz(chapterId: Int = 0, chapterName: String = ""): NavDirections =
        ActionSyllabusToQuiz(chapterId, chapterName)
  }
}
