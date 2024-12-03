package anagram.storage.map

import java.util.concurrent.ConcurrentHashMap

import anagram.storage.Storage
import anagram.storage.GetKeysForValuesFilterCriteria

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class  LocalInMemoryStorage: Storage {

    private val log = LoggerFactory.getLogger(LocalInMemoryStorage::class.java)

    private val keyValueMap = ConcurrentHashMap<String, String>()
 
    override fun add(key: String, addValue: String) {
       log.info("LocalInMemoryStorage add key:{}, value: {}}", key, addValue)
       keyValueMap.putIfAbsent(key, addValue)
    }

    override fun addMap( map: Map<String, String>)   {
        log.info("LocalInMemoryStorage addMap {}}", map)
        map.forEach { (key, addValue) ->
            keyValueMap.putIfAbsent(key, addValue)
        }
    }
 
    override fun getKeysForValue(addValue: kotlin.String): List<String> {
        log.info("LocalInMemoryStorage getKeysForValue {}}", addValue) 
        return keyValueMap.filterValues { it == addValue }.keys.toList()
    }

    override fun getKeysForValueWithFilter(criteria: GetKeysForValuesFilterCriteria): List<String> {
        log.info("LocalInMemoryStorage getKeysForValueWithFilter {}}", criteria) 
        val filteredMap = keyValueMap.filter { (key, addValue) ->
            key != criteria.excludeKey && addValue == criteria.targetValue 
        }
        return filteredMap.keys.toList()

    }

}
