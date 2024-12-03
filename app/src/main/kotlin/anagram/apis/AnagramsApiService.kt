package anagram.apis

import anagram.models.AnagramFindRequest
import anagram.models.AnagramFindResponse
import anagram.models.AnagramProcessRequest
import anagram.models.AnagramProcessResponse

interface AnagramsApiService {

    /**
     * POST /anagrams : Get all in memmory stored anagrams for a provided text. POST request for lenght and security resons.
     * This endpoint takes a strings and returns all anagrams that were previously reuested.
     *
     * @param anagramFindRequest  (required)
     * @return Successful (status code 200)
     *         or Bad request if one of the required fields is missing (status code 400)
     * @see AnagramsApi#anagramsPost
     */
    fun anagramsPost(anagramFindRequest: AnagramFindRequest): AnagramFindResponse

    /**
     * PUT /anagrams : This endpoint takes two strings and checks if theyare anagrams of each other
     * This endpoint takes two strings and returns true if they are anagrams. The strings are preserved within a single run.
     *
     * @param anagramProcessRequest  (required)
     * @return Successful (status code 200)
     *         or Bad request if one of the required fields is missing (status code 400)
     * @see AnagramsApi#anagramsPut
     */
    fun anagramsPut(anagramProcessRequest: AnagramProcessRequest): AnagramProcessResponse
}
