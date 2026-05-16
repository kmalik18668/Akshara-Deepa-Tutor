package com.aksharadeeptutor.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.aksharadeeptutor.data.model.Question;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class QuestionDao_Impl implements QuestionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Question> __insertionAdapterOfQuestion;

  public QuestionDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfQuestion = new EntityInsertionAdapter<Question>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `questions` (`id`,`chapterId`,`questionText`,`optionA`,`optionB`,`optionC`,`optionD`,`correctAnswer`,`explanation`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Question value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getChapterId());
        if (value.getQuestionText() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getQuestionText());
        }
        if (value.getOptionA() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getOptionA());
        }
        if (value.getOptionB() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getOptionB());
        }
        if (value.getOptionC() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getOptionC());
        }
        if (value.getOptionD() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getOptionD());
        }
        if (value.getCorrectAnswer() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getCorrectAnswer());
        }
        if (value.getExplanation() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getExplanation());
        }
      }
    };
  }

  @Override
  public Object insertQuestions(final List<Question> questions,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfQuestion.insert(questions);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object getQuestionsForChapter(final int chapterId,
      final Continuation<? super List<Question>> continuation) {
    final String _sql = "SELECT * FROM questions WHERE chapterId = ? ORDER BY RANDOM() LIMIT 5";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, chapterId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Question>>() {
      @Override
      public List<Question> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChapterId = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterId");
          final int _cursorIndexOfQuestionText = CursorUtil.getColumnIndexOrThrow(_cursor, "questionText");
          final int _cursorIndexOfOptionA = CursorUtil.getColumnIndexOrThrow(_cursor, "optionA");
          final int _cursorIndexOfOptionB = CursorUtil.getColumnIndexOrThrow(_cursor, "optionB");
          final int _cursorIndexOfOptionC = CursorUtil.getColumnIndexOrThrow(_cursor, "optionC");
          final int _cursorIndexOfOptionD = CursorUtil.getColumnIndexOrThrow(_cursor, "optionD");
          final int _cursorIndexOfCorrectAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "correctAnswer");
          final int _cursorIndexOfExplanation = CursorUtil.getColumnIndexOrThrow(_cursor, "explanation");
          final List<Question> _result = new ArrayList<Question>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Question _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpChapterId;
            _tmpChapterId = _cursor.getInt(_cursorIndexOfChapterId);
            final String _tmpQuestionText;
            if (_cursor.isNull(_cursorIndexOfQuestionText)) {
              _tmpQuestionText = null;
            } else {
              _tmpQuestionText = _cursor.getString(_cursorIndexOfQuestionText);
            }
            final String _tmpOptionA;
            if (_cursor.isNull(_cursorIndexOfOptionA)) {
              _tmpOptionA = null;
            } else {
              _tmpOptionA = _cursor.getString(_cursorIndexOfOptionA);
            }
            final String _tmpOptionB;
            if (_cursor.isNull(_cursorIndexOfOptionB)) {
              _tmpOptionB = null;
            } else {
              _tmpOptionB = _cursor.getString(_cursorIndexOfOptionB);
            }
            final String _tmpOptionC;
            if (_cursor.isNull(_cursorIndexOfOptionC)) {
              _tmpOptionC = null;
            } else {
              _tmpOptionC = _cursor.getString(_cursorIndexOfOptionC);
            }
            final String _tmpOptionD;
            if (_cursor.isNull(_cursorIndexOfOptionD)) {
              _tmpOptionD = null;
            } else {
              _tmpOptionD = _cursor.getString(_cursorIndexOfOptionD);
            }
            final String _tmpCorrectAnswer;
            if (_cursor.isNull(_cursorIndexOfCorrectAnswer)) {
              _tmpCorrectAnswer = null;
            } else {
              _tmpCorrectAnswer = _cursor.getString(_cursorIndexOfCorrectAnswer);
            }
            final String _tmpExplanation;
            if (_cursor.isNull(_cursorIndexOfExplanation)) {
              _tmpExplanation = null;
            } else {
              _tmpExplanation = _cursor.getString(_cursorIndexOfExplanation);
            }
            _item = new Question(_tmpId,_tmpChapterId,_tmpQuestionText,_tmpOptionA,_tmpOptionB,_tmpOptionC,_tmpOptionD,_tmpCorrectAnswer,_tmpExplanation);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @Override
  public Flow<List<Question>> getAllQuestionsForChapter(final int chapterId) {
    final String _sql = "SELECT * FROM questions WHERE chapterId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, chapterId);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"questions"}, new Callable<List<Question>>() {
      @Override
      public List<Question> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChapterId = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterId");
          final int _cursorIndexOfQuestionText = CursorUtil.getColumnIndexOrThrow(_cursor, "questionText");
          final int _cursorIndexOfOptionA = CursorUtil.getColumnIndexOrThrow(_cursor, "optionA");
          final int _cursorIndexOfOptionB = CursorUtil.getColumnIndexOrThrow(_cursor, "optionB");
          final int _cursorIndexOfOptionC = CursorUtil.getColumnIndexOrThrow(_cursor, "optionC");
          final int _cursorIndexOfOptionD = CursorUtil.getColumnIndexOrThrow(_cursor, "optionD");
          final int _cursorIndexOfCorrectAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "correctAnswer");
          final int _cursorIndexOfExplanation = CursorUtil.getColumnIndexOrThrow(_cursor, "explanation");
          final List<Question> _result = new ArrayList<Question>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Question _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpChapterId;
            _tmpChapterId = _cursor.getInt(_cursorIndexOfChapterId);
            final String _tmpQuestionText;
            if (_cursor.isNull(_cursorIndexOfQuestionText)) {
              _tmpQuestionText = null;
            } else {
              _tmpQuestionText = _cursor.getString(_cursorIndexOfQuestionText);
            }
            final String _tmpOptionA;
            if (_cursor.isNull(_cursorIndexOfOptionA)) {
              _tmpOptionA = null;
            } else {
              _tmpOptionA = _cursor.getString(_cursorIndexOfOptionA);
            }
            final String _tmpOptionB;
            if (_cursor.isNull(_cursorIndexOfOptionB)) {
              _tmpOptionB = null;
            } else {
              _tmpOptionB = _cursor.getString(_cursorIndexOfOptionB);
            }
            final String _tmpOptionC;
            if (_cursor.isNull(_cursorIndexOfOptionC)) {
              _tmpOptionC = null;
            } else {
              _tmpOptionC = _cursor.getString(_cursorIndexOfOptionC);
            }
            final String _tmpOptionD;
            if (_cursor.isNull(_cursorIndexOfOptionD)) {
              _tmpOptionD = null;
            } else {
              _tmpOptionD = _cursor.getString(_cursorIndexOfOptionD);
            }
            final String _tmpCorrectAnswer;
            if (_cursor.isNull(_cursorIndexOfCorrectAnswer)) {
              _tmpCorrectAnswer = null;
            } else {
              _tmpCorrectAnswer = _cursor.getString(_cursorIndexOfCorrectAnswer);
            }
            final String _tmpExplanation;
            if (_cursor.isNull(_cursorIndexOfExplanation)) {
              _tmpExplanation = null;
            } else {
              _tmpExplanation = _cursor.getString(_cursorIndexOfExplanation);
            }
            _item = new Question(_tmpId,_tmpChapterId,_tmpQuestionText,_tmpOptionA,_tmpOptionB,_tmpOptionC,_tmpOptionD,_tmpCorrectAnswer,_tmpExplanation);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getTotalQuestionCount(final Continuation<? super Integer> continuation) {
    final String _sql = "SELECT COUNT(*) FROM questions";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if(_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
