package ea.home.gcp.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class QueryParamValidationFilter : OncePerRequestFilter() {

    private val allowedParams = setOf("region", "ipVersion")

    public override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.requestURI.startsWith("/api/v1/gcp-ipranges")) {
            val unexpectedParams = request.parameterMap.keys - allowedParams
            if (unexpectedParams.isNotEmpty()) {
                response.status = HttpServletResponse.SC_BAD_REQUEST
                response.writer.write("Unerkannte Parameter: ${unexpectedParams.joinToString()}")
                return
            }
        }

        filterChain.doFilter(request, response)
    }
}
