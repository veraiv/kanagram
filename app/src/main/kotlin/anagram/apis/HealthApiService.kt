package anagram.apis


interface HealthApiService {

    /**
     * GET /health
     *
     * @return Service is Up and running (status code 200)
     *         or Unexpected error (status code 5XX)
     * @see HealthApi#healthGet
     */
    fun healthGet(): Unit
}
