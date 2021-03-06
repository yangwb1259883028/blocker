package cn.linhome.blocker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import cn.linhome.lib.blocker.FRunnableBlocker;
import cn.linhome.lib.looper.FLooper;
import cn.linhome.lib.looper.impl.FSimpleLooper;


public class RunnableBlockerActivity extends AppCompatActivity
{
    private TextView tv_block_msg, tv_msg;

    private FRunnableBlocker mBlocker = new FRunnableBlocker();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runnable_blocker);
        tv_block_msg = (TextView) findViewById(R.id.tv_block_msg);
        tv_msg = (TextView) findViewById(R.id.tv_msg);
    }

    private FLooper mLooper = new FSimpleLooper();
    private int mRequestCount; //请求执行次数
    private int mRealCount; // 实际执行次数

    public void onClickStart500(View view)
    {
        mBlocker.setMaxBlockCount(3); //设置延迟间隔内最大可以拦截3次，超过3次则立即执行
        mLooper.setInterval(500);//模拟每隔500毫秒请求执行一次的场景
        mLooper.start(new Runnable()
        {
            @Override
            public void run()
            {
                mBlocker.postDelayed(mTargetRunnable, 3000); //尝试post一个3000毫秒后执行的Runnable

                mRequestCount++;
                tv_block_msg.setText("请求执行次数：" + mRequestCount);
            }
        });
    }

    /**
     * 模拟耗性能Runnable
     */
    private Runnable mTargetRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            mRealCount++;
            tv_msg.setText("实际执行次数：" + String.valueOf(mRealCount));
        }
    };

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mLooper.stop();
    }
}
