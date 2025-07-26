package ea.home.gcp.config

import ea.home.gcp.service.GcpIprangeService
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.web.client.RestTemplate

@TestConfiguration
class GcpIprangeTestConfig {
    @Bean
    fun restTemplate(): RestTemplate = mock(RestTemplate::class.java)

    @Bean
    @Primary
    fun gcpIprangeService(): GcpIprangeService =
        GcpIprangeService(restTemplate(), "https://example.com")
}
