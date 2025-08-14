package ea.home.gcp.dto

import ea.home.gcp.common.Region

data class GcpIpRange(
    val ipv4Prefix: String?,
    val ipv6Prefix: String?,
    val service: String,
    val scope: String?
) {
    fun isValid(): Boolean {
        return !(ipv4Prefix == null && ipv6Prefix == null)
    }

    fun matchesRegion(region: Region): Boolean {
        if (region == Region.ALL) return true

        if (scope.isNullOrBlank()) return false

        return region.scopes.any { scopeValue ->
            scope.contains(scopeValue, ignoreCase = true)
        }
    }
}
