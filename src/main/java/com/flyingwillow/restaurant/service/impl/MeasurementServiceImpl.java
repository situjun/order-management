package com.flyingwillow.restaurant.service.impl;

import com.flyingwillow.restaurant.domain.Measurement;
import com.flyingwillow.restaurant.mapper.MeasurementMapper;
import com.flyingwillow.restaurant.service.IMeasurementService;
import com.flyingwillow.restaurant.util.web.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 刘旭辉 on 2017/9/14.
 */
@Service
public class MeasurementServiceImpl implements IMeasurementService{

    @Autowired
    private MeasurementMapper measurementMapper;

    @Override
    public List<Measurement> getMeasurementList(Map<String, Object> params, int start, int size) {
        start = start>0?start:0;
        size = size>0?size: Constants.PAGE_LENGTH;
        if(null==params){
            params = new HashMap<String,Object>();
        }
        params.put("start",start);
        params.put("size",size);
        return measurementMapper.getMeasurementList(params);
    }

    @Override
    public Integer getMeasurementCount(Map<String, Object> params) {
        params = null==params?(new HashMap<String,Object>(0)):params;
        return measurementMapper.getMeasurementCount(params);
    }

    @Override
    public Measurement getMeasurementById(Integer measurementId) {
        return measurementMapper.getMeasurementById(measurementId);
    }

    @Override
    public void saveMeasurement(Measurement measurement) {
        measurementMapper.saveMeasurement(measurement);
    }

    @Override
    public void updateMeasurement(Measurement measurement) {
        measurementMapper.updateMeasurement(measurement);
    }

    @Override
    public void deleteMeasurement(Integer id) {
        measurementMapper.deleteMeasurement(id);
    }

    @Override
    public void deleteMeasurementByIds(List<Integer> idList) {
        Map<String,Object> param = new HashMap<>(1);
        param.put("idList",idList);
        measurementMapper.deleteMeasurementByIds(param);
    }
}
