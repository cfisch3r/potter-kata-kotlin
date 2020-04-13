package de.agiledojo.potter

class Potter (private val pricing: Pricing, private val bundler: Bundler) {

    fun priceFor(books: List<BOOKS>) =
        bundler.bundles(books).map(this::toCombinationPrice).min() ?: Price(0)

    private fun toCombinationPrice(combination: BundleCombination) =
        combination.bundleSizes
                .map(pricing::priceForBundle)
                .fold(Price(0)) {sum, price -> sum + price}

}
