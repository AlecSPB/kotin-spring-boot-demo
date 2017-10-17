/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2016, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package com.demo.web.conversion

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.format.Formatter

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateFormatter : Formatter<Date> {

    private var messageSource: ReloadableResourceBundleMessageSource? = null

    @Autowired
    fun setMessageSource(messageSource: ReloadableResourceBundleMessageSource) {
        this.messageSource = messageSource
    }

    @Throws(ParseException::class)
    override fun parse(text: String, locale: Locale): Date {
        val dateFormat = createDateFormat(locale)
        return dateFormat.parse(text)
    }

    override fun print(`object`: Date, locale: Locale): String {
        val dateFormat = createDateFormat(locale)
        return dateFormat.format(`object`)
    }

    private fun createDateFormat(locale: Locale): SimpleDateFormat {
        val format = this.messageSource!!.getMessage("date.format", null, locale)
        val dateFormat = SimpleDateFormat(format)
        dateFormat.isLenient = false
        return dateFormat
    }

}
