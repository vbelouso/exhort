/*
 * Copyright 2023 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.redhat.exhort.analytics.segment;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public record TrackEvent(
    String anonymousId,
    String userId,
    String event,
    Context context,
    Date timestamp,
    Map<String, Object> properties) {

  public static final class Builder {
    String anonymousId;
    String userId;
    String event;
    Context context;
    Date timestamp;
    Map<String, Object> properties;

    public Builder(String event) {
      this.event = event;
      this.timestamp = new Date();
    }

    public Builder anonymousId(String anonymousId) {
      this.anonymousId = anonymousId;
      return this;
    }

    public Builder userId(String userId) {
      this.userId = userId;
      return this;
    }

    public Builder context(Context context) {
      this.context = context;
      return this;
    }

    public Builder properties(Map<String, Object> properties) {
      this.properties = properties;
      return this;
    }

    public TrackEvent build() {
      return new TrackEvent(anonymousId, userId, event, context, timestamp, properties);
    }
  }
}
