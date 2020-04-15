package de.agiledojo.potter

class DefaultBundler: Bundler {
    override fun variations(books: List<BOOKS>): Set<BundleCombination> {
        return variations(books.distinct(),books)
    }

    private fun variations(rootBundle: List<BOOKS>, books: List<BOOKS>): Set<BundleCombination> {
        if (rootBundle.size == 1)
            return setOf(BundleCombination(List(books.size) {1}))

        val combinations = mutableSetOf<BundleCombination>()

        val restBooks = books.remove(rootBundle)
        if (restBooks.size == 0)
            combinations.add(BundleCombination(listOf(rootBundle.size)))
        else {
            variations(restBooks).forEach {
                combinations.add(BundleCombination(it.bundleSizes + rootBundle.size))
            }
        }

        for (book in rootBundle)
            combinations.addAll(variations(rootBundle - book, books))

        return combinations
    }



    fun<T> List<T>.remove(otherList: List<T>): List<T> {
        var rest = this
        otherList.forEach {
            rest -= it
        }
        return rest
    }

}
