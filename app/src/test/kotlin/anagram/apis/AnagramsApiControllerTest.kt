package anagram.apis 

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
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

import org.hamcrest.Matchers.contains
import org.hamcrest.Matchers.hasSize
import org.hamcrest.Matchers.containsInAnyOrder

@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan(basePackages = ["anagram", "anagram.service", "anagram.api", "anagram.storage"])  
class AnagramsApiControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun testProcessMissingParameter() {

        mockMvc.perform(put("/v1/anagrams")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"textA\": \"text\" }")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    fun testProcessEqualTexts() {
 
        mockMvc.perform(put("/v1/anagrams")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"textA\": \"text\", \"textB\": \"text\" }") 
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON ))
            .andExpect(jsonPath("$.textA").value("text"))
            .andExpect(jsonPath("$.textB").value("text"))
            .andExpect(jsonPath("$.result").value(false)); 
            
    }


    @Test
    fun testProcessDifferentLengthTexts() {
 
        mockMvc.perform(put("/v1/anagrams")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"textA\": \"text\", \"textB\": \"te\" }") 
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON ))
            .andExpect(jsonPath("$.textA").value("text"))
            .andExpect(jsonPath("$.textB").value("te"))
            .andExpect(jsonPath("$.result").value(false)); 
     
    }

    @Test
    fun testProcessSameLengthTextsNotAnagrams() {
 
        mockMvc.perform(put("/v1/anagrams")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"textA\": \"text\", \"textB\": \"texx\" }") 
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON ))
        .andExpect(jsonPath("$.textA").value("text"))
        .andExpect(jsonPath("$.textB").value("texx"))
        .andExpect(jsonPath("$.result").value(false)); 

    }

    @Test
    fun testProcessSameAnagrams() {
 
        mockMvc.perform(put("/v1/anagrams")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"textA\": \"text\", \"textB\": \"txet\" }") 
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON ))
            .andExpect(jsonPath("$.textA").value("text"))
            .andExpect(jsonPath("$.textB").value("txet"))
            .andExpect(jsonPath("$.result").value(true)); 
            
    }

    @Test
    fun testGetEmptyAnagrams() {
 
        mockMvc.perform(post("/v1/anagrams")
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
        val response = mockMvc.perform(put("/v1/anagrams")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"textA\": \"text\", \"textB\": \"txet\" }") 
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON ))
            .andExpect(jsonPath("$.textA").value("text"))
            .andExpect(jsonPath("$.textB").value("txet"))
            .andExpect(jsonPath("$.result").value(true))
            .andReturn();    
        
        mockMvc.perform(post("/v1/anagrams")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"text\": \"text\"}") 
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON ))
            .andExpect(jsonPath("$.text").value("text"))
            .andExpect(jsonPath("$.anagrams").exists())
            .andExpect(jsonPath("$.anagrams").isArray())            
            .andExpect(jsonPath("$.anagrams").value(contains("txet")))
            .andExpect(jsonPath("$.anagrams", hasSize<Any>(1)));

        mockMvc.perform(put("/v1/anagrams")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"textA\": \"text\", \"textB\": \"txte\" }") 
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON ))
            .andExpect(jsonPath("$.textA").value("text"))
            .andExpect(jsonPath("$.textB").value("txte"))
            .andExpect(jsonPath("$.result").value(true));        
 
        mockMvc.perform(post("/v1/anagrams")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"text\": \"text\"}") 
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON ))
            .andExpect(jsonPath("$.text").value("text"))
            .andExpect(jsonPath("$.anagrams").exists())
            .andExpect(jsonPath("$.anagrams").isArray())
            .andExpect(jsonPath("$.anagrams").value(containsInAnyOrder("txet", "txte")))
            .andExpect(jsonPath("$.anagrams", hasSize<Any>(2)));
 
        mockMvc.perform(put("/v1/anagrams")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"textA\": \"text\", \"textB\": \"ttxe\" }") 
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON ))
            .andExpect(jsonPath("$.textA").value("text"))
            .andExpect(jsonPath("$.textB").value("ttxe"))
            .andExpect(jsonPath("$.result").value(true));        
 
        mockMvc.perform(post("/v1/anagrams")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"text\": \"text\"}") 
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON ))
            .andExpect(jsonPath("$.text").value("text"))
            .andExpect(jsonPath("$.anagrams").exists())
            .andExpect(jsonPath("$.anagrams").isArray())
            .andExpect(jsonPath("$.anagrams").value(containsInAnyOrder("txet", "txte", "ttxe")))
            .andExpect(jsonPath("$.anagrams", hasSize<Any>(3)));
    }
 
}
