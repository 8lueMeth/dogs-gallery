package com.example.dogbreeds.model

import android.os.Parcel
import android.os.Parcelable

sealed class Breed(open val name: String) : Parcelable {

    companion object : Parcelable.Creator<Breed> {

        override fun createFromParcel(source: Parcel): Breed? =
            when (source.readString()) {
                ParentBreed.TYPE -> ParentBreed(
                    source.readString() ?: "",
                    source.readParcelableArray(
                        SubBreed::class.java.classLoader
                    )?.filterIsInstance(SubBreed::class.java)?.toList() ?: emptyList()
                )
                SubBreed.TYPE -> SubBreed(
                    source.readString() ?: "",
                    source.readString() ?: ""
                )
                else -> null
            }

        override fun newArray(size: Int): Array<Breed?> = Array(size) { null }
    }

}

data class ParentBreed(
    override val name: String,
    val subBreeds: List<SubBreed>,
) : Breed(name) {

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(TYPE)
        parcel.writeString(name)
        parcel.writeParcelableArray(subBreeds.toTypedArray(), 0)
    }

    override fun describeContents(): Int = 0

    companion object {
        const val TYPE = "parent"

        @JvmField
        val CREATOR: Parcelable.Creator<Breed> = Breed
    }
}

data class SubBreed(
    override val name: String,
    val parentName: String,
) : Breed(name) {

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(TYPE)
        parcel.writeString(name)
        parcel.writeString(parentName)
    }

    override fun describeContents(): Int = 0

    companion object {
        const val TYPE = "sub"

        @JvmField
        val CREATOR: Parcelable.Creator<Breed> = Breed
    }
}