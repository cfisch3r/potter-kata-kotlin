package de.agiledojo.potter

interface Bundler {

    fun bundles(vararg books: BOOKS) : List<List<Int>>
}