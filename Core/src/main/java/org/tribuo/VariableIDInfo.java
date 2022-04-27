/*
 * Copyright (c) 2015-2020, Oracle and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.tribuo;

import com.google.protobuf.Message;

/**
 * Adds an id number to a {@link VariableInfo}.
 */
public interface VariableIDInfo<VISD extends Message> extends VariableInfo<VISD> {

    /**
     * The id number associated with this variable.
     * <p>
     * Ids are non-negative integers.
     * @return The id number.
     */
    public int getID();

}
