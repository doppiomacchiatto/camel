/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.opentracing.decorators;

import io.opentracing.Span;
import io.opentracing.tag.Tags;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;

public abstract class AbstractHttpSpanDecorator extends AbstractSpanDecorator {

    @Override
    public void pre(Span span, Exchange exchange, Endpoint endpoint) {
        super.pre(span, exchange, endpoint);
        span.setTag(Tags.HTTP_URL.getKey(), endpoint.getEndpointUri());
    }

    @Override
    public void post(Span span, Exchange exchange, Endpoint endpoint) {
        super.post(span, exchange, endpoint);

        if (exchange.hasOut()) {
            Object responseCode = exchange.getOut().getHeader(Exchange.HTTP_RESPONSE_CODE);
            if (responseCode instanceof Integer) {
                span.setTag(Tags.HTTP_STATUS.getKey(), (Integer) responseCode);
            }
        }
    }

}
