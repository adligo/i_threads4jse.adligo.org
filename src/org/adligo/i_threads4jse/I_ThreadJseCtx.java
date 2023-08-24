package org.adligo.i_threads4jse;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

import org.adligo.i_threads.I_ThreadCtx;

/**
 * This interface contains adaptor methods for basic threading used by JSE Like
 * all of Adligo's Ctx (Context) classes this provides a way to stub out the
 * creation of various methods using; {@link <a href=
 * "https://github.com/adligo/mockito_ext.adligo.org">mockito_ext.adligo.org</a>}.<br/>
 * <br/>
 * 
 * @author scott
 *
 *         <pre>
 * <code>
 *         ---------------- Apache ICENSE-2.0 --------------------------
 *
 *         Copyright 2022 Adligo Inc
 * 
 *         Licensed under the Apache License, Version 2.0 (the "License"); you
 *         may not use this file except in compliance with the License. You may
 *         obtain a copy of the License at
 * 
 *         http://www.apache.org/licenses/LICENSE-2.0
 * 
 *         Unless required by applicable law or agreed to in writing, software
 *         distributed under the License is distributed on an "AS IS" BASIS,
 *         WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *         implied. See the License for the specific language governing
 *         permissions and limitations under the License.
 *         </code>
 *         </pre>
 */
public interface I_ThreadJseCtx extends I_ThreadCtx, I_PollTrys {

  /**
   * This should generally return the value from;
   * 
   * https://download.java.net/java/early_access/loom/docs/api/java.base/java/lang/Runtime.html#availableProcessors()
   * 
   * @return
   * @see {@link java.lang.Runtime#availableProcessors()}
   */
  default int availableProcessors() {
    return Runtime.getRuntime().availableProcessors();
  }

  /**
   * This will eventually return new VirtualThreads when Project Loom's Virtual
   * Thread are promoted from pre-release to actually released;
   * https://download.java.net/java/early_access/loom/docs/api/java.base/java/util/concurrent/Executors.html#newVirtualThreadPerTaskExecutor()
   * <br/>
   * <br/>
   * However until then it SHOULD return a workStealingPool that is 8X the number
   * of availableProcessors(); Of course custom implementations will very. <br/>
   * <br/>
   * https://download.java.net/java/early_access/loom/docs/api/java.base/java/util/concurrent/Executors.html#newWorkStealingPool(int)
   * <br/>
   * <br/>
   * https://www.youtube.com/watch?v=EO9oMiL1fFo No more thread Pools ? async
   * await is 'thread like' but not a real thread
   * https://download.java.net/java/early_access/loom/docs/api/java.base/java/util/concurrent/Executors.html#newVirtualThreadPerTaskExecutor()
   * Throughput optimization formula
   * 
   * Brian Gotez 'Reactive Coding is dead' records are a surrogate for Tuples
   * https://www.youtube.com/watch?v=9si7gK94gLo
   * 
   * @return either a good old fashioned Java Thread or a Virtual Thread
   * @see Executors#newWorkStealingPool(int)
   * @see I_ThreadJseCtx#availableProcessors()
   */
  public Executor getDefaultExecutor();

  /**
   * Wraps the {@link Executors#newScheduledThreadPool(int)}
   * 
   * @return
   * @see Executors#newScheduledThreadPool(int)
   */
  default ScheduledExecutorService newScheduledThreadPool(int i) {
    return Executors.newScheduledThreadPool(i);
  }

  /**
   * Wraps the {@link Executors#newScheduledThreadPool(int, ThreadFactory)}
   * 
   * @return
   * @see Executors#newScheduledThreadPool(int, ThreadFactory)
   */
  default ScheduledExecutorService newScheduledThreadPool(int i, ThreadFactory threadFactory) {
    return Executors.newScheduledThreadPool(i, threadFactory);
  }

  /**
   * Wraps the {@link Executors#newSingleThreadScheduledExecutor}
   * 
   * @return
   * @see Executors#newSingleThreadScheduledExecutor
   */
  default ScheduledExecutorService newSingleThreadScheduledExecutor() {
    return Executors.newSingleThreadScheduledExecutor();
  }

  /**
   * Wraps the {@link Executors#newSingleThreadScheduledExecutor(ThreadFactory)}
   * 
   * @return
   * @see Executors#newSingleThreadScheduledExecutor(ThreadFactory)
   */
  default ScheduledExecutorService newSingleThreadScheduledExecutor(ThreadFactory threadFactory) {
    return Executors.newSingleThreadScheduledExecutor(threadFactory);
  }

  /**
   * Wraps the {@link Executors#newWorkStealingPool(int)
   * 
   * @return
   * @see Executors#newWorkStealingPool(int)
   */
  default ExecutorService newWorkStealingPool(int parallelism) {
    return Executors.newWorkStealingPool(parallelism);
  }

  /**
   * Effectivly this is the same as a synchronized block, however it plays nicer
   * with VirtualThreads (as far as I can from JFR tell no pinning of the Virutal
   * Threads).
   * 
   * @param condition the check to see if the runner ran
   * @param lock the lock that performs the synchronization
   * @param runner the code to run
   */
  default void synchronize(Supplier<Boolean> condition, 
      ReentrantLock lock, Runnable runner) {
    synchronize(condition, this, lock, runner);
  }
  
  /**
   * Effectivly this is the same as a synchronized block,
   * however it plays nicer with VirtualThreads 
   * (as far as I can from JFR
   * tell no pinning of the Virutal Threads).
   * @param condition the check to see if the runner ran
   * @param pollTrys the settings for the polling of the 
   *    lock's tryLock method, note you can use the 
   *    above method which passes this context in
   *    for default settings.
   * @param lock the lock that performs the synchronization
   * @param runner the code to run
   */
  default void synchronize(Supplier<Boolean> condition, 
    I_PollTrys pollTrys, ReentrantLock lock, Runnable runner ) {
    synchronize(condition, this, lock, runner, this);
  }
  
  /**
   * Effectivly this is the same as a synchronized block,
   * however it plays nicer with VirtualThreads 
   * (as far as I can from JFR
   * tell no pinning of the Virutal Threads).
   * @param condition the check to see if the runner ran
   * @param pollTrys the settings for the polling of the 
   *    lock's tryLock method, note you can use the 
   *    above method which passes this context in
   *    for default settings.
   * @param lock the lock that performs the synchronization
   * @param runner the code to run
   * @param the ctx (Context) mixin purly for testing of this method
   * to check when an InterruptedException is caught that
   * the interrupted flag is reset correctly on the Thread.
   */
  default void synchronize(Supplier<Boolean> condition, 
    I_PollTrys pollTrys, ReentrantLock lock, Runnable runner,
    I_ThreadCtx ctx) {
    for (int i=0; i <= pollTrys.getTimes(); i++){
      if (!condition.get()) {
        break;
      } else {
        try {
          if (lock.tryLock(pollTrys.getTimeout(), 
              pollTrys.getTimeUnit())) {
            try {
              runner.run();
            } finally {
              lock.unlock();
            }
          }
        } catch (InterruptedException x) {
          ctx.toggleInterruptFlag();
        }
      }
    }
  }
}
