package anagram.apis 

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ActiveProfiles
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import anagram.apis.AnagramsApiController


@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan(basePackages = ["anagram", "anagram.service", "anagram.api", "anagram.storage"]) // Ensure both packages are scanned
@ActiveProfiles("test")  // Optional: Specify the profile to use
class AnagramsApiController {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun testProcessEqualTexts() {
 
         mockMvc.perform(post("/v1/anagrams/process")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"textA\": \"text\", \"textB\": \"text\" }") 
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON ))
        .andExpect(jsonPath("$.textA").value("text"))
        .andExpect(jsonPath("$.textA").value("text"))
        .andExpect(jsonPath("$.result").value(false)); 
        
            
    }


    @Test
    fun testProcessDifferentLengthTexts() {
 
         mockMvc.perform(post("/v1/anagrams/process")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"textA\": \"text\", \"textB\": \"text\" }") 
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON ))
        .andExpect(jsonPath("$.textA").value("text"))
        .andExpect(jsonPath("$.textA").value("te"))
        .andExpect(jsonPath("$.result").value(false)); 
        
            
    }

    @Test
    fun testProcessSameLengthTextsNotAnagrams() {
 
         mockMvc.perform(post("/v1/anagrams/process")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"textA\": \"text\", \"textB\": \"text\" }") 
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON ))
        .andExpect(jsonPath("$.textA").value("text"))
        .andExpect(jsonPath("$.textA").value("text1"))
        .andExpect(jsonPath("$.result").value(false)); 
        
            
    }

    @Test
    fun testProcessSameAnagrams() {
 
         mockMvc.perform(post("/v1/anagrams/process")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"textA\": \"text\", \"textB\": \"text\" }") 
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON ))
        .andExpect(jsonPath("$.textA").value("text"))
        .andExpect(jsonPath("$.textA").value("txet"))
        .andExpect(jsonPath("$.result").value(true)); 
        
            
    }

    @Test
    fun testGetEmptyAnagrams() {
 
         mockMvc.perform(post("/v1/anagrams/process")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"text\": \"text\"}") 
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON ))
        .andExpect(jsonPath("$.text").value("text"))
        .andExpect(jsonPath("$.anagrams").isEmpty);
        
    }

    @Test
    fun testGetAnagrams() {
        mockMvc.perform(post("/v1/anagrams/find")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"textA\": \"text\", \"textB\": \"text\" }") 
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON ))
        .andExpect(jsonPath("$.textA").value("text"))
        .andExpect(jsonPath("$.textA").value("txet"))
        .andExpect(jsonPath("$.result").value(true));        
 
         mockMvc.perform(post("/v1/anagrams/find")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"text\": \"text\"}") 
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON ))
        .andExpect(jsonPath("$.text").value("text"))
        .andExpect(jsonPath("$.anagrams").isEmpty)
        .andExpect(jsonPath("$.data").value(listOf("txet")));
        
    }
}
