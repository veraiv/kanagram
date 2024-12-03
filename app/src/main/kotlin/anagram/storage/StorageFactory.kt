package anagram.storage

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
import org.springframework.beans.factory.annotation.Value 
import anagram.storage.hazelcast.HazelcastStorage
import anagram.storage.hazelcast.HazelcastConfig
import anagram.storage.map.LocalInMemoryStorage

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
        //if "haselcast" create hazelcast db, reate simple map otherwise 
        log.info("Creating Storage  {} ", storageType.lowercase())
        if(storageType.lowercase() ==   "hazelcast") {
            return hazelcastConfig.hazelcastInstance()           
        }
        return LocalInMemoryStorage()  
    }
}