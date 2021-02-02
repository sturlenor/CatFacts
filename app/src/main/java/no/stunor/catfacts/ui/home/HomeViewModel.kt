package no.stunor.catfacts.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import no.stunor.catfacts.data.CatFactRepository
import no.stunor.catfacts.data.CatRepository
import no.stunor.catfacts.model.Cat
import no.stunor.catfacts.model.CatFact


class HomeViewModel : ViewModel() {
    private var catFactRepository: CatFactRepository? = null
    private var catFactLiveData: LiveData<CatFact>? = null

    private var catRepository: CatRepository? = null
    private var catLiveData: LiveData<Cat>? = null


    init {
        catFactRepository = CatFactRepository()
        catRepository = CatRepository()

        catFactLiveData = catFactRepository!!.getCatFacts()
        catLiveData = catRepository!!.getCat()

    }

    fun getRandomCat() {
        catFactRepository!!.factsFromApi()
        catRepository!!.catFromApi()

    }

    fun getCatFacts(): LiveData<CatFact>? {
        return catFactLiveData
    }

    fun getCat(): LiveData<Cat>? {
        return catLiveData
    }
}