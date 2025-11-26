package com.example.carrental;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
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

@SuppressWarnings({"unchecked", "deprecation"})
public final class BookingDao_Impl implements BookingDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Booking> __insertionAdapterOfBooking;

  private final EntityDeletionOrUpdateAdapter<Booking> __deletionAdapterOfBooking;

  public BookingDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBooking = new EntityInsertionAdapter<Booking>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `bookings` (`id`,`userId`,`carId`,`carName`,`pickupLocation`,`dropoffLocation`,`pickupDate`,`returnDate`,`totalCost`,`status`,`createdAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Booking entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getUserId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getUserId());
        }
        if (entity.getCarId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getCarId());
        }
        if (entity.getCarName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getCarName());
        }
        if (entity.getPickupLocation() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getPickupLocation());
        }
        if (entity.getDropoffLocation() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getDropoffLocation());
        }
        if (entity.getPickupDate() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getPickupDate());
        }
        if (entity.getReturnDate() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getReturnDate());
        }
        statement.bindLong(9, entity.getTotalCost());
        if (entity.getStatus() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getStatus());
        }
        statement.bindLong(11, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfBooking = new EntityDeletionOrUpdateAdapter<Booking>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `bookings` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Booking entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
      }
    };
  }

  @Override
  public Object insertBooking(final Booking booking, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfBooking.insert(booking);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteBooking(final Booking booking, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfBooking.handle(booking);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getBookingsByUser(final String userId,
      final Continuation<? super List<Booking>> $completion) {
    final String _sql = "SELECT * FROM bookings WHERE userId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (userId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Booking>>() {
      @Override
      @NonNull
      public List<Booking> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfCarId = CursorUtil.getColumnIndexOrThrow(_cursor, "carId");
          final int _cursorIndexOfCarName = CursorUtil.getColumnIndexOrThrow(_cursor, "carName");
          final int _cursorIndexOfPickupLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "pickupLocation");
          final int _cursorIndexOfDropoffLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "dropoffLocation");
          final int _cursorIndexOfPickupDate = CursorUtil.getColumnIndexOrThrow(_cursor, "pickupDate");
          final int _cursorIndexOfReturnDate = CursorUtil.getColumnIndexOrThrow(_cursor, "returnDate");
          final int _cursorIndexOfTotalCost = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCost");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Booking> _result = new ArrayList<Booking>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Booking _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpUserId;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUserId = null;
            } else {
              _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            }
            final String _tmpCarId;
            if (_cursor.isNull(_cursorIndexOfCarId)) {
              _tmpCarId = null;
            } else {
              _tmpCarId = _cursor.getString(_cursorIndexOfCarId);
            }
            final String _tmpCarName;
            if (_cursor.isNull(_cursorIndexOfCarName)) {
              _tmpCarName = null;
            } else {
              _tmpCarName = _cursor.getString(_cursorIndexOfCarName);
            }
            final String _tmpPickupLocation;
            if (_cursor.isNull(_cursorIndexOfPickupLocation)) {
              _tmpPickupLocation = null;
            } else {
              _tmpPickupLocation = _cursor.getString(_cursorIndexOfPickupLocation);
            }
            final String _tmpDropoffLocation;
            if (_cursor.isNull(_cursorIndexOfDropoffLocation)) {
              _tmpDropoffLocation = null;
            } else {
              _tmpDropoffLocation = _cursor.getString(_cursorIndexOfDropoffLocation);
            }
            final String _tmpPickupDate;
            if (_cursor.isNull(_cursorIndexOfPickupDate)) {
              _tmpPickupDate = null;
            } else {
              _tmpPickupDate = _cursor.getString(_cursorIndexOfPickupDate);
            }
            final String _tmpReturnDate;
            if (_cursor.isNull(_cursorIndexOfReturnDate)) {
              _tmpReturnDate = null;
            } else {
              _tmpReturnDate = _cursor.getString(_cursorIndexOfReturnDate);
            }
            final int _tmpTotalCost;
            _tmpTotalCost = _cursor.getInt(_cursorIndexOfTotalCost);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Booking(_tmpId,_tmpUserId,_tmpCarId,_tmpCarName,_tmpPickupLocation,_tmpDropoffLocation,_tmpPickupDate,_tmpReturnDate,_tmpTotalCost,_tmpStatus,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
