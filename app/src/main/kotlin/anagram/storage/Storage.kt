package anagram.storage

interface Storage {
    fun add(key: String, addValue: String)
    fun addMap( map: Map<String, String>)    
    fun getKeysForValue(addValue: kotlin.String): List<String>
    fun getKeysForValueWithFilter(criteria: GetKeysForValuesFilterCriteria): List<String> 
}
