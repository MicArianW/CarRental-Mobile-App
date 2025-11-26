package com.example.carrental;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&\u00a8\u0006\t"}, d2 = {"Lcom/example/carrental/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "bookingDao", "Lcom/example/carrental/BookingDao;", "carDao", "Lcom/example/carrental/CarDao;", "userDao", "Lcom/example/carrental/UserDao;", "app_debug"})
@androidx.room.Database(entities = {com.example.carrental.Car.class, com.example.carrental.User.class, com.example.carrental.Booking.class, com.example.carrental.PaymentMethod.class}, version = 1, exportSchema = false)
@androidx.room.TypeConverters(value = {com.example.carrental.Converters.class})
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.carrental.CarDao carDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.carrental.UserDao userDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.carrental.BookingDao bookingDao();
}