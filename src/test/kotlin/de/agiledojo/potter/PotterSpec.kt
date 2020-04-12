package de.agiledojo.potter

import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.TestCase
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk

class PotterSpec : StringSpec() {

    val singleBookPrice = 50

    val pricing = mockk<Pricing>()
    val bundler = mockk<Bundler>()

    lateinit var  potter: Potter

    override fun beforeTest(testCase: TestCase) {
        clearAllMocks()
        configurePricingStub()
        configureBundlerStub()
        potter = Potter(pricing,bundler)
    }

    init {
        "when books are just one bundle then return then bundle price" {
            val price : Price = potter.priceFor(BOOKS.I)
            price.inCent shouldBe singleBookPrice
        }

        "when books have multiple bundles then return the sum of the bundle prices" {
            val price : Price = potter.priceFor(BOOKS.I,BOOKS.I)
            price.inCent shouldBe singleBookPrice + singleBookPrice
        }

        listOf(
                row("two book bundle is cheaper than single book bundles", 2*singleBookPrice-20,
                        3*singleBookPrice-20),
                row("two book bundle is more expensive than single book bundles", 2*singleBookPrice+20,
                        3*singleBookPrice)
        ).map {(description: String, twoBookPrice: Int, bestPriceInCent: Int) ->
            "when books can be splitted into several bundle combinations then return the price for the cheapest bundle combination, i.e.: $description" {
                every {pricing.priceForBundle(2)} returns Price(twoBookPrice)
                val price : Price = potter.priceFor(BOOKS.I,BOOKS.I,BOOKS.II)
                price.inCent shouldBe bestPriceInCent
            }
        }
    }

    private fun configurePricingStub() {
        every { pricing.priceForBundle(1) } returns Price(singleBookPrice)
    }

    private fun configureBundlerStub() {
        every { bundler.bundles(BOOKS.I) } returns listOf(listOf(1))
        every { bundler.bundles(BOOKS.I, BOOKS.I) } returns listOf(listOf(1, 1))
        every { bundler.bundles(BOOKS.I, BOOKS.I, BOOKS.II) } returns listOf(listOf(1, 1, 1), listOf(1, 2))
    }

}