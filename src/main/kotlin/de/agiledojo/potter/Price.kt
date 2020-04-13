package de.agiledojo.potter

import java.util.Comparator.comparingInt

data class Price(val inCent: Int): Comparable<Price> {

    companion object {
        private val COMPARATOR =
                comparingInt<Price> { it.inCent }
    }

    operator fun plus(price: Price): Price {
        return Price(inCent + price.inCent)
    }

    override fun compareTo(other: Price): Int {
        return COMPARATOR.compare(this,other)
    }
}
