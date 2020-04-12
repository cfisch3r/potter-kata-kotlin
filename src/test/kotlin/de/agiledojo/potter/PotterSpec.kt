package de.agiledojo.potter

import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk

class PotterSpec : StringSpec() {

    val singleBookPrice = 50

    val pricing = mockk<Pricing>()
    val bundler = mockk<Bundler>()

    override fun beforeTest(testCase: TestCase) {
        clearAllMocks()
        every {pricing.priceForBundle(1)} returns Price(singleBookPrice)
        every {bundler.bundles(BOOKS.I)} returns listOf(listOf(1))
        every {bundler.bundles(BOOKS.I,BOOKS.I)} returns listOf(listOf(1,1))
        every {bundler.bundles(BOOKS.I,BOOKS.I,BOOKS.II)} returns listOf(listOf(1,1,1),listOf(1,2))
    }

    init {
        "when purchased books are just one bundle return bundle price" {
            val potter = Potter(pricing,bundler)
            val price : Price = potter.priceFor(BOOKS.I)
            price.inCent shouldBe singleBookPrice
        }

        "when purchased books have multiple bundles return sum of bundle prices" {
            val potter = Potter(pricing,bundler)
            val price : Price = potter.priceFor(BOOKS.I,BOOKS.I)
            price.inCent shouldBe singleBookPrice + singleBookPrice
        }

        "when purchased books have multiple combinations of bundles return minimal price" {
            every {pricing.priceForBundle(2)} returns Price(80)
            val potter = Potter(pricing,bundler)
            val price : Price = potter.priceFor(BOOKS.I,BOOKS.I,BOOKS.II)
            price.inCent shouldBe 130
            every {pricing.priceForBundle(2)} returns Price(120)
            val otherprice : Price = potter.priceFor(BOOKS.I,BOOKS.I,BOOKS.II)
            otherprice.inCent shouldBe 150

        }
    }

}