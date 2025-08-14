package ea.home.gcp.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.test.assertEquals

class QueryParamValidationFilterTest {

    private val filter = QueryParamValidationFilter()

    @Test
    fun `should return 400 and error message when unknown parameters are present`() {
        // given
        val request = mock(HttpServletRequest::class.java)
        val response = mock(HttpServletResponse::class.java)
        val filterChain = mock(FilterChain::class.java)

        `when`(request.requestURI).thenReturn("/api/v1/gcp-ipranges")
        `when`(request.parameterMap).thenReturn(mapOf("regin" to arrayOf("eu")))

        val stringWriter = StringWriter()
        val printWriter = PrintWriter(stringWriter)
        `when`(response.writer).thenReturn(printWriter)

        // when
        filter.doFilterInternal(request, response, filterChain)
        printWriter.flush()

        // then
        verify(response).status = HttpServletResponse.SC_BAD_REQUEST
        assertEquals("Unerkannte Parameter: regin", stringWriter.toString())

        verify(filterChain, never()).doFilter(any(), any())
    }

    @Test
    fun `should continue filter chain when all parameters are valid`() {
        // given
        val request = mock(HttpServletRequest::class.java)
        val response = mock(HttpServletResponse::class.java)
        val filterChain = mock(FilterChain::class.java)

        `when`(request.requestURI).thenReturn("/api/v1/gcp-ipranges")
        `when`(request.parameterMap).thenReturn(mapOf("region" to arrayOf("eu"), "ipVersion" to arrayOf("ipv6")))

        // when
        filter.doFilterInternal(request, response, filterChain)

        // then
        verify(filterChain).doFilter(request, response)
        verify(response, never()).status = HttpServletResponse.SC_BAD_REQUEST
    }
}
