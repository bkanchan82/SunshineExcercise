package architech.android.com.sunshineexcercise.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutor {

    private static final Object LOCK = new Object();
    private static AppExecutor sInstance;

    private Executor diskIo;
    private Executor networkIo;
    private Executor mainThread;

    private AppExecutor(Executor diskIo,Executor networkIo,MainThread mainThread){
        this.diskIo = diskIo;
        this.networkIo = networkIo;
        this.mainThread = mainThread;
    }

    public static AppExecutor getInstance(){
        if(sInstance == null){
            synchronized (LOCK){
                if(sInstance == null){
                    sInstance = new AppExecutor(Executors.newSingleThreadExecutor(),
                            Executors.newFixedThreadPool(3),
                            new MainThread());
                }
            }
        }
        return sInstance;
    }

    public Executor getDiskIo(){
        return diskIo;
    }

    public Executor getNetworkIo() {
        return networkIo;
    }

    public Executor getMainThread() {
        return mainThread;
    }

    public static class MainThread implements Executor {

        private final Handler mHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {

            mHandler.post(runnable);

        }
    }

}
