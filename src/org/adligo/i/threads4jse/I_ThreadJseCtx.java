package org.adligo.i.threads4jse;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

import org.adligo.i.threads.I_ThreadCtx;

/**
 * This interface contains adaptor methods for basic threading used by JSE 
 *    Like all of Adligo's Ctx (Context) classes this provides a way to 
 * stub out the creation of various methods using mockito_ext.adligo.org.
 *  
 * @author scott
 *
 * 
 *  ---------------- Apache ICENSE-2.0 --------------------------
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
 */
public interface I_ThreadJseCtx extends I_ThreadCtx {


  /**
   * This should generally return the value from;
   * 
   * https://download.java.net/java/early_access/loom/docs/api/java.base/java/lang/Runtime.html#availableProcessors()
   * @return
   * @see {@link java.lang.Runtime#availableProcessors()}
   */
  public int availableProcessors();
  /**
   * This will eventually return new VirtualThreads when Project Loom's
   * Virtual Thread are promoted from pre-release to actually released;
   * https://download.java.net/java/early_access/loom/docs/api/java.base/java/util/concurrent/Executors.html#newVirtualThreadPerTaskExecutor()
   * <br/>
   * <br/>
   * However until then it SHOULD return a workStealingPool that is 8X the number of availableProcessors();
   * Of course custom implementations will very.
   * <br/>
   * <br/>
   * https://download.java.net/java/early_access/loom/docs/api/java.base/java/util/concurrent/Executors.html#newWorkStealingPool(int)
   * <br/>
   * <br/>
   * https://www.youtube.com/watch?v=EO9oMiL1fFo
   * No more thread Pools ? async await is 'thread like' but not a real thread
   * https://download.java.net/java/early_access/loom/docs/api/java.base/java/util/concurrent/Executors.html#newVirtualThreadPerTaskExecutor()
   * Throughput optimization formula 
   * 
   * Brian Gotez 'Reactive Coding is dead'
   * records are a surrogate for Tuples
   * https://www.youtube.com/watch?v=9si7gK94gLo
   * 
   * @return either a good old fashioned Java Thread or a Virtual Thread
   * @see Executors#newWorkStealingPool(int)
   * @see I_ThreadJseCtx#availableProcessors()
   */
  public Executor getDefaultExecutor();
  
  /**
   * Wraps the {@link Executors#newScheduledThreadPool(int)} 
   * @return
   * @see Executors#newScheduledThreadPool(int)
   */
  public ScheduledExecutorService newScheduledThreadPool(int i);

  /**
   * Wraps the {@link Executors#newScheduledThreadPool(int, ThreadFactory)} 
   * @return
   * @see Executors#newScheduledThreadPool(int, ThreadFactory)
   */
  public ScheduledExecutorService newScheduledThreadPool(int i, ThreadFactory threadFactory);


  /**
   * Wraps the {@link Executors#newSingleThreadScheduledExecutor} 
   * @return
   * @see Executors#newSingleThreadScheduledExecutor
   */
  public ScheduledExecutorService newSingleThreadScheduledExecutor();
  
  /**
   * Wraps the {@link Executors#newSingleThreadScheduledExecutor(ThreadFactory)} 
   * @return
   * @see Executors#newSingleThreadScheduledExecutor(ThreadFactory)
   */
  public ScheduledExecutorService newSingleThreadScheduledExecutor(ThreadFactory threadFactory);

  /**
   * Wraps the {@link Executors#newWorkStealingPool(int)
   * @return
   * @see Executors#newWorkStealingPool(int)
   */
  public ExecutorService newWorkStealingPool(int parallelism);
}
