package com.flyingwillow.restaurant.service;

import com.flyingwillow.restaurant.domain.CustomOrder;

import java.util.List;
import java.util.Map;

/**
 * Created by 刘旭辉 on 2017/9/14.
 */
public interface ICustomOrderService {
    public List<CustomOrder> getCustomOrderList(Map<String, Object> params, int start, int size);
    public List<CustomOrder> getCustomOrderList(Map<String, Object> params, Boolean showAll, int start, int size);

    public Integer getCustomOrderCount(Map<String, Object> params);
    public Integer getCustomOrderCount(Map<String, Object> params, Boolean showAll);

    public CustomOrder getCustomOrderById(Integer orderId);

    public void saveCustomOrder(CustomOrder order);

    public void updateCustomOrder(CustomOrder order);

    public void deleteCustomOrderByNumber(String number);

}
