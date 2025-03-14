    package com.example.contact

    import android.os.Parcel
    import android.os.Parcelable

    data class Contact(
        val name:String,
        val phone: String,
        val address: String,
        val email: String,
        val linkedIn: String,


    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
            parcel.writeString(phone)
            parcel.writeString(address)
            parcel.writeString(email)
            parcel.writeString(linkedIn)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Contact> {
            override fun createFromParcel(parcel: Parcel): Contact {
                return Contact(parcel)
            }

            override fun newArray(size: Int): Array<Contact?> {
                return arrayOfNulls(size)
            }
        }
    }
