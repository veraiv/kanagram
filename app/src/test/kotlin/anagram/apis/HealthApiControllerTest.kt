package anagram.apis 

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ActiveProfiles

import anagram.apis.HealthApiController

@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan(basePackages = ["anagram", "anagram.service", "anagram.api"])   
class HealthApiControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun testGetStatus() {
        mockMvc.perform(get("/v1/health"))
            .andExpect(status().isOk)
            
    }
}
