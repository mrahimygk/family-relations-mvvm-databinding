package ir.mrahimy.family.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import ir.mrahimy.family.data.pojo.Person

@Dao
interface PersonDao : BaseDao<Person> {

    @Query("SELECT * FROM people")
    suspend fun getAll()
}