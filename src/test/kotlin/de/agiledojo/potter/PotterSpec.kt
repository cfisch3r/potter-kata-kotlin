package de.agiledojo.potter

import io.kotest.core.spec.style.FreeSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk

class PotterSpec : FreeSpec() {

    val singleBookPrice = 50

    val pricing = mockk<Pricing>()
    val bundler = mockk<Bundler>()

    lateinit var potter: Potter

    override fun beforeTest(testCase: TestCase) {
        clearAllMocks()
        configurePricingStub()
        configureBundlerStub()
        potter = Potter(pricing, bundler)
    }

    init {
        "when books are just one bundle then return the bundle price" {
            val price = potter.priceFor(listOf(BOOKS.I))
            price.inCent shouldBe singleBookPrice
        }

        "when books have multiple bundles then return the sum of the bundle prices" {
            val price = potter.priceFor(listOf(BOOKS.I, BOOKS.I))
            price.inCent shouldBe singleBookPrice + singleBookPrice
        }

        "when books can be splitted into several bundle combinations then return the price for the cheapest combination" - {

            "choose two book bundle if it is cheaper than single book bundles" {
                setTwoBookBundlePrice(2 * singleBookPrice - 20)
                val price = potter.priceFor(listOf(BOOKS.I, BOOKS.I, BOOKS.II))
                price.inCent shouldBe 3 * singleBookPrice - 20
            }

            "choose single book bundles if two book bundle is more expensive" {
                setTwoBookBundlePrice(2 * singleBookPrice + 20)
                val price = potter.priceFor(listOf(BOOKS.I, BOOKS.I, BOOKS.II))
                price.inCent shouldBe 3 * singleBookPrice
            }
        }
    }

    private fun setTwoBookBundlePrice(bundlePrice: Int) {
        every { pricing.priceForBundle(2) } returns Price(bundlePrice)
    }

    private fun configurePricingStub() {
        every { pricing.priceForBundle(1) } returns Price(singleBookPrice)
    }

    private fun configureBundlerStub() {
        every { bundler.variations(listOf(BOOKS.I)) } returns setOf(BundleCombination(listOf(1)))
        every { bundler.variations(listOf(BOOKS.I, BOOKS.I)) } returns setOf(BundleCombination(listOf(1, 1)))
        every { bundler.variations(listOf(BOOKS.I, BOOKS.I, BOOKS.II)) } returns setOf(BundleCombination(listOf(1, 1, 1)),
                BundleCombination(listOf(1, 2)))
    }

}