package org.example

import com.google.gson.Gson
import com.google.gson.annotations.Expose

data class Author(
    val name: String,
    val age: Int,
    val books: List<Book>,
    val isAlive:Boolean,
    @Expose
    val ignored:String = "Ignored"
)

data class Book(
    val name: String,
    val year: Int
)

fun main() {
    val gson = Gson()
    val jsonString = """
        {
            "name":"John",
            "age":42,
            "books":[
                {"name":"Book 1", year:1999}, {"name":"Book 2", year:1989},{"name":"Book 3", year:1969}
            ],
            "isAlive":true
        }
    """.trimIndent()

    val author = gson.fromJson(jsonString, Author::class.java)

    println(author.name)
    println(author.age)
    println(author.isAlive)
    println(author.ignored)
    author.books.forEach {
        println(it)
    }

}