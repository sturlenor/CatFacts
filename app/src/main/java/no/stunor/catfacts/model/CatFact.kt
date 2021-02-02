package no.stunor.catfacts.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CatFact (
    @SerializedName("text")
    @Expose
    var text: String? = null
)

