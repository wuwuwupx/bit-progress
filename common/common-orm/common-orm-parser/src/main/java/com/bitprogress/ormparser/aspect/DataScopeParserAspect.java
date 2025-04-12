package com.bitprogress.ormparser.aspect;

import com.bitprogress.ormmodel.annotation.DataScopeParserMode;
import com.bitprogress.ormmodel.enums.Propagation;
import com.bitprogress.ormmodel.info.parser.DataScopeParserInfo;
import com.bitprogress.ormcontext.service.DataScopeContextService;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
public class DataScopeParserAspect {

    @Autowired
    private DataScopeContextService<?, ?> dataScopeContextService;

    @Around("@annotation(dataScopeParserMode)")
    @SneakyThrows
    public Object around(ProceedingJoinPoint point, DataScopeParserMode dataScopeParserMode) {
        // 已存在的 sql解析模式信息

        final Optional<DataScopeParserInfo> preDataScopeParserMsg = Optional
                .ofNullable(dataScopeContextService.getParserInfo());

        // 当前注解标注的 sql解析模式信息
        Propagation propagation = dataScopeParserMode.propagation();

        /*
         * 设置当前的 sql解析模式和 sql类型
         */
        try {
            // 根据传播方式设置 sql解析模式
            switch (propagation) {
                // 默认传播方式
                case REQUIRED -> {
                    // 没有解析模式则新建
                    if (preDataScopeParserMsg.isEmpty()) {
                        DataScopeParserInfo dataScopeParserInfo = DataScopeParserInfo
                                .createByDataScopeParserMode(dataScopeParserMode);
                        dataScopeContextService.setParserInfo(dataScopeParserInfo);
                    }
                }
                // 开启一个新的解析模式
                case REQUIRES_NEW -> {
                    DataScopeParserInfo dataScopeParserInfo = DataScopeParserInfo
                            .createByDataScopeParserMode(dataScopeParserMode);
                    dataScopeContextService.setParserInfo(dataScopeParserInfo);
                }
                // 关闭 sql解析模式
                case NOT_SUPPORTED ->
                        dataScopeContextService.setParserInfo(DataScopeParserInfo.createDisable());
            }
            /*
             * 执行当前方法
             */
            return point.proceed();
        } finally {
            /*
             * 清除当前 sql解析模式
             */
            dataScopeContextService.clearParserInfo();
            /*
             * 重置 sql解析模式和 sql类型
             */
            preDataScopeParserMsg.ifPresent(dataScopeContextService::setParserInfo);
        }
    }

}
