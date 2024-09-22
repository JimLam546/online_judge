package com.jim.ojbackendjudgeservice.judge.codesandbox.impl;


import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.jim.ojbackendcommon.common.ErrorCode;
import com.jim.ojbackendcommon.exception.BusinessException;
import com.jim.ojbackendcommon.model.codesandbox.ExecuteCodeRequest;
import com.jim.ojbackendcommon.model.codesandbox.ExecuteCodeResponse;
import com.jim.ojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import org.apache.commons.lang3.StringUtils;

/**
 * 远程代码沙箱（实际调用接口的沙箱）
 */
public class RemoteCodeSandbox implements CodeSandbox {
    // 定义鉴权请求头和密钥
    private static final String AUTH_REQUEST_HEADER = "auth";

    private static final String AUTH_REQUEST_SECRET = "secretKey";
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        // 本地代码沙箱
        // String url = "http://localhost:8090/executeCode";
        String url = "http://192.168.233.131:8090/executeCode";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                .body(json).timeout(5000)
                .execute()
                .body();
        if (StringUtils.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "执行远程代码沙箱错误, 信息= = " + responseStr);
        }
        System.out.println("responseStr=" + responseStr);
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
    }
}
