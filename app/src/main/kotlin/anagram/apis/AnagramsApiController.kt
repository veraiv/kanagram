package anagram.apis

import anagram.models.AnagramFindRequest
import anagram.models.AnagramFindResponse
import anagram.models.AnagramProcessRequest
import anagram.models.AnagramProcessResponse
import io.swagger.v3.oas.annotations.*
import io.swagger.v3.oas.annotations.enums.*
import io.swagger.v3.oas.annotations.media.*
import io.swagger.v3.oas.annotations.responses.*
import io.swagger.v3.oas.annotations.security.*
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.*
import org.springframework.validation.annotation.Validated
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.beans.factory.annotation.Autowired

import javax.validation.Valid
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Email
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

import kotlin.collections.List
import kotlin.collections.Map

@RestController
@Validated
@RequestMapping("\${api.base-path:/v1}")
class AnagramsApiController(@Autowired(required = true) val service: AnagramsApiService) {

    @Operation(
        summary = "Get all in memmory stored anagrams for a provided text. POST request for lenght and security resons.",
        operationId = "anagramsPost",
        description = """This endpoint takes a strings and returns all anagrams that were previously reuested.""",
        responses = [
            ApiResponse(responseCode = "200", description = "Successful", content = [Content(schema = Schema(implementation = AnagramFindResponse::class))]),
            ApiResponse(responseCode = "400", description = "Bad request if one of the required fields is missing") ]
    )
    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/anagrams"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun anagramsPost(@Parameter(description = "", required = true) @Valid @RequestBody anagramFindRequest: AnagramFindRequest): ResponseEntity<AnagramFindResponse> {
        return ResponseEntity(service.anagramsPost(anagramFindRequest), HttpStatus.valueOf(200))
    }

    @Operation(
        summary = "This endpoint takes two strings and checks if theyare anagrams of each other",
        operationId = "anagramsPut",
        description = """This endpoint takes two strings and returns true if they are anagrams. The strings are preserved within a single run.""",
        responses = [
            ApiResponse(responseCode = "200", description = "Successful", content = [Content(schema = Schema(implementation = AnagramProcessResponse::class))]),
            ApiResponse(responseCode = "400", description = "Bad request if one of the required fields is missing") ]
    )
    @RequestMapping(
        method = [RequestMethod.PUT],
        value = ["/anagrams"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun anagramsPut(@Parameter(description = "", required = true) @Valid @RequestBody anagramProcessRequest: AnagramProcessRequest): ResponseEntity<AnagramProcessResponse> {
        return ResponseEntity(service.anagramsPut(anagramProcessRequest), HttpStatus.valueOf(200))
    }
}
