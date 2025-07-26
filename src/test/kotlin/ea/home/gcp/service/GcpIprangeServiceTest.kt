package ea.home.gcp.service

import ea.home.gcp.common.IpVersion
import ea.home.gcp.common.Region
import ea.home.gcp.dto.GcpIpRange
import ea.home.gcp.dto.GcpIpRangesResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.client.RestTemplate
import kotlin.test.assertEquals

@SpringBootTest
class GcpIprangeServiceTest {

    private lateinit var restTemplate: RestTemplate
    private lateinit var gcpIprangeService: GcpIprangeService

    private val testUrl = "https://example.com/ip-ranges.json"

    @BeforeEach
    fun setUp() {
        restTemplate = mock(RestTemplate::class.java)
        gcpIprangeService = GcpIprangeService(restTemplate, testUrl)
    }

    @Test
    fun `get ip ranges if the response is a list of gcp ip ranges`() {
        // given
        val mockResponse = GcpIpRangesResponse(
            prefixes = listOf(
                GcpIpRange("35.185.128.0/19", null, "Google Cloud", "asia-east1"),
                GcpIpRange("1.1.1.0/24", null, "Google Cloud", "europe-west1"),
                GcpIpRange("35.185.160.0/20", null, "Google Cloud", "asia-east1"),
                GcpIpRange(null, "2600:1900:8000::/44", "Google Cloud", "africa-south1"),
                GcpIpRange(null, "2600:1900:4030::/44", "Google Cloud", "asia-east1")
            )
        )

        // rules
        `when`(restTemplate.getForObject(testUrl, GcpIpRangesResponse::class.java)).thenReturn(mockResponse)

        // when
        val result = gcpIprangeService.getIpRanges()

        // then
        assertEquals(5, result.size)
        assertEquals("35.185.128.0/19", result[0].ipv4Prefix)
        assertEquals(null, result[0].ipv6Prefix)
        assertEquals("Google Cloud", result[0].service)
        assertEquals("asia-east1", result[0].scope)

        assertEquals(null, result[4].ipv4Prefix)
        assertEquals("2600:1900:4030::/44", result[4].ipv6Prefix)
        assertEquals("Google Cloud", result[4].service)
        assertEquals("asia-east1", result[4].scope)
    }

    @Test
    fun `get no ip ranges if the response is a empty list of gcp ip ranges`() {
        // given
        val mockResponse = GcpIpRangesResponse(
            prefixes = listOf()
        )

        // rules
        `when`(restTemplate.getForObject(testUrl, GcpIpRangesResponse::class.java)).thenReturn(mockResponse)

        // when
        val result = gcpIprangeService.getIpRanges()

        // then
        assertEquals(0, result.size)
        assertEquals(emptyList(), result)
    }

    @Test
    fun `get no ip ranges if the response is empty`() {
        // given
        val mockResponse = null

        // rules
        `when`(restTemplate.getForObject(testUrl, GcpIpRangesResponse::class.java)).thenReturn(mockResponse)

        // when
        val result = gcpIprangeService.getIpRanges()

        // then
        assertEquals(0, result.size)
        assertEquals(emptyList(), result)
    }

    @Test
    fun `get all ip ranges by default region and by default ip version`() {
        // given
        val mockResponse = GcpIpRangesResponse(
            prefixes = listOf(
                GcpIpRange("35.185.128.0/19", null, "Google Cloud", "asia-east1"),
                GcpIpRange("1.1.1.0/24", null, "Google Cloud", "europe-west1"),
                GcpIpRange("35.185.160.0/20", null, "Google Cloud", "asia-east1"),
                GcpIpRange(null, "2600:1900:8000::/44", "Google Cloud", "africa-south1"),
                GcpIpRange(null, "2600:1900:4030::/44", "Google Cloud", "asia-east1")
            )
        )

        // rules
        `when`(restTemplate.getForObject(testUrl, GcpIpRangesResponse::class.java)).thenReturn(mockResponse)

        // when
        val ipRanges: List<String> = gcpIprangeService.getIpRangesByRegionAndByIpVersion()

        // then
        assertEquals(5, ipRanges.size)
        assertEquals("35.185.128.0/19", ipRanges[0])
        assertEquals("1.1.1.0/24", ipRanges[1])
        assertEquals("35.185.160.0/20", ipRanges[2])
        assertEquals("2600:1900:8000::/44", ipRanges[3])
        assertEquals("2600:1900:4030::/44", ipRanges[4])
    }

    @Test
    fun `get ip ranges by region EU and by ip version IPV4`() {
        // given
        val region: Region = Region.EU
        val ipVersion: IpVersion = IpVersion.IPV4
        val mockResponse = GcpIpRangesResponse(
            prefixes = listOf(
                GcpIpRange("35.185.128.0/19", null, "Google Cloud", "asia-east1"),
                GcpIpRange("1.1.1.0/24", null, "Google Cloud", "europe-west1"),
                GcpIpRange("35.185.160.0/20", null, "Google Cloud", "asia-east1"),
                GcpIpRange(null, "2600:1900:8000::/44", "Google Cloud", "africa-south1"),
                GcpIpRange(null, "2600:1900:4030::/44", "Google Cloud", "asia-east1")
            )
        )

        // rules
        `when`(restTemplate.getForObject(testUrl, GcpIpRangesResponse::class.java)).thenReturn(mockResponse)

        // when
        val ipRanges: List<String> = gcpIprangeService.getIpRangesByRegionAndByIpVersion(region, ipVersion)

        // then
        assertEquals(1, ipRanges.size)
        assertEquals("1.1.1.0/24", ipRanges[0])
    }

    @Test
    fun `get ip ranges by region AS and by ip version IPV6`() {
        // given
        val region: Region = Region.AS
        val ipVersion: IpVersion = IpVersion.IPV6
        val mockResponse = GcpIpRangesResponse(
            prefixes = listOf(
                GcpIpRange("35.185.128.0/19", null, "Google Cloud", "asia-east1"),
                GcpIpRange("1.1.1.0/24", null, "Google Cloud", "europe-west1"),
                GcpIpRange("35.185.160.0/20", null, "Google Cloud", "asia-east1"),
                GcpIpRange(null, "2600:1900:8000::/44", "Google Cloud", "africa-south1"),
                GcpIpRange(null, "2600:1900:4030::/44", "Google Cloud", "asia-east1")
            )
        )

        // rules
        `when`(restTemplate.getForObject(testUrl, GcpIpRangesResponse::class.java)).thenReturn(mockResponse)

        // when
        val ipRanges: List<String> = gcpIprangeService.getIpRangesByRegionAndByIpVersion(region, ipVersion)

        // then
        assertEquals(1, ipRanges.size)
        assertEquals("2600:1900:4030::/44", ipRanges[0])
    }

    @Test
    fun `get ip ranges by region AS and by ip version IPV4`() {
        // given
        val region: Region = Region.AS
        val ipVersion: IpVersion = IpVersion.IPV4
        val mockResponse = GcpIpRangesResponse(
            prefixes = listOf(
                GcpIpRange("35.185.128.0/19", null, "Google Cloud", "asia-east1"),
                GcpIpRange("1.1.1.0/24", null, "Google Cloud", "europe-west1"),
                GcpIpRange("35.185.160.0/20", null, "Google Cloud", "asia-east1"),
                GcpIpRange(null, "2600:1900:8000::/44", "Google Cloud", "africa-south1"),
                GcpIpRange(null, "2600:1900:4030::/44", "Google Cloud", "asia-east1")
            )
        )

        // rules
        `when`(restTemplate.getForObject(testUrl, GcpIpRangesResponse::class.java)).thenReturn(mockResponse)

        // when
        val ipRanges: List<String> = gcpIprangeService.getIpRangesByRegionAndByIpVersion(region, ipVersion)

        // then
        assertEquals(2, ipRanges.size)
        assertEquals("35.185.128.0/19", ipRanges[0])
        assertEquals("35.185.160.0/20", ipRanges[1])
    }

    @Test
    fun `get ip ranges by region AS and by ip version ALL`() {
        // given
        val region: Region = Region.AS
        val mockResponse = GcpIpRangesResponse(
            prefixes = listOf(
                GcpIpRange("35.185.128.0/19", null, "Google Cloud", "asia-east1"),
                GcpIpRange("1.1.1.0/24", null, "Google Cloud", "europe-west1"),
                GcpIpRange("35.185.160.0/20", null, "Google Cloud", "asia-east1"),
                GcpIpRange(null, "2600:1900:8000::/44", "Google Cloud", "africa-south1"),
                GcpIpRange(null, "2600:1900:4030::/44", "Google Cloud", "asia-east1")
            )
        )

        // rules
        `when`(restTemplate.getForObject(testUrl, GcpIpRangesResponse::class.java)).thenReturn(mockResponse)

        // when
        val ipRanges: List<String> = gcpIprangeService.getIpRangesByRegionAndByIpVersion(region = region)

        // then
        assertEquals(3, ipRanges.size)
        assertEquals("35.185.128.0/19", ipRanges[0])
        assertEquals("35.185.160.0/20", ipRanges[1])
        assertEquals("2600:1900:4030::/44", ipRanges[2])
    }

    @Test
    fun `get ip ranges by region ALL and by ip version IPV4`() {
        // given
        val ipVersion: IpVersion = IpVersion.IPV4
        val mockResponse = GcpIpRangesResponse(
            prefixes = listOf(
                GcpIpRange("35.185.128.0/19", null, "Google Cloud", "asia-east1"),
                GcpIpRange("1.1.1.0/24", null, "Google Cloud", "europe-west1"),
                GcpIpRange("35.185.160.0/20", null, "Google Cloud", "asia-east1"),
                GcpIpRange(null, "2600:1900:8000::/44", "Google Cloud", "africa-south1"),
                GcpIpRange(null, "2600:1900:4030::/44", "Google Cloud", "asia-east1")
            )
        )

        // rules
        `when`(restTemplate.getForObject(testUrl, GcpIpRangesResponse::class.java)).thenReturn(mockResponse)

        // when
        val ipRanges: List<String> = gcpIprangeService.getIpRangesByRegionAndByIpVersion(ipVersion = ipVersion)

        // then
        assertEquals(3, ipRanges.size)
        assertEquals("35.185.128.0/19", ipRanges[0])
        assertEquals("1.1.1.0/24", ipRanges[1])
        assertEquals("35.185.160.0/20", ipRanges[2])
    }

    @Test
    fun `get ip ranges by region ALL and by ip version IPV6`() {
        // given
        val ipVersion: IpVersion = IpVersion.IPV6
        val mockResponse = GcpIpRangesResponse(
            prefixes = listOf(
                GcpIpRange("35.185.128.0/19", null, "Google Cloud", "asia-east1"),
                GcpIpRange("1.1.1.0/24", null, "Google Cloud", "europe-west1"),
                GcpIpRange("35.185.160.0/20", null, "Google Cloud", "asia-east1"),
                GcpIpRange(null, "2600:1900:8000::/44", "Google Cloud", "africa-south1"),
                GcpIpRange(null, "2600:1900:4030::/44", "Google Cloud", "asia-east1")
            )
        )

        // rules
        `when`(restTemplate.getForObject(testUrl, GcpIpRangesResponse::class.java)).thenReturn(mockResponse)

        // when
        val ipRanges: List<String> = gcpIprangeService.getIpRangesByRegionAndByIpVersion(ipVersion = ipVersion)

        // then
        assertEquals(2, ipRanges.size)
        assertEquals("2600:1900:8000::/44", ipRanges[0])
        assertEquals("2600:1900:4030::/44", ipRanges[1])
    }
}
