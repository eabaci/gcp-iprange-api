package ea.home.gcp.dto

import ea.home.gcp.common.Region
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class GcpIpRangeTest {

    @Test
    fun `isValid should return true when ipv4Prefix is present`() {
        val range = GcpIpRange(
            ipv4Prefix = "34.35.0.0/16",
            ipv6Prefix = null,
            service = "Google Cloud",
            scope = "europe-west1"
        )

        assertTrue(range.isValid())
    }

    @Test
    fun `isValid should return true when ipv6Prefix is present`() {
        val range = GcpIpRange(
            ipv4Prefix = null,
            ipv6Prefix = "2600:1900:8000::/44",
            service = "Google Cloud",
            scope = "europe-west1"
        )

        assertTrue(range.isValid())
    }

    @Test
    fun `isValid should return false when both prefixes are null`() {
        val range = GcpIpRange(
            ipv4Prefix = null,
            ipv6Prefix = null,
            service = "Google Cloud",
            scope = "europe-west1"
        )

        assertFalse(range.isValid())
    }

    @Test
    fun `matchesRegion should return true if scope matches region scopes`() {
        val range = GcpIpRange(
            ipv4Prefix = "34.35.0.0/16",
            ipv6Prefix = null,
            service = "Google Cloud",
            scope = "europe-west1"
        )

        assertTrue(range.matchesRegion(Region.EU))
    }

    @Test
    fun `matchesRegion should return false if scope does not match region scopes`() {
        val range = GcpIpRange(
            ipv4Prefix = "34.35.0.0/16",
            ipv6Prefix = null,
            service = "Google Cloud",
            scope = "us-central1"
        )

        assertFalse(range.matchesRegion(Region.EU))
    }

    @Test
    fun `matchesRegion should return true if region is ALL`() {
        val range = GcpIpRange(
            ipv4Prefix = "34.35.0.0/16",
            ipv6Prefix = null,
            service = "Google Cloud",
            scope = "mars-south1"
        )

        assertTrue(range.matchesRegion(Region.ALL))
    }

    @Test
    fun `matchesRegion should return false if scope is null`() {
        val range = GcpIpRange(
            ipv4Prefix = "34.35.0.0/16",
            ipv6Prefix = null,
            service = "Google Cloud",
            scope = null
        )

        assertFalse(range.matchesRegion(Region.EU))
    }

    @Test
    fun `matchesRegion should return false if scope is blank`() {
        val range = GcpIpRange(
            ipv4Prefix = "34.35.0.0/16",
            ipv6Prefix = null,
            service = "Google Cloud",
            scope = " "
        )

        assertFalse(range.matchesRegion(Region.EU))
    }

    @Test
    fun `isValid should return false for invalid ipv4Prefix`() {
        val range = GcpIpRange(
            ipv4Prefix = "34.80.0.0/abc",
            ipv6Prefix = null,
            service = "Google Cloud",
            scope = "europe-west1"
        )

        assertFalse(range.isValid())
    }

    @Test
    fun `isValid should return false for invalid ipv6Prefix`() {
        val range = GcpIpRange(
            ipv4Prefix = null,
            ipv6Prefix = "2600:1900:::/zzz",
            service = "Google Cloud",
            scope = "europe-west1"
        )

        assertFalse(range.isValid())
    }

    @Test
    fun `isValid should return false for completely invalid ipv4Prefix`() {
        val range = GcpIpRange(
            ipv4Prefix = "999.999.999.999/24",
            ipv6Prefix = null,
            service = "Google Cloud",
            scope = "europe-west1"
        )

        assertFalse(range.isValid())
    }

    @Test
    fun `isValid should return false for invalid subnet size in ipv4`() {
        val range = GcpIpRange(
            ipv4Prefix = "10.0.0.0/35",
            ipv6Prefix = null,
            service = "Google Cloud",
            scope = "europe-west1"
        )

        assertFalse(range.isValid())
    }

    @Test
    fun `isValid should return false for invalid subnet size in ipv6`() {
        val range = GcpIpRange(
            ipv4Prefix = null,
            ipv6Prefix = "2001:db8::/129",
            service = "Google Cloud",
            scope = "europe-west1"
        )

        assertFalse(range.isValid())
    }
}
