package com.demo.web.controller

import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import java.util.*

/**
 * Home controller
 *
 * @version Revision History
 * <pre>
 * Author   Version     Date            Changes
 * pankplee  1.0         9/20/2017         Created
</pre> *
 * @since 1.0
 */
@Controller
@RequestMapping("/")
class HomeController : ApplicationContextAware {

    private lateinit var applicationContext: ApplicationContext

    @Throws(BeansException::class)
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        this.applicationContext = applicationContext
    }

    @RequestMapping(value = ["/showBeans"], method = arrayOf(RequestMethod.GET))
    @ResponseBody
    fun showBeans(): String {
        return Arrays.asList(*applicationContext.beanDefinitionNames).toString() + "\n" +
                Arrays.asList(*applicationContext.parent.beanDefinitionNames).toString()
    }

    @RequestMapping
    fun home(): String {
        return "redirect:/user"
    }
}