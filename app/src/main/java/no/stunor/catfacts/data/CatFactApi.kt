package no.stunor.catfacts.data
import no.stunor.catfacts.model.CatFact
import retrofit2.Call
import retrofit2.http.GET

interface CatFactApi {
    @GET("facts")
    fun getFacts(): Call<List<CatFact>>
}