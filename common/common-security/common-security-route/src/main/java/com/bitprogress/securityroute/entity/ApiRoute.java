package com.bitprogress.securityroute.entity;

import com.bitprogress.basemodel.Route;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiRoute extends Route {

    /**
     * 资源方法
     */
    private HttpMethod method;

    /**
     * 资源url
     */
    private String url;

}
