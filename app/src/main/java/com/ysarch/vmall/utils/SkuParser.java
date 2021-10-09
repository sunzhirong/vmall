package com.ysarch.vmall.utils;

import android.text.TextUtils;
import android.util.ArrayMap;

import com.alibaba.fastjson.JSON;
import com.ysarch.vmall.domain.bean.GoodsDetailItemBean;
import com.ysarch.vmall.domain.bean.SkuBeanV2;
import com.ysarch.vmall.domain.local.LocalPropSkuEntity;
import com.ysarch.vmall.domain.local.LocalSkuEntity;
import com.yslibrary.utils.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by fysong on 24/09/2020
 **/
public class SkuParser implements Serializable {
    /**
     * 解析sku数据，并进行拆分
     *
     * @param value
     * @return
     */
    public static Map<String, List<LocalSkuEntity>> parsePropsMap(String value) {
        if (TextUtils.isEmpty(value))
            return null;

        String[] skus = value.split(";");
        List<LocalSkuEntity> localSkuEntities = new ArrayList<>();
        List<String> propsLabel = new ArrayList<>();
        for (int i = 0; i < skus.length; i++) {
            String[] strings = skus[i].split(":");
            if (strings.length >= 4) {
                LocalSkuEntity entity = new LocalSkuEntity();
                entity.setSkuCode(strings[0] + ":" + strings[1]);
                entity.setSkuBelong(strings[2]);
                String skuLabel = strings[3];
                for (int j = 4; j < strings.length; j++) {
                    skuLabel = skuLabel + ":" + strings[j];
                }
                entity.setSkuLabel(skuLabel);
                localSkuEntities.add(entity);

                //sku归属规格名称
                if (!propsLabel.contains(entity.getSkuBelong())) {
                    propsLabel.add(entity.getSkuBelong());
                }
            }
        }

        if (CollectionUtils.isNotEmpty(localSkuEntities) && CollectionUtils.isNotEmpty(propsLabel)) {
            Map<String, List<LocalSkuEntity>> map = new ArrayMap<>();
            for (int i = 0; i < propsLabel.size(); i++) {
                String propLabel = propsLabel.get(i);
                Iterator<LocalSkuEntity> localSkuEntityIterator = localSkuEntities.iterator();
                List<LocalSkuEntity> localSkuEntityList = new ArrayList<>();
                while (localSkuEntityIterator.hasNext()) {
                    LocalSkuEntity entity = localSkuEntityIterator.next();
                    if (entity.getSkuBelong().equals(propLabel)) {
                        localSkuEntityList.add(entity);
                        localSkuEntityIterator.remove();
                    }
                }
                if (CollectionUtils.isNotEmpty(localSkuEntityList)) {
                    map.put(propLabel, localSkuEntityList);
                }
            }

            return map;
        }

        return null;
    }



    public static List<LocalPropSkuEntity> parseSkuData(GoodsDetailItemBean goodsDetailItemBean) {


        List<SkuBeanV2> skuBeanV2s = goodsDetailItemBean.getSku();
        List<GoodsDetailItemBean.PropsBean> propsBeans = goodsDetailItemBean.getProps();

        List<LocalPropSkuEntity> localPropSkuEntities = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(propsBeans) && CollectionUtils.isNotEmpty(skuBeanV2s)) {
            for (int i = 0; i < propsBeans.size(); i++) {
                List<GoodsDetailItemBean.PropsBean.ValuesBean> valuesBeans = propsBeans.get(i).getValues();
                if (CollectionUtils.isNotEmpty(valuesBeans)) {
                    List<LocalSkuEntity> localSkuEntities = new ArrayList<>();
                    String propId = propsBeans.get(i).getPid();
                    String propName = propsBeans.get(i).getName();
                    for (int j = 0; j < valuesBeans.size(); j++) {
                        GoodsDetailItemBean.PropsBean.ValuesBean valuesBean = valuesBeans.get(j);
                        String skuPropCode = valuesBean.getVid();//propId + ":" + valuesBean.getVid();
                        boolean existSkuGoods = false;

                        //判断库存,只要存在就展示
                        loop1:
                        for (int k = 0; k < skuBeanV2s.size(); k++) {
                            SkuBeanV2 skuBeanV2 = skuBeanV2s.get(k);
                            if(TextUtils.isEmpty(skuBeanV2.getPropPath())){
                                skuBeanV2.setPropPath("");
                            }
                            if (skuBeanV2.getPropPath().contains(skuPropCode)) {
                                existSkuGoods = true;
                                break loop1;
                            }
                        }

                        if (existSkuGoods) {
                            LocalSkuEntity localSkuEntity = new LocalSkuEntity();
                            localSkuEntity.setSkuLabel(valuesBean.getName());
                            localSkuEntity.setSkuCode(valuesBean.getVid());
                            localSkuEntity.setImage(valuesBean.getImage());
//                            localSkuEntity.setImage(valuesBean.getImage());
                            localSkuEntity.setSkuBelong(propName);
                            localSkuEntity.setSkuBelongId(propId);
                            localSkuEntities.add(localSkuEntity);
                        }
                    }

                    if (CollectionUtils.isNotEmpty(localSkuEntities)) {
                        LocalPropSkuEntity localPropSkuEntity = new LocalPropSkuEntity();
                        localPropSkuEntity.setPropId(propId);
                        localPropSkuEntity.setPropLabel(propName);
                        localPropSkuEntity.setLocalSkuEntities(localSkuEntities);
                        localPropSkuEntities.add(localPropSkuEntity);
                    }
                }
            }
        }

        return localPropSkuEntities;
    }

//    /**
//     * 1688数据
//     * @param goodsDetailItemBean
//     * @return
//     */
//    public static List<LocalPropSkuEntity> parseMultiSkuData(GoodsDetailItemBean goodsDetailItemBean) {
//        List<SkuBeanV2> skuBeanV2s = goodsDetailItemBean.getSku();
//        List<GoodsDetailItemBean.PropsBean> propsBeans = goodsDetailItemBean.getProps();
//
//        List<LocalPropSkuEntity> localPropSkuEntities = new ArrayList<>();
//
//        if (CollectionUtils.isNotEmpty(propsBeans) && CollectionUtils.isNotEmpty(skuBeanV2s)) {
//            for (int i = 0; i < propsBeans.size(); i++) {
//                List<GoodsDetailItemBean.PropsBean.ValuesBean> valuesBeans = propsBeans.get(i).getValues();
//                if (CollectionUtils.isNotEmpty(valuesBeans)) {
//                    List<LocalSkuEntity> localSkuEntities = new ArrayList<>();
////                    String propId = propsBeans.get(i).getPid();
//                    String propName = propsBeans.get(i).getName();
//                    for (int j = 0; j < valuesBeans.size(); j++) {
//                        GoodsDetailItemBean.PropsBean.ValuesBean valuesBean = valuesBeans.get(j);
//                        String skuPropCode =  valuesBean.getVid();
//                        boolean existSkuGoods = false;
//
//                        //判断库存,只要存在就展示
//                        loop1:
//                        for (int k = 0; k < skuBeanV2s.size(); k++) {
//                            SkuBeanV2 skuBeanV2 = skuBeanV2s.get(k);
//                            if (skuBeanV2.getPropPath().contains(skuPropCode)) {
//                                existSkuGoods = true;
//                                break loop1;
//                            }
//                        }
//
//                        if (existSkuGoods) {
//                            LocalSkuEntity localSkuEntity = new LocalSkuEntity();
//                            localSkuEntity.setSkuLabel(valuesBean.getName());
//                            localSkuEntity.setSkuCode(valuesBean.getVid());
//                            localSkuEntity.setImage(valuesBean.getImage());
//                            localSkuEntity.setSkuBelong(propName);
////                            localSkuEntity.setSkuBelongId(propId);
//                            localSkuEntities.add(localSkuEntity);
//                        }
//                    }
//
//                    if (CollectionUtils.isNotEmpty(localSkuEntities)) {
//                        LocalPropSkuEntity localPropSkuEntity = new LocalPropSkuEntity();
////                        localPropSkuEntity.setPropId(propId);
//                        localPropSkuEntity.setPropLabel(propName);
//                        localPropSkuEntity.setLocalSkuEntities(localSkuEntities);
//                        localPropSkuEntities.add(localPropSkuEntity);
//                    }
//                }
//            }
//        }
//
//        return localPropSkuEntities;
//    }


    /**
     * 解析sku数据，并进行拆分
     *
     * @param value
     * @return
     */
    public static List<LocalPropSkuEntity> parseProps(String value) {
        if (TextUtils.isEmpty(value))
            return null;

        String[] skus = value.split(";");
        List<LocalSkuEntity> localSkuEntities = new ArrayList<>();
        List<String> propsLabel = new ArrayList<>();
        for (int i = 0; i < skus.length; i++) {
            String[] strings = skus[i].split(":");
            if (strings.length >= 4) {
                LocalSkuEntity entity = new LocalSkuEntity();
                entity.setSkuCode(strings[0] + ":" + strings[1]);
                entity.setSkuBelong(strings[2]);
                String skuLabel = strings[3];
                for (int j = 4; j < strings.length; j++) {
                    skuLabel = skuLabel + ":" + strings[j];
                }
                entity.setSkuLabel(skuLabel);
                localSkuEntities.add(entity);

                //sku归属规格名称
                if (!propsLabel.contains(entity.getSkuBelong())) {
                    propsLabel.add(entity.getSkuBelong());
                }
            }
        }

        if (CollectionUtils.isNotEmpty(localSkuEntities) && CollectionUtils.isNotEmpty(propsLabel)) {
            List<LocalPropSkuEntity> propSkuEntities = new ArrayList<>();
            for (int i = 0; i < propsLabel.size(); i++) {
                String propLabel = propsLabel.get(i);
                Iterator<LocalSkuEntity> localSkuEntityIterator = localSkuEntities.iterator();
                List<LocalSkuEntity> localSkuEntityList = new ArrayList<>();
                while (localSkuEntityIterator.hasNext()) {
                    LocalSkuEntity entity = localSkuEntityIterator.next();
                    if (entity.getSkuBelong().equals(propLabel)) {
                        localSkuEntityList.add(entity);
                        localSkuEntityIterator.remove();
                    }
                }
                if (CollectionUtils.isNotEmpty(localSkuEntityList)) {
//                    map.put(propLabel, localSkuEntityList);
                    LocalPropSkuEntity propSkuEntity = new LocalPropSkuEntity();
                    propSkuEntity.setPropLabel(propLabel);
                    propSkuEntity.setLocalSkuEntities(localSkuEntityList);
                    propSkuEntities.add(propSkuEntity);
                }
            }

            return propSkuEntities;
        }

        return null;

    }

}
