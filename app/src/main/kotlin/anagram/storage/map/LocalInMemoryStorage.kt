package anagram.storage.map

import java.util.concurrent.ConcurrentHashMap

import anagram.storage.Storage
import anagram.storage.GetFilterCriteria

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class  LocalInMemoryStorage: Storage {

    private val log = LoggerFactory.getLogger(LocalInMemoryStorage::class.java)

    private val keyValueMap = ConcurrentHashMap<String, String>()

    override fun addMany(key: String,  vararg values: String){        
        for (next in values){
            keyValueMap.putIfAbsent(next, key)
        }
     }
 
    override fun getWithFilter(criteria: GetFilterCriteria): List<String>  {
        val filteredMap = keyValueMap.filter { (key, addValue) ->
            key != criteria.exclude && addValue == criteria.target
        }
        return filteredMap.keys.toList()
    }
}
