/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package org.apache.mina.service.executor;

import java.util.concurrent.Executor;

/**
 * (in progress)
 * 
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 * 
 */
public class OutOfOrderHandlerExecutor implements IoHandlerExecutor {

    private final Executor executor;

    public OutOfOrderHandlerExecutor(Executor executor) {
        this.executor = executor;
    }

    @Override
    public void execute(Event event) {
        executor.execute(new EventRunner(event));
    }

    private static class EventRunner implements Runnable {

        private final Event event;

        public EventRunner(Event event) {
            this.event = event;
        }

        private static HandlerCaller caller = new HandlerCaller();

        @Override
        public void run() {
            event.visit(caller);
        }

    }
}