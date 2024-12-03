package anagram.storage.hazelcast

import com.hazelcast.config.Config
import com.hazelcast.config.NetworkConfig
import com.hazelcast.core.HazelcastInstance
import com.hazelcast.core.Hazelcast
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value

import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Configuration
class HazelcastConfig {
    private val log = LoggerFactory.getLogger(HazelcastConfig::class.java)

    @Autowired
    private lateinit var environment: Environment
 
    @Value("\${HAZELCAST_CLUSTER_NAME:default-cluster}")
    private lateinit var clusterName: String

    @Value("\${HAZELCAST_NODE_IPS:127.0.0.1}") 
    private lateinit var nodeIps: String
   
    @Value("\${HAZELCAST_INSTANCE:hazelcast-instance}") 
    private lateinit var instanceName: String

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
        return HazelcastStorage(Hazelcast.newHazelcastInstance(config))
 
    }    
 
}
