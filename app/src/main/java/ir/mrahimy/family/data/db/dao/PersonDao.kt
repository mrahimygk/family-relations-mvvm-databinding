package ir.mrahimy.family.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ir.mrahimy.family.data.pojo.Person

@Dao
interface PersonDao : BaseDao<Person> {

    /**
     * Plural of person is people, not persons
     */
    @Query("SELECT * FROM people")
    fun getAll(): LiveData<List<Person>>

    /**
     * Plural of person is people, not persons
     */
    @Query("SELECT firstName FROM people")
    fun inferRelations(): LiveData<List<String>>
}