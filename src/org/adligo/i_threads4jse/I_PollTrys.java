package org.adligo.i_threads4jse;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This interface just holds the settings for retrying / spinning
 * in the syncrhonize methods in the I_ThreadJseCtx.  However,
 * it could be used for any polling thing with multiple trys (
 * ArrayBlockingQueue, ReentrantLock, etc).
 * 
 * <br/>
 * 
 * @author scott
 *
 *         <pre>
 * <code>
 *         ---------------- Apache ICENSE-2.0 --------------------------
 *
 *         Copyright 2023 Adligo Inc
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
public interface I_PollTrys {

  /**
   * The number of times you want to retry the get the lock, 
   * defaults to 23
   * @return
   */
  default int getTimes() {
    return 23;
  }
  
  /**
   * The number of time units you want to wait on 
   * each try. Defaults to 1000
   * @return
   */
  default long getTimeout() {
    return 1000;
  }
  
  /**
   * The type of time unit that the timeout setting pertains to,
   * defaults to Milliseconds.
   * @return
   */
  default TimeUnit getTimeUnit() {
    return TimeUnit.MILLISECONDS;
  }
  
}
