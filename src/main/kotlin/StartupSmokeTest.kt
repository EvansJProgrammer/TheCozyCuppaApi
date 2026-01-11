package dev.thecozycuppa

import dev.thecozycuppa.repository.PostRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class StartupSmokeTest(
    private val postRepository: PostRepository
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        println("Post count = ${postRepository.count()}")
    }
}
