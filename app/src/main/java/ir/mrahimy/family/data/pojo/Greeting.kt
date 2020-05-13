package ir.mrahimy.family.data.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Greeting(
    val id: Int,
    val title: String
) : Parcelable {

}