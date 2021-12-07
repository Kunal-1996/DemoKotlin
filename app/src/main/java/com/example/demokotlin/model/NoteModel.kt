package com.example.demokotlin.model

import android.os.Parcelable
import com.example.demokotlin.utilities.getRandomNumber
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
data class NoteModel(
    val id : Int = getRandomNumber(),
    val title : String = "",
    val note : String = "",
    val color : String = "#ffffff",
    val creationDate : Date = Date(),
    val modifiedDate : Date = Date(),
): Parcelable