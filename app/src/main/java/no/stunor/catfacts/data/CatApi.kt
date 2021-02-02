package no.stunor.catfacts.data
import no.stunor.catfacts.model.Cat
import no.stunor.catfacts.model.CatFact
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface CatApi {
    @GET("v1/images/search")
    fun getCat(@Header("x-api-key") apiKey: String): Call<List<Cat>>

}