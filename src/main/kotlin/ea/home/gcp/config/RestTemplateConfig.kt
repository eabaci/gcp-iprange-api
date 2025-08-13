package ea.home.gcp.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

@Configuration
class RestTemplateConfig {

    @Bean
    fun restTemplate(): RestTemplate {
        val factory = SimpleClientHttpRequestFactory().apply {
            setConnectTimeout(3000)
            setReadTimeout(3000)
        }
        return RestTemplate(factory)
    }
}
