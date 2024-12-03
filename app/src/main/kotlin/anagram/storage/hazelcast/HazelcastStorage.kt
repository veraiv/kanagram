package anagram.storage.hazelcast

import com.hazelcast.core.HazelcastInstance
import com.hazelcast.query.impl.predicates.SqlPredicate
import com.hazelcast.map.IMap;
import com.hazelcast.sql.SqlResult;
import com.hazelcast.sql.SqlRow;
import com.hazelcast.query.Predicates
import com.hazelcast.sql.SqlService
import org.springframework.beans.factory.annotation.Value
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.hazelcast.cluster.ClusterState
import java.util.concurrent.TimeUnit

import anagram.storage.Storage
import anagram.storage.GetKeysForValuesFilterCriteria

class HazelcastStorage(private val hazelcastInstance: HazelcastInstance): Storage {

      private val log = LoggerFactory.getLogger(HazelcastStorage::class.java)

      private val keyValueMap = hazelcastInstance.getMap<String, String>("distributedmap")
 
 
      init {
          log.info("HazelcastStorage initialize")
           
         waitForHazelcast()

         hazelcastInstance.sql.execute("""
               CREATE MAPPING distributedmap
               TYPE IMap
               OPTIONS (
                  'keyFormat' = 'varchar',
                  'valueFormat' = 'varchar'
               )
         """)

      }

      override fun add(key: String, addValue: String) {
         log.info("Hazelcast add key{} : val{}", key, addValue)
         keyValueMap[key] = addValue
      }
   
      override fun addMap( map: Map<String, String>)   {
         log.info("Hazelcast add map {}", map)
         keyValueMap.putAll(map)
      }
  
      override fun getKeysForValue(addValue: kotlin.String): List<String> {
         val list = mutableListOf<String>()
         val sqlQuery = "SELECT __key, this FROM distributedmap WHERE this = ?"
         hazelcastInstance.sql.execute(sqlQuery, addValue).use { result ->
            for (row in result) {
               val key = row.getObject<String>("__key")
               list.add(key)                
            }
         }
         log.info("Hazelcast getKeysForValue {}", list)
         return list
      }

      fun queryKeysByValue(addValue: String): List<String> {
         return keyValueMap
             .entrySet(Predicates.equal("__value", addValue))   
             .map { it.key }   
      }
 
      override fun getKeysForValueWithFilter(criteria: GetKeysForValuesFilterCriteria): List<String> {
         val list = mutableListOf<String>()
         val sqlQuery = "SELECT __key, this FROM distributedmap WHERE this = ?"
         hazelcastInstance.sql.execute(sqlQuery, criteria.targetValue ).use { result ->
            for (row in result) {
               row.getObject<String>("__key")?.let { key ->
                  if (key != criteria.excludeKey) {
                     list.add(key)
                  }
               }
                              
            }
         }

         log.info("Hazelcast getKeysForValue with filter {}", list)
         return list
      }
 
      // Helper function to wait for Hazelcast readiness
      private fun waitForHazelcast( ) {
         val cluster = hazelcastInstance.cluster

         val maxRetries: Int = System.getenv("WAIT_HASELCAST_RETRIES")?.toIntOrNull() ?: 10 
       
         var retries = 0
         while (retries < maxRetries) {
            if (cluster.members.isNotEmpty()) { // Check if there are active members
                println("Hazelcast cluster is ready.")
                return
            }
            retries++
            println("Waiting for Hazelcast to be ready...")
            Thread.sleep(1000)
        }
    

         throw IllegalStateException("Hazelcast instance did not become ready within the timeout period.")
  }
}
