package com.hm.iou.loginmodule.business.launch;

import android.content.Context;

import com.hm.iou.loginmodule.bean.AdvertisementRespBean;
import com.hm.iou.tools.ACache;
import com.hm.iou.tools.ImageLoader;
import com.hm.iou.tools.TimeUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author syl
 * @time 2018/5/30 下午6:57
 */
public class CacheDataUtil {

    private static final String KEY_LIST = "loginmodule_key_list";


    /**
     * 获取当前正在进行的广告对象
     *
     * @param context
     * @return
     */
    public static AdvertisementRespBean getAdvertisement(Context context) {
        List<AdvertisementRespBean> listCache = readAdvertisementListFromCache(context);
        if (listCache == null || listCache.isEmpty()) {
            return null;
        }
        AdvertisementRespBean advertisementRespBean = null;
        TimeUtil timeUtil = TimeUtil.getInstance(TimeUtil.SimpleDateFormatEnum.DateFormatForApi);
        for (AdvertisementRespBean adBean : listCache) {
            long startTime = 0;
            long endTime = 0;
            try {
                startTime = timeUtil.getTimeInLong(adBean.getStartTime());
                endTime = timeUtil.getTimeInLong(adBean.getEndTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long currentTime = timeUtil.getCurrentTimeInLong();
            if (startTime <= currentTime && endTime >= currentTime) {
                advertisementRespBean = adBean;
                break;
            }
        }
        return advertisementRespBean;
    }

    /**
     * 缓存启动页广告列表
     *
     * @param context
     * @param list    广告列表
     */
    public static void cacheAdvertisementList(Context context, List<AdvertisementRespBean> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        clearAdvertisementListCache(context);
        for (AdvertisementRespBean adBean : list) {
            ImageLoader.getInstance(context).fetchImage(adBean.getAdimageUrl());
        }
        ACache cache = ACache.get(context.getApplicationContext());
        cache.put(KEY_LIST, (ArrayList) list);
    }

    /**
     * 读取启动页广告列表
     *
     * @param context
     * @return
     */
    private static List<AdvertisementRespBean> readAdvertisementListFromCache(Context context) {
        ACache cache = ACache.get(context.getApplicationContext());
        List<AdvertisementRespBean> list = (ArrayList<AdvertisementRespBean>) cache.getAsObject(KEY_LIST);
        return list;
    }

    /**
     * 清除缓存启动页广告列表
     *
     * @param context
     */
    private static void clearAdvertisementListCache(Context context) {
        ACache cache = ACache.get(context.getApplicationContext());
        cache.remove(KEY_LIST);
    }

}