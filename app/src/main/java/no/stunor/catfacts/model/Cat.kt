package no.stunor.catfacts.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Cat (
    @SerializedName("url")
    @Expose
    var url: String? = null
)

