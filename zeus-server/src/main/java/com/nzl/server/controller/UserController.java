package com.nzl.server.controller;

import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.dao.UserMapper;
import com.nzl.model.dto.UserDto;
import com.nzl.server.common.ServerConstant;
import com.nzl.server.service.ServerUserService;
import com.nzl.server.service.impl.FileServiceImpl;
import com.nzl.server.service.impl.ServerUserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author: nizonglong
 * @date: 2020/3/21 21:48
 * @desc:
 * @version: 0.1
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private FileServiceImpl fileService;
    @Resource
    private ServerUserServiceImpl userService;

    @PostMapping("/updateHeadPic")
    ZeusResponseBean updateHeadPic(@RequestParam("file") MultipartFile file, @RequestParam("uid") String uid) {
        try {
            UserDto userDto = userService.getUser(uid);
            String path = ServerConstant.UPLOADPATH + userDto.getHeadPortraitUrl();
            int delRes = fileService.deleteFile(path);
            if (delRes <= 0) {
                return ZeusResponseBean.build(HttpStatus.BAD_REQUEST.value(), "头像更新异常，情重新尝试");
            } else {
                String res = fileService.uploadOne(file, ServerConstant.UPLOADPATH);
                if (res != null) {
                    return userService.updateHeadPic(res, uid);
                }
            }
        } catch (Exception e) {
            return ZeusResponseBean.build(HttpStatus.BAD_REQUEST.value(), "头像更新异常，情重新尝试");
        }
        return ZeusResponseBean.build(HttpStatus.BAD_REQUEST.value(), "头像更新异常，情重新尝试");
    }
}
