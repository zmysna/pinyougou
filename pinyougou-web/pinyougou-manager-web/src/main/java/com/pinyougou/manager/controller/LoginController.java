package com.pinyougou.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * 用户登录的控制器
 *
 * @Author zmysna
 * @Version 1.0
 * @Date 2019/2/25 21:26
 */
@Controller
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping("/login")
    public String login(HttpServletRequest request, String username, String password, String vcode) {
        // 判断请求方式
        HttpSession session = request.getSession();
        String sessionValue = (String) session.getAttribute("vcode");
        if (vcode != null && vcode.equalsIgnoreCase(sessionValue)) {
            if (request.getMethod().equalsIgnoreCase("post")) {
                System.out.println(username + "==" + password);
                // 创建用户名与密码认证对象
                UsernamePasswordAuthenticationToken token
                        = new UsernamePasswordAuthenticationToken(username, password);
                try {
                    // 调用认证方法，返回认证对象
                    Authentication authenticate = authenticationManager.authenticate(token);
                    // 判断是否认证成功
                    if (authenticate.isAuthenticated()) {
                        // 设置用户认证成功，往Session中添加认证通过信息
                        SecurityContextHolder.getContext().setAuthentication(authenticate);
                        // 重定向到登录成功页面
                        return "redirect:/admin/index.html";
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        // 重定向到登录页面
        return "redirect:/login.html";

    }

    private Random ran = new Random();      //随机类

    //1) 写一个方法随机获取颜色
    private Color getColor() {
        //红，绿，蓝取值0-255，随机生成 0-255之间数
        int r = ran.nextInt(256);
        int g = ran.nextInt(256);
        int b = ran.nextInt(256);
        return new Color(r, g, b);
    }

    @RequestMapping("/vcode")
    public void vcode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //2) 创建缓存图片：指定宽
        int width = 148, height = 29;
        //参数3：图片的类型，使用RGB类型
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //3) 获取画笔对象
        Graphics graphics = image.getGraphics();
        //4) 设置画笔颜色，并且填充矩形区域
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        //创建一个StringBuider对象
        StringBuilder sb = new StringBuilder();
        //5) 从字符数组中随机得到字符
        char[] arr = {'A', 'B', 'C', 'D', 'N', 'E', 'W', 'b', 'o', 'y', '1', '2', '3', '4', '5', '6'};
        for (int i = 0; i < 4; i++) {
            //随机从0到长度之间去取索引
            int index = ran.nextInt(arr.length);
            //得到1个字符
            char c = arr[index];
            sb.append(c);
            //6) 设置字体，大小为18，设置字的颜色随机
            graphics.setFont(new Font(Font.DIALOG, Font.BOLD + Font.ITALIC, 18));
            graphics.setColor(getColor());
            //7) 将每个字符画到图片，位置：5+(i*20), 20
            //将字符转成字符串
            graphics.drawString(String.valueOf(c), 5 + (i * 20), 20);
        }
        System.out.println("生成的验证码:" + sb);
        //得到验证码，将生成的字符串放到会话域中
        request.getSession().setAttribute("vcode", sb.toString());
        //8) 画干扰线4条线，线的位置是随机的，x范围在width之中，y的范围在height之中。
        for (int i = 0; i < 4; i++) {
            graphics.setColor(getColor());
            int x1 = ran.nextInt(width);
            int y1 = ran.nextInt(height);
            int x2 = ran.nextInt(width);
            int y2 = ran.nextInt(height);
            graphics.drawLine(x1, y1, x2, y2);
        }
        //9) 将缓存的图片输出到响应输出流中
        //参数1：要输出的缓存图片，参数2：格式，参数3：输出到哪个流中
        ImageIO.write(image, "jpeg", response.getOutputStream());
    }

}
