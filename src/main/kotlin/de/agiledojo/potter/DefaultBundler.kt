package de.agiledojo.potter

class DefaultBundler: Bundler {
    override fun bundles(books: List<BOOKS>): List<BundleCombination> {
        return variations(books.distinct(),books)
    }

    private fun variations(bundle: List<BOOKS>, books: List<BOOKS>): List<BundleCombination> {
        if (bundle.size == 1)
            return listOf(BundleCombination(List(books.size) {1}))

        val combinations = mutableListOf<BundleCombination>()
        combinations.add(combination(bundle, books))
        for (book in bundle)
            combinations.addAll(variations(bundle - book,books))

        return combinations
    }

    private fun combination(firstBundle: List<BOOKS>, books: List<BOOKS>): BundleCombination {
        val bundleSizes = mutableListOf<Int>(firstBundle.size)
        var remainingBooks = books.remove(firstBundle)
        while (remainingBooks.size > 0) {
            bundleSizes.add(remainingBooks.distinct().size)
            remainingBooks = remainingBooks.remove(remainingBooks.distinct())
        }
        val combination = BundleCombination(bundleSizes)
        return combination
    }

    fun<T> List<T>.remove(otherList: List<T>): List<T> {
        var rest = this
        otherList.forEach {
            rest -= it
        }
        return rest
    }

}
