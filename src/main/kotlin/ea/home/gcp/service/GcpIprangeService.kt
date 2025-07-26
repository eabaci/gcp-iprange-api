package ea.home.gcp.service

import ea.home.gcp.common.IpVersion
import ea.home.gcp.common.Region
import ea.home.gcp.dto.GcpIpRange
import ea.home.gcp.dto.GcpIpRangesResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class GcpIprangeService(
    private val restTemplate: RestTemplate,
    @Value("\${gcp.ipranges.url}") private val gcpIpRangesUrl: String
) {

    fun getIpRanges(): List<GcpIpRange> {
        val response = restTemplate.getForObject(gcpIpRangesUrl, GcpIpRangesResponse::class.java)
        return response?.prefixes ?: emptyList()
    }

    fun getIpRangesByRegionAndByIpVersion(
        region: Region = Region.ALL,
        ipVersion: IpVersion = IpVersion.ALL
    ): List<String> {
        val ipRanges = getIpRanges()

        val filteredByRegion = if (region == Region.ALL) {
            ipRanges
        } else {
            ipRanges.filter { prefix ->
                region.scopes.any { scope -> prefix.scope.contains(scope, ignoreCase = true) }
            }
        }

        return filteredByRegion.mapNotNull { prefix ->
            when (ipVersion) {
                IpVersion.IPV4 -> prefix.ipv4Prefix
                IpVersion.IPV6 -> prefix.ipv6Prefix
                IpVersion.ALL -> prefix.ipv4Prefix ?: prefix.ipv6Prefix
            }
        }
    }
}
