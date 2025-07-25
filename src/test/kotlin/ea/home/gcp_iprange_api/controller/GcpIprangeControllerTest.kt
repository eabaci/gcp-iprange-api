package ea.home.gcp_iprange_api.controller

import ea.home.gcp_iprange_api.common.IpVersion
import ea.home.gcp_iprange_api.common.Region
import ea.home.gcp_iprange_api.config.GcpIprangeTestConfig
import ea.home.gcp_iprange_api.dto.GcpIpRange
import ea.home.gcp_iprange_api.dto.GcpIpRangesResponse
import ea.home.gcp_iprange_api.service.GcpIprangeService
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.web.client.RestTemplate

@WebMvcTest(GcpIprangeController::class)
@Import(GcpIprangeTestConfig::class)
@AutoConfigureMockMvc
class GcpIprangeControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var gcpIprangeService: GcpIprangeService

    @Autowired
    private lateinit var restTemplate: RestTemplate

    @Test
    fun `should return ip ranges by region AF and by ip version IPV6`() {
        // given
        val gcpIpRanges = listOf("2600:1900:8000::/44")
        val mockResponse = GcpIpRangesResponse(
                prefixes = listOf(
                        GcpIpRange("35.185.128.0/19", null, "Google Cloud", "asia-east1"),
                        GcpIpRange("1.1.1.0/24", null, "Google Cloud", "europe-west1"),
                        GcpIpRange("35.185.160.0/20", null, "Google Cloud", "asia-east1"),
                        GcpIpRange(null, "2600:1900:8000::/44", "Google Cloud", "africa-south1"),
                        GcpIpRange(null, "2600:1900:4030::/44", "Google Cloud", "asia-east1")
                )
        )

        // when
        `when`(gcpIprangeService.getIpRangesByRegionAndByIpVersion(Region.AF, IpVersion.IPV6)).thenReturn(gcpIpRanges)
        `when`(restTemplate.getForObject(anyString(), eq(GcpIpRangesResponse::class.java))).thenReturn(mockResponse)

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/gcp-ipranges?region=AF&ipVersion=IPV6"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("2600:1900:8000::/44"))
    }

    @Test
    fun `should return all ip ranges by no region and by no ip version`() {
        // given
        val gcpIpRanges = listOf("34.1.208.0/20", "34.85.0.0/17", "35.243.8.0/21")
        val mockResponse = GcpIpRangesResponse(
                prefixes = listOf(
                        GcpIpRange("34.1.208.0/20", null, "Google Cloud", "africa-south1"),
                        GcpIpRange("34.85.0.0/17", null, "Google Cloud", "africa-south1"),
                        GcpIpRange("35.243.8.0/21", null, "Google Cloud", "africa-south1")
                )
        )

        // when
        `when`(gcpIprangeService.getIpRangesByRegionAndByIpVersion()).thenReturn(gcpIpRanges)
        `when`(restTemplate.getForObject(anyString(), eq(GcpIpRangesResponse::class.java))).thenReturn(mockResponse)

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/gcp-ipranges"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$")
                        .value("34.1.208.0/20\n34.85.0.0/17\n35.243.8.0/21"))
    }

    @Test
    fun `should return bad request by invalid region`() {
        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/gcp-ipranges?region=ABZ"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `should return bad request by invalid ip version`() {
        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/gcp-ipranges?ipVersion=ABZ"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }
}