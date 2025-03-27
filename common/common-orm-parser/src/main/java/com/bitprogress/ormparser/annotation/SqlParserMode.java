package com.bitprogress.ormparser.annotation;

import com.bitprogress.ormcontext.utils.DataScopeContextUtils;
import com.bitprogress.ormcontext.utils.TenantContextUtils;
import com.bitprogress.ormmodel.enums.*;
import com.bitprogress.ormparser.context.SqlParserContext;
import com.bitprogress.ormparser.entity.SqlParserMsg;

import java.lang.annotation.*;

/**
 * sql解析模式
 * 本注解主要是用于规避 sql解析，所以默认的解析模式是忽略解析
 * 解析模式的开启表现为 {@link SqlParserContext#getSqlParserMsg()} != null && {@link SqlParserMsg#getEnable()} == true
 * 当前方法开启新的解析模式的时候，会将上层方法的解析模式状态擦除，直到当前方法结束。
 * 假设 （1）方法A的解析方式为过滤解析，sql类型为 NONE  （2）方法B解析方式为强制解析，sql类型为 SELECT
 * 当方法A调用方法B的时，方法B中的 UPDATE 语句仍会被插件进行默认解析处理（实际是否进行处理，最终以系统是否开启sql处理插件为准）
 * 因为虽然方法A过滤掉所有的类型 sql的处理，但在方法B的作用域中，方法A的解析模式状态已被擦除
 * <p>
 * 引入传播机制，借鉴 Transactional 的传播机制
 * - 跟随当前解析模式，没有则创建新的解析模式
 * - 跟随当前解析模式
 * - 开启一个新的解析模式
 * - 关闭当前的 sql解析模式
 * <p>
 * 引入 sql类型
 * - 包含 {@link SqlType#NONE} 则表示对所有 sql类型生效
 * <p>
 * 引入租户模式开关
 * - 默认不开启
 * <p>
 * 引入租户类型，默认当前租户
 * - {@link TenantType#ALL} 所有租户，sql解析后设置租户ID为操作用户可操作的租户ID {@link TenantContextUtils#getOperateTenantIds()}
 * - {@link TenantType#CURRENT} 当前租户，sql解析后设置租户ID为操作用户当前的租户ID {@link TenantContextUtils#getTenantId()}
 * - {@link TenantType#OPERATE} 被操作租户，sql解析后设置租户ID为操作的租户ID {@link TenantContextUtils#getOperateTenantId()}
 * 应该在设置 {@link TenantContextUtils#setOperateTenantId(Long)} 时校验是否有权限操作该租户
 * <p>
 * 引入rpc传播方式
 * - true，rpc调用传播当前解析模式
 * - false，rpc调用不传播当前解析模式
 * <p>
 * 引入数据范围模式开关
 * 默认不开启
 * <p>
 * 引入数据范围类型
 * 默认为自身
 * - {@link DataScopeType#ALL} 全部，不需要做数据范围覆盖
 * - {@link DataScopeType#COMPOSITE_LEVEL} 数据范围，sql解析后设置数据范围为操作用户当前的数据范围 {@link DataScopeContextUtils#getDataScopes()}
 * - {@link DataScopeType#SELF} 自身，sql解析后设置数据范围为操作用户当前的数据范围 {@link DataScopeContextUtils#getUserId()}
 * <p>
 * 可考虑将当前注解拆开为全局解析开关、租户、数据权限注解（参考mybatis-plus原有的租户和数据权限模式）
 * 但是考虑到传播属性和优先级设计等问题，且当前注解的本意是为了一键去除租户模式和数据权限模式而创建，所以暂不进行拆分
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SqlParserMode {

    /**
     * 传播模式
     */
    Propagation propagation() default Propagation.REQUIRED;

    /**
     * sql 类型
     * 注解生效的 sql类型
     */
    SqlType[] sqlTypes() default {SqlType.NONE};

    /**
     * 解析类型
     * 默认忽略解析
     */
    ParserType parserType() default ParserType.EXCLUDE;

    /**
     * 是否启用租户
     */
    boolean tenantEnabled() default false;

    /**
     * 租户类型，默认当前租户
     */
    TenantType tenantType() default TenantType.CURRENT;

    /**
     * 是否启用数据范围
     */
    boolean dataScopeEnabled() default false;

    /**
     * 数据范围类型
     */
    DataScopeType dataScopeType() default DataScopeType.SELF;

    /**
     * rpc传播
     */
    boolean rpcPropagate() default false;

}
