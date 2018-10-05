package io.rybalkinsd.kotlinbootcamp.geometry

import kotlin.math.max
import kotlin.math.min

/**
 * Entity that can physically intersect, like flame and player
 */
interface Collider {
    fun isColliding(other: Collider): Boolean
}

/**
 * 2D point with integer coordinates
 */
data class Point(val x: Int, val y: Int) : Collider {
    override fun isColliding(other: Collider): Boolean {
        return when (other) {
            is Point -> other == this
            is Bar -> other.isContainsPoint(this)
            else -> false //TODO: exception
        }
    }
}

/**
 * Bar is a rectangle, which borders are parallel to coordinate axis
 * Like selection bar in desktop, this bar is defined by two opposite corners
 * Bar is not oriented
 * (It does not matter, which opposite corners you choose to define bar)
 */
class Bar(firstCornerX: Int, firstCornerY: Int, secondCornerX: Int, secondCornerY: Int) : Collider {
    val minX = min(firstCornerX, secondCornerX)
    val maxX = max(firstCornerX, secondCornerX)
    val minY = min(firstCornerY, secondCornerY)
    val maxY = max(firstCornerY, secondCornerY)
    override fun isColliding(other: Collider): Boolean {
        return when (other) {
            is Point -> isContainsPoint(other)
            is Bar -> isCollidingBar(other)
            else -> false
        }
    }

    fun isContainsPoint(point: Point): Boolean {
        return (point.x in minX..maxX && point.y in minY..maxX)
    }

    fun isCollidingBar(other: Bar): Boolean {
        if (minX > other.maxX || maxX < other.minX)
            return false
        if (minY > other.maxY || maxY < other.minY)
            return false
        return true
    }

    //auto generated
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Bar

        if (minX != other.minX) return false
        if (maxX != other.maxX) return false
        if (minY != other.minY) return false
        if (maxY != other.maxY) return false

        return true
    }

    override fun hashCode(): Int {
        var result = minX
        result = 31 * result + maxX
        result = 31 * result + minY
        result = 31 * result + maxY
        return result
    }
}