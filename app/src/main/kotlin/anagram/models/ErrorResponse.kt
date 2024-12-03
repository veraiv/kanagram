package anagram.models

import java.util.Objects
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Email
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size
import javax.validation.Valid
import io.swagger.v3.oas.annotations.media.Schema

/**
 * errorResponse
 * @param error 
 */
data class ErrorResponse(

    @Schema(example = "Missing required fields", required = true, description = "")
    @get:JsonProperty("error", required = true) val error: kotlin.String
    ) {

}

