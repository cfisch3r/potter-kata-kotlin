package de.agiledojo.potter

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe

class BundlerSpec: FreeSpec() {

    init {
        "single book are one single book bundle" {
            val defaultBundler = DefaultBundler()
            val combinations = defaultBundler.bundles(listOf(BOOKS.I))
            combinations shouldContainExactly listOf(BundleCombination(listOf(1)))
        }

        "two identical books are two single book bundles" {
            val defaultBundler = DefaultBundler()
            val combinations = defaultBundler.bundles(listOf(BOOKS.I,BOOKS.I))
            combinations shouldContainExactly listOf(BundleCombination(listOf(1,1)))
        }

        "series of two books can be bundled as series or single books" {
            val defaultBundler = DefaultBundler()
            val combinations = defaultBundler.bundles(listOf(BOOKS.I,BOOKS.II))
            combinations shouldContainAll  listOf(BundleCombination(listOf(1,1)),BundleCombination(listOf(2)))
        }

        "multiple identical series can be bundled as multiple series or single books" {
            val defaultBundler = DefaultBundler()
            val combinations = defaultBundler.bundles(listOf(BOOKS.I,BOOKS.II,BOOKS.I,BOOKS.II))
            combinations shouldContainAll  listOf(BundleCombination(listOf(1,1,1,1)),BundleCombination(listOf(2,2)))
        }

        "a combination of series can be bundled as series or single books" {
            val defaultBundler = DefaultBundler()
            val combinations = defaultBundler.bundles(listOf(BOOKS.I,BOOKS.II,BOOKS.I))
            combinations shouldContainAll  listOf(BundleCombination(listOf(1,1,1)),BundleCombination(listOf(2,1)))
        }

        "multiple combinations" {
            val defaultBundler = DefaultBundler()
            val combinations = defaultBundler.bundles(listOf(BOOKS.I,BOOKS.II,BOOKS.II,BOOKS.III))
            combinations shouldContainAll  listOf(BundleCombination(listOf(1,1,1,1)),BundleCombination(listOf(3,1)),
                    BundleCombination(listOf(2,2)),BundleCombination(listOf(2,1,1)))
        }
    }
}