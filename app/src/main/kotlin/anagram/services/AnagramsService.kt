package anagram.services


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import anagram.apis.AnagramsApiService
import anagram.models.AnagramProcessRequest
import anagram.models.AnagramProcessResponse
import anagram.models.AnagramFindRequest
import anagram.models.AnagramFindResponse

import anagram.storage.StorageFactory
import anagram.storage.GetFilterCriteria
import anagram.storage.Storage

@Service
class AnagramsService(@Autowired private val storageFactory: StorageFactory) : AnagramsApiService {
    @Autowired
    lateinit var storage: Storage  // Autowiring the Storage bean

    companion object {
        private const val MESSAGE_TEXTS_LENGTH_DIFFER = "Texts are not anagrams. Length differ!"
        private const val MESSAGE_TEXTS_ARE_IDENTICAL = "The texts are not anagrams. They are identical!"
        private const val MESSAGE_TEXTS_ARE_NOT_ANAGRAMS = "The texts are not anagrams."
        private const val MESSAGE_TEXTS_ARE_ANAGRAMS = "The texts are anagrams."
    }
 
    
    // Sort string to be used to identify anagrams
    private fun String.sortAsc() = toCharArray().sorted().joinToString("")
 
    override fun anagramsPost(anagramFindRequest: AnagramFindRequest): AnagramFindResponse {

        val aSorted = anagramFindRequest.text.sortAsc()
        val filter = GetFilterCriteria( exclude = anagramFindRequest.text, target = aSorted)
        val foundList = storage.getWithFilter(filter)
        return AnagramFindResponse( text = anagramFindRequest.text, anagrams = foundList)
    }


    override fun anagramsPut(anagramProcessRequest: AnagramProcessRequest): AnagramProcessResponse {
  
        //verify length first to avoid unnecessary operations
        if  (anagramProcessRequest.textA.length !=  anagramProcessRequest.textB.length){
            return AnagramProcessResponse(anagramProcessRequest.textA, anagramProcessRequest.textB, 
                                        result= false, message = MESSAGE_TEXTS_LENGTH_DIFFER)
        }

        //verify if both strings are equal
        if (anagramProcessRequest.textA == anagramProcessRequest.textB){
            return AnagramProcessResponse(textA = anagramProcessRequest.textA, textB = anagramProcessRequest.textB, 
                                        result= false, message = MESSAGE_TEXTS_ARE_IDENTICAL)
        }

        //sort and compare
        val aSorted = anagramProcessRequest.textA.sortAsc()
        val bSorted = anagramProcessRequest.textB.sortAsc()
 
        if (aSorted != bSorted){
            return AnagramProcessResponse(textA = anagramProcessRequest.textA, textB = anagramProcessRequest.textB, 
                                        result = false, message = MESSAGE_TEXTS_ARE_NOT_ANAGRAMS)
        }

        //strings are anagrams- store them
        storage.addMany(aSorted, anagramProcessRequest.textA , anagramProcessRequest.textB)
        return AnagramProcessResponse(textA = anagramProcessRequest.textA,  textB = anagramProcessRequest.textB,
                                    result = true, message = MESSAGE_TEXTS_ARE_ANAGRAMS)
    }
}