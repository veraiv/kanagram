package anagram.storage.hazelcast

import com.hazelcast.core.HazelcastInstance
import com.hazelcast.query.impl.predicates.SqlPredicate
import com.hazelcast.map.IMap;
import com.hazelcast.sql.SqlResult;
import com.hazelcast.sql.SqlRow;
import com.hazelcast.query.Predicates
import com.hazelcast.sql.SqlService
import org.springframework.beans.factory.annotation.Value
import org.slf4j.LoggerFactory
import com.hazelcast.cluster.ClusterState
import java.util.concurrent.TimeUnit

import anagram.storage.Storage
import anagram.storage.GetFilterCriteria

class HazelcastStorage(private val hazelcastInstance: HazelcastInstance,  private val maxRetries: Int = 10): Storage {

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

      override fun addMany(key: String,  vararg values: String){
         for (next in values){
             keyValueMap.putIfAbsent(next, key)
         }
      }
  
      override fun getWithFilter(criteria: GetFilterCriteria): List<String>  {
         val list = mutableListOf<String>()
         val sqlQuery = "SELECT __key, this FROM distributedmap WHERE this = ?"
         hazelcastInstance.sql.execute(sqlQuery, criteria.target ).use { result ->
            for (row in result) {
               row.getObject<String>("__key")?.let { key ->
                  if (key != criteria.exclude) {
                     list.add(key)
                  }
               }
                              
            }
         }

         log.info("Hazelcast getKeysForValue with filter {}", list)
         return list
      }
 
      private fun waitForHazelcast( ) {
         val cluster = hazelcastInstance.cluster
 
         var retries = 0
         while (retries < maxRetries) {
            if (cluster.members.isNotEmpty()) { 
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
