package anagram.storage.hazelcast

import com.hazelcast.config.Config
import com.hazelcast.config.NetworkConfig
import com.hazelcast.core.Hazelcast
import org.springframework.context.annotation.Configuration
import org.springframework.beans.factory.annotation.Value
import org.slf4j.LoggerFactory

@Configuration
class HazelcastConfig (
    @Value("\${haselcast.max.int.retry:10}") private val maxRetry: Int,
    @Value("\${haselcast.cluster.name:default-cluster}") private val clusterName: String,
    @Value("\${haselcast.node.ips:127.0.0.1}") private val nodeIps: String,
    @Value("\${haselcast.instance:hazelcast-instance}") private val instanceName: String
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
            try {
                hazelcastInstance.shutdown()
            } catch (e: Exception) {
                log.error("Error shutting down Hazelcast instance:", e)
            }
        })
        return HazelcastStorage(hazelcastInstance, maxRetry)
    }    
 
}
