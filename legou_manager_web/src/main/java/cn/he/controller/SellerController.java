package cn.he.controller;

import cn.he.Service.SellerService;
import cn.he.domain.TbSeller;
import cn.he.entity.result;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Reference
    private SellerService sellerService;

    /*
    * 查询所有商家
    * */
    @RequestMapping("/search/{pageNum}/{pageSize}")
    public result search(@RequestBody TbSeller tbSeller , @PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize){
        try {
            PageInfo<TbSeller> pageInfo= sellerService.search(pageNum,pageSize,tbSeller);

            return new result(true,"success",pageInfo.getTotal(),pageInfo.getList());
        } catch (Exception e) {
            return new result(false,"fail");
        }

    }

    /*
    * 查看商家详情
    * */
    @RequestMapping("/findOne/{sellerId}")
    public result findOne(@PathVariable("sellerId") String sellerId){
        try {
            TbSeller tbSeller = sellerService.findOne(sellerId);
            return new result(true,"success",tbSeller);
        } catch (Exception e) {
            return new result(false,"fail");
        }

    }
    //auditing

    @RequestMapping("/auditing/{sellerId}/{status}")
    public result auditing(@PathVariable("sellerId") String sellerId ,@PathVariable("status") String status){
        try {
            sellerService.auditing(sellerId,status);
            return new result(true,"success");
        } catch (Exception e) {
            return new result(false,"fail");
        }

    }

//    @RequestMapping("/save")
//    public result save(@RequestBody TbSeller tbSeller){
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        try {
//            tbSeller.setPassword(passwordEncoder.encode(tbSeller.getPassword()));
//            sellerService.save(tbSeller);
//            return new result(true,"success");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new result(false,"fail");
//        }
//
//    }

}
