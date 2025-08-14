package ea.home.gcp.dto

import ea.home.gcp.common.Region
import inet.ipaddr.IPAddressString

data class GcpIpRange(
    val ipv4Prefix: String?,
    val ipv6Prefix: String?,
    val service: String,
    val scope: String?
) {

    fun isValid(): Boolean {
        val validIpv4 = ipv4Prefix?.let { isValidCidr(it) } ?: false
        val validIpv6 = ipv6Prefix?.let { isValidCidr(it) } ?: false

        return validIpv4 || validIpv6
    }

    fun matchesRegion(region: Region): Boolean {
        if (region == Region.ALL) return true

        if (scope.isNullOrBlank()) return false

        return region.scopes.any { scopeValue ->
            scope.contains(scopeValue, ignoreCase = true)
        }
    }

    private fun isValidCidr(prefix: String): Boolean {
        return try {
            IPAddressString(prefix).isValid
        } catch (e: Exception) {
            false
        }
    }
}
