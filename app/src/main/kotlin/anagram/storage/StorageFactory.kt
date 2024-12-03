package anagram.storage

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
import org.springframework.beans.factory.annotation.Value 
import anagram.storage.hazelcast.HazelcastStorage
import anagram.storage.hazelcast.HazelcastConfig
import anagram.storage.map.LocalInMemoryStorage
import anagram.storage.redis.RedisStorage

import org.slf4j.Logger
import org.slf4j.LoggerFactory
 
import org.springframework.context.annotation.Configuration;

@Configuration

class StorageFactory {
 
    private val log = LoggerFactory.getLogger(StorageFactory::class.java)
    
    @Autowired
    private lateinit var environment: Environment    

    @Autowired
    private lateinit var hazelcastConfig: HazelcastConfig    

    @Value("\${STORAGE_TYPE:map}")
    private lateinit var storageType: String

    @Bean
    fun createStorage(): Storage {

        val type = storageType.lowercase()
        //if "haselcast" create hazelcast db, reate simple map otherwise 
        log.info("Creating Storage  {} ", storageType.lowercase())
        if(type == "hazelcast") {
            return hazelcastConfig.hazelcastInstance()           
        }
        if(type == "redis") {
            return RedisStorage()           
        }
        return LocalInMemoryStorage()  
    }
}