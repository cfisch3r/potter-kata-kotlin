package de.agiledojo.potter

interface Pricing {
    fun priceForBundle(bundleSize: Int): Price
}