package ea.home.gcp.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GcpIpRangesResponseTest {

    @Test
    fun `should contain provided list of GcpIpRange`() {
        val range1 = GcpIpRange(
            ipv4Prefix = "34.35.0.0/16",
            ipv6Prefix = null,
            service = "Google Cloud",
            scope = "europe-west1"
        )
        val range2 = GcpIpRange(
            ipv4Prefix = null,
            ipv6Prefix = "2001:4860::/32",
            service = "Google Cloud",
            scope = "us-west1"
        )

        val response = GcpIpRangesResponse(listOf(range1, range2))

        assertEquals(2, response.prefixes.size)
        assertEquals("34.35.0.0/16", response.prefixes[0].ipv4Prefix)
        assertEquals("us-west1", response.prefixes[1].scope)
    }

    @Test
    fun `should create empty response when given empty list`() {
        val response = GcpIpRangesResponse(emptyList())

        assertEquals(0, response.prefixes.size)
    }
}
