package org.adligo.i_threads4jse;

import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * This interface simply holds all the information
 * for ReentrantLock synchronization in the I_ThreadJseCtx
 * class, in order to reduce the number of parameters to 
 * the public methods.
 * 
 * Known Implementations
 * {@linkplain https://github.com/adligo/threads.adligo.org/src/org/adligo/threads/SyncInfoMutant.java}
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
public interface I_SyncInfo extends I_PollTrys {

  /**
   * This returns the condition check to see 
   * if the lock has been accquired and the code in
   * the runner has suceeded.
   */
  Supplier<Boolean> getCondition(); 
  
  /**
   * @return The lock 
   */
  ReentrantLock getLock();
  
  /**
   * @return the Runnable that does the actual work
   */
  Runnable getRunner();
}
