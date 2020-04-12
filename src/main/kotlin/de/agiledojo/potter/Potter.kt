package de.agiledojo.potter

class Potter (private val pricing: Pricing, private val bundler: Bundler) {

    fun priceFor(vararg books: BOOKS): Price {
        val min = bundler.bundles(*books).map {
            it.map(pricing::priceForBundle)
                    .map(Price::inCent)
                    .sum()
        }.min()
        return Price(min ?: 0)
    }

}
