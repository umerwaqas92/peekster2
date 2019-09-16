 package com.peekster.model

class Movies_model{


    internal var id: Int = 0
    internal var name: String=""
    internal var tumbnails: Int = 0
    internal var time: Long = 0
    internal var path: String=""
    internal var watch_length: Long = 0
    internal var watch_time: Int = 0
    internal var title: String=""
    internal var description: String=""
    internal var rating: Double? = null
    internal var year: Int = 0
    internal var category: Int = 0
    internal var length: Long = 0


    fun Movies_model(
        id: Int, name: String, tumbnails: Int, time: Long, path: String,
        watch_length: Long, watch_time: Int, title: String, description: String,
        rating: Double?, year: Int, category: Int, length: Long
    ) {
         this.id = id
        this.name = name
        this.tumbnails = tumbnails
        this.time = time
        this.path = path
        this.watch_length = watch_length
        this.watch_time = watch_time
        this.title = title
        this.description = description
        this.rating = rating
        this.year = year
        this.category = category
        this.length = length


    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getTumbnails(): Int {
        return tumbnails
    }

    fun setTumbnails(tumbnails: Int) {
        this.tumbnails = tumbnails
    }

    fun getTime(): Long {
        return time
    }

    fun setTime(time: Long) {
        this.time = time
    }

    fun getPath(): String {
        return path
    }

    fun setPath(path: String) {
        this.path = path
    }

    fun getWatch_length(): Long {
        return watch_length
    }

    fun setWatch_length(watch_length: Long) {
        this.watch_length = watch_length
    }

    fun getWatch_time(): Int {
        return watch_time
    }

    fun setWatch_time(watch_time: Int) {
        this.watch_time = watch_time
    }

    fun getTitle(): String {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun getDescription(): String {
        return description
    }

    fun setDescription(description: String) {
        this.description = description
    }

    fun getRating(): Double? {
        return rating
    }

    fun setRating(rating: Double?) {
        this.rating = rating
    }

    fun getYear(): Int {
        return year
    }

    fun setYear(year: Int) {
        this.year = year
    }

    fun getCategory(): Int {
        return category
    }

    fun setCategory(category: Int) {
        this.category = category
    }

    fun getLength(): Long {
        return length
    }

    fun setLength(length: Long) {
        this.length = length
    }


}
