package anagram.storage.hazelcast

import com.hazelcast.config.Config
import com.hazelcast.config.NetworkConfig
import com.hazelcast.core.HazelcastInstance
import com.hazelcast.core.Hazelcast
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.beans.factory.annotation.Value
import org.slf4j.LoggerFactory

@Configuration
class HazelcastConfig (
    @Value("\${HAZELCAST_MAX_INIT_RETRY:10}") private val maxRetry: Int,
    @Value("\${HAZELCAST_CLUSTER_NAME:default-cluster}") private val clusterName: String,
    @Value("\${HAZELCAST_NODE_IPS:127.0.0.1}") private val nodeIps: String,
    @Value("\${HAZELCAST_INSTANCE:hazelcast-instance}") private val instanceName: String
    ){
    private val log = LoggerFactory.getLogger(HazelcastConfig::class.java)
 
    fun hazelcastInstance(): HazelcastStorage {


        val nodes = nodeIps.split(",").map { it.trim() }

        val config = Config()
        config.instanceName = instanceName
        config.clusterName = clusterName
        config.getJetConfig().setEnabled(true)
        val networkConfig: NetworkConfig = config.networkConfig
        networkConfig.join.tcpIpConfig.isEnabled = true
        networkConfig.join.tcpIpConfig.members = nodes  
 
        log.info("Creating Hazelcast Storage")
 
        val hazelcastInstance = Hazelcast.newHazelcastInstance(config)

        Runtime.getRuntime().addShutdownHook(Thread {
            hazelcastInstance.shutdown()
            try {
                hazelcastInstance.shutdown()
            } catch (e: Exception) {
                log.error("Error shutting down Hazelcast instance:", e)
            }
        })
        return HazelcastStorage(hazelcastInstance,maxRetry)
 
    }    
 
}
