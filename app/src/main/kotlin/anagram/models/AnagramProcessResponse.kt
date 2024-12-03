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
 * anagramProcessResponse
 * @param textA 
 * @param textB 
 * @param result 
 * @param message 
 */
data class AnagramProcessResponse(

    @Schema(example = "null", required = true, description = "")
    @get:JsonProperty("textA", required = true) val textA: kotlin.String,

    @Schema(example = "null", required = true, description = "")
    @get:JsonProperty("textB", required = true) val textB: kotlin.String,

    @Schema(example = "null", required = true, description = "")
    @get:JsonProperty("result", required = true) val result: kotlin.Boolean,

    @Schema(example = "null", required = true, description = "")
    @get:JsonProperty("message", required = true) val message: kotlin.String
    ) {

}

