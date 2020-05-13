package ir.mrahimy.family.data.pojo

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import com.google.gson.annotations.SerializedName
import ir.mrahimy.family.network.Exclude
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Entity(
    tableName = "people",
    primaryKeys = ["firstName", "lastName"]
)
@Parcelize
data class Person(
    @SerializedName("firstName")
    @ColumnInfo(name = "firstName")
    val firstName: String,

    @SerializedName("lastName")
    @ColumnInfo(name = "lastName")
    val lastName: String
) : Parcelable {

    @Exclude
    @Ignore
    @IgnoredOnParcel
    var fullName = ""
}

fun Person.fillData(): Person {
    fullName = "$firstName $lastName"
    return this
}