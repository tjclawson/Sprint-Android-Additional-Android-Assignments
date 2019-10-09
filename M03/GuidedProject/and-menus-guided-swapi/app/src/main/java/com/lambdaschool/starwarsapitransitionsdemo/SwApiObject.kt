package com.lambdaschool.starwarsapitransitionsdemo

import java.io.Serializable

abstract class SwApiObject(id: Int, name: String) : Serializable {
    var id: Int = 0
        protected set
    var category: String? = null
    var name: String
        protected set

    init {
        this.id = id
        this.name = name
    }

    abstract override fun toString(): String

    companion object {
        // TODO 8b: Move comparison to the object

        fun compareNames(p0: SwApiObject, p1: SwApiObject): Int {
            return p0.name.compareTo(p1.name)
        }
    }
}
