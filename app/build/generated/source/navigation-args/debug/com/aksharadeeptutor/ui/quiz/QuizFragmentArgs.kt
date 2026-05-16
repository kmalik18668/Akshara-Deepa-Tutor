package com.aksharadeeptutor.ui.quiz

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Int
import kotlin.String
import kotlin.jvm.JvmStatic

public data class QuizFragmentArgs(
  public val chapterId: Int = 0,
  public val chapterName: String = "",
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putInt("chapterId", this.chapterId)
    result.putString("chapterName", this.chapterName)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("chapterId", this.chapterId)
    result.set("chapterName", this.chapterName)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): QuizFragmentArgs {
      bundle.setClassLoader(QuizFragmentArgs::class.java.classLoader)
      val __chapterId : Int
      if (bundle.containsKey("chapterId")) {
        __chapterId = bundle.getInt("chapterId")
      } else {
        __chapterId = 0
      }
      val __chapterName : String?
      if (bundle.containsKey("chapterName")) {
        __chapterName = bundle.getString("chapterName")
        if (__chapterName == null) {
          throw IllegalArgumentException("Argument \"chapterName\" is marked as non-null but was passed a null value.")
        }
      } else {
        __chapterName = ""
      }
      return QuizFragmentArgs(__chapterId, __chapterName)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): QuizFragmentArgs {
      val __chapterId : Int?
      if (savedStateHandle.contains("chapterId")) {
        __chapterId = savedStateHandle["chapterId"]
        if (__chapterId == null) {
          throw IllegalArgumentException("Argument \"chapterId\" of type integer does not support null values")
        }
      } else {
        __chapterId = 0
      }
      val __chapterName : String?
      if (savedStateHandle.contains("chapterName")) {
        __chapterName = savedStateHandle["chapterName"]
        if (__chapterName == null) {
          throw IllegalArgumentException("Argument \"chapterName\" is marked as non-null but was passed a null value")
        }
      } else {
        __chapterName = ""
      }
      return QuizFragmentArgs(__chapterId, __chapterName)
    }
  }
}
