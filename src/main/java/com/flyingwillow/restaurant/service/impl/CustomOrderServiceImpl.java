package com.flyingwillow.restaurant.service.impl;

import com.flyingwillow.restaurant.domain.CustomOrder;
import com.flyingwillow.restaurant.domain.CustomOrderDetail;
import com.flyingwillow.restaurant.domain.Menu;
import com.flyingwillow.restaurant.mapper.CustomOrderMapper;
import com.flyingwillow.restaurant.service.ICustomOrderDetailService;
import com.flyingwillow.restaurant.service.ICustomOrderService;
import com.flyingwillow.restaurant.service.IMenuService;
import com.flyingwillow.restaurant.util.web.Constants;
import com.flyingwillow.restaurant.util.web.SerialNumberGenerator;
import com.flyingwillow.restaurant.web.admin.dto.CheckoutDTO;
import com.flyingwillow.restaurant.web.waiter.dto.DetailDTO;
import com.flyingwillow.restaurant.web.waiter.dto.OrderDTO;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuxuhui on 2017/9/21.
 */
@Service
public class CustomOrderServiceImpl implements ICustomOrderService {

    @Autowired
    private CustomOrderMapper customOrderMapper;
    @Autowired
    private ICustomOrderDetailService customOrderDetailService;
    @Autowired
    private IMenuService menuService;

    @Override
    public List<CustomOrder> getCustomOrderList(Map<String, Object> params, int start, int size) {
        start = start>0?start:0;
        size = size>0?size: Constants.PAGE_LENGTH;
        if(null==params){
            params = new HashMap<String,Object>();
        }
        params.put("start",start);
        params.put("size",size);
        return customOrderMapper.getCustomOrderList(params);
    }

    @Override
    public List<CustomOrder> getCustomOrderList(Map<String, Object> params, Boolean showAll, int start, int size) {
        start = start>0?start:0;
        size = size>0?size: Constants.PAGE_LENGTH;
        if(null==params){
            params = new HashMap<String,Object>();
        }
        params.put("start",start);
        params.put("size",size);
        if(showAll==null||showAll==false){//显示未结订单
            params.put("isChecked",false);
        }
        return customOrderMapper.getCustomOrderList(params);
    }

    @Override
    public Integer getCustomOrderCount(Map<String, Object> params) {
        params = null==params?(new HashMap<String,Object>(0)):params;
        return customOrderMapper.getCustomOrderCount(params);
    }

    @Override
    public Integer getCustomOrderCount(Map<String, Object> params, Boolean showAll) {
        params = null==params?(new HashMap<String,Object>(0)):params;
        if(showAll==null||showAll==false){//显示未结订单
            params.put("isChecked",false);
        }
        return customOrderMapper.getCustomOrderCount(params);
    }

    @Override
    public CustomOrder getCustomOrderById(Integer menuId) {
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("id",menuId);
        return customOrderMapper.getCustomOrderById(params);
    }

    @Override
    public List<Map<String,Object>> getOrderNumbersByTableNo(Integer tableNo) {
        return customOrderMapper.getOrderNumbersByTableNo(tableNo);
    }

    @Override
    public Integer getOrderSerialNumber() {
        return customOrderMapper.getOrderSerialNumber();
    }

    @Override
    public List<Integer> getOrderedTableNumbers() {
        return customOrderMapper.getOrderedTableNumbers();
    }

    @Override
    public CustomOrder getCustomOrderByTableNo(Integer tableNo) {
        List<CustomOrder> list = customOrderMapper.getCustomOrderByTableNo(tableNo);
        if(null!=list&&list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public Integer getCustomOrderCountByTableNo(Integer tableNo) {
        return customOrderMapper.getCustomOrderCountByTableNo(tableNo);
    }

    @Override
    public void saveCustomOrder(CustomOrder order) {
        String number = SerialNumberGenerator.getSerialNumber("ORD",getOrderSerialNumber());
        order.setNumber(number);
        order.setCreateTime(new Date());
        order.setChecked(false);
        order.setPushed(false);
        customOrderMapper.saveCustomOrder(order);
    }

    @Override
    public CustomOrder saveCustomOrder(Integer tableNo) {
        CustomOrder order = new CustomOrder().setTableNo(tableNo);
        saveCustomOrder(order);
        return order;
    }

    @Override
    public void updateCustomOrder(CustomOrder order) {
        customOrderMapper.updateCustomOrder(order);
    }

    @Override
    public void updateOrderTotalPrice(Long orderId, Float totalPrice) {
        CustomOrder customOrder = new CustomOrder().setId(orderId).setTotalPrice(totalPrice);
        updateCustomOrder(customOrder);
    }

    @Override
    public void txIncrementTotalPrice(Long orderId, Float totalPrice) {
        HashMap<String,Object> params = new HashMap<>(2);
        params.put("orderId",orderId);
        params.put("totalPrice",totalPrice);
        customOrderMapper.updateOrderTotalPrice(params);
    }

    @Override
    public void deleteCustomOrderByNumber(String number) {
        customOrderMapper.deleteCustomOrderByNumber(number);
    }

    @Override
    public void checkoutOrder(Integer orderId, CheckoutDTO checkoutDTO) {
        CustomOrder order = new CustomOrder().setId(orderId.longValue());
        order.setCheckTime(new Date()).setChecked(true).setCheckOperator((String) SecurityUtils.getSubject().getPrincipal());
        order.setActualPrice(checkoutDTO.getReceivables()).setPayment(checkoutDTO.getPayment());
        customOrderMapper.updateCustomOrder(order);
    }
}
