package com.bitprogress.securityroute.property;

import com.bitprogress.securityroute.entity.PermissionRoute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties("spring.security.route.permission")
public class PermissionRouteProperties {

    /**
     * 是否覆盖
     */
    Boolean cover = false;

    private Set<PermissionRoute> routes = new HashSet<>();

}
