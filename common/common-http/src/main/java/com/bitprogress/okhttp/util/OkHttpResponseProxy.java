package com.bitprogress.okhttp.util;

import okhttp3.Response;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 * 三种http框架的response代理类，方便提取公共方法
 * Created by Binary Wang on 2017-8-3.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public class OkHttpResponseProxy {
  private static final Pattern PATTERN = Pattern.compile(".*filename=\"(.*)\"");

  public static String getFileName(Response response) {
    String content = response.header("Content-disposition");
    Matcher m = PATTERN.matcher(content);
    if (m.matches()) {
      return m.group(1);
    }
    throw new RuntimeException("无法获取到文件名，header信息有问题");
  }

}
