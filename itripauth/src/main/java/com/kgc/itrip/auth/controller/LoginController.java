package com.kgc.itrip.auth.controller;

import com.alibaba.fastjson.JSON;
import com.kgc.itrip.auth.service.ItripUserService;
import com.kgc.itrip.auth.service.TokenService;
import com.kgc.itrip.beans.model.ItripUser;
import com.kgc.itrip.tuils.common.*;

import com.kgc.itrip.tuils.userinfo.ItripTokenVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

/**
 * Created by ASUS on 2018/7/10.
 */
@Controller
@Api(value = "API")
@RequestMapping("/api")
public class LoginController {

    @Resource
    private ItripUserService itripUserService;
    @Resource
    private TokenService tokenService;
    @ApiOperation(value = "用户登录",httpMethod = "Post",produces = "HTTP",protocols = "application/json",response = Dto.class,notes = "根据用户名，密码进行统一认证")
    @RequestMapping(value = "/dologin", method = RequestMethod.POST,
            produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form",required = true,value = "用户名",name = "name",defaultValue = "1044732267@qq.com"),
            @ApiImplicitParam(paramType = "form",required = true,value = "密码",name = "password",defaultValue = "123456"),
    })
    @ResponseBody
    public Dto login(@RequestParam(value = "name")String userCode, @RequestParam("password")String password, HttpServletRequest rep){

        ItripUser itripUser=null;
        if (!EmptyUtils.isEmpty(userCode)&&!EmptyUtils.isEmpty(password)){
            try {
                itripUser=itripUserService.login(userCode,password);
            } catch (itripException e) {
                e.printStackTrace();
                return DtoUtil.returnFail(e.getMessage(), ErrorCode.AUTH_AUTHENTICATION_FAILED);
            }catch (Exception e){
                e.printStackTrace();
                return DtoUtil.returnFail(e.getMessage(),ErrorCode.AUTH_UNKNOWN);
            }
            if (!EmptyUtils.isEmpty(itripUser)){
                String token=tokenService.generateToKen(rep.getHeader("user-agent"),itripUser);
                tokenService.save(token,itripUser);
                ItripTokenVO tokenVO=new ItripTokenVO(token,Calendar.getInstance().getTimeInMillis()+TokenService.SESSION_TIMEOUT*1000,Calendar.getInstance().getTimeInMillis());
                return  DtoUtil.returnDataSuccess(tokenVO);
            }else {
                return DtoUtil.returnFail("用户名密码错误",ErrorCode.AUTH_AUTHENTICATION_FAILED);
            }
        }else {
            return  DtoUtil.returnFail("参数错误！检查提交的参数名称是否正确",ErrorCode.AUTH_PARAMETER_ERROR);
        }
    }
    @ApiOperation(value = "用户注销",httpMethod = "GET",produces = "HTTP",protocols = "application/json",response = Dto.class,notes = "客户端需要在header中发送token")
    @ApiImplicitParam(paramType = "header",required = true,name = "token",value = "用户认证凭证")
    @RequestMapping(value = "/logout",method = RequestMethod.GET,produces = "application/json",headers = "token")
    @ResponseBody
    public Dto logout(HttpServletRequest request){
        String token=request.getHeader("token");
        if (!tokenService.tokenisNo(request.getHeader("user-agent"),token)){
            return DtoUtil.returnFail("token无效",ErrorCode.AUTH_TOKEN_INVALID);
        }
        try {
            tokenService.del(token);
            return DtoUtil.returnSuccess("注销成功");
        }catch (Exception e){
            e.printStackTrace();
            return DtoUtil.returnFail("注销失败",ErrorCode.AUTH_UNKNOWN);
        }
    }

}
