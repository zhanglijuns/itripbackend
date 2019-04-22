package com.kgc.itrip.auth.controller;

import com.kgc.itrip.auth.service.ItripUserService;
import com.kgc.itrip.beans.model.ItripUser;
import com.kgc.itrip.beans.vo.userinfo.ItripUserVO;
import com.kgc.itrip.tuils.common.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by ASUS on 2019/1/25.
 */
@Controller
@RequestMapping(value = "/api")
public class UserController {
    @Resource
    private ItripUserService itripUserService;
    @ApiOperation(value = "使用邮箱注册",httpMethod = "POST",protocols = "HTTP",produces ="application/json",response = Dto.class,notes = "使用邮箱注册")
    @RequestMapping(value = "/doregister",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
public Dto doRegister(@ApiParam(name = "userVO",value = "实体类",required = true)@RequestBody ItripUserVO itripUserVO){
        if (!validEmail(itripUserVO.getUserCode())){
            return DtoUtil.returnFail("请正确使用邮箱地址", ErrorCode.AUTH_ILLEGAL_USERCODE);
        }
        ItripUser itripUser=new ItripUser();
        itripUser.setUserCode(itripUserVO.getUserCode());
        itripUser.setUserPassword(itripUserVO.getUserPassword());
        itripUser.setUserName(itripUserVO.getUserName());
        itripUser.setUserType(0);
        Map map=new HashMap();
        map.put("userCode",itripUserVO.getUserCode());
        try{
            List listByMap = itripUserService.getListByMap(map);
            if (listByMap==null || listByMap.size()==0){
                itripUser.setUserPassword(DigestUtil.hmacSign(itripUser.getUserPassword(),"kgc"));
                    itripUserService.registerByEmail(itripUser);
                    return  DtoUtil.returnSuccess();
            }else {
                return DtoUtil.returnFail("用户名已存在",ErrorCode.AUTH_USER_ALREADY_EXISTS);
            }
        }catch (Exception e){
            e.printStackTrace();
            return DtoUtil.returnFail("程序异常",ErrorCode.AUTH_UNKNOWN);
        }
}
    /**
     * 检查用户是否已注册
     * @param name
     * @return
     */
    @ApiOperation(value="用户名验证",httpMethod = "GET",
            protocols = "HTTP", produces = "application/json",
            response = Dto.class,notes="验证是否已存在该用户名")
    @RequestMapping(value="/ckusr",method=RequestMethod.GET,produces= "application/json")
    @ResponseBody
    public Dto checkUser(@ApiParam(name="name",value="被检查的用户名") @RequestParam(value = "name") String name) {
        try {
		Map map=new HashMap();
		map.put("userCode",name);
            if (null == itripUserService.getListByMap(map) || itripUserService.getListByMap(map).size()==0)
            {
                return DtoUtil.returnSuccess("用户名可用");
            }
            else
            {
                return DtoUtil.returnFail("用户已存在，注册失败", ErrorCode.AUTH_USER_ALREADY_EXISTS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(), ErrorCode.AUTH_UNKNOWN);
        }
    }
    /**
     * 使用手机注册
     * @param userVO
     * @return
     */
    @ApiOperation(value="使用手机注册",httpMethod = "POST",
            protocols = "HTTP", produces = "application/json",
            response = Dto.class,notes="使用手机注册 ")
    @RequestMapping(value="/registerbyphone",method=RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto registerByPhone(@ApiParam(name="userVO",value="用户实体",required=true) @RequestBody ItripUserVO userVO){
        try {
            if(!validPhone(userVO.getUserCode())) {
                return DtoUtil.returnFail("请使用正确的手机号注册", ErrorCode.AUTH_ILLEGAL_USERCODE);
            }
            ItripUser user=new ItripUser();
            user.setUserCode(userVO.getUserCode());
            user.setUserPassword(userVO.getUserPassword());
            user.setUserType(0);
            user.setUserName(userVO.getUserName());
            Map map=new HashMap();
            map.put("userCode",user.getUserCode());
            if (itripUserService.getListByMap(map)==null ||itripUserService.getListByMap(map).size()==0 ) {
                user.setUserPassword(DigestUtil.hmacSign(user.getUserPassword(), "kgc"));
                itripUserService.registerByPhone(user);
                return DtoUtil.returnSuccess();
            }else
            {
                return DtoUtil.returnFail("用户已存在，注册失败", ErrorCode.AUTH_USER_ALREADY_EXISTS);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(), ErrorCode.AUTH_UNKNOWN);
        }
    }
    @ApiOperation(value="邮箱注册用户激活",httpMethod = "PUT", protocols = "HTTP", produces = "application/json", response = Dto.class,notes="邮箱激活")
    @RequestMapping(value="/activate",method=RequestMethod.PUT,produces= "application/json")
    @ResponseBody
    public  Dto activate(
            @ApiParam(name="user",value="注册邮箱地址",defaultValue="test@bdqn.cn") @RequestParam String user, @ApiParam(name="code",value="激活码",defaultValue="018f9a8b2381839ee6f40ab2207c0cfe") @RequestParam String code){
        try {
            if(itripUserService.activate(user, code))
            {
                return DtoUtil.returnSuccess("激活成功");
            }else{
                return DtoUtil.returnSuccess("激活失败");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return DtoUtil.returnFail("激活失败", ErrorCode.AUTH_ACTIVATE_FAILED);
        }
    }
    @ApiOperation(value="手机注册用户短信验证",httpMethod = "PUT",
            protocols = "HTTP", produces = "application/json",
            response = Dto.class,notes="手机注册短信验证")
    @RequestMapping(value="/validatephone",method=RequestMethod.PUT,produces= "application/json")
    public @ResponseBody Dto validatePhone(
            @ApiParam(name="user",value="手机号码",defaultValue="18513311526")
            @RequestParam String user,
            @ApiParam(name="code",value="验证码",defaultValue="8888")
            @RequestParam String code){
        try {
            if(itripUserService.validatePhone(user, code))
            {
                return DtoUtil.returnSuccess("验证成功");
            }else{
                return DtoUtil.returnSuccess("验证失败");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return DtoUtil.returnFail("验证失败", ErrorCode.AUTH_ACTIVATE_FAILED);
        }
    }
    private boolean validEmail(String email){

        String regex="^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$"  ;
        return Pattern.compile(regex).matcher(email).find();
    }
    /**
     * 验证是否合法的手机号
     * @param phone
     * @return
     */
    private boolean validPhone(String phone) {
        String regex="^1[3578]{1}\\d{9}$";
        return Pattern.compile(regex).matcher(phone).find();
    }
}
