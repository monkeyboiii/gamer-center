package com.sustech.gamercenter.controller;

import com.sustech.gamercenter.model.JsonResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

//    AdminService adminService;
//
//    @Autowired
//    public AdminController(AdminService adminService) {
//        this.adminService = adminService;
//    }


    @GetMapping("/user/info")
    public JsonResponse getUserInfo(@RequestParam("admin_id") Integer admin_id,
                                    @RequestParam("user_id") Integer user_id,
                                    @RequestParam("user_name") String name,
                                    @RequestParam("user_email") String email
    ) {
        return new JsonResponse(0, "Successfully retrieved");
    }


    @PostMapping("/assign")
    public JsonResponse assignUserRoleById(@RequestParam("admin_id") Integer admin_id,
                                           @RequestParam("user_id") Integer user_id,
                                           @RequestParam("role") String role
    ) {

        return new JsonResponse(0, "Successfully assigned");
    }


    @DeleteMapping("/community/comment")
    public JsonResponse deleteCommentById(@RequestParam("admin_id") Integer admin_id,
                                          @RequestParam("comment_id") Integer comment_id) {
        return new JsonResponse(0, "Successfully deleted");
    }


    @DeleteMapping("/community/post")
    public JsonResponse deletePostById(@RequestParam("admin_id") Integer admin_id,
                                       @RequestParam("post_id") Integer post_id) {
        return new JsonResponse(0, "Successfully deleted");
    }

}
