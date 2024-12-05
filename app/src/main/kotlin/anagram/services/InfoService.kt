package anagram.services

import io.swagger.v3.oas.models.OpenAPI
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import anagram.apis.InfoApiService

@Service
class InfoService : InfoApiService {
@Autowired
    lateinit var openAPI: OpenAPI  // Autowiring the OpenAPI bean

    override fun infoGet(): kotlin.Any {
 
        val version = openAPI.info?.version ?: "Unknown Version"
        val title = openAPI.info?.title ?: "Unknown API"
        val description = openAPI.info?.description ?: "No description available"

        // Health check response
        return   mapOf(
            "status" to "Service is up and running.",
            "api" to mapOf(
                "version" to version,
                "title" to title,
                "description" to description
    
            )
        )
    }
}