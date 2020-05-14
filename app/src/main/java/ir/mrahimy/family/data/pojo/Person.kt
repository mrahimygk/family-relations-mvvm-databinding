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

@Parcelize
data class PersonRelations(
    val person: Person,
    val relatedPeople: List<Person>
) : Parcelable {
    @Exclude
    @IgnoredOnParcel
    var relationText = ""
}

fun PersonRelations.fillText(): PersonRelations {
    relationText = StringBuilder().apply {
        append(person.firstName)
        append(" ")
        append("is related to")
        append(" ")
        if (relatedPeople.isEmpty())
            append("no one")
        else {
            relatedPeople.firstOrNull()?.let { p -> append(p.firstName) }
            relatedPeople.drop(1).forEachIndexed { index, p ->
                append(if (index == relatedPeople.size - 2) " & " else ", ")
                append(p.firstName)
            }
        }
        append(".")
    }.toString()

    return this
}