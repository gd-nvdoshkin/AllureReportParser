package client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;

public interface BambooClient {

    @RequestLine("POST /userlogin.action")
    @Headers({"Accept: text/html", "Content-Type: application/x-www-form-urlencoded", "X-Atlassian-Token: no-check"})
    Response getJSessionId(@Param("os_username") String username,
                           @Param("os_password") String password);

    @RequestLine("GET /browse/{job}")
    @Headers({"Accept: text/html", "X-Atlassian-Token: no-check", "Cookie: {jSessionId}"})
    String getSubJobs(@Param("jSessionId") String jSessionId, @Param("job") String job);


    @RequestLine("GET {job}")
    @Headers({"Accept: text/html", "Content-Type: text/plain; charset=UTF-8",
            "Cookie: {jSessionId}"})
    String getTableText(@Param("jSessionId") String jSessionId, @Param("job") String job);
}
