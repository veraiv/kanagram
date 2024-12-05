package anagram.storage.redis

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.beans.factory.annotation.Value
import org.slf4j.LoggerFactory

@Configuration
class RedisConfig ( ) {
    private val log = LoggerFactory.getLogger(RedisConfig::class.java)

    @Value("\${redis.host}")
    private  val redisHost: String = "localhost"
    
    @Value("\${redis.port}") 
    private  val redisPort: Int = 6379

    fun redisInstance(): RedisStorage {

        log.info("Creating Redis Storage")
        return RedisStorage(redisHost, redisPort)
    }    
}