package architech.android.com.sunshineexcercise.utils;


import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    private final Executor mDiskIo;
    private final Executor mNetwork;
    private final MainThreadExecutor mMainThread;

    private AppExecutors(Executor diskIo, Executor network, MainThreadExecutor mainthread){
        mDiskIo = diskIo;
        mNetwork = network;
        mMainThread = mainthread;
    }

    public AppExecutors(){
        this(Executors.newSingleThreadExecutor(),Executors.newFixedThreadPool(3), new MainThreadExecutor());
    }

    public Executor diskIO() {
        return mDiskIo;
    }

    public Executor networkIO() {
        return mNetwork;
    }

    public Executor mainThread() {
        return mMainThread;
    }

    private static class MainThreadExecutor implements Executor{

        private final Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {
            handler.post(runnable);
        }
    }

}
