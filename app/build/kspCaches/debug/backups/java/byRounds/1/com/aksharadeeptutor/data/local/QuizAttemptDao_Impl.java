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
import com.aksharadeeptutor.data.model.QuizAttempt;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class QuizAttemptDao_Impl implements QuizAttemptDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<QuizAttempt> __insertionAdapterOfQuizAttempt;

  public QuizAttemptDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfQuizAttempt = new EntityInsertionAdapter<QuizAttempt>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `quiz_attempts` (`id`,`chapterId`,`score`,`totalQuestions`,`timestamp`,`answers`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, QuizAttempt value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getChapterId());
        stmt.bindLong(3, value.getScore());
        stmt.bindLong(4, value.getTotalQuestions());
        stmt.bindLong(5, value.getTimestamp());
        if (value.getAnswers() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getAnswers());
        }
      }
    };
  }

  @Override
  public Object insertQuizAttempt(final QuizAttempt quizAttempt,
      final Continuation<? super Long> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfQuizAttempt.insertAndReturnId(quizAttempt);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object getAttemptsByChapter(final int chapterId,
      final Continuation<? super List<QuizAttempt>> continuation) {
    final String _sql = "SELECT * FROM quiz_attempts WHERE chapterId = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, chapterId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<QuizAttempt>>() {
      @Override
      public List<QuizAttempt> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChapterId = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterId");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfTotalQuestions = CursorUtil.getColumnIndexOrThrow(_cursor, "totalQuestions");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfAnswers = CursorUtil.getColumnIndexOrThrow(_cursor, "answers");
          final List<QuizAttempt> _result = new ArrayList<QuizAttempt>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final QuizAttempt _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpChapterId;
            _tmpChapterId = _cursor.getInt(_cursorIndexOfChapterId);
            final int _tmpScore;
            _tmpScore = _cursor.getInt(_cursorIndexOfScore);
            final int _tmpTotalQuestions;
            _tmpTotalQuestions = _cursor.getInt(_cursorIndexOfTotalQuestions);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpAnswers;
            if (_cursor.isNull(_cursorIndexOfAnswers)) {
              _tmpAnswers = null;
            } else {
              _tmpAnswers = _cursor.getString(_cursorIndexOfAnswers);
            }
            _item = new QuizAttempt(_tmpId,_tmpChapterId,_tmpScore,_tmpTotalQuestions,_tmpTimestamp,_tmpAnswers);
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
  public Object getAllAttempts(final Continuation<? super List<QuizAttempt>> continuation) {
    final String _sql = "SELECT * FROM quiz_attempts ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<QuizAttempt>>() {
      @Override
      public List<QuizAttempt> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChapterId = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterId");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfTotalQuestions = CursorUtil.getColumnIndexOrThrow(_cursor, "totalQuestions");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfAnswers = CursorUtil.getColumnIndexOrThrow(_cursor, "answers");
          final List<QuizAttempt> _result = new ArrayList<QuizAttempt>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final QuizAttempt _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpChapterId;
            _tmpChapterId = _cursor.getInt(_cursorIndexOfChapterId);
            final int _tmpScore;
            _tmpScore = _cursor.getInt(_cursorIndexOfScore);
            final int _tmpTotalQuestions;
            _tmpTotalQuestions = _cursor.getInt(_cursorIndexOfTotalQuestions);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpAnswers;
            if (_cursor.isNull(_cursorIndexOfAnswers)) {
              _tmpAnswers = null;
            } else {
              _tmpAnswers = _cursor.getString(_cursorIndexOfAnswers);
            }
            _item = new QuizAttempt(_tmpId,_tmpChapterId,_tmpScore,_tmpTotalQuestions,_tmpTimestamp,_tmpAnswers);
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
  public Object getBestScoreForChapter(final int chapterId,
      final Continuation<? super Double> continuation) {
    final String _sql = "SELECT MAX(score * 1.0 / totalQuestions) FROM quiz_attempts WHERE chapterId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, chapterId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if(_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
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
  public Object getAverageScoreForSubject(final int subjectId,
      final Continuation<? super Double> continuation) {
    final String _sql = "SELECT AVG(score * 1.0 / totalQuestions) FROM quiz_attempts WHERE chapterId IN (SELECT id FROM chapters WHERE subjectId = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, subjectId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if(_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
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
