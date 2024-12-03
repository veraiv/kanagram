package anagram.storage

data class GetKeysForValuesFilterCriteria(
    val excludeKey: String = String(),
    val targetValue: String = String()
)

