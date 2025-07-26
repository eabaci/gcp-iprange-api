package ea.home.gcp.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GcpIpRangesResponseTest {

    @Test
    fun `Should correct IBAN validate without throwing any exception`() {
        // given
        val response = GcpIpRangesResponse(
            prefixes = listOf(
                GcpIpRange("35.185.128.0/19", null, "Google Cloud", "asia-east1"),
                GcpIpRange("1.1.1.0/24", null, "Google Cloud", "europe-west1"),
                GcpIpRange("35.185.160.0/20", null, "Google Cloud", "asia-east1"),
                GcpIpRange(null, "2600:1900:4030::/44", "Google Cloud", "asia-east1")
            )
        )

        // when
        assertEquals(4, response.prefixes.size)
    }
}
