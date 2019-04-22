package com.kgc.itrip.auth.controller;

import com.kgc.itrip.auth.exception.TokenException;
import com.kgc.itrip.auth.service.TokenService;
import com.kgc.itrip.beans.vo.ItripTokenVO;
import com.kgc.itrip.tuils.common.Dto;
import com.kgc.itrip.tuils.common.DtoUtil;
import com.kgc.itrip.tuils.common.ErrorCode;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

/**
 * Created by ASUS on 2019/1/25.
 */
@Controller
@RequestMapping(value = "/api")
public class TokenController {
    @Resource
    private TokenService tokenService;
    @ApiOperation(value = "客户端token置换",httpMethod = "POST",protocols = "HTTP",produces="application/json",response = Dto.class,notes = "提供客户端置换token操作，服务器需要获取客户端header中的token串")
    @ApiImplicitParam(paramType = "header",required = true,name = "token",value = "用户凭证")
    @RequestMapping(value ="/retoken",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto replace(HttpServletRequest request){
        String userAgent=request.getHeader("user-agent");
        String token=request.getHeader("token");
        try {
            String newToken = tokenService.replaceToken(userAgent, token);
            ItripTokenVO itripTokenVO=new ItripTokenVO(newToken, Calendar.getInstance().getTimeInMillis()+TokenService.SESSION_TIMEOUT*1000,Calendar.getInstance().getTimeInMillis());
            return DtoUtil.returnDataSuccess(itripTokenVO);
        } catch (TokenException e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(), ErrorCode.AUTH_REPLACEMENT_FAILED);
        }
    }

}
