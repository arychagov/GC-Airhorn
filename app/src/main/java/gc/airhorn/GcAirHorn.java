/***
 * Copyright (c) 2015
 *
 * Authors: Anton Rychagov <anton.rychagov@gmail.com>
 *          Adel Nizamutdinov <stiggpwnz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License" you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gc.airhorn;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

public class GcAirHorn {
    @Nullable private static volatile GcAirHorn sInstance;
    @NonNull private final Context mContext;
    @Nullable private Thread mThread;

    /**
     * Retrieves instance of this class
     * @param context context
     * @return instance
     */
    public static GcAirHorn getInstance(@NonNull Context context) {
        if (sInstance == null) {
            synchronized (GcAirHorn.class) {
                if (sInstance == null) {
                    sInstance = new GcAirHorn(context);
                }
            }
        }
        return sInstance;
    }

    private GcAirHorn(@NonNull Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * Starts listening for GC_FOR_ALLOC events
     */
    public void startListening() {
        if (mThread != null) {
            return;
        }

        mThread = new Thread() {
            @Override
            public void run() {
                final MediaPlayer player = MediaPlayer.create(mContext, R.raw.airhorn);
                final ReferenceQueue<Object> rq = new ReferenceQueue<>();

                //noinspection unused
                PhantomReference<Object> phantom = new PhantomReference<>(new Object(), rq);
                while (!isInterrupted()) {

                    if (Thread.interrupted()) {
                        return;
                    }

                    if (rq.poll() != null) {
                        if (player.isPlaying()) {
                            player.seekTo(0);
                        } else {
                            player.start();
                        }

                        //noinspection UnusedAssignment
                        phantom = new PhantomReference<>(new Object(), rq);
                    }

                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        };
        mThread.setPriority(Thread.MIN_PRIORITY);
        mThread.start();
    }

    /**
     * Stops listening for GC_FOR_ALLOC events
     */
    public void stopListening() {
        if (mThread != null) {
            mThread.interrupt();
            mThread = null;
        }
    }
}
