/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sample.galleryapp.common.executor;

import android.support.annotation.NonNull;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Decorated {@link java.util.concurrent.ThreadPoolExecutor}
 */
@Singleton
public class JobExecutor implements ThreadExecutor {
    private static final int CORE_POOL_SIZE = 3;
    private static final int MAXIMUM_POOL_SIZE = 5;
    private static final int KEEP_ALIVE_TIME_IN_SEC = 10;
    private final ThreadPoolExecutor threadPoolExecutor;

    @Inject
    JobExecutor() {
        this.threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME_IN_SEC, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), new JobThreadFactory());
    }

    @Override
    public void execute(@NonNull Runnable runnable) {
        this.threadPoolExecutor.execute(runnable);
    }

    private static class JobThreadFactory implements ThreadFactory {
        private int counter = 0;

        @Override
        public Thread newThread(@NonNull Runnable runnable) {
            return new Thread(runnable, "android_" + counter++);
        }
    }
}
