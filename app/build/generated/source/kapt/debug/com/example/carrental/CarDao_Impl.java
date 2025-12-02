package com.example.carrental;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CarDao_Impl implements CarDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Car> __insertionAdapterOfCar;

  private final EntityDeletionOrUpdateAdapter<Car> __deletionAdapterOfCar;

  public CarDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCar = new EntityInsertionAdapter<Car>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `cars` (`id`,`name`,`pricePerDay`,`imageRes`,`km`,`fuelType`,`seats`,`transmission`,`vehicleType`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Car entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getPricePerDay() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getPricePerDay());
        }
        statement.bindLong(4, entity.getImageRes());
        if (entity.getKm() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getKm());
        }
        if (entity.getFuelType() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getFuelType());
        }
        statement.bindLong(7, entity.getSeats());
        if (entity.getTransmission() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getTransmission());
        }
        statement.bindString(9, __VehicleType_enumToString(entity.getVehicleType()));
      }
    };
    this.__deletionAdapterOfCar = new EntityDeletionOrUpdateAdapter<Car>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `cars` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Car entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
      }
    };
  }

  @Override
  public Object insertCar(final Car car, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCar.insert(car);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteCar(final Car car, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfCar.handle(car);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllCars(final Continuation<? super List<Car>> $completion) {
    final String _sql = "SELECT * FROM cars";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Car>>() {
      @Override
      @NonNull
      public List<Car> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPricePerDay = CursorUtil.getColumnIndexOrThrow(_cursor, "pricePerDay");
          final int _cursorIndexOfImageRes = CursorUtil.getColumnIndexOrThrow(_cursor, "imageRes");
          final int _cursorIndexOfKm = CursorUtil.getColumnIndexOrThrow(_cursor, "km");
          final int _cursorIndexOfFuelType = CursorUtil.getColumnIndexOrThrow(_cursor, "fuelType");
          final int _cursorIndexOfSeats = CursorUtil.getColumnIndexOrThrow(_cursor, "seats");
          final int _cursorIndexOfTransmission = CursorUtil.getColumnIndexOrThrow(_cursor, "transmission");
          final int _cursorIndexOfVehicleType = CursorUtil.getColumnIndexOrThrow(_cursor, "vehicleType");
          final List<Car> _result = new ArrayList<Car>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Car _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpPricePerDay;
            if (_cursor.isNull(_cursorIndexOfPricePerDay)) {
              _tmpPricePerDay = null;
            } else {
              _tmpPricePerDay = _cursor.getString(_cursorIndexOfPricePerDay);
            }
            final int _tmpImageRes;
            _tmpImageRes = _cursor.getInt(_cursorIndexOfImageRes);
            final String _tmpKm;
            if (_cursor.isNull(_cursorIndexOfKm)) {
              _tmpKm = null;
            } else {
              _tmpKm = _cursor.getString(_cursorIndexOfKm);
            }
            final String _tmpFuelType;
            if (_cursor.isNull(_cursorIndexOfFuelType)) {
              _tmpFuelType = null;
            } else {
              _tmpFuelType = _cursor.getString(_cursorIndexOfFuelType);
            }
            final int _tmpSeats;
            _tmpSeats = _cursor.getInt(_cursorIndexOfSeats);
            final String _tmpTransmission;
            if (_cursor.isNull(_cursorIndexOfTransmission)) {
              _tmpTransmission = null;
            } else {
              _tmpTransmission = _cursor.getString(_cursorIndexOfTransmission);
            }
            final VehicleType _tmpVehicleType;
            _tmpVehicleType = __VehicleType_stringToEnum(_cursor.getString(_cursorIndexOfVehicleType));
            _item = new Car(_tmpId,_tmpName,_tmpPricePerDay,_tmpImageRes,_tmpKm,_tmpFuelType,_tmpSeats,_tmpTransmission,_tmpVehicleType);
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

  @Override
  public Object getCarById(final String id, final Continuation<? super Car> $completion) {
    final String _sql = "SELECT * FROM cars WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Car>() {
      @Override
      @Nullable
      public Car call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPricePerDay = CursorUtil.getColumnIndexOrThrow(_cursor, "pricePerDay");
          final int _cursorIndexOfImageRes = CursorUtil.getColumnIndexOrThrow(_cursor, "imageRes");
          final int _cursorIndexOfKm = CursorUtil.getColumnIndexOrThrow(_cursor, "km");
          final int _cursorIndexOfFuelType = CursorUtil.getColumnIndexOrThrow(_cursor, "fuelType");
          final int _cursorIndexOfSeats = CursorUtil.getColumnIndexOrThrow(_cursor, "seats");
          final int _cursorIndexOfTransmission = CursorUtil.getColumnIndexOrThrow(_cursor, "transmission");
          final int _cursorIndexOfVehicleType = CursorUtil.getColumnIndexOrThrow(_cursor, "vehicleType");
          final Car _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpPricePerDay;
            if (_cursor.isNull(_cursorIndexOfPricePerDay)) {
              _tmpPricePerDay = null;
            } else {
              _tmpPricePerDay = _cursor.getString(_cursorIndexOfPricePerDay);
            }
            final int _tmpImageRes;
            _tmpImageRes = _cursor.getInt(_cursorIndexOfImageRes);
            final String _tmpKm;
            if (_cursor.isNull(_cursorIndexOfKm)) {
              _tmpKm = null;
            } else {
              _tmpKm = _cursor.getString(_cursorIndexOfKm);
            }
            final String _tmpFuelType;
            if (_cursor.isNull(_cursorIndexOfFuelType)) {
              _tmpFuelType = null;
            } else {
              _tmpFuelType = _cursor.getString(_cursorIndexOfFuelType);
            }
            final int _tmpSeats;
            _tmpSeats = _cursor.getInt(_cursorIndexOfSeats);
            final String _tmpTransmission;
            if (_cursor.isNull(_cursorIndexOfTransmission)) {
              _tmpTransmission = null;
            } else {
              _tmpTransmission = _cursor.getString(_cursorIndexOfTransmission);
            }
            final VehicleType _tmpVehicleType;
            _tmpVehicleType = __VehicleType_stringToEnum(_cursor.getString(_cursorIndexOfVehicleType));
            _result = new Car(_tmpId,_tmpName,_tmpPricePerDay,_tmpImageRes,_tmpKm,_tmpFuelType,_tmpSeats,_tmpTransmission,_tmpVehicleType);
          } else {
            _result = null;
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

  private String __VehicleType_enumToString(@NonNull final VehicleType _value) {
    switch (_value) {
      case CAR: return "CAR";
      case SUV: return "SUV";
      case TRUCK: return "TRUCK";
      case VAN: return "VAN";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private VehicleType __VehicleType_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "CAR": return VehicleType.CAR;
      case "SUV": return VehicleType.SUV;
      case "TRUCK": return VehicleType.TRUCK;
      case "VAN": return VehicleType.VAN;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}
