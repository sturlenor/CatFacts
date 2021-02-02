package no.stunor.catfacts.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import no.stunor.catfacts.BuildConfig
import no.stunor.catfacts.model.Cat
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory


class CatRepository {
    private val baseUrl = "https://api.thecatapi.com/"

    private var catApi: CatApi? = null
    private var catResponse: MutableLiveData<Cat>? = null


    init {
        catResponse =  MutableLiveData()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client =  OkHttpClient.Builder().addInterceptor(interceptor).build()
        catApi = retrofit2.Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatApi::class.java)
    }

    fun catFromApi() {
        catApi!!.getCat(BuildConfig.CAT_API_KEY)
            .enqueue(object : Callback<List<Cat>> {
            override fun onResponse(call: Call<List<Cat>>, response: Response<List<Cat>>) {
                if (response.body() != null) {
                    catResponse!!.postValue(response.body()!![0])
                }

            }

            @Override
            override fun onFailure(call: Call<List<Cat>>, t: Throwable) {
                catResponse = null
            }
        })
    }

    fun getCat(): LiveData<Cat>? {
        return catResponse
    }
}