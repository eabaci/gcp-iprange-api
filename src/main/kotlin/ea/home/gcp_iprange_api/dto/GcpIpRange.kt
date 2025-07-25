package ea.home.gcp_iprange_api.dto

data class GcpIpRange(
        val ipv4Prefix: String?,
        val ipv6Prefix: String?,
        val service: String,
        val scope: String
)
