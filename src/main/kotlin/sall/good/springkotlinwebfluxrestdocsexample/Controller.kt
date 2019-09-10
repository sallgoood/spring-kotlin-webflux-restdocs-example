package sall.good.springkotlinwebfluxrestdocsexample

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class Controller {

    @GetMapping("/hello")
    fun hello(): Mono<String> {
        return Mono.just("world")
    }
}
