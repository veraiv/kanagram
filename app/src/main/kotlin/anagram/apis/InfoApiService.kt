package anagram.apis


interface InfoApiService {

    /**
     * GET /info
     *
     * @return Service is Up and running (status code 200)
     *         or Unexpected error (status code 5XX)
     * @see InfoApi#infoGet
     */
    fun infoGet(): kotlin.Any
}
