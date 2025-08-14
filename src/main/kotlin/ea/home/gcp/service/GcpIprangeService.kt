package ea.home.gcp.service

import ea.home.gcp.common.IpVersion
import ea.home.gcp.common.Region
import ea.home.gcp.dto.GcpIpRange
import ea.home.gcp.dto.GcpIpRangesResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.ResourceAccessException
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import java.net.SocketTimeoutException

@Service
class GcpIprangeService(
    private val restTemplate: RestTemplate,
    @Value("\${gcp.ipranges.url}") private val gcpIpRangesUrl: String
) {

    fun getIpRanges(): List<GcpIpRange> {
        return try {
            restTemplate.getForObject(gcpIpRangesUrl, GcpIpRangesResponse::class.java)?.prefixes ?: emptyList()
        } catch (e: SocketTimeoutException) {
            throw RuntimeException("Timeout beim Verbinden: ${e.message}", e)
        } catch (e: ResourceAccessException) {
            throw RuntimeException("Netzwerkfehler beim Aufruf der API: ${e.message}", e)
        } catch (e: RestClientException) {
            throw RuntimeException("REST-Client-Fehler: ${e.message}", e)
        } catch (e: Exception) {
            throw RuntimeException("Unerwarteter Fehler: ${e.message}", e)
        }
    }

    fun getIpRangesByRegionAndByIpVersion(
        region: Region = Region.ALL,
        ipVersion: IpVersion = IpVersion.ALL
    ): List<String> {
        val ipRanges = getIpRanges()

        val filteredByRegion = ipRanges.filter { it.isValid() }
            .filter { it.matchesRegion(region) }
            .toList()

        return filteredByRegion.mapNotNull { prefix ->
            when (ipVersion) {
                IpVersion.IPV4 -> prefix.ipv4Prefix
                IpVersion.IPV6 -> prefix.ipv6Prefix
                IpVersion.ALL -> prefix.ipv4Prefix ?: prefix.ipv6Prefix
            }
        }
    }
}
