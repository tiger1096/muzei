package com.nice.data;

import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.Log;

import com.alexqzhang.util.GsonUtils;
import com.nice.config.NTSYBeckend;
import com.nice.config.NiceToSeeYouConstant;
import com.nice.entity.Knowledge;
import com.nice.entity.LearnRecord;
import com.nice.helper.HttpHelper;
import com.nice.storage.DataBuffer;
import com.nice.storage.SQLiteStorageUtils;

import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
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
    private static final String TAG = DataCenter.class.getSimpleName();

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

    /**
     * 根据用户学习策略和学习进度，从后台拉取新的学习内容
     */
    public void fetchKnowledgeFromBackend() {
        httpRequestHandler.post(new Runnable() {
            @Override
            public void run() {
//                HttpHelper.postForResult();
                // 1、获取当前用户的学习策略
                // TODO 目前的用户习惯是固定的，就是获取5篇新的宋词

                // 2、获取当前用户的学习进度
                List<LearnRecord> records = SQLiteStorageUtils.getQueryByWhereLengthAndOrder(LearnRecord.class,
                        "user_id", new String[] {String.valueOf(NiceToSeeYouConstant.getUserId())},
                        0, 1, "timestamp", true);

                int itemId = 0;
                if (records != null && records.size() > 0) {
                    itemId = records.get(0).item_id;;
                }

//                List<Knowledge> knowledges = SQLiteStorageUtils.getQueryByWhere(Knowledge.class, "id", new Integer[] {itemId});
//                if (knowledges == null) {
//                    return;
//                }

                // 3、获取后台数据，并解析为Knowledge
//                int progess = knowledges.get(0).knowledge_id;
                JSONObject jsonObject = new JSONObject();
                String result = HttpHelper.postForResult(NTSYBeckend.FETCH_KNOWLEDGE, jsonObject);

                List<Knowledge> newKnowledge = null;
                if (!TextUtils.isEmpty(result)) {
                    newKnowledge = GsonUtils.json2ObjList(result, Knowledge.class);
                }

                // 4、存入数据库
                int insertNum = SQLiteStorageUtils.insertAll(newKnowledge);
                Log.e(TAG, "insertNum = " + insertNum);
            }
        });
    }

    /**
     * 根据当前用户设置的学习策略和学习进度，提供新的学习内容
     * @param dataBuffer 用户学习内容的缓冲区，系的学习内容将加入到缓冲区中
     */
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
                        Knowledge.class, "catagory", new String[] {"SongPhrase"}, 0, 5,
                        "id", true
                );

                if (knowledges == null || knowledges.size() == 0) {
                    return;
                }

                // 3、更新DataBuffer
                List<Object> objects = new ArrayList<>(knowledges.size());
                for (Knowledge knowledge : knowledges) {
                    objects.add(knowledge);
                }
                dataBuffer.push(objects);

            }
        });
    }

    public boolean recordLearnBehaviour(Knowledge knowledge) {
        LearnRecord record = new LearnRecord();
        record.item_id = knowledge.id;
        record.user_id = NiceToSeeYouConstant.getUserId();
        record.timestamp = new Timestamp(System.currentTimeMillis());
        long ret = SQLiteStorageUtils.insert(record);
        return true;
    }

    public void setKnowledgeFetchStrategy() {

    }
}
