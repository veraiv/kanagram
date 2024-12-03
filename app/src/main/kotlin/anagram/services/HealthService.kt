package anagram.services

import io.swagger.v3.oas.models.OpenAPI
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import anagram.apis.HealthApiService

@Service
class HealthService : HealthApiService {
@Autowired
    lateinit var openAPI: OpenAPI  // Autowiring the OpenAPI bean

    override fun healthGet(): Unit {
            //Do Nothing for now
    }
}