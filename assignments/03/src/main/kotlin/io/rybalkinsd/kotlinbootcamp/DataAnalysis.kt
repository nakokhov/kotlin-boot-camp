package io.rybalkinsd.kotlinbootcamp

class RawProfile(val rawData: String)

enum class DataSource {
    FACEBOOK,
    VK,
    LINKEDIN
}

sealed class Profile(
    var id: Long,
    var firstName: String? = null,
    var lastName: String? = null,
    var age: Int? = null,
    var dataSource: DataSource
)

/**
 * Task #1
 * Declare classes for all data sources
 */
class FacebookProfile(id: Long) : Profile(dataSource = DataSource.FACEBOOK, id = id)

class LinkedinProfile(id: Long) : Profile(dataSource = DataSource.LINKEDIN, id = id)
class VkProfile(id: Long) : Profile(dataSource = DataSource.VK, id = id)

fun RawProfile.toProfile(id: Long): Profile? {
    val dictData = rawData.split(",\n")
            .map { it.split("=") }
            .filter { it.size == 2 }
            .associate { it[0] to it[1] }

    return when (dictData["source"]) {
        "vk" -> VkProfile(id)
        "facebook" -> FacebookProfile(id)
        "linkedin" -> LinkedinProfile(id)
        else -> null
    }?.apply {
        firstName = dictData["fisrtName"]
        lastName = dictData["lastName"]
        age = dictData["age"]?.toIntOrNull()
    }
}

/**
 * Task #2
 * Find the average age for each datasource (for profiles that has age)
 *
 * TODO
 */
val avgAge: Map<DataSource, Double> = getAvgAge(getRawProfiles())

fun getAvgAge(profiles: Collection<RawProfile>): Map<DataSource, Double> {
    val dataSourceToProfile = makeProfiles(profiles).groupBy({ it.dataSource }, { it.age })
    return dataSourceToProfile.map { it.key to it.value.filterNotNull().average() }.toMap()
}

/**
 * Task #3
 * Group all user ids together with all profiles of this user.
 * We can assume users equality by : firstName & lastName & age
 *
 * TODO
 */

fun getGroupedProfiles(profiles: Collection<RawProfile>): Map<Long, List<Profile>> {
    val profiles = makeProfiles(profiles)
    val isSameProfile = { a: Profile, b: Profile ->
        a.firstName?.capitalize() == b.firstName?.capitalize() &&
                a.lastName?.capitalize() == b.lastName?.capitalize() &&
                a.age == b.age
    }
    return profiles.associate { p -> p.id to profiles.filter { isSameProfile(p, it) } }
}

val groupedProfiles: Map<Long, List<Profile>> = getGroupedProfiles(getRawProfiles())

/**
 * Here are Raw profiles to analyse
 */

fun getRawProfiles() = listOf(
        RawProfile("""
            fisrtName=alice,
            age=16,
            source=facebook
            """.trimIndent()
        ),
        RawProfile("""
            fisrtName=Dent,
            lastName=kent,
            age=88,
            source=linkedin
            """.trimIndent()
        ),
        RawProfile("""
            fisrtName=alla,
            lastName=OloOlooLoasla,
            source=vk
            """.trimIndent()
        ),
        RawProfile("""
            fisrtName=bella,
            age=36,
            source=vk
            """.trimIndent()
        ),
        RawProfile("""
            fisrtName=angel,
            age=I will not tell you =),
            source=facebook
            """.trimIndent()
        ),

        RawProfile(
                """
            lastName=carol,
            source=vk
            age=49,
            """.trimIndent()
        ),
        RawProfile("""
            fisrtName=bob,
            lastName=John,
            age=47,
            source=linkedin
            """.trimIndent()
        ),
        RawProfile("""
            lastName=kent,
            fisrtName=dent,
            age=88,
            source=facebook
            """.trimIndent()
        )
)

fun makeProfiles(source: Collection<RawProfile>): List<Profile> {
    var id = 0L
    @Suppress("UNCHECKED_CAST")
    return source.map { it.toProfile(id)?.also { id++ } }.filter { it != null } as List<Profile>
}
