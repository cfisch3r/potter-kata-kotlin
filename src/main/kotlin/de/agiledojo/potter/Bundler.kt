package de.agiledojo.potter

interface Bundler {
    fun variations(books: List<BOOKS>) : Set<BundleCombination>
}