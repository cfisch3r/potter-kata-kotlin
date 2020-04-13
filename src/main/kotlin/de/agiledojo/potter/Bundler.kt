package de.agiledojo.potter

interface Bundler {
    fun bundles(books: List<BOOKS>) : List<BundleCombination>
}