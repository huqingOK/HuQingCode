package com.hq.base.demo;

import android.os.Bundle;
import android.util.Log;

import com.hq.base.BaseActivity;
import com.hq.base.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 *  参考OkGo
 */
public class NetActivity extends BaseActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Map<String, String> params = new HashMap<String, String>();
        params.put("userName", "rghg");
        params.put("password", "123456");
        params.put("loginType", "1");
        params.put("smsCode", "");
        params.put("model", "FP07");
        JSONObject jsonObject = new JSONObject(params);
        OkGo.<String>post("http://www.12365aq.cn/api/user/login")//
                .tag(this)//
                .upJson(jsonObject)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new StringCallback()
                {
                    @Override
                    public void onSuccess(Response<String> response)
                    {
                        Log.e("hq", response.toString());
                    }

                    @Override
                    public void onCacheSuccess(Response<String> response)
                    {
                        Log.e("hq", response.toString());
                        super.onCacheSuccess(response);
                    }

                    @Override
                    public void onError(Response<String> response)
                    {
                    }
                });
    }
}
