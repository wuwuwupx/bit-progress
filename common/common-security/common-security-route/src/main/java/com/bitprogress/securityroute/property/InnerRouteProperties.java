package com.bitprogress.securityroute.property;

import com.bitprogress.securityroute.entity.ApiRoute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties("spring.security.route.inner")
public class InnerRouteProperties {

    /**
     * 是否覆盖
     */
    Boolean cover = false;

    private Set<ApiRoute> routes = new HashSet<>();

}
