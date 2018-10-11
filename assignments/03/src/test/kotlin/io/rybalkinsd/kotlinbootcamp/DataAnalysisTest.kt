package io.rybalkinsd.kotlinbootcamp

import junit.framework.TestCase.assertTrue
import org.junit.Test

class DataAnalysisTest {

    @Test
    fun `check avg age`() {
        assertTrue(avgAge.isNotEmpty())
    }

    @Test
    fun `check grouped profiles`() {
        assertTrue(groupedProfiles.isNotEmpty())
    }

    @Test
    fun `check grouped profiles are contain at least one element`() {
        assertTrue(getGroupedProfiles(getRawProfiles()).all { !it.value.isEmpty() })
    }

    @Test
    fun `check avg age2`() {
        val rawData = listOf<RawProfile>(
                RawProfile("""
                    age=10,
                    source=vk
                """.trimIndent()),
                RawProfile("""
                    age=20,
                    source=facebook
                """.trimIndent()),
                RawProfile("""
                    age=30,
                    source=linkedin
                """.trimIndent())
        )
        val sourceToAvg = getAvgAge(rawData)
        assertTrue((sourceToAvg[DataSource.VK] ?: 0) == 10.0)
        assertTrue((sourceToAvg[DataSource.FACEBOOK] ?: 0) == 20.0)
        assertTrue((sourceToAvg[DataSource.LINKEDIN] ?: 0) == 30.0)
    }
}
