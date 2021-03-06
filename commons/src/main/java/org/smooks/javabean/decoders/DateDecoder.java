/*-
 * ========================LICENSE_START=================================
 * Smooks Commons
 * %%
 * Copyright (C) 2020 Smooks
 * %%
 * Licensed under the terms of the Apache License Version 2.0, or
 * the GNU Lesser General Public License version 3.0 or later.
 * 
 * SPDX-License-Identifier: Apache-2.0 OR LGPL-3.0-or-later
 * 
 * ======================================================================
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * ======================================================================
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * =========================LICENSE_END==================================
 */
package org.smooks.javabean.decoders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.smooks.assertion.AssertArgument;
import org.smooks.javabean.DataDecodeException;
import org.smooks.javabean.DataDecoder;
import org.smooks.javabean.DataEncoder;
import org.smooks.javabean.DecodeType;

/**
 * {@link java.util.Date} data decoder.
 * <p/>
 * Decodes the supplied string into a {@link java.util.Date} value
 * based on the supplied "{@link java.text.SimpleDateFormat format}" parameter, or the default (see below).
 * <p/>
 * The default date format used is "<i>yyyy-MM-dd'T'HH:mm:ss</i>" (see {@link SimpleDateFormat}).
 * This format is based on the <a href="http://www.w3.org/TR/2004/REC-xmlschema-2-20041028/#isoformats">ISO 8601</a>
 * standard as used by the XML Schema type "<a href="http://www.w3.org/TR/xmlschema-2/#dateTime">dateTime</a>".
 * <p/>
 * This decoder is synchronized on its underlying {@link SimpleDateFormat} instance.
 * @see LocaleAwareDateDecoder
 *
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
@DecodeType(Date.class)
public class DateDecoder extends LocaleAwareDateDecoder implements DataDecoder, DataEncoder {

    public Object decode(String data) throws DataDecodeException {
        try {
            // Must be sync'd - DateFormat is not synchronized.
            synchronized(decoder) {
                return decoder.parse(data.trim());
            }
        } catch (ParseException e) {
            throw new DataDecodeException("Error decoding Date data value '" + data + "' with decode format '" + format + "'.", e);
        }
    }

    public String encode(Object date) throws DataDecodeException {
        AssertArgument.isNotNull(date, "date");
        if(!(date instanceof Date)) {
            throw new DataDecodeException("Cannot encode Object type '" + date.getClass().getName() + "'.  Must be type '" + Date.class.getName() + "'.");
        }
        // Must be sync'd - DateFormat is not synchronized.
        synchronized(decoder) {
            return decoder.format((Date) date);
        }
    }
}
