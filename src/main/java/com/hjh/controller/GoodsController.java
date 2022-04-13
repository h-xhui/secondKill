package com.hjh.controller;

import com.hjh.pojo.User;
import com.hjh.service.GoodsService;
import com.hjh.service.UserService;
import com.hjh.vo.DetailVo;
import com.hjh.vo.GoodsVo;
import com.hjh.vo.RespBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author 洪锦辉
 * 2022/2/16
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    @RequestMapping(value = "/list", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String list(Model model, User user, HttpServletRequest request, HttpServletResponse response) {
        if (user == null) {
            return "login";
        }
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get("goodsList");
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsService.getAllGoodsVo());

        WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", context);
        if (!StringUtils.isEmpty(html)) {
            valueOperations.set("goodsList", html, 60, TimeUnit.SECONDS);
        }

        return html;
    }

    @RequestMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public RespBean toDetail(Model model, User user, @PathVariable("goodsId") Long goodsId) {
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        Date startTime = goodsVo.getStartDate();
        Date endTime = goodsVo.getEndDate();
        Date nowTime = new Date();
        int seckillStatus = 0;
        int remainSeconds = 0;
        if (nowTime.before(startTime)) {
            remainSeconds = (int) (endTime.getTime() - startTime.getTime()) / 1000;
        }
        else if (nowTime.after(startTime) && nowTime.before(endTime)) {
            seckillStatus = 1;
        } else if (nowTime.after(endTime)) {
            seckillStatus = 2;
            remainSeconds = -1;
        }
        DetailVo detailVo = new DetailVo();
        detailVo.setUser(user);
        detailVo.setGoodsVo(goodsVo);
        detailVo.setSeckillStatus(seckillStatus);
        detailVo.setRemainSeconds(remainSeconds);
        return RespBean.success(detailVo);
    }
}
