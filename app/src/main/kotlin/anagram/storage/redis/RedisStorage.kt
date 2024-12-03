package anagram.storage.redis

import redis.clients.jedis.Jedis

import org.springframework.beans.factory.annotation.Value

import org.slf4j.LoggerFactory

import anagram.storage.Storage
import anagram.storage.GetFilterCriteria
import java.lang.Exception
 
class RedisStorage( @Value("\${REDIS_URL:localhost:6379}") private val redisUrl: String = "localhost:6379"): Storage  {

    private val log = LoggerFactory.getLogger(RedisStorage::class.java)
 
    private val jedis: Jedis

    init {       
        val (host, port) = parseRedisUrl(redisUrl)
        jedis = Jedis(host, port)

        Runtime.getRuntime().addShutdownHook(Thread {
            try {
                jedis.close()
            } catch (e: Exception) {
                log.error("Error closing jedis instance:", e)
            }
        })
    }
 
    override fun addMany(key: String, vararg values: String) {
          jedis.sadd(key, *values)
    }
 
    override fun getWithFilter(criteria: GetFilterCriteria): List<String>  {
        val setMembers: Set<String> = jedis.smembers(criteria.target) ?: emptySet()
        val modifiedSet = setMembers.toMutableSet()   
        modifiedSet.remove(criteria.exclude)  
        return modifiedSet.toList() 
    }
 
    private fun parseRedisUrl(url: String): Pair<String, Int> {
        val parts = url.split(":")
        require(parts.size == 2) { "Invalid Redis URL format. Expected 'host:port'." }
        val host = parts[0]
        val port = parts[1].toIntOrNull() ?: throw IllegalArgumentException("Port must be a valid integer.")
        return Pair(host, port)
    }
}
