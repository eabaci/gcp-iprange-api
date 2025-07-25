package ea.home.gcp_iprange_api.controller

import ea.home.gcp_iprange_api.common.IpVersion
import ea.home.gcp_iprange_api.common.Region
import ea.home.gcp_iprange_api.service.GcpIprangeService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/gcp-ipranges")
class GcpIprangeController(val gcpIpRangeService: GcpIprangeService) {

    @GetMapping(produces = [MediaType.TEXT_PLAIN_VALUE])
    fun getIpRangesByRegion(
            @RequestParam(required = false, defaultValue = "ALL") region: String,
            @RequestParam(required = false, defaultValue = "ALL") ipVersion: String
    ): ResponseEntity<Any> {

        val regionEnum = try {
            Region.valueOf(region.uppercase())
        } catch (ex: IllegalArgumentException) {
            return ResponseEntity.badRequest().body("Ungültige Region: '$region'. Erlaubte Werte sind: ${Region.entries.joinToString()}")

        }
        val ipVersionEnum = try {
            IpVersion.valueOf(ipVersion.uppercase())
        } catch (ex: IllegalArgumentException) {
            return  ResponseEntity.badRequest().body("Ungültige ip Version: '$ipVersion'. Erlaubte Werte sind: ${IpVersion.entries.joinToString()}")
        }

        val results = gcpIpRangeService.getIpRangesByRegionAndByIpVersion(regionEnum, ipVersionEnum)

        return ResponseEntity.ok(results.joinToString("\n"))
    }
}