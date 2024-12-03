package anagram

import org.springframework.boot.runApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["anagram", "anagram.apis", "anagram.models", "amagram.storage", "amagram.service"])
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
