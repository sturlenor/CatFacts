package no.stunor.catfacts.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import no.stunor.catfacts.model.CatFact
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random



class CatFactRepository {
    private val baseUrl = "https://cat-fact.herokuapp.com/"

    private var catFactApi: CatFactApi? = null
    private var catFactResponse: MutableLiveData<CatFact>? = null


    init {
        catFactResponse =  MutableLiveData()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client =  OkHttpClient.Builder().addInterceptor(interceptor).build()
        catFactApi = retrofit2.Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatFactApi::class.java)
    }

    fun factsFromApi() {
        catFactApi!!.getFacts()
            .enqueue(object : Callback<List<CatFact>> {
            override fun onResponse(call: Call<List<CatFact>>, response: Response<List<CatFact>>) {
                if (response.body() != null) {
                    catFactResponse!!.postValue(response.body()!![Random.nextInt(response.body()!!.size)])
                }

            }

            @Override
            override fun onFailure(call: Call<List<CatFact>>, t: Throwable) {
                catFactResponse = null
            }
        })
    }

    fun getCatFacts(): LiveData<CatFact>? {
        return catFactResponse
    }
}