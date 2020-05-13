package ir.mrahimy.family.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.mrahimy.family.data.db.dao.PersonDao
import ir.mrahimy.family.data.pojo.Person

@Database(
    entities = [Person::class],
    version = 1,
    exportSchema = false
)
abstract class FamilyDb : RoomDatabase() {
    abstract fun personDao(): PersonDao
}