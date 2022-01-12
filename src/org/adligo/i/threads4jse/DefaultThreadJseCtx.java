package org.adligo.i.threads4jse;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicReference;

import org.adligo.i.threads.DefaultThreadCtx;

/**
 * This interface provides the defaults for {@link I_ThreadJseCtx }<br/>
 *  <br/>
 * @author scott<br/>
 *
 * 
 * <pre><code>
 * 
 * ---------------- Apache ICENSE-2.0 --------------------------
 *
 * Copyright 2022 Adligo Inc
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </code></pre>
 */
public interface DefaultThreadJseCtx extends DefaultThreadCtx, I_ThreadJseCtx {
  static final AtomicReference<ExecutorService> DEFAULT = new AtomicReference<>();
  
  @Override
  default int availableProcessors() {
    return Runtime.getRuntime().availableProcessors();
  }
  
  @Override
  default Executor getDefaultExecutor() {
    if (DEFAULT.get() == null) {
      synchronized (DEFAULT) {
        if (DEFAULT.get() == null) {
          DEFAULT.set(newWorkStealingPool(availableProcessors()));
        }
      }
    }
    return DEFAULT.get();
  }
  
  @Override
  default ScheduledExecutorService newScheduledThreadPool(int i) {
    return Executors.newScheduledThreadPool(i);
  }

  @Override
  default ScheduledExecutorService newScheduledThreadPool(int i, ThreadFactory threadFactory) {
    return Executors.newScheduledThreadPool(i, threadFactory);
  }


  @Override
  default ScheduledExecutorService newSingleThreadScheduledExecutor() {
    return Executors.newSingleThreadScheduledExecutor();
  }
  

  @Override
  default ScheduledExecutorService newSingleThreadScheduledExecutor(ThreadFactory threadFactory) {
    return Executors.newSingleThreadScheduledExecutor(threadFactory);
  }

  @Override
  default ExecutorService newWorkStealingPool(int parallelism) {
    return Executors.newWorkStealingPool(parallelism);
  }
}
