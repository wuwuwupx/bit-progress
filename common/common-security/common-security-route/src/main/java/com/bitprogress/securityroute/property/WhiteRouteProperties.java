package com.bitprogress.securityroute.property;

import com.bitprogress.securityroute.entity.ApiRoute;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class WhiteRouteProperties {

    private List<ApiRoute> routes = new ArrayList<>();

}
