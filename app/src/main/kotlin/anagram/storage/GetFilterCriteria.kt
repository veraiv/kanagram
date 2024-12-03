package anagram.storage

data class GetFilterCriteria(
    val exclude: String = String(),
    val target: String = String()
)

