package anagram.storage

interface Storage {

    fun addMany(key: String,  vararg values: String)
    fun getWithFilter(criteria: GetFilterCriteria): List<String> 
}
