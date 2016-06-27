/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intel.hibench.streambench.gearpump.task

import com.intel.hibench.streambench.common.metrics.LatencyReporter
import com.intel.hibench.streambench.gearpump.util.GearpumpConfig
import org.apache.gearpump.Message
import org.apache.gearpump.cluster.UserConfig
import org.apache.gearpump.streaming.task.{Task, TaskContext}

class Identity(taskContext: TaskContext, conf: UserConfig) extends Task(taskContext, conf) {
  private val reporter = conf.getValue[LatencyReporter](GearpumpConfig.BENCH_LATENCY_REPORTER).get

  override def onNext(msg: Message): Unit = {
    taskContext.output(msg)
    val latency = System.currentTimeMillis() - msg.timestamp
    reporter.report(latency)
  }
}
