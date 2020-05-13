package ir.mrahimy.family.data.pojo

import com.google.gson.annotations.SerializedName
import ir.mrahimy.family.network.Exclude

data class Person(
    @SerializedName("firstName")
    val firstName: String,

    @SerializedName("lastName")
    val lastName: String
) {

    @Exclude
    var fullName = ""
}

fun Person.fillData(): Person {
    fullName = "$firstName $lastName"
    return this
}