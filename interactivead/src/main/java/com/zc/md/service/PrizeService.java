package com.zc.md.service;

import com.zc.entity.AdvertiserCampaign;
import com.zc.entity.AdvertiserCreative;
import com.zc.md.entity.CreativeClick;
import com.zc.md.entity.CreativeImp;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

public interface PrizeService {
    //优选活动
    AdvertiserCampaign getBestCampaign(List<AdvertiserCampaign> list);

    //优选创意
    AdvertiserCreative getBestCreatve(List<AdvertiserCreative> list);

    //写入日志
    void writeCreativeImplLog(CreativeImp creativeImp);

    //写入点击日志
    void writeCreativeClicklLog(CreativeClick creativeClick);
}
