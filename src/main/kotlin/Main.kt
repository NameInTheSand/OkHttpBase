package org.example

import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import okio.IOException

data class Author(
    val name: String,
    val age: Int,
    val books: List<Book>,
    val isAlive: Boolean,
)

data class Book(
    val name: String,
    val year: Int
)

fun main() {
    val gson = GsonBuilder()
        .registerTypeAdapter(Author::class.java, AuthorAdapter())
        .create()
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
    val person = gson.fromJson(jsonString, Author::class.java)
    println(gson.toJson(person))
}

class AuthorAdapter : TypeAdapter<Author>() {

    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: Author?) {
        if (value == null) {
            out.nullValue()
            return
        }

        out.beginObject()
        out.name("name").value(value.name)
        out.name("age").value(value.age)
        out.name("books").beginArray()
        value.books.forEach { book: Book ->
            out.beginObject()
            out.name("name").value(book.name)
            out.name("year").value(book.year)
            out.endObject()
        }
        out.endArray()
        out.name("isAlive").value(value.isAlive)
        out.endObject()
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): Author? {
        if (`in`.peek() == JsonToken.NULL) {
            `in`.nextNull()
            return null
        }
        `in`.beginObject()
        var name: String? = null
        var age = 0
        val books = mutableListOf<Book>()
        var isAlive = false
        while (`in`.hasNext()) {
            when (`in`.nextName()) {
                "name" -> name = `in`.nextString()
                "age" -> age = `in`.nextInt()
                "books" -> {
                    `in`.beginArray()
                    while (`in`.hasNext()) {
                        `in`.beginObject()
                        var bookName: String? = null
                        var year = 0
                        while (`in`.hasNext()) {
                            when (`in`.nextName()) {
                                "name" -> bookName = `in`.nextString()
                                "year" -> year = `in`.nextInt()
                            }
                        }
                        `in`.endObject()
                        if (bookName != null) books.add(Book(bookName, year))
                    }
                    `in`.endArray()
                }

                "isAlive" -> isAlive = `in`.nextBoolean()
            }
        }
        `in`.endObject()
        return if (name == null) {
            null
        } else {
            Author(name = name, age = age, books = books, isAlive = isAlive)
        }
    }

}