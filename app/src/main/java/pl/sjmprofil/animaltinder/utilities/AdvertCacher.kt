package pl.sjmprofil.animaltinder.utilities

import pl.sjmprofil.animaltinder.models.Advert

object AdvertCacher {

    private var cachedAdvert: MutableList<Advert> = mutableListOf()

    fun setNewCached(advert: Advert) {
        cachedAdvert.add(advert)
    }

    fun retrieveCached(): Advert? {
        if (cachedAdvert.isEmpty()) return null

        val lastElement = cachedAdvert.last()
        cachedAdvert.remove(lastElement)
        return lastElement
    }
}