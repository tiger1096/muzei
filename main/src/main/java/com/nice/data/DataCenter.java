package com.nice.data;

import android.os.Handler;
import android.os.HandlerThread;

import com.nice.entity.Knowledge;
import com.nice.helper.HttpHelper;
import com.nice.storage.DataBuffer;
import com.nice.storage.SQLiteStorageUtils;

import java.util.Collections;
import java.util.List;

/*
    数据中心作用:
	1、 拉取后台数据：壁纸变化和学习内容更新
	2、 提供给显示器数据：每次提供多组数据（如10首诗），给显示中心一定的buffer
	3、 数据变化的存储：用户看到了什么背景＋文字
	4、 存储后台提供的数据：增删改查
	5、 SharePreference存储：存储关键设置，如重复次数，重复
    6、 设置学习策略：复习策略、拉取策略、提供策略等
 */
public class DataCenter {

    private static DataCenter dataCenter = new DataCenter();
    private static final String NTSY_DATA_PROCESS_THREAD = "NTSY_DATA_PROCESS_THREAD";
    private static final String NTSY_HTTP_REQUEST_THREAD = "NTSY_HTTP_REQUEST_THREAD";

    private Handler dataProcessHandler;
    private Handler httpRequestHandler;

    private DataCenter() {
        HandlerThread dataProcessThread = new HandlerThread(NTSY_DATA_PROCESS_THREAD);
        dataProcessThread.start();
        dataProcessHandler = new Handler(dataProcessThread.getLooper());

        HandlerThread httpRequestThread = new HandlerThread(NTSY_HTTP_REQUEST_THREAD);
        httpRequestThread.start();
        httpRequestHandler = new Handler(httpRequestThread.getLooper());
    }

    public static DataCenter getInstance() {
        return dataCenter;
    }

    public void fetchKnowledgeFromBackend() {
        httpRequestHandler.post(new Runnable() {
            @Override
            public void run() {
//                HttpHelper.postForResult();
            }
        });
    }

    public void provideKnowledge(DataBuffer dataBuffer) {
        if (dataBuffer == null) {
            return;
        }
        dataProcessHandler.post(new Runnable() {
            @Override
            public void run() {
                // 1、从策略数据库中获取学习策略

                // TODO 目前没有策略，就是从学习内容中拿5个新的记录

                // 2、根据策略从学习内容数据中分别检索数据

                List<Knowledge> knowledges = SQLiteStorageUtils.getQueryByWhereLengthAndOrder(
                        Knowledge.class, "catagory", new String[] {"ci"}, 0, 5,
                        "id", true
                );

                // 3、更新DataBuffer
                dataBuffer.push(Collections.singletonList(knowledges));

            }
        });
    }

    public boolean recordLearnBehaviour() {
        return true;
    }

    public void setKnowledgeFetchStrategy() {

    }
}
